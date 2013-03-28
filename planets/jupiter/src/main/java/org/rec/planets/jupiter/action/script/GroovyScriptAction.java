package org.rec.planets.jupiter.action.script;

import java.util.Map;

import org.rec.planets.mercury.script.GroovyScriptUtil;

public class GroovyScriptAction extends AbstractScriptAction {
	@Override
	protected void runScript(String script, Map<String, Object> bindings)
			throws Exception {
		GroovyScriptUtil.run(script, bindings);
	}

}
