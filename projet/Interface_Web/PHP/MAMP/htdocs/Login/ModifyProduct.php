<?php session_start();
try {
	$db = new PDO('mysql:host=localhost;dbname=foodgps_db;charset=utf8','root','root'); //password will be poo4Zaec5e
}
catch (Exception $e) {
	die('Erreur : '. $e->getMessage());
}


$item_id = $_GET["Modify"];

if (isset($_POST["label"])){
    $query = " UPDATE products 
               SET label='" . $_POST["label"] . "', price='" . $_POST["price"] . "', x='" . $_POST["x"] . "', y='" . $_POST["y"] . "', z='" . $_POST["z"] . "' WHERE product_id='" .  $_POST["product_id"] . "';";
    echo $query;
    $db->query($query);
    header('Location: cible.php');
}
?>

<html>

    <head><title> E-Pokamp Administrative Service </title></head>

<body>
    <p>
        Modification d'article
    </p>
    <?php
    $query = 'SELECT * FROM Products WHERE product_id=' . $item_id . ';';
    $product = $db->query($query)->fetch();

    ?>
    <form method="post" action="ModifyProduct.php">
        <p>            
            Label : <input type="text" name="label" value='<?php echo $product["label"] ?>'/></br>
            Price : <input type="text" name="price" value='<?php echo $product["price"] ?>'/></br>
            Location X coordinate : <input type="text" name="x" value='<?php echo $product["x"] ?>'/></br>
            Location Y coordinate : <input type="text" name="y" value='<?php echo $product["y"] ?>'/></br>
            Shelf : <input type="text" name="z" value='<?php echo $product["z"] ?>'/></br>
            <input type="hidden" name="product_id" value='<?php echo $item_id?>'/>
            <input type="submit" value="Confirm"/>
        </p>
    </form>
</body>
</html>