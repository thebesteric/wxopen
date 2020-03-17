package org.wesoft.wechat.wxopen.listener;

import org.wesoft.common.utils.StringUtils;
import org.wesoft.wechat.wxopen.base.BaseEventRequestMessage;
import org.wesoft.wechat.wxopen.base.BaseRequestMessage;
import org.wesoft.wechat.wxopen.base.BaseResponseMessage;
import org.wesoft.wechat.wxopen.client.WechatHelper;
import org.wesoft.wechat.wxopen.constant.WechatConstant;
import org.wesoft.wechat.wxopen.domain.message.request.*;
import org.wesoft.wechat.wxopen.domain.message.response.ResImageMessage;
import org.wesoft.wechat.wxopen.domain.message.response.RespTextMessage;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public abstract class WechatEventListener extends WechatEvent {

    public final String listen(HttpServletRequest request, WechatHelper helper) {

        String respMessage = null;

        try {
            Object respContent = "请求处理异常，请稍候尝试";

            // xml 请求解析
            Map<String, String> requestMap = parseXml(request, helper);
            BaseRequestMessage baseRequestMessage = packageToBaseRequestMessage(requestMap);
            String msgType = baseRequestMessage.getMsgType();

            // 默认回复此文本消息
            RespTextMessage respTextMessage = new RespTextMessage();
            respTextMessage.setToUserName(baseRequestMessage.getFromUserName());
            respTextMessage.setFromUserName(baseRequestMessage.getToUserName());
            respTextMessage.setCreateTime(new Date().getTime());
            respTextMessage.setMsgType(WechatConstant.RESP_MESSAGE_TYPE_TEXT);
            respTextMessage.setFuncFlag(0);

            // 文本消息
            if (msgType.equalsIgnoreCase(WechatConstant.RESP_MESSAGE_TYPE_TEXT)) {
                ReqTextMessage req = (ReqTextMessage) new ReqTextMessage().setContent(requestMap.get("Content")).setBaseRequestMessage(baseRequestMessage);
                respContent = processText(helper, req);
            }
            // 图片消息
            else if (msgType.equalsIgnoreCase(WechatConstant.REQ_MESSAGE_TYPE_IMAGE)) {
                ReqImageMessage req = (ReqImageMessage) new ReqImageMessage().setPicUrl(requestMap.get("PicUrl")).setBaseRequestMessage(baseRequestMessage);
                respContent = processImage(helper, req);
            }
            // 地理位置消息
            else if (msgType.equalsIgnoreCase(WechatConstant.REQ_MESSAGE_TYPE_LOCATION)) {
                ReqLocationMessage req = (ReqLocationMessage) new ReqLocationMessage().setLocation_X(requestMap.get("Location_X"))
                        .setLocation_Y(requestMap.get("Location_Y")).setScale(requestMap.get("Scale")).setLabel(requestMap.get("Label")).setBaseRequestMessage(baseRequestMessage);
                respContent = processLocation(helper, req);
            }
            // 链接消息
            else if (msgType.equalsIgnoreCase(WechatConstant.REQ_MESSAGE_TYPE_LINK)) {
                ReqLinkMessage req = (ReqLinkMessage) new ReqLinkMessage().setTitle(requestMap.get("Title"))
                        .setDescription(requestMap.get("Description")).setUrl(requestMap.get("Url")).setBaseRequestMessage(baseRequestMessage);
                respContent = processLink(helper, req);
            }
            // 音频消息
            else if (msgType.equalsIgnoreCase(WechatConstant.REQ_MESSAGE_TYPE_VOICE)) {
                ReqVoiceMessage req = (ReqVoiceMessage) new ReqVoiceMessage().setMediaId(requestMap.get("MediaId"))
                        .setFormat(requestMap.get("Format")).setBaseRequestMessage(baseRequestMessage);
                respContent = processVoice(helper, req);
            }
            // 视频（小视频）消息
            else if (msgType.equalsIgnoreCase(WechatConstant.REQ_MESSAGE_TYPE_VIDEO) || msgType.equalsIgnoreCase(WechatConstant.REQ_MESSAGE_TYPE_SHORT_VIDEO)) {
                ReqVideoMessage req = (ReqVideoMessage) new ReqVideoMessage().setMediaId(requestMap.get("MediaId"))
                        .setThumbMediaId(requestMap.get("ThumbMediaId")).setBaseRequestMessage(baseRequestMessage);
                respContent = processVideo(helper, req);
            }
            // 事件推送
            else if (msgType.equalsIgnoreCase(WechatConstant.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                BaseEventRequestMessage baseEventRequestMessage = packageToBaseEventRequestMessage(requestMap);
                String event = baseEventRequestMessage.getEvent();
                String eventKey = requestMap.getOrDefault("EventKey", "");
                // 订阅（关注）
                if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_SUBSCRIBE)) {
                    // 扫描带参数二维码事件（首次关注）
                    if (StringUtils.isNotEmpty(eventKey) && eventKey.startsWith("qrscene_")) {
                        ReqEventSubscribeScanMessage req = (ReqEventSubscribeScanMessage) new ReqEventSubscribeScanMessage()
                                .setEventKey(eventKey).setTicket(requestMap.get("Ticket")).setBaseEventRequestMessage(baseEventRequestMessage);
                        respContent = processEventSubscribeScan(helper, req);
                    }
                    // 正常关注
                    else {
                        ReqEventSubscribeMessage req = (ReqEventSubscribeMessage) new ReqEventSubscribeMessage()
                                .setBaseEventRequestMessage(baseEventRequestMessage);
                        respContent = processEventSubscribe(helper, req);
                    }
                }
                // 用户已关注时，再次关注的事件推送
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_SCAN)) {
                    ReqEventSubscribeScanMessage req = (ReqEventSubscribeScanMessage) new ReqEventSubscribeScanMessage()
                            .setEventKey(eventKey).setTicket(requestMap.get("Ticket")).setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventScan(helper, req);
                }
                // 取消订阅（取消关注）
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_UNSUBSCRIBE)) {
                    ReqEventSubscribeMessage req = (ReqEventSubscribeMessage) new ReqEventSubscribeMessage()
                            .setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventUnSubscribe(helper, req);
                }
                // 自定义菜单 - 点击事件
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_MENU_CLICK)) {
                    ReqEventMenuClickMessage req = (ReqEventMenuClickMessage) new ReqEventMenuClickMessage()
                            .setEventKey(eventKey).setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventMenuClick(helper, req);
                }
                // 自定义菜单 - 跳转链接事件
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_MENU_VIEW)) {
                    ReqEventMenuViewMessage req = (ReqEventMenuViewMessage) new ReqEventMenuViewMessage()
                            .setEventKey(eventKey).setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventMenuView(helper, req);
                }
                // 自定义菜单 - 扫码推事件的事件推送
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_MENU_SCANCODE_PUSH)) {
                    String scanType = requestMap.get("ScanType");
                    String scanResult = requestMap.get("ScanResult");
                    ReqEventMenuScanCodeMessage req = (ReqEventMenuScanCodeMessage) new ReqEventMenuScanCodeMessage()
                            .setEventKey(eventKey).setScanCodeInfo(new ReqEventMenuScanCodeMessage.ScanCodeInfo(scanType, scanResult))
                            .setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventMenuScanCodePush(helper, req);
                }
                // 自定义菜单 - 扫码推事件且弹出"消息接收中"提示框的事件推送
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_MENU_SCANCODE_WAIT_MSG)) {
                    String scanType = requestMap.get("ScanType");
                    String scanResult = requestMap.get("ScanResult");
                    ReqEventMenuScanCodeMessage req = (ReqEventMenuScanCodeMessage) new ReqEventMenuScanCodeMessage()
                            .setEventKey(eventKey).setScanCodeInfo(new ReqEventMenuScanCodeMessage.ScanCodeInfo(scanType, scanResult))
                            .setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventMenuScanCodeWaitMsg(helper, req);
                }
                // 自定义菜单 - 弹出系统拍照发图的事件推送
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_MENU_PIC_SYS_PHOTO)) {
                    int count = Integer.parseInt(requestMap.getOrDefault("Count", "0"));
                    String picMd5Sum = requestMap.get("PicMd5Sum");
                    List<String> picMd5Sums = new ArrayList<>(8);
                    picMd5Sums.add(picMd5Sum);
                    ReqEventMenuPicPhotoMessage req = (ReqEventMenuPicPhotoMessage) new ReqEventMenuPicPhotoMessage()
                            .setEventKey(eventKey).setSendPicsInfo(new ReqEventMenuPicPhotoMessage.SendPicsInfo(count, picMd5Sums))
                            .setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventMenuPicSysPhoto(helper, req);
                }
                // 自定义菜单 - 弹出拍照或者相册发图的事件推送
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_MENU_PIC_PHOTO_OR_ALBUM)) {
                    int count = Integer.parseInt(requestMap.getOrDefault("Count", "0"));
                    String picMd5Sum = requestMap.get("PicMd5Sum");
                    List<String> picMd5Sums = new ArrayList<>(8);
                    picMd5Sums.add(picMd5Sum);
                    ReqEventMenuPicPhotoMessage req = (ReqEventMenuPicPhotoMessage) new ReqEventMenuPicPhotoMessage()
                            .setEventKey(eventKey).setSendPicsInfo(new ReqEventMenuPicPhotoMessage.SendPicsInfo(count, picMd5Sums))
                            .setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventMenuPicPhotoOrAlbum(helper, req);
                }
                // 自定义菜单 - 弹出微信相册发图器的事件推送
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_MENU_PIC_WEIXIN)) {
                    int count = Integer.parseInt(requestMap.getOrDefault("Count", "0"));
                    String picMd5Sum = requestMap.get("PicMd5Sum");
                    List<String> picMd5Sums = new ArrayList<>(8);
                    picMd5Sums.add(picMd5Sum);
                    ReqEventMenuPicPhotoMessage req = (ReqEventMenuPicPhotoMessage) new ReqEventMenuPicPhotoMessage()
                            .setEventKey(eventKey).setSendPicsInfo(new ReqEventMenuPicPhotoMessage.SendPicsInfo(count, picMd5Sums))
                            .setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventMenuPicWeiXin(helper, req);
                }
                // 自定义菜单 - 弹出地理位置选择器的事件推送
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_MENU_LOCATION_SELECT)) {
                    String locationX = requestMap.get("Location_X");
                    String locationY = requestMap.get("Location_Y");
                    String scale = requestMap.get("Scale");
                    String label = requestMap.get("Label");
                    String poiname = requestMap.get("Poiname");
                    ReqEventMenuLocationSelectMessage req = (ReqEventMenuLocationSelectMessage) new ReqEventMenuLocationSelectMessage()
                            .setEventKey(eventKey).setSendLocationInfo(new ReqEventMenuLocationSelectMessage.SendLocationInfo(locationX, locationY, scale, label, poiname))
                            .setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventMenuLocationSelect(helper, req);
                }
                // 自定义菜单 - 点击菜单跳转小程序的事件推送
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_MENU_VIEW_MINI_PROGRAM)) {
                    String menuId = requestMap.get("MenuId");
                    ReqEventMenuViewMiniProgramMessage req = (ReqEventMenuViewMiniProgramMessage) new ReqEventMenuViewMiniProgramMessage()
                            .setEventKey(eventKey).setMenuId(menuId).setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventMenuViewMiniProgram(helper, req);
                }

                // 自动上报地理信息事件
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_LOCATION)) {
                    ReqEventLocationMessage req = (ReqEventLocationMessage) new ReqEventLocationMessage()
                            .setLongitude(requestMap.get("Longitude")).setLatitude(requestMap.get("Latitude"))
                            .setPrecision(requestMap.get("Precision")).setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventLocation(helper, req);
                }
            }

            // 返回消息处理
            if (respContent == null) {
                respMessage = "success";
            } else if (respContent.getClass().equals(String.class)) {
                if (((String) respContent).length() == 0) {
                    respMessage = "success";
                } else {
                    respTextMessage.setContent((String) respContent);
                    respMessage = helper.responseMessageToXml(respTextMessage);
                }
            } else {
                respMessage = helper.responseMessageToXml((BaseResponseMessage) respContent);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return respMessage;
    }

    /**
     * 包装 BaseRequestMessage
     *
     * @param requestMap XML 元数据
     * @return BaseRequestMessage
     */
    private BaseRequestMessage packageToBaseRequestMessage(Map<String, String> requestMap) {
        BaseRequestMessage baseRequestMessage = new BaseRequestMessage();
        baseRequestMessage.setCreateTime(Long.parseLong(requestMap.get("CreateTime")));
        baseRequestMessage.setFromUserName(requestMap.get("FromUserName"));
        baseRequestMessage.setToUserName(requestMap.get("ToUserName"));
        baseRequestMessage.setMsgId(Long.parseLong(requestMap.getOrDefault("MsgId", "0")));
        baseRequestMessage.setMsgType(requestMap.get("MsgType"));
        return baseRequestMessage;
    }

    /**
     * 包装 BaseEventRequestMessage
     *
     * @param requestMap XML 元数据
     * @return BaseEventRequestMessage
     */
    private BaseEventRequestMessage packageToBaseEventRequestMessage(Map<String, String> requestMap) {
        BaseEventRequestMessage baseEventRequestMessage = new BaseEventRequestMessage();
        baseEventRequestMessage.setCreateTime(Long.parseLong(requestMap.get("CreateTime")));
        baseEventRequestMessage.setFromUserName(requestMap.get("FromUserName"));
        baseEventRequestMessage.setToUserName(requestMap.get("ToUserName"));
        baseEventRequestMessage.setEvent(requestMap.get("Event"));
        baseEventRequestMessage.setMsgType(requestMap.get("MsgType"));
        return baseEventRequestMessage;
    }

    /**
     * 解析 XML
     *
     * @param request      请求
     * @param wechatHelper wechatHelper
     * @return Map<String, String>
     */
    private Map<String, String> parseXml(HttpServletRequest request, WechatHelper wechatHelper) throws Exception {
        if (wechatHelper.getCrypt() != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            final StringBuilder buffer = new StringBuilder(1024);
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            wechatHelper.setMsgSignature(request.getParameter("msg_signature"));
            // 消息解密
            String decryptMsg = wechatHelper.getCrypt().decryptMsg(wechatHelper.getMsgSignature(), wechatHelper.getTimestamp(), wechatHelper.getNonce(), buffer.toString());
            return wechatHelper.parseXml(decryptMsg);
        }
        return wechatHelper.parseXml(request);
    }

}
