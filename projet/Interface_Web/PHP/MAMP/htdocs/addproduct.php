<?php session_start();
try {
	$db = new PDO('mysql:host=localhost;dbname=foodgps_db;charset=utf8','root','root'); //password will be poo4Zaec5e
}
catch (Exception $e) {
	die('Erreur : '. $e->getMessage());
}
?>

<html>

    <head><title> E-Pokamp Administrative Service </title></head>

<body>
    <p>
        Création d'article (WIP)
    </p>
</body>
</html>