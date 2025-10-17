import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ArchivoBusquedaDetallada {

    private Map<String, Set<String>> indiceInvertido;

    public ArchivoBusquedaDetallada() {
        indiceInvertido = new HashMap<>();
    }

    public void construirIndice(List<String> archivos) throws IOException {
        for (String archivo : archivos) {
            List<String> lineas = Files.readAllLines(Paths.get(archivo));
            for (String linea : lineas) {
                String[] palabras = linea.toLowerCase().split("\\W+");
                for (String palabra : palabras) {
                    if (palabra.isEmpty()) continue;
                    indiceInvertido
                        .computeIfAbsent(palabra, k -> new HashSet<>())
                        .add(archivo);
                }
            }
        }
    }

    public Set<String> buscar(String palabra) {
        return indiceInvertido.getOrDefault(palabra.toLowerCase(), Collections.emptySet());
    }

    public void mostrarIndice() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Set<String>> entry : indiceInvertido.entrySet()) {
            sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(400, 300));
        JOptionPane.showMessageDialog(null, scrollPane, "Índice Invertido", JOptionPane.INFORMATION_MESSAGE);
    }

    private static List<String> crearArchivosDePrueba() throws IOException {
        List<String> rutas = new ArrayList<>();

        String[] nombres = {"texto1.txt", "texto2.txt"};
        String[] contenidos = {
            "Hola mundo. Este es el primer archivo de ejemplo. Contiene palabras como sol y luna. El sol brilla mucho.",
            "Segundo archivo de texto. Aquí también está la palabra sol junto con estrellas y cielo azul."
        };

        for (int i = 0; i < nombres.length; i++) {
            Path ruta = Paths.get(nombres[i]);
            Files.write(ruta, contenidos[i].getBytes());
            rutas.add(ruta.toAbsolutePath().toString());
        }

        return rutas;
    }

    public static void main(String[] args) {
        ArchivoBusquedaDetallada buscador = new ArchivoBusquedaDetallada();
        List<String> archivos = new ArrayList<>();

        try {
            int opcion = JOptionPane.showConfirmDialog(null,
                    "¿Desea crear archivos de prueba automáticamente?",
                    "Archivos de prueba",
                    JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                archivos = crearArchivosDePrueba();
                JOptionPane.showMessageDialog(null,
                        "Se crearon los archivos de prueba:\n" + String.join("\n", archivos),
                        "Archivos creados",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                String entradaArchivos = JOptionPane.showInputDialog(null,
                        "Ingrese las rutas de los archivos separados por coma:",
                        "Archivos para indexar",
                        JOptionPane.QUESTION_MESSAGE);

                if (entradaArchivos == null || entradaArchivos.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No se ingresaron archivos. Saliendo.");
                    return;
                }

                for (String archivo : entradaArchivos.split(",")) {
                    archivos.add(archivo.trim());
                }
            }

            buscador.construirIndice(archivos);
            buscador.mostrarIndice();

            String palabra = JOptionPane.showInputDialog(null,
                    "Ingrese la palabra a buscar:",
                    "Buscar palabra",
                    JOptionPane.QUESTION_MESSAGE);

            if (palabra == null || palabra.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se ingresó palabra para buscar. Saliendo.");
                return;
            }

            Set<String> resultados = buscador.buscar(palabra.trim());

            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "No se encontraron resultados para '" + palabra + "'",
                        "Resultados",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("La palabra '").append(palabra).append("' aparece en los archivos:\n");
                for (String archivo : resultados) {
                    sb.append(" - ").append(archivo).append("\n");
                }
                JOptionPane.showMessageDialog(null, sb.toString(), "Resultados", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Error leyendo archivos: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
