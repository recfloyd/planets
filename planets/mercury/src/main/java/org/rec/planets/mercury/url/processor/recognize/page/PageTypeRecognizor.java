package org.rec.planets.mercury.url.processor.recognize.page;

import java.util.Collections;
import java.util.List;

import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.domain.constant.PageTypeConstants;
import org.rec.planets.mercury.parse.RegexUtil;
import org.rec.planets.mercury.parse.URLUtil;
import org.rec.planets.mercury.url.processor.URLProcessor;
import org.rec.planets.mercury.url.processor.recognize.page.bean.PageTypeIndicator;

/**
 * 依靠一组正则对url的pageType进行识别的处理器
 * 
 * @author rec
 * 
 */
public class PageTypeRecognizor extends AbstractBean implements URLProcessor {
	/**
	 * 与pageType相关的正则表达式组
	 */
	private List<PageTypeIndicator> indicators;
	/**
	 * 是否允许未知类型
	 */
	private boolean allowUnknownType;
	/**
	 * 如果pageType已经存在是否覆盖
	 */
	private boolean overwriten;

	@Override
	public void process(CrawlURL crawlURL, CrawlURL baseURL) throws Exception {
		if (crawlURL.getPageType() != null && !overwriten)
			return;
		String url = crawlURL.getUrl();
		String omitHostURL = URLUtil.getRelativePath(url);

		for (PageTypeIndicator indicator : indicators) {
			if (RegexUtil.test(indicator.isOmitHost() ? omitHostURL : url,
					indicator.getRegex())) {
				crawlURL.setPageType(indicator.getPageType());
				return;
			}
		}

		if (!allowUnknownType)
			throw new IllegalArgumentException(
					"cannot find pageType for crawlURL: " + crawlURL);
		else
			crawlURL.setPageType(PageTypeConstants.PAGE_TYPE_UNKNOWN);
	}

	public void setIndicators(List<PageTypeIndicator> indicators) {
		this.indicators = indicators;
		Collections.sort(this.indicators);
	}

	public void setAllowUnknownType(boolean allowUnknownType) {
		this.allowUnknownType = allowUnknownType;
	}

	public void setOverwriten(boolean overwriten) {
		this.overwriten = overwriten;
	}
}
