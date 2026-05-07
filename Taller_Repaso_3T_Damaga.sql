-- ============================================================
--  BASES DE DATOS - DAM 1
--  Taller de Repaso de Bases de Datos 3T
--  Alumno: Damaga
-- ============================================================


-- ============================================================
-- EJERCICIO 1 - PREGUNTAS TEÓRICAS (respuestas en comentarios)
-- ============================================================

/*
1. ¿Qué diferencia hay entre un procedimiento y una función?

   Un PROCEDIMIENTO (PROCEDURE) es un bloque de código almacenado que
   ejecuta una serie de instrucciones. Puede tener parámetros IN, OUT e
   INOUT, y NO está obligado a devolver ningún valor. Se invoca con CALL.

   Una FUNCIÓN (FUNCTION) también es un bloque almacenado, pero SIEMPRE
   debe devolver un único valor escalar mediante RETURN. Solo admite
   parámetros de entrada (equivalentes a IN). Se invoca dentro de una
   expresión SQL, igual que cualquier función nativa (SELECT, WHERE, etc.).

   Resumen:
   - Procedure → puede o no devolver valores (vía OUT), se llama con CALL.
   - Function  → SIEMPRE devuelve un valor escalar, usable en SELECT/WHERE.


2. ¿Qué parámetro se usa cuando queremos RECIBIR un valor dentro de un
   procedimiento?

   Se usa el modificador IN. Es el valor que el llamador le pasa al
   procedimiento para que lo use internamente. Dentro del procedimiento
   ese valor puede leerse pero cualquier modificación NO se refleja fuera.

   Ejemplo: IN p_codigo INT


3. ¿Qué parámetro se usa cuando queremos DEVOLVER un valor desde un
   procedimiento?

   Se usa el modificador OUT. La variable OUT se pasa desde fuera, el
   procedimiento le asigna un valor, y cuando el procedimiento termina
   esa variable contiene el resultado en el contexto del llamador.

   Ejemplo: OUT p_resultado VARCHAR(200)


4. ¿Qué hace DELIMITER?

   MySQL interpreta el punto y coma (;) como el fin de una sentencia y
   la ejecuta inmediatamente. Dentro de un procedimiento o función hay
   varios puntos y coma internos que NO deben terminar la definición.
   DELIMITER cambia temporalmente el carácter que MySQL usa como
   terminador de sentencia, de modo que todo el bloque CREATE se envíe
   de una sola vez. Al terminar se restaura a ;

   Ejemplo:
     DELIMITER $$
     CREATE PROCEDURE ... BEGIN ... END $$
     DELIMITER ;


5. ¿Para qué sirve CALL?

   CALL es la instrucción que invoca (ejecuta) un procedimiento almacenado.
   No se puede usar con funciones; para esas se usa SELECT o se las embebe
   en una expresión.

   Ejemplo: CALL SP_BUSCAR_CLIENTE(103);


6. ¿Para qué sirve RETURN en una función?

   RETURN indica el valor que la función devuelve al punto desde el que
   fue llamada y detiene la ejecución de la función en ese momento.
   Toda función DEBE tener al menos un RETURN. Si se llega al END sin
   haber ejecutado un RETURN, MySQL lanza un error.

   Ejemplo: RETURN precio_final;


7. ¿Puede una función devolver una tabla completa?

   NO. En MySQL una función almacenada solo puede devolver un valor
   escalar (INT, VARCHAR, DECIMAL, DATE, etc.). Si se necesita devolver
   un conjunto de filas hay que usar un procedimiento (que ejecuta un
   SELECT y MySQL devuelve el result set al cliente) o bien una vista.


8. ¿Qué diferencia hay entre IN, OUT e INOUT?

   IN    → El valor entra al procedimiento. Solo lectura dentro de él.
           El llamador pasa un literal o variable; cambios internos no
           se propagan hacia fuera.

   OUT   → El procedimiento escribe un valor en esa variable. Al entrar
           su valor es NULL. Al salir la variable del llamador contiene
           lo que el procedimiento le asignó.

   INOUT → Combinación de ambos. El llamador pasa un valor inicial, el
           procedimiento puede leerlo Y modificarlo, y el valor final
           queda disponible en la variable del llamador al terminar.


9. ¿Qué estructura usamos para tomar decisiones dentro de un
   procedimiento?

   Las principales son:

   a) IF / ELSEIF / ELSE / END IF
      Evalúa condiciones booleanas de forma secuencial.

   b) CASE / WHEN / THEN / ELSE / END CASE
      Compara un valor contra múltiples casos posibles (similar al
      switch de otros lenguajes). Más legible cuando hay muchas ramas
      basadas en el valor de una sola expresión.


10. ¿Qué diferencia hay entre IF y CASE en programación procedimental?

    IF:
    - Evalúa una condición booleana (cualquier expresión que dé TRUE/FALSE).
    - Permite condiciones complejas y distintas en cada rama (ELSEIF).
    - Es la opción natural cuando las ramas dependen de expresiones
      diferentes o rangos de valores.

    CASE:
    - Compara una expresión contra valores concretos (CASE expr WHEN val)
      o evalúa condiciones booleanas independientes (CASE WHEN cond).
    - Es más legible y compacto cuando se comprueban múltiples valores
      posibles de UNA misma variable (equivalente a switch/case).
    - No puede reemplazar a IF cuando las condiciones son rangos o
      expresiones heterogéneas, salvo usando la forma CASE WHEN.
*/


-- ============================================================
-- EJERCICIO 2 - PROCEDIMIENTO: SP_BUSCAR_CLIENTE
-- ============================================================
/*
   BASE DE DATOS ASUMIDA: estructura tipo Northwind / clásica de clase.

   Tabla: customers
   Columnas relevantes:
     customerNumber  INT           (PK - código del cliente)
     customerName    VARCHAR(50)
     city            VARCHAR(50)
     country         VARCHAR(50)

   ARGUMENTACIÓN:
   ─────────────────────────────────────────────────────────────
   ¿Qué parámetro usamos?
     IN p_codigo INT
     Usamos IN porque el procedimiento solo necesita RECIBIR el
     código del cliente; no tiene que devolver ningún valor extra
     mediante parámetro (el resultado se muestra con SELECT).

   ¿Qué recibe el procedimiento?
     El número identificador del cliente (customerNumber).

   ¿Qué devuelve visualmente?
     Si el cliente existe → un SELECT con su nombre, ciudad y país.
     Si no existe         → un SELECT con el mensaje
                            'Cliente no encontrado'.

   ¿Cómo lo llamamos?
     CALL SP_BUSCAR_CLIENTE(103);   -- con un código existente
     CALL SP_BUSCAR_CLIENTE(9999);  -- con uno que no existe
   ─────────────────────────────────────────────────────────────
*/

USE classicmodels;  -- ajustar al nombre real de la BD en clase

DROP PROCEDURE IF EXISTS SP_BUSCAR_CLIENTE;

DELIMITER $$

CREATE PROCEDURE SP_BUSCAR_CLIENTE(IN p_codigo INT)
BEGIN
    -- Variable auxiliar para comprobar si el cliente existe
    DECLARE v_nombre  VARCHAR(50);
    DECLARE v_ciudad  VARCHAR(50);
    DECLARE v_pais    VARCHAR(50);

    -- Intentamos recuperar los datos del cliente
    SELECT customerName, city, country
    INTO   v_nombre, v_ciudad, v_pais
    FROM   customers
    WHERE  customerNumber = p_codigo
    LIMIT  1;

    -- Si la variable quedó NULL el cliente no existe
    IF v_nombre IS NULL THEN
        SELECT 'Cliente no encontrado' AS mensaje;
    ELSE
        SELECT v_nombre  AS nombre_cliente,
               v_ciudad  AS ciudad,
               v_pais    AS pais;
    END IF;

END $$

DELIMITER ;

-- Pruebas
CALL SP_BUSCAR_CLIENTE(103);   -- debe devolver datos del cliente 103
CALL SP_BUSCAR_CLIENTE(9999);  -- debe devolver 'Cliente no encontrado'


-- ============================================================
-- EJERCICIO 3 - FUNCIÓN: FN_PRECIO_FINAL_STOCK
-- ============================================================
/*
   BASE DE DATOS: tienda musical (estructura asumida)

   Tabla: productos
   Columnas relevantes:
     codigo_producto  VARCHAR(15)  (PK)
     precio_unitario  DECIMAL(10,2)
     stock            INT

   NOTA: Si en clase la tabla se llama de otro modo (p.ej. products,
   Track, articulos...) basta con cambiar el nombre de tabla y columnas
   en el SELECT INTO de la función.

   LÓGICA DE DESCUENTOS:
     stock >= 50          → 20 % de descuento  → precio * 0.80
     stock BETWEEN 20-49  → 10 % de descuento  → precio * 0.90
     stock BETWEEN 1-19   →  5 % de descuento  → precio * 0.95
     stock = 0            →  0 % de descuento  → precio * 1.00
*/

DROP FUNCTION IF EXISTS FN_PRECIO_FINAL_STOCK;

DELIMITER $$

CREATE FUNCTION FN_PRECIO_FINAL_STOCK(p_codigo VARCHAR(15))
RETURNS DECIMAL(10,2)
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE v_precio  DECIMAL(10,2);
    DECLARE v_stock   INT;
    DECLARE v_final   DECIMAL(10,2);

    -- Recuperar precio y stock del producto
    SELECT precio_unitario, stock
    INTO   v_precio, v_stock
    FROM   productos
    WHERE  codigo_producto = p_codigo
    LIMIT  1;

    -- Si el producto no existe devolvemos NULL
    IF v_precio IS NULL THEN
        RETURN NULL;
    END IF;

    -- Aplicar descuento según stock usando CASE
    SET v_final = CASE
        WHEN v_stock >= 50                    THEN v_precio * 0.80  -- 20% dto
        WHEN v_stock BETWEEN 20 AND 49        THEN v_precio * 0.90  -- 10% dto
        WHEN v_stock BETWEEN 1  AND 19        THEN v_precio * 0.95  --  5% dto
        ELSE                                       v_precio * 1.00  --  0% dto
    END;

    RETURN v_final;

END $$

DELIMITER ;

-- Pruebas de la función
SELECT FN_PRECIO_FINAL_STOCK('P001') AS precio_final;

-- Ver precio original vs precio final para todos los productos
SELECT codigo_producto,
       nombre,
       stock,
       precio_unitario                          AS precio_original,
       FN_PRECIO_FINAL_STOCK(codigo_producto)   AS precio_con_descuento
FROM   productos
ORDER  BY stock DESC;





Drop function if exists TestDamaga

DELIMITER //
Create function TestDamaga(p_codigo varchar(200))
RETURNS decimal(10,2)
DETERMINISTIC
BEGIN
    Declare v_precio    decimal(10,2);
    Declare v_stock     int;
    declare v_final     decimal(10,2);

    select precioU, stock into v_precio, v_stock
    from prod
    where cod_pro = p_codigo;

    if(v_stock >= 50)
        then set v_final = v_precio * 0.8;
    ELSEIF v_stock BETWEEN 20 AND 49 THEN
        SET v_final = v_precio * 0.90;
    ELSEIF v_stock BETWEEN 1 AND 19 THEN
        SET v_final = v_precio * 0.95;
    ELSE
        SET v_final = v_precio;       -- stock = 0, sin descuento
    END IF;

    RETURN v_final;

END //
DELIMITER ;

Delimiter //
create procedure EJERCICIO1(p_id_album int)
begin
    declare v_nombreDisco varchar(500);
    declare v_nombreArt varchar(500);
    declare v_titulo varchar(500);
    declare v_nCanc int;
    declare v_duration varchar(500);
    declare v_masEsc varchar(500);

    SELECT titulo_cancion into v_masEsc from canciones where id_album = p_id_album
            order by reproduc desc limit 1;

    select count(*) into v_nCanc from canciones
        where id_album = p_id_album;

    select nombre_artista into v_nombreArt from albumes
        where id_album = p_id_album;

    select titulo_album into v_nombreDisco from albumes
        where id_album = p_id_album;
end ;
Delimiter ;;

-- Ejemplo Cursor + Handler

DELIMITER //
CREATE PROCEDURE SP_TICKET_ALBUM(IN p_id_album INT)
BEGIN
    -- Variables para guardar cada fila del cursor
    DECLARE v_titulo      VARCHAR(100);
    DECLARE v_precio      DECIMAL(10,2);
    DECLARE v_precio_dto  DECIMAL(10,2);
    DECLARE v_total       DECIMAL(10,2) DEFAULT 0;
    DECLARE v_nCanc       INT DEFAULT 0;
    DECLARE v_fin         BOOLEAN DEFAULT FALSE;  -- flag para saber cuándo parar

    -- Cursor: va a recorrer todas las canciones del álbum
    DECLARE cur CURSOR FOR
        SELECT titulo, precio FROM canciones
        WHERE id_album = p_id_album;

    -- Handler: cuando FETCH no encuentre más filas dispara esto
    -- pone v_fin = TRUE y CONTINÚA (no rompe el procedimiento)
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_fin = TRUE;

    OPEN cur;  -- ejecuta la query y se posiciona antes de la 1ª fila

    leer: LOOP
        FETCH cur INTO v_titulo, v_precio;  -- coge la siguiente fila

        IF v_fin THEN          -- si no había más filas → salir
            LEAVE leer;
        END IF;

        -- lógica con cada fila individualmente
        SET v_precio_dto = v_precio * 0.90;  -- 10% de descuento a cada una
        SET v_total      = v_total + v_precio_dto;
        SET v_nCanc      = v_nCanc + 1;

        SELECT v_titulo AS cancion,
               v_precio AS precio_original,
               v_precio_dto AS precio_con_descuento;
    END LOOP;

    CLOSE cur;  -- libera memoria, siempre obligatorio

    -- Resumen final tras recorrer todas las filas
    SELECT v_nCanc AS total_canciones,
           v_total AS precio_total_con_descuento;

END //
DELIMITER ;

CALL SP_TICKET_ALBUM(1);

-- Ejemplo con while

DELIMITER //
CREATE PROCEDURE SP_TICKET_ALBUM(IN p_id_album INT)
BEGIN
    DECLARE v_titulo      VARCHAR(100);
    DECLARE v_precio      DECIMAL(10,2);
    DECLARE v_precio_dto  DECIMAL(10,2);
    DECLARE v_total       DECIMAL(10,2) DEFAULT 0;
    DECLARE v_nCanc       INT DEFAULT 0;
    DECLARE v_fin         BOOLEAN DEFAULT FALSE;

    DECLARE cur CURSOR FOR
        SELECT titulo, precio FROM canciones
        WHERE id_album = p_id_album;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_fin = TRUE;

    OPEN cur;

    FETCH cur INTO v_titulo, v_precio;  -- primer FETCH antes del WHILE

    WHILE v_fin = FALSE DO              -- mientras haya filas
        SET v_precio_dto = v_precio * 0.90;
        SET v_total      = v_total + v_precio_dto;
        SET v_nCanc      = v_nCanc + 1;

        SELECT v_titulo AS cancion,
               v_precio AS precio_original,
               v_precio_dto AS precio_con_descuento;

        FETCH cur INTO v_titulo, v_precio;  -- FETCH al final del bloque
    END WHILE;

    CLOSE cur;

    SELECT v_nCanc AS total_canciones,
           v_total AS precio_total_con_descuento;

END //
DELIMITER ;
