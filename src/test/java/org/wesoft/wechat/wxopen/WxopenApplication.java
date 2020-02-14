package org.wesoft.wechat.wxopen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class WxopenApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxopenApplication.class, args);
    }

}
