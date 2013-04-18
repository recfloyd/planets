package org.rec.planets.jupiter.action.script;

import java.util.Map;

import org.rec.planets.mercury.script.GroovyScriptUtil;

/**
 * groovy执行器<br/>
 * <code>
 * 	<bean id="Groovy执行Action" class="">
 * 		<property name="resource" value="Resource资源"/>
 * 		<property name="encoding" value="默认UTF-8"/>
 * 	</bean>
 * </code>
 * 
 * @author rec
 * 
 */
public class GroovyScriptAction extends AbstractScriptAction {
	@Override
	protected void runScript(String script, Map<String, Object> bindings)
			throws Exception {
		GroovyScriptUtil.run(script, bindings);
	}

}
