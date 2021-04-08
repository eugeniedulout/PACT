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
    header("Location : cible.php");
}
?>

<html>

    <head><title> E-Pokamp Administrative Service </title></head>

<body>
    <p>
        Création d'article (WIP)
    </p>
    <form method="post" action="addproduct.php">
        <p>            
            Label : <input type="text" name="label"/></br>
            Price : <input type="text" name="price"/></br>
            Location X coordinate : <input type="text" name="x"/></br>
            Location Y coordinate : <input type="text" name="y"/></br>
            Shelf : <input type="text" name="z"/></br>
            <input type="submit" value="Confirm"/>
        </p>
    </form>
</body>
</html>