/**
 * Classe que gerencia as credenciais de acesso ao sistema de gerenciamento de aluguel de veículos.
 * Esta classe é responsável por carregar as credenciais dos usuários do arquivo, criar a tela de login,
 * e direcionar o usuário para a tela correspondente ao seu papel no sistema.
 */
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

    /**
     * Carrega as credenciais dos usuários do arquivo de credenciais.
     * O arquivo é lido e as credenciais são armazenadas em um mapa para acesso rápido.
     */

    // Método loadCredentials: Carrega as credenciais de um arquivo.
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

    /**
     * Cria a tela de login para o sistema.
     * Esta tela inclui campos para inserção do nome de usuário e senha, e um botão para realizar o login.
     *
     * @param primaryStage O palco principal da aplicação JavaFX.
     * @return Um VBox contendo todos os elementos da tela de login.
     */
    // Método createLoginScreen: Cria a tela de login do sistema.
    public VBox createLoginScreen(Stage primaryStage) {
        primaryStage.setTitle("Sistema de Gerenciamento de Aluguel de Veículos");

        // Inicia a configuração da tela de login, incluindo carregar credenciais e criar elementos de interface.
        loadCredentials();

        VBox loginBox = new VBox(10);

        Label loginLabel = new Label("Login:");
        TextField loginField = new TextField();

        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();

        // Define a ação ao clicar no botão de login, verificando as credenciais inseridas.
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            String login = loginField.getText();
            String password = passwordField.getText();
            navigateToRoleScreen(primaryStage, login, password);
        });

        loginBox.getChildren().addAll(loginLabel, loginField, passwordLabel, passwordField, loginButton);

        return loginBox;
    }

    /**
     * Direciona o usuário para a tela correspondente ao seu papel no sistema.
     * Se as credenciais do usuário forem válidas, a função direcionará para a tela de funcionário ou gerente.
     * Se as credenciais forem inválidas, uma mensagem de alerta é exibida.
     *
     * @param primaryStage O palco principal da aplicação JavaFX.
     * @param username O nome de usuário inserido.
     * @param password A senha inserida.
     */
    // Método navigateToRoleScreen: Direciona o usuário para a tela apropriada, baseada em seu papel (FUNCIONARIO ou GERENTE).
    private void navigateToRoleScreen(Stage primaryStage, String username, String password) {
        String storedPassword = userCredentials.get(username);

        // Verifica as credenciais e direciona para a tela correspondente.
        if (storedPassword != null && storedPassword.equals(password)) {
            if ("FUNCIONARIO".equals(username)) {
                Funcionario.loadEmployeeScreen(primaryStage);
            } else if ("GERENTE".equals(username)) {
                Gerente.loadManagerScreen(primaryStage);
            }
        } else {
            // Exibe um alerta se as credenciais forem inválidas.
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Invalid Credentials");
            alert.setHeaderText(null);
            alert.setContentText("Invalid credentials for user: " + username);

            alert.showAndWait();
        }
    }

}