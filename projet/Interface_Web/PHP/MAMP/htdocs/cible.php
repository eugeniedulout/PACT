<?php session_start();
$_SESSION['MarketID'] = $_POST["loginID"];
$_SESSION["password"] = $_POST["loginPW"];
?>
<html>
<head> <title> Page secrète </title>
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
}
?>
</body>
</html>