package com.commin.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class SalesCSVParser {

    // common
    public Map<String, Object> parse(File f, String storeName, int titleLineNum)
            throws FileNotFoundException {

        FileReader fr = null;
        BufferedReader br = null;

        List<String> titleList = new ArrayList<>();
        Queue<List<String>> documentStatusQueue = new LinkedList<>();
        Queue<List<String>> salesDataRowQueue = new LinkedList<>();
        Map<String, Object> resultMap = new HashMap<>();

        int readLineNum = 0;
        try {
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line != null && !line.trim().isEmpty() && !line.trim().equals("")) {
                    List<String> parseResultList = CSVParser.separatorForTab(line.trim()); // Parsing된 Row 리스트 한줄 읽었음.
                    readLineNum++; // 한줄 읽었으니 읽은 라인 넘버 올리기
                    if (readLineNum == titleLineNum) { // titleLineNumber와 같으면 타이틀 리스트 가져오는 로직 수행
                        titleList = parseResultList;
                    } else if (readLineNum > titleLineNum) { // titleLineNumber보다 크면 데이터 row로 판단.
                        if (titleList.size() >= parseResultList.size()) {
                            salesDataRowQueue.add(parseResultList);
                        } else {
                            System.out.println("타이틀 컬럼 개수보다 데이터 커럼개수가 더 많음 ");
                            salesDataRowQueue.add(parseResultList.subList(0, titleList.size()));
                        }
                    } else {
                        // SubOption 인지 확인
                        if (parseResultList != null && !parseResultList.isEmpty()) {
                            documentStatusQueue.add(parseResultList);
                        }
                    }

                }
            }

            resultMap.put("titleList", titleList);
            resultMap.put("documentStatusQueue", documentStatusQueue);
            resultMap.put("salesDataRowQueue", salesDataRowQueue);
            return resultMap;

            // while (!salesDataRowQueue.isEmpty()) {
            // List<String> salesRowData = salesDataRowQueue.poll();
            // SalesCommonDTO saleData = SalesCommonDTO.builder().build();
            // for (String field : needsFieldNames) {
            // int fieldIdx = titleList.indexOf(field);
            // if (fieldIdx != -1) {
            // salesRowData.get(fieldIdx);
            // } else {

            // }
            // }

            // }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
