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

$titel = $_GET['titel_betroffenes_angebot'];
$beschreibung = $_GET['beschreibung_betroffenes_angebot'];
$benutzer = $_GET['benutzer_betroffenes_angebot'];
$benutzer = (int)$benutzer; 

$statement = $dbconnect->prepare("DELETE FROM angebote WHERE titel = ? AND beschreibung = ? AND benutzer_id = ?");

$statement->bind_param("ssi", $titel, $beschreibung, $benutzer); 
if ($statement->execute()) {
    echo "true";
} else {
    echo "Error: " . $statement->error;
}
mysqli_close($dbconnect);
?>