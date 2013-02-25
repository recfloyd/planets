package org.rec.planets.jupiter.rule;

import org.rec.planets.jupiter.bean.CrawlRule;

/**
 * 抓取规则工厂
 * @author rec
 *
 */
public interface CrawlRuleFactory {
	CrawlRule build(String... resources) throws Exception;
}
