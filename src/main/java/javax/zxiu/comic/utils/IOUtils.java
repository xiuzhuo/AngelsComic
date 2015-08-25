package javax.zxiu.comic.utils;

import java.io.*;

/**
 * Created by Zhuo Xiu on 04/08/15.
 */
public class IOUtils {

    public static boolean writeToFile(InputStream inputStream, File file) {
        if (inputStream == null || file == null) {
            return false;
        }
        FileOutputStream fileOutputStream = null;
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            fileOutputStream = new FileOutputStream(file);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, read);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    final static int BUFFER_SIZE = 4096;

    public static String writeToString(InputStream inputStream) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        try {
            while ((count = inputStream.read(data, 0, BUFFER_SIZE)) != -1) {
                outStream.write(data, 0, count);
            }
            return new String(outStream.toByteArray(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readFromFile(String filePath) {
        return readFromFile(new File(filePath));
    }

    public static String readFromFile(File file) {
        StringBuilder builder = new StringBuilder();
        if (file != null || file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null) {
                    builder.append(sCurrentLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }


    // convert InputStream to String
    public static String inputStreamToString(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

}
