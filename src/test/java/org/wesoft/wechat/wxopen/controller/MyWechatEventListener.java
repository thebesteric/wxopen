package org.wesoft.wechat.wxopen.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.wesoft.wechat.wxopen.client.WechatHelper;
import org.wesoft.wechat.wxopen.domain.TemplateMessage;
import org.wesoft.wechat.wxopen.domain.message.request.*;
import org.wesoft.wechat.wxopen.exception.NullParameterException;
import org.wesoft.wechat.wxopen.listener.WechatEventListener;

import java.util.HashMap;
import java.util.Map;

@Component
public class MyWechatEventListener extends WechatEventListener {


    @Override
    public String processText(WechatHelper wechatHelper, ReqTextMessage reqTextMessage) throws NullParameterException {
        // JSONObject jsonObject = wechatHelper.generateTempQrCode("test");
        // return jsonObject.toString();
        Map<String, String[]> datas = new HashMap<>();
        datas.put("first", new String[]{"哈哈哈", TemplateMessage.Color.BLUE});
        datas.put("keyword1", new String[]{"呵呵呵"});
        datas.put("remark", new String[]{"嘿嘿嘿"});
        TemplateMessage templateMessage = wechatHelper.packageTemplateMessage("v9Rsb_rzdikFqLlEWgB9sUaxIxSPiLFF_vXaEQsGD8w", datas).setTouser(reqTextMessage.getFromUserName());
        JSONObject jsonObject = wechatHelper.sendTemplateMessage(templateMessage);
        return reqTextMessage.getContent() + " 收到了" + jsonObject;
    }

    @Override
    public String processImage(WechatHelper wechatHelper, ReqImageMessage reqImageMessage) {
        return null;
    }

    @Override
    public String processLocation(WechatHelper wechatHelper, ReqLocationMessage reqLocationMessage) {
        return null;
    }

    @Override
    public String processLink(WechatHelper wechatHelper, ReqLinkMessage reqLinkMessage) {
        return null;
    }

    @Override
    public String processVoice(WechatHelper wechatHelper, ReqVoiceMessage reqVoiceMessage) {
        return null;
    }

    @Override
    public String processVideo(WechatHelper wechatHelper, ReqVideoMessage reqVideoMessage) {
        return null;
    }

    @Override
    public String processEventSubscribe(WechatHelper wechatHelper, ReqEventSubscribeMessage reqEventSubscribeMessage) {
        return null;
    }

    @Override
    public String processEventSubscribeScan(WechatHelper wechatHelper, ReqEventSubscribeScanMessage reqEventSubscribeScanMessage) {
        return null;
    }

    @Override
    public String processEventScan(WechatHelper wechatHelper, ReqEventSubscribeScanMessage reqEventSubscribeScanMessage) {
        return null;
    }

    @Override
    public String processEventUnSubscribe(WechatHelper wechatHelper, ReqEventSubscribeMessage reqEventUnSubscribeMessage) {
        return null;
    }

    @Override
    public String processEventMenuClick(WechatHelper wechatHelper, ReqEventMenuClickMessage reqEventMenuClickMessage) {
        return null;
    }

    @Override
    public String processEventMenuView(WechatHelper wechatHelper, ReqEventMenuViewMessage reqEventMenuViewMessage) {
        return null;
    }

    @Override
    public String processEventMenuScanCodePush(WechatHelper wechatHelper, ReqEventMenuScanCodeMessage reqEventMenuScanCodePushMessage) {
        return null;
    }

    @Override
    public String processEventMenuScanCodeWaitMsg(WechatHelper wechatHelper, ReqEventMenuScanCodeMessage reqEventMenuScanCodeWaitMessage) {
        return null;
    }

    @Override
    public String processEventMenuPicSysPhoto(WechatHelper wechatHelper, ReqEventMenuPicPhotoMessage reqEventMenuPicSysPhotoMessage) {
        return null;
    }

    @Override
    public String processEventMenuPicPhotoOrAlbum(WechatHelper wechatHelper, ReqEventMenuPicPhotoMessage reqEventMenuPicPhotoOrAlbumMessage) {
        return null;
    }

    @Override
    public String processEventMenuPicWeiXin(WechatHelper wechatHelper, ReqEventMenuPicPhotoMessage reqEventMenuPicWeiXinMessage) {
        return null;
    }


    @Override
    public String processEventLocation(WechatHelper wechatHelper, ReqEventLocationMessage reqEventLocationMessage) {
        return null;
    }
}
