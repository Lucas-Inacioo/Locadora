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

import ModuloGerente.Veiculo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Representa uma reserva no sistema de gerenciamento de aluguel de veículos.
 */
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

    /**
     * Construtor vazio para a classe Reserva.
     */
    public Reserva(){}

    /**
     * Construtor para criar um novo objeto Reserva.
     * @param identificador Identificador único da reserva.
     * @param CPFCliente CPF do cliente que fez a reserva.
     * @param Placa Placa do veículo reservado.
     * @param dataRetirada Data de início da reserva.
     * @param dataDevolucao Data de término da reserva.
     * @param valorLocacao Valor total da locação.
     * @param status Status atual da reserva (ativa, concluída, etc).
     * @param contractedSeguro Indica se o seguro foi contratado.
     * @param contractedLimpezaInt Indica se a limpeza interna foi contratada.
     * @param contractedLimpezaExt Indica se a limpeza externa foi contratada.
     */
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

    /**
     * Salva os detalhes da reserva no arquivo de banco de dados.
     */
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

    /**
     * Gera uma lista de grupos de veículos disponíveis para reserva.
     * @param dataRetirada Data de início para a reserva.
     * @param dataDevolucao Data de término para a reserva.
     * @return Uma lista observável de grupos de veículos disponíveis.
     */
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

    /**
     * Gera uma lista de veículos disponíveis de um grupo selecionado.
     * @param selectedGrupo Grupo selecionado de veículos.
     * @return Uma lista observável de veículos do grupo selecionado.
     */
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

    /**
     * Calcula o preço total da locação com base em vários fatores.
     * @param selectedGrupo Grupo do veículo selecionado.
     * @param contractedSeguro Indica se o seguro foi contratado.
     * @param contractedLimpezaInt Indica se a limpeza interna foi contratada.
     * @param contractedLimpezaExt Indica se a limpeza externa foi contratada.
     * @param daysBetween Número de dias da locação.
     * @return O preço total calculado da locação.
     */
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

    /**
     * Calcula o preço da locação com base na limpeza e no nível de combustível.
     * @param selectedGrupo Grupo do veículo.
     * @param contractedLimpezaInt Indica se a limpeza interna foi contratada.
     * @param contractedLimpezaExt Indica se a limpeza externa foi contratada.
     * @param gasLevel Nível de combustível no retorno do veículo.
     * @return O preço total calculado.
     */
    public static float calculateLocacaoPrice(String selectedGrupo, Boolean contractedLimpezaInt, Boolean contractedLimpezaExt, String gasLevel) {
        Path pathConfiguracoes = Paths.get("database", "configuracoes.tsv");

        try (BufferedReader br = Files.newBufferedReader(pathConfiguracoes)) {
            String line;
            float limpezaExt = 0;
            float limpezaint = 0;
            float gas = 0;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                String grupo = parts[0].trim();
                if (grupo.equals(selectedGrupo)) {
                    gas = Float.parseFloat(parts[2].trim());
                    limpezaExt = Float.parseFloat(parts[3].trim());
                    limpezaint = Float.parseFloat(parts[4].trim());
                    break;
                }
            }

            float total = 0;
            if (contractedLimpezaInt) {
                total += limpezaint;
            }
            if (contractedLimpezaExt) {
                total += limpezaExt;
            }
            
            total += gas * (1 - Float.parseFloat(gasLevel));

            return total;

        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Obtém uma lista de reservas ativas associadas a um CPF específico.
     * @param CPF CPF do cliente.
     * @return Uma lista observável de reservas associadas ao CPF fornecido.
     */
    public static ObservableList<Reserva> getReservationsByCPF(String CPF) {
        ObservableList<Reserva> reservationList = FXCollections.observableArrayList();

        Path pathReservas = Paths.get("database", "reservas.tsv");

        try (BufferedReader br = Files.newBufferedReader(pathReservas)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                String cpf = parts[1].trim();
                if (cpf.equals(CPF)  && parts[6].equals("ATIVA")) {
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

    /**
     * Obtém os detalhes de uma reserva específica pelo seu identificador.
     * @param identificador Identificador único da reserva.
     * @return A reserva correspondente ao identificador, se encontrada.
     */
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

    /**
     * Obtém uma reserva específica pela placa do veículo.
     * @param desiredPlaca Placa do veículo da reserva.
     * @return A reserva associada à placa fornecida, se encontrada.
     */
    public static Reserva getReservaByPlaca(String desiredPlaca) {
        Path pathReservas = Paths.get("database", "reservas.tsv");

        try (BufferedReader br = Files.newBufferedReader(pathReservas)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                String placa = parts[2].trim();
                if (placa.equals(desiredPlaca)) {
                    return new Reserva(Long.parseLong(parts[0].trim()), parts[1].trim(), parts[2].trim(), parts[3].trim(), parts[4].trim(), Float.parseFloat(parts[5].trim()), parts[6].trim(), false, false, false);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Obtém os detalhes de serviços contratados para uma reserva específica.
     * @param reserva Objeto Reserva para o qual os detalhes são necessários.
     * @return Uma lista de valores booleanos representando os serviços contratados.
     */
    public static ArrayList<Boolean> getDetailsByReserva(Reserva reserva) {
        String relativePath = "database/reservasDetalhes.tsv";
        File configFile = new File(relativePath);

        ArrayList<Boolean> details = new ArrayList<>();

        if (configFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(reserva.getIdentificador() + "\t")) {
                        String[] data = line.split("\t");
                        details.add(Boolean.parseBoolean(data[1].trim()));
                        details.add(Boolean.parseBoolean(data[2].trim()));
                        details.add(Boolean.parseBoolean(data[3].trim()));
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return details;
    }

    /**
     * Verifica a existência de uma reserva à partir de um valor identificador.
     * @param identificador String que representa o atributo identificador de uma reserva.
     * @return Um valor booleano representando se a reserva já existe.
     */
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

    /**
     * Atualiza o status de uma reserva existente no sistema.
     * @param identificador Identificador da reserva a ser atualizada.
     * @param reason Novo status para a reserva.
     */
    public static void updateReserva(String identificador, String reason) {
        File inputFile = new File("database/reservas.tsv");
        File tempFile = new File("database/reservas_temp.tsv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] data = currentLine.split("\t");
                if (data[0].equals(identificador)) {
                    data[6] = reason;
                    currentLine = String.join("\t", data);
                }
                writer.write(currentLine + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Could not rename the temporary file.");
            }
        } else {
            System.out.println("Could not delete the original file.");
        }
    }

    /**
     * Encontra candidatos a no-show com base na data atual.
     * @param today Data atual para verificar no-shows.
     * @return Uma lista observável de reservas que são candidatas a no-show.
     */
    public static ObservableList<Reserva> findNoShowCandidates(LocalDate today) {
        ObservableList<Reserva> candidates = FXCollections.observableArrayList();

        Path path = Paths.get("database", "reservas.tsv");

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                LocalDate startDate = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String status = parts[6];

                if (!startDate.isAfter(today) && status.equals("ATIVA")) {
                    Reserva reserva = new Reserva(Long.parseLong(parts[0].trim()), parts[1].trim(), parts[2].trim(), parts[3].trim(), parts[4].trim(), Float.parseFloat(parts[5].trim()), parts[6].trim(), false, false, false);
                    candidates.add(reserva);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getMessage());
            e.printStackTrace();
        }

        return candidates;
    }
}
