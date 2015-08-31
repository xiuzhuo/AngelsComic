package javax.zxiu.comic.bean;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Zhuo Xiu on 04/08/15.
 */
public class Comic {
    private StringProperty title = new SimpleStringProperty();
    private StringProperty url = new SimpleStringProperty();
    private Date download_date;
    private Date upload_date;
    private SimpleBooleanProperty finished = new SimpleBooleanProperty();
    private ObservableList<Volume> volumes = FXCollections.observableArrayList();

    @Override
    public String toString() {
        return "Comic{" +
                "title=" + title +
                ", url=" + url +
                ", download_date=" + download_date +
                ", upload_date=" + upload_date +
                ", finished=" + finished +
                ", volumes=" + volumes +
                '}';
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getUrl() {
        return url.get();
    }

    public void setUrl(String url) {
        this.url.set(url);
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
        return finished.get();
    }

    public void setFinished(boolean finished) {
        this.finished.set(finished);
    }

    public ObservableList<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes.removeAll();
        this.volumes.addAll(volumes);
    }
}
