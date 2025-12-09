
package ActividadesArrays;

public class Array1 {
    static final int FILAS = 4;
    static final int COLS = 4;
    public static void main(String[] args) {
        int [][] matriz = crearArray();
        rellenarMatriz(matriz);
        mostrarArray(matriz);
    }
    public static int [][] crearArray(){
        int [][] matriz = new int[FILAS][COLS];
        return matriz;
    }
    private static void rellenarMatriz(int [][] matriz){
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = i*2 + j*4;
            }
        }
    }
    private static void mostrarArray(int [][] matriz){
        System.out.println("Matriz");
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.println(matriz[i][j]);
            }
        }
    }
}
