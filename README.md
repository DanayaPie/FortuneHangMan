# Fortune Hangman

## Project Description

Fortune Hangman is a competitive word/phrase guessing game where players earn points and compete for the highest score. It features multiple rounds based on the number of players, with each round involving the selection of a category and a randomly chosen word. Players take turns spinning a wheel to determine the points they can earn for correctly guessing a letter. If a player guesses incorrectly, their turn ends and the next player takes a turn. Vowels can be guessed at the cost of 250 points. The round concludes when the word/phrase is guessed, and the game ends when all players have chosen a category. Note that the rules may vary between the User Interface and Command Line versions of the game.

Fortune Hangman is implemented as a full-stack application. The backend is built using Java, Spring Boot, Apache, and Hibernate, while the frontend utilizes React, HTML, and CSS. Additionally, the Command Line version of the game is developed using Java, Apache, and Jackson.

## Technologies Used

- Backend

  - Java
  - Spring Boot
  - Apache
  - Hibernate
  - PostgreSQL

- User Interface

  - React
  - HTML
  - CSS

- Command Line Version

  - Java
  - Jackson
  - Apache

- Testing
  - Java
  - Mockito
  - JUnit 5
  - H2

## Features

- User Interface Version

  - Provide category selection functionality for users.
  - Players are required to utilize an external wheel to input the points into the application
  - Enable players to guess either individual letters or the entire word/phrase.

- Command Line Version
  - Allow users to select a category.
  - Randomly select a word/phrase based on the chosen category.
  - Implement a wheel spinning feature that generates random outcomes, including points, bankruptcy, losing a turn, or a spin token.
  - Enable players to make letter guesses or guess the entire word/phrase.

**To-do list:**

- Backend
- Utilize Spring modules such as Spring Data, Spring MVC, and other Spring modules

- User Interface

  - In house wheel
  - Player scoring system
  - Improve performance

- Command Line Version

  - Improve performance
  - Make changes to some game features

- Testing
  - Utilize Spring Test

## Getting Started

**Backend**

- Installed Java 11
- Clone backend repository: https://github.com/DanayaPie/FortuneHangman.git
- Set the runtime environment in 'application.properties'
  - Change 'datasource url, username, and password'
- Set up the database in 'application.properties'
  - change 'hibernate.ddl-auto' to create, create-drop, validate, or update as needed

**User Interface**

- Wait for the release

**Command Line Version**

- Installed Java 11
- Clone command line repository: https://github.com/DanayaPie/FortuneHangman.git

## Contributors

- Danaya Chusuwan
- Bryan Villatoro
