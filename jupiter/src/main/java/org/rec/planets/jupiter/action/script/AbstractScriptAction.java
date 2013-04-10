package org.rec.planets.jupiter.action.script;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

public abstract class AbstractScriptAction implements Action,
		ApplicationContextAware {
	public static final String BINDINGS_KEY_APPLICATIONCONTEXT = "script_bandings_applicationContext";
	public static final String BINDINGS_KEY_ACTIONCONTEXT = "script_bandings_actionContext";

	protected Resource resource;
	protected String encoding = "UTF-8";
	protected ApplicationContext applicationContext;

	@Override
	public void execute(ActionContext context) throws Exception {
		Charset charset = Charset.forName(encoding);
		String script = StreamUtils.copyToString(resource.getInputStream(),
				charset);
		Map<String, Object> bindings = new HashMap<String, Object>();
		bindings.put(BINDINGS_KEY_APPLICATIONCONTEXT, applicationContext);
		bindings.put(BINDINGS_KEY_ACTIONCONTEXT, context);
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

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
