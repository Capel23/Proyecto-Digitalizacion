
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String archivo = "gps_data.csv";
        GPSGenerador.generarDatosCSV(archivo);
        List<GPSDatos> datos = GPSAnalisis.leerDatos("csv/gps_data.csv");
        Menu.mostrarMenu(datos);

    }
}
