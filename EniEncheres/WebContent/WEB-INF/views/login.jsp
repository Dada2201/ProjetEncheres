<!doctype html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <title>Titre de la page</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="script.js"></script>
</head>

<body>
    <header>
        <div class="container">
            <div class="row justify-content-between">
                <p>ENI-Encheres</p>
            </div>
        </div>
    </header>
    <div class="container">
        <form class="col-12" action="login" method="post">
            <div class="form-group row mx-auto col-6">
                <label class="col-4">Identifiant :</label>
                <input type="text" class="form-control col-8" placeholder="Pseudo" name="pseudo" required>
            </div>
            <div class="form-group row mx-auto col-6">
                <label class="col-4">Mot de passe :</label>
                <input type="password" class="form-control col-8" placeholder="Mot de passe" name="motDePasse" required>
            </div>
            <div class="row mx-auto col-6">
                <button type="submit" class="btn btn-primary mr-auto ">Connexion</button>
                <div class="mr-auto">
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" name="souvenir">
                        <label class="form-check-label">Se souvenir de moi</label>
                    </div>
                    <a href="">Mot de passe oubli�</a>
                </div>
            </div>
        </form>
    </div>
    <div class="mt-5 row justify-content-center">
        <button type="submit" class="btn btn-primary">Créer un compte</button>
    </div>
</body>

</html>
