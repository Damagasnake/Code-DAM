import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class loginControlador implements ActionListener {
    private VLogin vl;
    private VPrincipal vp;
    private VRegistro vr;
    private UsuariosDAO usuariosDAO;

    public loginControlador(VLogin vl, VPrincipal vp){
        this.vl = vl;
        this.vp = vp;
        this.usuariosDAO = new UsuariosDAO();

        // Asignar los listeners de los botones del VLogin
        this.vl.btnAcceder.addActionListener(this);
        this.vl.btnRegistro.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String comando = e.getActionCommand();
        if (comando.equals(VLogin.AC_BTN_ACC)){
            String usuario = vl.txtUsuario.getText().trim();
            String password = vl.txtPassword.getText();

            if (usuariosDAO.validarCredenciales(usuario, password)) {
                JOptionPane.showMessageDialog(vl, "Login correcto");
                // Si hubiera VPrincipal, vp.setVisible(true) se haría aquí
            } else {
                JOptionPane.showMessageDialog(vl, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (comando.equals(VLogin.AC_BTN_REG)){
            if (vr == null) {
                vr = new VRegistro();
                vr.btnRegistrar.addActionListener(this);
                vr.btnCancelar.addActionListener(this);
            }
            vr.setVisible(true);
        } else if (comando.equals(VRegistro.AC_BTN_REGISTRAR)){
            Usuario nuevoUsuario = vr.obtenerDatos();
            if (nuevoUsuario == null) {
                JOptionPane.showMessageDialog(vr, "Por favor complete todos los datos", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (usuariosDAO.usuarioExiste(nuevoUsuario.getUsuario())) {
                JOptionPane.showMessageDialog(vr, "El usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (usuariosDAO.insertarUsuario(nuevoUsuario)) {
                JOptionPane.showMessageDialog(vr, "Usuario registrado con éxito");
                vr.dispose();
            } else {
                JOptionPane.showMessageDialog(vr, "Error al registrar usuario", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (comando.equals(VRegistro.AC_BTN_CANCELAR)){
            vr.dispose();
        }
    }
}
