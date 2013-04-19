package org.rec.planets.jupiter.context.accessor.writer;

import java.util.List;

import jregex.REFlags;

import org.rec.planets.jupiter.context.accessor.reader.ContextReader;
import org.rec.planets.mercury.expression.MvelUtil;
import org.rec.planets.mercury.parse.RegexUtil;
import org.rec.planets.mercury.parse.bean.Regex;

import com.google.common.base.Strings;

public class ThreadLocalReaderWriter implements ContextReader, ContextWriter {
	private static final Regex THREAD_LOCAL_KEY_PATTERN = new Regex(
			"@\\{(\\S+)\\}@(.*)", new int[] { 1, 2 }, REFlags.DEFAULT, false);

	private ContextWriter nestedWriter;

	@Override
	public Object read(Object context, String key) {
		List<String> split = RegexUtil.getFirstGroups(key,
				THREAD_LOCAL_KEY_PATTERN);
		Object source = ThreadLocalObjectHolder.getObject(split.get(0));
		if (source == null)
			return null;
		else {
			String newKey = split.get(1);
			if (Strings.isNullOrEmpty(newKey))
				return source;
			else
				return MvelUtil.evalFromObject(source, newKey);
		}
	}

	@Override
	public void write(Object context, String key, Object result) {
		List<String> split = RegexUtil.getFirstGroups(key,
				THREAD_LOCAL_KEY_PATTERN);
		String newKey = split.get(1);
		if (Strings.isNullOrEmpty(newKey))
			ThreadLocalObjectHolder.putObject(split.get(0), result);
		else {
			nestedWriter.write(ThreadLocalObjectHolder.getObject(split.get(0)),
					newKey, result);
		}
	}

	@Override
	public Object remove(Object context, String key) {
		List<String> split = RegexUtil.getFirstGroups(key,
				THREAD_LOCAL_KEY_PATTERN);
		String newKey = split.get(1);
		if (Strings.isNullOrEmpty(newKey))
			return ThreadLocalObjectHolder.removeObject(split.get(0));
		else {
			return nestedWriter.remove(
					ThreadLocalObjectHolder.getObject(split.get(0)), newKey);
		}
	}

	public void setNestedWriter(ContextWriter nestedWriter) {
		this.nestedWriter = nestedWriter;
	}
}
