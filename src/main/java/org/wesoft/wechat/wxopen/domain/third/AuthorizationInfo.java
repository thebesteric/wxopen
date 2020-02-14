package org.wesoft.wechat.wxopen.domain.third;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AuthorizationInfo {

    private String authorizer_appid;
    private String authorizer_access_token;
    private String authorizer_refresh_token;
    private int expires_in;
    private FuncInfo func_info;

    @Data
    private static class FuncInfo {
        private List<FuncScopeCategory> funcScopeCategories = new ArrayList<>();

        @Data
        private static class FuncScopeCategory {
            /**
             * 1、消息管理权限
             * 2、用户管理权限
             * 3、帐号服务权限
             * 4、网页服务权限
             * 5、微信小店权限
             * 6、微信多客服权限
             * 7、群发与通知权限
             * 8、微信卡券权限
             * 9、微信扫一扫权限
             * 10、微信连WIFI权限
             * 11、素材管理权限
             * 12、微信摇周边权限
             * 13、微信门店权限
             * 15、自定义菜单权限
             * 16、获取认证状态及信息
             * 17、帐号管理权限（小程序）
             * 18、开发管理与数据分析权限（小程序）
             * 19、客服消息管理权限（小程序）
             * 20、微信登录权限（小程序）
             * 21、数据分析权限（小程序）
             * 22、城市服务接口权限
             * 23、广告管理权限
             * 24、开放平台帐号管理权限
             * 25、 开放平台帐号管理权限（小程序）
             * 26、微信电子发票权限
             * 41、搜索widget的权限，请注意： 1）该字段的返回不会考虑公众号是否具备该权限集的权限（因为可能部分具备），请根据公众号的帐号类型和认证情况，来判断公众号的接口权限。
             */
            private int id;
        }
    }

}
