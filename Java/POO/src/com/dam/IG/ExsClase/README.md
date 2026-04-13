# PruebaParcialT3_2425 — Aplicación de Encuestas con MVC + Swing

Este documento explica el proyecto desde cero, asumiendo que no tienes experiencia previa con el patrón MVC ni con Java Swing.

---

## Tabla de contenidos

1. [¿Qué hace la aplicación?](#1-qué-hace-la-aplicación)
2. [¿Qué es Java Swing?](#2-qué-es-java-swing)
3. [¿Qué es el patrón MVC?](#3-qué-es-el-patrón-mvc)
4. [Estructura del proyecto](#4-estructura-del-proyecto)
5. [Capa Model — los datos](#5-capa-model--los-datos)
6. [Capa View — la interfaz gráfica](#6-capa-view--la-interfaz-gráfica)
7. [Capa Control — el cerebro](#7-capa-control--el-cerebro)
8. [Punto de entrada — `Inicio.java`](#8-punto-de-entrada--iniciojava)
9. [Flujo completo de una acción](#9-flujo-completo-de-una-acción)
10. [Diagrama de dependencias](#10-diagrama-de-dependencias)
11. [Conceptos Swing usados en el proyecto](#11-conceptos-swing-usados-en-el-proyecto)

---

## 1. ¿Qué hace la aplicación?

Es una pequeña encuesta sobre hábitos de consumo de series de televisión. El usuario puede:

- **Realizar una encuesta**: elegir su rango de edad, con qué frecuencia ve series y qué series ha visto.
- **Visualizar las encuestas guardadas**: ver un listado de todas las encuestas introducidas y obtener un análisis estadístico (rango de edad más frecuente, frecuencia de visionado más común, serie más vista).

---

## 2. ¿Qué es Java Swing?

Swing es la biblioteca estándar de Java para crear interfaces gráficas de escritorio (ventanas, botones, listas, etc.). Sus clases principales empiezan por `J`:

| Clase Swing       | Qué es                                              |
|-------------------|-----------------------------------------------------|
| `JFrame`          | Una ventana principal de escritorio                 |
| `JPanel`          | Un contenedor (como una "hoja" dentro de la ventana)|
| `JButton`         | Un botón clickable                                  |
| `JLabel`          | Un texto no editable                                |
| `JRadioButton`    | Botón de opción (solo uno del grupo puede marcarse) |
| `JCheckBox`       | Casilla de verificación (pueden marcarse varios)    |
| `JComboBox`       | Lista desplegable                                   |
| `JList`           | Lista de elementos desplazable                      |
| `JScrollPane`     | Contenedor con barras de desplazamiento             |
| `JMenuBar`        | Barra de menú de la ventana                         |
| `JMenu`           | Menú dentro de la barra (ej. "Archivo")             |
| `JMenuItem`       | Opción dentro de un menú                            |
| `JOptionPane`     | Ventana emergente de mensaje o diálogo              |

**`EventQueue.invokeLater`**: Swing no es thread-safe. Todo lo que toca la interfaz gráfica debe ejecutarse en el *Event Dispatch Thread* (EDT). `invokeLater` encola el código para que se ejecute en ese hilo especial.

---

## 3. ¿Qué es el patrón MVC?

MVC es una forma de organizar el código dividiéndolo en tres responsabilidades:

```
+----------+        eventos        +-----------+
|          | --------------------> |           |
|   VIEW   |                       | CONTROLLER|
|  (Vista) | <-------------------- |           |
+----------+   actualiza vista     +-----------+
                                        |
                                        | lee/escribe
                                        v
                                   +---------+
                                   |  MODEL  |
                                   | (Datos) |
                                   +---------+
```

| Capa       | Responsabilidad                                          | Regla clave                              |
|------------|----------------------------------------------------------|------------------------------------------|
| **Model**  | Almacena y gestiona los datos puros                      | No sabe que existe una interfaz gráfica  |
| **View**   | Muestra los datos y captura la interacción del usuario   | No contiene lógica de negocio            |
| **Control**| Reacciona a eventos de la vista, usa el modelo, actualiza la vista | Es el único que conoce a todos   |

**Por qué MVC**: si mañana cambias de Swing a una interfaz web, solo cambias la capa View. El Model y el Controller se reutilizan intactos. El código es más fácil de probar y mantener.

---

## 4. Estructura del proyecto

```
src/
├── Inicio.java                          ← Punto de entrada (main)
└── com/dam/a/
    ├── model/
    │   ├── Encuesta.java                ← Una encuesta individual (datos + constantes)
    │   └── DatosEncuestas.java          ← Repositorio en memoria de todas las encuestas
    ├── view/
    │   ├── IVPrincipalEncuestas.java    ← Interfaz (contrato) de la ventana principal
    │   ├── IPaneles.java                ← Interfaz (contrato) de los paneles
    │   ├── VPrincipalEncuestas.java     ← Ventana principal (JFrame)
    │   ├── PRealizarEncuestas.java      ← Panel para hacer una encuesta
    │   └── PVisualizarEncuestas.java    ← Panel para ver y analizar encuestas
    └── control/
        └── ControladorEncuestas.java    ← Controlador central (ActionListener)
```

---

## 5. Capa Model — los datos

### `Encuesta.java`

Representa **una encuesta individual**. Es un objeto de datos puro (un POJO).

```
Encuesta
├── rangoEdad      : String   ← ej. "Entre 18 y 30"
├── frecuencia     : String   ← ej. "3 o 4 veces por semana"
└── listaSeriesVistas : ArrayList<String>  ← ej. ["Breaking Bad", "Dragon Ball"]
```

También define **constantes estáticas** que usa toda la aplicación para evitar cadenas duplicadas:

```java
public static final String[] RANGOS_EDAD  = { "Entre 5 y 17", "Entre 18 y 30", ... };
public static final String[] FRECUENCIAS  = { "Ninguna", "1 o 2 veces por semana", ... };
public static final String[] SERIES       = { "Juego de Tronos", "Vikingos", ... };
```

El método `traducirFrecuencia()` convierte la frecuencia a formato corto para el `toString()`:
- `"3 o 4 veces por semana"` → `"3 o 4 v/s"`
- `"Todos los días"` → `"Todos"`
- `"Ninguna"` → `"0 v/s"`

### `DatosEncuestas.java`

Es el **repositorio en memoria** de todas las encuestas recogidas.

```
DatosEncuestas
└── listaEncuestas : ArrayList<Encuesta>
```

Métodos clave:

- `addEncuesta(Encuesta)` — guarda una nueva encuesta en la lista.
- `getListaEncuestas()` — devuelve toda la lista para mostrarla en la vista.
- `realizarAnalisis()` — recorre la lista y calcula tres estadísticos:
  1. **Conteo por rango de edad** — cuántos encuestados hay en cada franja.
  2. **Frecuencia más usada** — la frecuencia de visionado más votada.
  3. **Serie más vista** — la serie que más encuestados han marcado.

  Internamente usa tres `HashMap<String, Integer>` como contadores. Recorre todas las encuestas y acumula. Luego busca el máximo con un bucle. Devuelve un `String` formateado listo para mostrar.

---

## 6. Capa View — la interfaz gráfica

### Interfaces `IVPrincipalEncuestas` y `IPaneles`

Son **contratos Java** (interfaces). Definen los métodos que toda vista debe implementar, sin importar cómo estén construidos internamente. Esto permite que el controlador trabaje contra la interfaz en lugar de la implementación concreta (principio de inversión de dependencias).

```java
// IVPrincipalEncuestas
void configurarVentana();
void crearComponentes();
void crearMenu();
void setControlador(ControladorEncuestas ce);
void hacerVisible();
void cargarPanel(JPanel panel);   // ← crucial: intercambia paneles en la ventana

// IPaneles
void crearComponentes();
void setControlador(ControladorEncuestas control);
```

### `VPrincipalEncuestas.java` — La ventana principal

Extiende `JFrame` (es decir, ES una ventana). Es el **contenedor raíz** de toda la aplicación.

**Responsabilidades:**
1. Configurar la ventana (título, tamaño 600×400, centrarla en pantalla).
2. Crear un `JMenuBar` con el menú "Encuestas A" y dos `JMenuItem`:
   - "Realizar Encuesta"
   - "Visualizar Encuestas"
3. Crear un `JScrollPane` central que actúa como **área de contenido intercambiable**.
4. Método `cargarPanel(JPanel panel)` — reemplaza el contenido del `JScrollPane` con el panel recibido. Así la misma ventana muestra distintas pantallas sin abrir ventanas nuevas.

**Variables estáticas públicas** (`insetsL`, `insetsR`, `insetsT`, `insetsB`, `menuH`): guardan los márgenes de la ventana y la altura del menú para que los paneles puedan calcular su propio tamaño exacto.

### `PRealizarEncuestas.java` — Panel de encuesta

Extiende `JPanel`. Muestra el formulario de encuesta.

**Componentes:**
- 5 `JRadioButton` agrupados en un `ButtonGroup` para el rango de edad (solo uno seleccionable a la vez).
- 1 `JComboBox<String>` con las frecuencias (lista desplegable).
- 8 `JCheckBox` para las series (pueden marcarse varias a la vez).
- 1 `JButton` "Añadir Encuesta".

**Layout `null`**: los componentes se posicionan con coordenadas absolutas mediante `setBounds(x, y, ancho, alto)`. Es el método más sencillo pero menos flexible (no se adapta al redimensionado de la ventana).

**Métodos importantes:**

- `obtenerDatosEncuesta()` — lee el estado actual del formulario:
  - Recorre todos los componentes del panel buscando el `JRadioButton` seleccionado → rango de edad.
  - Lee el item seleccionado del `JComboBox` → frecuencia.
  - Recorre los `JCheckBox` seleccionados → lista de series.
  - Devuelve un objeto `Encuesta` construido con esos datos.

- `limpiarEncuesta()` — resetea el formulario: selecciona el primer radio button, el primer item del combo y desmarca todos los checkboxes.

- `mostrarMensaje(String)` — muestra un `JOptionPane` informativo.

### `PVisualizarEncuestas.java` — Panel de visualización

Extiende `JPanel`. Muestra las encuestas guardadas y el análisis.

**Componentes:**
- `JList<Object>` dentro de un `JScrollPane` para mostrar el listado de encuestas.
- `DefaultListModel<Object>` — el modelo de datos del `JList`. Swing usa el patrón Model-View también internamente: el `JList` es la vista, el `DefaultListModel` es su modelo.
- 1 `JButton` "Ver Resultados".

**Métodos importantes:**

- `cargarEncuestas(ArrayList<Encuesta>)` — limpia el modelo y añade cada encuesta. El `JList` llama automáticamente a `toString()` de cada `Encuesta` para mostrarla.
- `hacerVisibleBtnVer(boolean)` — oculta/muestra el botón. Si no hay encuestas, el botón se oculta.
- `mostrarMensaje(String)` — muestra un `JOptionPane` con el texto del análisis.

---

## 7. Capa Control — el cerebro

### `ControladorEncuestas.java`

Implementa `ActionListener`, la interfaz de Java para escuchar eventos de clic.

**Tiene referencias a todos:**
```java
private VPrincipalEncuestas vpe;   // la ventana
private PRealizarEncuestas pre;    // panel de realizar
private PVisualizarEncuestas pve;  // panel de visualizar
private DatosEncuestas datos;      // el modelo
```

**El método `actionPerformed(ActionEvent e)`** es llamado automáticamente por Swing cada vez que el usuario hace clic en un componente al que el controlador está registrado como listener.

Dentro, identifica **qué** se pulsó con `e.getSource()` y `e.getActionCommand()`:

```
¿Es un JMenuItem?
  ├── "Realizar Encuesta"   → vpe.cargarPanel(pre)
  └── "Visualizar Encuesta" → comprobar si hay datos
                               ├── sin datos → mensaje + ocultar botón
                               └── con datos → cargar lista + vpe.cargarPanel(pve)

¿Es un JButton?
  ├── AC_BTN_ADD ("Añadir Encuesta")
  │     → pre.obtenerDatosEncuesta()      ← lee el formulario
  │     → datos.addEncuesta(enc)          ← guarda en el modelo
  │     → pre.mostrarMensaje(...)         ← feedback al usuario
  │     → pre.limpiarEncuesta()           ← resetea el formulario
  │
  └── AC_BTN_RESULTADOS ("Ver Resultados")
        → datos.realizarAnalisis()        ← calcula estadísticos
        → pve.mostrarMensaje(analisis)    ← muestra resultado
```

**¿Por qué usar `ActionCommand` para los botones?** Porque el controlador no necesita tener una referencia directa al objeto `JButton`. Compara el texto de la acción (una constante definida en el panel), lo que desacopla el controlador de la implementación concreta del panel.

---

## 8. Punto de entrada — `Inicio.java`

```java
EventQueue.invokeLater(() -> {
    // 1. Crear vistas
    VPrincipalEncuestas vp = new VPrincipalEncuestas();
    PRealizarEncuestas pre = new PRealizarEncuestas();
    PVisualizarEncuestas pve = new PVisualizarEncuestas();

    // 2. Crear modelo
    DatosEncuestas datos = new DatosEncuestas();

    // 3. Crear controlador (inyectar vistas y modelo)
    ControladorEncuestas control = new ControladorEncuestas(vp, pre, pve, datos);

    // 4. Inyectar controlador en las vistas (para que puedan registrarlo en sus listeners)
    vp.setControlador(control);
    pre.setControlador(control);
    pve.setControlador(control);

    // 5. Mostrar la ventana
    vp.hacerVisible();
});
```

El orden importa:
- Las vistas se crean primero porque el controlador las necesita como argumentos.
- `setControlador` se llama después de crear el controlador porque hasta entonces no existe.
- `hacerVisible()` va al final para que la ventana aparezca ya completamente configurada.

---

## 9. Flujo completo de una acción

**Ejemplo: el usuario rellena la encuesta y pulsa "Añadir Encuesta"**

```
Usuario pulsa el JButton "Añadir Encuesta"
        │
        ▼
Swing llama a ControladorEncuestas.actionPerformed(e)
        │
        ├─ e.getActionCommand() == "Añadir Encuesta"  ✓
        │
        ├─ pre.obtenerDatosEncuesta()
        │       │
        │       └─ Lee JRadioButton seleccionado → rangoEdad
        │          Lee JComboBox seleccionado   → frecuencia
        │          Lee JCheckBoxes marcados     → listaSeriesVistas
        │          Devuelve new Encuesta(rangoEdad, frecuencia, listaSeriesVistas)
        │
        ├─ datos.addEncuesta(enc)
        │       └─ listaEncuestas.add(enc)
        │
        ├─ pre.mostrarMensaje("Se ha guardado la encuesta con éxito")
        │       └─ JOptionPane.showMessageDialog(...)
        │
        └─ pre.limpiarEncuesta()
                └─ Resetea radio buttons, combo y checkboxes
```

---

## 10. Diagrama de dependencias

```
Inicio
  │
  ├──crea──► VPrincipalEncuestas  ──implementa──► IVPrincipalEncuestas
  ├──crea──► PRealizarEncuestas   ──implementa──► IPaneles
  ├──crea──► PVisualizarEncuestas ──implementa──► IPaneles
  ├──crea──► DatosEncuestas
  │               └──contiene──► ArrayList<Encuesta>
  │
  └──crea──► ControladorEncuestas
                  ├──referencia──► VPrincipalEncuestas
                  ├──referencia──► PRealizarEncuestas
                  ├──referencia──► PVisualizarEncuestas
                  └──referencia──► DatosEncuestas

  Las vistas NO se referencian entre sí.
  Las vistas NO referencian al modelo directamente.
  El modelo NO sabe nada de las vistas ni del controlador.
```

---

## 11. Conceptos Swing usados en el proyecto

### `ButtonGroup`
Agrupa `JRadioButton` para que sean mutuamente excluyentes. Sin `ButtonGroup`, todos los radio buttons podrían estar marcados a la vez.

```java
ButtonGroup btngEdad = new ButtonGroup();
btngEdad.add(rdbtn517);
btngEdad.add(rdbtn1830);
// ... etc.
```

### `DefaultComboBoxModel`
El modelo de datos de un `JComboBox`. Se construye directamente desde un array de `String`:
```java
DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(Encuesta.FRECUENCIAS);
cmbFrecuencia.setModel(model);
```

### `DefaultListModel`
El modelo de datos de un `JList`. Permite añadir/eliminar elementos dinámicamente:
```java
lstModel.clear();
lstModel.addElement(encuesta);  // llama a encuesta.toString() al renderizar
```

### `JScrollPane` como contenedor de paneles
La ventana principal usa un `JScrollPane` no para hacer scroll sobre texto, sino como **contenedor intercambiable**:
```java
// Cambiar el panel mostrado:
scrpContenedor.setViewportView(nuevoPanel);
```

### `ActionListener` y `ActionCommand`
- `addActionListener(listener)` registra quién escucha los eventos de un componente.
- `getActionCommand()` devuelve el texto del botón (o el valor configurado con `setActionCommand()`).
- Las constantes `AC_BTN_ADD` y `AC_BTN_RESULTADOS` centralizan esas cadenas para evitar errores tipográficos.

### Layout `null`
```java
setLayout(null);
componente.setBounds(x, y, ancho, alto);
```
Posicionamiento absoluto en píxeles. Simple de entender pero los componentes no se reajustan si cambia el tamaño de la ventana. Por eso la ventana tiene tamaño fijo (600×400).