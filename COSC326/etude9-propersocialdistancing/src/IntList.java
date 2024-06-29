package src;
import java.util.ArrayList;


/**
 * Wrapper for Integer ArrayList
 * 
 * @group BobbyTables
 * @author Beckham Wilson
 * @author Dyrel Lumiwes
 * @author Raaid Taha
 * @author Kevin Albert
 */
public class IntList {
    public ArrayList<Integer> list;
  
    public IntList(ArrayList<Integer> list) {
      this.list = list;
    }
  
    public ArrayList<Integer> getList() {
      return list;
    }
  
    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      IntList intList = (IntList) o;
      return list.equals(intList.list);
    }
  
    @Override
    public int hashCode() {
      return list.hashCode();
    }
  }
  