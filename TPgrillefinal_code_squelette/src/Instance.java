import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Instance {
    private Coord startingP; //point de départ
    private int k; //k>=0, nombre de pas disponibles
    private boolean[][] plateau; //orientation: plateau[0][0] en haut à gauche, et plateau[ligne][col]

    //sortie du problème: une Solution (une solution est valide ssi c'est une liste de coordonées de taille au plus k+1, tel que deux coordonnées consécutives sont à distance 1,
    // et les coordonnées ne sortent pas du plateau)


    private ArrayList<Coord> listeCoordPieces;// attribut supplémentaire (qui est certes redondant) qui contiendra la liste des coordonnées des pièces du plateau
    //on numérote les pièces de haut en bas, puis de gauche à droite, par exemple sur l'instance suivante (s représente
    //le startinP et x les pièces
    //.x..x
    //x..s.
    //....x

    //les numéros sont
    //.0..1
    //2..s.
    //....3
    //et donc listeCoordPices.get(0) est la Coord (0,1)



    /************************************************
     **** debut methodes fournies              ******
     *************************************************/
    public Instance(boolean[][] p, Coord s, int kk, int hh) {
        plateau = p;
        startingP = s;
        k = kk;
        listeCoordPieces = getListeCoordPieces();
    }

    public Instance(boolean[][] p, Coord s, int kk) {
        plateau = p;
        startingP = s;
        k = kk;
        listeCoordPieces = getListeCoordPieces();
    }


   public Instance(Instance i){ //créer une instance qui est une deep copy (this doit etre independante de i)
        boolean [][] p2 = new boolean[i.plateau.length][i.plateau[0].length];
        for(int l = 0;l < p2.length;l++) {
            for (int c = 0; c < p2[0].length; c++) {
                p2[l][c] = i.plateau[l][c];
            }
        }

        plateau=p2;
        startingP=new Coord(i.startingP);
        k = i.k;
        listeCoordPieces = new ArrayList<>();
        for(Coord c : i.listeCoordPieces){
            listeCoordPieces.add(new Coord(c) );
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instance)) return false;
        Instance instance = (Instance) o;
        return getK() == instance.getK() && getStartingP().equals(instance.getStartingP()) && Algos.egalEnsembliste(getListeCoordPieces(),instance.getListeCoordPieces());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartingP(), getK(), getListeCoordPieces());
    }

    public int getNbL() {
        return plateau.length;
    }

    public int getNbC() {
        return plateau[0].length;
    }

    public Coord getStartingP() {
        return startingP;
    }

    public void setStartingP(Coord c) {
        startingP=c;
    }

    public int getK() {
        return k;
    }

    public void setK(int kk) {
        k=kk;
    }


    public ArrayList<Coord> getListeCoordPieces() {
        if(listeCoordPieces==null){
        ArrayList<Coord> listeCoordPieces = new ArrayList<>();
        for (int l = 0; l < getNbL(); l++) {
            for (int c = 0; c < getNbC(); c++) {
                if (piecePresente(new Coord(l, c))) {
                    listeCoordPieces.add(new Coord(l, c));
                }
            }
        }
        return listeCoordPieces;}
        else
            return listeCoordPieces;
    }


    public boolean piecePresente(Coord c) {
        return plateau[c.getL()][c.getC()];
    }

    public void retirerPiece(Coord c){
        //si pas de piece en c ne fait rien, sinon la retire du plateau et met à jour les coordonnées
        if(piecePresente(c)){
            plateau[c.getL()][c.getC()] =false;
            listeCoordPieces.remove(c);
        }
    }


    @Override
    public String toString() {
       //retourne une chaine représentant this,
        StringBuilder res = new StringBuilder("k = " + k + "\n" + "nb pieces = " + getListeCoordPieces().size() + "\nstarting point = " + startingP + "\n");
        for (int l = 0; l < getNbL(); l++) {
            for (int c = 0; c < getNbC(); c++) {

                if (startingP.equals(new Coord(l,c)))
                    res.append("s");
                else{
                    if (piecePresente(new Coord(l, c))) {
                        res.append("x");
                    } else {
                        res.append(".");
                    }
                }

            }
            res.append("\n");
        }
        return res.toString()+ "\nliste pieces " + getListeCoordPieces();
    }

    public String toString(Solution s) {

        //retourne une chaine sous la forme suivante
        //o!..
        //.ox.
        //.o..
        //.o..

        //où
        // '.' signifie que la solution ne passe pas là, et qu'il n'y a pas de pièce
        // 'x' signifie que la solution ne passe pas là, et qu'il y a pas une pièce
        // 'o' signifie que la solution passe par là, et qu'il n'y a pas de pièce
        // '!' signifie que la solution passe par là, et qu'il y a une pièce

        // dans l'exemple ci-dessus, on avait donc 2 pièces dans l'instance (dont 1 ramassée par s)
        //et la chaîne de l'exemple contient 4 fois le caractère "\n" (une fois à chaque fin de ligne)

        if(s==null) return null;
        StringBuilder res = new StringBuilder("");//\n k = " + k + "\n" + "nb pieces = " + listeCoordPieces.size() + "\n");
        for (int l = 0; l < getNbL(); l++) {
            for (int c = 0; c < getNbC(); c++) {
                if (startingP.equals(new Coord(l,c)))
                    res.append("s");
                else {
                    if (s.contains(new Coord(l, c)) && piecePresente(new Coord(l, c))) {
                        res.append("!");
                    }
                    if (!s.contains(new Coord(l, c)) && piecePresente(new Coord(l, c))) {
                        res.append("x");
                    }
                    if (s.contains(new Coord(l, c)) && !piecePresente(new Coord(l, c))) {
                        res.append("o");
                    }
                    if (!s.contains(new Coord(l, c)) && !piecePresente(new Coord(l, c))) {
                        res.append(".");
                    }
                }
            }
            res.append("\n");
        }
        return res.toString();
    }
    /************************************************
     **** méthodes à fournir relatives à une solution **
     *************************************************/

    //FONCTION SUPPLEMENTAIRE RAJOUTER PAR NOUS (LES ETUDIANTS)
    public boolean positionIsValide(Coord c){

        if(c == null || c.getL() >= this.getNbL() || c.getL() < 0 || c.getC() >= this.getNbC() || c.getC() < 0){
            return false;
        }else{
            return true;
        }
    }

    public boolean estValide(Solution s) {
        //prérequis : s!=null, et les s.get(i) !=null pour tout i (mais par contre s peut contenir n'importe quelle séquence de coordonnées)
        //retourne vrai ssi s est une solution valide (une solution est valide ssi c'est une liste de coordonnées de taille au plus k+1, telle que deux coordonnées consécutives sont à distance 1,
        // et les coordonnées ne sortent pas du plateau)

        if(s == null || s.size() > this.getK() + 1){
            return false;
        }else{
            Coord temp = null;
            for(Coord c : s){
                //Vérification si possède une valeur et ne sort pas de la grille
                if(c == null || c.getL() >= this.getNbL() || c.getL() < 0 || c.getC() >= this.getNbC() || c.getC() < 0){
                    return false;
                }
                if(temp != null){
                   if(!(c.estADistanceUn(temp))){
                       return false;
                   }
                }
                temp = c;
            }
        }
        return true;
    }


    public int evaluerSolution(Solution s) {
        //prerequis : s est valide (et donc !=null)
        //action : retourne le nombre de pièces ramassées par s (et ne doit pas modifier this ni s)

        int nbPiece = 0;
        for(int l = 0; l < this.getNbL() ; l++){
            for(int c =0 ; c < this.getNbC(); c++){
                if (s.contains(new Coord(l, c)) && piecePresente(new Coord(l, c))) {
                    nbPiece++;
                }
            }
        }

        return nbPiece;
    }



    public int nbStepsToCollectAll(ArrayList<Integer> permut) {

        //prérequis : permut est une permutation des entiers {0,..,listeCoordPieces.size()-1}
        // (et donc permut peut être vide, mais pas null, si il n'y a pas de pièces)

        //retourne le nombre de pas qu'il faudrait pour ramasser toutes les pièces dans l'ordre de permut

        //à compléter

        return 0;
    }

    /************************************************
     **** méthodes à fournir relatives au greedy        ******
     *************************************************/



    public ArrayList<Integer> greedyPermut() {
        //retourne une liste (x1,..,xp) où
        //x1 est la pièce la plus proche du point de départ
        //x2 est la pièce restante la plus proche de x1
        //x3 est la pièce restante la plus proche de x2
        //etc
        //Remarques :
        // -on doit donc retourner une sequence de taille listeCoordPieces.size() (donc sequence vide (et pas null) si il n'y a pas de pièces)
        // -si à un moment donné, lorsque l'on est sur une pièce xi, la pièce restante la plus proche de xi n'est pas unique,
        //   alors on peut prendre n'importe quelle pièce (parmi ces plus proches de xi)
        //par exemple,
        //si le plateau est
        //.s.x
        //....
        //x..x
        //avec la pièce 0 en haut à droite, la pièce 1 en bas à gauche, et la pièce 2 en bas à droite,
        //on doit retourner (0,2,1)

        ArrayList<Integer> listIndexPiece = new ArrayList<>();

        Coord actualCoord = startingP;
        Integer tempIndex = 0;

        for(int nbPiece = 0; nbPiece < listeCoordPieces.size(); nbPiece++){
            tempIndex = calcGreedy(actualCoord,listIndexPiece);
            actualCoord = listeCoordPieces.get(tempIndex);
            listIndexPiece.add(tempIndex);
        }
        return listIndexPiece;
    }

    public Integer calcGreedy(Coord coordActu, ArrayList<Integer> listIndexPiece){

        int minDist = Integer.MAX_VALUE;
        int minIndex = 0;

        for(int index = 0; index < listeCoordPieces.size(); index++){
            if(!listIndexPiece.contains(index)){
                if(listeCoordPieces.get(index).distanceFrom(coordActu) < minDist){
                    minDist = listeCoordPieces.get(index).distanceFrom(coordActu);
                    minIndex = index;
                }
            }
        }
        return minIndex;
    }


    public Solution calculerSol(ArrayList<Integer> permut) {

        //prérequis : permut est une permutation des entiers {0,..,listeCoordPieces.size()-1}
        // (et donc permut peut être vide, mais pas null, si il n'y a pas de pièces)

        //retourne la solution obtenue en concaténant les plus courts chemins successifs pour attraper
        // les pièces dans l'ordre donné par this.permut, jusqu'à avoir k mouvements ou à avoir ramassé toutes les pièces de la permut.
        // Remarque : entre deux pièces consécutives, le plus court chemin n'est pas unique, n'importe quel plus court chemin est ok.
        //par ex, si le plateau est
        //.s.x
        //....
        //x..x
        //avec la pièce 0 en haut à droite, la pièce 1 en bas à gauche, et la pièce 2 en bas à droite,
        //et que permut = (0,2,1), alors pour
        //k=3, il faut retourner (0,1)(0,2)(0,3)(1,3)  (dans ce cas là les plus courts sont uniques)
        //k=10, il faut retourner (0,1)(0,2)(0,3)(1,3)(2,3)(2,2)(2,1)(2,0)  (dans ce cas là les plus courts sont aussi uniques,
        // et on s'arrête avant d'avoir fait k pas car on a tout collecté)

        //a compléter

        int nbPas = this.getK();
        Coord actualPosition = this.getStartingP();
        Solution path = new Solution(this.getStartingP());
        int indexPiece = 0;
        while(nbPas > 0){
            if(actualPosition.getL() < listeCoordPieces.get(permut.get(indexPiece)).getL()){
                actualPosition = new Coord(actualPosition.getL()+1, actualPosition.getC());
            }else if(actualPosition.getL() > listeCoordPieces.get(permut.get(indexPiece)).getL()){
                actualPosition = new Coord(actualPosition.getL()-1, actualPosition.getC());
            }else if(actualPosition.getC() < listeCoordPieces.get(permut.get(indexPiece)).getC()){
                actualPosition = new Coord(actualPosition.getL(), actualPosition.getC()+1);
            }else if(actualPosition.getC() > listeCoordPieces.get(permut.get(indexPiece)).getC()){
                actualPosition = new Coord(actualPosition.getL(), actualPosition.getC()-1);
            }
            if(actualPosition.equals(listeCoordPieces.get(permut.get(indexPiece)))){
                this.retirerPiece(actualPosition);
            }
            nbPas -= 1;
            path.add(actualPosition);
        }


        return path;
    }



    /************************************************
     **** fin algo algo greedy                      ******
     *************************************************/



    public int borneSup(){
        //soit d0 la distance min entre la position de départ et une pièce
        //soit {d1,..,dx} l'ensemble des distances entre pièces (donc x = (nbpiece-1)*npbpiece / 2), triées par ordre croissant
        //retourne le nombre de pièces que l'on capturerait avec k pas dans le cas idéal où
        //toutes les pièces seraient disposées sur une ligne ainsi : (avec sp le point de départ à gauche)
        //sp .... p .. p .... p ....... p ...
        //    d0    d1    d2      d3
        //(vous pouvez réfléchir au fait que c'est bien une borne supérieure)
        //(pour des exemples précis, cf les tests)

        int nbPiece = this.listeCoordPieces.size();

        ArrayList<Integer> listDistances = new ArrayList<>();
        ArrayList<Integer> listDistancesAPremierePiece = new ArrayList<>();

        ArrayList<ArrayList<Coord>> listCoordPres = new ArrayList<>(); //ça devrait être une liste (pour chaque piece) de liste pour chaque tuple
        //DONC liste de liste de liste (car le dernier liste correspond aux tuples de coordonnées)

        //ArrayList<Tuple> // à utiliser à la place

        for (int p = 0 ; p < nbPiece; p ++){

            listDistancesAPremierePiece.add(this.startingP.distanceFrom(this.listeCoordPieces.get(p)));


            ArrayList<Coord> coordTemp = new ArrayList<>();
            /*
            coordTemp.add(this.startingP);
            coordTemp.add(this.listeCoordPieces.get(p));
            //Pas besoin car on va jamais itérer vérifier plusieurs fois la dist startingP-Piece
            */
            listCoordPres.add(coordTemp);

            Coord pieceCoord = this.listeCoordPieces.get(p);
            // double for
            // Vérifier si présent dans listCoordPres si non, ajouter la distance dans listDistance
            for (int p2 =0; p< nbPiece; p2++){
                Coord pieceProche = this.listeCoordPieces.get(p2);
                System.out.println("here");
                //On check dans la liste de liste si la coordonnée n'existe pas déjà
                if(!listCoordPres.get(p).isEmpty()){

                    for(ArrayList<Coord> coords : listCoordPres){

                        Coord coord1 = coords.get(0);
                        Coord coord2 = coords.get(1);
                        System.out.println(coord1);
                        System.out.println(coord2);

                        if(!(coord1.equals(pieceCoord) && coord2.equals(pieceProche) || coord1.equals(pieceProche) && coord2.equals(pieceCoord))){
                            listDistances.add(pieceCoord.distanceFrom(pieceProche));

                            ArrayList<Coord> tupleTemporaire = new ArrayList<>();
                            coordTemp.add(pieceProche);
                            coordTemp.add(pieceCoord);

                            listCoordPres.add(coordTemp);
                        }
                    }
                }
            }
        }

        Collections.sort(listDistances);
        Collections.sort(listDistancesAPremierePiece);


        int max = 0;
        int pas = this.getK() - listDistancesAPremierePiece.get(0);
        int index = 0;
        while(pas > 0){
            max++;
            pas = pas - listDistances.get(index);
            index++;
        }

        return max;



        /*
        for (int p = 0 ; p < nbPiece; p ++){

            listDistanceOfEachPieces.add(new ArrayList<Integer>());

            for (Coord p2 : this.listeCoordPieces){
                if (!this.listeCoordPieces.get(p).equals(p2)){
                    listDistanceOfEachPieces.get(p).add(this.listeCoordPieces.get(p).distanceFrom(p2));
                }
            }
            Collections.sort(listDistanceOfEachPieces.get(p));
        }

        int max = 0;
        int pas = 0;
        int j = 0;
        int maxTemp = 0;

        for (int i = 0; i < listDistanceOfEachPieces.size(); i++){

            maxTemp = 0;
            pas = this.k - this.startingP.distanceFrom(listeCoordPieces.get(i));
            j = 0;

            while (pas > 0 && j <= listDistanceOfEachPieces.get(i).size()){
                maxTemp = maxTemp+1;
                pas = pas - listDistanceOfEachPieces.get(i).get(j);
                j++;
            }

            max = Math.max(maxTemp, max);

        }

        return max;
        */
    }
}