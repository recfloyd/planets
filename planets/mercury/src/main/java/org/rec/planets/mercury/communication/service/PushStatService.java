package org.rec.planets.mercury.communication.service;

import java.util.Map;

import org.rec.planets.mercury.communication.bean.pack.StatPack;

/**
 * slave --> master 推送统计信息
 * 
 * @author rec
 * 
 */
public interface PushStatService {
	Map<String, Object> push(StatPack statPack);
}
