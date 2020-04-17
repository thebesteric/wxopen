package org.wesoft.wechat.wxopen.api;

import com.alibaba.fastjson.JSONObject;
import org.wesoft.common.utils.StringUtils;
import org.wesoft.common.utils.web.HttpUtils;
import org.wesoft.wechat.wxopen.client.WechatHelperSupport;
import org.wesoft.wechat.wxopen.domain.NetOAuthAccessToken;
import org.wesoft.wechat.wxopen.exception.InvalidParameterException;
import org.wesoft.wechat.wxopen.exception.NullParameterException;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户 API
 */
public class UserInfo extends WechatHelperSupport {

    /**
     * 获取用户基本信息
     *
     * @param openid openid
     */
    public JSONObject getUserInfo(String openid) throws NullParameterException {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
        url = String.format(url, getAccessToken(), openid);
        return HttpUtils.doGet(url);
    }

    /**
     * 获取用户基本信息（网页端）
     *
     * @param openid openid
     * @param code   code 作为换取 access_token 的票据，每次用户授权带上的 code 将不一样，code 只能使用一次，5 分钟未被使用自动过期
     */
    public JSONObject getUserInfoForWebCode(String openid, String code) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
        url = String.format(url, getWebAccessToken(code).getAccess_token(), openid);
        return HttpUtils.doGet(url);
    }

    /**
     * 获取用户基本信息（网页端）
     *
     * @param openid         openid
     * @param webAccessToken webAccessToken
     */
    public JSONObject getUserInfoForWebAccessToken(String openid, String webAccessToken) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
        url = String.format(url, webAccessToken, openid);
        return HttpUtils.doGet(url);
    }

    /**
     * 批量获取用户基本信息
     *
     * @param openids openids
     */
    public JSONObject batchGetUserInfo(List<String> openids) throws NullParameterException {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=%s";
        url = String.format(url, getAccessToken());
        List<JSONObject> openidList = new ArrayList<>();
        for (String openid : openids) {
            JSONObject param = new JSONObject();
            param.put("openid", openid);
            param.put("lang", "zh_CN");
            openidList.add(param);
        }
        JSONObject param = new JSONObject();
        param.put("user_list", openidList);
        return HttpUtils.doPost(url, param);
    }

    /**
     * 获取 openid 列表
     *
     * @param nextOpenid 第一个拉取的 OPENID，不填默认从头开始拉取
     */
    public JSONObject getOpenidList(String nextOpenid) throws NullParameterException {
        String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=%s&next_openid=%s";
        url = String.format(url, getAccessToken(), nextOpenid);
        return HttpUtils.doGet(url);
    }

    /**
     * 设置用户备注名
     *
     * @param openid openid
     * @param remark 新的备注名，长度必须小于30字符
     */
    public JSONObject updateRemark(String openid, String remark) throws NullParameterException, InvalidParameterException {
        if (StringUtils.isNotEmpty(remark) && remark.length() <= 30) {
            String url = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=%s";
            url = String.format(url, getAccessToken());
            JSONObject param = new JSONObject();
            param.put("openid", openid);
            param.put("remark", remark);
            return HttpUtils.doPost(url, param);
        }
        throw new InvalidParameterException("Remark cannot be empty, and length must be greater than 30");
    }

    /**
     * 获取公众号的黑名单列表
     *
     * @param next_openid 第一个拉取的 OPENID，不填默认从头开始拉取
     */
    public JSONObject getBlackList(String next_openid) throws NullParameterException {
        String url = "https://api.weixin.qq.com/cgi-bin/tags/members/getblacklist?access_token=%s";
        url = String.format(url, getAccessToken());
        JSONObject param = new JSONObject();
        param.put("begin_openid", next_openid);
        return HttpUtils.doPost(url, param);
    }

    /**
     * 批量拉黑用户
     *
     * @param openids 需要拉入黑名单的用户的 openid，一次拉黑最多允许 20 个
     */
    public JSONObject batchToBlackList(List<String> openids) throws NullParameterException, InvalidParameterException {
        if (!openids.isEmpty() && openids.size() <= 20) {
            String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist?access_token=%s";
            url = String.format(url, getAccessToken());
            JSONObject param = new JSONObject();
            param.put("openid_list", openids);
            return HttpUtils.doPost(url, param);
        }
        throw new InvalidParameterException("Openids length must be less than or equal to 20");
    }

    /**
     * 取消拉黑用户
     *
     * @param openids 需要取消拉入黑名单的用户的 openid，一次拉黑最多允许 20 个
     */
    public JSONObject batchToUnBlackList(List<String> openids) throws NullParameterException, InvalidParameterException {
        if (!openids.isEmpty() && openids.size() <= 20) {
            String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist?access_token=%s";
            url = String.format(url, getAccessToken());
            JSONObject param = new JSONObject();
            param.put("openid_list", openids);
            return HttpUtils.doPost(url, param);
        }
        throw new InvalidParameterException("Openids length must be less than or equal to 20");
    }

}
