import java.util.*;

class FactorySolPermut implements IFactory{
    private Instance i;

    public FactorySolPermut(Instance ii){
        i=ii;
    }
    public ElemPermutHC getRandomSol(){
        ArrayList<Integer> randomPermut = new ArrayList<Integer>();
        for(int j=0;j<i.getListeCoordPieces().size();j++){
            randomPermut.add(j);
        }
        Collections.shuffle(randomPermut);

       return new ElemPermutHC(i,randomPermut);
    }
}