package ModuloGerente;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Classe responsável por gerar relatórios diversos para o sistema de gerenciamento de veículos.
 */
public class Relatorio {
    /**
     * Gera um relatório com o total de reservas registradas.
     * @return Uma string representando o total de reservas.
     */
    public static String generateTotalReservationsReport() {
        try {
            long totalReservations = Files.lines(Paths.get("database/reservas.tsv"))
                                          .count();
            return "Total Reservations: " + totalReservations;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating report";
        }
    }

    /**
     * Gera um relatório com o total de receita obtida.
     * Considera apenas as reservas com status "CONCLUIDA" ou "EFETIVADA".
     * @return Uma string representando o total de receita.
     */
    public static String generateTotalRevenueReport() {
        try {
            double totalRevenue = Files.lines(Paths.get("database/reservas.tsv"))
                                       .map(line -> line.split("\t"))
                                       .filter(parts -> parts[6].equals("CONCLUIDA") || parts[6].equals("EFETIVADA"))
                                       .mapToDouble(parts -> Double.parseDouble(parts[5]))
                                       .sum();
            return "Total Revenue: " + totalRevenue;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating report";
        }
    }

    /**
     * Gera um relatório mostrando o uso de cada veículo com base nas reservas.
     * @return Uma string com a frequência de uso de cada veículo.
     */
    public static String generateVehicleUsageReport() {
        try {
            Map<String, Long> vehicleUsage = Files.lines(Paths.get("database/reservas.tsv"))
                                                .map(line -> line.split("\t")[2]) // placa is the third field
                                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            return vehicleUsage.entrySet()
                            .stream()
                            .map(entry -> entry.getKey() + ": " + entry.getValue())
                            .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating report";
        }
    }

    /**
     * Gera um relatório mostrando a contagem de reservas por status.
     * @return Uma string com a contagem de reservas para cada status.
     */
    public static String generateReservationStatusReport() {
        try {
            Map<String, Long> statusCounts = Files.lines(Paths.get("database/reservas.tsv"))
                                                  .map(line -> line.split("\t")[6]) // status is the seventh field
                                                  .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    
            return statusCounts.entrySet()
                               .stream()
                               .map(entry -> "Status " + entry.getKey() + ": " + entry.getValue())
                               .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating report";
        }
    }

    /**
     * Gera um relatório mostrando o status atual de cada veículo.
     * @return Uma string com o status de cada veículo.
     */
    public static String generateVehicleStatusReport() {
        try {
            return Files.lines(Paths.get("database/veiculo.tsv"))
                        .map(line -> line.split("\t"))
                        .map(parts -> "Placa: " + parts[0] + ", Status: " + parts[6])
                        .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating report";
        }
    }

    /**
     * Gera um relatório mostrando a distribuição de clientes por faixa etária.
     * As faixas etárias são categorizadas como: "Menos de 20", "21-40", "41-60" e "Acima de 60".
     * @return Uma string com a contagem de clientes em cada faixa etária.
     */
    public static String generateClientAgeGroupReport() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            return Files.lines(Paths.get("database/clienteS.tsv"))
                        .map(line -> line.split("\t"))
                        .map(parts -> {
                            LocalDate dob = LocalDate.parse(parts[2], formatter);
                            int age = LocalDate.now().getYear() - dob.getYear();
                            if (dob.plusYears(age).isAfter(LocalDate.now())) {
                                age--;
                            }
                            return age;
                        })
                        .collect(Collectors.groupingBy(age -> {
                            if (age < 20) return "Menos de 20";
                            else if (age <= 40) return "21-40";
                            else if (age <= 60) return "41-60";
                            else return "Acima de 60";
                        }, Collectors.counting()))
                        .entrySet()
                        .stream()
                        .map(entry -> entry.getKey() + ": " + entry.getValue())
                        .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating report";
        }
    }
}
