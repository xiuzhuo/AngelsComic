package javax.zxiu.comic;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicHeader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.zxiu.comic.utils.IOUtils;
import javax.zxiu.comic.utils.NetUtils;
import javax.zxiu.comic.utils.ParseUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
        String url = "http://manhua1025.146-71-123-50.cdndm5.com/17/16656/216286/1_4828.jpg?cid=216286&key=8a7242c45e67ec73f81b1cda91662ed1";
        NetUtils.download(url, new Header[]{new BasicHeader("Referer", "http://www.dm5.com/m216286/"), new BasicHeader("Accept", "image/webp,*/*;q=0.8")}, "2_3997_3_4.jpg");
        if (true) {
            return;
        }
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

    public static void testGetComicInformation() {
        int pages = 20;
        String url1 = "http://www.dm5.com/m216286/";
        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
        client.start();
        final HttpGet request = new HttpGet(url1);
        Future<HttpResponse> future = client.execute(request, null);
        try {
            HttpResponse response = future.get();
            System.err.println(IOUtils.writeToString(response.getEntity().getContent()));
            for (int i = 1; i < pages; i++) {
                String url2 = "http://www.dm5.com/m216286p" + i + "/chapterimagefun.ashx?cid=216286&page=" + i +
                        "&language=1&key=";
                System.out.println(url2);
                HttpGet request2 = new HttpGet(url2);
//                request2.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:39.0) Gecko/20100101 Firefox/39.0");
                request2.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.130 Safari/537.36");
                System.out.println(request2.getAllHeaders().length);
                Future<HttpResponse> future2 = client.execute(request2, null);
                HttpResponse response2 = future2.get();
                System.out.println(IOUtils.writeToString(response2.getEntity().getContent()));
            }

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
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
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

    public static void testJS() {
//        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
//        try {
//            engine.eval("print('Hello World!');");
//        } catch (ScriptException e) {
//            e.printStackTrace();
//        }

        String code = "eval(function(p,a,c,k,e,d){e=function(c){return(c<a?\"\":e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)d[e(c)]=k[c]||e(c);k=[function(e){return d[e]}];e=function(){return'\\\\w+'};c=1;};while(c--)if(k[c])p=p.replace(new RegExp('\\\\b'+e(c)+'\\\\b','g'),k[c]);return p;}('n 8(){2 6=4;2 7=\\'9\\';2 5=\"o://l.m-p-s-t.q.r/c/e/4\";2 3=[\"/b.1\",\"/a.1\",\"/j.1\",\"/k.1\",\"/f.1\",\"/g.1\",\"/h.1\",\"/u.1\",\"/F.1\",\"/E.1\",\"/D.1\",\"/G.1\",\"/J.1\",\"/H.1\",\"/I.1\",\"/C.1\",\"/x.1\",\"/w.1\",\"/v.1\",\"/y.1\"];B(2 i=0;i<3.A;i++){3[i]=5+3[i]+\\'?6=4&7=9\\'}z 3}2 d;d=8();',46,46,'|jpg|var|pvalue|154246|pix|cid|key|dm5imagefun|60f01803fa98cf784d8f55d6fea87160|4_4585|3_9610|15||14828|7_9559|8_2855|9_5479||5_2020|6_3359|manhua1023|107|function|http|181|cdndm5|com|243|170|10_8380|21_1568|20_9070|19_7299|22_6367|return|length|for|18_1708|13_9570|12_5230|11_3509|14_3537|16_4567|17_5508|15_9889'.split('|'),0,{}))";

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
//        System.out.println(Dummy.class.getClassLoader().getResource("sample.fxml").toExternalForm());
        File file = new File(Dummy.class.getClassLoader().getResource("unpack.js").getPath());
        System.out.println("file=" + file + " " + file.exists());
        try {
            engine.eval(new FileReader(file));
            Invocable invocable = (Invocable) engine;
            Object result = invocable.invokeFunction("unpack", code);

            engine.eval((String) result);
            result = invocable.invokeFunction("dm5imagefun");
            System.out.println(result);
            System.out.println(result.getClass() + " " + ((ScriptObjectMirror) result).getSlot(0));
//            for (Object r:(Object[])result){
//                System.out.println(r);
//            }
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


    }
}
