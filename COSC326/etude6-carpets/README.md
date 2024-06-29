# Etude 6 - Carpets

## Group Members:
1. Beckham Wilson
2. Dyrel Lumiwes
3. Raaid Taha
4. Kevin Albert

## Usage
Commands assume you are within src/ directory 

**Compile class files**
```
    javac src/*.java

```

**Run CarpetApp Program**
```
    java src/CarpetApp < input.txt [OPTION] [SIZE]
```

[OPTION] possible options 

-b Balanced carpet

-m Max match carpet 

-n No match carpet

[SIZE] positive integer denoting carpet output size

**Example CarpetApp Program**
```
    java CarpetApp < input.txt -n 5
```


## Our Strategy
The strategy for generating carpets with maximum matches (-m) involves a greedy approach, where the application appends strips to the carpet to maximize the number of matches between last strip and strip to append. Afterwards, it explores all possible combinations of strips and selects the one that maximizes the number of matches across the whole carpet via DFS and pruning. getNeighbours() is sorted based on ascending order of how many strips are in stock.

The strategy for generating carpets with no matches (-n) uses a pure depth-first search algorithm to explore all possible combinations of strips until it finds a carpet with no matches between adjacent strips. getNeighbours() is sorted per strip based on how many other strips is has no matches with in stock and the last strip in carpet.

The strategy for generating balanced carpets (-b) aims to create carpets where the number of matches is balanced with the number of non-matches. It starts with a randomly generated carpet and iteratively adjusts it by swapping strips inside the current carpet and from stock to achieve a more balanced score, defined as the absolute difference between matches and non-matches until it is balanced (or off by 1 if balance is not possible)

## Files
### CarpetApp.java
This file is the main program for a command-line tool focused on carpet tasks. It reads inputs, handles different operations like maximizing matches (-m), finding unmatched carpets (-n), and creating balanced designs (-b).
### Carpet.java
The Carpet class represents carpets made of strips. It calculates matches, checks balance, and generates neighboring carpets. It uses HashMaps and ArrayLists to manage strips and supports operations like swapping and addition.
### CarpetGen.java
This file manages carpet creation and optimization. It counts strips, precomputes matches, and offers methods for maximizing matches, finding unmatched carpets, and creating balanced designs.
### CarpetPair.java
The CarpetPair class defines pairs of strips from different carpets, including their indices and reversals. It ensures consistent hashing for effective strip management.
### Strip.java
Represents individual strips in carpets. It calculates matches, supports reversal, and provides methods for string manipulation.
### Utils.java
Provides utility methods for various tasks such as reading input from the console, reversing strings, and reading files.

