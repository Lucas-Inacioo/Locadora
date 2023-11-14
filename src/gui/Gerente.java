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
    public static void loadManagerScreen(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);

        TabPane tabPane = new TabPane();

        Tab veiculoTab = VeiculoTab.createVeiculoTab();
        Tab configuracoesTab = ConfiguracoesTab.createConfiguracoesTab();
        Tab relatorioTab = RelatorioTab.createRelatorioTab(primaryStage);

        tabPane.getTabs().addAll(veiculoTab, configuracoesTab, relatorioTab);
        root.setCenter(tabPane);

        primaryStage.setScene(scene);
    }
}
