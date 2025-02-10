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

$benutzernameEingabe = $_GET['benutzername'];
$passwortEingabe = $_GET['passwort'];

$login_succeeded = "false"; 

$statement = $dbconnect->prepare("SELECT passwort, id_b, vorname, nachname, klasse FROM benutzer WHERE benutzername = ?"); 
$statement->bind_param("s", $benutzernameEingabe); 
$statement->execute(); 
$result = $statement->get_result(); 
$row = $result->fetch_assoc(); 
  
$passwortDatenbank = $row['passwort']; 
$benutzer_id = $row['id_b']; 
$vorname = $row['vorname']; 
$nachname = $row['nachname'];
$klasse = $row['klasse']; 

if($passwortEingabe == $passwortDatenbank) {
    $login_succeeded=$benutzernameEingabe . time(); 
    $login_succeeded=hash("sha3-256", $login_succeeded);
    
    $statement = $dbconnect->prepare("UPDATE benutzer SET login = ? WHERE benutzername = ?");
    $statement->bind_param("ss", $login_succeeded, $benutzernameEingabe);
    $statement->execute();    
    
}else{
    $login_succeeded = "false"; 
}

if($benutzernameEingabe == null){
    $login_succeeded = "false";
}

if($passwortEingabe == null){
    $login_succeeded = "false";
}

echo $login_succeeded; 
if ($login_succeeded != "false"){
    echo "TRENNUNG_DER_WERTE";
    echo $benutzer_id; 
    echo "TRENNUNG_DER_WERTE";
    echo $vorname; 
    echo "TRENNUNG_DER_WERTE";
    echo $nachname; 
    echo "TRENNUNG_DER_WERTE";
    echo $klasse; 
}

mysqli_close($dbconnect);
?>