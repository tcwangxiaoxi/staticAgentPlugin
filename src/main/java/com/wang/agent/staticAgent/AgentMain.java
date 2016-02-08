package com.wang.agent.staticAgent;

import java.lang.instrument.Instrumentation;

/**
 * Hello world!
 *
 */
public class AgentMain {
	public static void premain(String options, Instrumentation ins) {
		// 注册我自己的字节码转换器
		ins.addTransformer(new MyClassFileTransformer());
	}
}
