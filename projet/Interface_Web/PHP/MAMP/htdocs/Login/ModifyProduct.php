<?php session_start();
try {
	$db = new PDO('mysql:host=localhost;dbname=foodgps_db;charset=utf8','root','poo4Zaec5e'); //password will be poo4Zaec5e
}
catch (Exception $e) {
	die('Erreur : '. $e->getMessage());
}


$item_id = $_GET["Modify"];

if (isset($_POST["label"])){
    $query = " UPDATE ProductsInMarkets 
               SET label='" . $_POST["label"] . "', price='" . $_POST["price"] . "', x='" . $_POST["x"] . "', y='" . $_POST["y"] . "', z='" . $_POST["z"] . "', barcode='" . $_POST["barcode"] . "' WHERE product_id='" .  $_POST["product_id"] . "';";
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
        Modifier un produit
    </p>
    <?php
    $query = 'SELECT * FROM ProductsInMarkets WHERE product_id=' . $item_id . ';';
    $product = $db->query($query)->fetch();

    ?>
    <form method="post" action="ModifyProduct.php">
        <p>            
            Label : <input type="text" name="label" value='<?php echo $product["label"] ?>'/></br>
            Price (cents): <input type="number" name="price" value='<?php echo $product["price"] ?>'/></br>
            Location X coordinate : <input type="number" step="0.01" name="x" value='<?php echo $product["x"] ?>'/></br>
            Location Y coordinate : <input type="number" step="0.01" name="y" value='<?php echo $product["y"] ?>'/></br>
            Shelf : <input type="number" name="z" value='<?php echo $product["z"] ?>'/></br>
            Barcode : <input type="number" name="barcode" value='<?php echo $product["barcode"]?>'/></br>
            <input type="hidden" name="product_id" value='<?php echo $item_id?>'/>
            <input type="submit" value="Confirm"/>
        </p>
    </form>
</body>
</html>