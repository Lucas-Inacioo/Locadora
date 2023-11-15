package ModuloFuncionario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Reserva {
    private String identificador;   
    private String CPFCliente;
    private String Placa;
    private String dataRetirada;
    private String dataDevolucao;
    private String valorLocacao;
    private String status;

    public Reserva(){}

    public Reserva(String identificador, String CPFCliente, String Placa, String dataRetirada, 
                   String dataDevolucao, String valorLocacao, String status) {
        this.identificador = identificador;
        this.CPFCliente = CPFCliente;
        this.Placa = Placa;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
        this.valorLocacao = valorLocacao;
        this.status = status;
    }

    // Getters
    public String getIdentificador() {
        return identificador;
    }

    public String getCPFCliente() {
        return CPFCliente;
    }

    public String getPlaca() {
        return Placa;
    }

    public String getDataRetirada() {
        return dataRetirada;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public String getValorLocacao() {
        return valorLocacao;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public void setCPFCliente(String CPFCliente) {
        this.CPFCliente = CPFCliente;
    }

    public void setPlaca(String Placa) {
        this.Placa = Placa;
    }

    public void setDataRetirada(String dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public void setValorLocacao(String valorLocacao) {
        this.valorLocacao = valorLocacao;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void saveReserva() {
        String data =   identificador + "\t" + CPFCliente + "\t" + Placa + "\t" + dataRetirada + "\t" + 
                        dataDevolucao + "\t" + valorLocacao + "\t" + status + "\n";

        String relativePath = "database/reservas.tsv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(relativePath, true))) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<String> generateGrupoList() {
        ObservableList<String> grupoList = FXCollections.observableArrayList();

        Path path = Paths.get("database", "veiculos.tsv");
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                String grupo = parts[5].trim();
                if (!grupoList.contains(grupo)) {
                    grupoList.add(grupo);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getMessage());
            e.printStackTrace();
        }
        
        return grupoList;
    }

    public static ObservableList<String> generatePlacaList(String selectedGrupo) {
        ObservableList<String> placaList = FXCollections.observableArrayList();

        Path path = Paths.get("database", "veiculos.tsv");
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                String placa = parts[0].trim();
                String grupo = parts[5].trim();
                if (grupo.equals(selectedGrupo)) {
                    placaList.add(placa);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getMessage());
            e.printStackTrace();
        }
        
        return placaList;
    }
}
