<?php session_start();
try {
	$db = new PDO('mysql:host=localhost;dbname=foodgps_db;charset=utf8','root','root'); //password will be poo4Zaec5e
}
catch (Exception $e) {
	die('Erreur : '. $e->getMessage());
}
if (isset ($_GET['Delete'])){
    $query = 'SELECT * FROM ProductsInMarkets WHERE (market=' . $_SESSION["MarketID"] . ' AND product_id=' . $_GET['Delete'] . ")";
    //echo $query;
    $reponse = $db->query($query);
    if  ($donnees = $reponse->fetch()){
        $query = 'DELETE FROM ProductsInMarkets WHERE (market=' . $_SESSION["MarketID"] . ' AND product_id=' . $_GET['Delete'] . ")";
        //echo $query;
        $db->query($query);
    }
    $reponse->closeCursor(); // Termine le traitement de la requête
}

?>

<html>
    <style>
        table, th, td {
        font-family: 'helvetica neue', helvetica, arial, sans-serif;
        border: 1px solid white;
        border-collapse: collapse;
        }
    </style>

    <head><title> E-Pokamp Administrative Service </title>
    <link rel="stylesheet" href="Styleprincipal.css" media="screen" type="text/css" /></head>

<body>

	<?php
	if (isset ($_SESSION['username'])AND isset($_SESSION['MarketID'])){
		echo "<p> Bienvenue dans l'espace administrateur de " . $_SESSION['username'] . ". Vous êtes connecté avec l'ID " . $_SESSION['MarketID'] . ". <a href='disconnect.php'>Se déconnecter</a></p>";
    } else {
	echo '<p> Accès refusé </p>';
    } 
    $query = "SELECT * FROM ProductsInMarkets WHERE market='" . $_SESSION["MarketID"] . "';";
    //echo $query;
    $reponse = $db->query($query);
    ?>


    <table style="width:100%">
       <tr>
            <th>ID</th>
            <th>Product</th> 
            <th>Price</th>
            <th>Location</th>
            <th>Shelf</th>
            <th>Barcode</th>
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
                <td><?php echo $donnees['barcode']; ?></td>
                <?php $ModifyLink="ModifyProduct.php?Modify=" . $donnees['product_id']; ?>
                <?php $DeleteLink="cible.php?Delete=" . $donnees['product_id']; ?>
                <td><a href=<?php echo $ModifyLink; ?>>Modify</a> / <a href=<?php echo $DeleteLink; ?>>Delete</a></td>
            </tr>
    <?php
    }
    $reponse->closeCursor(); // Termine le traitement de la requête
    ?>
    </table>
 
    <a href='addproduct.php' class="button">Add a Product</a>
</body>
</html>