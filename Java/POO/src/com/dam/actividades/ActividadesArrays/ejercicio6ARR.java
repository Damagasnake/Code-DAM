package ActividadesArrays;

import java.util.Scanner;
import java.util.Random;

public class ejercicio6ARR {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Introduce el tamaño n de la matriz (nxn): ");
        int n = Integer.parseInt(sc.nextLine());

        int[][] matriz = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matriz[i][j] = random.nextInt(0, 11);
            }
        }
        boolean esTriangularSuperior = true;

        for (int i = 0; i < n && esTriangularSuperior; i++) {
            for (int j = 0; j < i && esTriangularSuperior; j++) {
                if (matriz[i][j] != 0) {
                    esTriangularSuperior = false;
                }
            }
        }
        if (esTriangularSuperior) {
            System.out.println("\n La matriz ES triangular superior.");
        } else {
            System.out.println("\n La matriz NO es triangular superior.");
        }

        sc.close();
    }
}
