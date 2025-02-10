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

$benutzername = $_GET['benutzername'];
$sharedPreferences = $_GET['sharedPreferences'];

$loginValuesIdentical  = "false";

$statement = $dbconnect->prepare("SELECT login FROM benutzer WHERE benutzername = ?");
$statement->bind_param("s", $benutzername);
$statement->execute();
$result = $statement->get_result();
$row = $result->fetch_assoc();

$loginDatenbank = $row['login']; 

if($sharedPreferences == $loginDatenbank){
    $loginValuesIdentical = "true"; 
} else {
    $loginValuesIdentical = "false"; 
    
    $nullValue = ""; 
    
    $statement = $dbconnect->prepare("UPDATE benutzer SET login = ? WHERE benutzername = ?");
    $statement->bind_param("ss", $nullValue, $benutzername);
    $statement->execute();
}

echo $loginValuesIdentical;

mysqli_close($dbconnect);
?>