package ModuloFuncionario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import ModuloGerente.Veiculo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Reserva {
    private Long identificador;   
    private String CPFCliente;
    private String Placa;
    private String dataRetirada;
    private String dataDevolucao;
    private float valorLocacao;
    private String status;

    public Reserva(){}

    public Reserva(Long identificador, String CPFCliente, String Placa, String dataRetirada, 
                   String dataDevolucao, float valorLocacao, String status) {
        this.identificador = identificador;
        this.CPFCliente = CPFCliente;
        this.Placa = Placa;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
        this.valorLocacao = valorLocacao;
        this.status = status;
    }

    // Getters
    public Long getIdentificador() {
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

    public float getValorLocacao() {
        return valorLocacao;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setIdentificador(Long identificador) {
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

    public void setValorLocacao(float valorLocacao) {
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

    public static ObservableList<String> generateGrupoList(String dataRetirada, String dataDevolucao) {
        ObservableList<String> grupoList = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(dataRetirada, formatter);
        LocalDate endDate = LocalDate.parse(dataDevolucao, formatter);

        Path pathVeiculos = Paths.get("database", "veiculos.tsv");
        Path pathReservas = Paths.get("database", "reservas.tsv");

        try {
            HashSet<String> reservedPlacas = new HashSet<>();
            try (BufferedReader brReservas = Files.newBufferedReader(pathReservas)) {
                String line;
                while ((line = brReservas.readLine()) != null) {
                    String[] parts = line.split("\t");
                    LocalDate reservationStart = LocalDate.parse(parts[3].trim(), formatter);
                    LocalDate reservationEnd = LocalDate.parse(parts[4].trim(), formatter);

                    if (!(endDate.isBefore(reservationStart) || startDate.isAfter(reservationEnd))) {
                        reservedPlacas.add(parts[2].trim());
                    }
                }
            }

            try (BufferedReader brVeiculos = Files.newBufferedReader(pathVeiculos)) {
                String line;
                while ((line = brVeiculos.readLine()) != null) {
                    String[] parts = line.split("\t");
                    String placa = parts[0].trim();
                    String grupo = parts[5].trim();

                    if (!reservedPlacas.contains(placa) && !grupoList.contains(grupo)) {
                        grupoList.add(grupo);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getMessage());
            e.printStackTrace();
        }

        return grupoList;
    }

    public static ObservableList<Veiculo> generateVeiculosList(String selectedGrupo) {
        ObservableList<Veiculo> veiculosList = FXCollections.observableArrayList();
    
        Path path = Paths.get("database", "veiculos.tsv");
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                String grupo = parts[5].trim();
                if (grupo.equals(selectedGrupo)) {
                    Veiculo veiculo = new Veiculo(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim(), parts[4].trim(), parts[5].trim(), parts[6].trim());
                    veiculosList.add(veiculo);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getMessage());
            e.printStackTrace();
        }
        
        return veiculosList;
    }

    public static float calculateLocacaoPrice(String selectedGrupo, Boolean contractedSeguro, Boolean contractedLimpezaInt, Boolean contractedLimpezaExt, long daysBetween) {
        Path pathConfiguracoes = Paths.get("database", "configuracoes.tsv");

        try (BufferedReader br = Files.newBufferedReader(pathConfiguracoes)) {
            String line;
            float diaria = 0;
            float limpezaExt = 0;
            float limpezaint = 0;
            float seguro = 0;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                String grupo = parts[0].trim();
                if (grupo.equals(selectedGrupo)) {
                    diaria = Float.parseFloat(parts[1].trim());
                    limpezaExt = Float.parseFloat(parts[3].trim());
                    limpezaint = Float.parseFloat(parts[4].trim());
                    seguro = Float.parseFloat(parts[5].trim());
                    break;
                }
            }

            float total = diaria * daysBetween; 
            if (contractedSeguro) {
                total += seguro * daysBetween;
            }
            if (contractedLimpezaInt) {
                total += limpezaint;
            }
            if (contractedLimpezaExt) {
                total += limpezaExt;
            }

            return total;

        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }
}
