/**   
* @Title: MyClassFileTransformer.java 
* @Package com.wang.agent.staticAgent 
* @Description: TODO(用一句话描述该文件做什么) 
* @author River.W   
* @date 2016年2月7日 上午12:46:38 
* @version V1.0   
*/
package com.wang.agent.staticAgent;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.wang.agent.staticAgent.config.StaticAopConfig;
import com.wang.agent.staticAgent.model.StaticMethodBody;
import com.wang.agent.staticAgent.template.SimpleAopTemplate;
import com.wang.agent.staticAgent.template.StaticAopTemplate;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

/**
 * @ClassName: MyClassFileTransformer
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Company:
 * @author River.W
 * @date 2016年2月7日 上午12:46:38
 * 
 */
public class StaticAgentTransformer implements ClassFileTransformer {

	private static final List<StaticAopConfig> configs = new ArrayList<StaticAopConfig>();

	static {
		configs.add(StaticAopConfig.createAopConfig(
				"com.wang.springboot.mySpringBoot.api.account.service.impl.AccountServiceImpl", "aopTest2",
				new SimpleAopTemplate()));
	}

	/**
	 * 字节码加载到虚拟机前会进入这个方法
	 */
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

		// javassist的包名是用点分割的，需要转换下
		if (className.indexOf("/") != -1) {
			className = className.replaceAll("/", ".");
		}
		// 通过包名获取类文件
		CtClass cc = null;
		try {
			cc = ClassPool.getDefault().get(className);
		} catch (NotFoundException e) {
		}
		// 匹配信息，并拦截
		return aopClass(className, cc);
	}

	private byte[] aopClass(String className, CtClass cc) {

		MatchMethodContext matchMethodContext = MatchMethodContext.createMatchMethodContext(cc);

		for (StaticAopConfig configItem : configs) {
			// 通过对类名进行拦截
			Pattern cPattern = configItem.getClassNamePatternItem();
			if (cPattern.matcher(className).matches()) {
				System.out.println("============" + className + "class matched...=================");

				// 通过包名获取类文件,目前只有类文件可以进行拦截
				if (!cc.isInterface() && !cc.isAnnotation() && !cc.isEnum() && !cc.isArray()) {
					Pattern mPattern = configItem.getMethodNamePartternItem();
					// 遍历方法，查找匹配的方法。
					for (CtMethod mItem : cc.getMethods()) {
						// TODO:方法参数签名是否匹配
						String mName = mItem.getMethodInfo().getName();
						if (mPattern.matcher(mName).matches()) {
							System.out.println("=====" + mItem.getGenericSignature() + "=======" + mName
									+ " CtMethod matched...=================");
							matchMethodContext.addMatchMethod(mItem, configItem);
							System.out.println(matchMethodContext);
							System.out.println("============ End CtMethod matched...=================");
						}
					}
					// 修改原始方法的代码
					updateOrigClass(cc, matchMethodContext);
				}
				try {
					cc.writeFile("./src");
					return cc.toBytecode();
				} catch (IOException | CannotCompileException e) {
					throw new RuntimeException("不会发生！");
				}
			}
		}
		return null;
	}

	/**
	 * @Title: updateOrigMethod @Description:
	 *         TODO(这里用一句话描述这个方法的作用) @param @param cc @param @param
	 *         matchMethodContext 设定参数 @return void 返回类型 @throws
	 */
	private void updateOrigClass(CtClass cc, MatchMethodContext matchMethodContext) {

		Map<CtMethod, List<StaticMethodBody>> matchedMethodInfos = matchMethodContext.getMatchedMethodInfos();

		for (Entry<CtMethod, List<StaticMethodBody>> entry : matchedMethodInfos.entrySet()) {
			CtMethod mold = entry.getKey();
			List<StaticMethodBody> methodList = entry.getValue();

			// 修改原始方法的方法签名
			mold.setName(matchMethodContext.getNewOrigMethodName(mold));

			for (StaticMethodBody methodBody : methodList) {
				try {
					updateOrigMethod(cc, mold, methodBody);
				} catch (NotFoundException e) {
					e.printStackTrace();
				} catch (CannotCompileException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void updateOrigMethod(CtClass clas, CtMethod origM, StaticMethodBody methodBody)
			throws NotFoundException, CannotCompileException {

		// 拷贝被拦截的方法副本
		StaticAopTemplate template = methodBody.getTemplate();
		System.out
				.println("============" + template.methodTemplate() + " updateOrigMethod matched...=================");
		if (template != null) {
			String overMN = methodBody.getMethodName();
			// TODO: 对于有@标注的方法，有问题。（如SpringMvc的controller）
			CtMethod newM = CtNewMethod.copy(origM, overMN, clas, null);
			String type = origM.getReturnType().getName();

			// 创建拦截方法
			StringBuilder body = new StringBuilder("{\n");
			body.append(methodBody.getTemplate().methodTemplate());
			if (!"void".equals(type)) {
				body.append("return ").append(StaticAopTemplate.origMethodReturnVar).append(" ;\n");
			}
			body.append("}");

			StringBuilder returnBuilder = new StringBuilder("\n");
			if (!"void".equals(type)) {
				// 例如："String _return = "
				returnBuilder.append(type).append(" ").append(StaticAopTemplate.origMethodReturnVar).append(" = ");
			}
			String newMN = methodBody.getProcessMethodName();
			// 例如：" String _return = create(person); " 或 "create(person);"
			returnBuilder.append(newMN).append("($$);\n");

			// 替换要拦截的方法
			String regx = StaticAopTemplate.origMethodProcess.replace("$", "\\$").replace("{", "\\{").replace("}",
					"\\}");
			String returnStr = returnBuilder.toString().replaceAll("\\$", "\\\\\\$");
			String result = body.toString().replaceAll(regx, returnStr);
			// print the generated code block just to show what was done
			System.out.println("Interceptor method body:");
			System.out.println(result);
			newM.setBody(result);
			clas.addMethod(newM);

		}
	}
}