package javax.zxiu.comic.utils;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.NHttpConnection;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.protocol.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by Zhuo Xiu on 07/08/15.
 */
public class NetworkUtils {
    public enum METHOD {
        GET, POST, DELETE, UPDATE;
    }
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

    public static void request(METHOD method, String url, Header[] headers, FutureCallback<HttpResponse> callback) {
        HttpUriRequest request = null;
        switch (method) {
            case GET:
                request = new HttpGet(url);
                break;
            case POST:
                request = new HttpPost(url);
                break;
        }
        if (request != null) {
            request.setHeaders(headers);
            httpAsyncClient.execute(request, callback);
        }
    }

    public static void download(String url, Header[] headers, String filePath) {
        File file = new File(filePath);
        request(METHOD.GET, url, headers, new FutureCallback<HttpResponse>() {
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
