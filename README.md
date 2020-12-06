# League-rank command line interface (CLI)

This GitHub repository contains the League-rank CLI -  and supports the following functionality:

- Will take a single argument of the path to a file which contain scores to matches. Format shown below. The application 
  will then generate the league standings of the teams that were in that file.   

	
## Prerequisites

- You must have Java 11 or above installed, and available to use on your command line (Please note that the CLI does not 
  work with Java versions 10 or below).


## Getting started

- Build the tool from the source (```gradlew build```), or download the prebuilt [league-rank](https://github.com/dasinsoftware/league-rank/releases) ZIP file. 

## Usage

On the command line, in the same directory where you ran the gradlew build from :

```
cd build/install/league-rank/bin
./league-rank <path to results-of-games-file.txt>
```

### macOS/Linux
eg.
```
./league-rank /home/test/results-of-games.txt
```

### Windows
eg.
```
league-rank c:\home\test\results-of-games.txt
```

## Sample input file format
```
Lions 3, Snakes 3
Tarantulas 1, FC Awesome 0
Lions 1, FC Awesome 1
Tarantulas 3, Snakes 1
Lions 4, Grouches 0
```
