package org.rec.planets.mercury.url.processor.modify;

import org.rec.planets.mercury.parse.bean.Regex;

public class StripSessionIDModifier extends AbstractStripModifier {
	private StripSessionIDModifier() {
	}

	private static volatile StripSessionIDModifier instance;

	public static StripSessionIDModifier getInstance() {
		if (instance == null) {
			synchronized (StripSessionIDModifier.class) {
				if (instance == null)
					instance = new StripSessionIDModifier();
			}
		}
		return instance;
	}

	private static final Regex BASE_PATTERN = new Regex(
			"^(.+)(?:(?:(?:jsessionid)|(?:phpsessid))=[0-9a-zA-Z]{32})(?:&(.*))?$",
			new int[] { 1 });
	private static final Regex SID_PATTERN = new Regex(
			"^(.+)(?:sid=[0-9a-zA-Z]{32})(?:&(.*))?$", new int[] { 1 });
	private static final Regex ASPSESSION_PATTERN = new Regex(
			"^(.+)(?:ASPSESSIONID[a-zA-Z]{8}=[a-zA-Z]{24})(?:&(.*))?$",
			new int[] { 1 });

	@Override
	public String modify(String url, String baseURL) {
		url = super.extractFromRegex(url, BASE_PATTERN);
		url = super.extractFromRegex(url, SID_PATTERN);
		url = super.extractFromRegex(url, ASPSESSION_PATTERN);

		return url;
	}

}
