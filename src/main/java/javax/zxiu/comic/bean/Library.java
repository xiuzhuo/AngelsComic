package javax.zxiu.comic.bean;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Zhuo Xiu on 04/08/15.
 */
public class Library {

    private ObservableList<Comic> comics = FXCollections.observableArrayList();


    public ObservableList<Comic> getComics() {
        return comics;
    }

    public void setComics(List<Comic> comics) {
        this.comics.clear();
        this.comics.addAll(comics);
    }

    @Override
    public String toString() {
        return "Library{" +
                "comics=" + comics +
                '}';
    }
}
