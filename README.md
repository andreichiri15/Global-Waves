
## Overview
Global Waves is a Spotify-inspired application developed in Java, that is able to parse input commands from multiple users intermittently, with various functionalities, such as: searching a song/podcast/playlist based on a set of filters, skipping to the next song, playing/pausing etc. 

## Description of packages

### fileio.input
This package consists of classes used for storing the entire library of songs, podcasts and users.

### library
This package consists of all packages/classes that interact with library in some way. The classes that are included are:
- Library: which copies the information from fileio.input.copy, with additional fields and methods
- User: stores information regarding each user, plus methods which describe interactions with user-created playlists, such as the ability to create or follow playlists.
- SearchBar: which simulates the actual search bar of users, responsible for conducting the search of an audio file based of a set of filters given by the user
- Player: the 'main' part of the application's brain, which handles most operations possible

In addition, we have three subpackages:
- library.fields consisting of helper classes which aid in generating the fields of the json files correctly
- library.filetypes which consist of classes representing the audio file types
- library.user.helper which consists, of one class that aids in remembering the episode of a podcast that the user was listening to, in case the user wants to resume it, after listening to a song, a class that holds the revenue stats for artists, a subpackage which consists of classes that hold information regarding the wrapped statistics, and a notifications subpackage that hold the interfaces and classes that aid in notifying the user of certain events (Observer design pattern)) 

### commands
This package includes classes that help parsing the input of the user correctly. This class consists of:
- Class InputCommands: which is a class that contains all the possible fields that an input command given by the user can have
- Class CommandRunner: which is a class that is responsible for parsing the input commands correctly, and executing them
- Subpackage filters: which includes classes that aid in reading the filters given for search commands correctly

### json.generator
This is a large package containing classes which aid in generating the output JSON files for every singe command that can be given by the user

### main
This package represents the entry point in the program

### page
This package consists of classes that aid in printing the page which the user currently sees, in a correct format
The files in this package are:
- Page: which is the main class that is responsible selecting the correct strategy for printing the page (Strategy design pattern)
- Subpackage strategies: which consists of classes that represent the strategies for printing the page, based on the type of the page (playlist, album, artist etc.)
- Subpackage command: which consists of classes that aid in moving through the pages, based on the command given by the user (Command design pattern)

### utils
This package consists, so far, of a class which stores all the error constants that can be found in the application

## The "flow" of the application

### INPUT
We read using objectMapper all commands given by every user, using an array of InputCommands instances.

### INPUT PROCESSING / EXECUTING COMMANDS
In the CommandRunner class, we determine what type of command we have, based on the filetype field existent in InputCommand. Based on what type of command we have, we call the appropriate method which can be found in CommandRunner class, which also a method from a class found in the **library** package. If it is search, selection, or loading of an audio file, we are going to find these methods in the **SearchBar** class. If it is an operation that has to do with the actual interaction with an audio file, such as playing, pausing, forwarding etc. (while also remembering to update the revenue and wrapped statistics for all types of users), we are going to call methods from **Player** class. In case we want to execute a user-based command, such as creating, following a playlist, changing the page or liking a song, we look at the **User** Class. And so on.

### GENERATING THE OUTPUT
The output of this application consist of JSON file which includes information regarding each command executed, in chronological order. In order to do what, we will use the help of classes found in **json.generator** package. After each command, we fill an instance of a class with the corresponding class, which inherits the 'Result' class, which we add the end of a list of similar command results. After we are done with executing user commands, we take the list of result classes and generate the output JSON file using objectMapper.

## DESIGN PATTERNS
In this application, I used the following design patterns: Singleton, Strategy, Command, Observer and Facade.

### Singleton
I used the Singleton design pattern in the **Command Runner**, which is the class that is responsible for parsing the input commands and executing them. I used this design pattern because I wanted to make sure that there is only one instance of this class.

### Strategy
I used the Strategy design pattern in the **Page** package, which is responsible for printing the page that the user is currently on. I used this design pattern for the following reasons:
- I wanted to make sure that the code is extensible, in case I want to add more types of pages in the future
- It encapsulates the implementation of the page printing, so that the user of the class does not have to know how the page is printed, but only that it is printed correctly

### Command
I used the Command design pattern in the **Page** package, which is responsible for moving through the pages. I used this design pattern for the following reasons:
- I wanted to make sure that the code is extensible, in case I want to add more types of pages in the future
- It adds the ability to undo the last command, in case the user wants to go back to the previous page
- The user is also capable of redoing the last command, in case he wants to go back to the page he was on before undoing the last command

### Observer
I used the Observer design pattern in the **library.user.helper.notifications** package, which is responsible for notifying subscribed users of certain events. I believe this design pattern is the most appropriate for this situation, because:
- The Observer pattern helps maintain consistency between all subscribers, and the subject (the user) is not aware of the existence of the subscribers
- It's easy to add new observers or change existing ones without modifying the subject, the subject's activity is not affected by the number of observers, and the subject does not have to know the concrete class of the observer
- The subject can notify the observers in any order, and the observers can be added or removed at any time

### Facade
I used the Facade design pattern in the **commands** package, in the CommandRunner class, which is responsible for storing the entire library of songs, podcasts and users. This design pattern is useful because:
- It hides the complexity of the library from the user
- It makes the library easier to use, because the user does not have to know how the library is implemented, but only that it works correctly
- I want to mention that I am not sure whether CommandRunner is an actual implementation of Facade :))), from what I read online it seems like it, so I thought I'd mention it :D.




