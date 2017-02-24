import java.util.List;
import java.util.Random;

/**
 * Created by thebaker on 2/24/17.
 */
public class Population {
    private final int POPULATION_SIZE;
    private final Random random = new Random();
    private List<Genome> population;

    Population()
    {
        this.POPULATION_SIZE = 10;
        generatePopulation();
    }

    Population(int POPULATION_SIZE){
        this.POPULATION_SIZE = POPULATION_SIZE;
        generatePopulation();
    }

    private void generatePopulation()
    {
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Genome genome = new Genome(random,i, 10);
            genome.printGenome();
        }
    }


}
