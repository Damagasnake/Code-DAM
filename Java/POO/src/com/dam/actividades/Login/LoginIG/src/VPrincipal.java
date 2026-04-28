import javax.swing.*;

public class VPrincipal extends JFrame {
    public VPrincipal() {
        setTitle("Ventana Principal");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblBienvenido = new JLabel("Bienvenido al sistema", SwingConstants.CENTER);
        getContentPane().add(lblBienvenido);
    }
}

