package javax.zxiu.comic.tasks;

import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.TextUtils;

import javax.zxiu.comic.Config;
import javax.zxiu.comic.beans.AllComics;
import javax.zxiu.comic.beans.Book;
import javax.zxiu.comic.beans.Comic;
import javax.zxiu.comic.beans.Page;
import javax.zxiu.comic.utils.IOUtils;
import javax.zxiu.comic.utils.JSUtils;
import javax.zxiu.comic.utils.NetworkUtils;
import javax.zxiu.comic.utils.ParseUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zhuo Xiu on 07/08/15.
 */
public class DownloadTask {

    public static Book parseBook(Comic comic, int index) {

        Book book = comic.getBooks()[index];
        CountDownLatch countDownLatch=new CountDownLatch(book.getLast_page());


        String[] paths = book.getUrl().split("/");
        List<Page> pageList=new ArrayList<>();
        for (int i = 1; i <= book.getLast_page(); i++) {
            String url = paths[0] + "//" + paths[2] + "/" + paths[3] + "p" + i + "/chapterimagefun.ashx?cid=" + paths[3].substring(1, paths[3].length()) + "&page=" + i + "&language=1&key=";
//            System.out.println(url);
            final int finalI = i;
            NetworkUtils.request(NetworkUtils.METHOD.GET, url, new Header[]{new BasicHeader("Referer", book.getUrl())}, new FutureCallback<HttpResponse>() {
                @Override
                public void completed(HttpResponse result) {
                    try {
                        String url = JSUtils.unpack(IOUtils.InputStreamToString(result.getEntity().getContent()));
                        Page page=new Page();
                        page.setUrl(url);
                        page.setIndex(finalI);
                        pageList.add(page);
                        countDownLatch.countDown();
                        System.out.println(url);
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
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        book.setPages(pageList.toArray(book.getPages()));


        System.err.println(book.toString());
        return null;
    }

    public static Comic parseComic(AllComics allComics, int index) {
        Comic comic = allComics.getComics()[index];
        final String reg0 = "<ul\\sid=\"cbc_\\d\"[\\s\\S]*?</ul>";
        final String reg1 = "<li[\\s\\S]*?</li>";
        final String reg2 = "<a[\\s\\S]*?</a>";
        final String reg3 = "\\d+页";
        final String reg4 = "\\d+页";

        final Pattern pattern0 = Pattern.compile(reg0);
        final Pattern pattern1 = Pattern.compile(reg1);
        final Pattern pattern2 = Pattern.compile(reg2);
        final Pattern pattern3 = Pattern.compile(reg3);
        final Pattern pattern4 = Pattern.compile(reg4);

        String html = getDummyHtml();
        Matcher matcher0 = pattern0.matcher(html);
        List<Book> bookList = new ArrayList<>();
        while (matcher0.find()) {
            String ulText = html.substring(matcher0.start(), matcher0.end());
            Matcher matcher1 = pattern1.matcher(ulText);
            while (matcher1.find()) {
                String liText = ulText.substring(matcher1.start(), matcher1.end());
                int last_page = 0;
                String name = null;
                String url = null;
                Matcher matcher2 = pattern2.matcher(liText);
                if (matcher2.find()) {
                    String aText = liText.substring(matcher2.start(), matcher2.end());
                    name = ParseUtils.matchAttr(aText, "a", "title");
                    url = comic.getHost() + ParseUtils.matchAttr(aText, "a", "href");
                }

                Matcher matcher3 = pattern3.matcher(liText);
                if (matcher3.find()) {
                    Matcher matcher4 = pattern4.matcher(liText);
                    String pageText = liText.substring(matcher3.start(), matcher3.end());
//                    Integer.parseInt(pageText.substring(0,pageText.length()-1));

//                    System.out.println(pageText.substring(0, pageText.length() - 1));
                    last_page = Integer.parseInt(pageText.substring(0, pageText.length() - 1));

                }
                if (last_page != 0 && !TextUtils.isBlank(name) && !TextUtils.isBlank(url)) {
                    Book book = new Book();
                    book.setName(name);
                    book.setUrl(url);
                    book.setLast_count(last_page);
                    bookList.add(book);
                }


            }
        }
        comic.setBooks(bookList.toArray(comic.getBooks()));
//        System.err.println(JSON.toJSONString(comic, true));
        return allComics.getComics()[index];
    }

    public static AllComics parseInput() {
        String data = IOUtils.readFromFile(DownloadTask.class.getClassLoader().getResource("comic-in.json").getFile());
        AllComics allComics = JSON.parseObject(data, AllComics.class);
        System.err.println(JSON.toJSONString(allComics, true));
        return allComics;
    }

    public static String getDummyHtml() {
        String html = IOUtils.readFromFile(new File(Config.dummyFileName));
        return html;
    }


}
