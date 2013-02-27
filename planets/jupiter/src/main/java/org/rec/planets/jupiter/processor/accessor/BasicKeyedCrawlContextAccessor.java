package org.rec.planets.jupiter.processor.accessor;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.expression.ELUtil;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.Assert;

public class BasicKeyedCrawlContextAccessor extends AbstractBean implements
		CrawlContextAccessor {
	protected String sourceKey;
	protected String resultKey;
	protected boolean omitSourceAbsence;
	protected boolean omitResultAbsence;

	@Override
	public Object get(CrawlContext crawlContext) {
		Object source = ELUtil.evalFromObject(crawlContext, sourceKey);
		if (!omitSourceAbsence)
			Assert.notNull(source, "cannot get source from context by key "
					+ sourceKey + " of CrawlURL " + crawlContext.getCrawlURL());
		return source;
	}

	@Override
	public void set(CrawlContext crawlContext, Object result) {
		if (!omitResultAbsence)
			Assert.notNull(result, "cannot save result to context by key "
					+ resultKey + " of CrawlURL " + crawlContext.getCrawlURL());
		if (result != null) {
			BeanWrapper bw = new BeanWrapperImpl(crawlContext);
			bw.setPropertyValue(resultKey, result);
		}
	}

	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

	public void setOmitSourceAbsence(boolean omitSourceAbsence) {
		this.omitSourceAbsence = omitSourceAbsence;
	}

	public void setOmitResultAbsence(boolean omitResultAbsence) {
		this.omitResultAbsence = omitResultAbsence;
	}
}
