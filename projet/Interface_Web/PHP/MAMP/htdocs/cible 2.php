<?php session_start();
$_SESSION['MarketID'] = $_POST["loginID"];
$_SESSION["password"] = $_POST["loginPW"];

try {
	$db = new PDO('mysql:host=localhost;dbname=foodgps_db;charset=utf8','root','root'); //password will be poo4Zaec5e
}
catch (Exception $e) {
	die('Erreur : '. $e->getMessage());
} ?>
<html>
<style>
    table, th, td {
    border: 1px solid black;
    }
</style>
<head> <title> E-Pokamp Administrative Service </title>
     <link rel="stylesheet" href="style.css" media="screen" type="text/css" />
</head>

<body>
	<?php
	if (isset ($_SESSION['password'])AND isset($_SESSION['MarketID'])){
		?>
	<p>
    <?php echo $_SESSION['MarketID'] . " est connecté avec " . $_SESSION['password'] ?>
	</p>
	<?php
}
else{
	echo '<p> Accès refusé </p>';
} ?>

<?php
$reponse = $db->query('SELECT * FROM Products WHERE market=0');
?>

<table style="width:100%">
        <tr>
            <th>ID</th>
            <th>Product</th> 
            <th>Price</th>
            <th>Location</th>
            <th>Shelf</th>
        </tr>

<?php
while ($donnees = $reponse->fetch()) {
?>
        <tr>
            <td><?php echo $donnees['product_id']; ?></td>
            <td><?php echo $donnees['label']; ?></td>
            <td><?php echo $donnees['price']; ?></td>
            <td><?php echo $donnees['x']; ?>, <?php echo $donnees['y']; ?></td>
            <td><?php echo $donnees['z']; ?></td>
        </tr>

<?php
}
$reponse->closeCursor(); // Termine le traitement de la requête
?>
</table>
</body>
</html>