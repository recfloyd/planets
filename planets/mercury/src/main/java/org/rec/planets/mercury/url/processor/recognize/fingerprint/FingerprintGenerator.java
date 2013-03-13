package org.rec.planets.mercury.url.processor.recognize.fingerprint;

import java.util.Collections;
import java.util.List;

import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.parse.RegexUtil;
import org.rec.planets.mercury.parse.URLUtil;
import org.rec.planets.mercury.url.processor.URLProcessor;
import org.rec.planets.mercury.url.processor.recognize.fingerprint.bean.FingerprintIndicator;
import org.rec.planets.mercury.url.processor.recognize.fingerprint.calculator.Calculator;
import org.rec.planets.mercury.url.processor.recognize.fingerprint.combiner.Combiner;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Joiner;

/**
 * 指纹生成器,这个类首先通过一个正则表达式计算url的特征值
 * 
 * @author rec
 * 
 */
public class FingerprintGenerator extends AbstractBean implements URLProcessor {
	/**
	 * 特征值分隔符
	 */
	private String eigenvalueSeperator = "&";
	/**
	 * 正则表达式组
	 */
	private List<FingerprintIndicator> indicators;
	/**
	 * 是否允许未知类型
	 */
	private boolean allowUnknownType;
	/**
	 * 特征字符串连接器
	 */
	private Combiner combiner;
	/**
	 * 长整数计算器
	 */
	private Calculator calculator;
	/**
	 * 如果指纹已经存在是否覆盖
	 */
	private boolean overwriten;

	protected String calculateEigenvalue(CrawlURL crawlURL) throws Exception {
		String url = crawlURL.getUrl();
		String omitHostURL = URLUtil.getRelativePath(url);
		List<String> s = null;
		String eigenvalue = null;

		for (FingerprintIndicator indicator : indicators) {
			s = RegexUtil.getFirstGroups(indicator.isOmitHost() ? url
					: omitHostURL, indicator.getRegex());
			if (!CollectionUtils.isEmpty(s))
				break;
		}

		if (CollectionUtils.isEmpty(s)) {
			if (allowUnknownType) {
				eigenvalue = crawlURL.getUrl();// 如果允许未知类型的url计算特征值,那么以全部url作为特征值
			} else {
				throw new IllegalArgumentException(
						"cannot find calculate eigenvalue for crawlURL: "
								+ crawlURL);
			}
		} else {
			eigenvalue = Joiner.on(eigenvalueSeperator).skipNulls().join(s);
		}
		return eigenvalue;
	}

	@Override
	public void process(CrawlURL crawlURL, CrawlURL baseURL) throws Exception {
		if (crawlURL.getFingerprint() != null && !overwriten)
			return;
		String eigenvalue = calculateEigenvalue(crawlURL);
		String combinedString = combiner.combine(crawlURL, eigenvalue);
		long fingerprint = calculator.calculate(combinedString);
		crawlURL.setFingerprint(fingerprint);
	}

	public void setEigenvalueSeperator(String eigenvalueSeperator) {
		this.eigenvalueSeperator = eigenvalueSeperator;
	}

	public void setAllowUnknownType(boolean allowUnknownType) {
		this.allowUnknownType = allowUnknownType;
	}

	public void setCombiner(Combiner combiner) {
		this.combiner = combiner;
	}

	public void setCalculator(Calculator calculator) {
		this.calculator = calculator;
	}

	public void setOverwriten(boolean overwriten) {
		this.overwriten = overwriten;
	}

	public void setIndicators(List<FingerprintIndicator> indicators) {
		this.indicators = indicators;
		Collections.sort(this.indicators);
	}
}
