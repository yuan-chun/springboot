package com.yuanchun.util;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.Properties;

public class PropertiesUtil {

    /**
     * 配置文件工具类
     *
     * @date: 2016-9-5
     * @author: sunliang
     * @title: loadProperties
     * @param url
     * @param propName
     * @param defaultValue
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public static final String loadProperties(String url, String propName, String defaultValue) {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(url));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String returnValue = prop.getProperty(propName, defaultValue);
        return returnValue;
    }

    /**
     * 返回classpath下指定文件名，指定key的值
     *
     * @date: 2016-9-5
     * @author: sunliang
     * @title: getProperty
     * @param fileName
     * @param key
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public static final String getProperty(String fileName, String key) {
        Properties prop = new Properties();
        InputStream in = PropertiesUtil.class.getResourceAsStream("/" + fileName);
        if (in != null) {
            try {
                prop.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String value = prop.getProperty(key);
        return value;
    }

    /**
     * 返回web容器下指定文件名，指定key的值
     *
     * @date: 2016-10-24
     * @author: sunliang
     * @title: getServletContextProperty
     * @param fileName
     * @param key
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public static final String getServletContextProperty(String fileName, String key) {
        Properties prop = new Properties();
        InputStream in = ServletContext.class.getResourceAsStream("/" + fileName);
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String value = prop.getProperty(key);
        return value;
    }

    /**
     * 返回web容器下指定文件名的Properties对象
     *
     * @date: 2016-10-24
     * @author: sunliang
     * @title: getServletContextProperties
     * @param fileName
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public static final Properties getServletContextProperties(String fileName) {
        Properties prop = new Properties();
        InputStream in = ServletContext.class.getResourceAsStream(fileName);
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    /**
     * 返回classpath下指定文件名的Properties对象
     *
     * @date: 2016-9-5
     * @author: sunliang
     * @title: getProperties
     * @param fileName
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public static final Properties getProperties(String fileName) {
        Properties prop = new Properties();
        InputStream in = PropertiesUtil.class.getResourceAsStream("/" + fileName);
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    /**
     * 以UTF-8的方式生成properties
     *
     * @date: 2017年12月14日
     * @author: yanchao_mci
     * @title: getPropertiesUniversal
     * @param fileName
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public static final Properties getPropertiesUniversal(String fileName) {
        Properties prop = new Properties();
        try {
            InputStream in = PropertiesUtil.class.getResourceAsStream("/" + fileName);
            prop.load(new InputStreamReader(in, "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    /**
     * 获取指定properties对象，指定key的值
     *
     * @date: 2016-9-5
     * @author: sunliang
     * @title: getValue
     * @param prop
     * @param key
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public static final String getValue(Properties prop, String key) {
        String value = "";
        try {
            value = prop.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

}
