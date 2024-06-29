# Etudes 2 Rollin' Rollin' Rollin'

### Group BobbyTables Members:
---
* Beckham Wilson
* Dyrel Lumiwes
* Raaid Taha
* Kevin Albert

### Overview
---
This Etude was a task to derive a strategy win a dice game as quick as possible. Where the objective of the game is to form two sets of three dice each as quickly as possible. Each turn a new dice will roll and we need create a program that can check if the number rolled on that dice will get us a step closer or even accomplish the afformentioned objective.

### Strategy
---

The logic for the game is found in the OurStrategy.java file.

Firstly, the **getCompleteSet()** method returns the indices of the complete set (if any) and chooses the complete set based on maximizing the number of rolls needed to complete the remaining set. This helps avoid interfering with the complete set.

Secondly, the **getAllOptionsMatchesRoll()** method returns all possible options that, when executed with the current dice roll, result in a complete set.

If there's an option to complete a set (getAllOptionsMatchesRoll() not empty), **getIndexToSwapMaximiseOptions()** returns the index that, when swapped with the roll completes a set and maximizes options for the remaining set (if any) in the next roll.

If there's no option to complete a set (getAllOptionsMatchesRoll() is empty), then the **getIndexMaximiseOptionForNextRoll()** returns the index that, when swapped with the roll, maximizes options for completing the set in the next roll.