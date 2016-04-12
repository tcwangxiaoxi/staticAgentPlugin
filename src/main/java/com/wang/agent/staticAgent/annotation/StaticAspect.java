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
 * @ClassName: StaticAspect
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Company:
 * @author River.W
 * @date 2016年2月9日 上午12:40:40
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface StaticAspect {

	/**
	 * 
	 * @Title: value @Description: TODO(这里用一句话描述这个方法的作用) @param @return
	 *         设定参数 @return String 返回类型 @throws
	 */
	public String value() default "";
}
