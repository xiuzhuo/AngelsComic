package javax.zxiu.comic.beans;

import java.util.Date;

/**
 * Created by Zhuo Xiu on 04/08/15.
 */
public class Page {
    private int index;
    private String url;
    private String image_url;
    private String file_path;
    private Date download_date;
    private Date upload_date;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
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

    @Override
    public String toString() {
        return "Page{" +
                "index=" + index +
                ", url='" + url + '\'' +
                ", image_url='" + image_url + '\'' +
                ", file_path='" + file_path + '\'' +
                ", download_date=" + download_date +
                ", upload_date=" + upload_date +
                '}';
    }
}
