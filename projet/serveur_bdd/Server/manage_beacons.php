<!DOCTYPE html>

<?php
try {
	$db = new PDO('mysql:host=localhost;dbname=foodgps_db;charset=utf8','root','poo4Zaec5e');
} catch (Exception $e) {
	die('Erreur : ' . $e->getMessage());
}

if(isset($_POST['id']) && isset($_POST['uuid']) && isset($_POST['x']) && isset($_POST['y'])) {

	$x = $db->quote($_POST['x']);
	$y = $db->quote($_POST['y']);
	$uuid = $db->quote($_POST['uuid']);
	$req = "UPDATE beaconsPositions SET x=$x, y=$y WHERE uuid=$uuid";
	$db->exec($req);
}


$req = "SELECT * FROM beaconsPositions";
$data = $db->query($req);
$form = "";
while($row = $data->fetch()) {
	$id = $row['id'];
	$market = $row['market_id'];
	$uuid = $row['uuid'];
	$x = $row['x'];
	$y = $row['y'];

	$form .= "<form action='' method='POST'><input type='hidden' name='id' value='$id' /><input type='hidden' name ='uuid' value='$uuid' />market: $market -- $uuid -> x:<input type='text' name='x' value='$x' /> y:<input type='text' name='y' value='$y' /><input type='submit' value='Modifier'/></form>";
}
$form .= "</form>";
?>

<head>
<meta charset='utf-8' />
<title>Manage beacons</title>
</head>

<body>
	<h1>Manage your beacons</h1>
<?php print($form); ?>
</body>
