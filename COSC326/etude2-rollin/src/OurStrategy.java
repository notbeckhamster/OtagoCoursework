package src;

import java.util.*;

/**
 * This class optimizes dice swapping in a game where we attempt to form
 * two sets of three dice as quickly as possible. It inherits from
 * Rollin, implementing the handleRoll method to enhance the probability of
 * completing two sets of three dice.
 * 
 * @group BobbyTables
 * @author Beckham Wilson
 * @author Dyrel Lumiwes
 * @author Raaid Taha
 * @author Kevin Albert
 */
public class OurStrategy extends Rollin {

  public OurStrategy() {

  }

  /**
   * The handleRoll method determines how we should use the current roll given the
   * current set of dice.
   * If there's an option to complete a set with the current roll, it selects the
   * swap position
   * that maximizes options to complete the remaining set in the next roll.
   * If no immediate set completion is possible, it selects the die that maximizes
   * options for completing the set in the next roll.
   * 
   * @param roll The numerical value of the freshly rolled die.
   * @param dice An array representing the current values held by the dice.
   * @return The position of the die to be swapped with the roll, or an index -1
   *         indicating no swap
   */
  public int handleRoll(int roll, int[] dice) {
    int[] completeSet = getCompleteSet(dice, roll);
    ArrayList<Option> completeSetOptions = getCompleteSetOptions(dice, completeSet);
    ArrayList<Option> completeSetOptionsMatchRoll = getAllOptionsMatchesRoll(roll, completeSetOptions);
    // Can use roll to complete a set
    if (completeSetOptionsMatchRoll.isEmpty() == false) {
      int indexToSwap = getIndexForCompleteSetOptions(dice, roll, completeSetOptionsMatchRoll, completeSet);
      return indexToSwap;
    } else {
      // Cannot use option to complete set tries to form pair of die to enable next
      // roll to complete set
      int indexToSwap = getIndexForNonCompleteSetOptions(dice, roll, completeSet);
      return indexToSwap;
    }

  }

  /**
   * The getIndexForNonCompleteSetOptions method identifies the die to swap,
   * increasing the chances of forming a complete set on the subsequent roll.
   * 
   * 
   * @param dice        Array of integers representing the values of the current
   *                    dice.
   * @param roll        Numerical value of the most recent dice roll.
   * @param completeSet Array defining the desired set composition.
   * @return Index of the die recommended for exchange to advance towards
   *         completing a set, or -1 to indicate no exchange is warranted.
   */
  public int getIndexForNonCompleteSetOptions(int[] dice, int roll, int[] completeSet) {
    int[] diceCopy = dice.clone();
    int indexToSwap = -1;
    ArrayList<Option> completeSetOptions = getCompleteSetOptions(diceCopy, completeSet);
    HashSet<Integer> setOfRollsNeeded = getRollsNeeded(completeSetOptions);
    int maxRollsNeeded = setOfRollsNeeded.size();
    for (int i = 0; i < 6; i++) {
      if (Utils.contains(completeSet, i)) {
        continue;
      }
      int temp = diceCopy[i];
      diceCopy[i] = roll;
      ArrayList<Option> optionsToCompleteSet = getCompleteSetOptions(diceCopy, completeSet);

      setOfRollsNeeded = new HashSet<Integer>();
      for (Option eachOption : optionsToCompleteSet) {
        setOfRollsNeeded.add(eachOption.getRollNeeded());
      }

      if (setOfRollsNeeded.size() > maxRollsNeeded) {
        maxRollsNeeded = setOfRollsNeeded.size();
        indexToSwap = i;
        diceCopy[i] = temp;
      } else {
        diceCopy[i] = temp;
      }
    }
    return indexToSwap;
  }

  /**
   * The getIndexForCompleteSetOptions method determines best option and die to
   * swap, by maximizing the chances of completing the remaining set in the next
   * roll.
   * 
   * @param dice                Array indicating the present values of the dice.
   * @param roll                Numerical value of the dice roll just obtained.
   * @param allOptionsMatchRoll Collection of potential moves that are compatible
   *                            with the recent roll.
   * @param completeSet         Array representing the set that needs to be
   *                            achieved.
   * @return Index indicating which die should be exchanged to increase set
   *         completion paths, or -1 if no change is advisable.
   */

  public int getIndexForCompleteSetOptions(int[] dice, int roll, ArrayList<Option> allOptionsMatchRoll,
      int[] completeSet) {

    int indexToSwap = -1;
    int maxOptions = 0;

    for (Option option : allOptionsMatchRoll) {
      int[] diceCopy = dice.clone();
      int firstIndexToKeep = option.getFirstIndexToKeep();
      int secondIndexToKeep = option.getSecondIndexToKeep();
      ArrayList<Option> completeSetOptions = getCompleteSetOptions(diceCopy, completeSet);
      HashSet<Integer> listOfRollsNeeded = getRollsNeeded(completeSetOptions);
      int optionsCount = listOfRollsNeeded.size();

      for (int i = 0; i < 6; i++) {

        if (i == firstIndexToKeep || i == secondIndexToKeep || Utils.contains(completeSet, i)) {
          continue;
        }
        diceCopy[i] = roll;
        // Count the number of complete set options after applying the current option
        completeSetOptions = getCompleteSetOptions(diceCopy, completeSet);
        listOfRollsNeeded = getRollsNeeded(completeSetOptions);
        optionsCount = listOfRollsNeeded.size();
        // Check if this option maximizes the number of complete set options
        if (optionsCount > maxOptions) {
          maxOptions = optionsCount;
          indexToSwap = i;
        }
        diceCopy = dice.clone();

      }

    }
    return indexToSwap;
  }

  /**
   * Filters available options to identify those compatible with the latest dice
   * roll.
   * 
   * @param roll       Numerical value of the most recent dice roll.
   * @param allOptions Collection of all conceivable choices.
   * @return A list of choices that require the specified roll to finalize a set.
   */
  public ArrayList<Option> getAllOptionsMatchesRoll(int roll, ArrayList<Option> allOptions) {
    ArrayList<Option> optionsMatchRollArrLs = new ArrayList<Option>();
    for (Option eachOption : allOptions) {
      if (eachOption.getRollNeeded() == roll) {
        optionsMatchRollArrLs.add(eachOption);
      }
    }
    return optionsMatchRollArrLs;
  }

  /**
   * The getCompleteSet methodchecks whether a complete set is possible with the
   * existing dice values and the latest roll.
   * Also optimise the selection of completeSet to increase options in the
   * remaining set.
   * 
   * @param dice Array holding the current values of the dice.
   * @param roll The value obtained from the most recent roll.
   * @return An array containing the indices that form a complete set, or an empty
   *         array if completing a set is not possible.
   */
  public int[] getCompleteSet(int[] dice, int roll) {
    ArrayList<IntArrIntPair> rollCannotCompleteRemainingSet = new ArrayList<IntArrIntPair>();
    for (int[][] si : setIndices) {
      if (isSet(si[0], dice)) {
        HashSet<Integer> rollsNeededForRemainingSet = getRollsNeeded(getCompleteSetOptions(dice, si[0]));
        if (rollsNeededForRemainingSet.contains(Integer.valueOf(roll))) {
          return si[0];
        } else {
          rollCannotCompleteRemainingSet.add(new IntArrIntPair(si[0], rollsNeededForRemainingSet.size()));
        }

      } else if (isSet(si[1], dice)) {
        HashSet<Integer> rollsNeededForRemainingSet = getRollsNeeded(getCompleteSetOptions(dice, si[1]));
        if (rollsNeededForRemainingSet.contains(Integer.valueOf(roll))) {
          return si[1];
        } else {
          rollCannotCompleteRemainingSet.add(new IntArrIntPair(si[1], rollsNeededForRemainingSet.size()));
        }
      }
    }
    if (rollCannotCompleteRemainingSet.isEmpty()) {
      return new int[0];
    }
    Collections.sort(rollCannotCompleteRemainingSet, Comparator.comparingInt(pair -> pair.value));
    IntArrIntPair pairWithMostRollsNeeded = rollCannotCompleteRemainingSet
        .get(rollCannotCompleteRemainingSet.size() - 1);
    int[] completedSet = pairWithMostRollsNeeded.array;
    return completedSet;
  }

  /**
   * Determines the specific rolls required in the given list of options.
   * 
   * @param listOfOptions An array of choices available for achieving complete
   *                      sets.
   * @return A collection of integer values indicating the rolls necessary to
   *         accomplish a set.
   */

  public HashSet<Integer> getRollsNeeded(ArrayList<Option> listOfOptions) {
    HashSet<Integer> listOfRollsNeeded = new HashSet<Integer>();
    for (Option eachOption : listOfOptions) {
      listOfRollsNeeded.add(eachOption.getRollNeeded());
    }
    return listOfRollsNeeded;
  }

  /**
   * Generates options to achieve a complete set, considering the existing
   * arrangement of dice and flagged completeSet.
   * 
   * @param dice                 An array capturing the values of the dice in
   *                             play.
   * @param indexesOfCompleteSet A collection of indices pointing to dice that
   *                             might help form a complete set.
   * @return A compilation of strategies that could lead to the completion of the
   *         set.
   */
  public ArrayList<Option> getCompleteSetOptions(int[] dice, int[] indexesOfCompleteSet) {
    ArrayList<Option> listOfOptions = new ArrayList<Option>();

    for (int i = 0; i < dice.length; i++) {
      for (int j = i + 1; j < dice.length; j++) {
        if (Utils.contains(indexesOfCompleteSet, i) || Utils.contains(indexesOfCompleteSet, j)) {
          continue;
        }
        int max = Math.max(dice[i], dice[j]);
        int min = Math.min(dice[i], dice[j]);
        if (dice[i] == dice[j]) {
          Option optionSameNumber = new Option(dice[i], i, j);
          listOfOptions.add(optionSameNumber);

        } else if (max - min == 1) {
          if (dice[i] == 6 || dice[j] == 6) {
            Option optionLowerSequenceNumber = new Option(4, i, j);
            listOfOptions.add(optionLowerSequenceNumber);
          } else if (dice[i] == 1 || dice[j] == 1) {
            Option optionHigherSequenceNumber = new Option(3, i, j);
            listOfOptions.add(optionHigherSequenceNumber);
          } else {
            Option optionLowerSequenceNumber = new Option(Math.min(dice[i], dice[j]) - 1, i, j);
            listOfOptions.add(optionLowerSequenceNumber);
            Option optionHigherSequenceNumber = new Option(Math.max(dice[i], dice[j]) + 1, i, j);
            listOfOptions.add(optionHigherSequenceNumber);
          }
        } else if (max - min == 2) {
          Option optionBetween = new Option(Math.min(dice[i], dice[j]) + 1, i, j);
          listOfOptions.add(optionBetween);
        }
      }
    }
    return listOfOptions;
  }
}
