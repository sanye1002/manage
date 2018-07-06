package com.sanye.manage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-03-29 上午 10:11
 */
@Component
@Data
@ConfigurationProperties(prefix = "upload")
public class UploadConfig {
    private String path;

    public String getResultPath(String url){
        String ResultPath = this.path+ url.substring(9);
        System.out.println(ResultPath);
        return ResultPath;
    }

}
