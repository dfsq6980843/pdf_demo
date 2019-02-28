package com.fengx.utils;

import java.io.*;

/**
 * 解析json
 */
public class JsonFileUtil {

    /**
     * 读取json文件
     * @param path
     * @return
     */
    public static String readJsonFile(String path) {
        StringBuffer laststr = new StringBuffer();
        // 打开文件
        File file = new File(path);
        BufferedReader reader = null;
        try {
            FileInputStream in = new FileInputStream(file);
            // 读取文件
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr = laststr.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException el) {
                }
            }
        }
        return laststr.toString();
    }
}
