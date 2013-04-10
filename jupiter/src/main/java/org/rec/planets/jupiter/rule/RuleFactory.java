package org.rec.planets.jupiter.rule;


/**
 * 抓取规则工厂
 * @author rec
 *
 */
public interface RuleFactory {
	Rule build(String... resources) throws Exception;
}
