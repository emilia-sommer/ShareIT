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

$titel = $_GET['neues_angebot_titel'];
$beschreibung = $_GET['neues_angebot_beschreibung'];
$kategorie = $_GET['neues_angebot_kategorie'];
$kategorie = (int)$kategorie; 

$benutzer = $_GET['neues_angebot_benutzer'];
$benutzer = (int)$benutzer; 

$statement = $dbconnect->prepare("INSERT INTO angebote (titel, beschreibung, kategorie_id, benutzer_id) VALUES (?, ?, ?, ?)");

$statement->bind_param("ssii", $titel, $beschreibung, $kategorie, $benutzer); 
if ($statement->execute()) {
    echo "true";
} else {
    echo "Error: " . $statement->error;
}
mysqli_close($dbconnect);
?>