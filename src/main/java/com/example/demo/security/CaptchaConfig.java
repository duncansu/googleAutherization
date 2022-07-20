package com.example.demo.security;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class CaptchaConfig {
    @Bean
    public DefaultKaptcha getDefaultCaptcha() {
        //驗證碼生成器
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        //配置
        Properties properties = new Properties();
        //是否有邊框
        properties.setProperty("kaptcha.border", "yes");
        //設定邊框顏色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        //驗證碼
        properties.setProperty("kaptcha.session.key", "code");
        //驗證碼文本字符顏色 默認為黑色
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        //設定字體樣式
        properties.setProperty("kaptcha.textproducer.font.names", "楷體");
        //字體大小 默認40
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        //驗證碼文本字符內容范圍 默認為abced23456789gfynmnpwx
        properties.setProperty("kaptcha.textproducer.char.string", "");
        //字符長度 默認為5
        properties.setProperty("kaptcha.textproducer.char.length", "5");
        //字符間距 默認為2
        properties.setProperty("kaptcha.textproducer.char.space", "4");
        //驗證碼圖片寬度 默認為200
        properties.setProperty("kaptcha.image.width", "150");
        //驗證碼圖片高度 默認為40
        properties.setProperty("kaptcha.image.height", "40");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
