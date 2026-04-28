<?php
include 'conexion.php';

// Fetch products from the database
$query = "SELECT * FROM productos";
$result = $conexion->query($query);
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Productos</title>
</head>
<body>
    <h1>Lista de Productos</h1>
    <a href="crear.php">Agregar Nuevo Producto</a>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Precio</th>
            <th>Imagen</th>
            <th>Acciones</th>
        </tr>
        
        <!-- bucle while que enseña los datos de la tabla almacenada en la base de datos --> 
        <?php if ($result->num_rows > 0): ?>
            <?php while($row = $result->fetch_assoc()): ?>
                <tr>
                    <td><?php echo $row['id_producto']; ?></td>
                    <td><?php echo $row['nombre']; ?></td>
                    <td><?php echo $row['descripcion']; ?></td>
                    <td><?php echo $row['precio']; ?></td>
                    <td><img src="<?php echo $row['imagen']; ?>" alt="<?php echo $row['nombre']; ?>" width="100"></td>
                    <td>
                        <a href="editar.php?id_producto=<?php echo $row['id_producto']; ?>">Editar</a>
                        <a href="eliminar.php?id_producto=<?php echo $row['id_producto']; ?>" onclick="return confirm('¿Estás seguro de que deseas eliminar este producto?');">Eliminar</a>
                        <?php
                            /*                   
                            <td>
                              <form action="editar.php" method="post" style="display:inline;">
                                <input type="hidden" name="id_producto" value="<?php echo $row['id_producto']; ?>">
                                <button type="submit">Editar</button>
                              </form>
                              
                              <form action="eliminar.php" method="post" style="display:inline;" onsubmit="return confirm('¿Estás seguro?');">
                                <input type="hidden" name="id_producto" value="<?php echo $row['id_producto']; ?>">
                                <button type="submit">Eliminar</button>
                              </form>
                            </td>
                            */
                        ?>
                    </td>
                </tr>
            <?php endwhile; ?>
        <?php else: ?>
            <tr>
                <td colspan="6">No hay productos disponibles.</td>
            </tr>
        <?php endif; ?>
    </table>
</body>
</html>
<?php
$conexion->close();
?>