package com.yuanchun.controler;

import com.yuanchun.common.ApplicationProperties;
import com.yuanchun.util.DateTimeUtil;
import com.yuanchun.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

@Controller
public class FileUploadController {
    @Autowired
    private ApplicationProperties applicationProperties;
    // 访问路径为：http://ip:port/upload
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String upload() {
        return "fileupload";
    }




    // 访问路径为：http://ip:port/upload/batch
    @RequestMapping(value = "/upload/batch", method = RequestMethod.GET)
    public String batchUpload() {
        return "/mutifileupload";
    }

    /**
     * 文件上传具体实现方法（单文件上传）
     *
     * @param file
     * @return
     *
     * @author 单红宇(CSDN CATOOP)
     * @create 2017年3月11日
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
        /*String contentType = file.getContentType();   //图片文件类型
        String fileName = file.getOriginalFilename();  //图片名字
        //文件存放路径
        String filePath = "/nas/data/upload1/";

        if (!file.isEmpty()) {
            //调用文件处理类FileUtil，处理文件，将文件写入指定位置
            try {
                FileUtil.uploadFile(file.getBytes(), filePath, fileName);
            } catch (Exception e) {
                return "上传失败," + e.getMessage();
            }
            return "上传成功";
        } else {
            return "上传失败，因为文件是空的.";
        }*/
        System.out.println("type========"+request.getParameter("type"));
        try {
        if (file.getSize() > 0
                && file.getSize() <= 10 * 1024 * 1024 * 1024L) {
            if (!isImage(file.getInputStream())) {
                return "不是图片";
            }

            String imageSpec = getImageSpec(file.getInputStream());
            if ("null".equals(imageSpec)) {
                return "格式错误";
            }
            String imageSuffix = getImageSuffix(file.getOriginalFilename());
            String dynamicName = DateTimeUtil.getCurrentDay() + "/" + System.currentTimeMillis() + imageSpec + imageSuffix;
            String confPath = applicationProperties.getImageUploadPath();
            File targetFile = new File(confPath + dynamicName);
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdir();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    public static boolean isImage(InputStream file) {
        boolean isImage = false;
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
            isImage = (image == null || image.getWidth() <= 0 || image.getHeight() <= 0) ? false : true;
        } catch (IOException e) {
            e.printStackTrace();
            isImage = false;
        }
        return isImage;
    }

    public static boolean isNullStr(Object strin) {
        if(strin == null){
            return true;
        }
        String str = strin.toString();
        boolean flag = false;
        if ("null".equals(str) || "".equals(str) || "".equals(str.trim())) {
            flag = true;
        }
        return flag;
    }
    public static String getImageSuffix(String fileName) {
        String imageSuffix = ".";
        boolean b = fileName.contains(".");
        if (isNullStr(fileName) || !fileName.contains(".")) {
            imageSuffix = imageSuffix + "jpg";
        } else {
            imageSuffix = imageSuffix + fileName.split("\\.")[1];
        }
        return imageSuffix;
    }
    public static void copy(InputStream fis, FileOutputStream fos) throws IOException {
        // 读取和写入信息
            int len = 0;
            // 创建一个字节数组，当做缓冲区
            byte[] b = new byte[1024 * 5];
            while ((len = fis.read(b)) != -1) {
                fos.write(b, 0, len);
                fos.flush();
            }
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

    public static String getImageSpec(InputStream inputStream) {
        String imageSpec = "null";
        BufferedImage image = null;
        try {
            image = ImageIO.read(inputStream);
            int w = image.getWidth();
            int h = image.getHeight();
            String scale = minScale(w,h);
            switch(scale) {
                case "16:9":
                    imageSpec = "_H169_sc";
                    break;
                case "3:2":
                    imageSpec = "_H32_sc";
                    break;
                case "3:4":
                    imageSpec = "_V34_sc";
                    break;
                default:
                    imageSpec = "_TB_sc";
                    break;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return imageSpec;
    }
    /**
     * 多文件上传 主要是使用了MultipartHttpServletRequest和MultipartFile
     *
     * @param request
     * @return
     *
     * @author 单红宇(CSDN CATOOP)
     * @create 2017年3月11日
     */
    @RequestMapping(value = "/upload/batch", method = RequestMethod.POST)
    public @ResponseBody
    String batchUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    stream = null;
                    return "You failed to upload " + i + " => " + e.getMessage();
                }
            } else {
                return "You failed to upload " + i + " because the file was empty.";
            }
        }
        return "upload successful";
    }
}
