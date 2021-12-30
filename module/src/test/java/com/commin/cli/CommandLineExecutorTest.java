package com.commin.cli;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CommandLineExecutorTest {

    /**
     * diagnostic test
     * 
     * @args
     * 
     */
    @Test
    public void diagnostic_test() {
        boolean result = CommandLineExecutor.execute("transporter.cmd -m diagnostic -v eXtreme");
        assertTrue(result);
    }

    /**
     * status test
     * 
     * 
     * @args
     * 
     */
    @Test
    public void status_test() {
        boolean result = CommandLineExecutor.execute(
                "transporter -m status -u danalmusic@danalenter.co.kr  -p ixso-spww-vant-omuc -vendor_id 8809838633884 -t Aspera");

        assertTrue(result);
    }
}
