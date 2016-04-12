/**   
* @Title: Advice.java 
* @Package com.wang.agent.staticAgent.intercept 
* @Description: TODO(用一句话描述该文件做什么) 
* @author River.W   
* @date 2016年4月12日 上午11:19:55 
* @version V1.0   
*/
package com.wang.agent.staticAgent.intercept;

import com.wang.agent.staticAgent.test.TestStaticCallback.Callback;

/**
 * @ClassName: SAdvice
 * @Description: 通知接口，用于实现拦截器需要的自定义通知。
 * @Company:
 * @author River.W
 * @date 2016年4月12日 上午11:19:55
 * 
 */
public interface StaticAdvice {

	Object intercept(Callback callback, Object... args);

}
