import java.util.HashSet;
import java.util.Scanner;
public class Borrarprod {
    static Scanner sc;
    public static void main(String[] args) {
        sc = new Scanner(System.in);
    }
    private static void borrarProd(HashSet<Producto> listaCompra, Scanner sc) {
        String nombre = solNombre(sc);
        Producto productoABorrar = new Producto(nombre, 0);

        if (listaCompra.remove(productoABorrar)) {
            System.out.println("Producto '" + nombre + "' eliminado correctamente.");
        } else {
            System.out.println("Producto '" + nombre + "' no encontrado en la lista.");
        }
    }
}
