# Bank Account Galais-Brousse Valere

Application de gestion de compte bancaire développée avec une architecture hexagonale.

## 🏗️ Architecture

- **Backend** : Java avec Spring Boot
- **Frontend** : Angular
- **Architecture** : Hexagonale (Domain-Driven Design)

## 📋 Prérequis

- Java 17 ou supérieur
- Node.js 18 ou supérieur
- npm 9 ou supérieur
- Angular CLI 17 ou supérieur

## 🚀 Installation

### Backend

1. Se positionner dans le dossier backend
2. Installer les dépendances Maven
3. Lancer le serveur Spring Boot

Le serveur backend sera accessible sur : `http://localhost:8080`

### Frontend

1. Se positionner dans le dossier frontend/bank-app
2. Installer les dépendances npm
3. Lancer le serveur de développement Angular

L'application frontend sera accessible sur : `http://localhost:4200`

## 💻 Fonctionnalités

- Création de compte bancaire
- Consultation du solde
- Dépôt d'argent
- Retrait d'argent
- Historique des transactions
- Suppression de compte

## 📚 Structure du Projet

```
.
├── backend/                    # Application Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/kata/bank/
│   │   │   │       ├── domain/        # Logique métier
│   │   │   │       ├── adapter/       # Adaptateurs
│   │   │   │       └── application/   # Configuration
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
│
└── frontend/                  # Application Angular
    └── bank-app/
        ├── src/
        │   ├── app/
        │   │   ├── components/        # Composants Angular
        │   │   ├── services/          # Services Angular
        │   │   └── models/            # Modèles TypeScript
        │   ├── assets/
        │   └── styles.scss
        └── package.json
```

## 🔍 API Endpoints

- `GET /api/accounts` : Liste des comptes
- `POST /api/accounts` : Création d'un compte
- `GET /api/accounts/{id}` : Détails d'un compte
- `POST /api/accounts/{id}/deposit` : Dépôt d'argent
- `POST /api/accounts/{id}/withdraw` : Retrait d'argent
- `DELETE /api/accounts/{id}` : Suppression d'un compte

## ⚠️ Notes Importantes

Le backend utilise un stockage en mémoire (HashMap), les données sont perdues au redémarrage du serveur.

## 🤝 Contribution

1. Fork le projet
2. Créez une branche (`git checkout -b feature/AmazingFeature`)
3. Committez vos changements (`git commit -m 'Add some AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrez une Pull Request 
