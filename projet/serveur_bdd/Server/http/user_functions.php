<?php
include("db_connect.php");
if(isset($_POST['action'])) {
        switch($_POST['action']) {
        case "get_username":
                $response = $db->query("SELECT firstname, lastname FROM Users WHERE id=".$_POST['id']);
                $data = $response->fetch();
                if($data != null) {
                        $answer = ['ok' => true, 'username' => $data['username'] . " " . $data['lastname']];
                }
                $json_result = json_encode($answer);
                print($json_result);
                break;   

	case "connect":
		$response = $db->query("SELECT pass FROM Users WHERE mail=" . $db->quote($_POST['mail']));
		$data = $response->fetch();
		$answer = ['valid' => 'false'];
		if ($data != null) {
			if(password_verify($_POST['password'], $data['password'])) {
				$answer['valid'] = 'true';
			}
		}
		$json_result = json_encode($answer);
		print($json_result);
		break;
	}
}
?>
