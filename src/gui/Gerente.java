/**
 * Classe Gerente responsável por carregar a tela principal do sistema para gerentes.
 * Esta classe cria uma interface gráfica contendo abas para diferentes funcionalidades
 * acessíveis aos gerentes, como gestão de veículos, configurações e relatórios.
 */
package gui;

import ModuloGerente.gui.ConfiguracoesTab;
import ModuloGerente.gui.RelatorioTab;
import ModuloGerente.gui.VeiculoTab;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Gerente {

    /**
     * Carrega a tela principal do sistema para gerentes.
     * Esta tela inclui abas para gerenciamento de veículos, configurações e relatórios.
     *
     * @param primaryStage O palco principal da aplicação JavaFX.
     */
    public static void loadManagerScreen(Stage primaryStage) {
        // Configura o layout principal da tela
        BorderPane root = new BorderPane();
        // Cria a cena com dimensões específicas
        Scene scene = new Scene(root, 800, 600);

        // Cria o painel de abas para a interface
        TabPane tabPane = new TabPane();

        // Cria e adiciona as abas de veículos, configurações e relatórios
        Tab veiculoTab = VeiculoTab.createVeiculoTab();
        Tab configuracoesTab = ConfiguracoesTab.createConfiguracoesTab();
        Tab relatorioTab = RelatorioTab.createRelatorioTab(primaryStage);

        // Adiciona as abas ao painel de abas
        tabPane.getTabs().addAll(veiculoTab, configuracoesTab, relatorioTab);
        // Define o painel de abas como o elemento central do layout
        root.setCenter(tabPane);

        // Define a cena no palco principal
        primaryStage.setScene(scene);
    }
}
