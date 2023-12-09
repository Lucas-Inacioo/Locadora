package gui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Credentials {

    private Map<String, String> userCredentials = new HashMap<>();

    public void loadCredentials() {
        Path path = Paths.get("database", "credentials.tsv");
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 2) {
                    userCredentials.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read credentials: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public VBox createLoginScreen(Stage primaryStage) {
        primaryStage.setTitle("Sistema de Gerenciamento de Aluguel de Veículos");
        loadCredentials();

        VBox loginBox = new VBox(10);

        Label loginLabel = new Label("Login:");
        TextField loginField = new TextField();

        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            String login = loginField.getText();
            String password = passwordField.getText();
            navigateToRoleScreen(primaryStage, login, password);
        });

        loginBox.getChildren().addAll(loginLabel, loginField, passwordLabel, passwordField, loginButton);

        return loginBox;
    }

    private void navigateToRoleScreen(Stage primaryStage, String username, String password) {
        String storedPassword = userCredentials.get(username);
        if (storedPassword != null && storedPassword.equals(password)) {
            if ("FUNCIONARIO".equals(username)) {
                Funcionario.loadEmployeeScreen(primaryStage);
            } else if ("GERENTE".equals(username)) {
                Gerente.loadManagerScreen(primaryStage);
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Invalid Credentials");
            alert.setHeaderText(null);
            alert.setContentText("Invalid credentials for user: " + username);

            alert.showAndWait();
        }
    }

}