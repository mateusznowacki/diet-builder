package pl.dietbuilder.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.dietbuilder.dbmanagement.ConnectionManager;

import java.util.Objects;

public class Main extends Application {

    double x, y = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/main.fxml")));
        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.UNDECORATED);

//
//        root.setOnMousePressed(mouseEvent -> {
//            x = mouseEvent.getSceneX();
//            y = mouseEvent.getSceneY();
//        });
//
//        root.setOnMouseDragged(mouseEvent -> {
//            primaryStage.setX(mouseEvent.getScreenX() - x);
//            primaryStage.setY(mouseEvent.getScreenY() - y);
//        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Diet Builder");
        primaryStage.show();
        primaryStage.setResizable(false);


        ConnectionManager connectionManager = ConnectionManager.getInstance();
        System.out.println(connectionManager.getConnection());


    }

    public static void main(String[] args) {
        launch(args);
    }
}
