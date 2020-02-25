package org.wesoft.wechat.wxopen.listener;

import org.wesoft.common.utils.StringUtils;
import org.wesoft.wechat.wxopen.base.BaseEventRequestMessage;
import org.wesoft.wechat.wxopen.base.BaseRequestMessage;
import org.wesoft.wechat.wxopen.client.WechatHelper;
import org.wesoft.wechat.wxopen.constant.WechatConstant;
import org.wesoft.wechat.wxopen.domain.message.request.*;
import org.wesoft.wechat.wxopen.domain.message.response.ResTextMessage;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public abstract class WechatEventListener extends WechatEvent {

    public final String listen(HttpServletRequest request, WechatHelper wechatHelper) {

        String respMessage = null;

        try {
            String respContent = "请求处理异常，请稍候尝试";

            // xml 请求解析
            Map<String, String> requestMap = parseXml(request, wechatHelper);
            BaseRequestMessage baseRequestMessage = packageToBaseRequestMessage(requestMap);
            String msgType = baseRequestMessage.getMsgType();

            // 默认回复此文本消息
            ResTextMessage resTextMessage = new ResTextMessage();
            resTextMessage.setToUserName(baseRequestMessage.getFromUserName());
            resTextMessage.setFromUserName(baseRequestMessage.getToUserName());
            resTextMessage.setCreateTime(new Date().getTime());
            resTextMessage.setMsgType(WechatConstant.RESP_MESSAGE_TYPE_TEXT);
            resTextMessage.setFuncFlag(0);

            // 文本消息
            if (msgType.equalsIgnoreCase(WechatConstant.RESP_MESSAGE_TYPE_TEXT)) {
                ReqTextMessage reqTextMessage = (ReqTextMessage) new ReqTextMessage().setContent(requestMap.get("Content")).setBaseRequestMessage(baseRequestMessage);
                respContent = processText(wechatHelper, reqTextMessage);
            }
            // 图片消息
            else if (msgType.equalsIgnoreCase(WechatConstant.REQ_MESSAGE_TYPE_IMAGE)) {
                ReqImageMessage reqImageMessage = (ReqImageMessage) new ReqImageMessage().setPicUrl(requestMap.get("PicUrl")).setBaseRequestMessage(baseRequestMessage);
                respContent = processImage(wechatHelper, reqImageMessage);
            }
            // 地理位置消息
            else if (msgType.equalsIgnoreCase(WechatConstant.REQ_MESSAGE_TYPE_LOCATION)) {
                ReqLocationMessage reqLocationMessage = (ReqLocationMessage) new ReqLocationMessage().setLocation_X(requestMap.get("Location_X"))
                        .setLocation_Y(requestMap.get("Location_Y")).setScale(requestMap.get("Scale")).setLabel(requestMap.get("Label")).setBaseRequestMessage(baseRequestMessage);
                respContent = processLocation(wechatHelper, reqLocationMessage);
            }
            // 链接消息
            else if (msgType.equalsIgnoreCase(WechatConstant.REQ_MESSAGE_TYPE_LINK)) {
                ReqLinkMessage reqLinkMessage = (ReqLinkMessage) new ReqLinkMessage().setTitle(requestMap.get("Title"))
                        .setDescription(requestMap.get("Description")).setUrl(requestMap.get("Url")).setBaseRequestMessage(baseRequestMessage);
                respContent = processLink(wechatHelper, reqLinkMessage);
            }
            // 音频消息
            else if (msgType.equalsIgnoreCase(WechatConstant.REQ_MESSAGE_TYPE_VOICE)) {
                ReqVoiceMessage reqVoiceMessage = (ReqVoiceMessage) new ReqVoiceMessage().setMediaId(requestMap.get("MediaId"))
                        .setFormat(requestMap.get("Format")).setBaseRequestMessage(baseRequestMessage);
                respContent = processVoice(wechatHelper, reqVoiceMessage);
            }
            // 视频（小视频）消息
            else if (msgType.equalsIgnoreCase(WechatConstant.REQ_MESSAGE_TYPE_VIDEO) || msgType.equalsIgnoreCase(WechatConstant.REQ_MESSAGE_TYPE_SHORT_VIDEO)) {
                ReqVideoMessage reqVideoMessage = (ReqVideoMessage) new ReqVideoMessage().setMediaId(requestMap.get("MediaId"))
                        .setThumbMediaId(requestMap.get("ThumbMediaId")).setBaseRequestMessage(baseRequestMessage);
                respContent = processVideo(wechatHelper, reqVideoMessage);
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
                        ReqEventSubscribeScanMessage reqEventSubscribeScanMessage = (ReqEventSubscribeScanMessage) new ReqEventSubscribeScanMessage()
                                .setEventKey(eventKey).setTicket(requestMap.get("Ticket")).setBaseEventRequestMessage(baseEventRequestMessage);
                        respContent = processEventSubscribeScan(wechatHelper, reqEventSubscribeScanMessage);
                    }
                    // 正常关注
                    else {
                        ReqEventSubscribeMessage reqEventSubscribeMessage = (ReqEventSubscribeMessage) new ReqEventSubscribeMessage()
                                .setBaseEventRequestMessage(baseEventRequestMessage);
                        respContent = processEventSubscribe(wechatHelper, reqEventSubscribeMessage);
                    }
                }
                // 用户已关注时，再次关注的事件推送
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_SCAN)) {
                    ReqEventSubscribeScanMessage reqEventSubscribeScanMessage = (ReqEventSubscribeScanMessage) new ReqEventSubscribeScanMessage()
                            .setEventKey(eventKey).setTicket(requestMap.get("Ticket")).setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventScan(wechatHelper, reqEventSubscribeScanMessage);
                }
                // 取消订阅（取消关注）
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_UNSUBSCRIBE)) {
                    ReqEventSubscribeMessage reqEventUnSubscribeMessage = (ReqEventSubscribeMessage) new ReqEventSubscribeMessage()
                            .setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventUnSubscribe(wechatHelper, reqEventUnSubscribeMessage);
                }
                // 自定义菜单 - 点击事件
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_MENU_CLICK)) {
                    ReqEventMenuClickMessage reqEventMenuClickMessage = (ReqEventMenuClickMessage) new ReqEventMenuClickMessage()
                            .setEventKey(eventKey).setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventMenuClick(wechatHelper, reqEventMenuClickMessage);
                }
                // 自定义菜单 - 跳转链接事件
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_MENU_VIEW)) {
                    ReqEventMenuViewMessage reqEventMenuViewMessage = (ReqEventMenuViewMessage) new ReqEventMenuViewMessage()
                            .setEventKey(eventKey).setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventMenuView(wechatHelper, reqEventMenuViewMessage);
                }
                // 自定义菜单 - 扫码推事件的事件推送
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_MENU_SCANCODE_PUSH)) {
                    String scanType = requestMap.get("ScanType");
                    String scanResult = requestMap.get("ScanResult");
                    ReqEventMenuScanCodeMessage reqEventMenuScanCodePushMessage = (ReqEventMenuScanCodeMessage) new ReqEventMenuScanCodeMessage()
                            .setEventKey(eventKey).setScanCodeInfo(new ReqEventMenuScanCodeMessage.ScanCodeInfo(scanType, scanResult))
                            .setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventMenuScanCodePush(wechatHelper, reqEventMenuScanCodePushMessage);
                }
                // 自定义菜单 - 扫码推事件且弹出"消息接收中"提示框的事件推送
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_MENU_SCANCODE_WAIT_MSG)) {
                    String scanType = requestMap.get("ScanType");
                    String scanResult = requestMap.get("ScanResult");
                    ReqEventMenuScanCodeMessage reqEventMenuScanCodeWaitMessage = (ReqEventMenuScanCodeMessage) new ReqEventMenuScanCodeMessage()
                            .setEventKey(eventKey).setScanCodeInfo(new ReqEventMenuScanCodeMessage.ScanCodeInfo(scanType, scanResult))
                            .setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventMenuScanCodeWaitMsg(wechatHelper, reqEventMenuScanCodeWaitMessage);
                }
                // 自定义菜单 - 弹出系统拍照发图的事件推送
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_MENU_PIC_SYS_PHOTO)) {
                    int count = Integer.parseInt(requestMap.getOrDefault("Count", "0"));
                    String picMd5Sum = requestMap.get("PicMd5Sum");
                    List<String> picMd5Sums = new ArrayList<>(8);
                    picMd5Sums.add(picMd5Sum);
                    ReqEventMenuPicPhotoMessage reqEventMenuPicPhotoMessage = (ReqEventMenuPicPhotoMessage) new ReqEventMenuPicPhotoMessage()
                            .setEventKey(eventKey).setSendPicsInfo(new ReqEventMenuPicPhotoMessage.SendPicsInfo(count, picMd5Sums))
                            .setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventMenuPicSysPhoto(wechatHelper, reqEventMenuPicPhotoMessage);
                }
                // 自定义菜单 - 弹出拍照或者相册发图的事件推送
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_MENU_PIC_PHOTO_OR_ALBUM)) {
                    int count = Integer.parseInt(requestMap.getOrDefault("Count", "0"));
                    String picMd5Sum = requestMap.get("PicMd5Sum");
                    List<String> picMd5Sums = new ArrayList<>(8);
                    picMd5Sums.add(picMd5Sum);
                    ReqEventMenuPicPhotoMessage reqEventMenuPicPhotoOrAlbumMessage = (ReqEventMenuPicPhotoMessage) new ReqEventMenuPicPhotoMessage()
                            .setEventKey(eventKey).setSendPicsInfo(new ReqEventMenuPicPhotoMessage.SendPicsInfo(count, picMd5Sums))
                            .setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventMenuPicPhotoOrAlbum(wechatHelper, reqEventMenuPicPhotoOrAlbumMessage);
                }
                // 自定义菜单 - 弹出微信相册发图器的事件推送
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_MENU_PIC_WEIXIN)) {
                    int count = Integer.parseInt(requestMap.getOrDefault("Count", "0"));
                    String picMd5Sum = requestMap.get("PicMd5Sum");
                    List<String> picMd5Sums = new ArrayList<>(8);
                    picMd5Sums.add(picMd5Sum);
                    ReqEventMenuPicPhotoMessage reqEventMenuPicWeiXinMessage = (ReqEventMenuPicPhotoMessage) new ReqEventMenuPicPhotoMessage()
                            .setEventKey(eventKey).setSendPicsInfo(new ReqEventMenuPicPhotoMessage.SendPicsInfo(count, picMd5Sums))
                            .setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventMenuPicWeiXin(wechatHelper, reqEventMenuPicWeiXinMessage);
                }
                // 自定义菜单 - 弹出地理位置选择器的事件推送
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_MENU_LOCATION_SELECT)) {
                    String locationX = requestMap.get("Location_X");
                    String locationY = requestMap.get("Location_Y");
                    String scale = requestMap.get("Scale");
                    String label = requestMap.get("Label");
                    String poiname = requestMap.get("Poiname");
                    ReqEventMenuLocationSelectMessage reqEventMenuLocationSelectMessage = (ReqEventMenuLocationSelectMessage) new ReqEventMenuLocationSelectMessage()
                            .setEventKey(eventKey).setSendLocationInfo(new ReqEventMenuLocationSelectMessage.SendLocationInfo(locationX, locationY, scale, label, poiname))
                            .setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventMenuLocationSelect(wechatHelper, reqEventMenuLocationSelectMessage);
                }
                // 自定义菜单 - 点击菜单跳转小程序的事件推送
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_MENU_VIEW_MINI_PROGRAM)) {
                    String menuId = requestMap.get("MenuId");
                    ReqEventMenuViewMiniProgramMessage reqEventMenuViewMiniProgramMessage = (ReqEventMenuViewMiniProgramMessage) new ReqEventMenuViewMiniProgramMessage()
                            .setEventKey(eventKey).setMenuId(menuId).setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventMenuViewMiniProgram(wechatHelper, reqEventMenuViewMiniProgramMessage);
                }

                // 自动上报地理信息事件
                else if (event.equalsIgnoreCase(WechatConstant.EVENT_TYPE_LOCATION)) {
                    ReqEventLocationMessage reqEventLocationMessage = (ReqEventLocationMessage) new ReqEventLocationMessage()
                            .setLongitude(requestMap.get("Longitude")).setLatitude(requestMap.get("Latitude"))
                            .setPrecision(requestMap.get("Precision")).setBaseEventRequestMessage(baseEventRequestMessage);
                    respContent = processEventLocation(wechatHelper, reqEventLocationMessage);
                }
            }

            // 返回消息处理
            if (StringUtils.isNotEmpty(respContent)) {
                resTextMessage.setContent(respContent);
                respMessage = wechatHelper.responseMessageToXml(resTextMessage);
            } else {
                respMessage = "";
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
     *
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
     *
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
     *
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
