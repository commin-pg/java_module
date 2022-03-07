package com.commin.sales.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.commin.sales.dto.SalesCommonDTO;
import com.commin.utils.JsonOptionParser;
import com.commin.utils.SalesCSVParser;
import com.commin.utils.SalesDTOWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

public class SalesWriteServiceTest {

    // 1. store 별 option 읽기

    // 2. zip 파일 tmp 폴더에 풀고 directory 파싱하기

    // 3. Tax Rate 파일 파싱하기

    // 4. 디렉토리 별 CSV 파일 읽고 SalesCommonDTO 객체에 넣기

    // 5. Sales 테이블 Delete (where 정산월) & Bulk Insert

    // 6. tmp 폴더에서 directory 삭제하기

    private JsonOptionParser parser;

    @Before
    public void before() {
        try {
            this.parser = new JsonOptionParser(
                    ".\\src\\main\\resources\\config\\input_sales_properties.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void writeTest() {

        Map<String, JSONObject> stores = parser.getJSONOption();

        // String[] dirs = { "amazon", "apple_applemusic", "spotify" ,"deezer"};
        String[] dirs = { "deezer", };
        String[] amazon_file_paths = {
                ".\\src\\main\\resources\\202108_Sales_Data\\overseas\\amazon\\ZQPSC_Monthly_ADS_Usage_202108_EU.txt",
                ".\\src\\main\\resources\\202108_Sales_Data\\overseas\\amazon\\ZQPSC_Monthly_ADS_Usage_202108_JP.txt",
                ".\\src\\main\\resources\\202108_Sales_Data\\overseas\\amazon\\ZQPSC_Monthly_ADS_Usage_202108_ROW_FE.txt",
                ".\\src\\main\\resources\\202108_Sales_Data\\overseas\\amazon\\ZQPSC_Monthly_ADS_Usage_202108_ROW_NA.txt"
        };
        String[] aplle_applemusic_file_paths = {
                ".\\src\\main\\resources\\202108_Sales_Data\\overseas\\apple\\APPLEMUSIC\\S1_90638285_1021_AE.txt",
                ".\\src\\main\\resources\\202108_Sales_Data\\overseas\\apple\\APPLEMUSIC\\S1_90638285_1021_AU.txt",
                ".\\src\\main\\resources\\202108_Sales_Data\\overseas\\apple\\APPLEMUSIC\\S1_90638285_1021_BG.txt",
        };
        String[] spotify_file_paths = {
                ".\\src\\main\\resources\\202108_Sales_Data\\overseas\\spotify\\spotify-track-for-test-202103_copy.txt",
        };

        String[] deezer_file_paths = {
                ".\\src\\main\\resources\\202108_Sales_Data\\overseas\\deezer\\Deezer_test_20210301_20210331.txt",
        };

        List<SalesCommonDTO> resultDTOList = new ArrayList<>();

        for (String dir : dirs) {
            String[] filePaths = null;
            if (dir.equals("amazon")) {
                filePaths = amazon_file_paths;
            } else if (dir.equals("spotify")) {
                filePaths = spotify_file_paths;
            } else if (dir.equals("deezer")) {
                filePaths = deezer_file_paths;
            } else {
                filePaths = aplle_applemusic_file_paths;
            }
            // SPOTIFY == spotify
            JSONObject store = stores.get(dir.toUpperCase());
            // 필수 옵션 //
            String storeName = store.optString("store");
            int titleLineNum = 0;

            try {
                titleLineNum = store.getInt("titleLineNum");
            } catch (Exception e) {
            }

            String serviceType = store.optString("serviceType");
            JSONArray mappingArray = store.getJSONArray("mappings");
            List<String> titles = new ArrayList<>();
            // 필수 옵션 끝 //

            // 선택 옵션
            int subTitleLineNum = -1;
            String subOptionType = null;
            JSONArray subMappingArray = null;
            JSONObject subMappingOption = null;

            try {
                JSONArray json_titles = store.getJSONArray("titles");
                if (json_titles != null) {
                    for (int i = 0; i < json_titles.length(); i++) {
                        titles.add(json_titles.get(i).toString());
                    }
                }
            } catch (Exception e) {

            }
            try {
                subMappingOption = store.getJSONObject("subMapping");
            } catch (Exception e) {
            }
            if (subMappingOption != null) {
                subTitleLineNum = subMappingOption.getInt("subTitleLineNum");
                subOptionType = subMappingOption.getString("type");
                subMappingArray = subMappingOption.getJSONArray("mappings");//
            }

            for (String filePath : filePaths) {
                try {
                    File file = new File(filePath);
                    if (file.exists()) {
                        // 1. CSV 파일 하나씩 열어서 파싱

                        resultDTOList.addAll(SalesDTOWrapper.wrap(
                                new SalesCSVParser().parse(file, storeName, titleLineNum, subOptionType,
                                        subTitleLineNum, titles),
                                storeName, serviceType,
                                mappingArray, subMappingArray));

                    } else {
                        System.err.println("File Not Found");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            System.out.println();

        }

        System.out.println();

    }
}
