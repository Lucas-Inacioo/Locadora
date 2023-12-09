package ModuloGerente;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Relatorio {
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
