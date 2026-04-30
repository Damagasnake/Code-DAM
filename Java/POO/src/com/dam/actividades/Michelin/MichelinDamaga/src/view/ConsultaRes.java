package view;

import javax.swing.*;


public class ConsultaRes extends JFrame {
    private static int ANCHO = 600;
    private static int ALTO = 400;
    public static int insetsR;
    public static int insetsL;
    public static int insetsT;
    public static int insetsB;
    public static int menuH;
    private JScrollPane scrpContenedor;
    private static final String AC_BTN_Cons = "Consultar";
    private static final String AC_BTN_Del = "Eliminar";

    public void VPrincipalRes(){
        configVentana();
        crearComponentes();
    }

    private void crearComponentes() {

    }
    private void configVentana() {
        setTitle("R E S T A U R A N T E S");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(ANCHO, ALTO);

        insetsR = this.getInsets().right;
        insetsL = this.getInsets().left;
        insetsT = this.getInsets().top;
        insetsB = this.getInsets().bottom;
    }

}
