package javax.zxiu.comic.apis;

import com.alibaba.fastjson.JSONObject;

import javax.zxiu.comic.beans.Image;
import java.io.File;
import java.util.Base64;

/**
 * Created by Xiu on 2015/8/25.
 */
public class TietukuAPI extends UploadAPI {
    static TietukuAPI tietukuAPI = new TietukuAPI();
    static final String AccessKey = "ef3fc34e1b1f39d308d52f4deed3e98dd3df5234";
    static final String SecretKey = "da39a3ee5e6b4b0d3255bfef95601890afd80709";
    static final String OpenKey = "nZuZy8VkZpqVyJSWlGnIlWBuymdkyZaYnJiaZpdrb5ycaMrLl2NlacOblGWaYZw=";

    public TietukuAPI getInstance() {
        return tietukuAPI;
    }

    @Override
    protected String getToken(Param... params) {
        JSONObject jsonObject = new JSONObject();
        for (Param param : params) {
            jsonObject.put(param.getName(), param.getValue());
        }
        String base64param = Base64.getEncoder().encodeToString(jsonObject.toJSONString().getBytes());
        String asign = Base64.getEncoder().encodeToString(Hmac.SHA1.getDigest(base64param, SecretKey).getBytes());
        String token = AccessKey + ":" + asign + ":" + base64param;
        return token;
    }

    @Override
    public Image upload(File file) {
        return null;
    }

    public void createAlbum(String albumName) {

    }
}
