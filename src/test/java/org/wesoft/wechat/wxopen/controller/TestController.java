package org.wesoft.wechat.wxopen.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wesoft.wechat.wxopen.client.WechatClient;
import org.wesoft.wechat.wxopen.domain.Button;
import org.wesoft.wechat.wxopen.exception.InvalidSignatureException;
import org.wesoft.wechat.wxopen.exception.NullParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/test")
public class TestController {

    @Qualifier("testWechatClient")
    @Autowired
    private WechatClient testWechatClient;

    @Autowired
    private MyWechatEventListener myWechatEventListener;

    @GetMapping(value = "/")
    public Object test() {
        return "test";
    }

    @RequestMapping(value = "/callback", method = {RequestMethod.GET, RequestMethod.POST})
    public Object callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        // 微信校验
        if (testWechatClient.getWechatHelper().checkSignatures(signature, timestamp, nonce)) {
            // 请求验证
            if ("GET".equalsIgnoreCase(request.getMethod())) {
                return request.getParameter("echostr");
            }
            // 事件处理
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            return myWechatEventListener.listen(request, testWechatClient.getWechatHelper());
        }
        return new InvalidSignatureException();
    }

    @GetMapping(value = "/menu/create")
    public Object menuCreate() throws NullParameterException {
        Button button = new Button();

        Button.Menu menu = new Button.Menu();
        menu.setName("拍照相册");
        menu.setType(Button.Menu.TYPE_PIC_PHOTO_OR_ALBUM);
        menu.setKey("TYPE_PIC_PHOTO_OR_ALBUM");
        button.setMenu(menu);

        Button.Menu menu1 = new Button.Menu();
        menu1.setName("弹出系统");
        menu1.setType(Button.Menu.TYPE_PIC_SYS_PHOTO);
        menu1.setKey("TYPE_PIC_SYS_PHOTO");
        button.setMenu(menu1);

        Button.Menu menu2 = new Button.Menu();
        menu2.setName("地理位置");
        menu2.setType(Button.Menu.TYPE_LOCATION_SELECT);
        menu2.setKey("TYPE_LOCATION_SELECT");
        button.setMenu(menu2);

        return testWechatClient.getWechatHelper().menu.createMenu(button);
    }

}
