package javax.zxiu.comic.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.entity.NByteArrayEntity;

import javax.zxiu.comic.util.NetUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.Date;

/**
 * Created by Xiu on 2015/8/25.
 * http://open.tietuku.com/docv1
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
    static final String PARAM_AID = "aid";

    static final String PARAM_TOKEN = "Token";
    static final String HEADER_Content_Type = "Content-Type";

    static final String ACTION_CREATE = "create";
    static final String ACTION_GET = "get";

    static final String API_UPLOAD = "http://up.tietuku.com/";
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
        private int width, height, size;
        private String ubburl, htmlurl, linkurl, s_url, t_url;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getUbburl() {
            return ubburl;
        }

        public void setUbburl(String ubburl) {
            this.ubburl = ubburl;
        }

        public String getHtmlurl() {
            return htmlurl;
        }

        public void setHtmlurl(String htmlurl) {
            this.htmlurl = htmlurl;
        }

        public String getLinkurl() {
            return linkurl;
        }

        public void setLinkurl(String linkurl) {
            this.linkurl = linkurl;
        }

        public String getS_url() {
            return s_url;
        }

        public void setS_url(String s_url) {
            this.s_url = s_url;
        }

        public String getT_url() {
            return t_url;
        }

        public void setT_url(String t_url) {
            this.t_url = t_url;
        }
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


    public void getAlbums() {

    }


    public Pic uploadPic(long aid, File file) {
        String token = getToken(new Param(PARAM_DEADLINE, getDeadline()), new Param("from", "file"), new Param(PARAM_AID, aid));
        //token="ef3fc34e1b1f39d308d52f4deed3e98dd3df5234:YzdTY2VSMkcwUF9tU0xrTVAtU2NKZ1NJSnpVPQ==:eyJkZWFkbGluZSI6MTQ0MDY2ODk1OSwiYWN0aW9uIjoiZ2V0IiwidWlkIjoiNTE1OTE4IiwiYWlkIjoiMTEyNTUxNiIsImZyb20iOiJmaWxlIn0=";
        String boundary="----WebKitFormBoundarygSzncATl5myYPfIA";

        try {

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setContentType(ContentType.MULTIPART_FORM_DATA);
            builder.addTextBody(PARAM_TOKEN, token, ContentType.DEFAULT_TEXT);
            builder.addBinaryBody("file", file, ContentType.create("image/jpg"), file.getName());
            builder.setBoundary(boundary);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            builder.build().writeTo(baos);
            System.out.println("baos=\n" + baos.toString());
            NByteArrayEntity nByteArrayEntity = new NByteArrayEntity(baos.toByteArray());
            String result = NetUtils.post(API_UPLOAD, null, nByteArrayEntity);
            System.err.println(result);
            return JSON.parseObject(result, Pic.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public long createAlbum(String albumName) {
        String token = getToken(new Param(PARAM_DEADLINE, getDeadline()), new Param(PARAM_ACTION, ACTION_CREATE), new Param(PARAM_ALBUMNAME, albumName));
        try {
            EntityBuilder builder = EntityBuilder.create().setParameters(new BasicNameValuePair(PARAM_TOKEN, token));
            String result = NetUtils.post(API_ALBUM, new Header[]{}, builder.build());
            System.err.println(result);
            return JSON.parseObject(result).getLong("albumid");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean editAlbum(long aid, String albumname) {
        String token = getToken(new Param(PARAM_DEADLINE, getDeadline()), new Param(PARAM_ACTION, "editalbum"), new Param(PARAM_AID, aid), new Param("albumname", albumname));
        try {
            EntityBuilder builder = EntityBuilder.create().setParameters(new BasicNameValuePair(PARAM_TOKEN, token));
            String result = NetUtils.post(API_ALBUM, new Header[]{}, builder.build());
            System.err.println(result);
            return JSON.parseObject(result).getInteger("code") == 200;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean deleteAlbum(long aid) {
        String token = getToken(new Param(PARAM_DEADLINE, getDeadline()), new Param(PARAM_ACTION, "delalbum"), new Param(PARAM_AID, aid));
        try {
            EntityBuilder builder = EntityBuilder.create().setParameters(new BasicNameValuePair(PARAM_TOKEN, token));
            String result = NetUtils.post(API_ALBUM, new Header[]{}, builder.build());
            System.err.println(result);
            return JSON.parseObject(result).getInteger("code") == 200;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    static long getDeadline() {
        return new Date().getTime()/1000 + TIMEOUT;
    }

}
