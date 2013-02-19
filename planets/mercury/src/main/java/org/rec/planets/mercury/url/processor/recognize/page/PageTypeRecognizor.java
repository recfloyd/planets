package org.rec.planets.mercury.url.processor.recognize.page;

import java.util.Collections;
import java.util.List;

import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.parse.RegexUtil;
import org.rec.planets.mercury.parse.URLUtil;
import org.rec.planets.mercury.url.processor.URLProcessor;
import org.rec.planets.mercury.url.processor.recognize.page.bean.PageTypeOrderedRegex;

/**
 * 依靠一组正则对url的pageType进行识别的处理器
 * 
 * @author rec
 * 
 */
public class PageTypeRecognizor implements URLProcessor {
	/**
	 * 与pageType相关的正则表达式组
	 */
	private List<PageTypeOrderedRegex> regexes;
	/**
	 * 使用正则匹配时是否严谨匹配
	 */
	private boolean strict = true;
	/**
	 * 正则是否忽略了域名
	 */
	private boolean omitHost = true;
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
		String source = omitHost ? URLUtil.getRelativePath(crawlURL.getUrl())
				: crawlURL.getUrl();
		for (PageTypeOrderedRegex regex : regexes) {
			if ((strict && RegexUtil.matches(source, regex))
					|| (!strict && RegexUtil.find(source, regex))) {
				crawlURL.setPageType(regex.getPageType());
				return;
			}
		}

		if (!allowUnknownType)
			throw new IllegalArgumentException(
					"cannot find pageType for crawlURL: " + crawlURL);
	}

	public void setRegexes(List<PageTypeOrderedRegex> regexes) {
		this.regexes = regexes;
		Collections.sort(this.regexes);
	}

	public void setAllowUnknownType(boolean allowUnknownType) {
		this.allowUnknownType = allowUnknownType;
	}

	public void setStrict(boolean strict) {
		this.strict = strict;
	}

	public void setOmitHost(boolean omitHost) {
		this.omitHost = omitHost;
	}

	public void setOverwriten(boolean overwriten) {
		this.overwriten = overwriten;
	}
}
