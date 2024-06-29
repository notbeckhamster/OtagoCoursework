package src;
/**
 * Option class to store the roll needed and the indexes to keep
 * 
 * @group BobbyTables
 * @author Beckham Wilson
 */

public class Option {
    private int rollNeeded;
    private int firstIndexToKeep;
    private int secondIndexToKeep;

    /**
     * Creates Option object roll
     * 
     * @param rollNeeded
     * @param firstIndexToKeep
     * @param secondIndexToKeep
     */
    public Option(int rollNeeded, int firstIndexToKeep, int secondIndexToKeep) {
      this.rollNeeded = rollNeeded;
      this.firstIndexToKeep = firstIndexToKeep;
      this.secondIndexToKeep = secondIndexToKeep;

    }

    public int getRollNeeded() {
      return this.rollNeeded;
    }

    public int getFirstIndexToKeep() {
      return this.firstIndexToKeep;
    }

    public int getSecondIndexToKeep() {
      return this.secondIndexToKeep;
    }

    public String toString() {
      return "RollNeeded: " + this.rollNeeded + " firstIndexToKeep:" + this.firstIndexToKeep + " secondIndexToKeep"
          + this.secondIndexToKeep + "\n";
    }
  }