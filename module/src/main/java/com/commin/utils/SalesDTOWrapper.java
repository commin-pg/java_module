package com.commin.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.commin.sales.dto.SalesCommonDTO;

import org.json.JSONArray;

public class SalesDTOWrapper {

    public static List<SalesCommonDTO> wrap(Map<String, Object> map, String storeName, String serviceType,
            JSONArray mappingArray) throws Exception {

        List<String> titleList = (List<String>) map.get("titleList");
        Queue<List<String>> documentStatusQueue = (Queue<List<String>>) map.get("documentStatusQueue");
        Queue<List<String>> salesDataRowQueue = (Queue<List<String>>) map.get("salesDataRowQueue");
        List<SalesCommonDTO> resultList = new ArrayList<>();

        if (storeName != null && mappingArray != null && !mappingArray.isEmpty()) {
            while (!salesDataRowQueue.isEmpty()) {
                List<String> salesRowData = salesDataRowQueue.poll();
                // 공통인 data (정산요율, 서비스타입, 스토어이름 등등) 가져와서셋팅
                SalesCommonDTO sale = new SalesCommonDTO(storeName, serviceType);

                for (int i = 0; i < mappingArray.length(); i++) {
                    String dtoColumnName = mappingArray.getJSONObject(i).getString("variableName");
                    String csvTitle = mappingArray.getJSONObject(i).getString("csvTitle");
                    System.out.println(String.format("%s : %s ==> %s", dtoColumnName, csvTitle,
                            salesRowData.get(titleList.indexOf(csvTitle))));
                    sale.setVariable(dtoColumnName, salesRowData.get(titleList.indexOf(csvTitle)));
                }
                System.out.println();

                resultList.add(sale);

            }
            System.out.println();
        } else {
            // throw Exception
        }
        return resultList;
    }

}
