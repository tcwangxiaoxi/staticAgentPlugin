package com.wang.agent.staticAgent;

import java.lang.instrument.Instrumentation;

/**
 * 
 * @ClassName: StaticAgentMain
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Company:
 * @author River.W
 * @date 2016年4月12日 上午11:28:49
 *
 */
public class StaticAgentMain {
	public static void premain(String options, Instrumentation ins) {
		System.out.println(
				"=============================================================StaticAgentMain============================================================================");
		// 注册我自己的字节码转换器
		ins.addTransformer(new StaticAgentTransformer());
	}
}
