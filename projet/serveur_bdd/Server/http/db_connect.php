<?php
try {
	$db = new PDO('mysql:host=localhost;dbname=foodgps_db;charset=utf8','root','poo4Zaec5e');
}
catch (Exception $e) {
	die('Erreur : '. $e->getMessage());
}
 ?>

