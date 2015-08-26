package javax.zxiu.comic.apis;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.message.BasicNameValuePair;

import javax.zxiu.comic.utils.NetUtils;
import java.io.File;
import java.util.Date;

/**
 * Created by Xiu on 2015/8/25.
 */
public class TietukuAPI extends BaseUploadAPI {
    static TietukuAPI tietukuAPI = new TietukuAPI();
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


    public static class AlbumList {
        private String name;
        private int total;
        private Album[] album;
    }

    public static class Album {
        private long aid;
        private String albumname;
        private int num;
        private int code;
        private Pic[] pic;
    }

    public static class Pic {
        private long id;
        private String url;
    }

    public static TietukuAPI getInstance() {
        return tietukuAPI;
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
    public Pic upload(File file) {
        return null;
    }

    public void getAlbumList(){
        
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
