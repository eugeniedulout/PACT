<?php

include("db_connect.php");
if(isset($_POST['action'])) {
	switch($_POST['action']) {
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
