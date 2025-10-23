public class forsum50 {
    static final int MIN_NUM = 0;
    static final int MAX_NUM = 50;
    static final int MIN_NUMIN = 1;
    static final int SALTO = 2;
public static void main(String[] args) {
    //PARES
    
    int sumapares = 0;
    for (int i = MIN_NUM; i <= MAX_NUM; i +=SALTO)
    {
        sumapares = sumapares + i;
    }
    System.out.println("la suma de los pares es " + sumapares);
 
    //IMPARES 
    int sumaimpares = 0;
    for (int i = MIN_NUMIN; i <= MAX_NUM; i +=SALTO)
    {
        sumaimpares = sumaimpares + i;
    }
    System.out.println("la suma de los impares es " + sumaimpares);
}
}
