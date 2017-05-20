package fr.tikione.ini.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Utility class for <code>Map</code> manipulations.
 */
public class MapHelper {

    /** Suppresses default constructor, ensuring non-instantiability. */
    private MapHelper() {
    }

    /**
     * Add all the content of the second map to the first one, with a deep copy of the entries. This is like
     * the <code>Map.addAll</code> method, with a deep copy of the entries objects instead of a simple
     * copy of their reference.
     *
     * @param copy the destination map to copy the <code>original</code> map's content.
     * @param original the map to copy content.
     */
    public static void deepCopyMapBooleans(Map<String, Boolean> copy, Map<String, Boolean> original) {
        for (Entry<String, Boolean> entry : original.entrySet()) {
            copy.put(entry.getKey(), entry.getValue().booleanValue());
        }
    }

    /**
     * Add all the content of the second map to the first one, with a deep copy of the entries. This is like
     * the <code>Map.addAll</code> method, with a deep copy of the entries objects instead of a simple
     * copy of their reference.
     *
     * @param copy the destination map to copy the <code>original</code> map's content.
     * @param original the map to copy content.
     */
    public static void deepCopyMapIntegers(Map<String, Integer> copy, Map<String, Integer> original) {
        for (Entry<String, Integer> entry : original.entrySet()) {
            copy.put(entry.getKey(), entry.getValue().intValue());
        }
    }

    /**
     * Add all the content of the second map to the first one, with a deep copy of the entries. This is like
     * the <code>Map.addAll</code> method, with a deep copy of the entries objects instead of a simple
     * copy of their reference.
     *
     * @param copy the destination map to copy the <code>original</code> map's content.
     * @param original the map to copy content.
     */
    public static void deepCopyMapFloats(Map<String, Float> copy, Map<String, Float> original) {
        for (Entry<String, Float> entry : original.entrySet()) {
            copy.put(entry.getKey(), entry.getValue().floatValue());
        }
    }

    /**
     * Add all the content of the second map to the first one, with a deep copy of the entries. This is like
     * the <code>Map.addAll</code> method, with a deep copy of the entries objects instead of a simple
     * copy of their reference.
     *
     * @param copy the destination map to copy the <code>original</code> map's content.
     * @param original the map to copy content.
     */
    public static void deepCopyMapStringTables(Map<String, String[]> copy, Map<String, String[]> original) {
        for (Entry<String, String[]> entry : original.entrySet()) {
            copy.put(entry.getKey(), entry.getValue().clone());
        }
    }

    /**
     * Add all the content of the second map to the first one, with a deep copy of the entries. This is like
     * the <code>Map.addAll</code> method, with a deep copy of the entries objects instead of a simple
     * copy of their reference.
     *
     * @param copy the destination map to copy the <code>original</code> map's content.
     * @param original the map to copy content.
     */
    public static void deepCopyMapPatterns(Map<String, Pattern> copy, Map<String, Pattern> original) {
        for (Entry<String, Pattern> entry : original.entrySet()) {
            copy.put(entry.getKey(), Pattern.compile(entry.getValue().pattern()));
        }
    }

    /**
     * Indicate if two <code>Map&lt;String, String[]&gt;</code> are equivalent.
     *
     * @param map1 the first map.
     * @param map2 the second map.
     * @return <code>true</code> if the two maps have the same content, regardless of their keys order. Otherwise <code>false</code>.
     */
    public static boolean mapsStringTablesEqual(Map<String, String[]> map1, Map<String, String[]> map2) {
        boolean equals = true;
        if (map1.keySet().equals(map2.keySet())) {
            Set<Entry<String, String[]>> set1 = map1.entrySet();
            CHECK_ENTRIES:
            for (Entry<String, String[]> entry1 : set1) {
                String[] table1 = entry1.getValue();
                String[] table2 = map2.get(entry1.getKey());
                if (table1.length == table2.length) {
                    for (int nStr = 0; nStr < table1.length; nStr++) {
                        if (!table1[nStr].equals(table2[nStr])) {
                            equals = false;
                            break CHECK_ENTRIES;
                        }
                    }
                } else {
                    equals = false;
                    break CHECK_ENTRIES;
                }
            }
        } else {
            equals = false;
        }
        return equals;
    }
}
