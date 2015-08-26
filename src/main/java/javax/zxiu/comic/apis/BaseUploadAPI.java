package javax.zxiu.comic.apis;

import javax.zxiu.comic.entity.Picture;
import java.io.File;

/**
 * Created by Xiu on 2015/8/25.
 */
public abstract class BaseUploadAPI extends BaseAPI{

    BaseUploadAPI() {
        super();
    }

    protected abstract String getToken(Param... params);

    public abstract TietukuAPI.Pic upload(File file);


}
