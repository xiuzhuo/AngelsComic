package javax.zxiu.comic.tasks;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xml.internal.utils.URI;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.TextUtils;

import javax.zxiu.comic.beans.Library;
import javax.zxiu.comic.beans.Book;
import javax.zxiu.comic.beans.Comic;
import javax.zxiu.comic.beans.Page;
import javax.zxiu.comic.utils.IOUtils;
import javax.zxiu.comic.utils.JSUtils;
import javax.zxiu.comic.utils.NetworkUtils;
import javax.zxiu.comic.utils.ParseUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zhuo Xiu on 07/08/15.
 */
public class DownloadTask {

    public static Comic parseAllBooks(Comic comic) {
        CountDownLatch countDownLatch = new CountDownLatch(comic.getBooks().length);
        for (int i = 0; i < comic.getBooks().length; i++) {
            parseBook(comic, i);
            countDownLatch.countDown();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       return comic;
    }

    public static Comic parseBook(Comic comic, int index) {
        Book book = comic.getBooks()[index];
        CountDownLatch countDownLatch = new CountDownLatch(book.getLast_page());


        String[] paths = book.getUrl().split("/");
        List<Page> pageList = new ArrayList<>();
        for (int i = 1; i <= book.getLast_page(); i++) {
            String url = paths[0] + "//" + paths[2] + "/" + paths[3] + "p" + i + "/chapterimagefun.ashx?cid=" + paths[3].substring(1, paths[3].length()) + "&page=" + i + "&language=1&key=";
            System.out.println(url);
            final int finalI = i;
            NetworkUtils.get(url, new Header[]{new BasicHeader("Referer", book.getUrl())}, new FutureCallback<HttpResponse>() {
                @Override
                public void completed(HttpResponse result) {
                    try {
                        String image_download_url = JSUtils.unpack(IOUtils.inputStreamToString(result.getEntity().getContent()));
                        Page page = new Page();
                        page.setImageDownloadUrl(image_download_url);
                        page.setIndex(finalI);
                        pageList.add(page);
                        System.out.println(image_download_url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        countDownLatch.countDown();
                    }
                }

                @Override
                public void failed(Exception ex) {

                }

                @Override
                public void cancelled() {

                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        book.setPages(pageList.toArray(book.getPages()));
        Arrays.sort(book.getPages());
//        System.err.println(JSON.toJSONString(JSON.toJSON(comic), true));
        return comic;
    }

    static String getDownloadFileName(Comic comic,Book book,Page page){
        return "download/"+comic.getTitle()+"/"+book.getTitle()+"/"+page.getIndex()+".jpg";
    }

    public static void downloadComic(Comic comic) {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        for (Book book : comic.getBooks()) {
            for (Page page : book.getPages()) {
                if (page.getImageDownloadUrl()!=null){
                    String fileName=getDownloadFileName(comic,book,page);
                    System.err.println("prepare to download "+comic.getTitle()+" "+book.getTitle()+" "+fileName);
                    NetworkUtils.download(page.getImageDownloadUrl(), new Header[]{new BasicHeader("Referer", page.getImageDownloadUrl())}, fileName);
                    page.setFilePath(fileName);
                }
            }
        }
        countDownLatch.countDown();
        System.err.println(JSON.toJSONString(JSON.toJSON(comic), true));
        try{
            countDownLatch.await();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void downloadPage(Page page) {

    }

    public static Comic parseComic(Comic comic) {
        final String reg0 = "<ul[^<>]*?id=['\"]cbc_\\d*['\"][\\s\\S]*?</ul>";
        final String reg1 = "<li[\\s\\S]*?</li>";
        final String reg2 = "<a[\\s\\S]*?</a>";
        final String reg3 = "\\d+页";
        final String reg4 = "\\d+页";

        final Pattern pattern0 = Pattern.compile(reg0);
        final Pattern pattern1 = Pattern.compile(reg1);
        final Pattern pattern2 = Pattern.compile(reg2);
        final Pattern pattern3 = Pattern.compile(reg3);
        final Pattern pattern4 = Pattern.compile(reg4);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        NetworkUtils.get(comic.getUrl(), null, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse result) {
                try {
                    String html = IOUtils.inputStreamToString(result.getEntity().getContent());
                    System.out.println(html);
                    Matcher matcher0 = pattern0.matcher(html);
                    List<Book> bookList = new ArrayList<>();
                    while (matcher0.find()) {
                        String ulText = html.substring(matcher0.start(), matcher0.end());
                        System.out.println(ulText);
                        Matcher matcher1 = pattern1.matcher(ulText);
                        int index = 0;
                        while (matcher1.find()) {
                            index++;
                            String liText = ulText.substring(matcher1.start(), matcher1.end());
                            int last_page = 0;
                            String title = null;
                            String url = null;
                            Matcher matcher2 = pattern2.matcher(liText);
                            if (matcher2.find()) {
                                String aText = liText.substring(matcher2.start(), matcher2.end());
                                System.out.println(aText);
                                Matcher matchTitle = Pattern.compile("title=\"[\\s\\S]*?\"").matcher(aText);
                                if (matchTitle.find()) {
//                                    title = aText.substring(matchTitle.start()+7,matchTitle.end()-1);
//                                    System.out.println("title="+title);
                                    title = ParseUtils.matchAttr(aText, "a", "title");
                                    System.out.println("title=" + title);
                                }
                                url = comic.getHost() + ParseUtils.matchAttr(aText, "a", "href");
                                System.out.println("url=" + url);
                            }

                            Matcher matcher3 = pattern3.matcher(liText);
                            if (matcher3.find()) {
                                Matcher matcher4 = pattern4.matcher(liText);
                                String pageText = liText.substring(matcher3.start(), matcher3.end());
                                last_page = Integer.parseInt(pageText.substring(0, pageText.length() - 1));
                            }
                            if (last_page != 0 && !TextUtils.isBlank(title) && !TextUtils.isBlank(url)) {
                                Book book = new Book();
                                book.setIndex(index);
                                book.setTitle(title);
                                book.setUrl(url);
                                book.setLast_count(last_page);
                                bookList.add(book);
                            }
                        }
                    }
                    comic.setBooks(bookList.toArray(comic.getBooks()));
                    Arrays.sort(comic.getBooks());
                    countDownLatch.countDown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Exception ex) {

            }

            @Override
            public void cancelled() {

            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println("haha");
        System.err.println(JSON.toJSONString(comic, true));
        parseAllBooks(comic);

        return comic;
    }

    public static Library parseInput() {
        String data = IOUtils.readFromFile(DownloadTask.class.getClassLoader().getResource("src.json").getFile());
        Library library = JSON.parseObject(data, Library.class);
        System.err.println(JSON.toJSONString(library, true));
        return library;
   }
}
