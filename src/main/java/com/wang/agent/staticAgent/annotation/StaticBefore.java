/**   
* @Title: SBefore.java 
* @Package com.wang.agent.staticAgent.annotation 
* @Description: TODO(用一句话描述该文件做什么) 
* @author River.W   
* @date 2016年2月9日 上午12:40:40 
* @version V1.0   
*/
package com.wang.agent.staticAgent.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: StaticBefore
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Company:
 * @author River.W
 * @date 2016年2月9日 上午12:40:40
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface StaticBefore {

}
