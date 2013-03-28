package org.rec.planets.jupiter.action.script;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

public abstract class AbstractScriptAction implements Action {
	public static final String BINDINGS_KEY = "script_bandings";
	protected Resource resource;
	protected String encoding = "UTF-8";

	@Override
	public void execute(ActionContext context) throws Exception {
		Charset charset = Charset.forName(encoding);
		String script = StreamUtils.copyToString(resource.getInputStream(),
				charset);
		Map<String, Object> bindings = new HashMap<String, Object>();
		bindings.put(BINDINGS_KEY, context);
		runScript(script, bindings);
	}

	protected abstract void runScript(String script,
			Map<String, Object> bindings) throws Exception;

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
