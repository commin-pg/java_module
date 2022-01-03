package com.commin.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class CommandParserTest {

    @Test
    public void joinCommand() {
        Map<String, String> argMap = new HashMap<>();
        argMap.put("-key1", "value1");
        argMap.put("-key2", "value2");
        try {
            String result = CommandParser.joinCommand("cmd", argMap);
            assertEquals("cmd -key1 value1 -key2 value2", result);
        } catch (Exception e) {
            fail();
        }
    }

}
