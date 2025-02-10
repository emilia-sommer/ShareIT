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

$benutzernameSP = $_GET['benutzername'];

$dbconnect->real_query("SELECT id_a, titel, beschreibung, benutzer_id, benutzername, kategorie_id, vorname, nachname,
klasse, favorisiert FROM angebote, benutzer WHERE benutzer_id = id_b");

$result = $dbconnect->use_result();

$daten = "";
foreach ($result as $row) {
    
    $favorisiert = $row['favorisiert'];
    $benutzernameVorhanden = (strpos("," . $favorisiert . ",", "," . $benutzernameSP . ",") !== false);
    if ($benutzernameVorhanden) {
        $favorisiertTrueFalse = "true";
        
        $daten .= $row['titel'];
        $daten .= "§<>§°/";
        $daten .= $row['beschreibung'];
        $daten .= "§<>§°/";
        $daten .= $row['vorname'];
        $daten .= "§<>§°/";
        $daten .= $row['nachname'];
        $daten .= "§<>§°/";
        $daten .= $row['klasse'];
        $daten .= "§<>§°/";
        $daten .= $row['benutzer_id'];
        $daten .= "§<>§°/";
        $daten .= $favorisiertTrueFalse;
        $daten .= "§<>§°/";
        $daten .= $row['benutzername'];
        $daten .= "§<>§°/";
        $daten .= $row['id_a'];
        $daten .= "\n";
    } 
    
}

echo $daten;

mysqli_close($dbconnect);
?>