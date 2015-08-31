package javax.zxiu.comic.bean;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.util.Date;

/**
 * Created by Zhuo Xiu on 04/08/15.
 */
public class Page implements Comparable<Page> {
    private int index;
    private StringProperty imageDownloadUrl = new SimpleStringProperty();
    private StringProperty imageUploadUrl = new SimpleStringProperty();
    private LongProperty imageSize = new SimpleLongProperty();
    private File imageFile;
    private StringProperty imagePath = new SimpleStringProperty();
    private Date downloadDate;
    private Date uploadDate;

    @Override
    public String toString() {
        return "Page{" +
                "index=" + index +
                ", imageDownloadUrl='" + imageDownloadUrl + '\'' +
                ", imageUploadUrl='" + imageUploadUrl + '\'' +
                ", imageSize=" + imageSize +
                ", imagePath='" + imagePath + '\'' +
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
        return imageDownloadUrl.get();
    }

    public void setImageDownloadUrl(String imageDownloadUrl) {
        this.imageDownloadUrl.set(imageDownloadUrl);
    }

    public String getImageUploadUrl() {
        return imageUploadUrl.get();
    }

    public void setImageUploadUrl(String imageUploadUrl) {
        this.imageUploadUrl.set(imageUploadUrl);
    }

    public String getImagePath() {
        return imagePath.get();
    }

    public void setImagePath(String imagePath) {
        this.imagePath.set(imagePath);
    }

    public long getImageSize() {
        return imageSize.get();
    }

    public void setImageSize(long imageSize) {
        this.imageSize.set(imageSize);
    }

    public File getImageFile() {
        return getImagePath() == null ? null : new File(getImagePath());
    }

    public void setImageFile(File imageFile) {
        setImagePath(imageFile.getPath());
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
