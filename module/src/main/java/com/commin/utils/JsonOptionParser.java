package com.commin.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonOptionParser {
    private JSONObject jsonOption = null;

    public JsonOptionParser(String filePath) throws FileNotFoundException, IOException, ParseException {
        // String jsonOptionStr = "{\r\n"
        // + " \"options\": [{\r\n"
        // + " \"store\": \"AMAZON\",\r\n"
        // + " \"serviceType\": null,\r\n"
        // + " \"titleLineNum\": 1,\r\n"
        // + " \"mappings\": [{\r\n"
        // + " \"variableName\": \"isrcCode\",\r\n"
        // + " \"csvTitle\": \"isrc\"\r\n"
        // + " },\r\n"
        // + " {\r\n"
        // + " \"variableName\": \"barcode\",\r\n"
        // + " \"csvTitle\": \"digital album upc\"\r\n"
        // + " },\r\n"
        // + " {\r\n"
        // + " \"variableName\": \"artistName\",\r\n"
        // + " \"csvTitle\": \"artist name\"\r\n"
        // + " },\r\n"
        // + " {\r\n"
        // + " \"variableName\": \"albumName\",\r\n"
        // + " \"csvTitle\": \"album name\"\r\n"
        // + " },\r\n"
        // + " {\r\n"
        // + " \"variableName\": \"trackName\",\r\n"
        // + " \"csvTitle\": \"track name\"\r\n"
        // + " },\r\n"
        // + " {\r\n"
        // + " \"variableName\": \"labelName\",\r\n"
        // + " \"csvTitle\": \"label name\"\r\n"
        // + " },\r\n"
        // + " {\r\n"
        // + " \"variableName\": \"salesYearMonth\",\r\n"
        // + " \"csvTitle\": \"dataset date\"\r\n"
        // + " },\r\n"
        // + " {\r\n"
        // + " \"variableName\": \"salesCountry\",\r\n"
        // + " \"csvTitle\": \"territory code\"\r\n"
        // + " },\r\n"
        // + " {\r\n"
        // + " \"variableName\": \"quantity\",\r\n"
        // + " \"csvTitle\": \"total plays\"\r\n"
        // + " },\r\n"
        // + " {\r\n"
        // + " \"variableName\": \"appliedExchangeRate\",\r\n"
        // + " \"csvTitle\": \"effective royalties in ratecard currency\"\r\n"
        // + " },\r\n"
        // + " {\r\n"
        // + " \"variableName\": \"appliedCurrency\",\r\n"
        // + " \"csvTitle\": \"ratecard currency\"\r\n"
        // + " }\r\n"
        // + " ]\r\n"
        // + " }, {\r\n"
        // + " \"store\": \"APPLE\",\r\n"
        // + " \"serviceType\": \"ITUNES\",\r\n"
        // + " \"titleLineNum\": 5,\r\n"
        // + " \"subOptions\": [\r\n"
        // + " \"\", \"\"\r\n"
        // + " ]\r\n"
        // + " }, {\r\n"
        // + " \"store\": \"APPLE\",\r\n"
        // + " \"serviceType\": \"APPLEMUSIC\",\r\n"
        // + " \"titleLineNum\": 5,\r\n"
        // + " \"subOptions\": [\r\n"
        // + " \"\", \"\"\r\n"
        // + " ]\r\n"
        // + " }]\r\n"
        // + "}";

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(filePath));
        this.jsonOption = new JSONObject(obj.toString());
    }

    public Map<String, JSONObject> getJSONOption() {
        JSONArray optionArray = jsonOption.getJSONArray("options");
        Map<String, JSONObject> stores = new HashMap<>();
        for (int i = 0; i < optionArray.length(); i++) {
            String storeName = optionArray.getJSONObject(i).getString("store");

            String serviceType = optionArray.getJSONObject(i).has("serviceType")
                    ? (optionArray.getJSONObject(i).isNull("serviceType")
                            ? null
                            : optionArray.getJSONObject(i).optString("serviceType"))
                    : null;
            if (serviceType != null && !serviceType.equals("")) {
                storeName += "_" + serviceType;
            }

            stores.put(storeName, optionArray.getJSONObject(i));
        }

        return stores;
    }
}
