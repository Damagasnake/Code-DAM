# Guía completa de exámenes Java Swing + DAO

Esta guía explica **con código real de este proyecto** cómo resolver exámenes que siguen la fórmula de TODOs: aplicación Swing con patrón MVC, base de datos SQLite y múltiples paneles conectados entre sí.

El foco está en lo que más cuesta: **conectar ventanas, tablas, DAO y controlador** de forma que todo funcione junto.

---

## Índice

1. [Qué hace cada archivo y por qué existe](#1-qué-hace-cada-archivo-y-por-qué-existe)
2. [Las tres reglas de oro](#2-las-tres-reglas-de-oro)
3. [Cómo se ensambla todo al arrancar](#3-cómo-se-ensambla-todo-al-arrancar)
4. [Orden para resolver los TODOs](#4-orden-para-resolver-los-todos)
5. [Archivo: Empresa.java — la entidad y sus validaciones](#5-archivo-empresajava--la-entidad-y-sus-validaciones)
6. [Archivo: EmpresaContracts.java — los nombres de BD](#6-archivo-empresacontractsjava--los-nombres-de-bd)
7. [Archivo: AccesoDB.java — la conexión a SQLite](#7-archivo-accesodbjava--la-conexión-a-sqlite)
8. [Archivo: EmpresasDAO.java — todo el SQL](#8-archivo-empresasdaojava--todo-el-sql)
9. [Archivo: VPEmpresas.java — la ventana principal](#9-archivo-vpempresasjava--la-ventana-principal)
10. [Archivo: PRegistrarEmpresa.java — panel de alta](#10-archivo-pregistrarempresajava--panel-de-alta)
11. [Archivo: PConsultaEmpresas.java — panel con tabla](#11-archivo-pconsultaempresasjava--panel-con-tabla)
12. [Archivo: PModificarEmpresa.java — panel de edición](#12-archivo-pmodificarempresajava--panel-de-edición)
13. [Archivo: ControladorEmpresas.java — el cerebro](#13-archivo-controladorempresasjava--el-cerebro)
14. [Archivo: InicioEmpresas.java — el main](#14-archivo-inicioempresasjava--el-main)
15. [La tabla en detalle: JTable + DefaultTableModel](#15-la-tabla-en-detalle-jtable--defaulttablemodel)
16. [Flujo completo: registrar una empresa](#16-flujo-completo-registrar-una-empresa)
17. [Flujo completo: buscar y mostrar en tabla](#17-flujo-completo-buscar-y-mostrar-en-tabla)
18. [Flujo completo: modificar desde la tabla](#18-flujo-completo-modificar-desde-la-tabla)
19. [Flujo completo: eliminar desde la tabla](#19-flujo-completo-eliminar-desde-la-tabla)
20. [Checklist de examen](#20-checklist-de-examen)
21. [Errores frecuentes con solución](#21-errores-frecuentes-con-solución)
22. [Cómo ejecutar el proyecto](#22-cómo-ejecutar-el-proyecto)
23. [Swing: qué usa este ejercicio y qué no (con impacto en el DAO)](#23-swing-qué-usa-este-ejercicio-y-qué-no)

---

## 1. Qué hace cada archivo y por qué existe

```
src/
├── InicioEmpresas.java              ← main(): crea todo y lo conecta. Sin él la app no arranca.
└── com/dam/
    ├── control/
    │   └── ControladorEmpresas.java ← escucha TODOS los botones/menús de TODOS los paneles.
    │                                   Llama al DAO y actualiza las vistas.
    ├── model/
    │   ├── datos/
    │   │   ├── Empresa.java         ← objeto con los 9 campos + validaciones estáticas.
    │   │   └── Textos.java          ← constantes de todos los textos de la UI.
    │   └── db/
    │       ├── AccesoDB.java        ← lee ConfiguracionDB.properties y abre la conexión JDBC.
    │       ├── EmpresaContracts.java← constantes con nombres exactos de tabla y columnas SQL.
    │       └── EmpresasDAO.java     ← los 5 métodos SQL: insert, buscar, obtenerPorCif,
    │                                   update, delete.
    └── view/
        ├── IVFrame.java             ← interfaz que obliga a VPEmpresas a tener ciertos métodos.
        ├── IPaneles.java            ← interfaz que obliga a los paneles a tener ciertos métodos.
        ├── VPEmpresas.java          ← JFrame con menú y JScrollPane central donde van los paneles.
        ├── PRegistrarEmpresa.java   ← JPanel con formulario de alta.
        ├── PConsultaEmpresas.java   ← JPanel con filtros + JTable + botones Eliminar y Modificar.
        └── PModificarEmpresa.java   ← JPanel con formulario pre-rellenado para editar.

DB/
├── ConfiguracionDB.properties       ← driver JDBC y ruta del archivo .db
└── EMPRESAS.ddl                     ← SQL para crear la tabla

BBDD/
└── BBDD.db                          ← archivo SQLite (hay que crearlo una vez con el DDL)
```

**Relación entre archivos** (quién conoce a quién):

```
InicioEmpresas
    crea ──→ VPEmpresas, PRegistrarEmpresa, PConsultaEmpresas, PModificarEmpresa
    crea ──→ ControladorEmpresas
                tiene referencia a ──→ VPEmpresas
                tiene referencia a ──→ PRegistrarEmpresa
                tiene referencia a ──→ PConsultaEmpresas
                tiene referencia a ──→ PModificarEmpresa
                tiene referencia a ──→ EmpresasDAO
                                           usa ──→ AccesoDB
                                           usa ──→ EmpresaContracts
                                           devuelve/recibe ──→ Empresa
```

Ningún panel conoce a otro panel. Solo el **controlador** tiene referencias a todos.

---

## 2. Las tres reglas de oro

Estas tres reglas te salvan en cualquier examen de esta fórmula:

### Regla 1: Un botón que hace algo necesita cuatro cosas


| Dónde                                  | Qué poner                                                   |
| -------------------------------------- | ----------------------------------------------------------- |
| Atributo de la vista                   | `private JButton btnBuscar;`                                |
| Dentro de `crearComponentes()`         | `btnBuscar = new JButton("Buscar");` + `add(btnBuscar);`    |
| Dentro de `setControlador()`           | `btnBuscar.addActionListener(control);`                     |
| Getter en la vista                     | `public JButton getBtnBuscar() { return btnBuscar; }`       |
| En `actionPerformed()` del controlador | `else if (origen.equals(pce.getBtnBuscar())) { buscar(); }` |


Si falta cualquiera de los cinco, el botón no funciona.

### Regla 2: La vista nunca llama al DAO

```
CORRECTO:  Vista → Controlador → DAO → Controlador → Vista
INCORRECTO: Vista → DAO   (esto rompe el patrón MVC)
```

### Regla 3: El DAO nunca muestra mensajes

El DAO solo devuelve `int` (filas afectadas) o `ArrayList<Entidad>`. El que decide si mostrar un mensaje de éxito o error es el **controlador**, usando métodos de la vista.

---

## 3. Cómo se ensambla todo al arrancar

El `main` es el único sitio donde se crean los objetos y se conectan. Tiene **tres fases**:

**Fase 1** — Crear las vistas:

```java
VPEmpresas vp   = new VPEmpresas();           // JFrame con menú
PRegistrarEmpresa pre = new PRegistrarEmpresa(); // panel de alta
PConsultaEmpresas pce = new PConsultaEmpresas(); // panel de consulta
PModificarEmpresa pme = new PModificarEmpresa(); // panel de edición
```

**Fase 2** — Crear el controlador y darle acceso a las vistas:

```java
ControladorEmpresas ce = new ControladorEmpresas(vp); // vp en constructor
ce.setPre(pre);   // el controlador guarda pre en su atributo
ce.setPce(pce);   // el controlador guarda pce en su atributo
ce.setPme(pme);   // el controlador guarda pme en su atributo
```

**Fase 3** — Registrar el controlador como escuchador en cada vista.
Cada `setControlador` ejecuta internamente `addActionListener(ce)` en sus botones:

```java
vp.setControlador(ce);   // registra ce en mntmConsulta y mntmRegistrar
pre.setControlador(ce);  // registra ce en btnGuardar y btnCancelar
pce.setControlador(ce);  // registra ce en btnBuscar, btnEliminar, btnModificar
pme.setControlador(ce);  // registra ce en btnGuardar y btnCancelar
```

**Fase 4** — Mostrar la ventana:

```java
vp.hacerVisible(); // setVisible(true)
```

> **Nota importante sobre el orden:** El controlador recibe `vp` en el constructor, pero `pre`, `pce` y `pme` se pasan después con setters. Esto es necesario porque el controlador y los paneles se crean por separado. Si la clase exigiera todos en el constructor, habría dependencia circular.

---

## 4. Orden para resolver los TODOs

Este orden evita que un archivo dependa de otro que aún no has implementado:

```
1.  Empresa.java          → campos, constructor, getters, validaciones
2.  EmpresaContracts.java → constantes de nombres SQL
3.  AccesoDB.java         → suele estar hecho, solo revisar ruta del .properties
4.  EmpresasDAO.java      → insert, buscarEmpresas, obtenerPorCif, update, delete
5.  VPEmpresas.java       → constructor, configurarVentana(), crearMenu(), setControlador(),
                             cargarPanel(), getters de menuitems
6.  PRegistrarEmpresa.java → constructor, crearComponentes(), setControlador(),
                              obtenerDatos(), limpiarDatos(), getters de botones
7.  PConsultaEmpresas.java → ídem + configurarTabla(), cargarTabla(), setVisibleTabla(),
                              limpiarConsulta(), getters de tabla y modelo
8.  PModificarEmpresa.java → ídem + cargarDatos()
9.  ControladorEmpresas.java → atributos, constructor, setters, actionPerformed(),
                                registrar(), buscar(), eliminar(), abrirModificar(), modificar()
10. InicioEmpresas.java   → main() con las 4 fases
```

---

## 5. Archivo: Empresa.java — la entidad y sus validaciones

Este archivo representa una fila de la base de datos como objeto Java. Sus validaciones son `static` porque se llaman sin crear un objeto (`Empresa.validarCif("A12345678")`).

```java
package com.dam.model.datos;

public class Empresa {

    // --- Campos (uno por columna de la tabla) ---
    private String cif;
    private String razonSocial;
    private String domicilio;
    private String representante;
    private String correoRL;
    private String convenio;      // solo "SI" o "NO"
    private int numEmpleados;
    private String telefono;
    private String web;

    // --- Constructor (el orden IMPORTA: debe coincidir con el que uses en el DAO) ---
    public Empresa(String cif, String razonSocial, String domicilio, String representante,
            String correoRL, String convenio, int numEmpleados, String telefono, String web) {
        this.cif = cif;
        this.razonSocial = razonSocial;
        this.domicilio = domicilio;
        this.representante = representante;
        this.correoRL = correoRL;
        this.convenio = convenio;
        this.numEmpleados = numEmpleados;
        this.telefono = telefono;
        this.web = web;
    }

    // --- Getters ---
    public String getCif()           { return cif; }
    public String getRazonSocial()   { return razonSocial; }
    public String getDomicilio()     { return domicilio; }
    public String getRepresentante() { return representante; }
    public String getCorreoRL()      { return correoRL; }
    public String getConvenio()      { return convenio; }
    public int    getNumEmpleados()  { return numEmpleados; }
    public String getTelefono()      { return telefono; }
    public String getWeb()           { return web; }

    // --- Método utilitario: convierte "SI"/"NO" en texto legible para la tabla ---
    public String traducirConvenio() {
        if (convenio.equals("SI")) return "Firmado";
        return "Pendiente";
    }

    // --- Validaciones estáticas ---

    // CIF español: letra + 7 dígitos + letra o dígito = 9 caracteres
    public static boolean validarCif(String cif) {
        if (cif == null || cif.length() != 9 || !Character.isLetter(cif.charAt(0))) {
            return false;
        }
        for (int i = 1; i <= 7; i++) {
            if (!Character.isDigit(cif.charAt(i))) return false;
        }
        return Character.isLetterOrDigit(cif.charAt(8));
    }

    // Email: no vacío, sin espacios, exactamente un @, dominio con >=3 chars y punto
    public static boolean validarCorreo(String email) {
        if (email == null || email.trim().isEmpty() || email.contains(" ")) return false;
        int arroba = email.indexOf("@");
        if (arroba == -1 || arroba != email.lastIndexOf("@")) return false;
        String dominio = email.substring(arroba + 1);
        return dominio.length() >= 3 && dominio.contains(".");
    }

    // Teléfono: 9 dígitos, empieza por 6, 7, 8 o 9
    public static boolean validarTelefono(String telefono) {
        if (telefono == null || telefono.length() != 9) return false;
        char inicio = telefono.charAt(0);
        return inicio == '6' || inicio == '7' || inicio == '8' || inicio == '9';
    }

    // Web: empieza por "www.", dominio no vacío y con punto
    public static boolean validarWeb(String url) {
        if (url == null || url.trim().isEmpty() || url.contains(" ")) return false;
        if (!url.startsWith("www.")) return false;
        String dominio = url.substring(4);
        return !dominio.isEmpty() && dominio.contains(".");
    }
}
```

**Cómo se usan las validaciones en un panel:**

```java
// En obtenerDatos() de cualquier panel formulario:
if (!Empresa.validarCif(cif)) {
    mostrarMensaje(Textos.MSJ_ERROR_CIF, Textos.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
    return null;   // devuelve null para indicar que los datos no son válidos
}
```

---

## 6. Archivo: EmpresaContracts.java — los nombres de BD

Evita escribir strings literales como `"EMPRESAS"` o `"CIF"` en el SQL. Si el nombre cambia, solo cambia aquí.

```java
package com.dam.model.db;

public class EmpresaContracts {
    public static final String NOM_TABLA             = "EMPRESAS";
    public static final String COL_ID                = "CIF";
    public static final String COL_RAZON             = "RAZON_SOCIAL";
    public static final String COL_DOMICILIO         = "DOMICILIO";
    public static final String COL_REPRESENTANTE     = "REPRESENTANTE_LEGAL";
    public static final String COL_REPRESENTANTE_MAIL = "CORREO_RL";
    public static final String COL_CONVENIO          = "CONVENIO";
    public static final String COL_NUM_EMP           = "NUM_EMPLEADOS";
    public static final String COL_TLF               = "TELEFONO";
    public static final String COL_WEB               = "WEB";
}
```

**Truco:** los valores de estas constantes deben coincidir **exactamente** con los nombres en el DDL (`EMPRESAS.ddl`). Si hay diferencia en mayúsculas/minúsculas, SQLite puede dar error según la configuración.

---

## 7. Archivo: AccesoDB.java — la conexión a SQLite

Lee las propiedades del archivo `DB/ConfiguracionDB.properties` y abre la conexión JDBC.

```java
package com.dam.model.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class AccesoDB {

    private String driver;
    private String url;

    public AccesoDB() {
        Properties prop = new Properties();
        InputStream is = null;
        try {
            // La ruta es relativa al directorio de trabajo (carpeta raíz del proyecto)
            is = new FileInputStream("DB/ConfiguracionDB.properties");
            prop.load(is);
            driver = prop.getProperty("DRIVER");
            url    = prop.getProperty("URL");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { if (is != null) is.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    }

    public Connection getConexion() throws ClassNotFoundException, SQLException {
        Class.forName(driver);                      // carga el driver SQLite
        return DriverManager.getConnection(url);    // abre y devuelve la conexión
    }
}
```

**Contenido de `DB/ConfiguracionDB.properties`:**

```properties
DRIVER=org.sqlite.JDBC
URL=jdbc:sqlite:BBDD/BBDD.db
```

> **Importante:** La ruta `BBDD/BBDD.db` es relativa al directorio desde el que ejecutas el programa. Si lo ejecutas desde `ExamenProgAEv3_2526/`, el archivo estará en `ExamenProgAEv3_2526/BBDD/BBDD.db`. En Eclipse, el directorio de trabajo es la raíz del proyecto automáticamente.

---

## 8. Archivo: EmpresasDAO.java — todo el SQL

El DAO tiene exactamente un método por operación CRUD. **Patrón obligatorio** en cada método:

```
1. Declarar Connection = null, PreparedStatement = null (y ResultSet = null si hay SELECT)
2. try: abrir conexión, preparar sentencia, asignar parámetros, ejecutar
3. catch: ClassNotFoundException | SQLException
4. finally: cerrar SIEMPRE (aunque haya excepción)
5. return resultado
```

```java
package com.dam.model.db;

import java.sql.*;
import java.util.ArrayList;
import com.dam.model.datos.Empresa;

public class EmpresasDAO {

    // AccesoDB se crea una vez al instanciar el DAO
    private final AccesoDB acceso = new AccesoDB();

    // ─────────────────────────────────────────────────────────────────
    // INSERT
    // Devuelve 1 si se insertó, 0 si falló (ej: CIF duplicado)
    // ─────────────────────────────────────────────────────────────────
    public int insertEmpresa(Empresa e) {
        int res = 0;
        // Construir el INSERT con constantes. El orden de columnas y el nº de ? deben coincidir.
        String query = "INSERT INTO " + EmpresaContracts.NOM_TABLA + " ("
                + EmpresaContracts.COL_ID                + ", "
                + EmpresaContracts.COL_RAZON             + ", "
                + EmpresaContracts.COL_DOMICILIO         + ", "
                + EmpresaContracts.COL_REPRESENTANTE     + ", "
                + EmpresaContracts.COL_REPRESENTANTE_MAIL + ", "
                + EmpresaContracts.COL_CONVENIO          + ", "
                + EmpresaContracts.COL_NUM_EMP           + ", "
                + EmpresaContracts.COL_TLF               + ", "
                + EmpresaContracts.COL_WEB
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //                   1  2  3  4  5  6  7  8  9   ← posiciones de los ?

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con  = acceso.getConexion();
            stmt = con.prepareStatement(query);
            // Asignar cada ? en orden. El nº coincide con la posición en VALUES(...)
            stmt.setString(1, e.getCif());
            stmt.setString(2, e.getRazonSocial());
            stmt.setString(3, e.getDomicilio());
            stmt.setString(4, e.getRepresentante());
            stmt.setString(5, e.getCorreoRL());
            stmt.setString(6, e.getConvenio());
            stmt.setInt   (7, e.getNumEmpleados());  // setInt porque es INTEGER en la BD
            stmt.setString(8, e.getTelefono());
            stmt.setString(9, e.getWeb());
            res = stmt.executeUpdate();  // executeUpdate() para INSERT/UPDATE/DELETE
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            cerrar(stmt, con);  // SIEMPRE cerrar en finally
        }
        return res;
    }

    // ─────────────────────────────────────────────────────────────────
    // SELECT con filtros opcionales
    // Si cif y razon están vacíos → devuelve TODOS los registros
    // Si uno tiene valor → filtra por ese campo (OR entre ellos)
    // ─────────────────────────────────────────────────────────────────
    public ArrayList<Empresa> buscarEmpresas(String cif, String razon) {
        ArrayList<Empresa> lista = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM " + EmpresaContracts.NOM_TABLA);
        ArrayList<String> condiciones = new ArrayList<>();

        if (cif != null && !cif.isEmpty()) {
            condiciones.add(EmpresaContracts.COL_ID + " = ?");
        }
        if (razon != null && !razon.isEmpty()) {
            condiciones.add(EmpresaContracts.COL_RAZON + " LIKE ?");  // LIKE para "empieza por"
        }
        if (!condiciones.isEmpty()) {
            query.append(" WHERE ").append(String.join(" OR ", condiciones));
        }

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con  = acceso.getConexion();
            stmt = con.prepareStatement(query.toString());
            int param = 1;
            if (cif != null && !cif.isEmpty())   stmt.setString(param++, cif);
            if (razon != null && !razon.isEmpty()) stmt.setString(param, razon + "%");  // % al final = "empieza por"
            rs = stmt.executeQuery();  // executeQuery() para SELECT
            while (rs.next()) {
                lista.add(mapearEmpresa(rs));  // convierte cada fila en un objeto Empresa
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            cerrar(rs, stmt, con);  // también cerrar el ResultSet
        }
        return lista;
    }

    // ─────────────────────────────────────────────────────────────────
    // SELECT por clave primaria (para cargar datos en el panel modificar)
    // Devuelve null si no existe
    // ─────────────────────────────────────────────────────────────────
    public Empresa obtenerEmpresaPorCif(String cif) {
        Empresa empresa = null;
        String query = "SELECT * FROM " + EmpresaContracts.NOM_TABLA
                     + " WHERE " + EmpresaContracts.COL_ID + " = ?";
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con  = acceso.getConexion();
            stmt = con.prepareStatement(query);
            stmt.setString(1, cif);
            rs = stmt.executeQuery();
            if (rs.next()) {       // if, no while (solo esperamos 0 o 1 fila)
                empresa = mapearEmpresa(rs);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            cerrar(rs, stmt, con);
        }
        return empresa;
    }

    // ─────────────────────────────────────────────────────────────────
    // UPDATE por clave primaria
    // Devuelve 1 si se modificó, 0 si no encontró el CIF
    // ─────────────────────────────────────────────────────────────────
    public int updateEmpresa(Empresa e) {
        int res = 0;
        // El CIF (clave primaria) NO se modifica → no aparece en SET, solo en WHERE
        String query = "UPDATE " + EmpresaContracts.NOM_TABLA + " SET "
                + EmpresaContracts.COL_RAZON              + " = ?, "   // 1
                + EmpresaContracts.COL_DOMICILIO          + " = ?, "   // 2
                + EmpresaContracts.COL_REPRESENTANTE      + " = ?, "   // 3
                + EmpresaContracts.COL_REPRESENTANTE_MAIL + " = ?, "   // 4
                + EmpresaContracts.COL_CONVENIO           + " = ?, "   // 5
                + EmpresaContracts.COL_NUM_EMP            + " = ?, "   // 6
                + EmpresaContracts.COL_TLF                + " = ?, "   // 7
                + EmpresaContracts.COL_WEB                + " = ? "    // 8
                + "WHERE " + EmpresaContracts.COL_ID + " = ?";         // 9
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con  = acceso.getConexion();
            stmt = con.prepareStatement(query);
            stmt.setString(1, e.getRazonSocial());
            stmt.setString(2, e.getDomicilio());
            stmt.setString(3, e.getRepresentante());
            stmt.setString(4, e.getCorreoRL());
            stmt.setString(5, e.getConvenio());
            stmt.setInt   (6, e.getNumEmpleados());
            stmt.setString(7, e.getTelefono());
            stmt.setString(8, e.getWeb());
            stmt.setString(9, e.getCif());  // el WHERE va al final
            res = stmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            cerrar(stmt, con);
        }
        return res;
    }

    // ─────────────────────────────────────────────────────────────────
    // DELETE por clave primaria
    // Devuelve 1 si se eliminó, 0 si no existía
    // ─────────────────────────────────────────────────────────────────
    public int eliminarEmpresa(String cif) {
        int res = 0;
        String query = "DELETE FROM " + EmpresaContracts.NOM_TABLA
                     + " WHERE " + EmpresaContracts.COL_ID + " = ?";
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con  = acceso.getConexion();
            stmt = con.prepareStatement(query);
            stmt.setString(1, cif);
            res = stmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            cerrar(stmt, con);
        }
        return res;
    }

    // ─────────────────────────────────────────────────────────────────
    // Convierte una fila del ResultSet en un objeto Empresa
    // Este método privado evita repetir rs.getString(...) en cada método SELECT
    // ─────────────────────────────────────────────────────────────────
    private Empresa mapearEmpresa(ResultSet rs) throws SQLException {
        return new Empresa(
            rs.getString(EmpresaContracts.COL_ID),
            rs.getString(EmpresaContracts.COL_RAZON),
            rs.getString(EmpresaContracts.COL_DOMICILIO),
            rs.getString(EmpresaContracts.COL_REPRESENTANTE),
            rs.getString(EmpresaContracts.COL_REPRESENTANTE_MAIL),
            rs.getString(EmpresaContracts.COL_CONVENIO),
            rs.getInt   (EmpresaContracts.COL_NUM_EMP),      // getInt para INTEGER
            rs.getString(EmpresaContracts.COL_TLF),
            rs.getString(EmpresaContracts.COL_WEB)
        );
    }

    // Cierra cualquier combinación de Connection, Statement, ResultSet
    private void cerrar(AutoCloseable... recursos) {
        for (AutoCloseable r : recursos) {
            if (r != null) try { r.close(); } catch (Exception ex) { ex.printStackTrace(); }
        }
    }
}
```

---

## 9. Archivo: VPEmpresas.java — la ventana principal

El `JFrame` que contiene un menú y un `JScrollPane` central. Los paneles se intercambian dentro del scroll con `cargarPanel()`. **La ventana no se destruye**: solo cambia qué panel es visible.

```java
package com.dam.view;

import java.awt.*;
import javax.swing.*;
import com.dam.control.ControladorEmpresas;
import com.dam.model.datos.Textos;

public class VPEmpresas extends JFrame implements IVFrame {

    // Dimensiones públicas para que los paneles puedan calcular su propio tamaño
    public static final int ALTO  = 600;
    public static final int ANCHO = 500;

    // Márgenes e insets (calculados después de setSize, en configurarVentana)
    public static int insetsR, insetsL, insetsT, insetsB, menuH;

    private JScrollPane scrpContenedor;
    private JMenuItem mntmConsulta;
    private JMenuItem mntmRegistrar;

    public VPEmpresas() {
        super("** E M P R E S A S **");
        configurarVentana();
        crearMenu();
    }

    @Override
    public void configurarVentana() {
        setSize(ANCHO, ALTO);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        scrpContenedor = new JScrollPane();
        getContentPane().setLayout(new BorderLayout(0, 0));
        getContentPane().add(scrpContenedor, BorderLayout.CENTER);

        // Centrar en pantalla
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((pantalla.width - ANCHO) / 2, (pantalla.height - ALTO) / 2);

        // Guardar insets para que los paneles sepan el área real disponible
        insetsR = getInsets().right;
        insetsL = getInsets().left;
        insetsT = getInsets().top;
        insetsB = getInsets().bottom;
    }

    public void crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuH = menuBar.getPreferredSize().height;  // altura del menú para los paneles

        JMenu mnMantenimiento = new JMenu(Textos.MN_MANTENIMIENTO);
        menuBar.add(mnMantenimiento);

        mntmConsulta = new JMenuItem(Textos.MNTM_CONSULTAR);
        mnMantenimiento.add(mntmConsulta);

        mntmRegistrar = new JMenuItem(Textos.MNTM_REGISTRAR);
        mnMantenimiento.add(mntmRegistrar);
    }

    // ── Métodos de la interfaz IVFrame ──────────────────────────────

    @Override
    public void crearComponentes() { /* ya se hace en configurarVentana */ }

    @Override
    public void setControlador(ControladorEmpresas ce) {
        // Registrar ce como escuchador de los items del menú
        mntmConsulta.addActionListener(ce);
        mntmRegistrar.addActionListener(ce);
    }

    @Override
    public void hacerVisible() { setVisible(true); }

    @Override
    public void cargarPanel(JPanel panel) {
        // Cambia el panel visible dentro del JScrollPane central
        scrpContenedor.setViewportView(panel);
        scrpContenedor.revalidate();  // recalcula el layout tras el cambio de panel
        scrpContenedor.repaint();     // fuerza el redibujado para que el panel aparezca
    }

    // ── Getters (el controlador los necesita para comparar en actionPerformed) ──

    public JMenuItem getMntmConsulta()  { return mntmConsulta; }
    public JMenuItem getMntmRegistrar() { return mntmRegistrar; }
}
```

---

## 10. Archivo: PRegistrarEmpresa.java — panel de alta

Formulario con todos los campos. El método clave es `obtenerDatos()`: valida y devuelve un objeto `Empresa` o `null`.

```java
package com.dam.view;

import java.awt.*;
import javax.swing.*;
import com.dam.control.ControladorEmpresas;
import com.dam.model.datos.Empresa;
import com.dam.model.datos.Textos;

public class PRegistrarEmpresa extends JPanel implements IPaneles {

    // El tamaño del panel se calcula restando los márgenes de la ventana
    private static final int ANCHO = VPEmpresas.ANCHO - VPEmpresas.insetsL - VPEmpresas.insetsR;
    private static final int ALTO  = VPEmpresas.ALTO  - VPEmpresas.insetsT - VPEmpresas.insetsB - VPEmpresas.menuH;

    // Atributos de clase (NO variables locales) para poder acceder desde otros métodos
    private JTextField txtCIF, txtRazon, txtDomicilio;
    private JTextField txtRepresentante, txtCorreoR, txtWeb, txtTelefono;
    private JButton    btnGuardar, btnCancelar;
    private JSpinner   spnNumEmpleados;
    private JCheckBox  chckFirmado;

    public PRegistrarEmpresa() {
        crearComponentes();
        setPreferredSize(new Dimension(ANCHO, ALTO));
    }

    @Override
    public void crearComponentes() {
        setLayout(null);  // posicionamiento absoluto con setBounds(x, y, ancho, alto)

        JLabel lblTitulo = new JLabel(Textos.LBL_TIT_REGISTRO);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBounds(20, 15, 271, 20);
        add(lblTitulo);

        JLabel lblCIF = new JLabel(Textos.LBL_CIF);
        lblCIF.setBounds(30, 55, 80, 20);
        add(lblCIF);
        txtCIF = new JTextField(10);
        txtCIF.setBounds(120, 53, 150, 24);
        add(txtCIF);

        JLabel lblRazon = new JLabel(Textos.LBL_RAZON);
        lblRazon.setBounds(30, 95, 100, 20);
        add(lblRazon);
        txtRazon = new JTextField(10);
        txtRazon.setBounds(150, 93, 270, 24);
        add(txtRazon);

        JLabel lblDomicilio = new JLabel(Textos.LBL_DOMICILIO);
        lblDomicilio.setBounds(30, 135, 100, 20);
        add(lblDomicilio);
        txtDomicilio = new JTextField(10);
        txtDomicilio.setBounds(150, 133, 300, 24);
        add(txtDomicilio);

        JLabel lblRepresentante = new JLabel(Textos.LBL_REPRESENTANTE);
        lblRepresentante.setBounds(30, 175, 150, 20);
        add(lblRepresentante);
        txtRepresentante = new JTextField(10);
        txtRepresentante.setBounds(200, 173, 270, 24);
        add(txtRepresentante);

        JLabel lblCorreo = new JLabel(Textos.LBL_CORREO_REPRE);
        lblCorreo.setBounds(30, 215, 150, 20);
        add(lblCorreo);
        txtCorreoR = new JTextField(10);
        txtCorreoR.setBounds(200, 213, 300, 24);
        add(txtCorreoR);

        JLabel lblConvenio = new JLabel(Textos.LBL_CONVENIO);
        lblConvenio.setBounds(30, 255, 120, 20);
        add(lblConvenio);
        chckFirmado = new JCheckBox(Textos.CHCK_FIRMADO);
        chckFirmado.setBounds(120, 253, 90, 24);
        add(chckFirmado);

        JLabel lblWeb = new JLabel(Textos.LBL_WEB);
        lblWeb.setBounds(220, 255, 70, 20);
        add(lblWeb);
        txtWeb = new JTextField(10);
        txtWeb.setBounds(300, 253, 250, 24);
        add(txtWeb);

        JLabel lblNumEmpleados = new JLabel(Textos.LBL_NUM_EMPLE);
        lblNumEmpleados.setBounds(30, 295, 150, 20);
        add(lblNumEmpleados);
        spnNumEmpleados = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 10));
        spnNumEmpleados.setBounds(200, 293, 70, 24);
        add(spnNumEmpleados);

        JLabel lblTelefono = new JLabel(Textos.LBL_TELEFONO);
        lblTelefono.setBounds(300, 295, 100, 20);
        add(lblTelefono);
        txtTelefono = new JTextField(10);
        txtTelefono.setBounds(400, 293, 120, 24);
        add(txtTelefono);

        btnGuardar = new JButton(Textos.BTN_GUARDAR);
        btnGuardar.setBounds(125, 340, 150, 24);
        add(btnGuardar);

        btnCancelar = new JButton(Textos.BTN_CANCELAR);
        btnCancelar.setBounds(300, 340, 150, 24);
        add(btnCancelar);
    }

    @Override
    public void setControlador(ControladorEmpresas control) {
        btnGuardar.addActionListener(control);
        btnCancelar.addActionListener(control);
    }

    // ── Método clave: lee campos, valida, devuelve Empresa o null ────

    public Empresa obtenerDatos() {
        // 1. Leer todos los campos con trim() para quitar espacios
        String cif          = txtCIF.getText().trim();
        String razon        = txtRazon.getText().trim();
        String domicilio    = txtDomicilio.getText().trim();
        String representante = txtRepresentante.getText().trim();
        String correo       = txtCorreoR.getText().trim();
        String telefono     = txtTelefono.getText().trim();
        String web          = txtWeb.getText().trim();
        int numEmpleados    = (int) spnNumEmpleados.getValue();
        String convenio     = chckFirmado.isSelected() ? "SI" : "NO";  // checkbox → "SI"/"NO"

        // 2. Validar obligatorios
        if (cif.isEmpty() || razon.isEmpty() || domicilio.isEmpty()
                || representante.isEmpty() || correo.isEmpty()) {
            mostrarMensaje(Textos.MSJ_ERROR_DATOS, Textos.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
            return null;  // null indica error al controlador
        }
        // 3. Validar formato CIF
        if (!Empresa.validarCif(cif)) {
            mostrarMensaje(Textos.MSJ_ERROR_CIF, Textos.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
            return null;
        }
        // 4. Validar formato correo
        if (!Empresa.validarCorreo(correo)) {
            mostrarMensaje(Textos.MSJ_ERROR_CORREO, Textos.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
            return null;
        }
        // 5. Teléfono es opcional: solo validar si no está vacío
        if (!telefono.isEmpty() && !Empresa.validarTelefono(telefono)) {
            mostrarMensaje(Textos.MSJ_ERROR_TELEF, Textos.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // 6. Todo correcto → crear y devolver el objeto
        return new Empresa(cif, razon, domicilio, representante, correo, convenio, numEmpleados, telefono, web);
    }

    public void limpiarDatos() {
        txtCIF.setText("");
        txtRazon.setText("");
        txtDomicilio.setText("");
        txtRepresentante.setText("");
        txtCorreoR.setText("");
        txtTelefono.setText("");
        txtWeb.setText("");
        spnNumEmpleados.setValue(0);
        chckFirmado.setSelected(false);
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public JButton getBtnGuardar()  { return btnGuardar; }
    public JButton getBtnCancelar() { return btnCancelar; }
}
```

---

## 11. Archivo: PConsultaEmpresas.java — panel con tabla

Este es el panel más complejo porque tiene un `JTable` con su `DefaultTableModel`. Hay que entender bien qué hace cada objeto:


| Objeto                          | Qué es                     | Para qué sirve                                                          |
| ------------------------------- | -------------------------- | ----------------------------------------------------------------------- |
| `JTable tblEmpresas`            | Componente visual          | Muestra la cuadrícula al usuario                                        |
| `DefaultTableModel dtmEmpresas` | Modelo de datos en memoria | Guarda las filas y columnas. Lo que cambias aquí se refleja en la tabla |
| `JScrollPane scrpEmpresas`      | Contenedor con scroll      | Envuelve la tabla para que aparezca scrollbar si hay muchas filas       |


```java
package com.dam.view;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.dam.control.ControladorEmpresas;
import com.dam.model.datos.Empresa;
import com.dam.model.datos.Textos;
import com.dam.model.db.EmpresaContracts;

public class PConsultaEmpresas extends JPanel implements IPaneles {

    private static final int ANCHO = VPEmpresas.ANCHO - VPEmpresas.insetsL - VPEmpresas.insetsR;
    private static final int ALTO  = VPEmpresas.ALTO  - VPEmpresas.insetsT - VPEmpresas.insetsB - VPEmpresas.menuH;

    private JTextField txtCIF;
    private JTextField txtRazon;
    private JButton    btnBuscar;
    private JLabel     lblListado;
    private JScrollPane scrpEmpresas;
    private JTable     tblEmpresas;
    private JButton    btnEliminar;
    private JButton    btnModificar;
    private DefaultTableModel dtmEmpresas;

    public PConsultaEmpresas() {
        crearComponentes();
        setPreferredSize(new Dimension(ANCHO, ALTO));
    }

    @Override
    public void crearComponentes() {
        setLayout(null);

        JLabel lblTitulo = new JLabel(Textos.LBL_TIT_CONSULTA);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBounds(20, 15, 300, 20);
        add(lblTitulo);

        JLabel lblCIF = new JLabel(Textos.LBL_CIF_F);
        lblCIF.setBounds(30, 55, 80, 20);
        add(lblCIF);
        txtCIF = new JTextField(10);
        txtCIF.setBounds(120, 53, 150, 24);
        add(txtCIF);

        JLabel lblRazon = new JLabel(Textos.LBL_RAZON_F);
        lblRazon.setBounds(30, 95, 100, 20);
        add(lblRazon);
        txtRazon = new JTextField(10);
        txtRazon.setBounds(150, 93, 270, 24);
        add(txtRazon);

        btnBuscar = new JButton(Textos.BTN_BUSCAR);
        btnBuscar.setBounds(400, 135, 150, 24);
        add(btnBuscar);

        // La etiqueta "Listado de Empresas" y el scroll están ocultos hasta que haya resultados
        lblListado = new JLabel(Textos.LBL_LISTADO);
        lblListado.setVisible(false);
        lblListado.setBounds(30, 137, 218, 20);
        add(lblListado);

        scrpEmpresas = new JScrollPane();
        scrpEmpresas.setVisible(false);
        scrpEmpresas.setBounds(30, 175, 520, 175);
        add(scrpEmpresas);

        tblEmpresas = new JTable();
        tblEmpresas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // solo una fila a la vez
        scrpEmpresas.setViewportView(tblEmpresas);  // meter la tabla dentro del scroll

        configurarTabla();  // definir columnas del modelo

        btnEliminar = new JButton(Textos.BTN_ELIMINAR);
        btnEliminar.setBounds(400, 365, 150, 24);
        btnEliminar.setVisible(false);
        btnEliminar.setEnabled(false);
        add(btnEliminar);

        btnModificar = new JButton(Textos.BTN_MODIFICAR);
        btnModificar.setBounds(30, 365, 150, 24);
        btnModificar.setVisible(false);
        btnModificar.setEnabled(false);
        add(btnModificar);
    }

    // Configura el DefaultTableModel y sus columnas. Se llama UNA vez en crearComponentes.
    private void configurarTabla() {
        dtmEmpresas = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // el usuario no puede editar celdas directamente
            }
        };
        tblEmpresas.setModel(dtmEmpresas);

        // Las columnas se añaden en el orden que quieras mostrar
        // Este orden debe coincidir EXACTAMENTE con el de addRow() en cargarTabla()
        dtmEmpresas.addColumn(EmpresaContracts.COL_ID);                // columna 0 → CIF
        dtmEmpresas.addColumn(EmpresaContracts.COL_RAZON);             // columna 1
        dtmEmpresas.addColumn(EmpresaContracts.COL_DOMICILIO);         // columna 2
        dtmEmpresas.addColumn(EmpresaContracts.COL_REPRESENTANTE);     // columna 3
        dtmEmpresas.addColumn(EmpresaContracts.COL_REPRESENTANTE_MAIL);// columna 4
        dtmEmpresas.addColumn(EmpresaContracts.COL_CONVENIO);          // columna 5
        dtmEmpresas.addColumn(EmpresaContracts.COL_NUM_EMP);           // columna 6
        dtmEmpresas.addColumn(EmpresaContracts.COL_TLF);               // columna 7
        dtmEmpresas.addColumn(EmpresaContracts.COL_WEB);               // columna 8
    }

    // Vacía las filas y recarga con la lista recibida del controlador
    public void cargarTabla(ArrayList<Empresa> listaEmpresas) {
        tblEmpresas.clearSelection();
        dtmEmpresas.setRowCount(0);  // setRowCount(0) borra filas SIN borrar columnas
                                     // NO usar getDataVector().clear() que sí borra columnas

        for (Empresa e : listaEmpresas) {
            // El array debe tener exactamente tantos elementos como columnas, en el mismo orden
            dtmEmpresas.addRow(new Object[] {
                e.getCif(),           // columna 0 → CIF
                e.getRazonSocial(),   // columna 1
                e.getDomicilio(),     // columna 2
                e.getRepresentante(), // columna 3
                e.getCorreoRL(),      // columna 4
                e.traducirConvenio(), // columna 5 → "Firmado" o "Pendiente" (no "SI"/"NO")
                e.getNumEmpleados(),  // columna 6
                e.getTelefono(),      // columna 7
                e.getWeb()            // columna 8
            });
        }
    }

    // Muestra u oculta la tabla, el scroll, la etiqueta y los botones de acción
    public void setVisibleTabla(boolean visible) {
        scrpEmpresas.setVisible(visible);
        lblListado.setVisible(visible);
        btnEliminar.setVisible(visible);
        btnEliminar.setEnabled(visible);
        btnModificar.setVisible(visible);
        btnModificar.setEnabled(visible);
    }

    // Resetea el panel a su estado inicial
    public void limpiarConsulta() {
        txtCIF.setText("");
        txtRazon.setText("");
        dtmEmpresas.setRowCount(0);
        tblEmpresas.clearSelection();
        setVisibleTabla(false);
    }

    @Override
    public void setControlador(ControladorEmpresas control) {
        btnBuscar.addActionListener(control);
        btnEliminar.addActionListener(control);
        btnModificar.addActionListener(control);
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    // Para el diálogo de confirmación de borrado. Devuelve JOptionPane.YES_OPTION o NO_OPTION.
    public int mostrarConfirmacion(String mensaje, String titulo, int tipo) {
        return JOptionPane.showConfirmDialog(this, mensaje, titulo, tipo);
    }

    // Getters que el controlador necesita para leer el filtro y la tabla
    public String getCifFiltro()             { return txtCIF.getText().trim(); }
    public String getRazonFiltro()           { return txtRazon.getText().trim(); }
    public JTable getTblEmpresas()           { return tblEmpresas; }
    public DefaultTableModel getDtmEmpresas() { return dtmEmpresas; }
    public JButton getBtnBuscar()            { return btnBuscar; }
    public JButton getBtnEliminar()          { return btnEliminar; }
    public JButton getBtnModificar()         { return btnModificar; }
}
```

---

## 12. Archivo: PModificarEmpresa.java — panel de edición

Idéntico al de registro, con dos diferencias:

- El campo CIF es **no editable** (`setEditable(false)`) porque es la clave primaria.
- Tiene el método `cargarDatos(Empresa e)` para pre-rellenar los campos con los datos del registro seleccionado en la tabla.

```java
package com.dam.view;

import java.awt.*;
import javax.swing.*;
import com.dam.control.ControladorEmpresas;
import com.dam.model.datos.Empresa;
import com.dam.model.datos.Textos;

public class PModificarEmpresa extends JPanel implements IPaneles {

    private static final int ANCHO = VPEmpresas.ANCHO - VPEmpresas.insetsL - VPEmpresas.insetsR;
    private static final int ALTO  = VPEmpresas.ALTO  - VPEmpresas.insetsT - VPEmpresas.insetsB - VPEmpresas.menuH;

    private JTextField txtCIF, txtRazon, txtDomicilio;
    private JTextField txtRepresentante, txtCorreoR, txtWeb, txtTelefono;
    private JButton    btnGuardar, btnCancelar;
    private JSpinner   spnNumEmpleados;
    private JCheckBox  chckFirmado;

    public PModificarEmpresa() {
        crearComponentes();
        setPreferredSize(new Dimension(ANCHO, ALTO));
    }

    @Override
    public void crearComponentes() {
        setLayout(null);

        JLabel lblTitulo = new JLabel(Textos.LBL_TIT_MODIF);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBounds(20, 15, 271, 20);
        add(lblTitulo);

        JLabel lblCIF = new JLabel(Textos.LBL_CIF);
        lblCIF.setBounds(30, 55, 60, 20);
        add(lblCIF);
        txtCIF = new JTextField(10);
        txtCIF.setBounds(120, 53, 150, 24);
        txtCIF.setEditable(false);  // ← DIFERENCIA respecto al panel de registro
        add(txtCIF);

        JLabel lblRazon = new JLabel(Textos.LBL_RAZON);
        lblRazon.setBounds(30, 95, 100, 20);
        add(lblRazon);
        txtRazon = new JTextField(10);
        txtRazon.setBounds(150, 93, 270, 24);
        add(txtRazon);

        JLabel lblDomicilio = new JLabel(Textos.LBL_DOMICILIO);
        lblDomicilio.setBounds(30, 135, 100, 20);
        add(lblDomicilio);
        txtDomicilio = new JTextField(10);
        txtDomicilio.setBounds(150, 133, 300, 24);
        add(txtDomicilio);

        JLabel lblRepresentante = new JLabel(Textos.LBL_REPRESENTANTE);
        lblRepresentante.setBounds(30, 175, 150, 20);
        add(lblRepresentante);
        txtRepresentante = new JTextField(10);
        txtRepresentante.setBounds(200, 173, 270, 24);
        add(txtRepresentante);

        JLabel lblCorreo = new JLabel(Textos.LBL_CORREO_REPRE);
        lblCorreo.setBounds(30, 215, 150, 20);
        add(lblCorreo);
        txtCorreoR = new JTextField(10);
        txtCorreoR.setBounds(200, 213, 300, 24);
        add(txtCorreoR);

        JLabel lblConvenio = new JLabel(Textos.LBL_CONVENIO);
        lblConvenio.setBounds(30, 255, 120, 20);
        add(lblConvenio);
        chckFirmado = new JCheckBox(Textos.CHCK_FIRMADO);
        chckFirmado.setBounds(120, 253, 90, 24);
        add(chckFirmado);

        JLabel lblWeb = new JLabel(Textos.LBL_WEB);
        lblWeb.setBounds(220, 255, 70, 20);
        add(lblWeb);
        txtWeb = new JTextField(10);
        txtWeb.setBounds(300, 253, 250, 24);
        add(txtWeb);

        JLabel lblNumEmpleados = new JLabel(Textos.LBL_NUM_EMPLE);
        lblNumEmpleados.setBounds(30, 295, 150, 20);
        add(lblNumEmpleados);
        spnNumEmpleados = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 10));
        spnNumEmpleados.setBounds(200, 293, 70, 24);
        add(spnNumEmpleados);

        JLabel lblTelefono = new JLabel(Textos.LBL_TELEFONO);
        lblTelefono.setBounds(300, 295, 100, 20);
        add(lblTelefono);
        txtTelefono = new JTextField(10);
        txtTelefono.setBounds(400, 293, 120, 24);
        add(txtTelefono);

        btnGuardar = new JButton(Textos.BTN_GUARDAR);
        btnGuardar.setBounds(125, 340, 150, 24);
        add(btnGuardar);

        btnCancelar = new JButton(Textos.BTN_CANCELAR);
        btnCancelar.setBounds(300, 340, 150, 24);
        add(btnCancelar);
    }

    @Override
    public void setControlador(ControladorEmpresas control) {
        btnGuardar.addActionListener(control);
        btnCancelar.addActionListener(control);
    }

    // ── Método clave: pre-rellena los campos con los datos del objeto ─────────
    public void cargarDatos(Empresa empresa) {
        txtCIF.setText(empresa.getCif());
        txtRazon.setText(empresa.getRazonSocial());
        txtDomicilio.setText(empresa.getDomicilio());
        txtRepresentante.setText(empresa.getRepresentante());
        txtCorreoR.setText(empresa.getCorreoRL());
        chckFirmado.setSelected("SI".equals(empresa.getConvenio())); // "SI" → marcado, "NO" → desmarcado
        txtWeb.setText(empresa.getWeb() != null ? empresa.getWeb() : "");
        spnNumEmpleados.setValue(empresa.getNumEmpleados());
        txtTelefono.setText(empresa.getTelefono() != null ? empresa.getTelefono() : "");
    }

    // Igual que en PRegistrarEmpresa, con validaciones
    public Empresa obtenerDatos() {
        String cif          = txtCIF.getText().trim();
        String razon        = txtRazon.getText().trim();
        String domicilio    = txtDomicilio.getText().trim();
        String representante = txtRepresentante.getText().trim();
        String correo       = txtCorreoR.getText().trim();
        String telefono     = txtTelefono.getText().trim();
        String web          = txtWeb.getText().trim();
        int numEmpleados    = (int) spnNumEmpleados.getValue();
        String convenio     = chckFirmado.isSelected() ? "SI" : "NO";

        if (cif.isEmpty() || razon.isEmpty() || domicilio.isEmpty()
                || representante.isEmpty() || correo.isEmpty()) {
            mostrarMensaje(Textos.MSJ_ERROR_DATOS, Textos.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (!Empresa.validarCif(cif)) {
            mostrarMensaje(Textos.MSJ_ERROR_CIF, Textos.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (!Empresa.validarCorreo(correo)) {
            mostrarMensaje(Textos.MSJ_ERROR_CORREO, Textos.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (!telefono.isEmpty() && !Empresa.validarTelefono(telefono)) {
            mostrarMensaje(Textos.MSJ_ERROR_TELEF, Textos.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (!web.isEmpty() && !Empresa.validarWeb(web)) {
            mostrarMensaje(Textos.MSJ_ERROR_WEB, Textos.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return new Empresa(cif, razon, domicilio, representante, correo, convenio, numEmpleados, telefono, web);
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public JButton getBtnGuardar()  { return btnGuardar; }
    public JButton getBtnCancelar() { return btnCancelar; }
}
```

---

## 13. Archivo: ControladorEmpresas.java — el cerebro

El controlador es el único que:

- Tiene referencias a todos los paneles y la ventana
- Escucha todos los eventos
- Llama al DAO
- Decide qué hacer con el resultado

```java
package com.dam.control;

import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import com.dam.model.datos.*;
import com.dam.model.db.EmpresasDAO;
import com.dam.view.*;

public class ControladorEmpresas implements ActionListener {

    // ── Atributos: referencias a todas las vistas y al DAO ────────────────────

    private VPEmpresas       vp;   // ventana principal
    private PRegistrarEmpresa pre;  // panel de alta
    private PConsultaEmpresas pce;  // panel de consulta con tabla
    private PModificarEmpresa pme;  // panel de modificación

    private EmpresasDAO datosEmpresas;  // único punto de acceso a la BD

    // vp llega por constructor porque es el primer objeto que existe
    public ControladorEmpresas(VPEmpresas vp) {
        this.vp = vp;
        datosEmpresas = new EmpresasDAO();
    }

    // Los paneles llegan por setters porque se crean después del controlador
    public void setPre(PRegistrarEmpresa pre) { this.pre = pre; }
    public void setPce(PConsultaEmpresas pce) { this.pce = pce; }
    public void setPme(PModificarEmpresa pme) { this.pme = pme; }

    // ── El único método público de eventos ───────────────────────────────────
    // Se ejecuta cada vez que el usuario pulsa un botón o item de menú registrado

    @Override
    public void actionPerformed(ActionEvent ev) {
        Object origen = ev.getSource();  // objeto que generó el evento

        // Primero comprobar si es un JMenuItem (viene del menú de la ventana)
        if (origen instanceof JMenuItem) {
            if (origen.equals(vp.getMntmConsulta())) {
                pce.limpiarConsulta();       // resetear filtros y tabla
                vp.cargarPanel(pce);         // mostrar el panel de consulta
            } else if (origen.equals(vp.getMntmRegistrar())) {
                pre.limpiarDatos();
                vp.cargarPanel(pre);
            }
        }
        // Botones del panel de registro
        else if (origen.equals(pre.getBtnGuardar()))  { registrar(); }
        else if (origen.equals(pre.getBtnCancelar())) { pre.limpiarDatos(); }
        // Botones del panel de consulta
        else if (origen.equals(pce.getBtnBuscar()))    { buscar(); }
        else if (origen.equals(pce.getBtnEliminar()))  { eliminar(); }
        else if (origen.equals(pce.getBtnModificar())) { abrirModificar(); }
        // Botones del panel de modificación
        else if (origen.equals(pme.getBtnGuardar()))  { modificar(); }
        else if (origen.equals(pme.getBtnCancelar())) {
            buscar();              // refrescar la tabla con los datos actuales
            vp.cargarPanel(pce);   // volver al panel de consulta
        }
    }

    // ── Métodos privados: uno por cada acción ─────────────────────────────────

    private void registrar() {
        Empresa empresa = pre.obtenerDatos();  // devuelve null si hay error de validación
        if (empresa != null) {
            int res = datosEmpresas.insertEmpresa(empresa);
            if (res > 0) {
                pre.mostrarMensaje(Textos.MSJ_REGISTRO_OK, Textos.TIT_RESULTADO, JOptionPane.INFORMATION_MESSAGE);
                pre.limpiarDatos();   // limpiar formulario tras éxito
            } else {
                pre.mostrarMensaje(Textos.MSJ_REGISTRO_KO, Textos.TIT_RESULTADO, JOptionPane.ERROR_MESSAGE);
            }
        }
        // Si empresa == null, el propio panel ya mostró el mensaje de error
    }

    private void buscar() {
        String cif   = pce.getCifFiltro();
        String razon = pce.getRazonFiltro();

        ArrayList<Empresa> lista = datosEmpresas.buscarEmpresas(cif, razon);

        if (lista.isEmpty()) {
            pce.mostrarMensaje(Textos.MSJ_NO_DATOS, Textos.TIT_CONSULTA, JOptionPane.INFORMATION_MESSAGE);
            pce.setVisibleTabla(false);   // ocultar tabla si no hay resultados
        } else {
            pce.cargarTabla(lista);       // cargar los datos en el modelo
            pce.setVisibleTabla(true);    // mostrar tabla y botones
        }
    }

    private void eliminar() {
        // 1. Comprobar que hay una fila seleccionada
        int fila = pce.getTblEmpresas().getSelectedRow();
        if (fila == -1) {
            pce.mostrarMensaje(Textos.MSJ_ERROR_SEL_E, Textos.TIT_ERROR_SEL, JOptionPane.ERROR_MESSAGE);
            return;
        }
        // 2. Pedir confirmación
        int confirmacion = pce.mostrarConfirmacion(
            Textos.MSJ_CONFIR_ELIMINAR, Textos.TIT_CONFIRM, JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) return;

        // 3. Obtener el CIF de la columna 0 de la fila seleccionada
        String cif = (String) pce.getDtmEmpresas().getValueAt(fila, 0);

        // 4. Borrar en BD
        int res = datosEmpresas.eliminarEmpresa(cif);
        if (res > 0) {
            pce.mostrarMensaje(Textos.MSJ_RESULT_ELIMINAR, Textos.TIT_RESULTADO, JOptionPane.INFORMATION_MESSAGE);
            buscar();  // refrescar la tabla
        } else {
            pce.mostrarMensaje(Textos.MSJ_MODIF_KO, Textos.TIT_RESULTADO, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirModificar() {
        // 1. Comprobar que hay una fila seleccionada
        int fila = pce.getTblEmpresas().getSelectedRow();
        if (fila == -1) {
            pce.mostrarMensaje(Textos.MSJ_ERROR_SEL_M, Textos.TIT_ERROR_SEL, JOptionPane.ERROR_MESSAGE);
            return;
        }
        // 2. Obtener el CIF de columna 0
        String cif = (String) pce.getDtmEmpresas().getValueAt(fila, 0);

        // 3. Cargar el objeto completo desde la BD (la tabla puede no tener todos los campos)
        Empresa empresa = datosEmpresas.obtenerEmpresaPorCif(cif);
        if (empresa != null) {
            pme.cargarDatos(empresa);   // pre-rellenar el formulario de modificación
            vp.cargarPanel(pme);        // cambiar al panel de modificación
        }
    }

    private void modificar() {
        Empresa empresa = pme.obtenerDatos();
        if (empresa != null) {
            int res = datosEmpresas.updateEmpresa(empresa);
            if (res > 0) {
                pme.mostrarMensaje(Textos.MSJ_MODIF_OK, Textos.TIT_RESULTADO, JOptionPane.INFORMATION_MESSAGE);
                buscar();            // refrescar tabla con el cambio
                vp.cargarPanel(pce); // volver a consulta
            } else {
                pme.mostrarMensaje(Textos.MSJ_MODIF_KO, Textos.TIT_RESULTADO, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
```

---

## 14. Archivo: InicioEmpresas.java — el main

```java
import javax.swing.SwingUtilities;
import com.dam.control.ControladorEmpresas;
import com.dam.view.*;

public class InicioEmpresas {

    public static void main(String[] args) {
        // SwingUtilities.invokeLater garantiza que toda la UI se crea en el hilo de eventos
        // de Swing (EDT - Event Dispatch Thread). Sin esto, puede haber problemas de
        // concurrencia en pantallas con múltiples componentes.
        SwingUtilities.invokeLater(() -> {

            // FASE 1: Crear todas las vistas
            VPEmpresas        vp  = new VPEmpresas();           // JFrame: llama a configurarVentana() y crearMenu()
            PRegistrarEmpresa pre = new PRegistrarEmpresa();    // JPanel: llama a crearComponentes()
            PConsultaEmpresas pce = new PConsultaEmpresas();    // JPanel con tabla
            PModificarEmpresa pme = new PModificarEmpresa();    // JPanel de edición

            // FASE 2: Crear el controlador y darle acceso a las vistas
            ControladorEmpresas ce = new ControladorEmpresas(vp);
            ce.setPre(pre);
            ce.setPce(pce);
            ce.setPme(pme);

            // FASE 3: Registrar el controlador como ActionListener en cada vista
            // Internamente cada setControlador() hace addActionListener(ce) en sus botones
            vp.setControlador(ce);
            pre.setControlador(ce);
            pce.setControlador(ce);
            pme.setControlador(ce);

            // FASE 4: Mostrar la ventana
            vp.hacerVisible();  // setVisible(true)
        });
    }
}
```

---

## 15. La tabla en detalle: JTable + DefaultTableModel

Este es el concepto que más confunde. Explicación paso a paso:

### Relación entre los tres objetos

```
JScrollPane  ──contiene──►  JTable  ──usa──►  DefaultTableModel
  (scroll)                 (visual)           (datos en memoria)
```

Cuando cambias el `DefaultTableModel` (añades filas, las borras), el `JTable` se actualiza automáticamente.

### Configuración inicial (una sola vez)

```java
// 1. Crear el modelo SIN filas ni columnas todavía
DefaultTableModel dtmEmpresas = new DefaultTableModel() {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;  // tabla de solo lectura
    }
};

// 2. Crear la tabla y asignarle el modelo
JTable tblEmpresas = new JTable();
tblEmpresas.setModel(dtmEmpresas);

// 3. Añadir las columnas (solo headers, sin filas todavía)
dtmEmpresas.addColumn("CIF");        // columna 0
dtmEmpresas.addColumn("RAZON_SOCIAL"); // columna 1
// ... etc

// 4. Meter la tabla en un JScrollPane y el scroll en el panel
JScrollPane scrpEmpresas = new JScrollPane();
scrpEmpresas.setViewportView(tblEmpresas);
add(scrpEmpresas);
```

### Cargar filas (cada vez que hay resultados)

```java
public void cargarTabla(ArrayList<Empresa> lista) {
    tblEmpresas.clearSelection();  // deseleccionar fila actual
    dtmEmpresas.setRowCount(0);    // borrar filas sin borrar columnas

    for (Empresa e : lista) {
        dtmEmpresas.addRow(new Object[] {
            e.getCif(),           // posición 0, mismo orden que addColumn
            e.getRazonSocial(),   // posición 1
            // ... etc
        });
    }
}
```

### Leer la fila seleccionada (en el controlador)

```java
// Paso 1: obtener índice de fila seleccionada (-1 si ninguna)
int fila = pce.getTblEmpresas().getSelectedRow();
if (fila == -1) {
    // mostrar error: "debe seleccionar un registro"
    return;
}

// Paso 2: obtener el valor de una celda concreta
// getValueAt(fila, columna)  ← columna 0 = primera columna = CIF
String cif = (String) pce.getDtmEmpresas().getValueAt(fila, 0);
```

### Error típico con la tabla

```java
// MAL: borra columnas además de filas
dtmEmpresas.getDataVector().clear();

// BIEN: borra solo las filas
dtmEmpresas.setRowCount(0);
```

---

## 16. Flujo completo: registrar una empresa

```
Usuario rellena el formulario y pulsa "Guardar"
    │
    ▼
PRegistrarEmpresa.btnGuardar dispara ActionEvent
    │
    ▼
ControladorEmpresas.actionPerformed(ev)
    origen.equals(pre.getBtnGuardar()) → true
    │
    ▼
registrar()
    │
    ├─ pre.obtenerDatos()
    │      lee campos: txtCIF.getText().trim() ...
    │      valida obligatorios → si falta algo: mostrarMensaje + return null
    │      valida CIF: Empresa.validarCif(cif) → si falla: mostrarMensaje + return null
    │      valida correo → si falla: mostrarMensaje + return null
    │      valida teléfono (solo si no vacío) → si falla: mostrarMensaje + return null
    │      return new Empresa(cif, razon, ...)
    │
    ├─ si empresa != null:
    │      datosEmpresas.insertEmpresa(empresa)
    │          abre conexión → prepareStatement → setString(1, cif) ... → executeUpdate()
    │          devuelve 1 (éxito) o 0 (fallo)
    │
    └─ si res > 0:
           pre.mostrarMensaje(MSJ_REGISTRO_OK, ...)
           pre.limpiarDatos()
       si res == 0:
           pre.mostrarMensaje(MSJ_REGISTRO_KO, ...)
```

---

## 17. Flujo completo: buscar y mostrar en tabla

```
Usuario escribe un filtro y pulsa "Buscar"
    │
    ▼
PConsultaEmpresas.btnBuscar dispara ActionEvent
    │
    ▼
ControladorEmpresas.actionPerformed(ev)
    origen.equals(pce.getBtnBuscar()) → true
    │
    ▼
buscar()
    │
    ├─ cif   = pce.getCifFiltro()   → txtCIF.getText().trim()
    ├─ razon = pce.getRazonFiltro() → txtRazon.getText().trim()
    │
    ├─ lista = datosEmpresas.buscarEmpresas(cif, razon)
    │              construye SELECT * FROM EMPRESAS [WHERE CIF=? OR RAZON_SOCIAL LIKE ?]
    │              rs.next() en bucle → mapearEmpresa(rs) → lista.add(empresa)
    │              devuelve ArrayList<Empresa>
    │
    ├─ si lista.isEmpty():
    │      pce.mostrarMensaje(MSJ_NO_DATOS, ...)
    │      pce.setVisibleTabla(false)  → oculta scroll, etiqueta y botones
    │
    └─ si no vacía:
           pce.cargarTabla(lista)
               dtmEmpresas.setRowCount(0)      → borra filas
               for cada empresa → dtmEmpresas.addRow(new Object[]{...})  → añade fila
           pce.setVisibleTabla(true)  → muestra scroll, etiqueta y botones
```

---

## 18. Flujo completo: modificar desde la tabla

Este es el flujo más complejo porque pasa por **tres paneles**.

```
Usuario selecciona fila y pulsa "Modificar"
    │
    ▼
ControladorEmpresas.actionPerformed(ev)
    origen.equals(pce.getBtnModificar()) → true
    │
    ▼
abrirModificar()
    │
    ├─ fila = pce.getTblEmpresas().getSelectedRow()
    │      si fila == -1: error "debe seleccionar un registro" → return
    │
    ├─ cif = (String) pce.getDtmEmpresas().getValueAt(fila, 0)
    │      obtiene el CIF de la columna 0 de la fila seleccionada
    │
    ├─ empresa = datosEmpresas.obtenerEmpresaPorCif(cif)
    │      SELECT * FROM EMPRESAS WHERE CIF = ?
    │      devuelve objeto Empresa completo
    │
    ├─ pme.cargarDatos(empresa)
    │      txtCIF.setText(empresa.getCif())
    │      txtRazon.setText(empresa.getRazonSocial())
    │      ...
    │      chckFirmado.setSelected("SI".equals(empresa.getConvenio()))
    │
    └─ vp.cargarPanel(pme)
           scrpContenedor.setViewportView(pme)  → muestra el panel de modificación

El usuario edita campos y pulsa "Guardar"
    │
    ▼
ControladorEmpresas.actionPerformed(ev)
    origen.equals(pme.getBtnGuardar()) → true
    │
    ▼
modificar()
    │
    ├─ empresa = pme.obtenerDatos()   (igual que en registro, con validaciones)
    │
    ├─ datosEmpresas.updateEmpresa(empresa)
    │      UPDATE EMPRESAS SET RAZON_SOCIAL=?, ... WHERE CIF=?
    │
    ├─ si res > 0:
    │      pme.mostrarMensaje(MSJ_MODIF_OK, ...)
    │      buscar()          → refresca la tabla con los datos actualizados
    │      vp.cargarPanel(pce) → vuelve al panel de consulta
    │
    └─ si res == 0:
           pme.mostrarMensaje(MSJ_MODIF_KO, ...)
```

---

## 19. Flujo completo: eliminar desde la tabla

```
Usuario selecciona fila y pulsa "Eliminar"
    │
    ▼
ControladorEmpresas.actionPerformed(ev)
    origen.equals(pce.getBtnEliminar()) → true
    │
    ▼
eliminar()
    │
    ├─ fila = pce.getTblEmpresas().getSelectedRow()
    │      si fila == -1: error "debe seleccionar un registro" → return
    │
    ├─ confirmacion = pce.mostrarConfirmacion("¿desea continuar?", ..., YES_NO_OPTION)
    │      si confirmacion != YES_OPTION → return (usuario canceló)
    │
    ├─ cif = (String) pce.getDtmEmpresas().getValueAt(fila, 0)
    │
    ├─ datosEmpresas.eliminarEmpresa(cif)
    │      DELETE FROM EMPRESAS WHERE CIF = ?
    │      devuelve 1 (borrado) o 0 (no encontrado)
    │
    └─ si res > 0:
           pce.mostrarMensaje(MSJ_RESULT_ELIMINAR, ...)
           buscar()   → refresca la tabla sin el registro eliminado
```

---

## 20. Checklist de examen

Usa esta lista en el orden indicado. Cada punto depende del anterior.

### Entidad y contratos

- [ ] Clase entidad con todos los campos, constructor y getters
- [ ] Métodos `validarXxx` según el enunciado (son `static`)
- [ ] `XxxContracts` con `NOM_TABLA` y todos los `COL_xxx`
- [ ] Valores de `COL_xxx` coinciden exactamente con el DDL

### Base de datos

- [ ] Carpeta `BBDD/` creada
- [ ] Archivo `.db` creado ejecutando el DDL
- [ ] `ConfiguracionDB.properties` apunta a la ruta correcta del `.db`

### DAO

- [ ] `insertXxx`: query con todas las columnas y misma cantidad de `?`
- [ ] `insertXxx`: `setString`/`setInt` en el orden correcto
- [ ] `buscarXxx`: devuelve `ArrayList<Entidad>`, usa `executeQuery()`
- [ ] `buscarXxx`: maneja correctamente los filtros opcionales
- [ ] `obtenerXxxPorId`: usa `if (rs.next())` (no `while`)
- [ ] `updateXxx`: columnas en SET, clave primaria en WHERE al final
- [ ] `deleteXxx`: solo necesita la clave primaria
- [ ] `mapearXxx(ResultSet)`: privado, reutilizado en todos los SELECT
- [ ] `cerrar()`: se llama en `finally` en todos los métodos

### Ventana principal (JFrame)

- [ ] Constructor llama a `configurarVentana()` y `crearMenu()`
- [ ] `JMenuItem` son atributos de clase (no variables locales)
- [ ] `setControlador` hace `addActionListener(ce)` en cada `JMenuItem`
- [ ] `cargarPanel(JPanel)` hace `scrpContenedor.setViewportView(panel)`
- [ ] Getters para cada `JMenuItem` (`getMntmConsulta()`, etc.)
- [ ] `hacerVisible()` llama a `setVisible(true)`

### Paneles formulario (registro y modificación)

- [ ] Constructor llama a `crearComponentes()` y `setPreferredSize()`
- [ ] Campos y botones son **atributos de clase**, no variables locales
- [ ] `setControlador` hace `addActionListener(control)` en cada botón
- [ ] `obtenerDatos()`: lee todos los campos con `.getText().trim()`
- [ ] `obtenerDatos()`: valida obligatorios → `mostrarMensaje + return null`
- [ ] `obtenerDatos()`: valida formatos → `mostrarMensaje + return null`
- [ ] `obtenerDatos()`: devuelve `new Entidad(...)` si todo correcto
- [ ] `limpiarDatos()`: resetea todos los campos a vacío/0/false
- [ ] `mostrarMensaje()`: llama a `JOptionPane.showMessageDialog(this,...)`
- [ ] Getters para cada botón (`getBtnGuardar()`, `getBtnCancelar()`)
- [ ] En modificación: `cargarDatos(Entidad e)` que pre-rellena todos los campos
- [ ] En modificación: el campo de la clave primaria tiene `setEditable(false)`

### Panel de consulta con tabla

- [ ] Todos los puntos de paneles formulario
- [ ] `JTable`, `DefaultTableModel` y `JScrollPane` son atributos de clase
- [ ] `configurarTabla()`: crea modelo con `isCellEditable → false`
- [ ] `configurarTabla()`: `addColumn(...)` por cada columna, en orden
- [ ] `tblEmpresas.setSelectionMode(SINGLE_SELECTION)`
- [ ] `cargarTabla()`: primero `setRowCount(0)`, luego `addRow(new Object[]{...})`
- [ ] `addRow`: mismo número de valores que columnas, en el mismo orden
- [ ] `setVisibleTabla(boolean)`: muestra/oculta scroll, etiqueta Y botones
- [ ] `limpiarConsulta()`: limpia filtros, `setRowCount(0)`, `setVisibleTabla(false)`
- [ ] Getters: `getCifFiltro()`, `getRazonFiltro()`, `getTblEmpresas()`, `getDtmEmpresas()`
- [ ] Getter para cada botón de acción
- [ ] `mostrarConfirmacion()`: devuelve `JOptionPane.showConfirmDialog(...)`

### Controlador

- [ ] Implementa `ActionListener`
- [ ] Atributos para la ventana, todos los paneles y el DAO
- [ ] Constructor recibe la ventana; setters reciben los paneles
- [ ] `actionPerformed`: comprueba `instanceof JMenuItem` para el menú
- [ ] `actionPerformed`: un `else if` por cada botón de cada panel
- [ ] Comparar con `.equals()`, nunca con `==`
- [ ] `registrar()`: `pre.obtenerDatos()` → si `!= null` → DAO → mensaje → limpiar
- [ ] `buscar()`: filtros de `pce` → DAO → `cargarTabla` o mensaje
- [ ] `eliminar()`: comprobar selección → confirmación → CIF de columna 0 → DAO → refrescar
- [ ] `abrirModificar()`: comprobar selección → CIF de columna 0 → DAO → `pme.cargarDatos()` → `vp.cargarPanel(pme)`
- [ ] `modificar()`: `pme.obtenerDatos()` → si `!= null` → DAO → mensaje → `buscar()` → `vp.cargarPanel(pce)`

### Main

- [ ] `SwingUtilities.invokeLater(() -> { ... })`
- [ ] Crear todas las vistas
- [ ] Crear controlador pasándole la ventana
- [ ] `ce.setPre`, `ce.setPce`, `ce.setPme` (un setter por panel)
- [ ] `vp.setControlador(ce)`, `pre.setControlador(ce)`, `pce.setControlador(ce)`, `pme.setControlador(ce)`
- [ ] `vp.hacerVisible()`

---

## 21. Errores frecuentes con solución

### El botón no hace nada al pulsarlo

Puede fallar en tres sitios distintos. Revisar en orden:

```java
// ¿El botón está declarado como atributo de clase?
private JButton btnBuscar;  // ← debe estar aquí, no dentro de crearComponentes()

// ¿Se registró el controlador en setControlador()?
btnBuscar.addActionListener(control);  // ← sin esto no hay evento

// ¿Existe el else if correspondiente en actionPerformed()?
else if (origen.equals(pce.getBtnBuscar())) { buscar(); }  // ← sin esto no hace nada
```

### NullPointerException al pulsar un menú o botón

El controlador tiene un atributo `null`. Causas más comunes:

```java
// ¿Se llamó al setter correspondiente en el main?
ce.setPce(pce);  // ← si falta esto, pce es null en el controlador

// ¿Se usó el getter correcto en actionPerformed()?
origen.equals(pce.getBtnBuscar())  // si pce es null → NPE
```

### La tabla aparece vacía aunque el DAO devuelva datos

```java
// ¿Las columnas en configurarTabla() coinciden en número y orden con addRow()?
dtmEmpresas.addColumn("CIF");           // columna 0
dtmEmpresas.addColumn("RAZON_SOCIAL");  // columna 1

// En cargarTabla():
dtmEmpresas.addRow(new Object[] {
    e.getCif(),         // posición 0 ← debe coincidir con columna 0
    e.getRazonSocial(), // posición 1 ← debe coincidir con columna 1
});
// Si el array tiene más o menos elementos que columnas → datos no se muestran o excepción

// ¿Se llama a setVisibleTabla(true) después de cargarTabla()?
// Si el JScrollPane está oculto (setVisible(false)), la tabla no se ve aunque tenga datos
```

### UnsupportedClassVersionError al ejecutar desde el IDE

```
java.lang.UnsupportedClassVersionError: InicioEmpresas has been compiled by a more recent
version of the Java Runtime (class file version 70.0), this version of the Java Runtime
only recognizes class file versions up to 65.0
```

El compilador del sistema (`javac`) es más nuevo que el JRE que usa el IDE (Cursor/Eclipse). La tabla de versiones:


| Class file | Java                               |
| ---------- | ---------------------------------- |
| 65.0       | **21** ← el JRE de Cursor          |
| 66.0       | 22                                 |
| 67.0       | 23                                 |
| 68.0       | 24                                 |
| 69.0       | 25                                 |
| 70.0       | **26** ← el compilador del sistema |


**Solución:** usar siempre `--release 21` al compilar desde terminal para que el bytecode sea compatible con el JRE del IDE:

```bash
javac --release 21 -cp "lib/sqlite-jdbc-3.51.3.0.jar" -d bin $(find src -name "*.java")
```

Desde Eclipse o Cursor no hace falta: el proyecto tiene configurado `compliance=21` en `.settings/org.eclipse.jdt.core.prefs` y compila directamente a Java 21.

---

### No encuentra el archivo ConfiguracionDB.properties

```
FileNotFoundException: DB/ConfiguracionDB.properties (No such file or directory)
```

La ruta `"DB/ConfiguracionDB.properties"` es relativa al directorio de trabajo. En Eclipse, el directorio de trabajo es la raíz del proyecto (donde está `src/`). En terminal, debes estar en `ExamenProgAEv3_2526/`.

### Error SQL "no such table"

```
SQLException: no such table: EMPRESAS
```

O no creaste el archivo `.db`, o lo creaste sin ejecutar el DDL, o el nombre en `EmpresaContracts.NOM_TABLA` no coincide con el del DDL.

```java
// En EmpresaContracts:
public static final String NOM_TABLA = "EMPRESAS";  // debe coincidir exactamente con el DDL

// En el DDL:
CREATE TABLE EMPRESAS ( ... )  // mayúsculas, igual que en NOM_TABLA
```

### El INSERT devuelve 0 sin lanzar excepción

El registro no se guardó porque viola alguna restricción de la BD (clave primaria duplicada, UNIQUE constraint, CHECK constraint). Revisar el DDL y los datos enviados.

```sql
-- Este DDL tiene tres restricciones que pueden causar insert=0:
CIF          TEXT PRIMARY KEY    -- no puede haber dos empresas con el mismo CIF
RAZON_SOCIAL TEXT NOT NULL UNIQUE -- no puede haber dos empresas con la misma razón social
CONVENIO     CHECK(CONVENIO IN ('SI', 'NO'))  -- solo acepta exactamente "SI" o "NO"
```

### La ventana aparece pero los paneles están vacíos al pulsar el menú

**Causa 1 — orden incorrecto de `setLayout` y `add` en `configurarVentana()`.**
`BorderLayout` guarda internamente qué componente ocupa cada posición (CENTER, NORTH…). Si llamas a `add()` antes que a `setLayout()`, el componente queda registrado en el layout viejo. Al reemplazarlo con `setLayout()`, el nuevo `BorderLayout` no sabe dónde está el `JScrollPane` y le asigna tamaño 0.

```java
// MAL: add() antes de setLayout() → el nuevo BorderLayout no conoce CENTER → JScrollPane tamaño 0
getContentPane().add(scrpContenedor, BorderLayout.CENTER);
getContentPane().setLayout(new BorderLayout(0, 0));  // reemplaza el layout: pierde la referencia a CENTER

// BIEN: setLayout() primero, luego add()
getContentPane().setLayout(new BorderLayout(0, 0));
getContentPane().add(scrpContenedor, BorderLayout.CENTER);
```

**Causa 2 — `cargarPanel()` sin `revalidate()` ni `repaint()`.**
Después de `setViewportView()`, el scroll pane no siempre se redibuja solo. Sin estas dos llamadas el panel puede quedarse en blanco hasta que el usuario mueva la ventana.

```java
// MAL: el panel puede no aparecer en pantalla
public void cargarPanel(JPanel panel) {
    scrpContenedor.setViewportView(panel);
}

// BIEN: forzar el recálculo y redibujado
public void cargarPanel(JPanel panel) {
    scrpContenedor.setViewportView(panel);
    scrpContenedor.revalidate();
    scrpContenedor.repaint();
}
```

---

### getSelectedRow() siempre devuelve -1

El usuario no ha seleccionado ninguna fila. Esto es correcto: siempre hay que comprobarlo antes de intentar leer los datos:

```java
int fila = pce.getTblEmpresas().getSelectedRow();
if (fila == -1) {
    pce.mostrarMensaje(Textos.MSJ_ERROR_SEL_E, Textos.TIT_ERROR_SEL, JOptionPane.ERROR_MESSAGE);
    return;  // salir del método, no continuar
}
```

---

## 22. Cómo ejecutar el proyecto

### Desde terminal

```bash
# Situarse en la carpeta raíz del proyecto
cd ExamenProgAEv3_2526

# Crear la base de datos (solo la primera vez)
mkdir -p BBDD
sqlite3 BBDD/BBDD.db < DB/EMPRESAS.ddl

# Compilar todos los .java apuntando a Java 21
# --release 21 es obligatorio si el compilador del sistema es más nuevo que el JRE del IDE
javac --release 21 -cp "lib/sqlite-jdbc-3.51.3.0.jar" -d bin $(find src -name "*.java")

# Ejecutar
java -cp "bin:lib/sqlite-jdbc-3.51.3.0.jar" InicioEmpresas
```

### Desde Eclipse

1. Clic derecho sobre `InicioEmpresas.java`
2. Run As → Java Application

El directorio de trabajo en Eclipse es automáticamente la raíz del proyecto, así que `DB/ConfiguracionDB.properties` y `BBDD/BBDD.db` se encuentran sin problemas.

---

## 23. Swing: qué usa este ejercicio y qué no

Este proyecto **no necesita** más componentes Swing de los que ya tiene para funcionar. Pero en otros exámenes del DAM suelen aparecer elementos que aquí no se practican. Esta sección es **solo documentación**: los ejemplos son independientes y **no forman parte del código del ejercicio**.

### Lo que sí usa este proyecto

| Área | Elementos |
|------|-----------|
| Ventana | `JFrame`, `JMenuBar`, `JMenu`, `JMenuItem` |
| Contenedores | `JPanel`, `JScrollPane` |
| Entrada | `JTextField`, `JCheckBox`, `JSpinner` |
| Acción | `JButton`, `JLabel` |
| Datos | `JTable`, `DefaultTableModel`, `ListSelectionModel` |
| Diálogos | `JOptionPane` |
| Eventos | Solo `ActionListener` |
| Layout | `BorderLayout` (ventana) + `setLayout(null)` + `setBounds` (paneles) |
| Arranque | `SwingUtilities.invokeLater` |

### Lo más habitual en otros exámenes y ausente aquí

| Elemento | Por qué importa | Qué usa este ejercicio en su lugar |
|----------|-----------------|-----------------------------------|
| `JComboBox` | Filtros y listas desplegables | `JTextField` en consulta; `JCheckBox` para convenio |
| `JRadioButton` + `ButtonGroup` | Opciones excluyentes | `JCheckBox` (SI/NO) |
| `CardLayout` | Cambiar paneles con `show()` | `JScrollPane.setViewportView()` en `cargarPanel()` |
| `ListSelectionListener` | Habilitar botones al seleccionar fila | Botones visibles/habilitados al buscar |
| Otros layouts | `FlowLayout`, `GridLayout`, `GridBagLayout`… | Solo `BorderLayout` y posicionamiento absoluto |
| Otros listeners | `MouseListener`, `KeyListener`, `ItemListener`… | Solo `ActionListener` |

---

### Ejemplo 1: `JComboBox` como filtro de convenio

En exámenes como el de Michelin, los filtros suelen ser desplegables. Aquí el convenio es un `JCheckBox`, pero en otro enunciado podría pedirse esto:

```java
import javax.swing.*;

public class EjemploComboBox extends JPanel {

    private JComboBox<String> cmbConvenio;

    public EjemploComboBox() {
        setLayout(null);

        JLabel lbl = new JLabel("Convenio:");
        lbl.setBounds(30, 30, 80, 24);
        add(lbl);

        // Opciones fijas del desplegable
        cmbConvenio = new JComboBox<>(new String[] { "Todos", "Firmado", "Pendiente" });
        cmbConvenio.setBounds(120, 28, 150, 24);
        add(cmbConvenio);
    }

    // Getter para el controlador (misma idea que getCifFiltro())
    public String getConvenioFiltro() {
        Object seleccion = cmbConvenio.getSelectedItem();
        if (seleccion == null || "Todos".equals(seleccion)) {
            return "";          // sin filtro → el DAO devuelve todo
        }
        if ("Firmado".equals(seleccion)) {
            return "SI";
        }
        return "NO";
    }

    public JComboBox<String> getCmbConvenio() {
        return cmbConvenio;
    }
}
```

**En el controlador**, leerías el filtro igual que `getCifFiltro()`:

```java
String cif      = pce.getCifFiltro();
String razon    = pce.getRazonFiltro();
String convenio = pce.getConvenioFiltro();   // "SI", "NO" o "" si eligió "Todos"
ArrayList<Empresa> lista = datosEmpresas.buscarEmpresas(cif, razon, convenio);
```

#### Impacto en el DAO: **sí cambia** (solo la búsqueda)

Hoy `buscarEmpresas(String cif, String razon)` no filtra por convenio. Habría que ampliar la firma y añadir otra condición al `WHERE`:

```java
public ArrayList<Empresa> buscarEmpresas(String cif, String razon, String convenio) {
    ArrayList<Empresa> lista = new ArrayList<>();
    StringBuilder query = new StringBuilder("SELECT * FROM " + EmpresaContracts.NOM_TABLA);
    ArrayList<String> condiciones = new ArrayList<>();

    if (cif != null && !cif.isEmpty()) {
        condiciones.add(EmpresaContracts.COL_ID + " = ?");
    }
    if (razon != null && !razon.isEmpty()) {
        condiciones.add(EmpresaContracts.COL_RAZON + " LIKE ?");
    }
    // Nuevo filtro del JComboBox
    if (convenio != null && !convenio.isEmpty()) {
        condiciones.add(EmpresaContracts.COL_CONVENIO + " = ?");
    }

    if (!condiciones.isEmpty()) {
        query.append(" WHERE ").append(String.join(" AND ", condiciones));
        // Si el enunciado pide OR entre CIF y razón pero AND con convenio:
        // WHERE (CIF = ? OR RAZON_SOCIAL LIKE ?) AND CONVENIO = ?
    }

    // ... abrir conexión, asignar parámetros en orden, executeQuery(), mapearEmpresa() ...
    return lista;
}
```

`insertEmpresa` y `updateEmpresa` **no cambian**: el convenio ya se guarda con `e.getConvenio()` (`"SI"` / `"NO"`). El `JComboBox` solo afecta al **SELECT** de consulta.

---

### Ejemplo 2: `JRadioButton` + `ButtonGroup` (opciones excluyentes)

Si el enunciado pide elegir **un solo** rango o tipo entre varios, no sirve un `JCheckBox` por opción: hace falta un `ButtonGroup`:

```java
import javax.swing.*;
import java.awt.event.ActionListener;

public class EjemploRadioButtons extends JPanel {

    private ButtonGroup grpTamano;
    private JRadioButton rdbPequena;
    private JRadioButton rdbMediana;
    private JRadioButton rdbGrande;

    public EjemploRadioButtons() {
        setLayout(null);

        grpTamano = new ButtonGroup();

        rdbPequena  = new JRadioButton("Pequeña (<50 emp.)",  true);  // seleccionada por defecto
        rdbMediana  = new JRadioButton("Mediana (50-250 emp.)");
        rdbGrande   = new JRadioButton("Grande (>250 emp.)");

        rdbPequena.setBounds(30, 30, 200, 24);
        rdbMediana.setBounds(30, 60, 200, 24);
        rdbGrande.setBounds(30, 90, 200, 24);

        // El ButtonGroup garantiza que solo uno esté marcado
        grpTamano.add(rdbPequena);
        grpTamano.add(rdbMediana);
        grpTamano.add(rdbGrande);

        add(rdbPequena);
        add(rdbMediana);
        add(rdbGrande);
    }

    public void setControlador(ActionListener control) {
        rdbPequena.addActionListener(control);
        rdbMediana.addActionListener(control);
        rdbGrande.addActionListener(control);
    }

    public String getTamanoSeleccionado() {
        if (rdbPequena.isSelected())  return "PEQUENA";
        if (rdbMediana.isSelected())  return "MEDIANA";
        return "GRANDE";
    }
}
```

#### Impacto en el DAO: **sí cambia** (filtro por rango numérico)

Los radios devuelven `"PEQUENA"`, `"MEDIANA"`, `"GRANDE"`. Eso **no es una columna** de la tabla: hay que traducirlo a condiciones sobre `NUM_EMPLEADOS`:

| Radio seleccionado | Condición SQL |
|--------------------|---------------|
| Pequeña | `NUM_EMPLEADOS < 50` |
| Mediana | `NUM_EMPLEADOS BETWEEN 50 AND 250` |
| Grande | `NUM_EMPLEADOS > 250` |

**En el controlador:**

```java
String tamano = panel.getTamanoSeleccionado();
ArrayList<Empresa> lista = datosEmpresas.buscarEmpresas(cif, razon, tamano);
```

**En el DAO** (dentro de `buscarEmpresas` o en un método aparte):

```java
if ("PEQUENA".equals(tamano)) {
    condiciones.add(EmpresaContracts.COL_NUM_EMP + " < 50");
} else if ("MEDIANA".equals(tamano)) {
    condiciones.add(EmpresaContracts.COL_NUM_EMP + " BETWEEN 50 AND 250");
} else if ("GRANDE".equals(tamano)) {
    condiciones.add(EmpresaContracts.COL_NUM_EMP + " > 250");
}
// Si no hay filtro de tamaño → no añades condición
```

Estas condiciones **no llevan `?`** porque el rango lo defines tú en el código, no lo introduce el usuario. `insertEmpresa` y `updateEmpresa` **no cambian**: el número de empleados ya va en el `JSpinner` y en `e.getNumEmpleados()`.

---

### Ejemplo 3: `ListSelectionListener` en la tabla

En este ejercicio, **Modificar** y **Eliminar** se habilitan al mostrar resultados. Otro enunciado puede exigir que solo se activen **cuando el usuario selecciona una fila**:

```java
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class EjemploSeleccionTabla extends JPanel {

    private JTable tblEmpresas;
    private DefaultTableModel dtm;
    private JButton btnModificar;
    private JButton btnEliminar;

    public EjemploSeleccionTabla() {
        setLayout(null);

        dtm = new DefaultTableModel(new Object[] { "CIF", "Razón social" }, 0);
        tblEmpresas = new JTable(dtm);
        tblEmpresas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tblEmpresas);
        scroll.setBounds(30, 30, 400, 150);
        add(scroll);

        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(30, 200, 120, 24);
        btnModificar.setEnabled(false);   // deshabilitados hasta seleccionar fila
        add(btnModificar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(170, 200, 120, 24);
        btnEliminar.setEnabled(false);
        add(btnEliminar);

        // Escuchar cambios de selección en la tabla
        tblEmpresas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Ignorar eventos intermedios mientras arrastra el ratón
                if (e.getValueIsAdjusting()) {
                    return;
                }
                int fila = tblEmpresas.getSelectedRow();
                boolean hayFila = fila != -1;
                btnModificar.setEnabled(hayFila);
                btnEliminar.setEnabled(hayFila);
            }
        });
    }

    public JTable getTblEmpresas()       { return tblEmpresas; }
    public DefaultTableModel getDtm()    { return dtm; }
    public JButton getBtnModificar()     { return btnModificar; }
    public JButton getBtnEliminar()      { return btnEliminar; }
}
```

**Diferencia clave con este ejercicio:** aquí no hace falta un `else if` extra en `actionPerformed()`; la vista reacciona sola al seleccionar fila. El controlador sigue comprobando `getSelectedRow() == -1` antes de modificar o eliminar.

#### Impacto en el DAO: **no cambia**

Solo afecta a **cuándo** se habilitan los botones en la vista. Las llamadas al DAO siguen siendo las mismas:

| Acción del usuario | Métodos DAO (sin cambios) |
|--------------------|---------------------------|
| Modificar | `obtenerEmpresaPorCif(cif)` → `updateEmpresa(empresa)` |
| Eliminar | `eliminarEmpresa(cif)` |

El CIF sigue leyéndose de la fila seleccionada:

```java
String cif = (String) pce.getDtmEmpresas().getValueAt(fila, 0);
```

El SQL no sabe si el botón se habilitó al buscar o al seleccionar fila.

---

### Ejemplo 4: `CardLayout` para cambiar paneles

Este proyecto cambia paneles con `cargarPanel()` y `setViewportView()`. El patrón clásico alternativo es `CardLayout`:

```java
import javax.swing.*;
import java.awt.*;

public class EjemploCardLayout extends JFrame {

    private CardLayout cardLayout;
    private JPanel contenedor;
    private JPanel panelConsulta;
    private JPanel panelRegistro;

    public EjemploCardLayout() {
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        panelConsulta = new JPanel();
        panelConsulta.add(new JLabel("Panel de consulta"));

        panelRegistro = new JPanel();
        panelRegistro.add(new JLabel("Panel de registro"));

        // Cada add() necesita un nombre (String) para identificar la "carta"
        contenedor.add(panelConsulta, "consulta");
        contenedor.add(panelRegistro, "registro");

        add(contenedor, BorderLayout.CENTER);

        // Menú de ejemplo
        JMenuItem mntmConsulta = new JMenuItem("Consultar");
        mntmConsulta.addActionListener(e -> mostrarPanel("consulta"));

        JMenuItem mntmRegistrar = new JMenuItem("Registrar");
        mntmRegistrar.addActionListener(e -> mostrarPanel("registro"));

        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Mantenimiento");
        menu.add(mntmConsulta);
        menu.add(mntmRegistrar);
        bar.add(menu);
        setJMenuBar(bar);
    }

    public void mostrarPanel(String nombre) {
        cardLayout.show(contenedor, nombre);  // muestra la "carta" con ese nombre
        contenedor.revalidate();
        contenedor.repaint();
    }
}
```

**Equivalencia con este ejercicio:**

| Este proyecto | Con `CardLayout` |
|---------------|------------------|
| `vp.cargarPanel(pce)` | `cardLayout.show(contenedor, "consulta")` |
| `scrpContenedor.setViewportView(panel)` | Los paneles ya están en el contenedor desde el inicio |

#### Impacto en el DAO: **no cambia**

`CardLayout` es solo **navegación entre paneles**. Registrar, buscar, modificar y eliminar siguen usando los mismos cinco métodos del DAO:

- `insertEmpresa`
- `buscarEmpresas`
- `obtenerEmpresaPorCif`
- `updateEmpresa`
- `eliminarEmpresa`

El DAO no sabe si cambiaste de panel con `cargarPanel()` o con `cardLayout.show()`.

---

### Cómo influye el DAO en cada ejemplo (resumen)

**Regla general:** el DAO solo cambia si el componente Swing introduce un **dato nuevo** que hay que filtrar, insertar o actualizar en la BD. Si solo cambia la interacción o la pantalla, el DAO sigue igual.

```
Vista (Swing)  →  Controlador  →  DAO  →  SQL
     ↑                              ↓
     └──────── Empresa / ResultSet ─┘
```

| Ejemplo | ¿Cambia el DAO? | Qué tocar |
|---------|-----------------|-----------|
| `JComboBox` (convenio) | **Sí** | Ampliar `buscarEmpresas` con parámetro `convenio` y `CONVENIO = ?` |
| `JRadioButton` (tamaño) | **Sí** | Ampliar `buscarEmpresas` con condiciones sobre `NUM_EMPLEADOS` |
| `ListSelectionListener` | **No** | Solo vista: cuándo están activos Modificar/Eliminar |
| `CardLayout` | **No** | Solo vista: cómo se muestran los paneles |

**Flujos según el tipo de componente:**

```
JComboBox / JRadioButton
    → el controlador lee el valor del componente (getter)
    → llama al DAO con parámetros extra
    → el DAO construye el WHERE dinámico
    → devuelve ArrayList<Empresa>
    → la vista hace cargarTabla(lista)

ListSelectionListener / CardLayout
    → solo afectan a la vista (o al controlador al pulsar)
    → las llamadas al DAO son las mismas de siempre
```

**Regla práctica para el examen:** si el componente devuelve un dato que debe **filtrar, insertar o actualizar** en la BD → toca el DAO (y a veces `Empresa` u `obtenerDatos()`). Si solo cambia cuándo se pulsa algo o qué panel se ve → el DAO no se toca.

---

### Qué repasar si el examen trae algo distinto

1. **`JComboBox`** — `getSelectedItem()`, `getSelectedIndex()`, rellenar con `addItem()` o array en el constructor.
2. **`JRadioButton` + `ButtonGroup`** — solo una opción marcada; leer con `isSelected()`.
3. **`ListSelectionListener`** — habilitar acciones según la fila seleccionada en `JTable`.
4. **`CardLayout`** — cambiar vista con `show(contenedor, "nombre")`.
5. **Un layout distinto de `null`** — al menos saber colocar componentes con `FlowLayout` o `GridLayout`.

---

## Resumen en tres frases

1. **La vista** crea componentes, valida datos y expone getters. No llama al DAO.
2. **El DAO** solo tiene SQL. No conoce botones ni paneles. Devuelve objetos o enteros.
3. **El controlador** escucha todos los eventos, decide qué hacer y coordina vista con DAO.

Cuando te bloquees con un TODO pregúntate:

- *¿A qué capa pertenece esta tarea?*
- *¿Quién necesita saber el resultado?*
- *¿Tengo el getter necesario para que el controlador pueda leer ese dato?*

