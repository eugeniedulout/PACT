

   

<?php 
try {
    $bdd = new PDO('mysql:host=localhost;dbname=utilisateur;charset=utf8','root','root');
}
  catch (Exception $e) {
    die('Erreur : '. $e->getMessage());
} 

$username = htmlspecialchars($_POST['username']); 
$password = htmlspecialchars($_POST['password']);
//  Récupération de l'utilisateur et de son pass hashé
$req = $bdd->prepare('SELECT password FROM utilisateur WHERE username = :username');
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
        header('Location: principale.php');
    }
    else {
           header('Location: login.php?erreur=1'); // utilisateur ou mot de passe incorrect
        }
}s