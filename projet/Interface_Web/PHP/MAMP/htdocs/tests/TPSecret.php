<!DOCTYPE html>
<html>
<head> <title> Page secrète </title>
</head>
<body>
	<?php
	if (isset ($_POST['motdepasse'])AND $_POST['motdepasse']=="kangourou"){
		?>
	<p>
		Bienvenue sur le site !
	</p>
	<?php
}
else{
	echo '<p> Accès refusé </p>';
}
?>
</body>
</html>