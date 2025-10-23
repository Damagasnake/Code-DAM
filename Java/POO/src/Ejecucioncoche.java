import com.dam.pojo.Coche;
public class Ejecucioncoche {
    public static void main(String[] args) {
        Coche objetoCoche = new Coche();
        objetoCoche.setMatricula("AAAAAA97");
        objetoCoche.setVelocidad(50);
        System.out.println("la matricula de mi coche es: " + objetoCoche.getMatricula());
        objetoCoche.acelera(100);
        System.out.println("Mi velocidad es " + objetoCoche.getVelocidad() + "km/h");
    }
}
