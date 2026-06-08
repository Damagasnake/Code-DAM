# Teoría — Java Swing + MVC + DAO + JDBC

Guía teórica para entender el proyecto **ExamenProgAEv3_2526** (gestión de empresas con interfaz gráfica y base de datos SQLite).

Este documento explica **conceptos y principios**. Para ver el código paso a paso, consulta el `README.md` práctico del mismo proyecto.

---

## Índice

1. [Visión general del proyecto](#1-visión-general-del-proyecto)
2. [Arquitectura en capas (MVC)](#2-arquitectura-en-capas-mvc)
3. [Patrón DAO](#3-patrón-dao)
4. [Java orientado a objetos (lo imprescindible)](#4-java-orientado-a-objetos-lo-imprescindible)
5. [Programación orientada a eventos](#5-programación-orientada-a-eventos)
6. [Java Swing — fundamentos](#6-java-swing--fundamentos)
7. [Tablas en Swing (JTable)](#7-tablas-en-swing-jtable)
8. [Modelo de datos y validación](#8-modelo-de-datos-y-validación)
9. [SQL y diseño relacional](#9-sql-y-diseño-relacional)
10. [JDBC — acceso a datos desde Java](#10-jdbc--acceso-a-datos-desde-java)
11. [Interfaces como contrato](#11-interfaces-como-contrato)
12. [Ensamblaje de la aplicación (main)](#12-ensamblaje-de-la-aplicación-main)
13. [Flujos de datos del CRUD](#13-flujos-de-datos-del-crud)
14. [Reglas de oro del examen](#14-reglas-de-oro-del-examen)
15. [Errores conceptuales frecuentes](#15-errores-conceptuales-frecuentes)
16. [Extensiones habituales en otros exámenes](#16-extensiones-habituales-en-otros-exámenes)

---

## 1. Visión general del proyecto

Este tipo de ejercicio del DAM combina cuatro tecnologías:


| Tecnología  | Rol                                  |
| ----------- | ------------------------------------ |
| **Java SE** | Lenguaje y lógica de negocio         |
| **Swing**   | Interfaz gráfica de escritorio       |
| **SQLite**  | Base de datos embebida en archivo    |
| **JDBC**    | Puente entre Java y la base de datos |


La aplicación permite **CRUD** (Create, Read, Update, Delete) sobre la entidad `Empresa`:

- **Alta** → formulario de registro
- **Consulta** → filtros + tabla de resultados
- **Modificación** → formulario pre-rellenado
- **Borrado** → desde la fila seleccionada en la tabla

### Estructura lógica

```
InicioEmpresas (main)
    │
    ├── Vista (view)     → pantallas y componentes Swing
    ├── Controlador      → coordina acciones del usuario
    └── Modelo (model)   → entidad Empresa + acceso JDBC (DAO)
```

---

## 2. Arquitectura en capas (MVC)

**MVC** (Model-View-Controller) separa responsabilidades para que el código sea mantenible y predecible en un examen.

### Las tres capas


| Capa                         | Clases del proyecto                                                         | Responsabilidad                                                      |
| ---------------------------- | --------------------------------------------------------------------------- | -------------------------------------------------------------------- |
| **Vista (View)**             | `VPEmpresas`, `PRegistrarEmpresa`, `PConsultaEmpresas`, `PModificarEmpresa` | Mostrar UI, leer campos, validar entrada, mostrar mensajes           |
| **Controlador (Controller)** | `ControladorEmpresas`                                                       | Recibir eventos, decidir qué hacer, llamar al DAO, actualizar vistas |
| **Modelo (Model)**           | `Empresa`, `EmpresasDAO`, `AccesoDB`                                        | Representar datos y persistirlos en BD                               |


### Flujo de comunicación correcto

```
Usuario pulsa botón
    → Vista dispara evento
    → Controlador recibe el evento
    → Controlador llama al DAO
    → DAO ejecuta SQL y devuelve resultado
    → Controlador actualiza la Vista
```

### Lo que está prohibido en este patrón


| Incorrecto                      | Por qué                                               |
| ------------------------------- | ----------------------------------------------------- |
| Vista llama al DAO directamente | Rompe la separación; el controlador deja de coordinar |
| DAO muestra `JOptionPane`       | La capa de datos no debe conocer la interfaz          |
| Un panel llama a otro panel     | Solo el controlador conoce todos los paneles          |
| SQL en el controlador           | El SQL pertenece exclusivamente al DAO                |


### Diagrama de dependencias

```
        ┌─────────────┐
        │   Vista     │
        └──────┬──────┘
               │ eventos / getters
               ▼
        ┌─────────────┐
        │ Controlador │
        └──────┬──────┘
               │ métodos del DAO
               ▼
        ┌─────────────┐      ┌──────────┐
        │     DAO     │ ───► │ SQLite   │
        └──────┬──────┘      └──────────┘
               │
               ▼
        ┌─────────────┐
        │   Empresa   │  (objeto de dominio)
        └─────────────┘
```

---

## 3. Patrón DAO

**DAO** (Data Access Object) encapsula todo el acceso a la base de datos en una clase dedicada.

### Principios

1. **Un método DAO = una operación SQL** (insert, select, update, delete).
2. El DAO **no conoce botones ni paneles**.
3. Devuelve tipos simples:
  - `int` → filas afectadas (insert/update/delete)
  - `ArrayList<Empresa>` → listas de consulta
  - `Empresa` o `null` → un registro o ausencia
4. Usa un objeto de dominio (`Empresa`) para transportar datos entre capas.

### Ventajas en un examen

- El SQL está centralizado en un solo archivo.
- Si cambia la BD, solo tocas el DAO.
- El controlador no necesita saber cómo se escribe un `INSERT`.

### Métodos típicos de un DAO CRUD


| Método                             | SQL                        | Retorno              |
| ---------------------------------- | -------------------------- | -------------------- |
| `insertEmpresa(Empresa e)`         | `INSERT INTO ...`          | `int`                |
| `buscarEmpresas(...)`              | `SELECT ... WHERE ...`     | `ArrayList<Empresa>` |
| `obtenerEmpresaPorCif(String cif)` | `SELECT ... WHERE CIF = ?` | `Empresa` o `null`   |
| `updateEmpresa(Empresa e)`         | `UPDATE ... WHERE CIF = ?` | `int`                |
| `eliminarEmpresa(String cif)`      | `DELETE ... WHERE CIF = ?` | `int`                |


### Contratos de base de datos

`EmpresaContracts` centraliza los nombres de tabla y columnas como constantes `static final`. Evita "strings mágicos" dispersos en el SQL y reduce errores de tipeo.

---

## 4. Java orientado a objetos (lo imprescindible)

### Clase y objeto

- **Clase** (`Empresa`): plantilla con atributos y comportamiento.
- **Objeto**: instancia concreta (`new Empresa(...)`).

### Encapsulación

- Atributos **privados** (`private String cif`).
- Acceso externo mediante **getters** (`getCif()`).
- La vista y el DAO leen datos del objeto sin tocar sus campos directamente.

### Métodos estáticos vs de instancia


| Tipo      | Ejemplo                    | Cuándo usarlo                                |
| --------- | -------------------------- | -------------------------------------------- |
| Estático  | `Empresa.validarCif(cif)`  | Utilidad que no necesita un objeto concreto  |
| Instancia | `empresa.getRazonSocial()` | Opera sobre los datos de un objeto existente |


### Constantes

```java
public static final String NOM_TABLA = "EMPRESAS";
```

- `static`: pertenece a la clase, no a cada objeto.
- `final`: no se puede reasignar.

### Colecciones: ArrayList

- Lista dinámica de objetos (`ArrayList<Empresa>`).
- El DAO la rellena; la vista la recorre para cargar la tabla.
- Métodos clave: `add()`, `isEmpty()`, bucle `for-each`.

### Comparación de objetos

- `==` compara **referencias** (¿es el mismo objeto en memoria?).
- `.equals()` compara **contenido** o identidad lógica.
- En el controlador, al identificar qué botón pulsó el usuario, se usa **siempre** `.equals()`, nunca `==`.

### Manejo de excepciones

Patrón estándar en JDBC:

```java
try {
    // operación con BD
} catch (SQLException ex) {
    ex.printStackTrace();
} finally {
    // cerrar Connection, Statement, ResultSet SIEMPRE
}
```

El bloque `finally` se ejecuta haya o no excepción. Cerrar recursos evita fugas de conexiones.

---

## 5. Programación orientada a eventos

Las aplicaciones Swing no son secuenciales (línea 1, línea 2, línea 3). Son **reactivas**: el programa espera acciones del usuario.

### Conceptos clave


| Concepto     | Descripción                                                  |
| ------------ | ------------------------------------------------------------ |
| **Evento**   | Algo que ocurre (clic en botón, elegir menú)                 |
| **Fuente**   | Componente que lo genera (`JButton`, `JMenuItem`)            |
| **Listener** | Objeto que escucha y reacciona (`ActionListener`)            |
| **EDT**      | Event Dispatch Thread: hilo donde debe ejecutarse toda la UI |


### ActionListener

```java
public class ControladorEmpresas implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent ev) {
        Object origen = ev.getSource();
        // decidir qué hacer según el origen
    }
}
```

Registro del listener:

```java
btnGuardar.addActionListener(control);
```

### Las 5 piezas de un botón funcional

1. Declarar el botón como **atributo de clase**.
2. Instanciarlo en `crearComponentes()`.
3. Registrar el listener en `setControlador()`.
4. Exponer un **getter** (`getBtnGuardar()`).
5. Tratar el evento en `actionPerformed()` del controlador.

Si falta una pieza, el botón no hace nada o lanza `NullPointerException`.

### SwingUtilities.invokeLater

En el `main`, la UI se crea dentro de:

```java
SwingUtilities.invokeLater(() -> { ... });
```

Garantiza que los componentes Swing se construyen en el **hilo de eventos**, evitando problemas de concurrencia.

---

## 6. Java Swing — fundamentos

Swing es el toolkit gráfico de Java para aplicaciones de escritorio.

### Jerarquía de contenedores del proyecto

```
JFrame (VPEmpresas)
 └── JMenuBar
 └── ContentPane (BorderLayout)
      └── JScrollPane (CENTER)
           └── JPanel (panel activo: registro, consulta o modificación)
```

### Componentes usados


| Componente                         | Uso en el proyecto                    |
| ---------------------------------- | ------------------------------------- |
| `JFrame`                           | Ventana principal                     |
| `JPanel`                           | Contenedor de formularios             |
| `JLabel`                           | Etiquetas de texto                    |
| `JTextField`                       | Entrada de texto                      |
| `JButton`                          | Acciones (Guardar, Buscar, Eliminar…) |
| `JCheckBox`                        | Convenio firmado (SI/NO)              |
| `JSpinner`                         | Número de empleados                   |
| `JMenuBar` / `JMenu` / `JMenuItem` | Navegación entre paneles              |
| `JScrollPane`                      | Área con scroll para paneles y tablas |
| `JOptionPane`                      | Mensajes y confirmaciones             |


### Layouts


| Layout                 | Dónde se usa      | Idea                                         |
| ---------------------- | ----------------- | -------------------------------------------- |
| `BorderLayout`         | Ventana principal | Divide en NORTH, SOUTH, EAST, WEST, CENTER   |
| `null` + `setBounds()` | Paneles internos  | Posicionamiento absoluto (x, y, ancho, alto) |


**Regla importante:** en `BorderLayout`, llamar a `setLayout()` **antes** de `add()`. Si no, el componente central puede quedar con tamaño 0.

### Cambio de paneles

En lugar de destruir ventanas, se **intercambia** el panel dentro del `JScrollPane`:

```java
scrpContenedor.setViewportView(panel);
scrpContenedor.revalidate();
scrpContenedor.repaint();
```

- `revalidate()`: recalcula el layout.
- `repaint()`: fuerza el redibujado.

### Diálogos con JOptionPane


| Método              | Uso                                |
| ------------------- | ---------------------------------- |
| `showMessageDialog` | Informar (éxito, error, sin datos) |
| `showConfirmDialog` | Preguntar antes de eliminar        |


---

## 7. Tablas en Swing (JTable)

La tabla es el concepto más delicado del ejercicio. Involucra **tres objetos**:

```
JScrollPane  →  contiene  →  JTable  →  usa  →  DefaultTableModel
```


| Objeto              | Función                             |
| ------------------- | ----------------------------------- |
| `JTable`            | Componente visual (cuadrícula)      |
| `DefaultTableModel` | Datos en memoria (filas y columnas) |
| `JScrollPane`       | Scroll si hay muchas filas          |


### Configuración (una sola vez)

1. Crear `DefaultTableModel` (opcionalmente sobrescribir `isCellEditable` → `false`).
2. Asignarlo a la tabla: `tblEmpresas.setModel(dtmEmpresas)`.
3. Añadir columnas con `addColumn(...)`.
4. Meter la tabla en un `JScrollPane`.

### Carga de datos (cada búsqueda)

1. `setRowCount(0)` → vacía filas sin borrar columnas.
2. Para cada `Empresa`, `addRow(new Object[]{ ... })`.
3. El **orden** del array debe coincidir con el orden de `addColumn()`.

### Lectura de fila seleccionada

```java
int fila = tabla.getSelectedRow();  // -1 si no hay selección
Object valor = modelo.getValueAt(fila, 0);  // columna 0 = CIF
```

Siempre comprobar `fila == -1` antes de leer datos.

### Error típico

```java
// MAL: borra columnas también
dtm.getDataVector().clear();

// BIEN: solo borra filas
dtm.setRowCount(0);
```

---

## 8. Modelo de datos y validación

### POJO / entidad de dominio

`Empresa` representa **una fila** de la tabla `EMPRESAS`:

- Un atributo por columna.
- Constructor con todos los campos.
- Getters para lectura.
- Métodos de utilidad (`traducirConvenio()`).

### Validación en la vista

Patrón del examen en `obtenerDatos()`:

1. Leer campos (`getText().trim()`).
2. Comprobar obligatorios.
3. Validar formatos con métodos estáticos de `Empresa`.
4. Si hay error → mostrar mensaje y `return null`.
5. Si todo OK → `return new Empresa(...)`.

El controlador interpreta `null` como "datos inválidos; no llamar al DAO".

### Validaciones del enunciado


| Campo    | Regla                                            |
| -------- | ------------------------------------------------ |
| CIF      | 9 caracteres: letra + 7 dígitos + letra o dígito |
| Correo   | Un `@`, sin espacios, dominio con punto          |
| Teléfono | 9 dígitos, empieza por 6, 7, 8 o 9 (opcional)    |
| Web      | Empieza por `www.`, dominio con punto (opcional) |
| Convenio | `"SI"` o `"NO"` (desde checkbox)                 |


### Transformación vista ↔ BD


| En pantalla          | En base de datos                  |
| -------------------- | --------------------------------- |
| Checkbox marcado     | `"SI"`                            |
| Checkbox desmarcado  | `"NO"`                            |
| `"SI"` en BD         | Checkbox marcado al cargar        |
| `"Firmado"` en tabla | `traducirConvenio()` sobre `"SI"` |


---

## 9. SQL y diseño relacional

### DDL (definición de estructura)

El archivo `EMPRESAS.ddl` crea la tabla con restricciones:


| Restricción                       | Efecto                                          |
| --------------------------------- | ----------------------------------------------- |
| `PRIMARY KEY (CIF)`               | Identificador único; no se modifica en UPDATE   |
| `NOT NULL`                        | Campo obligatorio                               |
| `UNIQUE (RAZON_SOCIAL)`           | No puede haber dos empresas con el mismo nombre |
| `CHECK (CONVENIO IN ('SI','NO'))` | Solo valores permitidos                         |


Si un `INSERT` viola alguna restricción, JDBC devuelve 0 filas afectadas sin necesariamente lanzar excepción visible al usuario.

### DML (manipulación de datos)


| Operación  | Sentencia                            |
| ---------- | ------------------------------------ |
| Insertar   | `INSERT INTO ... VALUES (?, ?, ...)` |
| Consultar  | `SELECT * FROM ... WHERE ...`        |
| Actualizar | `UPDATE ... SET ... WHERE CIF = ?`   |
| Eliminar   | `DELETE FROM ... WHERE CIF = ?`      |


### Consultas dinámicas

En `buscarEmpresas`, el `WHERE` se construye según los filtros:

- Sin filtros → `SELECT * FROM EMPRESAS` (todos los registros).
- Con CIF → `CIF = ?`
- Con razón social → `RAZON_SOCIAL LIKE ?` (con `%` al final = "empieza por")
- Varios filtros → unidos con `OR` en este ejercicio.

### Clave primaria en UPDATE y DELETE

El CIF **no aparece en el SET** del `UPDATE`; solo en el `WHERE`. Es la clave que identifica qué fila modificar o borrar.

---

## 10. JDBC — acceso a datos desde Java

**JDBC** (Java Database Connectivity) es la API estándar para conectar Java con bases de datos.

### Clases principales


| Clase               | Función                          |
| ------------------- | -------------------------------- |
| `DriverManager`     | Obtiene conexiones               |
| `Connection`        | Sesión con la BD                 |
| `PreparedStatement` | Sentencia SQL con parámetros `?` |
| `ResultSet`         | Resultado de un `SELECT`         |


### Flujo de conexión

1. Leer `driver` y `url` desde `ConfiguracionDB.properties`.
2. `Class.forName(driver)` → carga el driver SQLite.
3. `DriverManager.getConnection(url)` → abre la conexión.

### PreparedStatement y parámetros

```java
stmt.setString(1, valor);  // primer ?
stmt.setInt(2, numero);    // segundo ?
```

Los índices empiezan en **1**, no en 0. El orden de los `setXxx` debe coincidir con el orden de los `?` en el SQL.

### executeUpdate vs executeQuery


| Método            | Para qué               | Retorna                 |
| ----------------- | ---------------------- | ----------------------- |
| `executeUpdate()` | INSERT, UPDATE, DELETE | `int` (filas afectadas) |
| `executeQuery()`  | SELECT                 | `ResultSet`             |


### ResultSet

- `rs.next()` avanza a la siguiente fila; devuelve `false` cuando no hay más.
- `while (rs.next())` → varias filas (búsqueda).
- `if (rs.next())` → una fila como máximo (buscar por CIF).
- `rs.getString("COLUMNA")`, `rs.getInt("COLUMNA")` → leer valores.

### Mapeo ResultSet → objeto

Método privado `mapearEmpresa(ResultSet rs)` convierte una fila SQL en un objeto `Empresa`. Evita duplicar código en cada `SELECT`.

### Cierre de recursos

Siempre en `finally`:

```java
cerrar(rs, stmt, con);
```

Orden recomendado de cierre: ResultSet → Statement → Connection.

### SQLite en este proyecto

- Base de datos en archivo (`BBDD/BBDD.db`).
- No requiere servidor; ideal para exámenes y proyectos locales.
- La ruta en `jdbc:sqlite:...` es relativa al **directorio de trabajo** al ejecutar el programa.

---

## 11. Interfaces como contrato

### ¿Qué es una interfaz?

Un contrato que define **qué métodos debe implementar** una clase, sin decir cómo.

### En este proyecto


| Interfaz         | Implementada por               | Obliga a tener                                                   |
| ---------------- | ------------------------------ | ---------------------------------------------------------------- |
| `IVFrame`        | `VPEmpresas`                   | `configurarVentana()`, `setControlador()`, `cargarPanel()`, etc. |
| `IPaneles`       | Paneles de formulario/consulta | `crearComponentes()`, `setControlador()`                         |
| `ActionListener` | `ControladorEmpresas`          | `actionPerformed(ActionEvent)`                                   |


### ¿Para qué sirven en el examen?

- Estandarizan la estructura de las vistas.
- El corrector sabe qué métodos esperar.
- Refuerzan el polimorfismo: el controlador trabaja con contratos, no con detalles internos.

---

## 12. Ensamblaje de la aplicación (main)

`InicioEmpresas` es el **composition root**: el único lugar donde se crean y conectan todos los objetos.

### Cuatro fases

```
FASE 1: Crear vistas (JFrame + JPanel)
FASE 2: Crear controlador y pasarle referencias a las vistas
FASE 3: Registrar controlador como listener en cada vista
FASE 4: Mostrar ventana (hacerVisible)
```

### ¿Por qué setters y no todo en el constructor?

El controlador y los paneles se crean por separado. Si el constructor del controlador exigiera todos los paneles a la vez, podría haber **dependencia circular** (cada uno necesita al otro). Por eso:

- `vp` entra por constructor.
- `pre`, `pce`, `pme` entran por setters (`setPre`, `setPce`, `setPme`).

### NullPointerException típico

Si en el `main` falta `ce.setPce(pce)`, al pulsar "Buscar" el controlador tendrá `pce == null` y fallará.

---

## 13. Flujos de datos del CRUD

### Registrar (Create)

```
Formulario → obtenerDatos() → validación
    → insertEmpresa() → mensaje → limpiar formulario
```

### Buscar (Read)

```
Filtros → buscarEmpresas() → ArrayList
    → si vacía: mensaje y ocultar tabla
    → si hay datos: cargarTabla() y mostrar tabla
```

### Modificar (Update)

```
Seleccionar fila → leer CIF de columna 0
    → obtenerEmpresaPorCif() → cargarDatos() en panel modificar
    → usuario edita → obtenerDatos() → updateEmpresa()
    → refrescar búsqueda → volver a panel consulta
```

### Eliminar (Delete)

```
Seleccionar fila → confirmar
    → leer CIF → eliminarEmpresa() → refrescar búsqueda
```

En modificar y eliminar, el CIF se obtiene de la **tabla** (columna 0), no del formulario de filtros.

---

## 14. Reglas de oro del examen

### Regla 1 — Flujo de capas

```
Vista → Controlador → DAO → Controlador → Vista
```

### Regla 2 — Botón funcional = 5 piezas

Atributo + crear + listener + getter + `else if` en controlador.

### Regla 3 — Responsabilidades claras


| Capa        | Hace                  | No hace                      |
| ----------- | --------------------- | ---------------------------- |
| Vista       | UI, validar, mensajes | SQL, llamar DAO              |
| Controlador | Coordinar             | SQL, crear componentes       |
| DAO         | SQL, mapear ResultSet | JOptionPane, leer JTextField |


### Regla 4 — Preguntas al resolver un TODO

1. ¿A qué capa pertenece esta tarea?
2. ¿Quién necesita el resultado?
3. ¿Existe el getter para que el controlador lea ese dato?

---

## 15. Errores conceptuales frecuentes


| Error                           | Causa                                               | Solución conceptual                        |
| ------------------------------- | --------------------------------------------------- | ------------------------------------------ |
| Botón no responde               | Falta listener o `else if`                          | Completar las 5 piezas del botón           |
| NullPointerException            | Falta setter en main o getter en vista              | Revisar ensamblaje                         |
| Tabla vacía con datos en BD     | Orden de columnas ≠ orden de `addRow`               | Alinear modelo y carga                     |
| Tabla no se ve                  | `setVisible(false)` en scroll                       | Llamar `setVisibleTabla(true)` tras cargar |
| INSERT devuelve 0               | CIF o razón social duplicados, convenio inválido    | Revisar restricciones DDL                  |
| Panel en blanco al cambiar menú | `add()` antes de `setLayout()` o sin `revalidate()` | Orden correcto de layout                   |
| `no such table`                 | BD no creada o nombre distinto al DDL               | Ejecutar DDL y revisar `EmpresaContracts`  |
| `getSelectedRow()` = -1         | No hay fila seleccionada                            | Validar antes de modificar/eliminar        |


---

## 16. Extensiones habituales en otros exámenes

Este proyecto no las usa, pero conviene conocerlas:


| Elemento                       | Impacto en DAO                                               |
| ------------------------------ | ------------------------------------------------------------ |
| `JComboBox` como filtro        | Sí: nuevo parámetro en `buscar` y condición `WHERE`          |
| `JRadioButton` + `ButtonGroup` | Sí: traducir opción a condición SQL (ej. rango de empleados) |
| `ListSelectionListener`        | No: solo habilita botones al seleccionar fila                |
| `CardLayout`                   | No: solo cambia cómo se muestran paneles                     |


**Regla práctica:** si el componente introduce un **dato nuevo** para filtrar, insertar o actualizar en BD → toca el DAO. Si solo cambia la interacción o la pantalla → el DAO no cambia.

---

## Resumen final

Para dominar este tipo de ejercicio necesitas integrar:

1. **OOP en Java** — clases, encapsulación, colecciones, excepciones.
2. **MVC** — separación estricta de vista, controlador y modelo.
3. **DAO** — SQL encapsulado, mapeo objeto-relacional manual.
4. **JDBC** — conexión, PreparedStatement, ResultSet, cierre de recursos.
5. **Swing** — componentes, layouts, tablas, diálogos.
6. **Eventos** — ActionListener y coordinación en el controlador.

La clave no es memorizar archivos sueltos, sino entender **quién hace qué** y **en qué orden fluyen los datos** cuando el usuario pulsa cada botón.

---

## Referencias del proyecto


| Archivo                     | Contenido teórico relacionado          |
| --------------------------- | -------------------------------------- |
| `InicioEmpresas.java`       | Ensamblaje e inyección de dependencias |
| `ControladorEmpresas.java`  | MVC, ActionListener, coordinación      |
| `EmpresasDAO.java`          | DAO, JDBC, SQL dinámico                |
| `Empresa.java`              | POJO, validación estática              |
| `EmpresaContracts.java`     | Contratos de persistencia              |
| `AccesoDB.java`             | Conexión JDBC y Properties             |
| `VPEmpresas.java` + paneles | Swing, layouts, tabla                  |
| `DB/EMPRESAS.ddl`           | Modelo relacional y restricciones      |


