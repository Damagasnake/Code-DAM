<?php
include 'conexion.php'; // incluimos el archivo de conexion para poder usar la base de datos

if ($_SERVER['REQUEST_METHOD'] == 'POST') { // chequea si se ha cargado la página
    // aqui se asignan los datos del formulario a las variables 
    $nombre = $_POST['nombre'];
    $descripcion = $_POST['descripcion'];
    $precio = $_POST['precio'];
    $imagen = '';

    if (isset($_FILES['imagen']) && $_FILES['imagen']['error'] == UPLOAD_ERR_OK) { // check para ver si el codigo de error es igual a 0(funciona correctamente) != 0 (no funciona)
        $nombre_imagen = time() . '_' . $_FILES['imagen']['name']; //Se le asigna de nombre la fecha y lo concatena con el nombre de la imagen
        $ruta_temporal = $_FILES['imagen']['tmp_name'];
        $ruta_destino = 'imgs/' . $nombre_imagen;

        move_uploaded_file($ruta_temporal, $ruta_destino); // mueve el archivo de la ruta temporal a la ruta destino
        $imagen = $ruta_destino; // se asigna la ruta de la imagen a la variable $imagen para guardarla en la base de datos
    }

    $query = "INSERT INTO productos (nombre, descripcion, precio, imagen) VALUES ('$nombre', '$descripcion', '$precio', '$imagen')"; //query preparada para insertar otro producto
    //$query = "INSERT INTO productos(nombre, descripcion, precio, imagen) values (?,?,?,?)";
    //$stmt = $conexion->prepare($query);
    //$stmt->bind_param("ssds", $nombre, $descripcion, $precio, $imagen);
    // $stmt->execute;
    if ($conexion->query($query) === TRUE) {
        echo "Producto guardado correctamente";
    } else {
        echo "Error al guardar el producto: " . $conexion->error;
    }
}

?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Crear Producto</title>
</head>
<body> 
    <!-- formulario que se enseña para añadir un producto-->
    <h1>Agregar Nuevo Producto</h1>
    <form action="crear.php" method="POST" enctype="multipart/form-data">
        <label for="nombre">Nombre:</label>
        <input type="text" name="nombre" required><br>

        <label for="descripcion">Descripción:</label>
        <textarea name="descripcion" required></textarea><br>

        <label for="precio">Precio:</label>
        <input type="number" name="precio" step="0.01" required><br>

        <label for="imagen">Imagen:</label>
        <input type="file" name="imagen" accept="image/*"><br>

        <input type="submit" value="Guardar Producto">
    </form>
    <a href="index.php">Volver a la lista de productos</a>
</body>
</html>