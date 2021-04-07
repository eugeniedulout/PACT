<!DOCTYPE html >
<html>
<head>
	<title> Bienvenue sur E-POKAMP ! </title>
</head>
<body>
	Veuillez vous connecter à votre espace administrateur.
	<?php 

//  Récupération de l'utilisateur et de son pass hashé
$req = $bdd->prepare('SELECT id, pass FROM membres WHERE pseudo = :pseudo');
$req->execute(array(
    'pseudo' => $pseudo));
$resultat = $req->fetch();

// Comparaison du pass envoyé via le formulaire avec la base
$isPasswordCorrect = password_verify($_POST['pass'], $resultat['pass']);

if (!$resultat)
{
    echo 'Mauvais identifiant ou mot de passe !';
}
else
{
    if ($isPasswordCorrect) {
        session_start();
        $_SESSION['id'] = $resultat['id'];
        $_SESSION['pseudo'] = $pseudo;
        echo 'Bienvenue dans votre espace administrateur.';
    }
    else {
        echo 'Mauvais identifiant ou mot de passe !';
    }
}
?>
