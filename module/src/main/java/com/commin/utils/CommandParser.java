package com.commin.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * CommandParser
 */
public class CommandParser {

    public static String joinCommand(String cmd, Map<String, String> argMap) throws Exception {
        if (cmd == null)
            throw new NullPointerException();

        List<String> command_result_arr = argMap.keySet().stream().map(key -> {
            return String.format("%s %s", key, argMap.get(key));
        }).collect(Collectors.toList());
        command_result_arr.add(0, cmd);

        return String.join(" ", command_result_arr);
    }
}