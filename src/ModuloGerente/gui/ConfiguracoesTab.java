package ModuloGerente.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ModuloGerente.Configuracoes;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ConfiguracoesTab {
    
    public static Tab createCofiguracoesTab() {
        Tab operationalTab = new Tab("Configurações");
        GridPane operationalGrid = new GridPane();
        tabConfiguracoes(operationalGrid);
        operationalTab.setContent(operationalGrid);

        return operationalTab;
    }

    private static void tabConfiguracoes(GridPane operationalGrid) {
        operationalGrid.setVgap(10);
        operationalGrid.setHgap(10);

        Label nomeGrupoLabel = new Label("Nome do Grupo:");
        TextField nomeGrupoField = new TextField();
        operationalGrid.add(nomeGrupoLabel, 0, 0);
        operationalGrid.add(nomeGrupoField, 1, 0);

        Label valorDiariaLabel = new Label("Valor da Diária:");
        TextField valorDiariaField = new TextField();
        operationalGrid.add(valorDiariaLabel, 0, 1);
        operationalGrid.add(valorDiariaField, 1, 1);

        Label valorTanqueLabel = new Label("Valor Para Encher o Tanque:");
        TextField valorTanqueField = new TextField();
        operationalGrid.add(valorTanqueLabel, 0, 2);
        operationalGrid.add(valorTanqueField, 1, 2);

        Label valorLimpezaExtLabel = new Label("Valor da Limpeza Externa:");
        TextField valorLimpezaExtField = new TextField();
        operationalGrid.add(valorLimpezaExtLabel, 0, 3);
        operationalGrid.add(valorLimpezaExtField, 1, 3);

        Label valorLimpezaintLabel = new Label("Valor da Limpeza Interna:");
        TextField valorLimpezaintField = new TextField();
        operationalGrid.add(valorLimpezaintLabel, 0, 4);
        operationalGrid.add(valorLimpezaintField, 1, 4);

        Label diariaSeguroLabel = new Label("Valor da Diária do Seguro:");
        TextField diariaSeguroField = new TextField();
        operationalGrid.add(diariaSeguroLabel, 0, 5);
        operationalGrid.add(diariaSeguroField, 1, 5);

        Button submitButton = new Button("Registrar Configurações");
        submitButton.setOnAction(e -> {
            Configuracoes configuracoes = new Configuracoes(
                nomeGrupoField.getText(),
                valorDiariaField.getText(),
                valorTanqueField.getText(),
                valorLimpezaExtField.getText(),
                valorLimpezaintField.getText(),
                diariaSeguroField.getText()
            );
            configuracoes.saveConfiguracoes();
        });
        operationalGrid.add(submitButton, 1, 7);
    }

}
