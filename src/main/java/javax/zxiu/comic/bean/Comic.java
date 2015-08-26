package javax.zxiu.comic.bean;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Zhuo Xiu on 04/08/15.
 */
public class Comic {
    private String title;
    private String host;
    private String url;
    private Date download_date;
    private Date upload_date;
    private boolean finished;
    private Volume[] volumes = new Volume[0];

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
