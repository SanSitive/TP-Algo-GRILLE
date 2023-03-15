import java.util.ArrayList;

public interface IElemHC {
    //représente un élément dans l'espace de recherche d'un algorithme de HillClimbing

    Solution getSol(); //retourne la Solution associée à l'élément
    int getVal(); //retourne la valeur de l'élément (le plus grand, le mieux) (la valeur peut être la valeur de la solution
    //associée, mais pas forcément
    ArrayList<? extends IElemHC> getVoisins();

}