# Code-DAM

Repositorio personal con el código y el material de estudio del **1º DAM** (David Martínez Gallego). Aquí van acumulándose ejercicios de clase, actividades por unidad formativa, pruebas parciales y proyectos más completos — desde programas de consola hasta aplicaciones Swing con base de datos.

No es un único proyecto desplegable: es una colección organizada por carpetas, cada una con su propio `main` o punto de entrada.

---

## Contenido general

| Área | Carpeta | Qué hay |
|------|---------|---------|
| Fundamentos Java | [`Java/UF_4/`](Java/UF_4/) · [`Java/SentenciasRep/`](Java/SentenciasRep/) · [`Java/Pruebas_UF4_1/`](Java/Pruebas_UF4_1/) | Condicionales, `switch`, bucles `for`/`while` |
| POO y colecciones | [`Java/POO/`](Java/POO/) | Clases, herencia, arrays, `HashMap`, excepciones, actividades sueltas |
| Arquitectura y consola avanzada | [`Java/ActividadUF8_2_*/`](Java/) · [`Java/Ejercicio2_ExT2_2425/`](Java/Ejercicio2_ExT2_2425/) | Menús de consola, composición, herencia aplicada |
| Interfaces gráficas (Swing) | [`Java/POO/src/com/dam/IG/`](Java/POO/src/com/dam/IG/) · [`ExamenT3/`](ExamenT3/) · [`recuperacionT3/`](recuperacionT3/) | MVC, paneles, tablas, JDBC + SQLite |
| Bases de datos | Scripts en raíz + DDL dentro de proyectos Java | SQL, procedimientos MySQL, SQLite embebida |
| PHP | [`ActividadPHP_DavidMartinezGallego/`](ActividadPHP_DavidMartinezGallego/) | CRUD web de productos con MySQL |
| Apuntes | Varios `.md` y [`Java/Apuntes/`](Java/Apuntes/) | Patrones reutilizables, cheatsheets, guías |

---

## Proyectos destacados

Los más completos y con más código propio:

### ExamenProgAEv3_2526 — Gestión de empresas

**Ruta:** [`recuperacionT3/ExamenProgAEv3_2526/`](recuperacionT3/ExamenProgAEv3_2526/)

Aplicación Swing para dar de alta, consultar, modificar y eliminar empresas. Arquitectura MVC con DAO, SQLite y validaciones de CIF, correo y teléfono.

- **Entrada:** `InicioEmpresas`
- **Stack:** Java 21, Swing, JDBC, SQLite
- **Docs:** [README del proyecto](recuperacionT3/ExamenProgAEv3_2526/README.md) · [TEORIA.md](recuperacionT3/ExamenProgAEv3_2526/TEORIA.md)

---

### Guía Michelin — Restaurantes

**Ruta:** [`ExamenT3/ActividadUF10_2_Guia_Michelin/`](ExamenT3/ActividadUF10_2_Guia_Michelin/)

CRUD de restaurantes de la guía Michelin: consulta con filtros por región y distinción, registro, modificación y eliminación desde tabla.

- **Entrada:** `com.michelin.main.RestaurantesMain`
- **Stack:** Swing, MVC, SQLite
- **Docs:** [Guía del bloque ExamenT3](ExamenT3/README.md)

Variante propia en [`ExamenT3/Michelin/MichelinDamaga/`](ExamenT3/Michelin/MichelinDamaga/).

---

### Encuestas — Consumo de series (IG)

**Ruta:** [`Java/POO/src/com/dam/IG/ExsClase/`](Java/POO/src/com/dam/IG/ExsClase/)

Aplicación Swing con patrón MVC para crear encuestas sobre consumo de series de TV y visualizar estadísticas.

- **Entrada:** `Inicio`
- **Stack:** Swing, MVC
- **Docs:** [README del proyecto](Java/POO/src/com/dam/IG/ExsClase/README.md)

Plantilla y apuntes relacionados en [`IG/ApuntesExaIG/`](Java/POO/src/com/dam/IG/ApuntesExaIG/).

---

### GestionNoticias — Noticias por consola

**Ruta:** [`Java/Ejercicio2_ExT2_2425/`](Java/Ejercicio2_ExT2_2425/)

Menú de consola para gestionar noticias con jerarquía de clases (política, deportes, economía). Herencia y polimorfismo aplicados.

- **Entrada:** `com.dam.main.GestionNoticias`

---

### ActividadPHP — CRUD de productos

**Ruta:** [`ActividadPHP_DavidMartinezGallego/`](ActividadPHP_DavidMartinezGallego/)

Aplicación web en PHP con MySQL: listar, crear, editar y eliminar productos, con subida de imágenes.

- **Entrada:** `index.php`
- **Stack:** PHP, MySQL, HTML

---

## Resto de proyectos Java

### Fundamentos (UF4)

| Carpeta | Descripción | Entrada principal |
|---------|-------------|-------------------|
| [`Java/UF_4/UF_4/`](Java/UF_4/UF_4/) | Ejercicios de `if`, `switch` y bucles (`ex06`–`ex12`, etc.) | Varios: `dam.Ejercicio2`, `TestIF`, `Switchcase`… |
| [`Java/SentenciasRep/`](Java/SentenciasRep/) | Repaso de sentencias de control | `Practica2`, `Practica3`, `Ejercicio4`–`6` |
| [`Java/Pruebas_UF4_1/`](Java/Pruebas_UF4_1/) | Práctica de bucles `for` y `while` | `forsum50`, `pruebasfor`, `pruebawhile`… |
| [`Java/testNV/`](Java/testNV/) | Prueba rápida con `ArrayList` | `ArrL` |

### POO — [`Java/POO/`](Java/POO/)

Carpeta grande con muchas actividades sueltas. Las subcarpetas más relevantes:

| Subcarpeta | Descripción | Entrada |
|------------|-------------|---------|
| [`pojo/`](Java/POO/src/com/dam/pojo/) | Clases de dominio: `Persona`, `Coche`, `Helipuerto`, `Piramide`… | `helipuertomain` |
| [`actividades/`](Java/POO/src/com/dam/actividades/) | Ejercicios varios: strings, arrays, productos, COVID, palíndromos | `Productomain`, `covidexe`, `arraysDamaga`… |
| [`actividades/herencia/`](Java/POO/src/com/dam/actividades/herencia/) | Herencia con empleados de empresa y jerarquía de noticias | `EmpresaMain`, `NoticiaMain` |
| [`actividades/playlist/`](Java/POO/src/com/dam/actividades/playlist/) | Playlist de canciones con `ArrayList` y menú por consola | `playlistmain` |
| [`actividades/Login/`](Java/POO/src/com/dam/actividades/Login/) | Prototipo Swing de login → ventana principal | `LoginIG/src/Main` |
| [`actividades/dbCode/`](Java/POO/src/com/dam/actividades/dbCode/) | Laboratorio JDBC con Maven y SQLite | `ProyectoMaven/.../App` |
| [`hashmaps/`](Java/POO/src/com/dam/hashmaps/) | `HashMap`, `TreeMap`, Tetris de puntuaciones, quiz de verbos | `Tetris`, `Ex01Hashmaps` |
| [`exceptions/`](Java/POO/src/com/dam/exceptions/) | Pruebas con excepciones y estructuras de productos | `exceptionTest00` |
| [`testparcial/`](Java/POO/src/com/dam/testparcial/) | Ejercicios de pruebas parciales (arrays, consola) | `ex01`, `ARRBI2021A`… |
| [`tests/`](Java/POO/src/com/dam/tests/) | Básicos de `String`, arrays y listas | `Strings`, `arrays`, `listest` |

### Arquitectura y consola (UF8)

| Carpeta | Descripción | Entrada |
|---------|-------------|---------|
| [`Java/ActividadUF8_2_Arquitectura/`](Java/ActividadUF8_2_Arquitectura/) | Construir una casa con habitaciones tipadas (`Hall`, `Cocina`, `Salon`…) | `GestionArq` |
| [`Java/ActividadUF8_2_Empresa/`](Java/ActividadUF8_2_Empresa/) | Gestión de empresa con empleados de producción y distribución | `GestionEmpresa` |

### Pruebas y borradores

| Carpeta | Descripción | Entrada |
|---------|-------------|---------|
| [`PruebaParcialAE1DavidMartinez/`](PruebaParcialAE1DavidMartinez/) | Dos ejercicios de consola: múltiplos y elección de restaurante | `Ejercicio1`, `EleccionRestaurante` |
| [`Java/Exs/`](Java/Exs/) | Ejercicio suelto (plantilla) | `ejercicio6` |
| [`Java/ProgramaME/`](Java/ProgramaME/) | Borrador de programa de triángulos | `triangulos` (vacío) |

---

## Material de referencia (no ejecutable)

| Archivo / carpeta | Contenido |
|-------------------|-----------|
| [`Java/Apuntes/`](Java/Apuntes/) | Estructuras de clase reutilizables para exámenes de consola |
| [`mysql_procedural_cheatsheet.md`](mysql_procedural_cheatsheet.md) | Apuntes de procedimientos, funciones y triggers en MySQL |
| [`Taller_Repaso_3T_Damaga.sql`](Taller_Repaso_3T_Damaga.sql) | Scripts SQL del taller de repaso del tercer trimestre |
| [`yazi-cheatsheet.md`](yazi-cheatsheet.md) | Atajos del gestor de archivos Yazi |

---

## Tecnologías usadas

| Tecnología | Dónde aparece |
|------------|---------------|
| Java SE 21 | Proyectos principales |
| Swing | Empresas, Michelin, Encuestas, Login |
| JDBC + SQLite | Empresas, Michelin, dbCode |
| MySQL | PHP, scripts SQL, cheatsheet procedural |
| PHP + HTML | Actividad de productos |
| Maven | `actividades/dbCode/ProyectoMaven` |

---

## Cómo ejecutar un proyecto

Cada carpeta es independiente. En general:

1. Abrir la carpeta del proyecto en Eclipse, IntelliJ o VS Code.
2. Localizar la clase con `public static void main`.
3. Ejecutar como Java Application.

Los proyectos con SQLite necesitan el `.jar` del driver en `lib/` y la base de datos creada con el DDL correspondiente. El proyecto de empresas tiene instrucciones concretas en su [README](recuperacionT3/ExamenProgAEv3_2526/README.md#22-cómo-ejecutar-el-proyecto).

---

## Estructura del repositorio

```
Code-DAM/
├── Java/                          ← ejercicios y actividades por UF
│   ├── UF_4/                      ← fundamentos
│   ├── POO/                       ← POO, colecciones, IG, actividades
│   ├── ActividadUF8_2_*/          ← arquitectura y empresa (consola)
│   ├── Ejercicio2_ExT2_2425/      ← gestión de noticias
│   └── Apuntes/                   ← patrones de consola
│
├── ExamenT3/                      ← guía Michelin + implementaciones
├── recuperacionT3/                ← examen de empresas
├── PruebaParcialAE1DavidMartinez/ ← prueba parcial AE1
├── ActividadPHP_DavidMartinezGallego/
│
├── mysql_procedural_cheatsheet.md
├── Taller_Repaso_3T_Damaga.sql
└── README.md
```

---

**Autor:** David Martínez Gallego · 1º DAM
