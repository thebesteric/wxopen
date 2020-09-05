package org.wesoft.wechat.wxopen.client.applet;

import com.alibaba.fastjson.JSONObject;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wesoft.common.utils.StringUtils;
import org.wesoft.common.utils.web.HttpUtils;
import org.wesoft.wechat.wxopen.cache.LocalCache;
import org.wesoft.wechat.wxopen.constant.WechatConstant;
import org.wesoft.wechat.wxopen.exception.NullParameterException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;

/**
 * 小程序助手支持
 *
 * @author Eric Joe
 * @version Ver 1.0
 * @build 2020-05-11 20:55
 */
public class AppletHelperSupport {

    private static final Logger logger = LoggerFactory.getLogger(AppletHelperSupport.class);

    private static final String KEY_ALGORITHM = "AES";
    private static final String ALGORITHM_STR = "AES/CBC/PKCS7Padding";
    private Cipher cipher;

    /**
     * 获取 AccessToken
     *
     * @return String
     */
    public String getAccessToken(String appID, String appSecret) throws NullParameterException {
        String accessToken = (String) LocalCache.getInstance().get(WechatConstant.APPLET_ACCESS_TOKEN_PREFIX + appID);
        if (StringUtils.isEmpty(accessToken)) {
            if (StringUtils.isEmpty(appID) || StringUtils.isEmpty(appSecret)) {
                throw new NullParameterException("AppID and AppSecret must be not null");
            }
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
            url = String.format(url, appID, appSecret);
            JSONObject ret = HttpUtils.doGet(url);
            if (ret != null) {
                logger.info(WechatConstant.APPLET_ACCESS_TOKEN_PREFIX + appID + "_" + ret.toString());
                accessToken = ret.getString("access_token");
                LocalCache.getInstance().put(WechatConstant.APPLET_ACCESS_TOKEN_PREFIX + appID, accessToken, ret.getLong(WechatConstant.EXPIRES_IN));
            }
        }
        return accessToken;
    }

    /**
     * 数据解密
     *
     * @param encryptData 加密数据
     * @param sessionKey  sessionKey
     * @param iv          iv
     */
    public JSONObject decryptData(String encryptData, String sessionKey, String iv) {
        byte[] decrypt = decrypt(Base64.decode(encryptData), Base64.decode(sessionKey), Base64.decode(iv));
        return JSONObject.parseObject(new String(decrypt));
    }

    /**
     * 解密方法
     *
     * @param encryptedData 加密数据
     * @param keyBytes      解密密钥
     * @param ivs           iv
     */
    private byte[] decrypt(byte[] encryptedData, byte[] keyBytes, byte[] ivs) {
        byte[] encryptedText = null;

        int base = 16;
        if (keyBytes.length % base != 0) {
            // int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            int groups = keyBytes.length / base + 1;
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        Security.addProvider(new BouncyCastleProvider());
        Key key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        try {
            // 初始化cipher
            if (cipher == null) {
                cipher = Cipher.getInstance(ALGORITHM_STR, "BC");
            }
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivs));
            encryptedText = cipher.doFinal(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encryptedText;
    }

}
