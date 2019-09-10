package com.yuanchun.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
//文件上传工具类服务方法

    public static void uploadFile(byte[] file, String filePath, String fileName) throws IOException {
        System.out.println("filePath================="+filePath);
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        System.out.println("getAbsolutePath================="+targetFile.getAbsolutePath());

//        FileOutputStream out = new FileOutputStream(filePath+fileName);
//        out.write(file);
//        out.flush();
//        out.close();
    }

    public static String minScale(int a, int b) {
        String scale = "";
        int tmp = a;
        if(a > b) {
            tmp = b;
        }
        for(int i = tmp; i > 0; i--) {
            if(a % i == 0 && b % i ==0) {
                scale = (a / i) + ":" + (b / i);
                break;
            }
        }
        return scale;
    }

    public static void main(String[] args) {
//        int a = 16 ;
//        int b = 9;
//        int c = 1600/900;
        int a = 3 ;
        int b = 2;
        int c = 900/600;
        System.out.println(a/b);
        System.out.println(c);
        System.out.println(minScale(900,600));
        System.out.println(minScale(1600,900));


    }


}