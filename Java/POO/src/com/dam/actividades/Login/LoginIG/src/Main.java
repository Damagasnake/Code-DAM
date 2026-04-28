import java.awt.*;
public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                VLogin vl = new VLogin();
                VPrincipal vp = new VPrincipal();
                loginControlador controlador = new loginControlador(vl, vp);

                vl.setVisible(true);
            }
        });
    }
}