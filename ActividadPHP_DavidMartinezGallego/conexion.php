<?php
$servername = "localhost:3307";
$username = "root";
$password = "";
$dbname = "productosdb";

// Create connection
$conexion = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conexion->connect_error) {
    die("Connection failed: " . $conexion->connect_error);
}
?>