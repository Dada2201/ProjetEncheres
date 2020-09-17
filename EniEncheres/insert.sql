insert into utilisateurs (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) values("Masque34","Nalad","Rafael","rafanalad@gmail.com","0231215130","rue du moulin","14000","Caen","81dc9bdb52d04dc20036dbd8313ed055","1000",0);
insert into utilisateurs (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) values("CarlosDu13","Martin","Carl","carlos13@gmail.com","0231215131","rue de la villa","13090","Aix-En-Provence","81dc9bdb52d04dc20036dbd8313ed055","1000",0);
insert into utilisateurs (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) values("Micheline","Halo","Micheline","michmich@gmail.com","0231215132","rue du hein","35000","Rennes","81dc9bdb52d04dc20036dbd8313ed055","1000",0);
insert into utilisateurs (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) values("DumontJ","Dumont","Jean","jd@gmail.com","0231215429","Avenue Dumont","13000","Marseille","81dc9bdb52d04dc20036dbd8313ed055","1000",0);
insert into utilisateurs (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) values("Louisatak","Attac","Louis","LouisAtak@gmail.com","0231215929","rue du moulin","14000","Caen","81dc9bdb52d04dc20036dbd8313ed055","1000",0);
insert into utilisateurs (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) values("Jojo","Lorent","Jordan","jl@gmail.com","0231215129","rue du moulin","14000","Caen","81dc9bdb52d04dc20036dbd8313ed055","1000",0);

select * from articles_vendus

insert into articles_vendus (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie) values("Cafetiere","Elle fait le café","2020-09-10","2020-09-30",30,0,1,16);
insert into articles_vendus (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie) values("Maison 200m","15 chambres","2020-09-10","2020-09-30",200000,0,2,7);
insert into articles_vendus (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie) values("Table en marbre","Etat neuf, seulement qq apéro dessus","2020-09-10","2020-09-30",100,0,3,15);
insert into articles_vendus (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie) values("Canard","5 mois","2020-09-30","2020-10-05",80,0,4,23);
insert into articles_vendus (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie) values("Casque Sony WH-1000XM3","Encore sous garanti","2020-09-10","2020-10-05",150,0,5,13);
insert into articles_vendus (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie) values("Aquarium","Capacité : 30l, les poissons ne sont pas à vendre","2020-09-10","2020-10-05",150,0,1,15);
insert into articles_vendus (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie) values("PC Gamer Full puissance","Encore sous garanti","2020-09-10","2020-10-05",350,0,2,12);
insert into articles_vendus (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie) values("Ventilateur Dyson","3 niveaux de puissance, acheté à Darty en juillet","2020-09-10","2020-10-05",50,0,3,16);
insert into articles_vendus (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie) values("Renault Clio 2005","Ventes pièces à négocier","2020-09-10","2020-10-05",1000,0,4,1);
insert into articles_vendus (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie) values("Peugeot 206+","2000000km, vidange à prévoir fin octobre","2020-09-10","2020-10-05",1500,0,5,1);
insert into articles_vendus (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie) values("2 billets Caen-Toulouse","Tribunes K, match le 12 octobre 2020","2020-09-10","2020-10-05",20,0,1,29);
insert into articles_vendus (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie) values("Pelle","Comme neuve","2020-09-10","2020-10-05",5,0,2,45);
insert into articles_vendus (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie) values("T1 centre Rennes","loué non meublé, dispo le 1er novembre. téléphoner au 0612453678","2020-09-10","2020-10-05",420,0,3,8);

insert into retraits (no_article,rue,code_postal,ville) values(1,"rue des artisans",14000,"Caen");
insert into retraits (no_article,rue,code_postal,ville) values(2,"rue des peintres",14000,"Caen");
insert into retraits (no_article,rue,code_postal,ville) values(3,"rue des bouchers",14000,"Caen");
insert into retraits (no_article,rue,code_postal,ville) values(4,"rue des boulangers",14000,"Caen");
insert into retraits (no_article,rue,code_postal,ville) values(5,"rue des coiffeurs",14000,"Caen");
insert into retraits (no_article,rue,code_postal,ville) values(6,"rue des artistes",14000,"Caen");
insert into retraits (no_article,rue,code_postal,ville) values(7,"rue des docteurs",14000,"Caen");
insert into retraits (no_article,rue,code_postal,ville) values(8,"rue des pompiers",14000,"Caen");
insert into retraits (no_article,rue,code_postal,ville) values(9,"rue des medecins",14000,"Caen");
insert into retraits (no_article,rue,code_postal,ville) values(10,"rue des chanteurs",14000,"Caen");
insert into retraits (no_article,rue,code_postal,ville) values(11,"rue des sportifs",14000,"Caen");
insert into retraits (no_article,rue,code_postal,ville) values(12,"rue des marins",14000,"Caen");
insert into retraits (no_article,rue,code_postal,ville) values(13,"rue de l'or",14000,"Caen");

select * from encheres;

insert into encheres (no_utilisateur,no_article,date_enchere,montant_enchere) values (2,1,"2020-09-16",31);
insert into encheres (no_utilisateur,no_article,date_enchere,montant_enchere) values (3,1,"2020-09-17",40);
insert into encheres (no_utilisateur,no_article,date_enchere,montant_enchere) values (1,3,"2020-09-17",105);
insert into encheres (no_utilisateur,no_article,date_enchere,montant_enchere) values (2,3,"2020-09-17",110);
insert into encheres (no_utilisateur,no_article,date_enchere,montant_enchere) values (1,4,"2020-09-16",85);
insert into encheres (no_utilisateur,no_article,date_enchere,montant_enchere) values (3,5,"2020-09-17",155);
insert into encheres (no_utilisateur,no_article,date_enchere,montant_enchere) values (1,7,"2020-09-17",400);
insert into encheres (no_utilisateur,no_article,date_enchere,montant_enchere) values (2,8,"2020-09-15",55);
insert into encheres (no_utilisateur,no_article,date_enchere,montant_enchere) values (4,11,"2020-09-24",25);
insert into encheres (no_utilisateur,no_article,date_enchere,montant_enchere) values (4,12,"2020-09-09",10);
insert into encheres (no_utilisateur,no_article,date_enchere,montant_enchere) values (5,13,"2020-09-17",415);

