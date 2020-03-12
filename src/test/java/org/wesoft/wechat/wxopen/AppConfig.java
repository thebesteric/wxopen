package org.wesoft.wechat.wxopen;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wesoft.wechat.wxopen.client.WechatClient;

@Configuration
public class AppConfig {

    @Bean
    @Qualifier("testWechatClient")
    public WechatClient testWechatClient() {
        // return new WechatClient("wx6dbc04ce2e617787", "10907e76e8268395804ecd33de83da74", "wesoft", null);
        return new WechatClient("wxca112d1fac8b27da", "1ac21b11b3ac1b6948c39c83b67420d1", "wesoft", "KOjUc5WiYIsNmyYwGGBqPMNjX7LsUrCoyWJpmeNu8EU");
        // return new WechatClient("wx4f972cafca121e21", "1c3f49776fbf1aaabceef2795ae5ddc8", "shuinfo", "wxGY8GMWrJtF3A3ACwWjTr4aiH7pRfp2H5wr3My5ccu");
    }

}
