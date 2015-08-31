package javax.zxiu.comic.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import org.apache.http.util.TextUtils;

import javax.zxiu.comic.bean.Comic;
import javax.zxiu.comic.ui.MainApp;

/**
 * Created by Zhuo Xiu on 31/08/15.
 */
public class ComicListController {


    @FXML
    private ListView<Comic> comicList;

    @FXML
    private TextField searchText;

    @FXML
    private Button searchButton;
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ComicListController() {
        //initialize();
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
//        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
//        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        comicList.setCellFactory(new Callback<ListView<Comic>, ListCell<Comic>>() {
            @Override
            public ListCell<Comic> call(ListView<Comic> param) {
                return new ComicCell();
            }

        });
        comicList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Comic>() {
            @Override
            public void changed(ObservableValue<? extends Comic> observable, Comic oldValue, Comic newValue) {
                System.out.println("Selected item: " + newValue);
            }
        });
        searchButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                searchText.clear();
                searchText.requestFocus();
            }
        });
        searchText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
                searchButton.setVisible(!TextUtils.isEmpty(newValue));
            }
        });
    }

    static class ComicCell extends ListCell<Comic> {
        @Override
        protected void updateItem(Comic item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty){
                setText(item.getTitle());
            }
        }
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        // Add observable list data to the table
        comicList.setItems(mainApp.getComicList());
    }
}
