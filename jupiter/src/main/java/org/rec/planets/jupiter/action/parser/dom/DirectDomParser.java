package org.rec.planets.jupiter.action.parser.dom;

import com.google.common.base.Strings;
import org.jsoup.nodes.Element;
import org.rec.planets.jupiter.action.AbstractReadProcessWriteAction;
import org.rec.planets.jupiter.action.parser.dom.bean.DomElement;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * Created with IntelliJ IDEA.
 * User: rec
 * Date: 13-4-25
 * Time: 下午8:47
 * To change this template use File | Settings | File Templates.
 */
public class DirectDomParser extends AbstractReadProcessWriteAction {
    protected DomElement domElement;

    @Override
    protected Object processInternal(ActionContext context, Object source) throws Exception {
        Element element = (Element) source;
        if (Strings.isNullOrEmpty(domElement.getAttrabute())) {
            return element.html().trim();
        } else {
            return element.attr(domElement.getAttrabute()).trim();
        }
    }

    public void setDomElement(DomElement domElement) {
        this.domElement = domElement;
    }
}
