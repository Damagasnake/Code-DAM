package ActividadesArrays;
import java.util.Scanner;
import java.util.Random;
public class Arrays4 {
    public static void main(String[] args) {
        int [][] matriz = crearArray();
        fillArr(matriz);
        showArr(matriz);
        swapRows(matriz);
        showArr(matriz);
    }
    public static int [][] crearArray(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el numero de filas ");
        int rows = Integer.parseInt(sc.nextLine());
        System.out.println("Introduce el numero de columnas");
        int cols = Integer.parseInt(sc.nextLine());
        sc.close();

        int [][] biex4 = new int[rows][cols];
        return biex4;
    }
    public static void fillArr(int [][] matriz){
        Random rd = new Random();
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = rd.nextInt(1, 10);
            }
        }
    }
    public static void showArr(int [][] matriz){
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println(" ");
        }
    }
    public static void swapRows(int [][] matriz){
        int [] swap;
        swap = matriz[0];
        matriz[0] = matriz[1];
        matriz[1] = swap;
    }
}
