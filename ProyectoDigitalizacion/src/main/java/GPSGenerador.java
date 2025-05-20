
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

public class GPSGenerador {

    /**
     * Genera un archivo CSV con datos simulados de autobuses.
     * El archivo se guarda en la carpeta "csv/".
     *
     * @param nombreArchivoSoloNombre Por ejemplo: "gps_data.csv"
     */
    public static void generarDatosCSV(String nombreArchivoSoloNombre) {
        // Crear carpeta "csv" si no existe
        File carpeta = new File("csv");
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        // Ruta completa del archivo
        String rutaCompleta = "csv/" + nombreArchivoSoloNombre;

        try (FileWriter writer = new FileWriter(rutaCompleta)) {
            writer.write("busId,timestamp,latitude,longitude,speed\n");
            String[] buses = {"BUS01", "BUS02", "BUS03"};
            LocalDateTime inicio = LocalDateTime.of(2025, 3, 25, 8, 0);
            Random rand = new Random();

            for (String busId : buses) {
                for (int i = 0; i < 60; i++) {
                    LocalDateTime timestamp = inicio.plusMinutes(i);
                    double lat = 40.40 + rand.nextDouble() * 0.02;
                    double lon = -3.70 + rand.nextDouble() * 0.02;
                    double speed = rand.nextDouble() < 0.1 ? 0 : 20 + rand.nextDouble() * 40;
                    writer.write(busId + "," + timestamp + "," + lat + "," + lon + "," + String.format("%.2f", speed) + "\n");
                }
            }

            System.out.println("Archivo CSV generado: " + rutaCompleta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
