package org.rec.planets.mercury.url.processor.recognize.fingerprint;

import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.url.processor.URLProcessor;
import org.rec.planets.mercury.url.processor.recognize.fingerprint.calculator.Calculator;
import org.rec.planets.mercury.url.processor.recognize.fingerprint.combiner.Combiner;
import org.rec.planets.mercury.url.processor.recognize.fingerprint.eigen.EigenPicker;

/**
 * 指纹生成器,通过3个步骤生成指纹<br/>
 * 1.抽取特征值<br/>
 * 2.生成特征字符串<br/>
 * 3.根据特征字符串计算指纹<br/>
 * 
 * @author rec
 * 
 */
public class FingerprintGenerator extends AbstractBean implements URLProcessor {
	/**
	 * 特征值抽取器
	 */
	private EigenPicker eigenPicker;
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
		String eigen = this.eigenPicker.pickup(crawlURL);
		if (eigen == null) {
			if (allowUnknownType) {
				eigen = crawlURL.getUrl();// 如果允许未知类型的url计算特征值,那么以全部url作为特征值
			} else {
				throw new IllegalArgumentException(
						"cannot find calculate eigenvalue for crawlURL: "
								+ crawlURL);
			}
		}
		return eigen;
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
}
