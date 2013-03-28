package org.rec.planets.mercury.script;

import java.util.Map;

import org.rec.planets.mercury.expression.MvelUtil;

public abstract class MvelScriptUtil {
	public static Object run(String script, Map<String, Object> bindings) {
		return MvelUtil.runScript(bindings, script);
	}
}
