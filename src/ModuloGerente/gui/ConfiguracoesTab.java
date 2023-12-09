package ModuloGerente.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ModuloGerente.Configuracoes;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

/**
 * Classe que representa a aba de configurações na interface gráfica do módulo gerente.
 */
public class ConfiguracoesTab {
    
    /**
     * Cria e retorna uma aba de configurações.
     * @return A aba de configurações criada.
     */
    public static Tab createConfiguracoesTab() {
        Tab operationalTab = new Tab("Configurações");
        GridPane operationalGrid = new GridPane();
        tabConfiguracoes(operationalGrid);
        operationalTab.setContent(operationalGrid);

        return operationalTab;
    }

    /**
     * Configura o layout e os componentes da aba de configurações.
     * @param operationalGrid O grid pane onde os componentes serão adicionados.
     */
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
            // Define todas as configurações dos grupos em uma lista de listas
            List<List<String>> allConfigurations = Arrays.asList(
                Arrays.asList("BASICO", valorDiariaFieldBasico.getText(), valorTanqueFieldBasico.getText(), valorLimpezaExtFieldBasico.getText(), valorLimpezaintFieldBasico.getText(), diariaSeguroFieldBasico.getText()),
                Arrays.asList("PADRAO", valorDiariaFieldPadrao.getText(), valorTanqueFieldPadrao.getText(), valorLimpezaExtFieldPadrao.getText(), valorLimpezaintFieldPadrao.getText(), diariaSeguroFieldPadrao.getText()),
                Arrays.asList("PREMIUM", valorDiariaFieldPremium.getText(), valorTanqueFieldPremium.getText(), valorLimpezaExtFieldPremium.getText(), valorLimpezaintFieldPremium.getText(), diariaSeguroFieldPremium.getText())
            );

            // Lista para armazenar os valores inválidos encontrados
            List<String> invalidValues = new ArrayList<>();
            
            // Itera sobre cada lista de configurações
            for (List<String> configuration : allConfigurations) {
                // Pula o primeiro elemento de cada lista, que é o nome do grupo
                List<String> valuesToCheck = configuration.subList(1, configuration.size());

                // Itera sobre cada valor da configuração, exceto o nome do grupo
                for (String value : valuesToCheck) {
                    if (!value.matches("^[0-9]+(\\.[0-9]+)?$")) {
                        // Adiciona o nome do grupo e o valor inválido à lista de inválidos
                        invalidValues.add(configuration.get(0) + " - " + value);
                    }
                }
            }

            // Verifica se há valores inválidos
            if (!invalidValues.isEmpty()) {
                // Junta todos os valores inválidos em uma string para exibição
                String joinedInvalidValues = String.join(", ", invalidValues);
                
                // Exibe um alerta com os valores inválidos
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Valor inválido");
                alert.setHeaderText("Entrada(s) inválida(s) detectada(s)");
                alert.setContentText("Os seguintes valores são inválidos: " + joinedInvalidValues + ".\nPor favor, insira apenas valores numéricos válidos (ex: 100, 100.0, 0.5).");
                alert.showAndWait();
            } else if (confirmation()) {
                // Se todos os valores forem válidos e o usuário confirmar, salva as configurações
                Configuracoes.saveMultiConfiguracoes(allConfigurations);
                
                // Exibe um alerta de sucesso
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Configurações alteradas com sucesso!");
                alert.showAndWait();
            }
        });
        operationalGrid.add(submitButton, 5, 7);

        Map<String, List<String>> allConfigValues = Configuracoes.readAllConfigurations();

        Configuracoes.setConfigValues(allConfigValues.get("BASICO"), valorDiariaFieldBasico, valorTanqueFieldBasico, valorLimpezaExtFieldBasico, valorLimpezaintFieldBasico, diariaSeguroFieldBasico);
        Configuracoes.setConfigValues(allConfigValues.get("PADRAO"), valorDiariaFieldPadrao, valorTanqueFieldPadrao, valorLimpezaExtFieldPadrao, valorLimpezaintFieldPadrao, diariaSeguroFieldPadrao);
        Configuracoes.setConfigValues(allConfigValues.get("PREMIUM"), valorDiariaFieldPremium, valorTanqueFieldPremium, valorLimpezaExtFieldPremium, valorLimpezaintFieldPremium, diariaSeguroFieldPremium);
    }

    /**
     * Exibe um diálogo de confirmação antes de salvar as configurações.
     * @return Verdadeiro se o usuário confirmar, falso caso contrário.
     */
    private static Boolean confirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); 
        alert.setTitle("Alterar configurações");
        alert.setHeaderText("Alterar configurações"); 
        alert.setContentText("Tem certeza que deseja alterar as configurações");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}