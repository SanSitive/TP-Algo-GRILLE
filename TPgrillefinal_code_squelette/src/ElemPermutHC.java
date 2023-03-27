import java.util.ArrayList;
import java.util.Objects;

public class ElemPermutHC implements IElemHC {

    private Instance i;
    private ArrayList<Integer> permut; //permutation de {0,..,i.getListePieces().size()-1} représentant l'ordre dans lequel on souhaite ramasser les pièces
    private static int dist = 1; //distance à laquelle on génère voisinage

    public ElemPermutHC(Instance i, ArrayList<Integer> p){
        this.i = i;
        permut = p;
    }

    public ElemPermutHC(ElemPermutHC s){
        this.i = new Instance(s.i);
        this.permut = new ArrayList<Integer>();
        permut.addAll(s.permut);
    }

    public static void setDist(int d){
        dist = d;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ElemPermutHC)) return false;
        ElemPermutHC that = (ElemPermutHC) o;
        return i.equals(that.i) && permut.equals(that.permut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, permut);
    }

    public int getVal(){


        //retourne nbCases * valSol - nbStepsTotal, où :
        //- nbCases est le nombre de cases du plateau
        //- valSol est la valeur de la solution associée à this
        //- nbStepsTotal est le nombre de pas total qu'il faudrait pour ramasser toutes les pièces dans l'ordre de permut

        // à compléter

        int distMinPourRecupAllPiece = this.i.nbStepsToCollectAll(this.permut);

        int valSol = this.i.evaluerSolution(this.getSol());
        int nbCases = i.getNbL() * i.getNbC();
        return nbCases * valSol - distMinPourRecupAllPiece;

    }

    public Solution getSol(){
        return i.calculerSol(permut);
    }


    public ArrayList<ElemPermutHC> getVoisinsImmediats() {


        //retourne l'ensemble des voisins à dist <= 1 (et donc this fait partie du résultat car à distance 0)
        //voisins = toutes les permutations que l'on peut atteindre en repoussant un élément de permut à la fin
        //ex pour permut = (0,1,2), doit retourner {(1,2,0),(0,2,1),(0,1,2)}
        //les objets retournés doivent être indépendant de this, et cette méthode ne doit pas modifier this

        //ne dois pas modifier this

        //à compléter
        ArrayList<ElemPermutHC> toReturn = new ArrayList<>();
        for(int i =0; i< this.permut.size(); i++){
            ArrayList<Integer> newPermut = new ArrayList<>();
            for(Integer j : this.permut){
                newPermut.add(j);
            }
            Integer elem = this.permut.get(i);
            newPermut.remove(this.permut.get(i));
            newPermut.add(elem);

            toReturn.add(new ElemPermutHC(this.i,newPermut));
        }


        return toReturn;
    }



    public ArrayList<ElemPermutHC> getVoisins(){

        //retourne voisins (sans doublons) à une distance <= dist
        //pour dist = 1, doit retourner getVoisinsImmediats();

        //à compléter
        ArrayList<ElemPermutHC> toReturn = this.getVoisinsImmediats();

        for(int i = 1; i < this.dist; i++){
            for(ElemPermutHC elem : toReturn){
                ArrayList<ElemPermutHC> procheVoisins = elem.getVoisinsImmediats();
                for(ElemPermutHC elemPV : procheVoisins){
                    if(!toReturn.contains(elemPV)){
                        toReturn.add(elemPV);
                    }
                }
            }
        }




      return toReturn;
    }

}