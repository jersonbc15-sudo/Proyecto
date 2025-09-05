/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package insertionsortjav;

/**
 *
 * @author LAB-USR-AREQUIPA
 */
import javax.swing.JOptionPane;

public class InsertionSort {
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int clave = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > clave) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = clave;
        }
    }

    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6};

        insertionSort(arr);

        // Construir el mensaje con el array ordenado
        StringBuilder resultado = new StringBuilder("Array ordenado:\n");
        for (int num : arr) {
            resultado.append(num).append(" ");
        }

        // Mostrar cuadro emergente con el resultado
        JOptionPane.showMessageDialog(null, resultado.toString(), "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }
}
