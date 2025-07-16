# Insured trading platform

**This project is a Java-Angular fullstack developer portfolio.**

It is built for scalability, security, and clean modular architecture using the latest technologies:

- Backend: Java 21 & Spring Boot 3.5, with Spring Security and JWT for robust authentication and authorization.

- Frontend: Responsive Angular using signals and resources and keeping components fully independent and driven by services. Use of Angular Material for theming.

- UI: Angular Material for a clean, accessible, and responsive design supporting light/dark mode from user preferences.

- Database: PostgreSQL, with JPA for data access.

*As this is not meant to be an entreprise-level application, it focuses on development skills, not on business logic or deployment facilities.*

*It has a monolithic architecture, to avoid micro-services infrastructure complexity.*

*It uses enum types to aggregate models that would otherwise each require their own model (e.g. BankingAccount and TradingAccount models instead of, in this application's case, Account model with "Banking" or "Trading" enum type field).*

## Run the application

Requires the installation of docker and docker compose.

Launch from the application's root directory:

```
docker compose up
```

*Use --watch flag to enable hot-reload*

## Description

- Register and login
Create a user account and log in – no email verification is required.

- Create new accounts
Open banking, savings, and trading accounts to simulate a real user portfolio.
Accounts are all created "Active" as default for testing purposes.

- Subscribe to new policies
Protect your accounts by subscribing to yearly policies linked to each one.
Policies are all created "Active" as default for testing purposes.

-Sort lists
Sort accounts and policies lists using the sort buttons.

- Transfer funds
Move money between your own accounts, or transfer to other users' accounts to test external transfers.
Transfers are all executed instantly for testing purposes.

- View account details and transfer history
Open any account details to see the detailed account's information and its list of transfers.

- View policy details
Open any policy details to see the detailed policy's informations.
