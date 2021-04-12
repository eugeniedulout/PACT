

   

<?php 
try {
    $bdd = new PDO('mysql:host=localhost;dbname=foodgps_db;charset=utf8','root','poo4Zaec5e');
}
  catch (Exception $e) {
    die('Erreur : '. $e->getMessage());
} 

$username = htmlspecialchars($_POST['username']); 
$password = htmlspecialchars($_POST['password']);
//  Récupération de l'utilisateur et de son pass hashé
$req = $bdd->prepare('SELECT password, market_id FROM AdminUsers WHERE username = :username');
$req->execute(array(
    'username' => $username));
$resultat = $req->fetch();

// Comparaison du pass envoyé via le formulaire avec la base
$isPasswordCorrect = password_verify($_POST['password'], $resultat['password']);

if (!$resultat)
{
    echo 'Mauvais identifiant ou mot de passe !';
}
else
{
    if ($isPasswordCorrect) {
        session_start();
        $_SESSION['username'] = $username;
        $_SESSION['MarketID'] = (int) $resultat['market_id'];
        //echo $_SESSION['MarketID'] . "from" . $resultat['market_id'];
        header('Location: cible.php');
    }
    else {
           header('Location: login.php?erreur=1'); // utilisateur ou mot de passe incorrect
        }
} ?>