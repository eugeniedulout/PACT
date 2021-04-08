<?php session_start();
if (isset($_POST["loginID"])){
$_SESSION['MarketID'] = $_POST["loginID"]; //WIP
$_SESSION["password"] = $_POST["loginPW"]; //WIP
}
try {
	$db = new PDO('mysql:host=localhost;dbname=foodgps_db;charset=utf8','root','root'); //password will be poo4Zaec5e
}
catch (Exception $e) {
	die('Erreur : '. $e->getMessage());
}
if (isset ($_GET['Delete'])){
    $query = 'SELECT * FROM Products WHERE (market=' . $_SESSION["MarketID"] . ' AND product_id=' . $_GET['Delete'] . ")";
    echo $query;
    $reponse = $db->query($query);
    if  ($donnees = $reponse->fetch()){
        $query = 'DELETE FROM Products WHERE (market=' . $_SESSION["MarketID"] . ' AND product_id=' . $_GET['Delete'] . ")";
        echo $query;
        $db->query($query);
    }
    $reponse->closeCursor(); // Termine le traitement de la requête
}

?>

<html>
    <style>
        table, th, td {
        border: 1px solid black;
        }
    </style>

    <head><title> E-Pokamp Administrative Service </title></head>

<body>

	<?php
	if (isset ($_SESSION['password'])AND isset($_SESSION['MarketID'])){
		echo "<p>" . $_SESSION['MarketID'] . " est connecté avec " . $_SESSION['password'] . "</p>";
    } else {
	echo '<p> Accès refusé </p>';
    } 

    $reponse = $db->query('SELECT * FROM Products WHERE market=' . $_SESSION["MarketID"]);
    ?>

    <table style="width:100%">
       <tr>
            <th>ID</th>
            <th>Product</th> 
            <th>Price</th>
            <th>Location</th>
            <th>Shelf</th>
            <th>Modify</th>
        </tr>

    <?php
    while ($donnees = $reponse->fetch()) {
    ?>
            <tr>
                <td><?php echo $donnees['product_id']; ?></td>
                <td><?php echo $donnees['label']; ?></td>
                <td><?php echo $donnees['price']/100 . "€"; ?></td>
                <td><?php echo $donnees['x']; ?>, <?php echo $donnees['y']; ?></td>
                <td><?php echo $donnees['z']; ?></td>
                <?php $ModifyLink="cible.php?Modify=" . $donnees['product_id']; ?>
                <?php $DeleteLink="cible.php?Delete=" . $donnees['product_id']; ?>
                <td><a href=<?php echo $ModifyLink; ?>>Modify</a> / <a href=<?php echo $DeleteLink; ?>>Delete</a></td>
            </tr>
    <?php
    }
    $reponse->closeCursor(); // Termine le traitement de la requête
    ?>
    </table>
    <a href='addproduct.php'>Add a Product</a>
</body>
</html>