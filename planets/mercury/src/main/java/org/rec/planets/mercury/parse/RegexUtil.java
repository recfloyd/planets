package org.rec.planets.mercury.parse;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import jregex.MatchIterator;
import jregex.MatchResult;
import jregex.Matcher;
import jregex.Pattern;
import jregex.Replacer;

import org.rec.planets.mercury.parse.bean.Regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public final class RegexUtil {
	private RegexUtil() {
	}

	private static final Logger logger = LoggerFactory
			.getLogger(RegexUtil.class);
	private static LoadingCache<Regex, Pattern> patternCache = CacheBuilder
			.newBuilder().concurrencyLevel(16).initialCapacity(1)
			.maximumSize(512).softValues()
			.build(new CacheLoader<Regex, Pattern>() {
				@Override
				public Pattern load(Regex regex) throws Exception {
					return new Pattern(regex.getExpression(), regex.getFlag());
				}
			});

	private static Pattern getPattern(Regex regex) {
		Pattern pattern = null;
		try {
			pattern = patternCache.get(regex);
		} catch (ExecutionException e) {
			logger.error("error when getting pattern", e);
			return null;
		}
		return pattern;
	}

	private static Matcher getMatcher(String string, Regex regex) {
		return getPattern(regex).matcher(string);
	}

	/**
	 * 检测字符串与正则表达式是否全量匹配
	 * 
	 * @param string
	 * @param regex
	 * @return
	 */
	public static boolean matches(String string, Regex regex) {
		return getMatcher(string, regex).matches();
	}

	/**
	 * 检测字符串与正则表达式是否部分匹配
	 * 
	 * @param string
	 * @param regex
	 * @return
	 */
	public static boolean find(String string, Regex regex) {
		return getMatcher(string, regex).find();
	}

	/**
	 * 根据正则表达式从字符串中抽取字段
	 * 
	 * @param string
	 * @param regex
	 * @param strict
	 *            true为全量匹配,false为部分匹配
	 * @return
	 */
	public static String[] groupFirstMatch(String string, Regex regex,
			boolean strict) {
		Matcher matcher = getMatcher(string, regex);

		boolean tag = false;

		tag = strict ? matcher.matches() : matcher.find();

		if (!tag)
			return null;

		int[] groups = regex.getGroups();
		String[] result = new String[groups.length];

		for (int i = 0; i < groups.length; i++) {
			result[i] = matcher.group(groups[i]);
		}

		return result;
	}

	/**
	 * 根据正则表达式从字符串中抽取字段
	 * 
	 * @param string
	 * @param regex
	 * @return
	 */
	public static List<String[]> groupAllMatch(String string, Regex regex) {
		Matcher matcher = getMatcher(string, regex);
		List<String[]> result = new LinkedList<String[]>();
		MatchIterator matchIterator = matcher.findAll();
		MatchResult mr = null;
		int[] groups = regex.getGroups();
		String[] groupResult = null;

		while (matchIterator.hasMore()) {
			mr = matchIterator.nextMatch();
			groupResult = new String[groups.length];
			for (int i = 0; i < groups.length; i++) {
				groupResult[i] = mr.group(groups[i]);
			}
			result.add(groupResult);
		}

		return result;
	}

	/**
	 * 替换所有符合正则的字符串
	 * 
	 * @param string
	 * @param regex
	 * @param replacement
	 * @return
	 */
	public static String replaceAllMatch(String string, Regex regex,
			String replacement) {
		Pattern pattern = getPattern(regex);
		Replacer replacer = new Replacer(pattern, replacement);
		return replacer.replace(string);
	}
}
