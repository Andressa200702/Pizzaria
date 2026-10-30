package br.senac.sp.pizzaria;

import br.senac.sp.pizzaria.factory.ConnectionFactory;
import br.senac.sp.pizzaria.factory.JpaUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        JpaUtil.getEntityManager();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/pedido-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Pizzaria");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}