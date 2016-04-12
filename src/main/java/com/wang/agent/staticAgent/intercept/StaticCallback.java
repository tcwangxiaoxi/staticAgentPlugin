/**   
* @Title: Callback.java 
* @Package com.wang.agent.staticAgent.intercept 
* @Description: TODO(用一句话描述该文件做什么) 
* @author River.W   
* @date 2016年4月12日 上午11:07:51 
* @version V1.0   
*/
package com.wang.agent.staticAgent.intercept;

/**
 * @ClassName: SCallback
 * @Description: 回调方法接口，由于扩展方法回调原始方法。
 * @Company:
 * @author River.W
 * @date 2016年4月12日 上午11:07:51
 * 
 */
public interface StaticCallback {
	Object intercept();
}
