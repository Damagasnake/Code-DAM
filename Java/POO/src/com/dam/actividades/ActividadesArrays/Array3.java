package ActividadesArrays;
public class Array3 {
        static final int FILAS = 4;
    static final int COLS = 4;
    public static void main(String[] args) {
        int [][] matriz = crearArray();
        rellenarMatriz(matriz);
        mostrarArray(matriz);
        mayorVal(matriz);
    }
    public static int [][] crearArray(){
        int [][] matriz = new int[FILAS][COLS];
        return matriz;
    }
    private static void rellenarMatriz(int [][] matriz){
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = i + j;
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
    private static void mayorVal(int [][] matriz){
        int bigCol = 0;
        int bigRow = 0;
        int bigN = Integer.MIN_VALUE;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if(matriz[i][j] > bigN)
                {
                    bigN = matriz[i][j];
                    bigCol = j + 1;
                    bigRow = i + 1;
                }
            }
        }
        System.out.println("El numero mas grande es " + bigN + "\n" + "Se encuentra en " + bigRow + " y " + bigCol);
    }
}