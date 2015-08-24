package javax.zxiu.comic.tasks;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import javax.zxiu.comic.utils.IOUtils;
import javax.zxiu.comic.utils.NetUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zxiu on 07.08.15.
 */
public class PostTask {
    private static final String API = "http://site.6park.com/life8/index.php?app=forum&act=dopost";

    private static String username;
    private static String password;

    private static String origin = "http://site.6park.com";
    private static String referer = "http://site.6park.com/life8/index.php?app=forum&act=threadview&tid=13803586";
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

    public static void post(String subject, String content) {
        Header[] headers = new Header[]{
                new BasicHeader("Content-Type", content_type),
                new BasicHeader("Origin", origin),
                new BasicHeader("Referer", referer)
        };
        CountDownLatch countDownLatch=new CountDownLatch(1);
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

        UrlEncodedFormEntity entity = null;

        try {
//            urlParameters.add(new BasicNameValuePair("subject", new String(subject.getBytes(),"GB2312")));
//            urlParameters.add(new BasicNameValuePair("content", new String(content.getBytes(),"GB2312")));
            urlParameters.add(new BasicNameValuePair("subject",subject));
            urlParameters.add(new BasicNameValuePair("content", content));
            urlParameters.add(new BasicNameValuePair("username", "MiroslavKlose"));
            urlParameters.add(new BasicNameValuePair("password", "keluoze"));
            urlParameters.add(new BasicNameValuePair("uptid", "13803586"));
            urlParameters.add(new BasicNameValuePair("rootid", "13803586"));
            entity = new UrlEncodedFormEntity(urlParameters, "GB2312");
            NetUtils.post(API, headers, entity, new FutureCallback<HttpResponse>() {
                @Override
                public void completed(HttpResponse result) {
                    try {
                        System.out.println(IOUtils.inputStreamToString(result.getEntity().getContent()));
                        countDownLatch.countDown();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failed(Exception ex) {
                    ex.printStackTrace();
                    countDownLatch.countDown();
                }

                @Override
                public void cancelled() {
                    countDownLatch.countDown();
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }finally {

        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
