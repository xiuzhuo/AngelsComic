package javax.zxiu.comic.api;

import java.io.File;

/**
 * Created by Xiu on 2015/8/25.
 */
public abstract class BaseUploadAPI extends BaseAPI{

    BaseUploadAPI() {
        super();
    }

    protected abstract String getToken(Param... params);

}
