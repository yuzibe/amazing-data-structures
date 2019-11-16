package com.keybarrel;

import at.svvx.core.Trie;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = onLoad();
        inputSearchOnReady(root);
        onShow(primaryStage, root);
    }

    private void onShow(Stage primaryStage, Parent root) {
        primaryStage.setTitle("词典联想引擎");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    private void inputSearchOnReady(Parent root) {
        TextField inputSearch = (TextField) root.lookup("#inputSearch");
        inputSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            ListView matchListView = (ListView) root.lookup("#matchListView");
            if (newValue.trim().length() != 0) {
                List<String> keys = trie.find(newValue.trim());
                if (keys != null)
                    matchListView.getItems().setAll(keys);
                else
                    matchListView.setVisible(false);
                matchListView.setVisible(true);
            } else {
                matchListView.setVisible(false);
            }
        });
    }

    private Parent onLoad() throws java.io.IOException {
        FileReader reader = new FileReader("Dictionary.data");
        BufferedReader buffer = new BufferedReader(reader);

        String line = null;
        String[] strings = null;
        while ((line = buffer.readLine()) != null) {
            strings = new String(line).split("\\, ");
        }

        for (int i = 0; i < strings.length; i++) {
            trie.insert(strings[i]);
        }

        return FXMLLoader.load(getClass().getResource("resource/interface.fxml"));
    }

    static Trie trie = new Trie();

    public static void main(String[] args) {
        launch(args);
    }
}
