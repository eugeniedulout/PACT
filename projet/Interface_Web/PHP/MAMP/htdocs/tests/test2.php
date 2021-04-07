<!DOCTYPE html>
<html>
    <head>
        <title>Ceci est une page de test avec des balises PHP</title>
        <meta charset="utf-8" />
    </head>
    <body>
        <h2>Page de test</h2>
        
        <p>
            Cette page contient du code HTML avec des balises PHP.<br />
            <?php /* Insérer du code PHP ici */ ?>
            Voici quelques petits tests :
        </p>
        
        <ul>
        <li style="color: blue;">Texte en bleu</li>
        <li style="color: red;">Texte en rouge</li>
        <li style="color: green;">Texte en vert</li>
        <?php echo "Ceci est du texte"; ?>
        <?php echo "Ceci est du <strong>texte</strong>"; ?>
        </ul>
        
        <a href="test2.php?nom=Waungerz&amp;prenom=Lisao">Dis-moi bonjour !</a>
        <p>Bonjour <?php echo $_GET['prenom']; ?> !</p>
        <p>Bonjour <?php echo $_GET['prenom'] . ' ' . $_GET['nom']; ?> !</p>
    </body>
</html>
