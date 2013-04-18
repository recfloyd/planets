package org.rec.planets.jupiter.action.script;

import java.util.Map;

import org.rec.planets.mercury.script.MvelScriptUtil;

/**
 * mvel脚本执行器<br/>
 * <code>
 * 	<bean id="mvel脚本执行Action" class="">
 * 		<property name="resource" value="Resource资源"/>
 * 		<property name="encoding" value="默认UTF-8"/>
 * 	</bean>
 * </code>
 * @author rec
 *
 */
public class MvelScriptAction extends AbstractScriptAction {

	@Override
	protected void runScript(String script, Map<String, Object> bindings)
			throws Exception {
		MvelScriptUtil.run(script, bindings);
	}
}
