package javax.zxiu.comic.beans;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Zhuo Xiu on 04/08/15.
 */

public class Book implements Comparable<Book> {
    private int index;
    private String title;
    private String url;
    private Date download_date;
    private Date upload_date;
    private int last_page;
    private Page[] pages = new Page[0];

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDownload_date() {
        return download_date;
    }

    public void setDownload_date(Date download_date) {
        this.download_date = download_date;
    }

    public Date getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(Date upload_date) {
        this.upload_date = upload_date;
    }

    public void setLast_count(int last_page) {
        this.last_page = last_page;
    }

    public Page[] getPages() {
        return pages;
    }

    public void setPages(Page[] pages) {
        this.pages = pages;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public int getLast_page() {
        return last_page;
    }

    @Override
    public String toString() {
        return "Book{" +
                "index=" + index +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", download_date=" + download_date +
                ", upload_date=" + upload_date +
                ", last_page=" + last_page +
                ", pages=" + Arrays.toString(pages) +
                '}';
    }


    @Override
    public int compareTo(Book book) {
        return index - book.index;
    }
}
