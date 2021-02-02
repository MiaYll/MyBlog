package me.wangcai.myblog.config;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class FileConfig {
    private static String PATH  = null;

    public static String getPath(){
        if(PATH == null){
            try {
                PATH = ResourceUtils.getFile("classpath:/static/") +  "/upload/";
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        File file = new File(PATH);
        if(!file.exists()){
            file.mkdirs();
            System.out.println(file.getAbsolutePath() + "不存在,已经创建");
        }
        return PATH;
    }


}
