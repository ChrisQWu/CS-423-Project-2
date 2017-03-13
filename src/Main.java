import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by thebaker on 2/24/17.
 * Entry point for the program. It's main use at the moment is to test out GA
 */
public class Main {

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
//        for (int i = 0; i < 5; i++) {
//            varySelection(i);
//        }
//        for (int i = 0; i < 5; i++) {
//            varyCrossover(i);
//        }
//        for (int i = 0; i < 5; i++) {
//            varyMutationRate(i);
//        }
//        for (int i = 0; i < 5; i++) {
//            varyCrossoverRate(i);
//        }

//        for (int i = 0; i < 10; i++) {
//             island(i);
//        }
//        for (int i = 0; i < 5; i++) {
//            elitism(i);
//        }
        bestOfTheBest();
    }


    /**
     * Run GA with varied selections
     */
    private void varySelection(int NUMBER) {
        Constants.folder = "Selection";

        Constants.type = "Random_OnePoint_Mutation_" + NUMBER;
        Constants.BEST_WARRIOR = Constants.folder + "/BEST_Warrior_" + Constants.type + "_";
        Constants.WORST_WARRIOR = Constants.folder + "/WORST_Warrior_" + Constants.type + "_";

        (new Population(100, 0.5, 0.01,
                Constants.SELECTION_MODE.RANDOM,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION)).start();
        Constants.type = "Roulette_OnePoint_Mutation_" + NUMBER;
        (new Population(100, 0.5, 0.01,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION)).start();
        Constants.type = "Tournament_OnePoint_Mutation_" + NUMBER;
        (new Population(100, 0.5, 0.01,
                Constants.SELECTION_MODE.TOURNAMENT,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION)).start();
    }

    private void varyCrossover(int NUMBER) {
        Constants.folder = "Crossover";

        Constants.type = "Roulette_OnePoint_Mutation_" + NUMBER;
        Constants.BEST_WARRIOR = Constants.folder + "/BEST_Warrior_" + Constants.type + "_";
        Constants.WORST_WARRIOR = Constants.folder + "/WORST_Warrior_" + Constants.type + "_";
        (new Population(100, 0.5, 0.01,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION)).start();
        Constants.type = "Roulette_Uniform_Mutation_" + NUMBER;
        Constants.BEST_WARRIOR = Constants.folder + "/BEST_Warrior_" + Constants.type + "_";
        Constants.WORST_WARRIOR = Constants.folder + "/WORST_Warrior_" + Constants.type + "_";
        (new Population(100, 0.5, 0.01,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.UNIFORM_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION)).start();
        Constants.type = "Roulette_No_Mutation_" + NUMBER;
        Constants.BEST_WARRIOR = Constants.folder + "/BEST_Warrior_" + Constants.type + "_";
        Constants.WORST_WARRIOR = Constants.folder + "/WORST_Warrior_" + Constants.type + "_";
        (new Population(100, 0.5, 0.01,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.NO_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION)).start();
    }

    private void varyMutationRate(int NUMBER) {
        Constants.folder = "Mutation_Rate";

        Constants.type = "Roulette_OnePoint_Zero_" + NUMBER;
        Constants.BEST_WARRIOR = Constants.folder + "/BEST_Warrior_" + Constants.type + "_";
        Constants.WORST_WARRIOR = Constants.folder + "/WORST_Warrior_" + Constants.type + "_";
        (new Population(100, 0.5, 0,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION)).start();
        Constants.type = "Roulette_Uniform_NonZero_" + NUMBER;
        Constants.BEST_WARRIOR = Constants.folder + "/BEST_Warrior_" + Constants.type + "_";
        Constants.WORST_WARRIOR = Constants.folder + "/WORST_Warrior_" + Constants.type + "_";
        (new Population(100, 0.5, 0.01,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION)).start();
        Constants.type = "Roulette_Uniform_Greater_" + NUMBER;
        Constants.BEST_WARRIOR = Constants.folder + "/BEST_Warrior_" + Constants.type + "_";
        Constants.WORST_WARRIOR = Constants.folder + "/WORST_Warrior_" + Constants.type + "_";
        (new Population(100, 0.5, 0.025,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION)).start();
    }

    private void varyCrossoverRate(int NUMBER) {
        Constants.folder = "Crossover_Rate";

        Constants.type = "Roulette_Half_Mutation_" + NUMBER;
        Constants.BEST_WARRIOR = Constants.folder + "/BEST_Warrior_" + Constants.type + "_";
        Constants.WORST_WARRIOR = Constants.folder + "/WORST_Warrior_" + Constants.type + "_";
        (new Population(100, 0.5, 0.01,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION)).start();
        Constants.type = "Roulette_Quarter_Mutation_" + NUMBER;
        Constants.BEST_WARRIOR = Constants.folder + "/BEST_Warrior_" + Constants.type + "_";
        Constants.WORST_WARRIOR = Constants.folder + "/WORST_Warrior_" + Constants.type + "_";
        (new Population(100, 0.25, 0.01,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION)).start();
    }

    /**
     * Sets up the islands and evenly distributes the total population across each island.
     * Then runs each island for 10 iterations.
     * Then takes the all the islands, and redistributes the population back on the islands.
     * For now the redistribution will be the first island with all of the best, the second island with the next
     * interval best, and etc.
     */
    private void island(int NUMBER) {
        Constants.folder = "Island";

        Constants.type = "Roulette_OnePoint_Mutation_Island_" + NUMBER;
        Constants.BEST_WARRIOR = Constants.folder + "/BEST_Warrior_" + Constants.type + "_";
        Constants.WORST_WARRIOR = Constants.folder + "/WORST_Warrior_" + Constants.type + "_";
        int numberIsland = 10;
        int population_size = 1000;
        int totalIterations = 100;
        int seedIterations = totalIterations / 10;
        Population[] islands = new Population[numberIsland];
        ArrayList<Genome> allWarriors = new ArrayList<>();
        for (int j = 0; j < numberIsland; j++) {
            islands[j] = new Population(population_size / numberIsland, 0.5, 0.01,
                    Constants.SELECTION_MODE.ROULETTE,
                    Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                    Constants.MUTATION_MODE.MUTATION);
            islands[j].start(seedIterations);
            allWarriors.addAll(islands[j].getCurrentPopulationAndEmpty());
        }
        Collections.sort(allWarriors, new FitnessComparator());

        for (int j = 0; j < numberIsland; j++) {
            ArrayList<Genome> temp = new ArrayList<>();
            for(int i = 0; i<population_size/numberIsland; i++)
            {
                temp.add(allWarriors.remove(0));
            }
            islands[j].setCurrentPopulation(temp);
        }
        allWarriors.clear();
        for (int j = 0; j < numberIsland; j++) {
            islands[j].start(totalIterations - seedIterations);
        }
    }

    private void elitism(int NUMBER) {
        Constants.folder = "Elitism";

        Constants.type = "Roulette_OnePoint_Mutation_Elite_" + NUMBER;
        Constants.BEST_WARRIOR = Constants.folder + "/BEST_Warrior_" + Constants.type + "_";
        Constants.WORST_WARRIOR = Constants.folder + "/WORST_Warrior_" + Constants.type + "_";
        new Population(100, 0.5, 0.01,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION).start();

        Constants.type = "Roulette_OnePoint_Mutation_NoElite_" + NUMBER;
        Constants.BEST_WARRIOR = Constants.folder + "/BEST_Warrior_" + Constants.type + "_";
        Constants.WORST_WARRIOR = Constants.folder + "/WORST_Warrior_" + Constants.type + "_";
        Constants.ELITISM = 0;
        new Population(100, 0.5, 0.01,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION).start();
    }

    public void bestOfTheBest()
    {
        Constants.ELITISM = 0.2;

        while(true) {
            new Population(1000, 0.5, 0.01,
                    Constants.SELECTION_MODE.ROULETTE,
                    Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                    Constants.MUTATION_MODE.MUTATION).start(500);

            new Population(1000, 0.5, 0.01,
                    Constants.SELECTION_MODE.ROULETTE,
                    Constants.CROSSOVER_MODE.UNIFORM_CROSSOVER,
                    Constants.MUTATION_MODE.MUTATION).start(500);



            new Population(1000, 0.5, 0.01,
                    Constants.SELECTION_MODE.RANDOM,
                    Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                    Constants.MUTATION_MODE.MUTATION).start(500);


            new Population(1000, 0.5, 0.01,
                    Constants.SELECTION_MODE.RANDOM,
                    Constants.CROSSOVER_MODE.UNIFORM_CROSSOVER,
                    Constants.MUTATION_MODE.MUTATION).start(500);
        }
    }
}
