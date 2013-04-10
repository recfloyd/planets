package org.rec.planets.mercury.url.processor.recognize.fingerprint.eigen;

import java.util.Collections;
import java.util.List;

import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.parse.RegexUtil;
import org.rec.planets.mercury.parse.URLUtil;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Joiner;

public class RegexEigenPicker implements EigenPicker {
	/**
	 * 特征值分隔符
	 */
	private String seperator = "&";
	/**
	 * 正则表达式组
	 */
	private List<EigenRegex> regexList;

	@Override
	public String pickup(CrawlURL crawlURL) {
		String url = crawlURL.getUrl();
		String omitHostURL = URLUtil.getRelativePath(url);
		List<String> s = null;

		for (EigenRegex regex : regexList) {
			s = RegexUtil.getFirstGroups(
					regex.isOmitHost() ? omitHostURL : url, regex.getRegex());
			if (!CollectionUtils.isEmpty(s))
				break;
		}

		if (!CollectionUtils.isEmpty(s)) {
			return Joiner.on(seperator).skipNulls().join(s);
		} else {
			return null;
		}
	}

	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}

	public void setRegexList(List<EigenRegex> regexList) {
		this.regexList = regexList;
		Collections.sort(this.regexList);
	}
}
