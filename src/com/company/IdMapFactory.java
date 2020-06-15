package com.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdMapFactory {

    public static <T> Map<Integer, T> createIdMap(int startId, List<T> objects) {
        Map<Integer, T> idMap = new HashMap<>();
        for (int i = 0; i < objects.size(); i++) {
            idMap.put(i+startId, objects.get(i));
        }

        return idMap;
    }

}
