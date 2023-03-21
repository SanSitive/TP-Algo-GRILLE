

import java.util.*;

public class Algos {

    public static boolean egalEnsembliste(ArrayList<?> a1, ArrayList<?> a2){
        //retourn vrai ssi les a1 à les même éléments que a2 (peut importe l'ordre)
        return a1.containsAll(a2) && a2.containsAll(a1);
    }


    public static Solution greedySolver(Instance i) {

        //calcule la solution obtenue en allant chercher à chaque étape la pièce restante la plus proche
        //(si plusieurs pièces sont à la même distance, on fait un choix arbitraire)

        return i.calculerSol(i.greedyPermut());
    }


    public static Solution algoFPT1(InstanceDec id) {
        //algorithme qui décide id (c'est à dire si opt(id.i) >= id.c) en branchant (en 4^k) dans les 4 directions pour chacun des k pas
        //retourne une solution de valeur >= c si une telle solution existe, et null sinon
        //Ne doit pas modifier le paramètre
        //Rappel : si c==0, on peut retourner la solution égale au point de départ puisque l'on est pas obligé d'utiliser les k pas
        // (on peut aussi retourner une solution plus longue si on veut)
        //Remarque : quand vous aurez codé la borneSup, pensez à l'utiliser dans cet algorithme pour ajouter un cas de base

        //à compléter

        Solution res = null;
        if(id.c == 0){
            return new Solution(id.i.getStartingP());
        }else{
            System.out.println(id.i);
            Solution s = new Solution(id.i.getStartingP());
            res = algoFPT4CheminsAux(id,s);
        }
        System.out.println(res);
        return res;
    }

    //Fonction annexe
    public static Solution algoFPT4CheminsAux(InstanceDec id, Solution s){
        Coord actualP = id.i.getStartingP();
        int seuil = id.c;

        Solution pHaut = null;
        Solution pBas = null;
        Solution pGauche = null;
        Solution pDroite = null;

        //Si l'on a trouvé une solution
        if(seuil == 0){
            System.out.println("on a un chemin");
            return s;
        }else{
            //si l'on a utilisé tous nos pas et que l'on a donc pas de solution
            if(id.i.getK() == 0 && seuil != 0){
                System.out.println("k == 0");
                return null;
            }else{
                if(id.i.piecePresente(id.i.getStartingP())){
                    System.out.println("on prend une piece");
                    id.i.retirerPiece(id.i.getStartingP());
                    seuil -= 1;
                }
                //On fait un pas donc un decrease le k
                id.i.setK(id.i.getK()-1);

                //On va dans les 4 directions possibles (si l'on peut vis a vis de la grille)
                //Et on compare les résultats (null ou un chemin existe)

                if(id.i.positionIsValide(new Coord(actualP.getL()-1, actualP.getC()))){ //HAUT
                    System.out.println("h");
                    id.i.setStartingP(new Coord(actualP.getL()-1, actualP.getC())); //On déplace le startingPoint à la nouvelle position
                    InstanceDec haut = new InstanceDec(new Instance(id.i),seuil); //On crée la nouvelle instance utilisé
                    Solution sHaut = new Solution(); //On crée la nouvelle solution en copiant l'ancienne + rajout de la nouvelle position
                    for(Coord c : s){
                        sHaut.add(c);
                    }
                    sHaut.add(id.i.getStartingP());
                    pHaut = algoFPT4CheminsAux(haut,sHaut);

                }
                if(id.i.positionIsValide(new Coord(actualP.getL()+1, actualP.getC()))){ //BAS
                    System.out.println("b");
                    id.i.setStartingP(new Coord(actualP.getL()+1, actualP.getC()));
                    InstanceDec bas = new InstanceDec(new Instance(id.i),seuil);
                    Solution sBas = new Solution(); //On crée la nouvelle solution en copiant l'ancienne + rajout de la nouvelle position
                    for(Coord c : s){
                        sBas.add(c);
                    }
                    sBas.add(id.i.getStartingP());
                    pBas = algoFPT4CheminsAux(bas,sBas);

                }
                if(id.i.positionIsValide(new Coord(actualP.getL(), actualP.getC()-1))){ //GAUCHE
                    System.out.println("g");
                    id.i.setStartingP(new Coord(actualP.getL(), actualP.getC()-1));
                    InstanceDec gauche = new InstanceDec(new Instance(id.i),seuil);
                    Solution sGauche = new Solution(); //On crée la nouvelle solution en copiant l'ancienne + rajout de la nouvelle position
                    for(Coord c : s){
                        sGauche.add(c);
                    }
                    sGauche.add(id.i.getStartingP());
                    pGauche = algoFPT4CheminsAux(gauche,sGauche);

                }
                if (id.i.positionIsValide(new Coord(actualP.getL(), actualP.getC()+1))){ //DROITE
                    System.out.println("d");
                    id.i.setStartingP(new Coord(actualP.getL(), actualP.getC()+1));
                    InstanceDec droite = new InstanceDec(new Instance(id.i),seuil);
                    Solution sDroite = new Solution(); //On crée la nouvelle solution en copiant l'ancienne + rajout de la nouvelle position
                    for(Coord c : s){
                        sDroite.add(c);
                    }
                    sDroite.add(id.i.getStartingP());
                    pDroite = algoFPT4CheminsAux(droite,sDroite);
                }
            }
        }

        if(pHaut != null){
            return pHaut;
        }else if(pBas != null){
            return pBas;
        }else if(pGauche != null){
            return pGauche;
        }else{
            return pDroite;
        }

    }




    public static Solution algoFPT1DP(InstanceDec id,  HashMap<InstanceDec,Solution> table) {
        //même spécification que algoFPT1, si ce n'est que
        // - si table.containsKey(id), alors id a déjà été calculée, et on se contente de retourner table.get(id)
        // - sinon, alors on doit calculer la solution s pour id, la ranger dans la table (table.put(id,res)), et la retourner

        //Remarques
        // - ne doit pas modifier l'instance id en param (mais va modifier la table bien sûr)
        // - même si le branchement est le même que dans algoFPT1, ne faites PAS appel à algoFPT1 (et donc il y aura de la duplication de code)


        //à compléter
        return null;
    }


    public static Solution algoFPT1DPClient(InstanceDec id){
        //si il est possible de collecter >= id.c pièces dans id.i, alors retourne une Solution de valeur >= c, sinon retourne null
        //doit faire appel à algoFPT1DP

        //à completer
        return null;

    }



}
