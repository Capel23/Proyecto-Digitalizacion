
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class GPSInterfaz{

    public static void main(String[] args) {
        JFrame frame = new JFrame("Análisis de GPS de Autobuses");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(0, 1, 10, 10));

        JButton btnGenerar = new JButton("Generar CSV");
        JButton btnAnalizar = new JButton("Analizar Datos");
        JTextArea resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);

        JScrollPane scroll = new JScrollPane(resultadoArea);

        btnGenerar.addActionListener(e -> {
            GPSGenerador.generarDatosCSV("gps_data.csv");
            resultadoArea.setText("CSV generado correctamente.\n");
        });

        btnAnalizar.addActionListener(e -> {
            List<GPSDatos> datos = GPSAnalisis.leerDatos("csv/gps_data.csv");

            StringBuilder sb = new StringBuilder();
            for (String bus : new String[]{"BUS01", "BUS02", "BUS03"}) {
                double media = GPSAnalisis.calcularVelocidadMedia(datos, bus);
                long paradas = GPSAnalisis.contarParadas(datos, bus);
                sb.append(bus).append(":\n");
                sb.append("  Velocidad media: ").append(String.format("%.2f", media)).append(" km/h\n");
                sb.append("  Paradas: ").append(paradas).append("\n");

                GPSAnalisis.exportarJSON(datos, bus);
                sb.append("  JSON exportado a json/").append(bus.toLowerCase()).append("_status.json\n\n");
            }

            resultadoArea.setText(sb.toString());
        });

        frame.add(btnGenerar);
        frame.add(btnAnalizar);
        frame.add(scroll);

        frame.setVisible(true);
    }
}
