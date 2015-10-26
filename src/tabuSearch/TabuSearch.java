package tabuSearch;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import lteStructure.Packet;

public class TabuSearch {

	private static ArrayList<Packet> instance = new ArrayList<Packet>();
    private static ArrayList<TabuList> tabuGlobal = new ArrayList<TabuList>();
    private static int solutionID = 0, no=0;
    static long antes, depois;
    
    private static String pc = "\\";

        public static void main(){

            Solution a = makeInitialSolution();
            tabuSearch(a);

            depois = System.currentTimeMillis();
            System.out.println("\n");
            System.out.println(" Tempo de execucao = " + ((double)(depois - antes)/1000) + " segundos");

            System.exit(0);
            
        }

    private static Solution makeInitialSolution(){
        Solution solutionInitial = new Solution();
        ArrayList<Packet> instanceTmp = new ArrayList<Packet>(instance);        

        int objetiveValue = instanceTmp.size();

        solutionInitial.setId(solutionID);
        solutionInitial.setObjValue(objetiveValue);
        solutionID = solutionID + 1;

        int binId = 0;

        no++;
        return solutionInitial;
    }

    private static Solution makeSolutionTwoOpt(Solution old){
        ArrayList<TabuList> tabuLocal = new ArrayList<TabuList>();
        ArrayList<Solution> tenSolutions = new ArrayList<Solution>();
        TabuList tmpTabu = null;
        TabuList bestTabu = null;
        Solution oldSol = new Solution();
        oldSol = old;

        int n=0, p=0;

        while(tenSolutions.size() < 100){
            Solution newSolution = new Solution();



            newSolution.setId(solutionID);
            solutionID = solutionID + 1;

//            n = getRandomNumber();
//            p = getRandomNumber();
//
//            while (p == n) {
//                p = getRandomNumber();
//            }
//
//            if (tabuLocal.size() > 0) {
//                while (p == n || isTabu(tabuLocal,n,p) == true || isTabu(tabuGlobal,n,p) == true){
//                    n = getRandomNumber();
//                    p = getRandomNumber();
//                }
//            }

            no++;
            tmpTabu = new TabuList();

            tmpTabu.setMoveA(n);
            tmpTabu.setMoveB(p);
            tmpTabu.setGenSolutionId(solutionID -1);
            tabuLocal.add(tmpTabu);

            calcObjFunction(newSolution);

            tenSolutions.add(newSolution);
        }

        Solution bestSolution = new Solution();

        int a = 0, b=instance.size();
        for (Solution y : tenSolutions){
            a = y.getObjValue();
            if (a < b){
                b = y.getObjValue();
                bestSolution = y;
            }
        }

        bestTabu = new TabuList();

        for (TabuList z : tabuLocal){
            if (bestSolution.getId() == z.getGenSolutionId()){
                bestTabu = z;
                tabuGlobal.add(bestTabu);
            }
        }

        return bestSolution;
    }

    /**
     * @param newSolution
     */
    private static void calcObjFunction(Solution newSolution) {
       

       // newSolution.setObjValue(fo);
    }

    private static boolean isTabu(ArrayList<TabuList> l, int n, int p) {

        boolean isTabuMov = false;

        for (TabuList a : l){
            if ( a.getMoveA() == n | a.getMoveA() == p &&  a.getMoveB() == n | a.getMoveB() == p ){
                isTabuMov = true;
                return isTabuMov;
            }
        }

        return isTabuMov;
    }

    private static void tabuSearch(Solution initialSolution) {

        Solution solucaoLocal = initialSolution;
        Solution betterNeighbourSolution = new Solution();

        int funcaoObjetivoLocal = initialSolution.getObjValue();
        Solution melhorSolucao = new Solution();

        System.out.println("\n\nSolucao Inicial pior caso ID  = " + solucaoLocal.getId() + " | FO = " + solucaoLocal.getObjValue() + "\n");

        int count=0;
        while(count < 10000) {

            betterNeighbourSolution = makeSolutionTwoOpt(solucaoLocal);

            if (betterNeighbourSolution.getObjValue() < funcaoObjetivoLocal) {
                funcaoObjetivoLocal = betterNeighbourSolution.getObjValue();
                melhorSolucao = betterNeighbourSolution;
            }

            solucaoLocal = betterNeighbourSolution;

            if (tabuGlobal.size() > 15){
                tabuGlobal.remove(0);
                tabuGlobal.remove(1);
            }

            count++;
        }

        System.out.println("-------------------------");
        System.out.println("Solucao Final ID = " + melhorSolucao.getId() + " | FO = " + melhorSolucao.getObjValue() );
        System.out.println(" Nro Solucoes geradas ao todo = " + no);
        //printSolution(melhorSolucao);

    }
  }