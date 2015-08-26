package javax.zxiu.comic.bean;

import java.util.Arrays;

/**
 * Created by Zhuo Xiu on 04/08/15.
 */
public class Library {
    public Comic[] getComics() {
        return comics;
    }

    public void setComics(Comic[] comics) {
        this.comics = comics;
    }

    private Comic[] comics = new Comic[0];

    @Override
    public String toString() {


        return "Library{" +
                "comics=" + Arrays.toString(comics) +
                '}';
    }
}
