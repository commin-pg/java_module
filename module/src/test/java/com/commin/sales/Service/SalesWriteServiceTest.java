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

        // String[] dirs = { "amazon", "apple_applemusic" };
        String[] dirs = { "amazon" };
        String[] amazon_file_paths = {
                ".\\src\\main\\resources\\202108_Sales_Data\\overseas\\amazon\\ZQPSC_Monthly_ADS_Usage_202108_EU.txt",
                ".\\src\\main\\resources\\202108_Sales_Data\\overseas\\amazon\\ZQPSC_Monthly_ADS_Usage_202108_JP.txt",
                ".\\src\\main\\resources\\202108_Sales_Data\\overseas\\amazon\\ZQPSC_Monthly_ADS_Usage_202108_ROW_FE.txt",
                ".\\src\\main\\resources\\202108_Sales_Data\\overseas\\amazon\\ZQPSC_Monthly_ADS_Usage_202108_ROW_NA.txt"
        };
        String[] aplle_applemusic_file_paths = {
                ".\\src\\main\\resources\\202108_Sales_Data\\overseas\\apple\\applemusic\\S1_90638285_1021_AE.txt",
                ".\\src\\main\\resources\\202108_Sales_Data\\overseas\\apple\\applemusic\\S1_90638285_1021_AU.txt",
                ".\\src\\main\\resources\\202108_Sales_Data\\overseas\\apple\\applemusic\\S1_90638285_1021_BG.txt",
        };
        List<SalesCommonDTO> resultDTOList = new ArrayList<>();

        for (String dir : dirs) {
            String[] filePaths = null;
            if (dir.equals("amazon")) {
                filePaths = amazon_file_paths;
            } else {
                filePaths = aplle_applemusic_file_paths;
            }

            JSONObject store = stores.get(dir.toUpperCase());

            String storeName = store.optString("store");// 필수 옵션
            int titleLineNum = store.getInt("titleLineNum");
            String serviceType = store.optString("serviceType");
            JSONArray mappingArray = store.getJSONArray("mappings");// 필수 옵션

            for (String filePath : filePaths) {
                try {
                    File file = new File(filePath);
                    if (file.exists()) {
                        // 1. CSV 파일 하나씩 열어서 파싱

                        resultDTOList.addAll(SalesDTOWrapper.wrap(new SalesCSVParser().parse(
                                file,
                                storeName,
                                titleLineNum),
                                storeName, serviceType, mappingArray));

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

    }
}
