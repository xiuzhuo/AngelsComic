package javax.zxiu.comic;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import javax.zxiu.comic.utils.IOUtils;
import javax.zxiu.comic.utils.ParseUtils;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Zhuo Xiu on 04/08/15.
 */
public class Dummy {

    public static final String cacheFolderPath = "cache";


    public static void testDownload() {
        String url = "http://manhua1025.104-250-152-74.cdndm5.com/17/16656/216286/2_3997.jpg?cid=216286&key=5235e87ad8b4d1900017e3492b30ff86";
        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
        System.out.println(System.getProperty("java.version"));
        File file = new File("2_3997_3_4.jpg");
        client.start();

        final HttpGet request = new HttpGet(url);
        request.addHeader("Accept", "image/webp,*/*;q=0.8");
        request.addHeader("Referer", "http://www.dm5.com/m216286/");
        Future<HttpResponse> future = client.execute(request, null);

        try {
            HttpResponse response = future.get();
            System.out.println(IOUtils.writeToFile(response.getEntity().getContent(), file) ? "SUCCESSFUL" : "FAILS");
        } catch (InterruptedException | ExecutionException | IOException | UnsupportedOperationException ex) {
            Logger.getLogger(ComicPoster.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(ComicPoster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void testConnect() {
        String url = "http://www.dm5.com/manhua-quanyuanaxiuluo/";
        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
        System.out.println(System.getProperty("java.version"));
        File file = new File("2_3997_3_4.jpg");
        client.start();

        final HttpGet request = new HttpGet(url);
        request.addHeader("Accept", "image/webp,*/*;q=0.8");
        request.addHeader("Referer", "http://www.dm5.com/m216286/");
        Future<HttpResponse> future = client.execute(request, null);

        try {
            HttpResponse response = future.get();
            System.out.println(IOUtils.writeToString(response.getEntity().getContent()));
        } catch (InterruptedException | ExecutionException | IOException | UnsupportedOperationException ex) {
            Logger.getLogger(ComicPoster.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(ComicPoster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void testReadInputFile() {
        ParseUtils.readInputFile();
    }

    public static void testLoadPage() {
            String url = "http://www.dm5.com/manhua-quanyuanaxiuluo/";
            WebView webView=new WebView();
            WebEngine webEngine=webView.getEngine();
            webEngine.load(url);
            String html = (String) webEngine.executeScript("document.documentElement.outerHTML");


            System.out.println(html);
        System.out.println(webEngine.getTitle());
//        new Thread(){
//            @Override
//            public void run() {
//                String url = "http://www.dm5.com/manhua-quanyuanaxiuluo/";
//                WebView webView=new WebView();
//                WebEngine webEngine=webView.getEngine();
//                webEngine.load(url);
//                String html = (String) webEngine.executeScript("document.documentElement.outerHTML");
//
//
//                System.out.println(html);
//            }
//        }.start();

    }
}
