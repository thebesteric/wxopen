package org.wesoft.wechat.wxopen.api;

import com.alibaba.fastjson.JSONObject;
import org.wesoft.common.utils.HttpUtils;
import org.wesoft.common.utils.StringUtils;
import org.wesoft.wechat.wxopen.client.WechatHelperSupport;
import org.wesoft.wechat.wxopen.exception.InvalidParameterException;
import org.wesoft.wechat.wxopen.exception.NullParameterException;

import java.util.ArrayList;
import java.util.List;

/**
 * 公众号标签 API
 */
public class Tag extends WechatHelperSupport {

    /**
     * 创建标签
     *
     * @param name 标签名（30个字符以内）
     */
    public JSONObject create(String name) throws NullParameterException, InvalidParameterException {
        if (StringUtils.isNotEmpty(name) && name.length() <= 30) {
            String url = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=%s";
            url = String.format(url, getAccessToken());
            JSONObject param = new JSONObject();
            param.put("name", name);
            return HttpUtils.doPost(url, packageTagParams(param));
        }
        throw new InvalidParameterException("Tag Name cannot be empty, and length must be greater than 30");
    }

    /**
     * 获取公众号已创建的标签
     */
    public JSONObject list() throws NullParameterException {
        String url = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=%s";
        url = String.format(url, getAccessToken());
        return HttpUtils.doGet(url);
    }

    /**
     * 编辑标签
     *
     * @param id   标签 ID
     * @param name 标签名（30个字符以内）
     */
    public JSONObject update(Integer id, String name) throws NullParameterException, InvalidParameterException {
        if (id != null && StringUtils.isNotEmpty(name) && name.length() <= 30) {
            String url = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=%s";
            url = String.format(url, getAccessToken());
            JSONObject param = new JSONObject();
            param.put("id", id);
            param.put("name", name);
            return HttpUtils.doPost(url, packageTagParams(param));
        }
        throw new InvalidParameterException("Tag Name cannot be empty, and length must be greater than 30");
    }

    /**
     * 删除标签
     *
     * @param id 标签 ID
     */
    public JSONObject delete(Integer id) throws NullParameterException, InvalidParameterException {
        if (id != 1 && id != 2 && id != 3) {
            String url = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=%s";
            url = String.format(url, getAccessToken());
            JSONObject param = new JSONObject();
            param.put("id", id);
            return HttpUtils.doPost(url, packageTagParams(param));
        }
        throw new InvalidParameterException("Can not be delete 1 or 2 or 3 tag ID, It is system retention attribute");
    }

    /**
     * 获取标签下粉丝列表
     *
     * @param id         标签 ID
     * @param nextOpenid 第一个拉取的 OPENID，不填默认从头开始拉取
     */
    public JSONObject getOpenidByTag(Integer id, String nextOpenid) throws NullParameterException {
        String url = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=%s";
        url = String.format(url, getAccessToken());
        JSONObject param = new JSONObject();
        param.put("tagid", id);
        param.put("next_openid", nextOpenid);
        return HttpUtils.doPost(url, param);
    }

    /**
     * 获取用户身上的标签列表
     *
     * @param openid openid
     */
    public JSONObject getTagByOpenid(String openid) throws NullParameterException {
        String url = "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=%s";
        url = String.format(url, getAccessToken());
        JSONObject param = new JSONObject();
        param.put("openid", openid);
        return HttpUtils.doPost(url, param);
    }

    /**
     * 批量为用户打标签
     *
     * @param id      标签 ID
     * @param openids openids
     */
    public JSONObject batchTaggingToUser(Integer id, List<String> openids) throws NullParameterException {
        String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=%s";
        url = String.format(url, getAccessToken());
        JSONObject param = new JSONObject();
        param.put("tagid", id);
        param.put("openid_list", openids);
        return HttpUtils.doPost(url, param);
    }

    /**
     * 批量为用户取消标签
     *
     * @param id      标签 ID
     * @param openids openids
     */
    public JSONObject batchUnTaggingToUser(Integer id, List<String> openids) throws NullParameterException {
        String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=%s";
        url = String.format(url, getAccessToken());
        JSONObject param = new JSONObject();
        param.put("tagid", id);
        param.put("openid_list", openids);
        return HttpUtils.doPost(url, param);
    }


    private JSONObject packageTagParams(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tag", param);
        return jsonObject;
    }


}
