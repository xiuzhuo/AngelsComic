package javax.zxiu.comic.task;

import com.alibaba.fastjson.JSON;

import javax.zxiu.comic.bean.Library;
import javax.zxiu.comic.util.IOUtils;

/**
 * Created by Zhuo Xiu on 31/08/15.
 */
public class ParseTask {

    public static Library parseLibrary() {
        String data = IOUtils.readFromFile(DownloadTask.class.getClassLoader().getResource("src.json").getFile());
        Library library = JSON.parseObject(data, Library.class);
        System.err.println(JSON.toJSONString(library, true));
        return library;
    }
}
