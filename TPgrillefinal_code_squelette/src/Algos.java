

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

        Solution res = null;
        if(id.c == 0){
            return new Solution(id.i.getStartingP());
        }else{

            Solution s = new Solution(id.i.getStartingP());
            res = algoFPT4CheminsAux(id,s);
        }

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


        if(id.i.piecePresente(id.i.getStartingP())){
            id.i.retirerPiece(id.i.getStartingP());
            seuil -= 1;
        }

        //Si l'on a trouvé une solution
        if(seuil == 0){
            return s;
        }else {
            //si l'on a utilisé tous nos pas et que l'on a donc pas de solution
            if (id.i.getK() == 0 && seuil != 0) {
                return null;
            } else if (id.i.borneSup() < seuil) {
                return null;
            } else {

                //On fait un pas donc un decrease le k
                id.i.setK(id.i.getK() - 1);

                //On va dans les 4 directions possibles (si l'on peut vis a vis de la grille)
                //Et on compare les résultats (null ou un chemin existe)

                if (id.i.positionIsValide(new Coord(actualP.getL() - 1, actualP.getC()))) { //HAUT

                    id.i.setStartingP(new Coord(actualP.getL() - 1, actualP.getC())); //On déplace le startingPoint à la nouvelle position
                    InstanceDec haut = new InstanceDec(new Instance(id.i), seuil); //On crée la nouvelle instance utilisé
                    Solution sHaut = new Solution(); //On crée la nouvelle solution en copiant l'ancienne + rajout de la nouvelle position
                    for (Coord c : s) {
                        sHaut.add(c);
                    }
                    sHaut.add(id.i.getStartingP());
                    pHaut = algoFPT4CheminsAux(haut, sHaut);

                }
                if (id.i.positionIsValide(new Coord(actualP.getL() + 1, actualP.getC()))) { //BAS

                    id.i.setStartingP(new Coord(actualP.getL() + 1, actualP.getC()));
                    InstanceDec bas = new InstanceDec(new Instance(id.i), seuil);
                    Solution sBas = new Solution(); //On crée la nouvelle solution en copiant l'ancienne + rajout de la nouvelle position
                    for (Coord c : s) {
                        sBas.add(c);
                    }
                    sBas.add(id.i.getStartingP());
                    pBas = algoFPT4CheminsAux(bas, sBas);

                }
                if (id.i.positionIsValide(new Coord(actualP.getL(), actualP.getC() - 1))) { //GAUCHE

                    id.i.setStartingP(new Coord(actualP.getL(), actualP.getC() - 1));
                    InstanceDec gauche = new InstanceDec(new Instance(id.i), seuil);
                    Solution sGauche = new Solution(); //On crée la nouvelle solution en copiant l'ancienne + rajout de la nouvelle position
                    for (Coord c : s) {
                        sGauche.add(c);
                    }
                    sGauche.add(id.i.getStartingP());
                    pGauche = algoFPT4CheminsAux(gauche, sGauche);

                }
                if (id.i.positionIsValide(new Coord(actualP.getL(), actualP.getC() + 1))) { //DROITE

                    id.i.setStartingP(new Coord(actualP.getL(), actualP.getC() + 1));
                    InstanceDec droite = new InstanceDec(new Instance(id.i), seuil);
                    Solution sDroite = new Solution(); //On crée la nouvelle solution en copiant l'ancienne + rajout de la nouvelle position
                    for (Coord c : s) {
                        sDroite.add(c);
                    }
                    sDroite.add(id.i.getStartingP());
                    pDroite = algoFPT4CheminsAux(droite, sDroite);
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

        if (id.c > id.i.getK()+1 || id.i.borneSup() < id.c) {
            return null;
        } else if (id.c == 0) {
            return new Solution(id.i.getStartingP());
        } else {
            return algoFPT1DPAux(new Solution(), new InstanceDec(new Instance(id.i), id.c), table);
        }
    }

    private static Solution algoFPT1DPAux(Solution sol, InstanceDec id,  HashMap<InstanceDec,Solution> table) {
        if(!table.containsKey(id)) {
            if (!id.i.getStartingP().estDansPlateau(id.i.getNbL(), id.i.getNbC())) {
                table.put(id, null);
            } else {
                Solution s = new Solution(sol);
                s.add(id.i.getStartingP());

                if (id.i.piecePresente(id.i.getStartingP())) {
                    id.i.retirerPiece(id.i.getStartingP());
                    id.c--;
                }

                if (id.c <= 0){
                    table.put(id, s);
                } else if (id.i.getK() <= 0 || id.i.borneSup() < id.c) {
                    table.put(id, null);
                } else {
                    id.i.setK(id.i.getK() - 1);
                    Coord newCoordHaut = new Coord(id.i.getStartingP().getL() - 1, id.i.getStartingP().getC());
                    Solution sol1 = null;
                    if(newCoordHaut.estDansPlateau(id.i.getNbL(), id.i.getNbC())) {
                        Instance instanceDecHaut = new Instance(id.i);
                        instanceDecHaut.setStartingP(newCoordHaut);
                        InstanceDec idHaut = new InstanceDec(instanceDecHaut, id.c);
                        sol1 = algoFPT1DPAux(s, idHaut, table);
                    }

                    if (sol1 != null) {
                        table.put(id, sol1);
                    } else {
                        Coord newCoordBas = new Coord(id.i.getStartingP().getL() + 1, id.i.getStartingP().getC());
                        Solution sol2 = null;
                        if(newCoordBas.estDansPlateau(id.i.getNbL(), id.i.getNbC())) {
                            Instance instanceDecBas = new Instance(id.i);
                            instanceDecBas.setStartingP(newCoordBas);
                            InstanceDec idBas = new InstanceDec(instanceDecBas, id.c);
                            sol2 = algoFPT1DPAux(s, idBas, table);
                        }

                        if (sol2 != null) {
                            table.put(id, sol2);
                        } else {
                            Coord newCoordDroite = new Coord(id.i.getStartingP().getL(), id.i.getStartingP().getC() + 1);
                            Solution sol3 = null;
                            if(newCoordDroite.estDansPlateau(id.i.getNbL(), id.i.getNbC())) {
                                Instance instanceDecDroite = new Instance(id.i);
                                instanceDecDroite.setStartingP(newCoordDroite);
                                InstanceDec idDroite = new InstanceDec(instanceDecDroite, id.c);
                                sol3 = algoFPT1DPAux(s, idDroite, table);
                            }

                            if (sol3 != null) {
                                table.put(id, sol3);
                            } else {
                                Coord newCoordGauche = new Coord(id.i.getStartingP().getL(), id.i.getStartingP().getC() - 1);
                                Solution sol4 = null;
                                if(newCoordGauche.estDansPlateau(id.i.getNbL(), id.i.getNbC())) {
                                    Instance instanceDecGauche = new Instance(id.i);
                                    instanceDecGauche.setStartingP(newCoordGauche);
                                    InstanceDec idGauche = new InstanceDec(instanceDecGauche, id.c);
                                    sol4 = algoFPT1DPAux(s, idGauche, table);
                                }

                                if (sol4 != null)  {
                                    table.put(id, sol4);
                                } else {
                                    table.put(id, null);
                                }
                            }
                        }
                    }
                }
            }
        }
        return table.get(id);
    }


    public static Solution algoFPT1DPClient(InstanceDec id){
        //si il est possible de collecter >= id.c pièces dans id.i, alors retourne une Solution de valeur >= c, sinon retourne null
        //doit faire appel à algoFPT1DP

        HashMap<InstanceDec, Solution> table = new HashMap<>();
        return algoFPT1DP(id, table);
    }



}
