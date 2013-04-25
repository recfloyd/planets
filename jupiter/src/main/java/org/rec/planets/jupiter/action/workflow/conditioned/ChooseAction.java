package org.rec.planets.jupiter.action.workflow.conditioned;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.reader.ContextReader;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: rec
 * Date: 13-4-25
 * Time: 下午9:05
 * To change this template use File | Settings | File Templates.
 */
public class ChooseAction implements Action {
    private ContextReader contextReader;
    private Map<String, Action> actionMap;

    @Override
    public void execute(ActionContext context) throws Exception {
        Boolean value = null;
        for (Map.Entry<String, Action> entry : actionMap.entrySet()) {
            value = (Boolean) contextReader.read(context, entry.getKey());
            if (value != null && value) {
                entry.getValue().execute(context);
                break;
            }
        }
    }

    public void setActionMap(Map<String, Action> actionMap) {
        this.actionMap = actionMap;
    }

    public void setContextReader(ContextReader contextReader) {
        this.contextReader = contextReader;
    }
}
