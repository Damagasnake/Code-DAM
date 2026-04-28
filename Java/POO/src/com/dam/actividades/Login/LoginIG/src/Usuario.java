public class Usuario {
    private String usuario;
    private String password;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con parámetros
    public Usuario(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    // Getters
    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "usuario='" + usuario + '\'' +
                ", password='" + (password != null ? "****" : "null") + '\'' +
                '}';
    }
}
