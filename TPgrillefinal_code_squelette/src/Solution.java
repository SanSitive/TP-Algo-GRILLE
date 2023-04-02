
import java.util.ArrayList;


public class Solution extends ArrayList<Coord> {
    public Solution(Coord c){
        this.add(c);
    }
    public Solution(){

    }


    public Solution(Solution solution) {
        for (Coord c: solution) {
            add(c);
        }
    }
}
