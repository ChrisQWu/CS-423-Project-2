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
        varySelection();
        varyCrossover();
        varyMutationRate();
        varyCrossoverRate();
        island();
        elitism();
    }

    /**
     * Sets up the islands and evenly distributes the total population across each island.
     * Then runs each island for 10 iterations.
     * Then takes the all the islands, and redistributes the population back on the islands.
     * For now the redistribution will be the first island with all of the best, the second island with the next
     * interval best, and etc.
     */

    /* TODO:1) run every selection type with crossover and mutation constant
            2) Every crossover with selection and mutation constant
            3) different mutation rates
            4) different crossover rates
            5) Island GA
            6) different elitism values (0% or 2%) (OPTIONAL)
            7) Record best/worst/avg warrior as .RED and for a csv for the fitness values PER generation
                Add to constants for the filename used for them, and add folders for each step
    */

    /**
     * Run GA with varied selections
     */
    private void varySelection()
    {
        Constants.folder = "Selection";
        Constants.type = "Random_OnePoint_Mutation";

        new Population(1000, 0.5, 0.0001,
                Constants.SELECTION_MODE.RANDOM,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION);
        Constants.type = "Roulette_OnePoint_Mutation";
        new Population(1000, 0.5, 0.0001,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION);
        Constants.type = "Tournament_OnePoint_Mutation";
        new Population(1000, 0.5, 0.0001,
                Constants.SELECTION_MODE.TOURNAMENT,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION);
    }

    private void varyCrossover()
    {
        Constants.folder = "Crossover";

        Constants.type = "Roulette_OnePoint_Mutation";
        new Population(1000, 0.5, 0.0001,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION);
        Constants.type = "Roulette_Uniform_Mutation";
        new Population(1000, 0.5, 0.0001,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.UNIFORM_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION);
        Constants.type = "Roulette_No_Mutation";
        new Population(1000, 0.5, 0.0001,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.NO_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION);
    }

    private void varyMutationRate()
    {
        Constants.folder = "Mutation_Rate";

        Constants.type = "Roulette_OnePoint_Zero";
        new Population(1000, 0.5, 0,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION);
        Constants.type = "Roulette_Uniform_NonZero";
        new Population(1000, 0.5, 0.0001,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION);
    }

    private void varyCrossoverRate()
    {
        Constants.folder = "Crossover_Rate";

        Constants.type = "Roulette_Half_Mutation";
        new Population(1000, 0.5, 0.0001,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION);
        Constants.type = "Roulette_Quarter_Mutation";
        new Population(1000, 0.25, 0.0001,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION);
    }

    private void island() {
        Constants.folder = "Island";

        Constants.type = "Roulette_OnePoint_Mutation_Island";
        int numberIsland = 10;
        int population_size = 1000;
        int totalIterations = 1000;
        int seedIterations = totalIterations/10;
        Population[] islands = new Population[numberIsland];
        ArrayList<Genome> allWarriors = new ArrayList<>();
        for (Population p:islands) {
            p = new Population(population_size/numberIsland, 0.5, 0.0001,
                    Constants.SELECTION_MODE.ROULETTE,
                    Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                    Constants.MUTATION_MODE.MUTATION);
            p.start(seedIterations);
            allWarriors.addAll(p.getCurrentPopulationAndEmpty());
        }
        Collections.sort(allWarriors,new FitnessComparator());

        for (int i = 0, j=0; i < population_size-population_size/numberIsland; i+=population_size/numberIsland,j++) {
            islands[j].setCurrentPopulation(allWarriors.subList(i,i+population_size/numberIsland));
        }

        allWarriors.clear();
        for (Population p:islands) {
            p.start(totalIterations-seedIterations);
        }
    }

    private void elitism()
    {
        Constants.folder = "Elitism";

        Constants.type = "Roulette_OnePoint_Mutation_Elite";
        new Population(1000, 0.5, 0.0001,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION);

        Constants.type = "Roulette_OnePoint_Mutation_NoElite";
        Constants.ELITISM = 0;
        new Population(1000, 0.25, 0.0001,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION);
    }
}
