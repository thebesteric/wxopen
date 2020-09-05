package org.wesoft.wechat.wxopen.api.applet;

import com.alibaba.fastjson.JSONObject;
import org.wesoft.common.utils.web.HttpUtils;
import org.wesoft.wechat.wxopen.client.applet.AppletHelperSupport;
import org.wesoft.wechat.wxopen.exception.NullParameterException;

/**
 * 认证相关
 *
 * @author Eric Joe
 * @version Ver 1.0
 * @build 2020-05-11 16:26
 */
public class Auth extends AppletHelperSupport {

    private String appID, appSecret;

    public Auth(String appID, String appSecret) {
        this.appID = appID;
        this.appSecret = appSecret;
    }

    /**
     * code 换取 openid, unionid 等信息
     *
     * @param jsCode jsCode
     */
    public JSONObject code2Session(String jsCode) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
        url = String.format(url, appID, appSecret, jsCode);
        return HttpUtils.doGet(url);
    }

    /**
     * 户支付完成后，获取用户的 UnionId
     *
     * @param openid openid
     */
    public JSONObject getPaidUnionId(String openid) throws NullParameterException {
        String url = "https://api.weixin.qq.com/wxa/getpaidunionid?access_token=%s&openid=%s";
        url = String.format(url, getAccessToken(appID, appSecret), openid);
        return HttpUtils.doGet(url);
    }

    /**
     * 户支付完成后，获取用户的 UnionId
     *
     * @param openid        openid
     * @param transactionId 微信支付订单号
     */
    public JSONObject getPaidUnionId(String openid, String transactionId) throws NullParameterException {
        String url = "https://api.weixin.qq.com/wxa/getpaidunionid?access_token=%s&openid=%s&transaction_id=%s";
        url = String.format(url, getAccessToken(appID, appSecret), openid, transactionId);
        return HttpUtils.doGet(url);
    }

    /**
     * 户支付完成后，获取用户的 UnionId
     *
     * @param openid     openid
     * @param mchId      微信支付分配的商户号
     * @param outTradeNo 微信支付商户订单号
     */
    public JSONObject getPaidUnionId(String openid, String mchId, String outTradeNo) throws NullParameterException {
        String url = "https://api.weixin.qq.com/wxa/getpaidunionid?access_token=%s&openid=%s&mch_id=%s&out_trade_no=%s";
        url = String.format(url, getAccessToken(appID, appSecret), openid, mchId, outTradeNo);
        return HttpUtils.doGet(url);
    }
}
