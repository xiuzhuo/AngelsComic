package javax.zxiu.comic;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;

import javax.zxiu.comic.tasks.DownloadTask;

/**
 * Created by Zhuo Xiu on 04/08/15.
 */
public class ComicPoster extends Application {

    static CloseableHttpAsyncClient client;
    static String url = "http://manhua1025.104-250-152-74.cdndm5.com/17/16656/216286/2_3997.jpg?cid=216286&key=5235e87ad8b4d1900017e3492b30ff86";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DownloadTask.parseBook(DownloadTask.parseComic(DownloadTask.parseInput(), 0),0);

//        Dummy.testDownload();
//        Dummy.testReadInputFile();
//        Dummy.testLoadPage();
//        Dummy.testConnect();
//        Dummy.testJS();
//        launch(args);
//Dummy.testGetComicInformation();
//        ParseUtils.getAllComics();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        System.out.println(getClass());
//        System.out.println(getClass().getClassLoader().getResource("sample.fxml"));
//        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 900, 275));
//        primaryStage.show();
//
//
//        String url = "http://www.dm5.com/manhua-quanyuanaxiuluo/";
//        WebView webView = new WebView();
//        WebEngine webEngine = webView.getEngine();
//        webEngine.load(url);
//
//
//        webEngine.getLoadWorker().stateProperty().addListener(
//                new ChangeListener<State>() {
//                    public void changed(ObservableValue ov, State oldState, State newState) {
//                        if (newState == State.SUCCEEDED) {
//                            primaryStage.setTitle(webEngine.getLocation());
//                        }
//                        String html = (String) webEngine.executeScript("document.documentElement.outerHTML");
//                        System.out.println(html);
//                        System.out.println(webEngine.getTitle());
//                        primaryStage.setTitle(webEngine.getLocation());
//                    }
//                });
//        webEngine.load("http://javafx.com");
//        Dummy.testLoadPage();
        init(primaryStage);
        primaryStage.show();
    }


    public static final String defaultURL="http://www.dm5.com/manhua-quanyuanaxiuluo/";
//    public static final String defaultURL="http://baidu.com";

    private void init(Stage primaryStage){
        final Stage stage=primaryStage;
        Group group=new Group();//作为根节点，也就是root
        primaryStage.setScene(new Scene(group));

        WebView webView=new WebView();
        final WebEngine engine=  webView.getEngine();
        engine.load(defaultURL);

        final TextField textField=new TextField(defaultURL);
        /**修改输入栏的地址，也就是访问那个网站，这个地址栏显示那个网站的地址
         * locationProperty()是获得当前页面的url封装好的ReadOnlyStringProperty对象
         */
        engine.locationProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                textField.setText(newValue);
            }
        });
        /**
         * 设置标题栏为当前访问页面的标题。
         */
        engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>(){
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if(newValue==Worker.State.SUCCEEDED){
                    stage.setTitle(engine.getTitle());


                    String html = (String) engine.executeScript("document.documentElement.outerHTML");


                    System.out.println(html);
                }
            }
        });
        stage.setTitle("ahhaha");

        /**
         * 测试能否获得javascript上面的交互内容。
         * 可以自己写一个包含window.alert("neirong")的html进行测试。
         * 返回的是neirong
         */
        engine.setOnAlert(new EventHandler<WebEvent<String>>() {

            @Override
            public void handle(WebEvent<String> event) {
                System.out.println("this is event" + event);
            }
        });

        //加载新的地址
        EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                engine.load(textField.getText().startsWith("http://")?textField.getText().trim():"http://"+textField.getText().trim());
            }
        };

        textField.setOnAction(handler);

        Button okButton=new Button("go");
        okButton.setDefaultButton(true);
        okButton.setOnAction(handler);

        HBox hbox=new HBox();
        hbox.getChildren().addAll(textField,okButton);
        HBox.setHgrow(textField, Priority.ALWAYS);

        VBox vBox=new VBox();
        vBox.getChildren().addAll(hbox,webView);
        VBox.setVgrow(webView, Priority.ALWAYS);

        group.getChildren().add(vBox);
    }
}
