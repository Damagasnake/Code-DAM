import javax.swing.*;

public class VLogin extends JFrame {
    private static final int ANCHO = 400;
    private static final int ALTO = 250;
    JTextField txtUsuario;
    JTextField txtPassword;
    public JButton btnAcceder;
    public JButton btnRegistro;
    public static final String AC_BTN_ACC = "Acceder";
    public static final String AC_BTN_REG = "Registro";

    public VLogin() {
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
    }

    private void crearComponentes() {
        getContentPane().setLayout(null);

        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setBounds(10, 10, 100, 20);
        getContentPane().add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(100, 10, 200, 20);
        txtUsuario.setToolTipText("Ingrese su usuario");
        getContentPane().add(txtUsuario);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(10, 40, 100, 20);
        getContentPane().add(lblPassword);
        txtPassword = new JTextField();
        txtPassword.setBounds(100, 40, 200, 20);
        txtPassword.setToolTipText("Ingrese su password");
        getContentPane().add(txtPassword);

        btnAcceder = new JButton(AC_BTN_ACC);
        btnAcceder.setBounds(10, 70, 100, 20);
        getContentPane().add(btnAcceder);

        btnRegistro = new JButton(AC_BTN_REG);
        btnRegistro.setBounds(120, 70, 100, 20);
        getContentPane().add(btnRegistro);
    }

    public Usuario obtenerDatos() {
        String usuario = txtUsuario.getText().trim();
        String password = "";
        if (usuario.isEmpty()) {
            System.out.println("Usuario vacio");
            return null;
        } else {
            password = txtPassword.getText();
            if (password.isEmpty()) {
                System.out.println("Password vacio");
                return null;
            } else {
                return new Usuario(usuario, password);
            }
        }
    }
}
