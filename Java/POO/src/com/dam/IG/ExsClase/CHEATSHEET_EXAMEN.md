# Cheatsheet MVC + Swing — Examen

---

## 1. El patrón MVC de un vistazo

```
USUARIO
  │  (clic, selección...)
  ▼
VIEW  ──────────────────────► CONTROLLER  ──── lee/escribe ───► MODEL
(JFrame, JPanel)   evento    (ActionListener)                  (datos puros)
      ▲                            │
      └──────── actualiza vista ───┘
```

| Capa | Clase en el proyecto | Regla clave |
|------|----------------------|-------------|
| Model | `Encuesta`, `DatosEncuestas` | No importa nada de `javax.swing` |
| View | `VPrincipalEncuestas`, `PRealizarEncuestas`, `PVisualizarEncuestas` | No tiene lógica de negocio |
| Control | `ControladorEncuestas` | Es el único que conoce a los demás |

---

## 2. Orden de construcción en Inicio.java (CRÍTICO)

```java
EventQueue.invokeLater(() -> {
    // 1. Vistas primero (el controlador las necesita como args)
    VPrincipalEncuestas vp  = new VPrincipalEncuestas();   // lee insets → DEBE ser el primero
    PRealizarEncuestas  pre = new PRealizarEncuestas();    // usa insets de vp
    PVisualizarEncuestas pve = new PVisualizarEncuestas(); // usa insets de vp

    // 2. Modelo
    DatosEncuestas datos = new DatosEncuestas();

    // 3. Controlador (necesita las vistas ya creadas)
    ControladorEncuestas control = new ControladorEncuestas(vp, pre, pve, datos);

    // 4. Inyectar controlador en las vistas (ahora ya existe)
    vp.setControlador(control);
    pre.setControlador(control);
    pve.setControlador(control);

    // 5. Mostrar al final (todo ya configurado)
    vp.hacerVisible();
});
```

**Por qué `invokeLater`**: Swing no es thread-safe. Todo lo que toque la UI debe
correr en el Event Dispatch Thread (EDT). `invokeLater` encola el código en ese hilo.

---

## 3. Componentes Swing esenciales

### JFrame — ventana principal
```java
public class MiVentana extends JFrame {
    public MiVentana() {
        setTitle("Título");
        setSize(600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
```

### JPanel — contenedor de componentes
```java
public class MiPanel extends JPanel {
    public MiPanel() {
        setLayout(null);                    // posicionamiento absoluto
        JLabel lbl = new JLabel("Hola");
        lbl.setBounds(10, 10, 100, 20);    // (x, y, ancho, alto)
        add(lbl);
    }
}
```

### JRadioButton + ButtonGroup (exclusión mutua)
```java
ButtonGroup grupo = new ButtonGroup();

JRadioButton opcion1 = new JRadioButton("Opción 1");
JRadioButton opcion2 = new JRadioButton("Opción 2");

grupo.add(opcion1);   // ← imprescindible para la exclusión
grupo.add(opcion2);
opcion1.setSelected(true);   // seleccionado por defecto

add(opcion1);
add(opcion2);
```
**Leer cuál está marcado:**
```java
for (Component c : getComponents()) {
    if (c instanceof JRadioButton && ((JRadioButton) c).isSelected()) {
        String valor = ((JRadioButton) c).getText();
    }
}
```

### JCheckBox (selección múltiple)
```java
JCheckBox chk = new JCheckBox("Opción A");
add(chk);

// Leer estado:
if (chk.isSelected()) { /* marcado */ }

// Desmarcar:
chk.setSelected(false);
```

### JComboBox — lista desplegable
```java
JComboBox<String> combo = new JComboBox<>();
DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>(miArray);
combo.setModel(modelo);

// Leer selección:
String elegido = (String) combo.getSelectedItem();

// Resetear al primero:
combo.setSelectedIndex(0);
```

### JList + DefaultListModel
```java
JList<Object> lista = new JList<>();
DefaultListModel<Object> modelo = new DefaultListModel<>();
lista.setModel(modelo);

// Añadir elemento (llama a toString() al renderizar):
modelo.addElement(miObjeto);

// Limpiar:
modelo.clear();
```

### JScrollPane — scroll + contenedor intercambiable
```java
JScrollPane scroll = new JScrollPane();
getContentPane().add(scroll, BorderLayout.CENTER);

// Cambiar el contenido (navegación entre "pantallas"):
scroll.setViewportView(otroPanel);
```

### JMenuBar / JMenu / JMenuItem
```java
JMenuBar barra = new JMenuBar();
setJMenuBar(barra);

JMenu menu = new JMenu("Archivo");
barra.add(menu);

JMenuItem item = new JMenuItem("Abrir");
menu.add(item);

item.addActionListener(controlador);   // registrar listener
```

### JOptionPane — diálogo emergente
```java
// Mensaje informativo:
JOptionPane.showMessageDialog(this, "Texto", "Título", JOptionPane.INFORMATION_MESSAGE);

// Tipos: INFORMATION_MESSAGE, WARNING_MESSAGE, ERROR_MESSAGE, QUESTION_MESSAGE
```

---

## 4. ActionListener — cómo maneja eventos el controlador

```java
public class MiControlador implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        Object fuente = e.getSource();         // ¿quién disparó el evento?
        String comando = e.getActionCommand(); // texto/comando del componente

        if (fuente instanceof JMenuItem) {
            if (fuente.equals(ventana.getItemRealizar())) {
                // navegar al panel de realizar
            }

        } else if (fuente instanceof JButton) {
            if (comando.equals(PanelA.AC_GUARDAR)) {
                // guardar
            } else if (comando.equals(PanelB.AC_VER)) {
                // mostrar resultados
            }
        }
    }
}
```

**Registrar el controlador en un componente:**
```java
miBoton.addActionListener(controlador);    // en setControlador() del panel
miItem.addActionListener(controlador);     // en setControlador() de la ventana
```

**Por qué usar ActionCommand en botones y getSource en menús:**
- Menús: comparamos `fuente.equals(ventana.getItemX())` → necesitamos getter en la ventana.
- Botones: comparamos `comando.equals(Panel.CONSTANTE)` → el controlador no necesita
  una referencia directa al botón. Más desacoplado.

---

## 5. Interfaces como contratos (inversión de dependencias)

```java
// La interfaz define QUÉ se puede hacer, no CÓMO.
public interface IPaneles {
    void crearComponentes();
    void setControlador(ControladorEncuestas control);
}

// La implementación define el CÓMO.
public class PRealizarEncuestas extends JPanel implements IPaneles {
    @Override
    public void crearComponentes() { /* ... */ }

    @Override
    public void setControlador(ControladorEncuestas control) {
        btnGuardar.addActionListener(control);
    }
}
```

**Ventaja**: si mañana cambias Swing por JavaFX, solo cambias las implementaciones.
El controlador sigue usando `IPaneles` sin tocar una línea.

---

## 6. HashMap como contador — patrón estándar

```java
// 1. Inicializar con todas las claves a 0
HashMap<String, Integer> contador = new HashMap<>();
for (String clave : arrayDeClaves) {
    contador.put(clave, 0);
}

// 2. Acumular
contador.put(clave, contador.get(clave) + 1);

// 3. Buscar el máximo
String ganador = "";
int mayor = -1;   // -1 garantiza que el primer valor siempre gana
for (Map.Entry<String, Integer> entry : contador.entrySet()) {
    if (entry.getValue() > mayor) {
        ganador = entry.getKey();
        mayor   = entry.getValue();
    }
}
```

---

## 7. Layout null — posicionamiento absoluto

```java
setLayout(null);
componente.setBounds(x, y, ancho, alto);
add(componente);
```

- **x, y**: coordenada superior-izquierda del componente dentro del panel.
- Pros: sencillo de entender y predecible.
- Contras: no se adapta si la ventana cambia de tamaño (por eso se fija el tamaño).

**Calcular el tamaño útil del panel:**
```java
// En VPrincipalEncuestas (después de setSize):
insetsR = getInsets().right;
insetsL = getInsets().left;
insetsT = getInsets().top;
insetsB = getInsets().bottom;
menuH   = menuBar.getPreferredSize().height;

// En los paneles:
int ANCHO = VPrincipalEncuestas.ANCHO - insetsL - insetsR;
int ALTO  = VPrincipalEncuestas.ALTO  - insetsT - insetsB - menuH;
```

---

## 8. Flujo completo "Añadir Encuesta"

```
Usuario pulsa "Añadir Encuesta"
    │
    ▼
Swing → ControladorEncuestas.actionPerformed(e)
    │
    ├─ e.getActionCommand() == "Añadir Encuesta"
    │
    ├─ Encuesta enc = pre.obtenerDatosEncuesta()
    │      ├─ Itera getComponents() → JRadioButton seleccionado → rangoEdad
    │      ├─ cmbFrecuencia.getSelectedItem() → frecuencia
    │      └─ Itera getComponents() → JCheckBox marcados → listaSeriesVistas
    │
    ├─ datos.addEncuesta(enc)            ← guarda en el modelo
    ├─ pre.mostrarMensaje("Guardado")    ← feedback
    └─ pre.limpiarEncuesta()             ← resetea formulario
```

---

## 9. Patrones reutilizables — plantilla de nuevo panel

```java
public class PNuevoPanel extends JPanel implements IPaneles {

    // 1. Constantes de acción
    public static final String AC_BTN_ACCION = "Texto del botón";

    // 2. Campos privados de los componentes
    private JButton btnAccion;
    private JTextField txtCampo;

    public PNuevoPanel() {
        setSize(ANCHO, ALTO);
        crearComponentes();
    }

    @Override
    public void crearComponentes() {
        setLayout(null);

        txtCampo = new JTextField();
        txtCampo.setBounds(50, 50, 200, 25);
        add(txtCampo);

        btnAccion = new JButton(AC_BTN_ACCION);
        btnAccion.setBounds(50, 100, 150, 25);
        add(btnAccion);
    }

    @Override
    public void setControlador(ControladorEncuestas control) {
        btnAccion.addActionListener(control);   // registrar solo los botones que disparan lógica
    }

    // Métodos de lectura/escritura que el controlador llamará:
    public String leerCampo() {
        return txtCampo.getText();
    }

    public void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}
```

---

## 10. Preguntas trampa frecuentes

| Pregunta | Respuesta |
|----------|-----------|
| ¿Puede la vista acceder al modelo directamente? | **No.** Todo pasa por el controlador. |
| ¿Puede el modelo importar clases de Swing? | **No.** El modelo no sabe que existe una UI. |
| ¿Para qué sirve `ButtonGroup`? | Para que solo UN JRadioButton del grupo esté marcado a la vez. |
| ¿Diferencia entre `JList` y `DefaultListModel`? | `JList` es la vista (lo que se pinta); `DefaultListModel` es el modelo (los datos). |
| ¿Por qué `invokeLater`? | Swing no es thread-safe; hay que ejecutar la UI en el EDT. |
| ¿Para qué usar `ActionCommand` en vez de comparar el objeto? | Para no necesitar una referencia directa al botón en el controlador → menos acoplamiento. |
| ¿Cuándo se llama a `toString()` de un objeto en un `JList`? | Automáticamente al renderizar cada celda. |
| ¿Qué hace `scrpContenedor.setViewportView(panel)`? | Reemplaza el contenido del JScrollPane con otro panel → navegación entre pantallas. |
