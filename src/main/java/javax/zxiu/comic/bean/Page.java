package javax.zxiu.comic.bean;

import java.io.File;
import java.util.Date;

/**
 * Created by Zhuo Xiu on 04/08/15.
 */
public class Page implements Comparable<Page> {
    private int index;
    private String imageDownloadUrl;
    private String imageUploadUrl;
    private long imageSize;
    private File imageFile;
    private String filePath;
    private Date downloadDate;
    private Date uploadDate;

    @Override
    public String toString() {
        return "Page{" +
                "index=" + index +
                ", imageDownloadUrl='" + imageDownloadUrl + '\'' +
                ", imageUploadUrl='" + imageUploadUrl + '\'' +
                ", imageSize=" + imageSize +
                ", filePath='" + filePath + '\'' +
                ", downloadDate=" + downloadDate +
                ", uploadDate=" + uploadDate +
                '}';
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getImageDownloadUrl() {
        return imageDownloadUrl;
    }

    public void setImageDownloadUrl(String imageDownloadUrl) {
        this.imageDownloadUrl = imageDownloadUrl;
    }

    public String getImageUploadUrl() {
        return imageUploadUrl;
    }

    public void setImageUploadUrl(String imageUploadUrl) {
        this.imageUploadUrl = imageUploadUrl;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getImageSize() {
        return imageSize;
    }

    public void setImageSize(long imageSize) {
        this.imageSize = imageSize;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public Date getDownloadDate() {
        return downloadDate;
    }

    public void setDownloadDate(Date downloadDate) {
        this.downloadDate = downloadDate;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public int compareTo(Page p) {
         return index - p.index;
    }
}
