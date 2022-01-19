package com.commin.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;

public class CSVParserTest {
    private SalesCSVParser salesCSVParser = null;

    @Before
    public void init() {
        salesCSVParser = new SalesCSVParser();
    }

    // amazone
    @Test
    public void parserTestForAmazone() {
        salesCSVParser.parserCSVByTabForAmazone();
        assertTrue(true);
    }

    // apple - applemusic

    @Test
    public void parserTestForAppleMusic() {
        salesCSVParser.parserCSVByTabForAppleMusic();
        assertTrue(true);
    }

    // apple - itunes
    @Test
    public void parserTestForItunes() {
        salesCSVParser.parserCSVByTabForItunes();
        assertTrue(true);
    }

    // deezer
    @Test
    public void parserTestForDeezer() {
        salesCSVParser.parserCSVByTabForDeezer();
        assertTrue(true);
    }

    // spotify
    @Test
    public void parserTestForSpotify() {
        salesCSVParser.parserCSVByTabForSpotify();
        assertTrue(true);
    }

    // kkbox, qqmusic > excel

    @Test
    public void separatorForTab() {
        List<String> result = CSVParser.separatorForTab("text1\ttext2");

        result.forEach(item -> {
            System.out.println(item);
        });

        assertEquals(result.size(), 2);
    }

    /**
     * * Note
     * 1. TitleLineNum이 필요할듯
     */
    @Test
    public void separetorTest() {
        File f = new File("./src/test/java/com/commin/utils/assets/test_apple.txt");
        FileReader fr = null;
        BufferedReader br = null;

        List<String> titleList = new ArrayList<>();
        Queue<List<String>> documentStatusQueue = new LinkedList<>();
        Queue<List<String>> salesDataRowQueue = new LinkedList<>();

        int titleLineNum = 1;
        int readLineNum = 0;
        try {
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line != null && !line.trim().isEmpty() && !line.trim().equals("")) {
                    List<String> parseResultList = CSVParser.separatorForTab(line.trim()); // Parsing된 Row 리스트 한줄 읽었음.
                    readLineNum++; // 한줄 읽었으니 읽은 라인 넘버 올리기
                    if (readLineNum < titleLineNum) {
                        // titleLineNum 보다 작으면 아직 타이틀을 시작한게 아니니까 documentStatusList 넣기
                        documentStatusQueue.add(parseResultList);
                    }
                    if (readLineNum == titleLineNum) { // titleLineNumber와 같으면 타이틀 리스트 가져오는 로직 수행
                        titleList = parseResultList;
                    } else if(readLineNum > titleLineNum){ // titleLineNumber보다 크면 데이터 row로 판단.
                        salesDataRowQueue.add(parseResultList);
                    }else{
                        // SubOption 
                        // SubOption 
                    }

                }
            }

            System.out.println(String.join("	", titleList));
            while (!salesDataRowQueue.isEmpty()) {
                List<String> salesRowData = salesDataRowQueue.poll();
                System.out.println(String.join("	", salesRowData));

            }
            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void separatorForTabReadFileTest() {

        String dirName = System.getProperty("user.dir");
        System.out.println(dirName);
        File f = new File("./src/test/java/com/commin/utils/assets/test.txt");
        FileReader fr = null;
        BufferedReader br = null;
        Map<String, List<String>> resultMap = new HashMap<String, List<String>>();
        List<String> titleList = new ArrayList<>();

        Date startDate = new Date();
        int lineNum = 0;
        int wrongLineNum = 0;
        try {
            if (f.exists()) {
                fr = new FileReader(f);
                br = new BufferedReader(fr);
                String line = "";

                while ((line = br.readLine()) != null) {

                    List<String> result = CSVParser.separatorForTab(line.trim());
                    if (line != null && !line.trim().isEmpty() && !line.trim().equals("")) {
                        lineNum++;
                        if (lineNum == 1) {
                            for (int idx = 0; idx < result.size(); idx++) {
                                resultMap.put(result.get(idx), new ArrayList<String>());
                                titleList = result;
                            }
                        } else {
                            if (resultMap.size() >= result.size()) {
                                for (int idx = 0; idx < titleList.size(); idx++) {
                                    resultMap.get(titleList.get(idx)).add(result.get(idx));
                                }
                            } else {
                                System.out.println("데이터의 개수가 제목의 개수보다 많음");
                                wrongLineNum++;
                            }
                        }
                    }
                }
            } else {
                System.out.println("File Not Found");
            }
            System.out.println("-----------------------------------------------------------------------------");
            for (String key : resultMap.keySet()) {
                String columnName = key;
                int rowNum = resultMap.get(key).size();
                System.out.println(String.format("[ColumnName] : %s , [rowNum] : %d ", columnName, rowNum));
            }

            System.out.println("-----------------------------------------------------------------------------");

            System.out.println(
                    "Wrong Line Num : " + wrongLineNum + " Line Num : " + lineNum + " 실행 시간 "
                            + String.valueOf(new Date().getTime() - startDate.getTime()));
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }

    }

    @Test
    public void separatorTestBasic() {
        List<String> titles = CSVParser.separatorForTab(
                "Start Date	End Date	UPC	ISRC/ISBN	Vendor Identifier	Quantity	Partner Share	Extended Partner Share	Partner Share Currency	Sales or Return	Apple Identifier	Artist/Show/Developer/Author	Title	Label/Studio/Network/Developer/Publisher	Grid	Product Type Identifier	ISAN/Other Identifier	Country Of Sale	Pre-order Flag	Promo Code	Customer Price	Customer Currency");
        System.out.println(String.join(",", titles));
        List<String> result = CSVParser.separatorForTab(
                "08/02/2021	08/02/2021		KRB361210183	KRB361210183	1			AUD		506147465	Groove Edition	행복한 나를 (Me Happy) [In the Style of Eco]	Musicen & Pison Contents		H3		AU				");
        System.out.println(String.join(",", result));
    }

    @Test
    public void separatorForTabReadFileTestApple() {

        String dirName = System.getProperty("user.dir");
        System.out.println(dirName);
        File f = new File("./src/test/java/com/commin/utils/assets/test_apple.txt");
        FileReader fr = null;
        BufferedReader br = null;
        Map<String, List<String>> resultMap = new HashMap<String, List<String>>();
        List<String> titleList = new ArrayList<>();

        Date startDate = new Date();
        int lineNum = 0;
        int wrongLineNum = 0;
        try {
            if (f.exists()) {
                fr = new FileReader(f);
                br = new BufferedReader(fr);
                String line = "";

                while ((line = br.readLine()) != null) {

                    List<String> result = CSVParser.separatorForTab(line.trim());
                    if (line != null && !line.trim().isEmpty() && !line.trim().equals("")) {
                        lineNum++;
                        if (lineNum == 1) {
                            for (int idx = 0; idx < result.size(); idx++) {
                                resultMap.put(result.get(idx), new ArrayList<String>());
                                titleList = result;
                            }
                        } else {
                            if (resultMap.size() == result.size()) {
                                for (int idx = 0; idx < titleList.size(); idx++) {
                                    resultMap.get(titleList.get(idx)).add(result.get(idx));
                                }
                            } else {
                                System.out.println("컬럼 개수가 맞지 않음");
                                wrongLineNum++;
                            }
                        }
                    }
                }
            } else {
                System.out.println("File Not Found");
            }
            System.out.println("-----------------------------------------------------------------------------");
            for (String key : resultMap.keySet()) {
                String columnName = key;
                int rowNum = resultMap.get(key).size();
                System.out.println(String.format("[ColumnName] : %s , [rowNum] : %d ", columnName, rowNum));
            }

            System.out.println("-----------------------------------------------------------------------------");

            System.out.println(
                    "Wrong Line Num : " + wrongLineNum + " Line Num : " + lineNum + " 실행 시간 "
                            + String.valueOf(new Date().getTime() - startDate.getTime()));
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }

    }
}
