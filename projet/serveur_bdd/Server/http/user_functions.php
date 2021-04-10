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
		$response = $db->query("SELECT * FROM Users WHERE mail=" . $db->quote($_POST['mail']));
		$data = $response->fetch();
		$answer = ['valid' => false];
		if ($data != null) {
			if(password_verify($_POST['password'], $data['password'])) {
				$answer['valid'] = true;
				$answer['user'] = array("id" => $data['id'], "mail" => $data['mail'], "firstname" => $data['firstname'], "lastname" => $data['lastname']);
			}
		}
		$json_result = json_encode($answer);
		print($json_result);
		break;

	case "get_all_products":
                $req = "SELECT AllProductsRef.name, AllProductsRef.Image, ProductsInMarkets.price, AllProductsRef.description, x, y, z FROM AllProductsRef JOIN ProductsInMarkets ON AllProductsRef.product_barcode=ProductsInMarkets.barcode WHERE ProductsInMarkets.market=".$db->quote($_POST['market_id']);
                $response = $db->query($req);
                $array = array();
                while($row = $response->fetch()) {
                        array_push($array, array("name" => $row["name"], "img_url" => $row["Image"], "price" => $row["price"], "description" => $row["description"], "x" => $row['x'], "y" => $row['y'], "z" => $row['z']));
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

        case "add_new_list":
                $user_id = $_POST['user_id'];
                $list_name = $_POST['list_name'];
                $dir_name = "/var/www/html/data/lists/$user_id/";
                if(!file_exists($dir_name)) {
                        mkdir($dir_name, 0755, true);
                }
                $list_json = $_POST['list'];
                file_put_contents($dir_name.$list_name.'.json',$list_json);
		break;

	case "get_market_offers":
		$market_id = $_POST['market_id'];
		$req = "SELECT ProductsInMarkets.label, ProductsInMarkets.price, Promotions.new_price, Promotions.expire_date, AllProductsRef.Image, AllProductsRef.description FROM (ProductsInMarkets JOIN Promotions ON ProductsInMarkets.product_id = Promotions.product_id) JOIN AllProductsRef ON ProductsInMarkets.barcode = AllProductsRef.product_barcode WHERE ProductsInMarkets.market=$market_id";

		$data = $db->query($req);
		$products = array();
		while($row = $data->fetch()) {
			array_push($products, array("name" => $row["label"], "img_url" => $row['Image'], "price" => $row['price'], "description" => $row['description'], "new_price" => $row['new_price'], "expiration_date" => $row['expire_date']));
		}

		$json_result = json_encode($products);
		print($json_result);
		break;

	case 'get_user_friends':
		$user_id = $_POST['user_id'];
		$req = "SELECT friends FROM Users WHERE id=$user_id";
		$data = $db->query($req);
		print($data->fetch()['friends']);
		break;


	case "add_new_recipe":
		$user_id = $_POST['user_id'];
		$name = $_POST['recipe_name'];
		$json_recipe = $_POST['recipe'];
		$dir_name = "/var/www/html/data/recipes/$user_id/";

		if(!file_exists($dir_name)) {
			mkdir($dir_name, 0755, true);
		}
		file_put_contents($dir_name.$name.'.json',$json_recipe);
		break;
		

	case "get_user_recipes":
		$user_id = $_POST['user_id'];
		$dir_name = "/var/www/html/data/recipes/$user_id/";
		$dir = opendir($dir_name);

		$recipe_list = array();
		while($file = readdir($dir)) {
			if($file != '.' && $file != '..') {
				$recipe = json_decode(file_get_contents($dir_name.$file), true);
				array_push($recipe_list, $recipe);
			}
		}
		print(json_encode($recipe_list));
		closedir($dir);
		break;


	case "get_user":
		$user_id = $_POST['user_id'];
		$req = "SELECT mail, firstname, lastname FROM Users WHERE id=$user_id";
		$data = $db->query($req);

		$answer = array('valid' => false);

		if($row = $data->fetch()) {
			$answer['valid'] = true;
			$user = array("id" => $user_id, "mail" => $row['mail'], "firstname" => $row['firstname'], "lastname" => $row['lastname']);
			$answer['user'] = $user;
		}

		print(json_encode($answer));
		break;


	case "sign_up":
		$mail = $db->quote($_POST['mail']);
		$password = $_POST['password'];
		$firstname = $db->quote($_POST['firstname']);
		$lastname = $db->quote($_POST['lastname']);

		$answer = array("valid" => false);

		$req = "SELECT * FROM Users WHERE mail=$mail";
		$data = $db->query($req);
		if(!($data->fetch())) {
			$password_hash = password_hash($password, PASSWORD_DEFAULT);
			$req = "INSERT INTO Users (`mail`, `password`, `firstname`, `lastname`);SELECT LAST_INSERT_ID();";
			$data = $db->exec($req);
			$id = $data->fetch()[0];

			$user = array("id" => $id, "mail" => $_POST['mail'], "firstname" => $_POST['firstname'], "lastname" => $_POST['lastname']);
			$answer['user'] = $user;
		}
		print(json_encode($answer);
		break;


	case "update_password":
		$user_id = $_POST['user_id'];
		$new_pass = $_POST['new_password'];

		$pass_hash = password_hash($new_pass, PASSWORD_DEFAULT);
		$req = "UPDATE Users SET password=$pass_hash WHERE id=$user_id";
		$db->exec($req);
		break;


	case "set_email":
		$user_id = $_POST['user_id'];
		$new_mail = $db->quote($_POST['new_mail']);

		$req = "UPDATE Users SET mail=$new_mail WHERE id=$user_id";
		$db->exec($req);
		break;



	case "add_friend":
		$user_id = $_POST['user_id'];
		$friend_id = $_POST['friend_id'];

		$req = "SELECT friends FROM Users WHERE id=$user_id";
		$data = $db->query($req);
		$json_friends = $data->fetch()['friends'];
		$friends_array = json_decode($json_friends, true);

		if(!in_array($friend_id, $friends_array)) {
			array_push($friends_array, $friend_id);
			$new_json_friends = json_encode($friends_array);
			$req = "UPDATE Users SET friends='$new_json_friends' WHERE id=$user_id";
			$db->exec($req);
		}
		break;
		

	case "send_demand":
		$user_id = $_POST['user_id'];
		$friend_id = $_POST['friend_id'];

		$req = "SELECT friends,demands FROM Users WHERE id=$friend_id";
		$data = $db->query($req);
		$row = $data->fetch();
		$json_friends = $row['friends'];
		$friends_array = json_decode($json_friends, true);

		if(!in_array($friend_id, $friends_array)) {
			$demands = json_decode($row['demands'], true);
			array_push($demands, $user_id);
			$json_demands = json_encode($demands);
			$req = "UPDATE Users SET demands='$json_demands' WHERE id=$friend_id";
			$db->exec($req);
		}
		break;

	}
}
?>
