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

	case "get_all_products":
                $req = "SELECT AllProductsRef.name, AllProductsRef.Image, ProductsInMarkets.price, AllProductsRef.description FROM AllProductsRef JOIN ProductsInMarkets ON AllProductsRef.product_barcode=ProductsInMarkets.barcode WHERE ProductsInMarkets.market=".$db->quote($_POST['market_id']);
                $response = $db->query($req);
                $array = array();
                while($row = $response->fetch()) {
                        array_push($array, array("name" => $row["name"], "img_url" => $row["Image"], "price" => $row["price"], "description" => $row["description"]));
                }

                $json_result = json_encode($array);
                print($json_result);
		break;

        case "get_all_markets":
                $req = "SELECT market_id, name, open_hours, close_hours, logo FROM Markets";
                $response = $db->query($req);
                $array = array();
                while($row = $response->fetch()) {
                        array_push($array, array("market_id" => $row["market_id"], "name" => $row["name"], "open_hours" => $row["open_hours"], "close_hours" => $row["close_hours"], "logo" => $row["logo"]));
                }

                $json_result = json_encode($array);
                print($json_result);
                break;

        case "get_friend_lists":
                $user_id = $_POST['user_id'];
                $friend_id = $_POST['friend_id'];
                $dir_name = "/var/www/html/data/lists/$friend_id/";
                $dir = opendir($dir_name);

                $list_array = array();
                while($file = readdir($dir)) {
                        if($file != '.' && $file != '..') {
                                $array = json_decode(file_get_contents($dir_name.$file),true);
                                if(in_array("*",$array['shared']) || in_array($user_id,$array['shared'])) {
                                        array_push($list_array, $array["list"]);
                                }
                        }
                }

                print(json_encode($list_array));
                closedir($dir);
                break;

	case "get_user_lists":
		$user_id = $_POST['user_id'];
		$dir_name = "/var/www/html/data/lists/$user_id/";
		$dir = opendir($dir_name);

		$list_array = array();
		while($file = readdir($dir)) {
			if($file != '.' && $file != '..') {
				$array = json_decode(file_get_contents($dir_name.$file), true);
				array_push($list_array, $array['list']);
			}
		}

		print(json_encode($list_array));
		closedir($dir);
		break;

	}
}
?>
