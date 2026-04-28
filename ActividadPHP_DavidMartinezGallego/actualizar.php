<?php
include 'conexion.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id = $_POST['id'];
    $nombre = $_POST['nombre'];
    $descripcion = $_POST['descripcion'];
    $precio = $_POST['precio'];
    $imagen = $_POST['imagen'];

    if (isset($_FILES['imagen']) && $_FILES['imagen']['error'] == UPLOAD_ERR_OK) {
        $nombre_imagen = time() . '_' . $_FILES['imagen']['name'];
        $ruta_temporal = $_FILES['imagen']['tmp_name'];
        $ruta_destino = 'imgs/' . $nombre_imagen;
        move_uploaded_file($ruta_temporal, $ruta_destino);
        $imagen = $ruta_destino;
    }

    $query = "UPDATE productos SET nombre='$nombre', descripcion='$descripcion', precio='$precio', imagen='$imagen' WHERE id='$id'";

    if ($conexion->query($query) === TRUE) {
        echo "Producto actualizado correctamente";
    } else {
        echo "Error al actualizar el producto: " . $conexion->error;
    }
}

$id = $_GET['id'];
$query = "SELECT * FROM productos WHERE id='$id'";
$result = $conexion->query($query);
$product = $result->fetch_assoc();

$conexion->close();
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Actualizar Producto</title>
</head>
<body>
    <h1>Actualizar Producto</h1>
    <form action="actualizar.php" method="POST" enctype="multipart/form-data">
        <input type="hidden" name="id" value="<?php echo $product['id']; ?>">
        <label for="nombre">Nombre:</label>
        <input type="text" name="nombre" value="<?php echo $product['nombre']; ?>" required>
        <br>
        <label for="descripcion">Descripción:</label>
        <textarea name="descripcion" required><?php echo $product['descripcion']; ?></textarea>
        <br>
        <label for="precio">Precio:</label>
        <input type="number" name="precio" value="<?php echo $product['precio']; ?>" required>
        <br>
        <label for="imagen">Imagen:</label>
        <input type="file" name="imagen">
        <br>
        <input type="submit" value="Actualizar Producto">
    </form>
</body>
</html>