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

	        case "get_all_products":
                $req = "SELECT AllProductsRef.name, AllProductsRef.Image, ProductsInMarkets.price, AllProductsRef.description FROM AllProductsRef JOIN ProductsInMarkets ON AllProductsRef.product_barcode=ProductsInMarkets.barcode WHERE ProductsInMarkets.market=".$db->quote($_POST['market_id']);
                $response = $db->query($req);
                $array = array();
                $i=0;
                while($row = $response->fetch()) {
                        array_push($array, array("name" => $row["name"], "img_url" => $row["Image"], "price" => $row["price"], "description" => $row["description"]));
                }

                $json_result = json_encode($array);
                print($json_result);
                break;
}
?>
