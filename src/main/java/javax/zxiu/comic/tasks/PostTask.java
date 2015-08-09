package javax.zxiu.comic.tasks;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import sun.rmi.runtime.RuntimeUtil;

import javax.zxiu.comic.utils.NetworkUtils;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxiu on 07.08.15.
 */
public class PostTask {
    private static final String API = "http://site.6park.com/life8/index.php?app=forum&act=dopost";

    private static String username;
    private static String password;

    private static String origin = "http://site.6park.com";
    private static String referer = "http://site.6park.com/life8/index.php?app=forum&act=threadview&tid=13803578";
    private static String content_type = "application/x-www-form-urlencoded";

    public static void setUsername(String username) {
        PostTask.username = username;
    }

    public static void setPassword(String password) {
        PostTask.password = password;
    }

    public static void setReferer(String referer) {
        PostTask.referer = referer;
    }

    public static void post(String subject, String text) {
        Header[] headers = new Header[]{
                new BasicHeader(" ", " ")
        };
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("subject", subject));
        urlParameters.add(new BasicNameValuePair("text", text));
        UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(urlParameters, "utf-8");
            NetworkUtils.post(API, headers, entity, new FutureCallback<HttpResponse>() {
                @Override
                public void completed(HttpResponse result) {

                }

                @Override
                public void failed(Exception ex) {

                }

                @Override
                public void cancelled() {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
}
