/**   
* @Title: AopContext.java 
* @Package com.wang.agent.staticAgent 
* @Description: TODO(用一句话描述该文件做什么) 
* @author River.W   
* @date 2016年2月11日 下午9:52:23 
* @version V1.0   
*/
package com.wang.agent.staticAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.wang.agent.staticAgent.config.StaticAopConfig;
import com.wang.agent.staticAgent.model.StaticMethodBody;

import javassist.CtClass;
import javassist.CtMethod;

/**
 * @ClassName: AopContext
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Company:
 * @author River.W
 * @date 2016年2月11日 下午9:52:23
 * 
 */
public class MatchMethodContext {

	private static final String SURFIX = "$proxy_";

	private final CtClass cc;

	private final Map<CtMethod, Integer> matchedMethodIndexMap = new HashMap<CtMethod, Integer>();

	private final Map<CtMethod, List<StaticMethodBody>> matchedMethodMap = new HashMap<CtMethod, List<StaticMethodBody>>();

	private MatchMethodContext(CtClass cc) {
		this.cc = cc;
	};

	public static MatchMethodContext createMatchMethodContext(CtClass cc) {
		return new MatchMethodContext(cc);
	}

	public void addMatchMethod(CtMethod method, StaticAopConfig configItem) {
		Integer matchIndex = matchedMethodIndexMap.get(method);
		if (matchIndex != null) {
			matchIndex++;
			matchedMethodIndexMap.put(method, matchIndex);
		} else {
			matchedMethodIndexMap.put(method, 1);
		}
		initMatchedInfo(method, configItem);
	}

	/**
	 * @Title: initMatchedInfo @Description: 初始化匹配的方法的配置信息。 @param @param
	 *         method @param @param configItem 设定参数 @return void 返回类型 @throws
	 */
	private void initMatchedInfo(CtMethod method, StaticAopConfig configItem) {

		String[] newMethodNamePair = getNewMethodNamePair(method);
		StaticMethodBody newMethodBody = StaticMethodBody.createMethodBody(newMethodNamePair[0], newMethodNamePair[1],
				configItem.getStaticAopTemplate());

		List<StaticMethodBody> list = matchedMethodMap.get(method);
		if (list == null) {
			list = new ArrayList<StaticMethodBody>();
			matchedMethodMap.put(method, list);
		}
		list.add(newMethodBody);

	}

	/**
	 * 
	 * @Title: getNewMethodNamePair @Description:
	 *         获取当前方法需要创建的代理方法的名称以及要代理的方法的名称。 @param method 当前匹配的方法对象。 @return
	 *         String[] 返回类型 长度为2，第一个为要创建的代理方法的方法名，第二个为要代理的方法的方法名。 @throws
	 */
	private String[] getNewMethodNamePair(CtMethod method) {
		Integer matchedIndex = matchedMethodIndexMap.get(method);
		if (matchedIndex == null) {
			throw new IllegalArgumentException("该方法没有被匹配，无法获取方法名");
		}
		String origName = method.getName();
		if (matchedIndex == 1) {
			return new String[] { origName, getNewMethodNameByIndex(origName, matchedIndex) };
		}
		int oldIndex = matchedIndex - 1;
		return new String[] { getNewMethodNameByIndex(origName, oldIndex),
				getNewMethodNameByIndex(origName, matchedIndex) };
	}

	private String getNewMethodNameByIndex(String origName, Integer index) {
		return new StringBuilder(origName).append(SURFIX).append(index).toString();
	}

	public Map<CtMethod, List<StaticMethodBody>> getMatchedMethodInfos() {
		return matchedMethodMap;
	}

	/**
	 * 
	 * @Title: getNewOrigMethodName @Description: 获得指定原始方法的新名称。
	 * @param @return
	 *            设定参数 @return String 返回类型 @throws
	 */
	public String getNewOrigMethodName(CtMethod method) {
		Integer matchedIndex = matchedMethodIndexMap.get(method);
		if (matchedIndex == null) {
			throw new IllegalArgumentException("该方法没有被匹配，无法获取方法名");
		}
		return getNewMethodNameByIndex(method.getName(), matchedIndex);
	}

	@Override
	public String toString() {
		StringBuilder toStringBuilder = new StringBuilder("matchedMethodMap:\n");
		for (Entry<CtMethod, List<StaticMethodBody>> entry : matchedMethodMap.entrySet()) {
			CtMethod mold = entry.getKey();
			toStringBuilder.append("==").append(mold.getLongName()).append(":\n");

			for (StaticMethodBody methodBody : entry.getValue()) {
				toStringBuilder.append("==++").append(methodBody).append("\n");
			}
		}
		return toStringBuilder.toString();
	}

}
