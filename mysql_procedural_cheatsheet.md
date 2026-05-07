# MySQL Procedural — Cheatsheet

---

## 📦 Procedimiento — estructura base

```sql
DELIMITER //
CREATE PROCEDURE MiProc(
    IN    p_entrada   INT,           -- solo lectura
    OUT   p_salida    VARCHAR(100),  -- solo escritura
    INOUT p_ambos     INT            -- lectura y escritura
)
BEGIN
    -- lógica aquí
END //
DELIMITER ;

CALL MiProc(5, @res, @val);   -- así se invoca
SELECT @res;                   -- leer variable OUT
```

| Modificador | Dirección | ¿Se puede leer dentro? | ¿Sale el valor al llamador? |
|-------------|-----------|------------------------|-----------------------------|
| `IN`        | entrada   | ✅                     | ❌                          |
| `OUT`       | salida    | ❌ (entra NULL)        | ✅                          |
| `INOUT`     | ambos     | ✅                     | ✅                          |

---

## ⚡ Función — estructura base

```sql
DELIMITER //
CREATE FUNCTION MiFn(p_cod INT)
RETURNS DECIMAL(10,2)
DETERMINISTIC       -- mismo input → mismo output siempre
READS SQL DATA      -- hace SELECTs pero no modifica datos
BEGIN
    DECLARE v_res DECIMAL(10,2);

    SELECT precio INTO v_res
    FROM productos WHERE id = p_cod;

    RETURN v_res;   -- OBLIGATORIO, sin esto no compila
END //
DELIMITER ;

SELECT MiFn(3);           -- se usa en SELECT
SELECT * FROM t WHERE precio > MiFn(5);  -- o en WHERE
```

> ⚠️ Las funciones NO usan `CALL`. Solo aceptan parámetros `IN`.

---

## 📝 DECLARE — variables locales

```sql
BEGIN
    -- SIEMPRE al principio, antes de cualquier instrucción
    DECLARE v_nombre   VARCHAR(50);
    DECLARE v_edad     INT           DEFAULT 0;
    DECLARE v_precio   DECIMAL(10,2);
    DECLARE v_ok       BOOLEAN       DEFAULT FALSE;

    -- Asignar con SET:
    SET v_edad = 25;
    SET v_nombre = 'Texto';

    -- Asignar desde query:
    SELECT nombre INTO v_nombre
    FROM clientes WHERE id = 1;
END
```

> ⚠️ **Orden obligatorio dentro del BEGIN:**
> `DECLARE variables` → `DECLARE cursores` → `DECLARE handlers` → código

---

## 🔀 IF / ELSEIF / ELSE

```sql
IF condicion1 THEN
    -- bloque 1
ELSEIF condicion2 THEN
    -- bloque 2
ELSEIF condicion3 THEN
    -- bloque 3
ELSE
    -- si ninguna se cumple
END IF;   -- ← obligatorio cerrarlo

-- Ejemplo real (descuento por stock):
IF v_stock >= 50 THEN
    SET v_final = v_precio * 0.80;
ELSEIF v_stock BETWEEN 20 AND 49 THEN
    SET v_final = v_precio * 0.90;
ELSEIF v_stock BETWEEN 1 AND 19 THEN
    SET v_final = v_precio * 0.95;
ELSE
    SET v_final = v_precio;
END IF;
```

---

## 🎯 CASE / WHEN

```sql
-- Forma 1: comparar valor exacto (como switch)
CASE v_estado
    WHEN 'A' THEN SET v_txt = 'Activo';
    WHEN 'I' THEN SET v_txt = 'Inactivo';
    ELSE           SET v_txt = 'Desconocido';
END CASE;

-- Forma 2: condiciones booleanas (como IF encadenado)
CASE
    WHEN v_stock >= 50             THEN SET v_dto = 0.20;
    WHEN v_stock BETWEEN 20 AND 49 THEN SET v_dto = 0.10;
    ELSE                                SET v_dto = 0.00;
END CASE;

-- En SELECT (devuelve valor, sin END CASE):
SELECT CASE WHEN stock >= 50 THEN 'Alto' ELSE 'Bajo' END AS nivel
FROM productos;
```

---

## 🔁 Bucles: WHILE / REPEAT / LOOP

```sql
-- ── WHILE ──────────────────────────────────────────
-- Evalúa la condición ANTES de ejecutar.
-- Si la condición es falsa desde el inicio, no entra.
WHILE v_i <= 10 DO
    SET v_suma = v_suma + v_i;
    SET v_i    = v_i + 1;        -- ⚠️ no olvidar o bucle infinito
END WHILE;

-- ── REPEAT ─────────────────────────────────────────
-- Evalúa la condición AL FINAL.
-- Se ejecuta mínimo 1 vez aunque la condición sea falsa.
REPEAT
    SET v_i = v_i + 1;
UNTIL v_i > 10
END REPEAT;

-- ── LOOP ───────────────────────────────────────────
-- Bucle infinito. Tú controlas cuándo salir con LEAVE.
mi_loop: LOOP
    IF v_i > 10 THEN
        LEAVE mi_loop;    -- = break
    END IF;
    SET v_i = v_i + 1;
    ITERATE mi_loop;      -- = continue (vuelve al inicio del LOOP)
END LOOP;
```

| Bucle    | Evalúa condición | Mínimo de ejecuciones | Salida manual |
|----------|------------------|-----------------------|---------------|
| `WHILE`  | Al inicio        | 0                     | No necesaria  |
| `REPEAT` | Al final         | 1                     | No necesaria  |
| `LOOP`   | Nunca            | ∞                     | `LEAVE` obligatorio |

---

## 🖱️ CURSOR — recorrer filas una a una

Un cursor es un puntero que recorre una query fila a fila dentro de un procedimiento.

```sql
DELIMITER //
CREATE PROCEDURE SP_CON_CURSOR()
BEGIN
    -- 1. Variables donde guardar cada fila
    DECLARE v_nombre  VARCHAR(50);
    DECLARE v_precio  DECIMAL(10,2);
    DECLARE v_fin     BOOLEAN DEFAULT FALSE;

    -- 2. Declarar el cursor (la query que va a recorrer)
    DECLARE cur CURSOR FOR
        SELECT nombre, precio FROM productos WHERE stock > 0;

    -- 3. Handler: cuando no haya más filas → pone v_fin = TRUE
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_fin = TRUE;

    -- 4. Abrir el cursor (ejecuta la query y posiciona al inicio)
    OPEN cur;

    -- 5. Bucle de lectura
    leer: LOOP
        FETCH cur INTO v_nombre, v_precio;   -- lee la siguiente fila
        IF v_fin THEN LEAVE leer; END IF;    -- si no hay más → salir

        -- lógica con cada fila:
        SELECT v_nombre, v_precio;
    END LOOP;

    -- 6. Cerrar el cursor (libera memoria)
    CLOSE cur;
END //
DELIMITER ;
```

**Flujo del cursor:**
```
DECLARE → OPEN → [FETCH → procesar] × N filas → NOT FOUND → LEAVE → CLOSE
```

---

## 🚨 HANDLER — capturar errores y eventos

Un handler es un bloque que se activa automáticamente cuando ocurre un evento o error concreto.

```sql
-- ── CONTINUE HANDLER ───────────────────────────────
-- Captura el evento, ejecuta su bloque, y CONTINÚA desde
-- la siguiente instrucción después de la que lo disparó.

DECLARE CONTINUE HANDLER FOR NOT FOUND
    SET v_fin = TRUE;

DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
    SET v_error = TRUE;

-- ── EXIT HANDLER ───────────────────────────────────
-- Captura el evento y SALE del bloque BEGIN..END donde está.

DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
    SELECT 'Ocurrió un error' AS msg;
    -- el procedimiento termina aquí
END;

-- ── Eventos más usados ─────────────────────────────
-- NOT FOUND      → cursor sin más filas / SELECT INTO sin resultados
-- SQLEXCEPTION   → cualquier error SQL (duplicate key, FK violation...)
-- SQLWARNING     → advertencias (división por 0, truncamiento...)
```

---

## 📋 Comandos útiles

```sql
-- Borrar antes de recrear (evita error "already exists")
DROP PROCEDURE IF EXISTS MiProc;
DROP FUNCTION  IF EXISTS MiFn;

-- Ver qué procedures/functions existen en la BD
SHOW PROCEDURE STATUS WHERE Db = 'mi_bd';
SHOW FUNCTION  STATUS WHERE Db = 'mi_bd';

-- Ver el código fuente de uno
SHOW CREATE PROCEDURE MiProc;
SHOW CREATE FUNCTION  MiFn;

-- Variable de sesión (para recibir OUT fuera del proc)
SET @mi_var = 0;
CALL MiProc(1, @mi_var);
SELECT @mi_var;
```

---

## ⚠️ Errores comunes

| Error | Causa |
|-------|-------|
| `RETURN` fuera de función | `RETURN` solo existe en funciones, no en procedures |
| `DELIMITER` olvidado | MySQL ejecuta cada `;` interno como sentencia suelta |
| `DECLARE` después de código | Las variables deben declararse antes que cualquier instrucción |
| Cursor no cerrado | Fuga de memoria; siempre hacer `CLOSE` |
| `HANDLER` antes que el cursor | El orden es: variables → cursores → handlers |
| Bucle infinito en `WHILE` | Olvidar incrementar la variable de control |
