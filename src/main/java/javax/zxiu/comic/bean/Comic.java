package javax.zxiu.comic.bean;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Zhuo Xiu on 04/08/15.
 */
public class Comic {
    private StringProperty title = new SimpleStringProperty();
    private StringProperty host = new SimpleStringProperty();
    private StringProperty url = new SimpleStringProperty();
    private Date download_date;
    private Date upload_date;
    private SimpleBooleanProperty finished = new SimpleBooleanProperty();
    private Volume[] volumes = new Volume[0];

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getHost() {
        return host.get();
    }

    public void setHost(String host) {
        this.host.set(host);
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

    public Volume[] getVolumes() {
        return volumes;
    }

    public void setVolumes(Volume[] volumes) {
        this.volumes = volumes;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "title='" + title + '\'' +
                ", host='" + host + '\'' +
                ", url='" + url + '\'' +
                ", download_date=" + download_date +
                ", upload_date=" + upload_date +
                ", finished=" + finished +
                ", volumes=" + Arrays.toString(volumes) +
                '}';
    }
}
