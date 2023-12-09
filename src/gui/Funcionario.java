/**
 * Classe Funcionario responsável por carregar a tela principal do sistema para funcionários.
 * Esta classe cria uma interface gráfica contendo abas para diferentes funcionalidades
 * acessíveis aos funcionários, como gestão de clientes e reservas.
 */
package gui;

import ModuloFuncionario.gui.ClienteTab;
import ModuloFuncionario.gui.ReservaTab;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Funcionario {

    /**
     * Carrega a tela principal do sistema para funcionários.
     * Esta tela inclui abas para gerenciamento de clientes e reservas.
     *
     * @param primaryStage O palco principal da aplicação JavaFX.
     */
    public static void loadEmployeeScreen(Stage primaryStage) {
        // Configura o layout principal da tela
        BorderPane root = new BorderPane();
        // Cria a cena com dimensões específicas
        Scene scene = new Scene(root, 800, 600);

        // Cria o painel de abas para a interface
        TabPane tabPane = new TabPane();

        // Cria e adiciona as abas de clientes e reservas
        Tab clienteTab = ClienteTab.createClientesTab();
        Tab reservaTab = ReservaTab.createVeiculosDisponiveisTab(primaryStage);

        // Adiciona as abas ao painel de abas
        tabPane.getTabs().addAll(clienteTab, reservaTab);
        // Define o painel de abas como o elemento central do layout
        root.setCenter(tabPane);

        // Define a cena no palco principal
        primaryStage.setScene(scene);
    }
}