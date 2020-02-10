package com.cgm.pagesplit.util;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author cgm
 */
public class JsonUtils {
    public static String readJsonFile(String filePath) {
        String jsonStr = "";
        if (!filePath.endsWith(".json")) {
            filePath = filePath + ".json";
        }

        try {
            File jsonFile = new File(filePath);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8);
            int ch;
            StringBuilder sb = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeJsonFile(String filePath, String jsonStr) {
        if (!filePath.endsWith(".json")) {
            filePath = filePath + ".json";
        }

        File jsonFile = new File(filePath);
        try {
            FileWriter writer = new FileWriter(jsonFile);
            writer.write(jsonStr);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
