<?php


$bdd = new PDO('mysql:host=localhost;dbname=Membres;charset=utf8', 'root', 'root');
?>
$reponse = $bdd->query('SELECT * FROM membres');
while ($donnees = $reponse->fetch())
{
?>
    <p>
    <strong>Identifiant</strong> : <?php echo $donnees['username']; ?></p>
}