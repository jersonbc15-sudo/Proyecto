import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class BusquedaTexto {

    public static void buscarPalabraEnArchivo(String nombreArchivo, String palabra) {
        int lineasLeidas = 0;
        int lineasConPalabra = 0;
        int totalPalabrasEncontradas = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                lineasLeidas++;

                if (contienePalabra(linea, palabra)) {
                    lineasConPalabra++;
                    int ocurrencias = contarOcurrencias(linea, palabra);
                    totalPalabrasEncontradas += ocurrencias;

                    System.out.println("Línea " + lineasLeidas + ": " + linea);
                    System.out.println("  Ocurrencias en esta línea: " + ocurrencias);
                }
            }

            System.out.println("\n===== Resumen =====");
            System.out.println("Total líneas leídas: " + lineasLeidas);
            System.out.println("Líneas que contienen la palabra '" + palabra + "': " + lineasConPalabra);
            System.out.println("Total de ocurrencias encontradas: " + totalPalabrasEncontradas);

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public static boolean contienePalabra(String linea, String palabra) {
        return linea.toLowerCase().contains(palabra.toLowerCase());
    }

    public static int contarOcurrencias(String linea, String palabra) {
        String lineaMinuscula = linea.toLowerCase();
        String palabraMinuscula = palabra.toLowerCase();

        int contador = 0;
        int index = 0;

        while ((index = lineaMinuscula.indexOf(palabraMinuscula, index)) != -1) {
            contador++;
            index += palabraMinuscula.length();
        }

        return contador;
    }

    public static String pedirRutaArchivo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la ruta del archivo de texto: ");
        return scanner.nextLine();
    }

    public static String pedirPalabra() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la palabra a buscar: ");
        return scanner.nextLine();
    }

    public static void mostrarMenu() {
        System.out.println("=====================================");
        System.out.println(" Sistema de búsqueda en archivo");
        System.out.println("=====================================");
        System.out.println("1. Buscar palabra en archivo");
        System.out.println("2. Salir");
        System.out.println("=====================================");
        System.out.print("Seleccione una opción: ");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    String ruta = pedirRutaArchivo();
                    String palabra = pedirPalabra();
                    buscarPalabraEnArchivo(ruta, palabra);
                    System.out.println();
                    break;

                case "2":
                    System.out.println("Gracias por usar el programa. ¡Adiós!");
                    salir = true;
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }
        }

        scanner.close();
    }
}
