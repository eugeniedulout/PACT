<?php
//connexion à la base de donnée
include("db_connect.php");

//On vérifie qu'une action a bien été demandée
if(isset($_POST['action'])) {

	//On effectue le traitement selon l'action demandée
	switch($_POST['action']) {

	//obtenir le nom d'utilisateur	
	case "get_username":
		$response = $db->query("SELECT firstname, lastname FROM Users WHERE id=".$_POST['id']);
		$data = $response->fetch();
		if($data != null)
			print("true%".$data['firstname']." ".$data['lastname']);
		else
			print("false");
		break;
	}

}


?>
