<?php
$hostname = "x";
$username = "x";
$password = "x";
$db = "x";
$port = "x";

$dbconnect=mysqli_connect($hostname,$username,$password,$db,$port);

if ($dbconnect->connect_error) {
    die("Database connection failed: " . $dbconnect->connect_error);
}

$angebot_id = $_GET['angebot_id'];
$benutzername = $_GET['benutzername']; 


$statement = $dbconnect->prepare("SELECT favorisiert FROM angebote WHERE id_a = ?");
$statement->bind_param("s", $angebot_id);
$statement->execute();
$result = $statement->get_result();
$row = $result->fetch_assoc();
$favorisiertDatenbank = $row['favorisiert']; 

if ($favorisiertDatenbank == null) {
    $favorisiertNeu = $benutzername; 
} else {
    $favorisiertNeu = $favorisiertDatenbank . "," . $benutzername; 
}

$statement2 = $dbconnect->prepare("UPDATE angebote SET favorisiert = ? WHERE id_a = ?");
$statement2->bind_param("ss", $favorisiertNeu, $angebot_id);
$statement2->execute();

echo "done";

mysqli_close($dbconnect);
?> 