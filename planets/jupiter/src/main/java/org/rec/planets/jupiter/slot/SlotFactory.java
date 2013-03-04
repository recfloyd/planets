package org.rec.planets.jupiter.slot;

import org.rec.planets.jupiter.rule.RuleVersionToDestroyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public class SlotFactory implements ApplicationEventPublisherAware {
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

}
