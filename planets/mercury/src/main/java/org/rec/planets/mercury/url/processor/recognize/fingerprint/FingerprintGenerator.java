package org.rec.planets.mercury.url.processor.recognize.fingerprint;

import java.util.List;

import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.parse.RegexUtil;
import org.rec.planets.mercury.parse.URLUtil;
import org.rec.planets.mercury.parse.bean.OrderedRegex;
import org.rec.planets.mercury.url.processor.URLProcessor;
import org.rec.planets.mercury.url.processor.recognize.fingerprint.calculator.Calculator;
import org.rec.planets.mercury.url.processor.recognize.fingerprint.combiner.Combiner;

import com.google.common.base.Joiner;

/**
 * 指纹生成器,这个类首先通过一个正则表达式计算url的特征值
 * 
 * @author rec
 * 
 */
public class FingerprintGenerator implements URLProcessor {
	/**
	 * 特征值分隔符
	 */
	private String eigenvalueSeperator = "&";
	/**
	 * 正则表达式组
	 */
	private List<OrderedRegex> regexes;
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
		String source = omitHost ? URLUtil.getRelativePath(crawlURL.getUrl())
				: crawlURL.getUrl();
		String[] s = null;
		String eigenvalue = null;
		for (OrderedRegex regex : regexes) {
			s = RegexUtil.groupFirstMatch(source, regex, strict);
			if (s != null)
				break;
		}

		if (s == null) {
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

	public void setRegexes(List<OrderedRegex> regexes) {
		this.regexes = regexes;
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
