<?php session_start();
try {
	$db = new PDO('mysql:host=localhost;dbname=foodgps_db;charset=utf8','root','root'); //password will be poo4Zaec5e
}
catch (Exception $e) {
	die('Erreur : '. $e->getMessage());
}
if (isset($_POST["label"])){
    $query = "INSERT INTO `products` (`label`, `market`, `price`, `quality`, `x`, `y`, `z`) 
    VALUES ('" . $_POST["label"] . "', '" . $_SESSION["MarketID"] . "', '" . $_POST["price"] . "', '0', '" . $_POST["x"] . "', '" . $_POST["y"] . "', '" . $_POST["z"] . "');";
    echo $query;
    $db->query($query);
    header('Location: cible.php');
}
?>

<html>

    <head>
        <title> E-Pokamp Administrative Service </title>
        <link rel="stylesheet" href="Styleprincipal.css" media="screen" type="text/css" />
    </head>

<body>
    <p>
        Ajouter un produit
    </p>
    <form method="post" action="addproduct.php">
        <p>            
            Label : <input type="text" name="label"/></br>
            Price (cents) : <input type="number" name="price"/></br>
            Location X coordinate : <input type="number" step="0.01" name="x"/></br>
            Location Y coordinate : <input type="number" step="0.01" name="y"/></br>
            Shelf : <input type="number" name="z"/></br>
            <input type="submit" value="Confirm"/>
        </p>
    </form>
</body>
</html>