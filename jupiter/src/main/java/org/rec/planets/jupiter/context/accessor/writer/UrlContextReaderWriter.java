package org.rec.planets.jupiter.context.accessor.writer;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.reader.ContextReader;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: rec
 * Date: 13-4-25
 * Time: 下午4:38
 * To change this template use File | Settings | File Templates.
 */
public class UrlContextReaderWriter implements ContextReader, ContextWriter {
    private Map<String, Object> getUrlContext(Object context) {
        ActionContext actionContext = (ActionContext) context;
        return actionContext.getUrlcontext();
    }

    @Override
    public Object read(Object context, String key) {
        Map<String, Object> urlContext = this.getUrlContext(context);
        if (urlContext == null)
            return null;
        else return urlContext.get(key);
    }

    @Override
    public void write(Object context, String key, Object result) {
        Map<String, Object> urlContext = this.getUrlContext(context);
        if (urlContext != null)
            urlContext.put(key, result);
    }

    @Override
    public Object remove(Object context, String key) {
        Map<String, Object> urlContext = this.getUrlContext(context);
        if (urlContext != null)
            return urlContext.remove(key);
        else return null;
    }
}
