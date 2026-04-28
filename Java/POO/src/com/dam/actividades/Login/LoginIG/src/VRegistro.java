import javax.swing.*;

public class VRegistro extends JFrame {
    private static final int ANCHO = 400;
    private static final int ALTO = 250;
    public JTextField txtUsuario;
    public JTextField txtPassword;
    public JButton btnRegistrar;
    public JButton btnCancelar;
    public static final String AC_BTN_REGISTRAR = "RegistrarRegistro";
    public static final String AC_BTN_CANCELAR = "CancelarRegistro";

    public VRegistro() {
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("Registro de Actividades");
        setSize(ANCHO, ALTO);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void crearComponentes() {
        getContentPane().setLayout(null);

        JLabel lblInfo = new JLabel("Registrar Nuevo Usuario");
        lblInfo.setBounds(10, 10, 200, 20);
        getContentPane().add(lblInfo);

        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setBounds(10, 40, 100, 20);
        getContentPane().add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(100, 40, 200, 20);
        txtUsuario.setToolTipText("Ingrese su usuario");
        getContentPane().add(txtUsuario);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(10, 70, 100, 20);
        getContentPane().add(lblPassword);

        txtPassword = new JTextField();
        txtPassword.setBounds(100, 70, 200, 20);
        txtPassword.setToolTipText("Ingrese su password");
        getContentPane().add(txtPassword);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setActionCommand(AC_BTN_REGISTRAR);
        btnRegistrar.setBounds(10, 110, 100, 20);
        getContentPane().add(btnRegistrar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setActionCommand(AC_BTN_CANCELAR);
        btnCancelar.setBounds(120, 110, 100, 20);
        getContentPane().add(btnCancelar);
    }

    public Usuario obtenerDatos() {
        String usuario = txtUsuario.getText().trim();
        String password = txtPassword.getText();
        if (usuario.isEmpty() || password.isEmpty()) {
            return null;
        }
        return new Usuario(usuario, password);
    }
}

