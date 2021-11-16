package com.example.utils;

import java.util.ResourceBundle;

public class PropertiesUtil {

    private static ResourceBundle bundle;//传入的Locale -> 系统语言的Locale -> Default -> MissingResourceException
    /**
     * 读取配置文件
     * @param fileName
     */
    public static void readProperties(String fileName){

        bundle= ResourceBundle.getBundle(fileName);

    }

    /**
     * 根据key读取对应的value
     * @param key
     * @return
     */
    public static String getProperty(String config,String key){
        if(bundle==null){
            readProperties(config);
        }
        return bundle.getString(key);
    }
}