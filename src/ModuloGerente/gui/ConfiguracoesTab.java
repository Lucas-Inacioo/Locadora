package ModuloGerente.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ModuloGerente.Configuracoes;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

public class ConfiguracoesTab {
    
    public static Tab createConfiguracoesTab() {
        Tab operationalTab = new Tab("Configurações");
        GridPane operationalGrid = new GridPane();
        tabConfiguracoes(operationalGrid);
        operationalTab.setContent(operationalGrid);

        return operationalTab;
    }

    private static void tabConfiguracoes(GridPane operationalGrid) {
        operationalGrid.setVgap(10);
        operationalGrid.setHgap(10);

        // First Column
        Label firstColLabel = new Label("Configuração BÁSICO");
        operationalGrid.add(firstColLabel, 0, 0); 

        // Second Column
        Label secondColLabel = new Label("Configuração PADRÃO");
        operationalGrid.add(secondColLabel, 2, 0);

        // Third Column
        Label thirdColLabel = new Label("Configuração PREMIUM");
        operationalGrid.add(thirdColLabel, 4, 0);

        // Add content for the first column
        Label valorDiariaLabelBasico = new Label("Valor da Diária:");
        TextField valorDiariaFieldBasico = new TextField();
        operationalGrid.add(valorDiariaLabelBasico, 0, 1);
        operationalGrid.add(valorDiariaFieldBasico, 1, 1);

        Label valorTanqueLabelBasico = new Label("Valor Para Encher o Tanque:");
        TextField valorTanqueFieldBasico = new TextField();
        operationalGrid.add(valorTanqueLabelBasico, 0, 2);
        operationalGrid.add(valorTanqueFieldBasico, 1, 2);

        Label valorLimpezaExtLabelBasico = new Label("Valor da Limpeza Externa:");
        TextField valorLimpezaExtFieldBasico = new TextField();
        operationalGrid.add(valorLimpezaExtLabelBasico, 0, 3);
        operationalGrid.add(valorLimpezaExtFieldBasico, 1, 3);

        Label valorLimpezaintLabelBasico = new Label("Valor da Limpeza Interna:");
        TextField valorLimpezaintFieldBasico = new TextField();
        operationalGrid.add(valorLimpezaintLabelBasico, 0, 4);
        operationalGrid.add(valorLimpezaintFieldBasico, 1, 4);

        Label diariaSeguroLabelBasico = new Label("Valor da Diária do Seguro:");
        TextField diariaSeguroFieldBasico = new TextField();
        operationalGrid.add(diariaSeguroLabelBasico, 0, 5);
        operationalGrid.add(diariaSeguroFieldBasico, 1, 5);

        // Add content for the second column
        Label valorDiariaLabelPadrao = new Label("Valor da Diária:");
        TextField valorDiariaFieldPadrao = new TextField();
        operationalGrid.add(valorDiariaLabelPadrao, 2, 1);
        operationalGrid.add(valorDiariaFieldPadrao, 3, 1);

        Label valorTanqueLabelPadrao = new Label("Valor Para Encher o Tanque:");
        TextField valorTanqueFieldPadrao = new TextField();
        operationalGrid.add(valorTanqueLabelPadrao, 2, 2);
        operationalGrid.add(valorTanqueFieldPadrao, 3, 2);

        Label valorLimpezaExtLabelPadrao = new Label("Valor da Limpeza Externa:");
        TextField valorLimpezaExtFieldPadrao = new TextField();
        operationalGrid.add(valorLimpezaExtLabelPadrao, 2, 3);
        operationalGrid.add(valorLimpezaExtFieldPadrao, 3, 3);

        Label valorLimpezaintLabelPadrao = new Label("Valor da Limpeza Interna:");
        TextField valorLimpezaintFieldPadrao = new TextField();
        operationalGrid.add(valorLimpezaintLabelPadrao, 2, 4);
        operationalGrid.add(valorLimpezaintFieldPadrao, 3, 4);

        Label diariaSeguroLabelPadrao = new Label("Valor da Diária do Seguro:");
        TextField diariaSeguroFieldPadrao = new TextField();
        operationalGrid.add(diariaSeguroLabelPadrao, 2, 5);
        operationalGrid.add(diariaSeguroFieldPadrao, 3, 5);

        // Add content for the third column
        Label valorDiariaLabelPremium = new Label("Valor da Diária:");
        TextField valorDiariaFieldPremium = new TextField();
        operationalGrid.add(valorDiariaLabelPremium, 4, 1);
        operationalGrid.add(valorDiariaFieldPremium, 5, 1);

        Label valorTanqueLabelPremium = new Label("Valor Para Encher o Tanque:");
        TextField valorTanqueFieldPremium = new TextField();
        operationalGrid.add(valorTanqueLabelPremium, 4, 2);
        operationalGrid.add(valorTanqueFieldPremium, 5, 2);

        Label valorLimpezaExtLabelPremium = new Label("Valor da Limpeza Externa:");
        TextField valorLimpezaExtFieldPremium = new TextField();
        operationalGrid.add(valorLimpezaExtLabelPremium, 4, 3);
        operationalGrid.add(valorLimpezaExtFieldPremium, 5, 3);

        Label valorLimpezaintLabelPremium = new Label("Valor da Limpeza Interna:");
        TextField valorLimpezaintFieldPremium = new TextField();
        operationalGrid.add(valorLimpezaintLabelPremium, 4, 4);
        operationalGrid.add(valorLimpezaintFieldPremium, 5, 4);

        Label diariaSeguroLabelPremium = new Label("Valor da Diária do Seguro:");
        TextField diariaSeguroFieldPremium = new TextField();
        operationalGrid.add(diariaSeguroLabelPremium, 4, 5);
        operationalGrid.add(diariaSeguroFieldPremium, 5, 5);

        Button submitButton = new Button("Registrar Configurações");
        submitButton.setOnAction(e -> {
            List<String> basicoValues = Arrays.asList(
                "BASICO",
                valorDiariaFieldBasico.getText(),
                valorTanqueFieldBasico.getText(),
                valorLimpezaExtFieldBasico.getText(),
                valorLimpezaintFieldBasico.getText(),
                diariaSeguroFieldBasico.getText()
            );
        
            List<String> padraoValues = Arrays.asList(
                "PADRAO",
                valorDiariaFieldPadrao.getText(),
                valorTanqueFieldPadrao.getText(),
                valorLimpezaExtFieldPadrao.getText(),
                valorLimpezaintFieldPadrao.getText(),
                diariaSeguroFieldPadrao.getText()
            );
        
            List<String> premiumValues = Arrays.asList(
                "PREMIUM",
                valorDiariaFieldPremium.getText(),
                valorTanqueFieldPremium.getText(),
                valorLimpezaExtFieldPremium.getText(),
                valorLimpezaintFieldPremium.getText(),
                diariaSeguroFieldPremium.getText()
            );
        
            List<List<String>> allConfigurations = Arrays.asList(basicoValues, padraoValues, premiumValues);
            
            if (confirmation()) {
                Configuracoes.saveMultiConfiguracoes(allConfigurations);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Configurações alteradas com sucesso!");
                alert.showAndWait();
            }
        });
        operationalGrid.add(submitButton, 1, 7);

        Map<String, List<String>> allConfigValues = Configuracoes.readAllConfigurations();

        Configuracoes.setConfigValues(allConfigValues.get("BASICO"), valorDiariaFieldBasico, valorTanqueFieldBasico, valorLimpezaExtFieldBasico, valorLimpezaintFieldBasico, diariaSeguroFieldBasico);
        Configuracoes.setConfigValues(allConfigValues.get("PADRAO"), valorDiariaFieldPadrao, valorTanqueFieldPadrao, valorLimpezaExtFieldPadrao, valorLimpezaintFieldPadrao, diariaSeguroFieldPadrao);
        Configuracoes.setConfigValues(allConfigValues.get("PREMIUM"), valorDiariaFieldPremium, valorTanqueFieldPremium, valorLimpezaExtFieldPremium, valorLimpezaintFieldPremium, diariaSeguroFieldPremium);
    }

    private static Boolean confirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); 
        alert.setTitle("Alterar configurações");
        alert.setHeaderText("Alterar configurações"); 
        alert.setContentText("Tem certeza que deseja alterar as configurações");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}