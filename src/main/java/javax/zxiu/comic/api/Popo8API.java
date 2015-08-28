package javax.zxiu.comic.api;

import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.entity.NByteArrayEntity;

import javax.zxiu.comic.util.NetUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zhuo Xiu on 28/08/15.
 */
public class Popo8API {
    static final String URL = "http://www.popo8.com/host/index.php?ctl=upload&act=dopicupload";
    static Pattern pattern=Pattern.compile("\\u56fe\\u7247\\u94fe\\u63a5.*?<span.*?>(http://.*?)</span>");
    public static String upload(File file) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setContentType(ContentType.MULTIPART_FORM_DATA);
        builder.addBinaryBody("imgs[]", file, ContentType.create("image/jpg"), file.getName());
        builder.setBoundary("----WebKitFormBoundary4z557UAWx9usiTvH");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            builder.build().writeTo(baos);
            NByteArrayEntity nByteArrayEntity = new NByteArrayEntity(baos.toByteArray());
            String result = NetUtils.post(URL, new Header[]{new BasicHeader("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary4z557UAWx9usiTvH")}, nByteArrayEntity);
            Matcher matcher=pattern.matcher(result);
            if (matcher.find()){
                System.err.println(matcher.group(0));
                System.err.println(matcher.group(1));
                return matcher.group(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
