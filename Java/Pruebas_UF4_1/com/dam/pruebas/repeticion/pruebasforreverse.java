public class pruebasforreverse {
    static final int LIM_INF = 50;
    static final int LIM_MAX = 100;
    static final int SALTO = 2;
    public static void main(String[] args) {
        for(int i = LIM_MAX; i <= LIM_INF; i-= SALTO)
            System.out.println(i);
    }
}
