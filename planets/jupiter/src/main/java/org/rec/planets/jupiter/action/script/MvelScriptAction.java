package org.rec.planets.jupiter.action.script;

import java.util.Map;

import org.rec.planets.mercury.script.MvelScriptUtil;

public class MvelScriptAction extends AbstractScriptAction {

	@Override
	protected void runScript(String script, Map<String, Object> bindings)
			throws Exception {
		MvelScriptUtil.run(script, bindings);
	}
}
