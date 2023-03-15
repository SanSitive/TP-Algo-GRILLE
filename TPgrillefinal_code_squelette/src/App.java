

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

class App {

    public static Instance randomGenerator1(int size, int proba) {
        //prérequis proba >= 1
        //génère instance de taille size * size avec
        //pieces tirees au hasard avec proba 1/proba pour chaque case

        boolean[][] p4 = new boolean[size][size];
        Random r = new Random();
        for (int i = 0; i < p4.length; i++) {
            for (int j = 0; j < p4[0].length; j++) {
                if (r.nextInt(proba) == 0) {
                    p4[i][j] = true;
                }
            }
        }
        Coord sp4 = new Coord(0, 0);
        int k4 = p4.length * p4.length / 10;
        return new Instance(p4, sp4, k4);
    }

    public static Instance randomGenerator2(int size) {


    // génèr instance de taille size*size avec des paquets de pieces dont
    // - la position du coin haut gauche est tirée au hasard
    // - la hauteur et largeur de chaque paquet est tirée au hasard
    boolean[][] p5 = new boolean[size][size];
    Random r = new Random();
    int nbpaquets = r.nextInt(p5.length * 3 / 2);
    for(int i = 0;    i<nbpaquets;i++)    {
        //on tire coin haut gauche du rectangle, et ses dimensions
        int l = r.nextInt(p5.length);
        int c = r.nextInt(p5[0].length);
        int larg = r.nextInt(p5.length / 5);
        int haut = r.nextInt(p5[0].length / 5);
        larg = Math.min(larg, p5[0].length - c);
        haut = Math.min(haut, p5.length - l);
        for (int ll = l; ll < l + haut; ll++) {
            for (int cc = c; cc < c + larg; cc++) {
                p5[ll][cc] = true;
            }
        }
    }

    Coord sp5 = new Coord(0, 0);
    int k5 = p5.length * p5[0].length / 10;
    return new Instance(p5, sp5, k5);
    }
    //System.out.println("in5 \n" + in5);
    public static void main(String[] args) {


        /************************************************
         **** petite instance pour jouer ******
         *************************************************/
        //une instance ou greedy n'est pas optimal
        boolean[][] p1 = {
                {true, false, false, false, true},
                {false, false, false, false, false},
                {false, false, false, true, true},
        };


        Coord sp1 = new Coord(1, 1);
        int k1 = 6;
        Instance in1 = new Instance(p1, sp1, k1);
        System.out.println("instance in1 :\n" + in1);

        Solution s1 = new Solution();
        s1.add(sp1);
        s1.add(new Coord(2,3));
        s1.add(new Coord(2,4));
        s1.add(new Coord(1,4));
        System.out.println("\n\n\nafficahge de la solution s1 :\n" + in1.toString(s1));

        Solution sg1 = Algos.greedySolver(in1);
        Solution sfpt1 = Algos.algoFPT1(new InstanceDec(in1,3));
        System.out.println("valeur de greedy : " + in1.evaluerSolution(sg1));
        if(sfpt1 == null)
            System.out.println("valeur de fpt :  null");
        else
            System.out.println("valeur de fpt : " + in1.evaluerSolution(sfpt1));


        /************************************************
         **** tests sur instances  plus grandes        ******
         *************************************************/

        /////////////////////////////////////////////////
        // définition des instances  ////////////////////
        /////////////////////////////////////////////////


        // in2 : avec pieces dans la diagonale
        /*
        boolean[][] p2 = new boolean[40][40];
        for (int i = 0; i < p2.length; i++) {
            p2[i][i] = true;
        }
        Coord sp2 = new Coord(0, 0);
        int k2 = p2.length/2;
        Instance in2 = new Instance(p2, sp2, k2);
        System.out.println("in2 \n" + in2);*/


        // in6 : une instance que l'on configure comme on souhaite

        /*boolean[][] p6 = new boolean[40][40];
        int[] l6 = {2,2,5,8,10,15,15,15,15,15,16,36,38,39};
        int[] c6 = {1,4,20,2,6,15,16,17,18,19,19,36,38,39};

        for (int i = 0; i < l6.length; i++) {
            p6[l6[i]][c6[i]] = true;
        }
        Coord sp6 = new Coord(13,8);
        int k6 = 60;
        Instance in6 = new Instance(p6,sp6,k6);
        int sg6 = in6.evaluerSolution(Algos.greedySolver(in6));
        int sfpt6 = in6.evaluerSolution(Algos.algoFPT1DPClient(new InstanceDec(in6,sg6+1)));
        System.out.println("greedy6 " + sg6 + " sfpt6 " + sfpt6);*/


        /************************************************
         **** comparaison des algos        ******
         *************************************************/
        /*
        int sommeG = 0;
        int sommeL = 0;
        for(int i=0;i<20;i++){
            Instance inst = randomGenerator1(10,10);
            sommeG += inst.evaluerSolution(Algos.greedySolver(inst));
            FactorySolPermut facto = new FactorySolPermut(inst);
            sommeL += inst.evaluerSolution(HillClimbing.hillClimbingWithRestart(facto,10));
        }
        System.out.println("somme G " + sommeG + " somme L " + sommeL);
        */
    }
}
