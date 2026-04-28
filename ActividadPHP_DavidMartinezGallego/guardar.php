<?php
include 'conexion.php';

$nombre = $_POST['nombre'];
$descripcion = $_POST['descripcion'];
$precio = $_POST['precio'];
$imagen = '';

if (isset($_FILES['imagen']) && $_FILES['imagen']['error'] == UPLOAD_ERR_OK) {
    $nombre_imagen = time() . '_' . $_FILES['imagen']['name'];
    $ruta_temporal = $_FILES['imagen']['tmp_name'];
    $ruta_destino = 'imgs/' . $nombre_imagen;

    if (move_uploaded_file($ruta_temporal, $ruta_destino)) {
        $imagen = $ruta_destino;
    } else {
        echo "Error al subir la imagen.";
        exit;
    }
}

$query = "INSERT INTO productos (nombre, descripcion, precio, imagen) VALUES ('$nombre', '$descripcion', '$precio', '$imagen')";
if ($conexion->query($query) === TRUE) {
    echo "Producto guardado correctamente";
} else {
    echo "Error al guardar el producto: " . $conexion->error;
}

$conexion->close();
?>