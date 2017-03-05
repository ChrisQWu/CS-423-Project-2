import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by thebaker on 2/24/17.
 * Entry point for the program. It's main use at the moment is to test out GA
 */
public class Main {

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        Population population = new Population();
        population.start();

    }

    /**
     * Sets up the islands and evenly distributes the total population across each island.
     * Then runs each island for 10 iterations.
     * Then takes the all the islands, and redistributes the population back on the islands.
     * For now the redistribution will be the first island with all of the best, the second island with the next
     * interval best, and etc.
     */
    private void island() {
        int island_number = 10;
        int total_population = 1000;
        int population_size = total_population / island_number;
        Population[] islands = new Population[island_number];

        for (Population p : islands) {//initialize all islands with their own population objects
            p = new Population(population_size, 0.5, 0.0001,
                    Constants.SELECTION_MODE.ROULETTE,
                    Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                    Constants.MUTATION_MODE.MUTATION);
            p.start(10);
        }

        List<Genome> seeding = new ArrayList<>();

        for (Population p : islands) {
            seeding.addAll(p.getCurrentPopulationAndEmpty());//beginning of the migration
        }

        List<Genome> seed = new ArrayList<>();
        for (int i = 0; i < island_number; i++) {//migrate intervals to each islands
            for (int j = 0; j < population_size; j++) {
                seed.add(seeding.get(0));
                seeding.remove(0);
            }
            islands[i].setCurrentPopulation(seed);
            seed.clear();
        }

        for (Population p : islands) {//now that the islands are ready, run ga on each
            p.start(990);
        }
    }
}
