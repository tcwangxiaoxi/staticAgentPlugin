/**   
* @Title: AopConfig.java 
* @Package com.wang.agent.staticAgent.config 
* @Description: TODO(用一句话描述该文件做什么) 
* @author River.W   
* @date 2016年2月11日 下午9:18:50 
* @version V1.0   
*/
package com.wang.agent.staticAgent.config;

import java.util.regex.Pattern;

import com.wang.agent.staticAgent.template.StaticAopTemplate;

/**
 * @ClassName: StaticAopConfig
 * @Description: AOP配置项，用于根据该配置项进行匹配要进行拦截的对象，并实现具体功能。
 * @Company:
 * @author River.W
 * @date 2016年2月11日 下午9:18:50
 * 
 */
public class StaticAopConfig {

	private String classNameParttern;

	private Pattern classNamePartternItem;

	private String methodNameParttern;

	private Pattern methodNamePartternItem;

	private StaticAopTemplate staticAopTemplate;

	public String getClassNameParttern() {
		return classNameParttern;
	}

	public String getMethodNameParttern() {
		return methodNameParttern;
	}

	public StaticAopTemplate getStaticAopTemplate() {
		return staticAopTemplate;
	}

	public Pattern getClassNamePatternItem() {
		return classNamePartternItem;
	}

	public Pattern getMethodNamePartternItem() {
		return methodNamePartternItem;
	}

	private StaticAopConfig() {
	};

	public static StaticAopConfig createAopConfig(String classNameParttern, String methodNameParttern,
			StaticAopTemplate staticAopTemplate) {
		StaticAopConfig aopConfig = new StaticAopConfig();
		aopConfig.classNameParttern = classNameParttern;
		aopConfig.methodNameParttern = methodNameParttern;
		aopConfig.staticAopTemplate = staticAopTemplate;
		aopConfig.classNamePartternItem = Pattern.compile(classNameParttern);
		aopConfig.methodNamePartternItem = Pattern.compile(methodNameParttern);
		return aopConfig;
	}

	@Override
	public String toString() {
		return "StaticAopConfig [classNameParttern=" + classNameParttern + ", methodNameParttern=" + methodNameParttern
				+ ", staticAopTemplate=" + staticAopTemplate.methodTemplate() + "]";
	}

}
