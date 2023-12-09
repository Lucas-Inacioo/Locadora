package ModuloFuncionario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
    private Boolean contractedSeguro; 
    private Boolean contractedLimpezaInt; 
    private Boolean contractedLimpezaExt;

    public Reserva(){}

    public Reserva( Long identificador, String CPFCliente, String Placa, String dataRetirada, 
                    String dataDevolucao, float valorLocacao, String status, Boolean contractedSeguro,
                    Boolean contractedLimpezaInt, Boolean contractedLimpezaExt) {
        this.identificador = identificador;
        this.CPFCliente = CPFCliente;
        this.Placa = Placa;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
        this.valorLocacao = valorLocacao;
        this.status = status;
        this.contractedSeguro = contractedSeguro;
        this.contractedLimpezaInt = contractedLimpezaInt;
        this.contractedLimpezaExt = contractedLimpezaExt;
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

    public Boolean getContractedSeguro() {
        return contractedSeguro;
    }

    public Boolean getContractedLimpezaInt() {
        return contractedLimpezaInt;
    }

    public Boolean getContractedLimpezaExt() {
        return contractedLimpezaExt;
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

    public void setContractedSeguro(Boolean contractedSeguro) {
        this.contractedSeguro = contractedSeguro;
    }

    public void setContractedLimpezaInt(Boolean contractedLimpezaInt) {
        this.contractedLimpezaInt = contractedLimpezaInt;
    }

    public void setContractedLimpezaExt(Boolean contractedLimpezaExt) {
        this.contractedLimpezaExt = contractedLimpezaExt;
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

        data =  identificador + "\t" + contractedSeguro + "\t" + contractedLimpezaInt + "\t" + 
                contractedLimpezaExt + "\n";
        relativePath = "database/reservasDetalhes.tsv";

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

    public static ObservableList<Reserva> getReservationsByCPF(String CPF) {
        ObservableList<Reserva> reservationList = FXCollections.observableArrayList();

        Path pathReservas = Paths.get("database", "reservas.tsv");

        try (BufferedReader br = Files.newBufferedReader(pathReservas)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                String cpf = parts[1].trim();
                if (cpf.equals(CPF)) {
                    Reserva reserva = new Reserva(Long.parseLong(parts[0].trim()), parts[1].trim(), parts[2].trim(), parts[3].trim(), parts[4].trim(), Float.parseFloat(parts[5].trim()), parts[6].trim(), false, false, false);
                    reservationList.add(reserva);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getMessage());
            e.printStackTrace();
        }
        
        return reservationList;
    }

    public static Reserva getReservaByID(String identificador) {
        Path pathReservas = Paths.get("database", "reservas.tsv");

        try (BufferedReader br = Files.newBufferedReader(pathReservas)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                String id = parts[0].trim();
                if (id.equals(identificador)) {
                    return new Reserva(Long.parseLong(parts[0].trim()), parts[1].trim(), parts[2].trim(), parts[3].trim(), parts[4].trim(), Float.parseFloat(parts[5].trim()), parts[6].trim(), false, false, false);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean isDuplicatedReserva(String identificador) {
        String relativePath = "database/reservas.tsv";
        File configFile = new File(relativePath);
        boolean duplicated = false;

        if (configFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(identificador + "\t")) {
                        duplicated = true;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (duplicated) {
            return true;
        }
        return false;
    }

    public static void deleteReserva(String identificador) {
        deleteFromFile("database/reservas.tsv", identificador);
        deleteFromFile("database/reservasDetalhes.tsv", identificador);
    }

    private static void deleteFromFile(String filePath, String identificador) {
        File configFile = new File(filePath);
        if (!configFile.exists()) {
            return; // If file does not exist, return immediately
        }

        List<String> linesToKeep = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(identificador + "\t")) {
                    linesToKeep.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile, false))) {
            for (String line : linesToKeep) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
