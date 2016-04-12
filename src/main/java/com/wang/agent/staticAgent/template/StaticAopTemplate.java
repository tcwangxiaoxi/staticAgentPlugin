/**   
* @Title: StaticAopTemplate.java 
* @Package com.wang.agent.staticAgent.template 
* @Description: TODO(用一句话描述该文件做什么) 
* @author River.W   
* @date 2016年2月12日 上午12:03:33 
* @version V1.0   
*/
package com.wang.agent.staticAgent.template;

/**
 * @ClassName: StaticAopTemplate
 * @Description: AOP模板接口。注意变量声明不能使用"_return"作为本地变量名称，会产生冲突。
 * @Company:
 * @author River.W
 * @date 2016年2月12日 上午12:03:33
 * 
 */
public interface StaticAopTemplate {
	static final String origMethodProcess = "${process}";

	static final String origMethodReturnVar = "_return";

	static final String origMethodParams = "$$";

	String methodTemplate();
}
