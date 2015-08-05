package javax.zxiu.comic.beans;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Zhuo Xiu on 04/08/15.
 */
public class Comic {
    private String name;
    private String host;
    private String url;
    private String pattern;
    private Date download_date;
    private Date upload_date;
    private boolean finished;
    private Book[] books = new Book[0];

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
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

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Book[] getBooks() {
        return books;
    }

    public void setBooks(Book[] books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "name='" + name + '\'' +
                ", host='" + host + '\'' +
                ", url='" + url + '\'' +
                ", pattern='" + pattern + '\'' +
                ", download_date=" + download_date +
                ", upload_date=" + upload_date +
                ", finished=" + finished +
                ", books=" + Arrays.toString(books) +
                '}';
    }
}
