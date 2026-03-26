import javax.swing.WindowConstants;

public class IVIpoteca {
    public void configVentana() {
        setTitle("Calculadora de hipoteca");
        setSize(400, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void configComponentes() {
        getContentPane().setLayout(null);
    }

    public void hacerVisible() {
        setVisible(true);
    }

}
