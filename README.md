# Code-DAM

Repositorio personal de ejercicios, prácticas y material de estudio del **1º DAM** (Desarrollo de Aplicaciones Multiplataforma). Aquí concentro el código que he ido escribiendo a lo largo del curso, desde los fundamentos de Java hasta aplicaciones de escritorio con **Swing**, **patrón MVC**, **DAO** y **JDBC** con **SQLite**.

---

## Qué he aprendido

### 1. Fundamentos de Java (UF4)

- Tipos de datos, operadores y entrada/salida por consola.
- Estructuras de control: `if`/`else`, `switch`, bucles `for`, `while` y `do-while`.
- Métodos, parámetros y ámbito de variables.

**Carpetas:** [`Java/UF_4/`](Java/UF_4/) · [`Java/SentenciasRep/`](Java/SentenciasRep/) · [`Java/Pruebas_UF4_1/`](Java/Pruebas_UF4_1/)

### 2. Programación orientada a objetos

- Clases, objetos, constructores, encapsulación (`private` + getters).
- Herencia, polimorfismo y composición.
- Arrays, `ArrayList`, `HashMap` y `HashSet`.
- Manejo de excepciones (`try`/`catch`/`finally`).
- Cadenas (`String`, `StringBuilder`) y comparación de objetos.

**Carpeta principal:** [`Java/POO/`](Java/POO/)

Subcarpetas destacadas:

| Carpeta | Contenido |
|---------|-----------|
| `pojo/` | Clases de dominio (Persona, Coche, CampoFutbol…) |
| `actividades/herencia/` | Herencia con empleados de empresa |
| `hashmaps/` | HashMap, ordenación y colecciones |
| `exceptions/` | Pruebas con excepciones |
| `actividades/playlist/` | Actividad con listas y objetos |
| `IG/` | Interfaces gráficas y apuntes de examen |

### 3. Aplicaciones de escritorio — MVC + DAO + JDBC

El bloque más importante para los exámenes: una aplicación Swing con arquitectura en capas.

```
Usuario → Vista (Swing) → Controlador → DAO → SQLite
                ↑              ↓
                └──── Empresa / ResultSet ──┘
```

| Capa | Responsabilidad | No debe hacer |
|------|-----------------|---------------|
| **Vista** | Componentes, validar campos, mostrar mensajes | Llamar al DAO, escribir SQL |
| **Controlador** | Escuchar eventos, coordinar vista y DAO | Crear componentes, escribir SQL |
| **Modelo (DAO)** | SQL, conexión JDBC, mapear `ResultSet` → objeto | Mostrar `JOptionPane`, leer `JTextField` |

**Tres reglas que no se rompen en examen:**

1. Cada botón activo necesita: atributo → `crearComponentes()` → `setControlador()` → getter → `else if` en el controlador.
2. La vista **nunca** llama al DAO.
3. El DAO **nunca** muestra mensajes al usuario.

### 4. Bases de datos

- SQL: `CREATE TABLE`, `INSERT`, `SELECT`, `UPDATE`, `DELETE`, filtros con `WHERE`, `LIKE`, claves primarias y restricciones.
- JDBC: `Connection`, `PreparedStatement`, `ResultSet`, cierre en `finally`.
- Configuración externa con `.properties` y driver SQLite.
- MySQL procedural: procedimientos, funciones y triggers (cheatsheet en raíz).

**Recursos:** [`mysql_procedural_cheatsheet.md`](mysql_procedural_cheatsheet.md) · [`Taller_Repaso_3T_Damaga.sql`](Taller_Repaso_3T_Damaga.sql)

---

## Proyectos principales

Estos son los ejercicios más completos, con guías propias:

| Proyecto | Descripción | Documentación |
|----------|-------------|---------------|
| **ExamenProgAEv3_2526** | CRUD de empresas (Swing + MVC + SQLite). Proyecto de referencia actual. | [README](recuperacionT3/ExamenProgAEv3_2526/README.md) · [TEORIA.md](recuperacionT3/ExamenProgAEv3_2526/TEORIA.md) |
| **Guía Michelin** | CRUD de restaurantes con `JComboBox`, filtros y tabla. | [ExamenT3/README.md](ExamenT3/README.md) |
| **PruebaParcial AE1** | Ejercicios de consola (restaurante, opciones). | [`PruebaParcialAE1DavidMartinez/`](PruebaParcialAE1DavidMartinez/) |
| **Encuestas (IG)** | Aplicación MVC + Swing (prueba parcial T3). | [`Java/POO/src/com/dam/IG/`](Java/POO/src/com/dam/IG/) |

### Estructura típica de un examen Swing

```
src/
├── InicioXxx.java              ← main: crea vistas, controlador y conecta listeners
└── com/dam/
    ├── control/                ← ActionListener, coordina todo
    ├── view/                   ← JFrame, JPanel, JTable, formularios
    └── model/
        ├── datos/              ← Entidad (POJO) + Textos.java
        └── db/                 ← AccesoDB, XxxContracts, XxxDAO

DB/ o BBDD/
├── ConfiguracionDB.properties
├── ENTIDAD.ddl
└── BBDD.db                     ← SQLite (crear con el DDL)

lib/
└── sqlite-jdbc-3.51.3.0.jar
```

### Orden recomendado para resolver un examen con TODOs

```
1. Entidad (POJO) + validaciones
2. Contracts (nombres de tabla y columnas)
3. AccesoDB (conexión JDBC)
4. DAO (insert, buscar, obtenerPorId, update, delete)
5. VPEntidad (JFrame + menú)
6. Paneles (registro, consulta con tabla, modificación)
7. Controlador (actionPerformed + métodos privados)
8. Main (SwingUtilities.invokeLater + ensamblaje)
```

---

## Estructura del repositorio

```
Code-DAM/
├── README.md                          ← este archivo
├── mysql_procedural_cheatsheet.md     ← apuntes MySQL procedural
├── Taller_Repaso_3T_Damaga.sql        ← scripts SQL de repaso
│
├── Java/                              ← ejercicios por unidad formativa
│   ├── UF_4/                          ← fundamentos (if, switch, bucles)
│   ├── SentenciasRep/                 ← repaso de sentencias
│   ├── Pruebas_UF4_1/                 ← bucles for/while
│   ├── POO/                           ← POO, colecciones, herencia, hashmaps
│   ├── ActividadUF8_2_Arquitectura/   ← arquitectura en capas
│   └── ActividadUF8_2_Empresa/        ← actividad empresa
│
├── ExamenT3/                          ← guía Michelin + plantillas T3
│   ├── README.md
│   ├── ActividadUF10_2_Guia_Michelin/
│   └── Michelin/
│
├── recuperacionT3/
│   └── ExamenProgAEv3_2526/           ← examen empresas (proyecto estrella)
│       ├── README.md                  ← guía práctica con código
│       └── TEORIA.md                  ← conceptos MVC, DAO, JDBC, Swing
│
├── PruebaParcialAE1DavidMartinez/     ← prueba parcial AE1
└── ActividadPHP_DavidMartinezGallego/ ← actividad PHP (otra asignatura)
```

---

## Cómo ejecutar un proyecto Swing + SQLite

Requisitos: **Java 21**, driver SQLite en `lib/`, base de datos creada con el DDL.

```bash
cd recuperacionT3/ExamenProgAEv3_2526

# Crear la BD (solo la primera vez)
mkdir -p BBDD
sqlite3 BBDD/BBDD.db < DB/EMPRESAS.ddl

# Compilar (--release 21 si el javac del sistema es más nuevo que el JRE del IDE)
javac --release 21 -cp "lib/sqlite-jdbc-3.51.3.0.jar" -d bin $(find src -name "*.java")

# Ejecutar (desde la raíz del proyecto, donde están DB/ y BBDD/)
java -cp "bin:lib/sqlite-jdbc-3.51.3.0.jar" InicioEmpresas
```

En **Eclipse** o **Cursor/VS Code**: clic derecho sobre la clase `main` → Run. El directorio de trabajo debe ser la raíz del proyecto.

---

## Cheatsheets y apuntes

| Archivo | Contenido |
|---------|-----------|
| [recuperacionT3/.../TEORIA.md](recuperacionT3/ExamenProgAEv3_2526/TEORIA.md) | Teoría MVC, DAO, JDBC, Swing, flujos CRUD |
| [recuperacionT3/.../README.md](recuperacionT3/ExamenProgAEv3_2526/README.md) | Guía práctica archivo por archivo con código |
| [ExamenT3/README.md](ExamenT3/README.md) | Plantilla Michelin paso a paso |
| [mysql_procedural_cheatsheet.md](mysql_procedural_cheatsheet.md) | Procedimientos, funciones y triggers MySQL |
| [Java/POO/.../CHEATSHEET_EXAMEN.md](Java/POO/src/com/dam/IG/ExsClase/CHEATSHEET_EXAMEN.md) | Resumen MVC + Swing para examen |

---

## Tecnologías

| Tecnología | Uso en el repo |
|------------|----------------|
| Java SE 21 | Lenguaje principal |
| Swing | Interfaces gráficas de escritorio |
| JDBC + SQLite | Persistencia en exámenes y actividades |
| MySQL | Scripts y apuntes de BD |
| PHP | Actividad puntual en otra asignatura |

---

## Resumen

Este repositorio no es un único proyecto: es un **cuaderno de código** que refleja la progresión del DAM en Java — de `System.out.println` y bucles a aplicaciones completas con ventanas, tablas, menús y base de datos. El hilo conductor del tercer trimestre es dominar **quién hace qué** en MVC y **cómo fluyen los datos** cuando el usuario pulsa cada botón.

> *¿A qué capa pertenece este TODO? ¿Quién necesita el resultado? ¿Tengo el getter para que el controlador lea ese dato?*

---

**Autor:** David Martínez Gallego · 1º DAM
