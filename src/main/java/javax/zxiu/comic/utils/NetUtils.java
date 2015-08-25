package javax.zxiu.comic.utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.CloseableHttpPipeliningClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;


/**
 * Created by Zhuo Xiu on 07/08/15.
 */
public class NetUtils {
    static ConnectingIOReactor ioReactor;
    static PoolingNHttpClientConnectionManager connectionManager;
    static CloseableHttpAsyncClient httpAsyncClient;
    static CloseableHttpPipeliningClient httpPipeliningClient;

    static {
        try {
            ioReactor = new DefaultConnectingIOReactor();
            connectionManager = new PoolingNHttpClientConnectionManager(ioReactor);
            connectionManager.setMaxTotal(100);
            httpAsyncClient = HttpAsyncClients.custom().setConnectionManager(connectionManager).build();
            httpAsyncClient.start();
        } catch (IOReactorException e) {
            e.printStackTrace();
        }
    }

    public static void get(String url, Header[] headers, FutureCallback<HttpResponse> callback) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeaders(headers);
        httpAsyncClient.execute(httpGet, callback);
    }

    public static void post(String url, Header[] headers, HttpEntity entity, FutureCallback<HttpResponse> callback) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeaders(headers);
        httpPost.setEntity(entity);
        httpAsyncClient.execute(httpPost, callback);
    }

    public static String post(String url, Header[] headers, HttpEntity entity) {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeaders(headers);
        httpPost.setEntity(entity);
        StringBuffer stringBuffer = new StringBuffer();
        httpAsyncClient.execute(httpPost, new FutureCallback<HttpResponse>() {

            @Override
            public void completed(HttpResponse result) {
                try {
                    stringBuffer.append(IOUtils.inputStreamToString(result.getEntity().getContent()));
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }

            @Override
            public void failed(Exception ex) {
                stringBuffer.append(ex.getMessage());
                countDownLatch.countDown();
            }

            @Override
            public void cancelled() {
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }


    public static void download(String url, Header[] headers, String filePath) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        File file = new File(filePath);
        get(url, headers, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse result) {
                try {
                    System.out.println("writting file " + file);
                    IOUtils.writeToFile(result.getEntity().getContent(), file);
                } catch (IOException ex) {
                    failed(ex);
                }
                countDownLatch.countDown();
            }

            @Override
            public void failed(Exception ex) {
                ex.printStackTrace();
                countDownLatch.countDown();
            }

            @Override
            public void cancelled() {
                System.out.println("cancelled");
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
