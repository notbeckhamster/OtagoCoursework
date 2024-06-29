package src;
import java.util.Objects;
/**
 * 
 * This class represents a coordinate in the ant plane
 * 
 * 
 * @author Beckham Wilson
 * @author Dyrel Lumiwes
 */
public class Coordinate implements Comparable<Coordinate> {
    long x;
    long y;

    public Coordinate(long x, long y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o){
        if (o==null || getClass() != o.getClass())return false;
        Coordinate other = (Coordinate) o;
        return x==other.x && y==other.y;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x,y);
    }

    
    @Override
    public int compareTo(Coordinate o){
        if (o==null || getClass() != o.getClass())return -1;
        Coordinate other = (Coordinate) o;

        double distanceOther = Math.sqrt( Math.pow(other.x,2) +  Math.pow(other.y,2)) ;
        double distance = Math.sqrt( Math.pow(this.x,2) +  Math.pow(this.y,2)) ;
        return Double.compare(distance, distanceOther);
    }
    
    public Coordinate move(char direction){
        Coordinate newCoord = new Coordinate(x, y);
        switch (direction) {
            case 'N':
                newCoord.y++;
                break;
            case 'S':
                newCoord.y--;
                break;
            case 'E':
                newCoord.x++;
                break;
            case 'W':
                newCoord.x--;
                break;
        }
        return newCoord;
    }
    @Override
    public String toString(){
        return x + " " + y;
    }
    
    public double getDistance(){
        return Math.sqrt( Math.pow(this.x,2) +  Math.pow(this.y,2));
    }


}
