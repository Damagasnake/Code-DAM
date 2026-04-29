<?php
// Declaración de variables (sensibles a mayúsculas/minúsculas)
$nombre = "Juan"; 
$edad = 25;

// Constantes
define("PI", 3.1416);
const PAIS = "España";

// Mostrar datos
echo "Hola $nombre"; 

// Escalares
$entero = 10;
$decimal = 3.14;
$booleano = true;
$booleano = false;
var_dump($booleano);// para mostrar el false
$cadena = "Texto";

// Arrays
$indexado = ["Manzana", "Banana"]; // Acceso: $indexado[0]
$asociativo = ["nombre" => "Ana", "edad" => 25]; // Acceso: $asociativo["nombre"]
echo $asociativo["nombre"];
echo $indexado[0];
/*
OPERADORES
Aritméticos: +, -, *, /, % (módulo), ** (exponenciación).
Asignación: =, +=, -=, /=, *=.
Comparación: == (igual), === (idéntico: valor y tipo), !=, <, >, <=, >=.
Lógicos: && (AND), || (OR), ! (NOT), xor.
Incremento: ++$x (pre), $x++ (post). 
String: "Hola" . "Mundo"
*/

// Condicionales
if ($x < 5) {
    // ...
} elseif ($x == 10) {
    // ...
} else {
    // ...
}

// Switch
switch ($variable) {
    case 'valor':
        break;
    default:
        // ...
}

// Bucles
for ($i = 0; $i < 5; $i++) { /* ... */ }
foreach ($array as $valor) { /* ... */ }
while ($condicion) { /* ... */ }

//include
// include archivo.php;
// require archivo.php;


function nombreFuncion($param1, $param2 = "defecto") {
    return $param1 + $param2;
}

// Paso por referencia (modifica la variable original)
function incrementar(&$num) {
    $num++;
}

//forzar tipado en funciones
function suma(int $a, int $b): int {
    return $a + $b;
}

// Conexión
$conexion = new mysqli("localhost", "usuario", "pass", "bd");

// Sentencia preparada (Recomendado para evitar inyecciones SQL)
$stmt = $conexion->prepare("INSERT INTO tabla (col1, col2) VALUES (?, ?)");
$stmt->bind_param("ss", $valor1, $valor2); // "s" = string, "i" = int
$stmt->execute();

$stmt->close();
$conexion->close();

/*
$_GET: Datos enviados por URL.
$_POST: Datos enviados ocultos (formularios).
$_REQUEST: Combinación de GET y POST.
$_SESSION, $_COOKIE: Manejo de estado.
$_FILES: Gestión de subida de archivos.
$_ENV, $GLOBALS: Variables de entorno del sistema, variables globales.
*/

//get 
// Ejemplo de acceso seguro
if (isset($_GET['nombre'])) {
    $nombre = htmlspecialchars($_GET['nombre']); // Sanitización básica
    echo "Hola, " . $nombre;
} else {
    echo "No se ha proporcionado un nombre.";
}

/*
<form method="post" action="procesar.php">
    <label for="nombre">Nombre:</label>
    <input type="text" id="nombre" name="nombre">
    <br><br>
    <input type="submit" value="Enviar">
</form>
*/

// Comprobamos si el formulario ha sido enviado usando POST
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    
    // Verificamos si existe el campo 'nombre'
    if (isset($_POST['nombre']) && !empty($_POST['nombre'])) {
        // Sanitización básica para evitar código malicioso
        $nombre = htmlspecialchars($_POST['nombre']);
        echo "Hola, " . $nombre . ". Los datos se han recibido por POST.";
    } else {
        echo "No se ha proporcionado un nombre en el formulario.";
    }
} else {
    echo "Acceso no autorizado.";
}

/*
en bind param
i	integer	Números enteros (ej. 1, -5, 100).
d	double	Números con decimales (ej. 3.14, -0.01).
s	string	Cadenas de texto, fechas, o cualquier dato que se trate como texto.
b	blob	Datos binarios pesados, como imágenes o archivos.
*/
?>

