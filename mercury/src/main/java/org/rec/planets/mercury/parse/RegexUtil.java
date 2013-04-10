package org.rec.planets.mercury.parse;

import java.util.ArrayList;
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

public abstract class RegexUtil {
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
	 * 检测一个字符串是否与给定正则匹配
	 * 
	 * @param string
	 * @param regex
	 * @return
	 */
	public static boolean test(String string, Regex regex) {
		Matcher matcher = getMatcher(string, regex);
		return regex.isStrict() ? matcher.matches() : matcher.find();
	}

	/**
	 * 检测一个字符串是否与给定正则表达式完全匹配
	 * 
	 * @param string
	 * @param regex
	 * @return
	 */
	public static boolean matches(String string, String regex) {
		return test(string, new Regex(regex, true));
	}

	/**
	 * 检测一个字符串是否与给定正则表达式部分匹配
	 * 
	 * @param string
	 * @param regex
	 * @return
	 */
	public static boolean find(String string, String regex) {
		return test(string, new Regex(regex, false));
	}

	/**
	 * 根据正则表达式从字符串中抽取第一批捕获组
	 * 
	 * @param string
	 * @param regex
	 * @return
	 */
	public static List<String> getFirstGroups(String string, Regex regex) {
		Matcher matcher = getMatcher(string, regex);

		boolean tag = false;

		tag = regex.isStrict() ? matcher.matches() : matcher.find();

		if (!tag)
			return null;

		int[] groups = regex.getGroups();
		List<String> result = new ArrayList<String>(groups.length);

		for (int i = 0; i < groups.length; i++) {
			result.add(matcher.group(groups[i]));
		}

		return result;
	}

	/**
	 * 根据正则表达式从字符串中抽取全部捕获组
	 * 
	 * @param string
	 * @param regex
	 * @return
	 */
	public static List<List<String>> getAllGroups(String string, Regex regex) {
		Matcher matcher = getMatcher(string, regex);
		List<List<String>> result = new LinkedList<List<String>>();
		MatchIterator matchIterator = matcher.findAll();
		MatchResult mr = null;
		int[] groups = regex.getGroups();
		List<String> groupResult = null;

		while (matchIterator.hasMore()) {
			mr = matchIterator.nextMatch();
			groupResult = new ArrayList<String>(groups.length);
			for (int i = 0; i < groups.length; i++) {
				groupResult.add(mr.group(groups[i]));
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
	public static String replaceAll(String string, Regex regex,
			String replacement) {
		Pattern pattern = getPattern(regex);
		Replacer replacer = new Replacer(pattern, replacement);
		return replacer.replace(string);
	}

	/**
	 * 替换所有符合正则的字符串
	 * 
	 * @param string
	 * @param regex
	 * @param replacement
	 * @return
	 */
	public static String replaceAll(String string, String regex,
			String replacement) {
		Pattern pattern = getPattern(new Regex(regex));
		Replacer replacer = new Replacer(pattern, replacement);
		return replacer.replace(string);
	}
}
