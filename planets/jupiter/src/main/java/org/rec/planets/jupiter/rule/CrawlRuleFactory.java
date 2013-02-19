package org.rec.planets.jupiter.rule;

import org.rec.planets.jupiter.bean.CrawlRule;

public interface CrawlRuleFactory {
	CrawlRule build(String... resources) throws Exception;
}
