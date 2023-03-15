import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.*;

class TestsAutomatiques {

    public static void main(String[] args) {

        double note = 0;


        note +=runTest(TestsAutomatiques::testSolValideok, "testSolValideok",1);
        note +=runTest(TestsAutomatiques::testEvalSolBase,"testEvalSolBase",2);
        note +=runTest(TestsAutomatiques::testGreedyPermut1,"testGreedyPermut1",2);
        note +=runTest(TestsAutomatiques::testCalculerSol1,"testCalculerSol1",2);
        note +=runTest(TestsAutomatiques::testFPT1_1,"testFPT1_1",2);
        note +=runTest(TestsAutomatiques::testBorneSup_1,"testBorneInf_1",1);
        note +=runTest(TestsAutomatiques::testElemPermutHC_1,"testElemPermutHC_1",2);
        note +=runTest(TestsAutomatiques::testNbSteps_1,"testNbSteps_1",2);


        System.out.println("fin des tests : note = " + note);

    }

    public static double runTest(Callable<Double> r, String s, int bareme){
        ExecutorService executorService = Executors.newSingleThreadExecutor(); //si on submit à nouveau sans re créer ça timeout aussi pour tests suivants
        Future<Double> future = executorService.submit(r);
        double note = 0;
        try {
            // Wait for at most 5 seconds until the result is returned
            note+= future.get(1L, TimeUnit.SECONDS)*bareme; //get renvoie entre 0 et 1
            System.out.println("****************************************************");
            System.out.println(s + " terminé, note: " + note + "/" + bareme);
            System.out.println("****************************************************");

        } catch (TimeoutException e) {
            System.out.println("****************************************************");
            System.out.println(s + " timeout");
            System.out.println("****************************************************");
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("****************************************************");
            System.out.println(s + " erreur " + e.getMessage());
            System.out.println("****************************************************");
        }
        executorService.shutdownNow();
        return note;

    }


    private static double testSolValideok()
    {
        boolean[][] p0 = new boolean[4][4];
        Coord sp0 = new Coord(3, 0);
        Coord sp1 = new Coord(3,1);
        Coord sp2 = new Coord(3,2);

        int k0 = 2;
        Instance in0 = new Instance(p0, sp0, k0);
        Solution s = new Solution();
        s.add(sp0);
        s.add(sp1);
        s.add(sp2);
        if (in0.estValide(s))
            return 1;
        else
            return 0;
    }

    private static double testEvalSolBase()
    {
        boolean[][] p0 = {
            {true, false, false, false, true},
            {false, false, false, false, false},
            {false, false, false, true, false},
        };
        Coord sp0 = new Coord(2, 0);
        Coord sp1 = new Coord(2,1);
        Coord sp2 = new Coord(2,2);
        Coord sp3 = new Coord(2,3);

        int k0 = 4;
        Instance in0 = new Instance(p0, sp0, k0);
        Solution s = new Solution();
        s.add(sp0);
        s.add(sp1);
        s.add(sp2);
        s.add(sp3);


        if (in0.evaluerSolution(s)==1)
            return 1;
        else
            return 0;
    }

    private static double testGreedyPermut1()
    {
        boolean[][] p0 = {
                {false, false, false, false, true},
                {false, false, false, false, false},
                {true, false, false, false, true},
        };
        Coord sp0 = new Coord(0, 2);

        int k0 = 4;

        Instance in0 = new Instance(p0, sp0, k0);
        ArrayList<Integer> res = new ArrayList<>();
        res.add(0);
        res.add(2);
        res.add(1);

        if (in0.greedyPermut().equals(res))
            return 1;
        else
            return 0;
    }

    private static double testCalculerSol1()
    {

        //.s.x
        //....
        //x..x
        //avec la pièce 0 en haut à droite, la pièce 1 en bas à gauche, et la pièce 2 en bas à droite,
        //et que permut = (0,2,1), alors pour
        //k=3, il faut retourner (0,1)(0,2)(0,3)(1,3)  (dans ce cas là les plus courts sont uniques)
        //k=10, il fa

        boolean[][] p0 = {
                {false, false, false, true},
                {false, false, false, false},
                {false, false, false, false},
                {true, false, false, true}
        };
        Coord sp0 = new Coord(0, 1);
        int k0 = 3;
        Instance in0 = new Instance(p0, sp0, k0);
        ArrayList<Integer> permut = new ArrayList<>();
        permut.add(0);
        permut.add(2);
        permut.add(1);

        ArrayList<Coord> res = new ArrayList<>();
        res.add(new Coord(0,1));
        res.add(new Coord(0,2));
        res.add(new Coord(0,3));
        res.add(new Coord(1,3));
        if(in0.calculerSol(permut).equals(res))
            return 1;
        else
            return 0;
    }



    private static double testFPT1_1()
    {



        boolean[][] p0 = {
                {true, true, false, false, true}
        };
        Coord sp0 = new Coord(0, 3);
        int k0 = 3;
        Instance in0 = new Instance(p0, sp0, k0);
        InstanceDec id0 = new InstanceDec(in0,2);

        ArrayList<Coord> res = new ArrayList<>();
        res.add(new Coord(0,3));
        res.add(new Coord(0,2));
        res.add(new Coord(0,1));
        res.add(new Coord(0,0));
        Solution s =  Algos.algoFPT1(id0);
        if(s != null && s.equals(res))
            return 1;
        else
            return 0;
    }

    private static double testBorneSup_1()
    {



        boolean[][] p0 = {
                {true, false, false, true, true}};
        Coord sp0 = new Coord(0, 1);
        int k0 = 2;
        Instance in0 = new Instance(p0, sp0, k0);
        int binf  =  in0.borneSup();;
        if(binf == 2)
            return 1;
        else
            return 0;
    }


    private static double testElemPermutHC_1()
    {



        boolean[][] p0 = {
                {true, true},
                {false, false},
                {true, false},

        };
        Coord sp0 = new Coord(0, 0);
        int k0 = 3;
        Instance in0 = new Instance(p0, sp0, k0);
        ArrayList<Integer> permut = new ArrayList<>();
        permut.add(0);
        permut.add(1);
        permut.add(2);
        ElemPermutHC e = new ElemPermutHC(in0,permut);
        ArrayList<ElemPermutHC> res = e.getVoisinsImmediats();
        HashSet<ElemPermutHC> resset = new HashSet<>();
        resset.addAll(res);
        //on vérifie que pas de doublons, et bonne taille
        if((resset.size()==res.size() && (res.size()==3)))
            return 1;
        else
            return 0;
    }


    private static double testNbSteps_1()
    {



        boolean[][] p0 = {
                {true, true},
                {false, false},
                {true, true},

        };
        Coord sp0 = new Coord(0, 0);
        int k0 = 3;
        Instance in0 = new Instance(p0, sp0, k0);
        ArrayList<Integer> permut = new ArrayList<>();
        permut.add(0);
        permut.add(1);
        permut.add(2);
        permut.add(3);
        if(in0.nbStepsToCollectAll(permut)==5)
            return 1;
        else
            return 0;
    }


}
