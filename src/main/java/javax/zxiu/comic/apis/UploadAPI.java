package javax.zxiu.comic.apis;

import org.apache.http.NameValuePair;

import javax.zxiu.comic.beans.Image;
import java.io.File;

/**
 * Created by Xiu on 2015/8/25.
 */
public abstract class UploadAPI extends BaseAPI{

    protected abstract String getToken(Param... params);

    public abstract Image upload(File file);


}
