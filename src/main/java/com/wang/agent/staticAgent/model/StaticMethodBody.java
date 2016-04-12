/**   
* @Title: MethodBody.java 
* @Package com.wang.agent.staticAgent.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @author River.W   
* @date 2016年2月13日 下午2:34:54 
* @version V1.0   
*/
package com.wang.agent.staticAgent.model;

import com.wang.agent.staticAgent.template.StaticAopTemplate;

/**
 * @ClassName: StaticMethodBody
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Company:
 * @author River.W
 * @date 2016年2月13日 下午2:34:54
 * 
 */
public class StaticMethodBody {

	private String methodName;

	private String processMethodName;

	private StaticAopTemplate template;

	private StaticMethodBody() {
	}

	public static StaticMethodBody createMethodBody(String methodName, String processMethodName,
			StaticAopTemplate template) {
		StaticMethodBody methodBody = new StaticMethodBody();
		methodBody.methodName = methodName;
		methodBody.processMethodName = processMethodName;
		methodBody.template = template;
		return methodBody;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getProcessMethodName() {
		return processMethodName;
	}

	public StaticAopTemplate getTemplate() {
		return template;
	}

	@Override
	public String toString() {
		return "StaticMethodBody [methodName=" + methodName + ", processMethodName=" + processMethodName + ", template="
				+ template.methodTemplate() + "]";
	}

}
