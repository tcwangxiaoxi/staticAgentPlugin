/**   
* @Title: TestCallback.java 
* @Package com.wang.agent.staticAgent.test 
* @Description: TODO(用一句话描述该文件做什么) 
* @author River.W   
* @date 2016年4月11日 下午7:32:57 
* @version V1.0   
*/
package com.wang.agent.staticAgent.test;

/**
 * @ClassName: TestCallback
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Company:
 * @author River.W
 * @date 2016年4月11日 下午7:32:57
 * 
 */
public class TestStaticCallback {

	public static interface Callback {
		Object intercept();
	}

	public static class Method {

		public Integer test(Integer arg1, Integer arg2) {

			Callback callback = new Callback() {
				@Override
				public Object intercept() {
					return Method.this.test_proxy(arg1, arg2);
				}
			};
			Intercept intercept = new Intercept();
			return (Integer) intercept.doIt(callback, arg1);
		}

		public Integer test_proxy(Integer arg1, Integer arg2) {
			Integer str = arg1 / arg2;
			System.out.println(str);
			return str;
		}
	}

	public static class Intercept {

		public Object doIt(Callback callback, Object... args) {
			System.out.println("start...");
			try {
				Object intercept = callback.intercept();
				System.out.println("finish...");
				return intercept;
			} catch (Exception e) {
				System.out.println("exception...");
			}
			return null;
		}
	}

	public static void main(String[] args) {
		new TestStaticCallback.Method().test(123, 0);
	}

}
