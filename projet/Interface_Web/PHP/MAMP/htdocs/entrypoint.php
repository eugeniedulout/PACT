<?php session_start();
$_SESSION['login'] = 'post';
$_SESSION['nom'] = 'Dupont';
$_SESSION['age'] = 24;
?>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>E-POKAMP Administrative Service</title>
    </head>
    <body>
    <form method="post" action="cible.php">
        <p>            
            Identifiant : <input type="text" name="loginID"/></br>
            Mot de passe : <input type="password" name="loginPW"/></br>
            <input type="submit" value="Confirm" />
        </p>
    </form>
        

    </body>
</html>