public class pruebasArraysBi {
    public static void main(String[] args) {
        int[][] bi = new int[3][4]; // Bidimensional reg.
        int[][] bi2 = new int[2][];
        for (int i = 0; i < bi2.length; i++) {
            bi2[i] = new int[i + 1];
        }
        int[][] bi3 = { { 1, 3, 5, 6, 7 }, { 1, 3 }, { 10 }, { 1, 2, 3 } };
        for (int i = 0; i < bi3.length; i++) {
            for (int j = 0; j < bi3[i].length; j++) {
                System.out.print(bi3[i][j] + " ");
            }
            System.out.println();
        }
        for (int i = 0; i < bi.length; i++) {
            for (int j = 0; j < bi[i].length; j++) {
                bi[i][j] = i + j;
            }
        }
        bi.clone();
    }
}
