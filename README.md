# Simple Music Player

## Description

A simple console-app music player with 3 different role users: `Admin`, `Artist`, `Listener`.
Feel free to fork, clone and have fun!

## Folder Structure

The workspace contains following two folders by default, Meanwhile, the compiled output files will be generated in the `bin` folder by default.
- `lib`: the folder to maintain dependencies
- `src`: the folder to maintain sources
  - `Abstracts`: contains abstract classes commonly used in project
  - `Controllers`: contains middleware and controllers (ex. `LoginController.java`)
  - `Entities`: contains all entities application needs
  - `Enums`: contains enums
  - `Managers`: contains managers to handle collection apis
  - `Pages`: contains UI classes to give options to user
  - `Services`: contains utils and methods commonly used in Pages and others
  - `Songs`: used for storing imported songs to app (ignored by git)

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Sources Used

Mostly viewed following sources for any methods, castings, implementations and extensions, 

- [Oracle Java Docs](https://docs.oracle.com/javase/8/docs/)
- [Geeks For Geeks](https://www.geeksforgeeks.org/java/)
