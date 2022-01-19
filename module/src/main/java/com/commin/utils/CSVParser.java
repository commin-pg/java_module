
package com.commin.utils;

import java.util.Arrays;
import java.util.List;

/**
 * CSVParser
 */
public class CSVParser {

    public static List<String> separatorForTab(String text) {
        if (text == null || text.equals(""))
            return null;
        String[] strs = text.split("\t");
        return Arrays.asList(strs);
    }

}