<?php
function obtenerConexion() {
    $host = 'localhost';
    $puerto = '3307';
    $usuario = 'root';
    $password = '';
    $basedatos = 'productobd2';

    // Construcción del host con puerto usando dos puntos
    $servidor = $host . ':' . $puerto;

    $conexion = new mysqli($servidor, $usuario, $password, $basedatos);

    if ($conexion->connect_error) {
        die("Error de conexión: " . $conexion->connect_error);
    }

    return $conexion;
}

function crearTabla($conexion) {
    // Definición de la estructura de la tabla (ejemplo: tabla de productos)
    $sql = "CREATE TABLE productos (
        id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
        nombre VARCHAR(30) NOT NULL,
        precio DECIMAL(10, 2) NOT NULL,
        fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    )";

    // Ejecución y verificación
    if ($conexion->query($sql) === TRUE) {
        echo "Tabla '$nombreTabla' creada exitosamente.";
    } else {
        echo "Error al crear la tabla: " . $conexion->error;
    }
}

function cerrarConexion($conexion) {
    if ($conexion) {
        $conexion->close();
        echo "Conexión cerrada correctamente.";
    }
}

function insertarProducto($conexion, $nombre, $precio) {
    $sql = "INSERT INTO productos (nombre, precio) VALUES (?, ?)";
    //SELECT nombre, precio FROM productos WHERE id = ?;
    //UPDATE productos SET precio = ?, stock = ? WHERE id = ?;
    //DELETE FROM productos WHERE id = ?"
    $stmt = $conexion->prepare($sql);   
    $stmt->bind_param("sd", $nombre, $precio);

    // 3. Ejecutar y verificar
    if ($stmt->execute()) {
        echo "Producto insertado correctamente.";
    } else {
        echo "Error al insertar: " . $stmt->error;
    }

    // 4. Cerrar la sentencia
    $stmt->close();
}
?>