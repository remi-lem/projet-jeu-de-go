# Projet jeu de Go

Projet effectué dans le cadre du cours de Qualité de Développement de la deuxième année de BUT Informatique.<br>
Ce projet a pour objectif de nous apprendre les principes SOLID et à les respecter.

## Auteurs
- [Rémi L.](https://github.com/remi-lem)
- [Esteban C.R.](https://github.com/EstebanCRz)
- [Clothilde P.](https://github.com/TorielLink)

## Fonctionnalités de l'application
### Sprint 1
- Diagramme d'Architecture (de Paquetage)
- Interpreter qui accepte des commandes
- Mise en place du protocole GTP
- Commande QUIT
- Commande BOARDSIZE
- Commande SHOW_BOARD

### Sprint 2
- Commande CLEAR_BOARD
- Commande GENMOVE
- Commande PLAY
- Commande FINAL_SCORE
- Capture d'une ou d'un groupe de pierres
- Jeux de tests

### Sprint 3
- Jeu hors GTP (joueurs humains ou robots)
- Commande PLAYER
- Commande PLAY pass
- Commande SET_HANDICAPS
- Commande UNDO
- Bot qui joue aléatoirement

## Améliorations possibles
- Gérer les suicides
- Gérer la règle du KO
- Coder un bot qui joue selon les règles du jeu de Go

## Principes SOLID et Design Pattern
Nous respectons ces principes SOLID : 
- DIP : Pour chaque paquetage qui pourrait varier, nous "cachons" les classes concrètes derrière une interface.
- SRP : Chaque paquetage a un et un seul axe de changement.
- OCP : La classe Player, par exemple, est fermée à la modification directe, mais ouverte à l'extension
(avec Human et Robot).
- Patron de création Factory : pour éviter la dépendance d'un paquet stable a un instable.
- DRY (Don't Repeat Yourself) : Pas de redondance dans le code.

## Diagramme d'Architecture / de Paquetage
![DiagrammeArchi.png](DiagrammeArchi.png)
---

## Languages utilisés
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![LaTeX](https://img.shields.io/badge/latex-%23008080.svg?style=for-the-badge&logo=latex&logoColor=white)

## Outils utilisés
### IDE :
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)

### Travail collaboratif :
![Trello](https://img.shields.io/badge/Trello-%23026AA7.svg?style=for-the-badge&logo=Trello&logoColor=white)
![Google Drive](https://img.shields.io/badge/Google%20Drive-4285F4?style=for-the-badge&logo=googledrive&logoColor=white)
![Discord](https://img.shields.io/badge/Discord-%235865F2.svg?style=for-the-badge&logo=discord&logoColor=white)

<img src="https://goat-inc.co.jp/wp-content/uploads/2021/03/logo-plantuml-visual-code.png" alt=PlantUML width="100"/>


