package com.cgm.pagesplit.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * 文本类型的文件工具（txt/json/js/java等）
 * @author cgm
 */
public class TextFileUtils {
    public static String read(File file){
        StringBuilder result = new StringBuilder();
        try{
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            //使用readLine方法，一次读一行
            while((s = br.readLine()) != null){
                result.append(System.lineSeparator()).append(s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
}
