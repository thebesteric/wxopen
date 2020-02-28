package org.wesoft.wechat.wxopen.api;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.NonNull;
import org.wesoft.common.utils.StringUtils;
import org.wesoft.common.utils.web.HttpUtils;
import org.wesoft.common.utils.web.R;
import org.wesoft.wechat.wxopen.client.WechatHelperSupport;
import org.wesoft.wechat.wxopen.exception.NullParameterException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eric Joe
 * @version Ver 1.0
 * @info 微信导购模块
 * @build 2020-02-11 16:02
 */
@Getter
public class Guide extends WechatHelperSupport {

    /**
     * 导购管理 API
     */
    private GuideApi guideApi = new GuideApi();

    /**
     * 粉丝管理 API
     */
    private BuyerApi buyerApi = new BuyerApi();

    /**
     * 标签管理 API
     */
    private TagApi tagApi = new TagApi();

    /**
     * 账号管理 API
     */
    private AccountApi accountApi = new AccountApi();

    /**
     * 导购管理 API
     */
    class GuideApi {

        /**
         * 绑定导购
         *
         * @param guideAccount  导购微信号
         * @param guideNickname 导购昵称
         */
        public R addGuideAccount(@NonNull String guideAccount, String guideNickname) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/addguideacct?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("guide_account", guideAccount);
            if (StringUtils.isNotEmpty(guideNickname)) {
                jsonObject.put("guide_nickname", guideNickname);
            }
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

        /**
         * 更新导购
         *
         * @param guideAccount  导购微信号
         * @param guideNickname 导购昵称
         */
        public R updateGuideAccount(@NonNull String guideAccount, String guideNickname) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/updateguideacct?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("guide_account", guideAccount);
            if (StringUtils.isNotEmpty(guideNickname)) {
                jsonObject.put("guide_nickname", guideNickname);
            }
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

        /**
         * 删除导购
         *
         * @param guideAccount 导购微信号
         */
        public R deleteGuideAccount(@NonNull String guideAccount) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/delguideacct?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("guide_account", guideAccount);
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

        /**
         * 拉取导购列表
         *
         * @param page 分页页数，从 0 开始
         * @param num  每页数量
         */
        public R getGuideAccountList(Integer page, Integer num) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/getguideacctlist?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("page", page == null ? 0 : page);
            jsonObject.put("num", num == null ? 10 : num);
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

        /**
         * 拉取单个导购
         *
         * @param guideAccount 导购微信号
         */
        public R getGuideAccount(@NonNull String guideAccount) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/getguideacct?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("guide_account", guideAccount);
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

        /**
         * 生成导购二维码
         *
         * @param guideAccount 导购微信号
         * @param qrcodeInfo   二维码额外参数
         */
        public R createGuideQrcode(@NonNull String guideAccount, String qrcodeInfo) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/guidecreateqrcode?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("guide_account", guideAccount);
            jsonObject.put("qrcode_info", qrcodeInfo);
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

        /**
         * 导购聊天记录获取（暂不可用）
         *
         * @param guideAccount 导购微信号
         * @param openid       粉丝 openid
         * @param page         分页页数，从 0 开始
         * @param num          每页数量
         */
        public R getGuideBuyerChatRecord(@NonNull String guideAccount, @NonNull String openid, Integer page, Integer num) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/getguidebuyerchatrecord?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("guide_account", guideAccount);
            jsonObject.put("openid", openid);
            jsonObject.put("page", page == null ? 0 : page);
            jsonObject.put("num", num == null ? 10 : num);
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

    }

    /**
     * 粉丝管理 API
     */
    class BuyerApi {

        /**
         * 绑定粉丝导购关系
         *
         * @param guideAccount  导购微信号
         * @param openid        粉丝 openid
         * @param buyerNickname 粉丝昵称
         */
        public R addGuideBuyerRelation(@NonNull String guideAccount, @NonNull String openid, String buyerNickname) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/addguidebuyerrelation?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("guide_account", guideAccount);
            jsonObject.put("openid", openid);
            if (StringUtils.isNotEmpty(buyerNickname)) {
                jsonObject.put("buyer_nickname", buyerNickname);
            }
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

        /**
         * 更新粉丝导购关系
         *
         * @param guideAccount  导购微信号
         * @param openid        粉丝 openid
         * @param buyerNickname 粉丝昵称
         */
        public R updateGuideBuyerRelation(@NonNull String guideAccount, @NonNull String openid, String buyerNickname) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/updateguidebuyerrelation?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("guide_account", guideAccount);
            jsonObject.put("openid", openid);
            if (StringUtils.isNotEmpty(buyerNickname)) {
                jsonObject.put("buyer_nickname", buyerNickname);
            }
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

        /**
         * 删除粉丝导购关系
         *
         * @param guideAccount 导购微信号
         * @param openid       粉丝 openid
         */
        public R deleteGuideBuyerRelation(@NonNull String guideAccount, @NonNull String openid) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/delguidebuyerrelation?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("guide_account", guideAccount);
            jsonObject.put("openid", openid);
            return R.success(HttpUtils.doPost(url, jsonObject));
        }


        /**
         * 拉取粉丝导购关系对列表
         *
         * @param guideAccount 导购微信号
         * @param page         当前页
         * @param num          每页现实条数
         */
        public R getGuideBuyerRelationList(@NonNull String guideAccount, Integer page, Integer num) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/getguidebuyerrelationlist?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("guide_account", guideAccount);
            jsonObject.put("page", page == null ? 0 : page);
            jsonObject.put("num", num == null ? 10 : num);
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

        /**
         * 拉取单个粉丝导购关系
         *
         * @param guideAccount 导购微信号
         * @param openid       粉丝 openid
         */
        public R getGuideBuyerRelation(@NonNull String guideAccount, @NonNull String openid) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/getguidebuyerrelation?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("guide_account", guideAccount);
            jsonObject.put("openid", openid);
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

        /**
         * 拉取粉丝对应的导购
         *
         * @param openid 粉丝 openid
         */
        public R getGuideBuyerRelationByBuyer(@NonNull String openid) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/getguidebuyerrelationbybuyer?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("openid", openid);
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

    }

    /**
     * 标签管理 API
     */
    class TagApi {

        /**
         * 新建标签类别
         *
         * @param tagName   标签类型名字
         * @param tagValues 标签可选值
         */
        public R newGuideTagOption(@NonNull String tagName, String... tagValues) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/newguidetagoption?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tag_name", tagName);
            if (tagValues != null && tagValues.length > 0) {
                jsonObject.put("tag_values", tagValues);
            }
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

        /**
         * 添加标签可选值
         *
         * @param tagName   标签类型名字
         * @param tagValues 标签可选值
         */
        public R addGuideTagOption(@NonNull String tagName, @NonNull String... tagValues) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/addguidetagoption?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tag_name", tagName);
            jsonObject.put("tag_values", tagValues);
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

        /**
         * 查询标签可选值信息
         */
        public R getGuideTagOption() throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/getguidetagoption?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            return R.success(HttpUtils.doPost(url));
        }


        /**
         * 置粉丝标签
         *
         * @param guideAccount 导购微信号
         * @param openid       粉丝 openid
         * @param tagValue     标签值（该值必须在可选标签值集合中）
         */
        public R addGuideBuyerTag(@NonNull String guideAccount, @NonNull String openid, @NonNull String tagValue) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/addguidebuyertag?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("guide_account", guideAccount);
            jsonObject.put("openid", openid);
            jsonObject.put("tag_value", tagValue);
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

        /**
         * 删除粉丝标签
         *
         * @param guideAccount 导购微信号
         * @param openid       粉丝 openid
         * @param tagValue     标签值
         */
        public R deleteGuideBuyerTag(@NonNull String guideAccount, @NonNull String openid, @NonNull String tagValue) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/delguidebuyertag?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("guide_account", guideAccount);
            jsonObject.put("openid", openid);
            jsonObject.put("tag_value", tagValue);
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

        /**
         * 根据标签值筛选粉丝
         *
         * @param guideAccount 导购微信号
         * @param pushCount    已推送次数
         * @param tagValues    标签值（该值必须在可选标签值集合中）
         */
        public R queryGuideBuyerByTag(@NonNull String guideAccount, Integer pushCount, String... tagValues) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/queryguidebuyerbytag?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("guide_account", guideAccount);
            if (pushCount != null) {
                jsonObject.put("pushCount", pushCount);
            }
            if (tagValues != null && tagValues.length > 0) {
                jsonObject.put("tag_values", tagValues);
            }
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

        /**
         * 查询粉丝标签
         *
         * @param guideAccount 导购微信号
         * @param openid       粉丝 openid
         */
        public R getGuideBuyerTag(@NonNull String guideAccount, @NonNull String openid) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/getguidebuyertag?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("guide_account", guideAccount);
            jsonObject.put("openid", openid);
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

        /**
         * 查询展示标签信息
         *
         * @param guideAccount 导购微信号
         * @param openid       粉丝 openid
         */
        public R getGuideBuyerDisplayTag(@NonNull String guideAccount, @NonNull String openid) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/getguidebuyerdisplaytag?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("guide_account", guideAccount);
            jsonObject.put("openid", openid);
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

        /**
         * 设置展示标签信息
         *
         * @param guideAccount 导购微信号
         * @param openid       粉丝 openid
         * @param displayTags  展示标签值（全量更新，每次传全量的信息）
         */
        public R getGuideBuyerDisplayTag(@NonNull String guideAccount, @NonNull String openid, @NonNull String... displayTags) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/getguidebuyerdisplaytag?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("guide_account", guideAccount);
            jsonObject.put("openid", openid);
            jsonObject.put("display_tag_list", displayTags);
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

    }

    /**
     * 账号属性 API
     */
    class AccountApi {

        /**
         * 设置账号相关属性（敏感词等）
         *
         * @param isDelete       操作类型：false 表示添加, true 表示删除
         * @param blackKeywords  敏感词：添加会覆盖原来数据
         * @param guideAutoReply 自动回复
         */
        public R setGuideAcctConfig(@NonNull String isDelete, String[] blackKeywords, String guideAutoReply) throws NullParameterException {
            String url = "https://api.weixin.qq.com/cgi-bin/guide/setGuideAcctConfig?access_token=%s";
            url = String.format(url, getAloneOrNotAccessToken());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("is_delete", isDelete);
            if (blackKeywords != null && blackKeywords.length > 0) {
                Map<String, Object> values = new HashMap<>();
                values.put("values", blackKeywords);
                jsonObject.put("black_keyword", values);
            }
            if (guideAutoReply != null) {
                jsonObject.put("guide_auto_reply", guideAutoReply);
            }
            return R.success(HttpUtils.doPost(url, jsonObject));
        }

    }


    /**
     * 如果 Guide 作为独立模块使用的话，需要重写改方法
     */
    public String getAloneOrNotAccessToken() throws NullParameterException {
        return getAccessToken();
    }
}
