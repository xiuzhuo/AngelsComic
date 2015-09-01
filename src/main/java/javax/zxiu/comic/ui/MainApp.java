package javax.zxiu.comic.ui;/**
 * Created by Zhuo Xiu on 31/08/15.
 */

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.zxiu.comic.bean.Comic;
import javax.zxiu.comic.bean.Library;
import javax.zxiu.comic.controllers.ComicListController;
import javax.zxiu.comic.task.ParseTask;
import java.io.IOException;
import java.util.List;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private Library library;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        library = ParseTask.parseLibrary();


        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Main");
        initRootLayout();
        showComicList();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("/javafx/RootLayout.fxml"));
            rootLayout = (BorderPane) fxmlLoader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showComicList() {
        try {
            // Load person overview.
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("/javafx/ComicList.fxml"));
            AnchorPane personOverview = fxmlLoader.load();
            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
            ComicListController controller = fxmlLoader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Comic> getComicList() {
        return library.getComics();
    }
}
