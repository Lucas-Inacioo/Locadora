package ModuloGerente.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ModuloGerente.Configuracoes;
import javafx.scene.control.Button;
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
        Label thirdColLabel = new Label("Configuração VIP");
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
        Label valorDiariaLabelVIP = new Label("Valor da Diária:");
        TextField valorDiariaFieldVIP = new TextField();
        operationalGrid.add(valorDiariaLabelVIP, 4, 1);
        operationalGrid.add(valorDiariaFieldVIP, 5, 1);

        Label valorTanqueLabelVIP = new Label("Valor Para Encher o Tanque:");
        TextField valorTanqueFieldVIP = new TextField();
        operationalGrid.add(valorTanqueLabelVIP, 4, 2);
        operationalGrid.add(valorTanqueFieldVIP, 5, 2);

        Label valorLimpezaExtLabelVIP = new Label("Valor da Limpeza Externa:");
        TextField valorLimpezaExtFieldVIP = new TextField();
        operationalGrid.add(valorLimpezaExtLabelVIP, 4, 3);
        operationalGrid.add(valorLimpezaExtFieldVIP, 5, 3);

        Label valorLimpezaintLabelVIP = new Label("Valor da Limpeza Interna:");
        TextField valorLimpezaintFieldVIP = new TextField();
        operationalGrid.add(valorLimpezaintLabelVIP, 4, 4);
        operationalGrid.add(valorLimpezaintFieldVIP, 5, 4);

        Label diariaSeguroLabelVIP = new Label("Valor da Diária do Seguro:");
        TextField diariaSeguroFieldVIP = new TextField();
        operationalGrid.add(diariaSeguroLabelVIP, 4, 5);
        operationalGrid.add(diariaSeguroFieldVIP, 5, 5);

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
        
            List<String> vipValues = Arrays.asList(
                "VIP",
                valorDiariaFieldVIP.getText(),
                valorTanqueFieldVIP.getText(),
                valorLimpezaExtFieldVIP.getText(),
                valorLimpezaintFieldVIP.getText(),
                diariaSeguroFieldVIP.getText()
            );
        
            List<List<String>> allConfigurations = Arrays.asList(basicoValues, padraoValues, vipValues);
        
            Configuracoes.saveMultiConfiguracoes(allConfigurations);
        });
        operationalGrid.add(submitButton, 1, 7);

        Map<String, List<String>> allConfigValues = Configuracoes.readAllConfigurations();

        Configuracoes.setConfigValues(allConfigValues.get("BASICO"), valorDiariaFieldBasico, valorTanqueFieldBasico, valorLimpezaExtFieldBasico, valorLimpezaintFieldBasico, diariaSeguroFieldBasico);
        Configuracoes.setConfigValues(allConfigValues.get("PADRAO"), valorDiariaFieldPadrao, valorTanqueFieldPadrao, valorLimpezaExtFieldPadrao, valorLimpezaintFieldPadrao, diariaSeguroFieldPadrao);
        Configuracoes.setConfigValues(allConfigValues.get("VIP"), valorDiariaFieldVIP, valorTanqueFieldVIP, valorLimpezaExtFieldVIP, valorLimpezaintFieldVIP, diariaSeguroFieldVIP);
    }

}