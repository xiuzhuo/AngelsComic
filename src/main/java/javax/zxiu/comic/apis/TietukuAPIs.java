package javax.zxiu.comic.apis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.zxiu.comic.beans.Image;
import javax.zxiu.comic.utils.NetUtils;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by Xiu on 2015/8/25.
 */
public class TietukuAPIs extends UploadAPIs {
    static TietukuAPIs tietukuAPI = new TietukuAPIs();
    static final String AccessKey = "ef3fc34e1b1f39d308d52f4deed3e98dd3df5234";
    static final String SecretKey = "da39a3ee5e6b4b0d3255bfef95601890afd80709";
    static final String OpenKey = "nZuZy8VkZpqVyJSWlGnIlWBuymdkyZaYnJiaZpdrb5ycaMrLl2NlacOblGWaYZw=";
    static final int TIMEOUT = 60;
    static final String PARAM_DEADLINE = "deadline";
    static final String PARAM_ACTION = "action";
    static final String PARAM_ALBUMNAME = "albumname";

    static final String ACTION_CREATE = "create";

    static final String PARAM_TOKEN = "Token";


    static final String API_ALBUM = "http://api.tietuku.com/v1/Album";


    public static TietukuAPIs getInstance() {
        return tietukuAPI;
    }

    public static String base64(byte[] target) {
        return Base64.encodeBase64URLSafeString(target).trim();
    }

    public static String hmac_sha1(String value, String key) {
        try {
            byte[] keyBytes = key.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(value.getBytes());
            return new String(rawHmac).trim();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String getToken(Param... params) {
        JSONObject jsonObject = new JSONObject();
        for (Param param : params) {
            jsonObject.put(param.getName(), param.getValue());
        }
        String jsoncode = jsonObject.toJSONString();
        System.out.println(jsoncode);
        String base64param = base64(jsoncode.getBytes());
        String encodedSign = base64(hmac_sha1(base64param, SecretKey).getBytes());
        String token = AccessKey + ":" + encodedSign + ":" + base64param;
        System.out.println(token);
        return token;
    }

    @Override
    public Image upload(File file) {
        return null;
    }

    public void createAlbum(String albumName) {
        String token = getToken(new Param(PARAM_DEADLINE, getDeadline()), new Param(PARAM_ACTION, ACTION_CREATE), new Param(PARAM_ALBUMNAME, albumName));
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put(PARAM_TOKEN, token);
        try {
            EntityBuilder builder = EntityBuilder.create();
            builder.setParameters(new BasicNameValuePair(PARAM_TOKEN, token));


            String result = NetUtils.post(API_ALBUM, new Header[]{}, builder.build());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static long getDeadline() {
        return new Date().getTime() / 1000 + TIMEOUT;
    }

}
