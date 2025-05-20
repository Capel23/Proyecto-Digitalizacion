
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class GPSAnalisis {

    public static List<GPSDatos> leerDatos(String archivoCSV) {
        List<GPSDatos> datos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            br.readLine(); // Saltar cabecera
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                String busId = partes[0];
                LocalDateTime timestamp = LocalDateTime.parse(partes[1]);
                double lat = Double.parseDouble(partes[2]);
                double lon = Double.parseDouble(partes[3]);
                double speed = Double.parseDouble(partes[4]);
                datos.add(new GPSDatos(busId, timestamp, lat, lon, speed));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datos;
    }

    public static double calcularVelocidadMedia(List<GPSDatos> lista, String busId) {
        return lista.stream()
                .filter(d -> d.getBusId().equals(busId))
                .mapToDouble(GPSDatos::getSpeed)
                .average()
                .orElse(0.0);
    }

    public static long contarParadas(List<GPSDatos> lista, String busId) {
        return lista.stream()
                .filter(d -> d.getBusId().equals(busId))
                .filter(d -> d.getSpeed() == 0.0)
                .count();
    }

    public static void exportarJSON(List<GPSDatos> lista, String busId) {
        List<GPSDatos> delBus = lista.stream()
                .filter(d -> d.getBusId().equals(busId))
                .sorted(Comparator.comparing(GPSDatos::getTimestamp).reversed())
                .collect(Collectors.toList());

        if (!delBus.isEmpty()) {
            GPSDatos ultima = delBus.get(0);
            String json = "{\n" +
                    "  \"busId\": \"" + ultima.getBusId() + "\",\n" +
                    "  \"latitude\": " + ultima.getLatitude() + ",\n" +
                    "  \"longitude\": " + ultima.getLongitude() + ",\n" +
                    "  \"timestamp\": \"" + ultima.getTimestamp() + "\"\n" +
                    "}";

            // Crear carpeta json si no existe
            File carpetaJson = new File("json");
            if (!carpetaJson.exists()) {
                carpetaJson.mkdirs();
            }

            String rutaArchivoJson = "json/" + busId.toLowerCase() + "_status.json";

            try (FileWriter fw = new FileWriter(rutaArchivoJson)) {
                fw.write(json);
                System.out.println("Archivo JSON generado: " + rutaArchivoJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
