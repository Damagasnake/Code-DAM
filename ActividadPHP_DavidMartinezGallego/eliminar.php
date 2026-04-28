<?php
include 'conexion.php';

if (isset($_GET['id_producto'])) {
    $id_producto = $_GET['id_producto'];

    $query = "DELETE FROM productos WHERE id_producto = $id_producto";

    if ($conexion->query($query) === TRUE) {
        echo "Producto eliminado correctamente";
    } else {
        echo "Error al eliminar el producto: " . $conexion->error;
    }
} else {
    echo "ID de producto no especificado.";
}

$conexion->close();
?>