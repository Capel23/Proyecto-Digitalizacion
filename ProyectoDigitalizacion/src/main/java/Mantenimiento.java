import java.util.List;

public class Mantenimiento {
    public static void modificarRuta(List<GPSDatos> datos, String busId) {
        for (GPSDatos d : datos) {
            if (d.getBusId().equals(busId)) {
                // Cambiar coordenadas como ejemplo de modificación
                d = new GPSDatos(d.getBusId(), d.getTimestamp(),
                        d.getLatitude() + 0.001, d.getLongitude() + 0.001,
                        d.getSpeed());
            }
        }
        System.out.println("Datos modificados para " + busId);
    }
}

