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
        return new WechatClient("xxxxx", "xxxxx", "xxxxx", "xxxxx");
    }

}
