package ru.terra.activitystore.util;

import java.util.Map;

public class RandomUtils {

	public static Object getMapKeyByValue(Map map, Object value) {
		for (Object key : map.keySet()) {
			if (map.get(key).equals(value))
				return key;
		}
		return null;
	}
}
