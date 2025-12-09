package ActividadesArrays;

public class ArraySim {
    static final int FILAS = 4;
    static final int COLS = 4;
    public static void main(String[] args) {
        int [][] matriz = crearArray();
        rellenarMatriz(matriz);
        mostrarArray(matriz);
        isSim(matriz);
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
    public static void isSim(int [][] matriz){
        boolean isSime = true;
        for (int i = 0; i < matriz.length - 1 && isSime; i++) {
            for (int j = i + 1; j < matriz[i].length && isSime; j++) {
                if(matriz[i][j] != matriz[j][i]){
                    isSime = false;
                    
                }
            }
        }
        if (isSime) {
            System.out.println("Simetrica");
        }else{
            System.out.println("No Simetrica");
        }
    }
}
