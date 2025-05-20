
import java.util.List;
import java.util.Scanner;

public class Menu {
    public static void mostrarMenu(List<GPSDatos> datos) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n--- Menú Seguimiento GPS ---");
            System.out.println("1. Ver velocidad media por autobús");
            System.out.println("2. Ver número de paradas por autobús");
            System.out.println("3. Exportar JSON con posición actual");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    for (String busId : List.of("BUS01", "BUS02", "BUS03")) {
                        double media = GPSAnalisis.calcularVelocidadMedia(datos, busId);
                        System.out.printf("%s: %.2f km/h\n", busId, media);
                    }
                    break;
                case 2:
                    for (String busId : List.of("BUS01", "BUS02", "BUS03")) {
                        long paradas = GPSAnalisis.contarParadas(datos, busId);
                        System.out.printf("%s: %d paradas\n", busId, paradas);
                    }
                    break;
                case 3:
                    for (String busId : List.of("BUS01", "BUS02", "BUS03")) {
                        GPSAnalisis.exportarJSON(datos, busId);
                    }
                    break;
                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 4);
    }
}

