package org.wesoft.wechat.wxopen.client;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDomDriver;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wesoft.common.utils.StringUtils;
import org.wesoft.common.utils.web.HttpUtils;
import org.wesoft.wechat.wxopen.base.BaseResponseMessage;
import org.wesoft.wechat.wxopen.base.BaseWechatDomain;
import org.wesoft.wechat.wxopen.cache.LocalCache;
import org.wesoft.wechat.wxopen.client.crypt.WXBizMsgCrypt;
import org.wesoft.wechat.wxopen.constant.WechatConstant;
import org.wesoft.wechat.wxopen.domain.NetOAuthAccessToken;
import org.wesoft.wechat.wxopen.domain.WebSignature;
import org.wesoft.wechat.wxopen.exception.NullParameterException;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class WechatHelperSupport extends WechatConstant {

    private static final Logger logger = LoggerFactory.getLogger(WechatHelperSupport.class);

    public String msgSignature, signature, timestamp, nonce;

    public WXBizMsgCrypt crypt;

    /**
     * 扩展 xstream，使其支持 CDATA 块
     */
    protected static XStream xStream = new XStream(new XppDomDriver() {
        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                boolean cdata = true;

                @Override
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                @Override
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    /**
     * 获取 AccessToken
     *
     * @return String
     */
    public String getAccessToken(String appID, String appSecret) throws NullParameterException {
        String accessToken = (String) LocalCache.getInstance().get(WechatConstant.ACCESS_TOKEN_PREFIX + appID);
        if (StringUtils.isEmpty(accessToken)) {
            if (StringUtils.isEmpty(appID) || StringUtils.isEmpty(appSecret)) {
                throw new NullParameterException("AppID and AppSecret must be not null");
            }
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
            url = String.format(url, appID, appSecret);
            JSONObject ret = HttpUtils.doGet(url);
            if (ret != null) {
                logger.info(ACCESS_TOKEN_PREFIX + appID + "_" + ret.toString());
                accessToken = ret.getString("access_token");
                LocalCache.getInstance().put(WechatConstant.ACCESS_TOKEN_PREFIX + appID, accessToken, ret.getLong(WechatConstant.EXPIRES_IN));
            }
        }
        return accessToken;
    }

    /**
     * 通过 code 换取网页授权 access_token
     *
     * @param code code 作为换取 access_token 的票据，每次用户授权带上的 code 将不一样，code 只能使用一次，5分钟未被使用自动过期
     * @return NetOAuthAccessToken
     */
    public NetOAuthAccessToken getWebAccessToken(String appID, String appSecret, String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
        url = String.format(url, appID, appSecret, code);
        JSONObject jsonObject = HttpUtils.doGet(url);
        return jsonObject.toJavaObject(NetOAuthAccessToken.class);
    }

    /**
     * 从流中解析对象
     *
     * @param request 请求
     * @return Map<String, String>
     */
    public Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<>(16);
        InputStream inputStream = request.getInputStream();
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        List<Element> elements = parseDocument(document);
        for (Element e : elements) {
            packageElement(e, map);
        }
        inputStream.close();

        return map;
    }

    public Map<String, String> parseXml(String xml) throws Exception {
        Map<String, String> map = new HashMap<>(16);
        Document document = DocumentHelper.parseText(xml);
        List<Element> elements = parseDocument(document);
        for (Element e : elements) {
            packageElement(e, map);
        }
        return map;
    }

    private void packageElement(Element element, Map<String, String> map) {
        if (element.elements() != null) {
            @SuppressWarnings("unchecked")
            List<Element> elements = (List<Element>) element.elements();
            for (Element e : elements) {
                packageElement(e, map);
            }
        }
        map.put(element.getName(), element.getText());
    }


    @SuppressWarnings("unchecked")
    private List<Element> parseDocument(Document document) {
        Element root = document.getRootElement();
        return (List<Element>) root.elements();
    }

    /**
     * 响应消息对象转换成 XML 格式
     *
     * @param responseMessage 响应消息对象
     * @return xml
     */
    public String responseMessageToXml(BaseResponseMessage responseMessage) {
        xStream.alias("xml", responseMessage.getClass());
        return new String(xStream.toXML(responseMessage).trim().getBytes(), StandardCharsets.UTF_8);
    }

    public static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static class SHA1 {

        private static String getFormattedText(byte[] bytes) {
            int len = bytes.length;
            StringBuilder hexStr = new StringBuilder(len * 2);
            String shaHex = "";
            for (byte aByte : bytes) {
                shaHex = Integer.toHexString(aByte & 0xFF);
                if (shaHex.length() < 2) hexStr.append(0);
                hexStr.append(shaHex);
            }
            return hexStr.toString();
        }

        public static String encode(String str) {
            if (str == null) return null;
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                messageDigest.update(str.getBytes());
                return getFormattedText(messageDigest.digest());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

}
