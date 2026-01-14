import java.util.ArrayList;

public class ArrL {
    public static void main(String[] args) {
        int n = 100;
        ArrayList<Integer> damaga = new ArrayList<>(n);
        damaga.add(9);
        System.out.println(damaga);
        damaga.addFirst(1000);
        damaga.set(50, 100);
        System.out.println("hi");
    }
}

