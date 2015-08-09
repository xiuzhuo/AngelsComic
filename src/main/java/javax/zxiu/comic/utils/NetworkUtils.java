package javax.zxiu.comic.utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;

import java.io.File;
import java.io.IOException;


/**
 * Created by Zhuo Xiu on 07/08/15.
 */
public class NetworkUtils {
    static ConnectingIOReactor ioReactor;
    static PoolingNHttpClientConnectionManager connectionManager;
    static CloseableHttpAsyncClient httpAsyncClient;

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
        httpAsyncClient.execute(httpGet,callback);
    }

    public static void post(String url, Header[] headers, HttpEntity entity,FutureCallback<HttpResponse> callback) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeaders(headers);
        httpPost.setEntity(entity);
        httpAsyncClient.execute(httpPost, callback);
    }

    public static void download(String url, Header[] headers, String filePath) {
        File file = new File(filePath);
        get(url, headers, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse result) {
                try {
                    IOUtils.writeToFile(result.getEntity().getContent(), file);
                } catch (IOException ex) {
                    failed(ex);
                }
            }

            @Override
            public void failed(Exception ex) {
                ex.printStackTrace();
            }

            @Override
            public void cancelled() {
                System.out.println("cancelled");
            }
        });
    }
    }
