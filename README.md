# Système de Gestion de Tickets

Ce projet est le backend d'une application web simple qui permet la gestion de tickets. Il est construit en utilisant Java.

### Prérequis et Configuration

1. Clonez le dépôt sur votre machine locale.
2. Assurez-vous d'avoir une base de données MySQL en cours d'exécution sur votre machine. Le nom de la base de données est `db_example`, l'utilisateur est `root` et le mot de passe est vide. Ces paramètres peuvent être modifiés dans le fichier `persistence.xml` situé dans le dossier `resources/META-INF`.

### Initialisation de la Base de Données

Pour peupler la base de données avec des exemples, vous pouvez exécuter le fichier `src/main/java/jpa/JpaTest.java`. Cela créera quelques tickets et utilisateurs dans la base de données.

## Exécution du Projet

Pour démarrer le backend, vous pouvez exécuter le fichier `src/main/java/jpa/RestServer.java`. Cela démarrera un serveur sur le port 8080.

## Structure du Projet

Le projet est structuré comme suit :

- `src/main/java/jpa/domain` : Contient les classes de domaine (entités) telles que `Ticket`, `Discussion`, `Utilisateur`, etc.
- `src/main/java/jpa/rest` : Contient les end points de l'API RESTful pour l'application.
- `src/main/resources/META-INF` : Contient le fichier `persistence.xml` pour configurer l'unité de persistance JPA.

## End points de l'API

L'application fournit les end points de l'API RESTful suivants :

- `GET /ticket` : Récupère tous les tickets.
- `GET /ticket/{ticketId}` : Récupère un ticket spécifique par son ID.
- `POST /ticket/add` : Ajoute un nouveau ticket.
- `PUT /ticket/update/{ticketId}` : Met à jour un ticket existant.