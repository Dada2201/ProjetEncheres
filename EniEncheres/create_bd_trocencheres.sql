CREATE TABLE CATEGORIES (
    no_categorie   INTEGER auto_increment NOT NULL PRIMARY KEY,
    libelle        VARCHAR(30) NOT NULL
);

CREATE TABLE ENCHERES (
    no_utilisateur   INTEGER NOT NULL,
    no_article       INTEGER NOT NULL,
    date_enchere     datetime NOT NULL,
	montant_enchere  INTEGER NOT NULL

);

ALTER TABLE ENCHERES ADD constraint enchere_pk PRIMARY KEY (no_utilisateur, no_article, montant_enchere);

CREATE TABLE RETRAITS (
	no_article         INTEGER NOT NULL,
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(15) NOT NULL,
    ville            VARCHAR(30) NOT NULL
);

ALTER TABLE RETRAITS ADD constraint retrait_pk PRIMARY KEY  (no_article);

CREATE TABLE UTILISATEURS (
    no_utilisateur   INTEGER auto_increment NOT NULL PRIMARY KEY,
    pseudo           VARCHAR(30) UNIQUE NOT NULL,
    nom              VARCHAR(30) NOT NULL,
    prenom           VARCHAR(30) NOT NULL,
    email            VARCHAR(255) UNIQUE NOT NULL,
    telephone        VARCHAR(15),
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(10) NOT NULL,
    ville            VARCHAR(30) NOT NULL,
    mot_de_passe     VARCHAR(255) NOT NULL,
    credit           INTEGER NOT NULL,
    administrateur   bit NOT NULL
);

CREATE TABLE ARTICLES_VENDUS (
    no_article                    INTEGER auto_increment NOT NULL PRIMARY KEY,
    nom_article                   VARCHAR(30) NOT NULL,
    description                   VARCHAR(300) NOT NULL,
	date_debut_encheres           DATE NOT NULL,
    date_fin_encheres             DATE NOT NULL,
    prix_initial                  INTEGER,
    prix_vente                    INTEGER,
    no_utilisateur                INTEGER NOT NULL,
    no_categorie                  INTEGER NOT NULL
);

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT encheres_utilisateur_fk FOREIGN KEY ( no_utilisateur ) REFERENCES UTILISATEURS ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action ;

ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_articles_vendus_fk FOREIGN KEY ( no_article )
        REFERENCES ARTICLES_VENDUS ( no_article )
ON DELETE NO ACTION 
    ON UPDATE no action ;

ALTER TABLE RETRAITS
    ADD CONSTRAINT retraits_articles_vendus_fk FOREIGN KEY ( no_article )
        REFERENCES ARTICLES_VENDUS ( no_article )
ON DELETE NO ACTION 
    ON UPDATE no action ;

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT articles_vendus_categories_fk FOREIGN KEY ( no_categorie )
        REFERENCES categories ( no_categorie )
ON DELETE NO ACTION 
    ON UPDATE no action ;

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT ventes_utilisateur_fk FOREIGN KEY ( no_utilisateur )
        REFERENCES utilisateurs ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action ;