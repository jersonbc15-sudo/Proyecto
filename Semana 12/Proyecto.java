package ordenamientosventana;

import javax.swing.JOptionPane;
import java.util.Arrays;

public class OrdenamientosVentana {

    public static void main(String[] args) {

        String[] opciones = {"Quick Sort", "Shell Sort"};
        int seleccion = JOptionPane.showOptionDialog(
                null,
                "Elige un método de ordenación:",
                "Menú de Ordenamientos",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        String input = JOptionPane.showInputDialog(
                null,
                "Ingresa números separados por comas:\nEjemplo: 8,3,10,2,6"
        );

        String[] parts = input.split(",");
        int[] arr = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i].trim());
        }

        if (seleccion == 0) {
            quickSort(arr, 0, arr.length - 1);
            JOptionPane.showMessageDialog(null,
                    "Ordenado con QUICK SORT:\n" + Arrays.toString(arr));
        } else {
            shellSort(arr);
            JOptionPane.showMessageDialog(null,
                    "Ordenado con SHELL SORT:\n" + Arrays.toString(arr));
        }
    }

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    public static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void shellSort(int[] arr) {
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j = i;
                while (j >= gap && arr[j - gap] > temp) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = temp;
            }
        }
    }
}
