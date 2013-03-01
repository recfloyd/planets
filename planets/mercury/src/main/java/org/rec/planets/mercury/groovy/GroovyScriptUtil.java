package org.rec.planets.mercury.groovy;

import groovy.lang.GroovyShell;

import java.util.Map;

public abstract class GroovyScriptUtil {
	public static Object run(String script, Map<String, Object> bindings) {
		GroovyShell shell = new GroovyShell();
		if (bindings != null) {
			for (Map.Entry<String, Object> entry : bindings.entrySet()) {
				shell.setVariable(entry.getKey(), entry.getValue());
			}
		}
		return shell.evaluate(script);
	}
}
