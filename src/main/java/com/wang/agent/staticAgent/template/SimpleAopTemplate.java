/**   
* @Title: SimpleAopTemplate.java 
* @Package com.wang.agent.staticAgent.template 
* @Description: TODO(用一句话描述该文件做什么) 
* @author River.W   
* @date 2016年2月11日 下午11:52:02 
* @version V1.0   
*/
package com.wang.agent.staticAgent.template;

import java.util.concurrent.Callable;
import java.util.logging.Handler;

/**
 * @ClassName: SimpleAopTemplate
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Company:
 * @author River.W
 * @date 2016年2月11日 下午11:52:02
 * 
 */
public class SimpleAopTemplate implements StaticAopTemplate {

	/*
	 * (非 Javadoc) <p>Title: methodTemplate</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see
	 * com.wang.agent.staticAgent.template.StaticAopTemplate#methodTemplate()
	 */
	@Override
	public String methodTemplate() {
		StringBuilder bodyBuilder = new StringBuilder("long start = System.currentTimeMillis();\n");
		bodyBuilder.append(SimpleAopTemplate.origMethodProcess).append("\n System.out.println(\"Call to method create "
				+ " took \" +\n (System.currentTimeMillis()-start) + " + "\" ms.\");\n");

		return bodyBuilder.toString();
	}

}
