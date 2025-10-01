package org.example.files.comparators;

import java.util.Comparator;
import java.util.Map;

public class TagsAlphabetComparator implements Comparator<Map.Entry<String, Integer>> {

    @Override
    public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
        return e1.getKey().compareTo(e2.getKey());
    }
}

