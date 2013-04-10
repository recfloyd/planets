package org.rec.planets.jupiter.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.rec.planets.jupiter.rule.Rule;
import org.rec.planets.jupiter.rule.RuleFactory;
import org.rec.planets.jupiter.rule.RuleVersionToDestroyEvent;
import org.rec.planets.mercury.communication.bean.snapshot.CurrentJobSnapshot;
import org.rec.planets.mercury.communication.bean.snapshot.JobResultSnapshot;
import org.rec.planets.mercury.communication.bean.snapshot.WebsiteSnapshot;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public class DefaultSlotFactory implements SlotFactory,
		ApplicationEventPublisherAware, InitializingBean {
	class RuleVersionHook {
		void destroyRuleVersion(Short websiteId, Long version) {
			applicationEventPublisher
					.publishEvent(new RuleVersionToDestroyEvent(websiteId,
							version));
		}
	}

	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public void setApplicationEventPublisher(
			ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	/**
	 * 任务槽采用线程安全的map存储 代码中的不需要过多考虑线程安全性,
	 */
	private ConcurrentMap<Short, Slot> slots;

	private RuleFactory ruleFactory;

	private RuleVersionHook hook;

	@Override
	public Slot getSlot(Short websiteId, String[] resources) throws Exception {
		if (resources == null || resources.length == 0) {
			Slot slot = slots.get(websiteId);
			if (slot == null) {
				throw new RuntimeException("cannot found slot for website "
						+ websiteId + " and no resource supplied.");
			}
			return slot;
		} else {
			updateRule(websiteId, resources);
			return slots.get(websiteId);
		}
	}

	@Override
	public void updateRule(Short websiteId, String[] resources)
			throws Exception {
		if (websiteId == null || resources == null || resources.length == 0) {
			throw new RuntimeException("can not update rule for web "
					+ websiteId);
		}

		Rule rule = ruleFactory.build(resources);

		Slot slot = slots.get(websiteId);

		if (slot == null) {
			slots.putIfAbsent(websiteId, new DefaultSlot(rule, hook));
		} else {
			Long currentVersion = slot.getRuleVersion();
			if (rule.getVersion() > currentVersion) {// 如果新资源的版本号更高,就更新任务槽的规则
				slot.setRule(rule);
			}
		}
	}

	@Override
	public Map<Short, Long> getRuleVersions() {
		Map<Short, Long> result = new HashMap<Short, Long>(slots.size());
		for (Map.Entry<Short, Slot> entry : slots.entrySet()) {
			result.put(entry.getKey(), entry.getValue().getRuleVersion());
		}
		return result;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		slots = new ConcurrentHashMap<Short, Slot>();
	}

	public void setRuleFactory(RuleFactory ruleFactory) {
		this.ruleFactory = ruleFactory;
		hook = new RuleVersionHook();
	}

	@Override
	public List<JobResultSnapshot> getJobResultSnapshots() {
		List<JobResultSnapshot> result = new ArrayList<JobResultSnapshot>();
		for (Slot slot : slots.values()) {
			result.addAll(slot.getJobResultSnapshots());
		}
		return result;
	}

	@Override
	public List<CurrentJobSnapshot> getCurrentJobSnapshots() {
		List<CurrentJobSnapshot> result = new ArrayList<CurrentJobSnapshot>();
		for (Slot slot : slots.values()) {
			result.addAll(slot.getCurrentJobSnapshot());
		}
		return result;
	}

	@Override
	public List<WebsiteSnapshot> getWebsiteSnapshots() {
		List<WebsiteSnapshot> result = new ArrayList<WebsiteSnapshot>();
		for (Slot slot : slots.values()) {
			result.add(slot.getWebsiteSnapshot());
		}
		return result;
	}

}
