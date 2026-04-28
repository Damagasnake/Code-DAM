<?php
include 'conexion.php';

if (isset($_GET['id_producto'])) { // Igual que antes se chequea si se ha pasado un id
    $id_producto = $_GET['id_producto']; //lo asigno a una variable
    $query = "SELECT * FROM productos WHERE id_producto = $id_producto"; // query
    $result = $conexion->query($query);// ejecuto la query
    $producto = $result->fetch_assoc(); // asigno el resultado a una variable
} else {
    echo "No se ha proporcionado un ID de producto.";
    exit;
}

if ($_SERVER['REQUEST_METHOD'] == 'POST') { // se le asignan a todas las variables los datos del formulario
    $nombre = $_POST['nombre'];
    $descripcion = $_POST['descripcion'];
    $precio = $_POST['precio'];
    $imagen = $_POST['imagen'];

    $query = "UPDATE productos SET nombre='$nombre', descripcion='$descripcion', precio='$precio', imagen='$imagen' WHERE id_producto=$id_producto"; // query preparada para actualizar el producto con los datos del formulario
    
    if ($conexion->query($query) === TRUE) {
        echo "Producto actualizado correctamente";
        header("Location: index.php");
        exit;
    } else {
        echo "Error al actualizar el producto: " . $conexion->error;
    }
}

$conexion->close();
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Producto</title>
</head>
<body>
    <h1>Editar Producto</h1>
    <form action="" method="POST">
        <label for="nombre">Nombre:</label>
        <input type="text" name="nombre" value="<?php echo $producto['nombre']; ?>" required>
        <br>
        <label for="descripcion">Descripción:</label>
        <textarea name="descripcion" required><?php echo $producto['descripcion']; ?></textarea>
        <br>
        <label for="precio">Precio:</label>
        <input type="number" name="precio" value="<?php echo $producto['precio']; ?>" required>
        <br>
        <label for="imagen">Imagen:</label>
        <input type="text" name="imagen" value="<?php echo $producto['imagen']; ?>">
        <br>
        <input type="submit" value="Actualizar Producto">
    </form>
    <a href="index.php">Volver a la lista de productos</a>
</body>
</html>