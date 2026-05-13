# Guía de Examen — Aplicación JavaSwing + SQLite (Patrón MVC)

> Esta guía explica cómo rellenar la plantilla del examen archivo por archivo.
> El proyecto de referencia es **ActividadUF10_2_Guia_Michelin** (Guía Michelin).
> Adapta los nombres de clases, campos y constantes a los datos de **tu** examen.

---

## Estructura del proyecto

```
src/com/michelin/
├── main/
│   └── RestaurantesMain.java          ← punto de entrada
├── control/
│   └── RestaurantesListener.java      ← controlador (ActionListener)
├── view/
│   ├── VPRestaurantes.java            ← ventana principal (JFrame)
│   ├── PConsultarRestaurantes.java    ← panel consulta
│   ├── PRegistrarRestaurante.java     ← panel alta
│   └── PModificarRestaurante.java     ← panel modificación
└── model/
    ├── data/
    │   ├── Restaurante.java           ← entidad/POJO
    │   └── Texto.java                 ← constantes de texto
    └── db/
        ├── AccesoDB.java              ← gestión de conexión
        ├── RestaurantesContract.java  ← nombres de columnas
        └── RestaurantesDAO.java       ← operaciones CRUD

BBDD/
├── GUIA_MICHELIN.db                   ← base de datos SQLite
└── ConfiguracionDB.properties         ← configuración de conexión

lib/
└── sqlite-jdbc-3.51.3.0.jar           ← driver JDBC SQLite
```

---

## Orden de implementación recomendado

```
1. ConfiguracionDB.properties
2. RestaurantesContract.java
3. Restaurante.java  (+ Texto.java)
4. AccesoDB.java
5. RestaurantesDAO.java
6. VPRestaurantes.java
7. PConsultarRestaurantes.java
8. PRegistrarRestaurante.java
9. PModificarRestaurante.java
10. RestaurantesListener.java
11. RestaurantesMain.java
```

---

## Archivo 1 — `ConfiguracionDB.properties`

Configuración externa de la base de datos. No requiere recompilar para cambiar la ruta.

```properties
DRIVER=org.sqlite.JDBC
URL=jdbc:sqlite:BBDD/GUIA_MICHELIN.db
```

> **Clave `TODO`:** cambia el nombre del archivo `.db` para que coincida con el de tu examen.

---

## Archivo 2 — `RestaurantesContract.java`

Clase de constantes con los nombres exactos de tabla y columnas de la BD.
Evita errores de tipografía en las queries SQL.

```java
package com.michelin.model.db;

public class RestaurantesContract {

    // nombre de la tabla
    public static final String NOM_TABLA    = "RESTAURANTES";

    // columnas — deben coincidir exactamente con el esquema SQLite
    public static final String COL_ID       = "ID";
    public static final String COL_NOMBRE   = "NOMBRE";
    public static final String COL_REGION   = "REGION";
    public static final String COL_CIUDAD   = "CIUDAD";
    public static final String COL_DISTINCION = "DISTINCION";
    public static final String COL_DIRECCION = "DIRECCION";
    public static final String COL_PREC_MIN = "PRECIO_MIN";
    public static final String COL_PREC_MAX = "PRECIO_MAX";
    public static final String COL_COCINA   = "COCINA";
    public static final String COL_TELEF    = "TELEFONO";
    public static final String COL_WEB      = "WEB";
}
```

> **`TODO`:** añade o elimina constantes según las columnas de tu tabla.

---

## Archivo 3 — `Restaurante.java`

POJO (Plain Old Java Object) que representa una fila de la tabla. Sin setters — es inmutable tras la creación.

```java
package com.michelin.model.data;

public class Restaurante {

    // arrays de datos predefinidos para combos/spinners
    public static final String[] REGIONES = {
        "Andalucía", "Aragón", "Asturias", "Islas Baleares", "Cantabria",
        "Islas Canarias", "Castilla - La Mancha", "Castilla y León", "Cataluña",
        "Galicia", "Extremadura", "Madrid", "Murcia", "Navarra",
        "País Vasco", "La Rioja", "Comunidad Valenciana"
    };

    public static final String[] DISTINCIONES = {"1 estrella", "2 estrellas", "3 estrellas"};
    public static final String[] TIPOS_COCINA  = {"Creativa", "Moderna", "Tradicional", "Regional", "Fusión"};

    // campos privados
    private int    id;
    private String nombre;
    private String region;
    private String ciudad;
    private int    distincion;
    private String direccion;
    private double precioMin;
    private double precioMax;
    private String cocina;
    private String telefono;
    private String web;

    // constructor completo (con id) — para SELECT
    public Restaurante(int id, String nombre, String region, String ciudad,
                       int distincion, String direccion,
                       double precioMin, double precioMax,
                       String cocina, String telefono, String web) {
        this.id        = id;
        this.nombre    = nombre;
        this.region    = region;
        this.ciudad    = ciudad;
        this.distincion = distincion;
        this.direccion = direccion;
        this.precioMin = precioMin;
        this.precioMax = precioMax;
        this.cocina    = cocina;
        this.telefono  = telefono;
        this.web       = web;
    }

    // constructor sin id — para INSERT (la BD asigna el ID)
    public Restaurante(String nombre, String region, String ciudad,
                       int distincion, String direccion,
                       double precioMin, double precioMax,
                       String cocina, String telefono, String web) {
        this.nombre    = nombre;
        this.region    = region;
        this.ciudad    = ciudad;
        this.distincion = distincion;
        this.direccion = direccion;
        this.precioMin = precioMin;
        this.precioMax = precioMax;
        this.cocina    = cocina;
        this.telefono  = telefono;
        this.web       = web;
    }

    // getters
    public int    getId()        { return id; }
    public String getNombre()    { return nombre; }
    public String getRegion()    { return region; }
    public String getCiudad()    { return ciudad; }
    public int    getDistincion() { return distincion; }
    public String getDireccion() { return direccion; }
    public double getPrecioMin() { return precioMin; }
    public double getPrecioMax() { return precioMax; }
    public String getCocina()    { return cocina; }
    public String getTelefono()  { return telefono; }
    public String getWeb()       { return web; }
}
```

> **`TODO`:** ajusta los campos al enunciado. Añade getters para todos los campos que añadas.

---

## Archivo 4 — `Texto.java`

Centraliza todos los literales de texto de la UI. Se referencia desde vistas y controlador.

```java
package com.michelin.model.data;

public class Texto {

    // mensajes de operación
    public static final String MSJ_REGISTRO          = "Se ha registrado el restaurante con éxito";
    public static final String MSJ_MODIFICACION      = "Se ha modificado el restaurante con éxito";
    public static final String MSJ_RESULT_ELIMINAR   = "Se ha eliminado el restaurante con éxito";
    public static final String MSJ_NOMBRE_DUP        = "Ya existe un restaurante con el nombre introducido";
    public static final String MSJ_CONSULTA          = "No se ha encontrado ningún restaurante para el dato introducido";
    public static final String MSJ_NO_DATOS          = "No se han encontrado datos para el filtro introducido";

    // títulos de diálogos
    public static final String TIT_MSJ_RESULTADO     = "Resultado de Operación";
    public static final String TIT_MSJ_CONSULTA      = "Información de consulta";
    public static final String TIT_ERROR_DATOS       = "Error de datos";
    public static final String TIT_ERROR_SEL         = "Error de selección";
    public static final String TIT_CONFIRM           = "Confirmación";

    // confirmaciones
    public static final String MSJ_CONFIR_SALIR      = "Se va a cerrar la aplicación ¿desea continuar?";
    public static final String MSJ_CONFIR_ELIMINAR   = "Se va a eliminar el registro seleccionado ¿desea continuar?";

    // errores de validación
    public static final String MSJ_ERROR_NOM         = "Debe introducir el nombre del restaurante";
    public static final String MSJ_ERROR_CIU         = "Debe introducir una ciudad";
    public static final String MSJ_NOMBRE_OBLIG      = "Debe introducir un nombre";
    public static final String MSJ_ERROR_SEL         = "Debe seleccionar el registro a eliminar";
    public static final String MSJ_ERROR_PRECIOS_1   = "El precio mínimo no puede ser mayor que el máximo";
    public static final String MSJ_ERROR_PRECIOS_2   = "El precio mínimo y el máximo deben ser numéricos";

    // menú
    public static final String MN_MTO                = "Mantenimiento Restaurantes";
    public static final String MNTM_CONSULTA         = "Consulta de Restaurantes";
    public static final String MNTM_REGISTRO         = "Registro de Restaurante";
    public static final String MNTM_MODIFICACION     = "Modificación de Restaurante";
    public static final String MNTM_SALIR            = "Salir";

    // etiquetas y botones
    public static final String LBL_TIT_CONSULTA      = "Consulta de Restaurantes";
    public static final String LBL_TIT_REGISTRO      = "Registrar Restaurante";
    public static final String LBL_TIT_MODIF         = "Modificar Restaurante";
    public static final String LBL_FILTRO            = "Filtro:";
    public static final String LBL_REGION            = "Región:";
    public static final String LBL_DISTINCION        = "Distinción:";
    public static final String LBL_NOMBRE            = "Nombre:";
    public static final String LBL_CIUDAD            = "Ciudad:";
    public static final String LBL_DIRECCION         = "Dirección:";
    public static final String LBL_TELEFONO          = "Teléfono:";
    public static final String LBL_WEB               = "Web:";
    public static final String LBL_COCINA            = "Cocina:";
    public static final String LBL_ESTRELLAS         = "estrellas";
    public static final String LBL_PRECIO_MIN        = "Precio mínimo:";
    public static final String LBL_PRECIO_MAX        = "máximo:";
    public static final String LBL_LISTADO           = "Listado de Restaurantes";
    public static final String BTN_CONSULTAR         = "Consultar";
    public static final String BTN_ELIMINAR          = "Eliminar";
    public static final String BTN_REGISTRAR         = "Guardar Datos";
    public static final String BTN_LIMPIAR           = "Limpiar Datos";
    public static final String BTN_GUARDAR           = "Guardar Datos";
    public static final String BTN_CANCELAR          = "Cancelar";
    public static final String BTN_BUSCAR            = "Buscar";

    // columnas tabla
    public static final String CLM_NOMBRE            = "NOMBRE";
    public static final String CLM_CIUDAD            = "CIUDAD";
    public static final String CLM_DISTINCION        = "DISTINCIÓN";
    public static final String CLM_COCINA            = "COCINA";
    public static final String CLM_PRECIO            = "PRECIO";

    // valor especial combos de filtro
    public static final String TODAS                 = "TODAS";
}
```

> **`TODO`:** añade las constantes que necesite tu proyecto. No escribas literales sueltos en las vistas.

---

## Archivo 5 — `AccesoDB.java`

Lee la configuración del `.properties` y devuelve objetos `Connection`.

```java
package com.michelin.model.db;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class AccesoDB {

    private String driver;
    private String url;

    public AccesoDB() {
        Properties prop = new Properties();
        InputStream is = null;

        try {
            is = new FileInputStream("BBDD/ConfiguracionDB.properties");
            prop.load(is);
            driver = prop.getProperty("DRIVER");
            url    = prop.getProperty("URL");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConexion() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(url);
    }
}
```

> **`TODO`:** la ruta del `.properties` es relativa al directorio de trabajo del proyecto (raíz del proyecto en Eclipse).

---

## Archivo 6 — `RestaurantesDAO.java`

Todas las operaciones con la base de datos. Cada método abre y cierra su propia conexión.

### Patrón base de un método SELECT

```java
public ArrayList<Restaurante> selectRestaurantes() {
    ArrayList<Restaurante> lista = new ArrayList<>();

    String query = "SELECT * FROM " + RestaurantesContract.NOM_TABLA;

    Connection con   = null;
    Statement  stmt  = null;
    ResultSet  rslt  = null;

    try {
        con  = acceso.getConexion();
        stmt = con.createStatement();
        rslt = stmt.executeQuery(query);

        while (rslt.next()) {
            Restaurante r = new Restaurante(
                rslt.getInt(RestaurantesContract.COL_ID),
                rslt.getString(RestaurantesContract.COL_NOMBRE),
                rslt.getString(RestaurantesContract.COL_REGION),
                rslt.getString(RestaurantesContract.COL_CIUDAD),
                rslt.getInt(RestaurantesContract.COL_DISTINCION),
                rslt.getString(RestaurantesContract.COL_DIRECCION),
                rslt.getDouble(RestaurantesContract.COL_PREC_MIN),
                rslt.getDouble(RestaurantesContract.COL_PREC_MAX),
                rslt.getString(RestaurantesContract.COL_COCINA),
                rslt.getString(RestaurantesContract.COL_TELEF),
                rslt.getString(RestaurantesContract.COL_WEB)
            );
            lista.add(r);
        }

    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (rslt != null) rslt.close();
            if (stmt != null) stmt.close();
            if (con  != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return lista;
}
```

### SELECT con filtros (`WHERE` dinámico)

```java
// devuelve restaurantes filtrando por región y/o distinción
// si el valor es "TODAS" no aplica ese filtro
public ArrayList<Restaurante> selectRestaurantesFiltro(String reg, String dist) {
    ArrayList<Restaurante> lista = new ArrayList<>();

    String query = "SELECT * FROM " + RestaurantesContract.NOM_TABLA;

    if (!reg.equals(Texto.TODAS) && !dist.equals(Texto.TODAS)) {
        query += " WHERE " + RestaurantesContract.COL_REGION    + " = ? AND "
               + RestaurantesContract.COL_DISTINCION + " = ?";
    } else if (!reg.equals(Texto.TODAS)) {
        query += " WHERE " + RestaurantesContract.COL_REGION    + " = ?";
    } else if (!dist.equals(Texto.TODAS)) {
        query += " WHERE " + RestaurantesContract.COL_DISTINCION + " = ?";
    }

    Connection        con  = null;
    PreparedStatement stmt = null;
    ResultSet         rslt = null;

    try {
        con  = acceso.getConexion();
        stmt = con.prepareStatement(query);

        if (!reg.equals(Texto.TODAS) && !dist.equals(Texto.TODAS)) {
            stmt.setString(1, reg);
            stmt.setInt(2, Character.getNumericValue(dist.charAt(0)));
        } else if (!reg.equals(Texto.TODAS)) {
            stmt.setString(1, reg);
        } else if (!dist.equals(Texto.TODAS)) {
            stmt.setInt(1, Character.getNumericValue(dist.charAt(0)));
        }

        rslt = stmt.executeQuery();

        while (rslt.next()) {
            // mapear igual que en selectRestaurantes()
        }

    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    } finally {
        // cerrar rslt, stmt, con
    }

    return lista;
}
```

### SELECT por nombre (LIKE)

```java
public Restaurante selectRestauranteNombre(String nombre) {
    Restaurante restaurante = null;

    String query = "SELECT * FROM " + RestaurantesContract.NOM_TABLA
                 + " WHERE " + RestaurantesContract.COL_NOMBRE + " LIKE ?";

    Connection        con   = null;
    PreparedStatement pstmt = null;
    ResultSet         rslt  = null;

    try {
        con   = acceso.getConexion();
        pstmt = con.prepareStatement(query);
        pstmt.setString(1, "%" + nombre + "%");

        rslt = pstmt.executeQuery();

        if (rslt.next()) {
            restaurante = new Restaurante(
                rslt.getInt(1), rslt.getString(2), rslt.getString(3),
                rslt.getString(4), rslt.getInt(5), rslt.getString(6),
                rslt.getDouble(7), rslt.getDouble(8),
                rslt.getString(9), rslt.getString(10), rslt.getString(11)
            );
        }

    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    } finally {
        // cerrar rslt, pstmt, con
    }

    return restaurante;
}
```

### INSERT

```java
public int insertRestaurante(Restaurante r) {
    int res = 0;

    String query = "INSERT INTO " + RestaurantesContract.NOM_TABLA + " ("
        + RestaurantesContract.COL_NOMBRE    + ", "
        + RestaurantesContract.COL_REGION    + ", "
        + RestaurantesContract.COL_CIUDAD    + ", "
        + RestaurantesContract.COL_DISTINCION + ", "
        + RestaurantesContract.COL_DIRECCION + ", "
        + RestaurantesContract.COL_PREC_MIN  + ", "
        + RestaurantesContract.COL_PREC_MAX  + ", "
        + RestaurantesContract.COL_COCINA    + ", "
        + RestaurantesContract.COL_TELEF     + ", "
        + RestaurantesContract.COL_WEB
        + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    Connection        con  = null;
    PreparedStatement stmt = null;

    try {
        con  = acceso.getConexion();
        stmt = con.prepareStatement(query);
        stmt.setString(1, r.getNombre());
        stmt.setString(2, r.getRegion());
        stmt.setString(3, r.getCiudad());
        stmt.setInt   (4, r.getDistincion());
        stmt.setString(5, r.getDireccion());
        stmt.setDouble(6, r.getPrecioMin());
        stmt.setDouble(7, r.getPrecioMax());
        stmt.setString(8, r.getCocina());
        stmt.setString(9, r.getTelefono());
        stmt.setString(10, r.getWeb());

        res = stmt.executeUpdate();   // devuelve 1 si se insertó, 0 si nombre duplicado

    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    } finally {
        // cerrar stmt, con
    }

    return res;
}
```

### UPDATE

```java
public int updateRestaurante(Restaurante r) {
    int res = 0;

    String query = "UPDATE " + RestaurantesContract.NOM_TABLA + " SET "
        + RestaurantesContract.COL_REGION    + " = ?, "
        + RestaurantesContract.COL_CIUDAD    + " = ?, "
        + RestaurantesContract.COL_DISTINCION + " = ?, "
        + RestaurantesContract.COL_DIRECCION + " = ?, "
        + RestaurantesContract.COL_PREC_MIN  + " = ?, "
        + RestaurantesContract.COL_PREC_MAX  + " = ?, "
        + RestaurantesContract.COL_COCINA    + " = ?, "
        + RestaurantesContract.COL_TELEF     + " = ?, "
        + RestaurantesContract.COL_WEB       + " = ? "
        + "WHERE " + RestaurantesContract.COL_ID + " = ?";

    Connection        con   = null;
    PreparedStatement pstmt = null;

    try {
        con   = acceso.getConexion();
        pstmt = con.prepareStatement(query);
        pstmt.setString(1, r.getRegion());
        pstmt.setString(2, r.getCiudad());
        pstmt.setInt   (3, r.getDistincion());
        pstmt.setString(4, r.getDireccion());
        pstmt.setDouble(5, r.getPrecioMin());
        pstmt.setDouble(6, r.getPrecioMax());
        pstmt.setString(7, r.getCocina());
        pstmt.setString(8, r.getTelefono());
        pstmt.setString(9, r.getWeb());
        pstmt.setInt   (10, r.getId());     // el WHERE va siempre al final

        res = pstmt.executeUpdate();

    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    } finally {
        // cerrar pstmt, con
    }

    return res;
}
```

### DELETE

```java
public int deleteRestaurante(String nombre) {
    int res = 0;

    String query = "DELETE FROM " + RestaurantesContract.NOM_TABLA
                 + " WHERE " + RestaurantesContract.COL_NOMBRE + " = ?";

    Connection        con  = null;
    PreparedStatement stmt = null;

    try {
        con  = acceso.getConexion();
        stmt = con.prepareStatement(query);
        stmt.setString(1, nombre);

        res = stmt.executeUpdate();

    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    } finally {
        // cerrar stmt, con
    }

    return res;
}
```

---

## Archivo 7 — `VPRestaurantes.java` (ventana principal)

Extiende `JFrame`. Contiene la barra de menú y un `JScrollPane` que actúa de contenedor de paneles.

```java
package com.michelin.view;

import java.awt.*;
import javax.swing.*;
import com.michelin.control.RestaurantesListener;
import com.michelin.model.data.Texto;

public class VPRestaurantes extends JFrame {

    public static final int ALTO  = 500;
    public static final int ANCHO = 750;

    // insets y alto de menú — los usan los paneles para calcular su tamaño
    public static int insetsR, insetsL, insetsT, insetsB, menuH;

    private JMenuItem mntmConsulta;
    private JMenuItem mntmAlta;
    private JMenuItem mntmModif;
    private JMenuItem mntmSalir;
    private JScrollPane scrpContenedor;

    public VPRestaurantes() {
        super("* * G U I A  M I C H E L I N * *");
        crearMenu();
        configurarVentana();
    }

    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuH = menuBar.getPreferredSize().height;

        JMenu mnMto = new JMenu(Texto.MN_MTO);
        menuBar.add(mnMto);

        mntmConsulta = new JMenuItem(Texto.MNTM_CONSULTA);
        mnMto.add(mntmConsulta);

        mntmAlta = new JMenuItem(Texto.MNTM_REGISTRO);
        mnMto.add(mntmAlta);

        mntmModif = new JMenuItem(Texto.MNTM_MODIFICACION);
        mnMto.add(mntmModif);

        mntmSalir = new JMenuItem(Texto.MNTM_SALIR);
        menuBar.add(mntmSalir);
    }

    private void configurarVentana() {
        setSize(ANCHO, ALTO);

        // guardar insets para que los paneles puedan calcular su tamaño
        insetsR = getInsets().right;
        insetsL = getInsets().left;
        insetsT = getInsets().top;
        insetsB = getInsets().bottom;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));

        scrpContenedor = new JScrollPane();
        getContentPane().add(scrpContenedor, BorderLayout.CENTER);

        // centrar en pantalla
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((pantalla.width - ANCHO) / 2, (pantalla.height - ALTO) / 2);
    }

    public void hacerVisible() {
        setVisible(true);
    }

    // registra el controlador como listener de los items de menú
    public void setControlador(RestaurantesListener controlador) {
        mntmConsulta.addActionListener(controlador);
        mntmAlta.addActionListener(controlador);
        mntmModif.addActionListener(controlador);
        mntmSalir.addActionListener(controlador);
    }

    // cambia el panel visible dentro del scrollpane
    public void cargarPanel(JPanel panel) {
        scrpContenedor.setViewportView(panel);
    }

    public void confirmarSalida() {
        int opcion = JOptionPane.showConfirmDialog(this,
            Texto.MSJ_CONFIR_SALIR,
            Texto.TIT_CONFIRM,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) System.exit(0);
    }

    // getters para que el controlador pueda comparar la fuente del evento
    public JMenuItem getMntmConsulta() { return mntmConsulta; }
    public JMenuItem getMntmAlta()     { return mntmAlta; }
    public JMenuItem getMntmModif()    { return mntmModif; }
    public JMenuItem getMntmSalir()    { return mntmSalir; }
}
```

---

## Archivo 8 — `PConsultarRestaurantes.java` (panel consulta)

Panel con dos combos de filtro, botón Consultar, tabla de resultados y botón Eliminar.

```java
package com.michelin.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import com.michelin.control.RestaurantesListener;
import com.michelin.model.data.Restaurante;
import com.michelin.model.data.Texto;

public class PConsultarRestaurantes extends JPanel {

    private static final int ANCHO = VPRestaurantes.ANCHO - VPRestaurantes.insetsL - VPRestaurantes.insetsR;
    private static final int ALTO  = VPRestaurantes.ALTO  - VPRestaurantes.insetsT - VPRestaurantes.insetsB - VPRestaurantes.menuH;

    private JComboBox<String>    cmbRegion;
    private JComboBox<String>    cmbDist;
    private DefaultComboBoxModel<String> dcbmReg;
    private DefaultComboBoxModel<String> dcbmDist;
    private JButton              btnConsultar;
    private JButton              btnEliminar;
    private JTable               tblRestaurantes;
    private DefaultTableModel    dtmRestaurantes;
    private JScrollPane          scrpRestaurantes;
    private JLabel               lblListado;

    public PConsultarRestaurantes() {
        setSize(ANCHO, ALTO);
        initComponents();
    }

    private void initComponents() {
        setLayout(null);

        JLabel lblTitulo = new JLabel(Texto.LBL_TIT_CONSULTA);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBounds(15, 16, 300, 20);
        add(lblTitulo);

        add(new JLabel(Texto.LBL_FILTRO)    {{ setBounds(35,  52, 149, 20); }});
        add(new JLabel(Texto.LBL_REGION)    {{ setBounds(61,  85,  64, 20); }});
        add(new JLabel(Texto.LBL_DISTINCION){{ setBounds(400, 85,  69, 20); }});

        // combo región — se rellena desde la BD al abrir el panel
        cmbRegion = new JComboBox<>();
        dcbmReg   = new DefaultComboBoxModel<>();
        cmbRegion.setModel(dcbmReg);
        cmbRegion.setBounds(140, 82, 212, 26);
        add(cmbRegion);

        // combo distinción — fijo con "TODAS" + valores del array
        cmbDist  = new JComboBox<>();
        dcbmDist = new DefaultComboBoxModel<>();
        dcbmDist.addElement(Texto.TODAS);
        for (String d : Restaurante.DISTINCIONES) dcbmDist.addElement(d);
        cmbDist.setModel(dcbmDist);
        cmbDist.setBounds(484, 82, 140, 26);
        add(cmbDist);

        btnConsultar = new JButton(Texto.BTN_CONSULTAR);
        btnConsultar.setBounds(525, 125, 133, 29);
        add(btnConsultar);

        lblListado = new JLabel(Texto.LBL_LISTADO);
        lblListado.setVisible(false);
        lblListado.setBounds(35, 134, 218, 20);
        add(lblListado);

        // tabla dentro de scrollpane — oculta hasta que haya resultados
        scrpRestaurantes = new JScrollPane();
        scrpRestaurantes.setVisible(false);
        scrpRestaurantes.setBounds(61, 169, 597, 175);
        add(scrpRestaurantes);

        tblRestaurantes = new JTable();
        tblRestaurantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrpRestaurantes.setViewportView(tblRestaurantes);
        configurarTabla();

        btnEliminar = new JButton(Texto.BTN_ELIMINAR);
        btnEliminar.setBounds(525, 360, 133, 29);
        btnEliminar.setVisible(false);
        btnEliminar.setEnabled(false);
        add(btnEliminar);
    }

    private void configurarTabla() {
        dtmRestaurantes = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblRestaurantes.setModel(dtmRestaurantes);

        dtmRestaurantes.addColumn(Texto.CLM_NOMBRE);
        dtmRestaurantes.addColumn(Texto.CLM_CIUDAD);
        dtmRestaurantes.addColumn(Texto.CLM_DISTINCION);
        dtmRestaurantes.addColumn(Texto.CLM_COCINA);
        dtmRestaurantes.addColumn(Texto.CLM_PRECIO);
    }

    // rellena la tabla con la lista recibida del controlador
    public void cargarTabla(ArrayList<Restaurante> lista) {
        tblRestaurantes.clearSelection();
        dtmRestaurantes.getDataVector().clear();

        for (Restaurante r : lista) {
            String estrellas = "*".repeat(r.getDistincion());
            String precio    = String.valueOf(r.getPrecioMin());
            if (r.getPrecioMax() > 0) precio += " - " + r.getPrecioMax();

            dtmRestaurantes.addRow(new Object[]{
                r.getNombre(), r.getCiudad(), estrellas, r.getCocina(), precio
            });
        }
    }

    // carga las regiones devueltas por el DAO en el combo
    public void cargarCombo(ArrayList<String> regiones) {
        dcbmReg.removeAllElements();
        dcbmReg.addElement(Texto.TODAS);
        dcbmReg.addAll(regiones);
    }

    // muestra u oculta la tabla y el botón eliminar
    public void setVisibleTabla(boolean b) {
        scrpRestaurantes.setVisible(b);
        lblListado.setVisible(b);
        btnEliminar.setVisible(b);
        btnEliminar.setEnabled(b);
    }

    // limpia el panel al entrar desde el menú
    public void limpiarConsulta() {
        setVisibleTabla(false);
        cmbDist.setSelectedIndex(0);
        cmbRegion.setSelectedIndex(0);
    }

    // devuelve el NOMBRE de la fila seleccionada, o null si no hay selección
    public String getNombreRegSel() {
        int fila = tblRestaurantes.getSelectedRow();
        if (fila != -1) return (String) dtmRestaurantes.getValueAt(fila, 0);
        return null;
    }

    public void setControlador(RestaurantesListener c) {
        btnConsultar.addActionListener(c);
        btnEliminar.addActionListener(c);
    }

    public JButton              getBtnConsultar()    { return btnConsultar; }
    public JButton              getBtnEliminar()     { return btnEliminar; }
    public JComboBox<String>    getCmbRegion()       { return cmbRegion; }
    public JComboBox<String>    getCmbDist()         { return cmbDist; }
    public JTable               getTblRestaurantes() { return tblRestaurantes; }
    public DefaultTableModel    getDtmRestaurantes() { return dtmRestaurantes; }
}
```

---

## Archivo 9 — `PRegistrarRestaurante.java` (panel alta)

Formulario de alta. El método clave es `obtenerDatos()` — valida y construye el objeto.

```java
package com.michelin.view;

import javax.swing.*;
import java.awt.*;
import com.michelin.control.RestaurantesListener;
import com.michelin.model.data.Restaurante;
import com.michelin.model.data.Texto;

public class PRegistrarRestaurante extends JPanel {

    private static final int ANCHO = VPRestaurantes.ANCHO - VPRestaurantes.insetsL - VPRestaurantes.insetsR;
    private static final int ALTO  = VPRestaurantes.ALTO  - VPRestaurantes.insetsT - VPRestaurantes.insetsB - VPRestaurantes.menuH;

    private JTextField          txtNombre, txtCiudad, txtDireccion;
    private JTextField          txtPrecioMin, txtPrecioMax;
    private JTextField          txtTelefono, txtWeb;
    private JComboBox<String>   cmbRegion, cmbCocina;
    private JSpinner             spnDist;
    private JButton              btnGuardarDatos, btnLimpiarDatos;

    public PRegistrarRestaurante() {
        setSize(ANCHO, ALTO);
        initComponents();
    }

    private void initComponents() {
        setLayout(null);

        // título
        JLabel lbl = new JLabel(Texto.LBL_TIT_REGISTRO);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 16));
        lbl.setBounds(15, 16, 271, 20);
        add(lbl);

        // campos y etiquetas — ajusta los setBounds al diseño de tu examen
        add(new JLabel(Texto.LBL_NOMBRE)    {{ setBounds(43,  70, 69, 20); }});
        txtNombre = new JTextField(); txtNombre.setBounds(127, 67, 271, 26); add(txtNombre);

        add(new JLabel(Texto.LBL_REGION)    {{ setBounds(43, 115, 58, 20); }});
        cmbRegion = new JComboBox<>(Restaurante.REGIONES);
        cmbRegion.setBounds(116, 112, 212, 26); add(cmbRegion);

        add(new JLabel(Texto.LBL_CIUDAD)    {{ setBounds(360, 115, 69, 20); }});
        txtCiudad = new JTextField(); txtCiudad.setBounds(437, 112, 231, 26); add(txtCiudad);

        add(new JLabel(Texto.LBL_DIRECCION) {{ setBounds(43, 160, 94, 20); }});
        txtDireccion = new JTextField(); txtDireccion.setBounds(139, 157, 413, 26); add(txtDireccion);

        add(new JLabel(Texto.LBL_DISTINCION){{ setBounds(43, 205, 94, 20); }});
        spnDist = new JSpinner(new SpinnerNumberModel(1, 1, 3, 1));
        spnDist.setBounds(139, 202, 42, 26); add(spnDist);

        add(new JLabel(Texto.LBL_PRECIO_MIN){{ setBounds(298, 205, 125, 20); }});
        txtPrecioMin = new JTextField(); txtPrecioMin.setBounds(430, 202, 69, 26); add(txtPrecioMin);

        add(new JLabel(Texto.LBL_PRECIO_MAX){{ setBounds(514, 205, 86, 20); }});
        txtPrecioMax = new JTextField(); txtPrecioMax.setBounds(598, 202, 69, 26); add(txtPrecioMax);

        add(new JLabel(Texto.LBL_COCINA)    {{ setBounds(430, 70, 69, 20); }});
        cmbCocina = new JComboBox<>(Restaurante.TIPOS_COCINA);
        cmbCocina.setBounds(504, 67, 147, 26); add(cmbCocina);

        add(new JLabel(Texto.LBL_TELEFONO)  {{ setBounds(43, 250, 86, 20); }});
        txtTelefono = new JTextField(); txtTelefono.setBounds(133, 247, 138, 26); add(txtTelefono);

        add(new JLabel(Texto.LBL_WEB)       {{ setBounds(308, 250, 58, 20); }});
        txtWeb = new JTextField(); txtWeb.setBounds(367, 247, 301, 26); add(txtWeb);

        // botones
        btnGuardarDatos = new JButton(Texto.BTN_REGISTRAR);
        btnGuardarDatos.setBounds(175, 311, 153, 29); add(btnGuardarDatos);

        btnLimpiarDatos = new JButton(Texto.BTN_LIMPIAR);
        btnLimpiarDatos.setBounds(360, 311, 153, 29); add(btnLimpiarDatos);
    }

    // valida los datos del formulario y devuelve un Restaurante, o null si hay error
    public Restaurante obtenerDatos() {
        String nom     = txtNombre.getText().trim();
        String reg     = (String) cmbRegion.getSelectedItem();
        String ciu     = txtCiudad.getText().trim();
        int    dist    = (int) spnDist.getValue();
        String dir     = txtDireccion.getText();
        String sPrecMin = txtPrecioMin.getText();
        String sPrecMax = txtPrecioMax.getText();
        String coc     = (String) cmbCocina.getSelectedItem();
        String tel     = txtTelefono.getText();
        String web     = txtWeb.getText();
        double precMin = 0, precMax = 0;

        if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(this, Texto.MSJ_ERROR_NOM, Texto.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (ciu.isEmpty()) {
            JOptionPane.showMessageDialog(this, Texto.MSJ_ERROR_CIU, Texto.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            if (!sPrecMin.isEmpty()) precMin = Double.parseDouble(sPrecMin);
            if (!sPrecMax.isEmpty()) precMax = Double.parseDouble(sPrecMax);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, Texto.MSJ_ERROR_PRECIOS_2, Texto.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (precMax != 0 && precMin > precMax) {
            JOptionPane.showMessageDialog(this, Texto.MSJ_ERROR_PRECIOS_1, Texto.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return new Restaurante(nom, reg, ciu, dist, dir, precMin, precMax, coc, tel, web);
    }

    public void limpiarDatos() {
        txtNombre.setText("");    cmbRegion.setSelectedIndex(0);
        txtCiudad.setText("");    spnDist.setValue(1);
        txtDireccion.setText(""); txtPrecioMin.setText("");
        txtPrecioMax.setText(""); cmbCocina.setSelectedIndex(0);
        txtTelefono.setText("");  txtWeb.setText("");
    }

    public void setControlador(RestaurantesListener c) {
        btnGuardarDatos.addActionListener(c);
        btnLimpiarDatos.addActionListener(c);
    }

    public JButton getBtnGuardarDatos() { return btnGuardarDatos; }
    public JButton getBtnLimpiarDatos() { return btnLimpiarDatos; }
}
```

---

## Archivo 10 — `PModificarRestaurante.java` (panel modificación)

Igual que el panel de alta pero con flujo buscar-cargar-editar-guardar.
La diferencia clave: `habilitarModif(boolean)` controla qué campos están activos.

```java
// campos adicionales respecto a PRegistrarRestaurante
private int    id;          // guarda el id del restaurante encontrado
private JButton btnBuscar;  // busca por nombre antes de habilitar edición

// estado inicial: sólo nombre y btnBuscar activos
public void habilitarModif(boolean b) {
    txtNombre.setEnabled(!b);     // si b=true, nombre y buscar se bloquean
    btnBuscar.setEnabled(!b);
    cmbRegion.setEnabled(b);
    txtCiudad.setEnabled(b);
    spnDist.setEnabled(b);
    txtDireccion.setEnabled(b);
    txtPrecioMin.setEnabled(b);
    txtPrecioMax.setEnabled(b);
    cmbCocina.setEnabled(b);
    txtTelefono.setEnabled(b);
    txtWeb.setEnabled(b);
    btnGuardarDatos.setEnabled(b);
    btnCancelar.setEnabled(b);
}

// carga los datos de un restaurante en los campos del formulario
public void cargarRestaurante(Restaurante r) {
    id = r.getId();                                   // necesario para el UPDATE por ID
    txtNombre.setText(r.getNombre());
    cmbRegion.setSelectedItem(r.getRegion());
    txtCiudad.setText(r.getCiudad());
    spnDist.setValue(r.getDistincion());
    txtDireccion.setText(r.getDireccion());
    txtPrecioMin.setText(String.valueOf(r.getPrecioMin()));
    if (r.getPrecioMax() != 0)
        txtPrecioMax.setText(String.valueOf(r.getPrecioMax()));
    cmbCocina.setSelectedItem(r.getCocina());
    txtTelefono.setText(r.getTelefono());
    txtWeb.setText(r.getWeb());
}

// obtenerDatos() — igual que en PRegistrarRestaurante pero usa el constructor con id
// restaurante = new Restaurante(id, nom, reg, ciu, dist, dir, precMin, precMax, coc, tel, web);
```

> **`TODO`:** llama `habilitarModif(false)` al final de `initComponents()` para que el panel arranque en estado correcto.

---

## Archivo 11 — `RestaurantesListener.java` (controlador)

Implementa `ActionListener`. Recibe todos los eventos de menú y botones, y delega al DAO.

```java
package com.michelin.control;

import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import com.michelin.model.data.*;
import com.michelin.model.db.RestaurantesDAO;
import com.michelin.view.*;

public class RestaurantesListener implements ActionListener {

    private VPRestaurantes       vp;
    private PConsultarRestaurantes pcr;
    private PRegistrarRestaurante  prr;
    private PModificarRestaurante  pmr;

    private RestaurantesDAO datosRestaurantes;

    public RestaurantesListener(VPRestaurantes vp) {
        this.vp = vp;
        datosRestaurantes = new RestaurantesDAO();
    }

    // setters de los paneles — se llaman desde Main antes de hacer visible la ventana
    public void setPcr(PConsultarRestaurantes pcr) { this.pcr = pcr; }
    public void setPrr(PRegistrarRestaurante  prr) { this.prr = prr; }
    public void setPmr(PModificarRestaurante  pmr) { this.pmr = pmr; }

    @Override
    public void actionPerformed(ActionEvent ev) {

        if (ev.getSource() instanceof JMenuItem) {
            // ---- eventos de menú ----
            if (ev.getSource().equals(vp.getMntmConsulta())) {
                pcr.cargarCombo(datosRestaurantes.selectRegiones());
                pcr.limpiarConsulta();
                vp.cargarPanel(pcr);

            } else if (ev.getSource().equals(vp.getMntmAlta())) {
                vp.cargarPanel(prr);

            } else if (ev.getSource().equals(vp.getMntmModif())) {
                pmr.limpiarDatos();
                pmr.habilitarModif(false);
                vp.cargarPanel(pmr);

            } else if (ev.getSource().equals(vp.getMntmSalir())) {
                vp.confirmarSalida();
            }

        } else if (ev.getSource() instanceof JButton) {
            // ---- eventos de botones ----
            if      (ev.getSource().equals(pcr.getBtnConsultar()))    consultarRestaurantes();
            else if (ev.getSource().equals(pcr.getBtnEliminar()))     eliminarRestaurante();
            else if (ev.getSource().equals(prr.getBtnGuardarDatos())) registrarRestaurante();
            else if (ev.getSource().equals(prr.getBtnLimpiarDatos())) prr.limpiarDatos();
            else if (ev.getSource().equals(pmr.getBtnBuscar()))       buscarRestaurante();
            else if (ev.getSource().equals(pmr.getBtnGuardarDatos())) modificarRestaurante();
            else if (ev.getSource().equals(pmr.getBtnCancelar())) {
                pmr.limpiarDatos();
                pmr.habilitarModif(false);
            }
        }
    }

    // ---- métodos privados de lógica ----

    private void consultarRestaurantes() {
        String reg  = (String) pcr.getCmbRegion().getSelectedItem();
        String dist = (String) pcr.getCmbDist().getSelectedItem();

        ArrayList<Restaurante> lista = datosRestaurantes.selectRestaurantesFiltro(reg, dist);

        if (!lista.isEmpty()) {
            pcr.cargarTabla(lista);
            pcr.setVisibleTabla(true);
        } else {
            JOptionPane.showMessageDialog(pcr, Texto.MSJ_NO_DATOS,
                Texto.TIT_MSJ_CONSULTA, JOptionPane.INFORMATION_MESSAGE);
            pcr.setVisibleTabla(false);
        }
    }

    private void registrarRestaurante() {
        Restaurante r = prr.obtenerDatos();
        if (r == null) return;

        int res = datosRestaurantes.insertRestaurante(r);

        if (res == 1) {
            JOptionPane.showMessageDialog(prr, Texto.MSJ_REGISTRO,
                Texto.TIT_MSJ_RESULTADO, JOptionPane.INFORMATION_MESSAGE);
            prr.limpiarDatos();
        } else {
            JOptionPane.showMessageDialog(prr, Texto.MSJ_NOMBRE_DUP,
                Texto.TIT_MSJ_RESULTADO, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarRestaurante() {
        String nombre = pmr.getTxtNombre().getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(pmr, Texto.MSJ_NOMBRE_OBLIG,
                Texto.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
            return;
        }

        Restaurante r = datosRestaurantes.selectRestauranteNombre(nombre);

        if (r != null) {
            pmr.cargarRestaurante(r);
            pmr.habilitarModif(true);
        } else {
            JOptionPane.showMessageDialog(pmr, Texto.MSJ_CONSULTA,
                Texto.TIT_MSJ_CONSULTA, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void modificarRestaurante() {
        Restaurante r = pmr.obtenerDatos();
        if (r == null) return;

        int res = datosRestaurantes.updateRestaurante(r);

        if (res == 1) {
            JOptionPane.showMessageDialog(pmr, Texto.MSJ_MODIFICACION,
                Texto.TIT_MSJ_RESULTADO, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void eliminarRestaurante() {
        String nom = pcr.getNombreRegSel();

        if (nom == null) {
            JOptionPane.showMessageDialog(pcr, Texto.MSJ_ERROR_SEL,
                Texto.TIT_ERROR_SEL, JOptionPane.ERROR_MESSAGE);
            return;
        }

        int resp = JOptionPane.showConfirmDialog(pcr, Texto.MSJ_CONFIR_ELIMINAR,
            Texto.TIT_CONFIRM, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (resp == JOptionPane.YES_OPTION) {
            int res = datosRestaurantes.deleteRestaurante(nom);
            if (res == 1) {
                JOptionPane.showMessageDialog(pcr, Texto.MSJ_RESULT_ELIMINAR,
                    Texto.TIT_MSJ_RESULTADO, JOptionPane.INFORMATION_MESSAGE);
                consultarRestaurantes();   // refresca la tabla
            }
        }
    }
}
```

---

## Archivo 12 — `RestaurantesMain.java` (punto de entrada)

Crea todas las piezas, las conecta y arranca la aplicación. **Nunca crear componentes Swing fuera de `EventQueue.invokeLater`.**

```java
package com.michelin.main;

import java.awt.EventQueue;
import com.michelin.control.RestaurantesListener;
import com.michelin.view.*;

public class RestaurantesMain {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {

            // 1. crear ventana principal
            VPRestaurantes vp = new VPRestaurantes();

            // 2. crear paneles
            PConsultarRestaurantes pcr = new PConsultarRestaurantes();
            PRegistrarRestaurante  prr = new PRegistrarRestaurante();
            PModificarRestaurante  pmr = new PModificarRestaurante();

            // 3. crear controlador y darle referencias a los paneles
            RestaurantesListener controlador = new RestaurantesListener(vp);
            controlador.setPcr(pcr);
            controlador.setPrr(prr);
            controlador.setPmr(pmr);

            // 4. registrar el controlador como listener en cada vista
            vp.setControlador(controlador);
            pcr.setControlador(controlador);
            prr.setControlador(controlador);
            pmr.setControlador(controlador);

            // 5. hacer visible
            vp.hacerVisible();
        });
    }
}
```

---

## Checklist de examen

### Antes de empezar

- [ ] Verificar que el `.db` existe en la carpeta `BBDD/`
- [ ] Verificar que el `sqlite-jdbc-*.jar` está en `lib/` y en el Build Path de Eclipse
- [ ] Abrir el `.db` con DB Browser for SQLite y revisar el esquema de la tabla

### Al implementar

- [ ] `RestaurantesContract` tiene todas las columnas de la tabla
- [ ] `Restaurante` tiene un campo y getter por cada columna
- [ ] `AccesoDB` lee la ruta correcta del `.properties`
- [ ] Cada método del DAO cierra `ResultSet`, `Statement` y `Connection` en el `finally`
- [ ] `obtenerDatos()` en los paneles devuelve `null` ante cualquier error de validación
- [ ] `habilitarModif(false)` se llama al final de `initComponents()` en el panel de modificación
- [ ] El controlador compara con `ev.getSource().equals(...)` (no `getActionCommand`)
- [ ] El `Main` llama a `setControlador` en todos los paneles y en la ventana principal

### Trampas frecuentes

| Error | Causa | Solución |
|-------|-------|----------|
| `NullPointerException` al pulsar botón | `setControlador` no fue llamado | Revisar el `Main` |
| La tabla no aparece tras consultar | `setVisibleTabla(true)` no se llama | Revisar `consultarRestaurantes()` |
| INSERT no inserta nada | Nombre duplicado (UNIQUE en BD) | El DAO devuelve 0; mostrar `MSJ_NOMBRE_DUP` |
| `FileNotFoundException` del `.properties` | Ruta relativa incorrecta | El working directory en Eclipse es la raíz del proyecto |
| `ClassNotFoundException` del driver | Jar no añadido al Build Path | Project → Properties → Java Build Path → Add JARs |
| Panel de modificación no habilita campos | `habilitarModif(false)` no está en `initComponents` | Añadirlo al final de `initComponents()` |
| UPDATE modifica el registro equivocado | Se usa nombre en vez de ID en el WHERE | Guardar `id` en `cargarRestaurante()` y usarlo en `obtenerDatos()` |
