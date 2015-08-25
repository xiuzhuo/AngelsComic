package javax.zxiu.comic.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zhuo Xiu on 04/08/15.
 */
public class ParseUtils {
    static final String reg_0 = "<ul\\sid=\"cbc_\\d\"[\\s\\S]*?</ul>";
    static final String reg_1 = "<a[\\s\\S]*?</a>";

    static final Pattern pattern0 = Pattern.compile(reg_0);
    static final Pattern pattern1 = Pattern.compile(reg_1);

    public static void readInputFile() {
//        File file = new File(Config.dataFileName);
//        System.out.println(file + " " + file.exists());
//        System.out.println("size=" + file.length());
//        String result = IOUtils.readFromFile(file);
//        System.out.println("length=" + result.length());
//        System.out.println(result);
    }


    public static void parseBook(){

    }

    public static List<String> match(String source, String element, String attr) {
        List<String> result = new ArrayList<String>();
        String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?\\s.*?>";
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            String r = m.group(1);
            result.add(r);
        }
        return result;
    }

    public static String matchAttr(String source, String element, String attr) {
        String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"](.*?)['\"][^<>]*?>";
        Matcher m = Pattern.compile(reg).matcher(source);
        String result = null;
        //System.out.println("m.find()="+m.find()+" m.group(1)="+m.group(1));
//        if (m.find()) {
//            result = m.group(1);
//        }
        return m.find()?m.group(1):null;
    }
}
