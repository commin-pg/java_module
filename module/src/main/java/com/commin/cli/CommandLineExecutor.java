package com.commin.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * CommandLineExecutor
 */
public class CommandLineExecutor {

    public static boolean execute(String cmd, Map<String, List<String>>... args) {
        return true;
    }

    /**
     * cmd 명령어 실행
     *
     * @param cmd
     */
    public static boolean execute(String cmd) {
        Process process = null;
        Runtime runtime = Runtime.getRuntime();

        List<String> cmdList = new ArrayList<String>();

        // 운영체제 구분 (window, window 가 아니면 무조건 linux 로 판단)
        if (System.getProperty("os.name").indexOf("Windows") > -1) {
            cmdList.add("cmd");
            cmdList.add("/c");
        } else {
            cmdList.add("/bin/sh");
            cmdList.add("-c");
        }
        // 명령어 셋팅
        cmdList.add(cmd);
        String[] array = cmdList.toArray(new String[cmdList.size()]);

        try {

            // 명령어 실행
            process = runtime.exec(array);

            StringBuffer strMsg = new StringBuffer();
            ProcessOutputThread o = new ProcessOutputThread(process.getInputStream(), strMsg);
            o.start();

            StringBuffer errMsg = new StringBuffer();
            ProcessOutputThread oe = new ProcessOutputThread(process.getErrorStream(), errMsg);
            oe.start();

            // 프로세스의 수행이 끝날때까지 대기
            process.waitFor();

            // shell 실행이 정상 종료되었을 경우
            if (process.exitValue() == 0) {
                System.out.println("성공");
                System.out.println(o.getMsg());
                return true;
            } else {
                // shell 실행이 비정상 종료되었을 경우
                System.out.println("비정상 종료");
                System.out.println(oe.getMsg());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            process.destroy();
        }

        return false;
    }

}

class ProcessOutputThread extends Thread {

    private InputStream is;

    private StringBuffer msg;

    public ProcessOutputThread(InputStream is, StringBuffer msg) {
        this.is = is;
        this.msg = msg;
    }

    public String getMsg() {
        if (this.msg != null)
            return msg.toString();
        else
            return null;
    }

    public void run() {
        try {
            msg.append(getStreamString(is));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getStreamString(InputStream is) {
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new InputStreamReader(is, "EUC-KR"));
            StringBuffer out = new StringBuffer();
            String stdLine;
            while ((stdLine = reader.readLine()) != null) {
                String line = stdLine;
                if (line.trim() == "" || line.isEmpty())
                    continue;

                line = line.trim();
                if (!line.endsWith(System.getProperty("line.separator")))
                    line += System.getProperty("line.separator");
                out.append(line);
            }
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}