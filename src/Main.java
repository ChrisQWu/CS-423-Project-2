import java.util.List;
import java.util.Random;

/**
 * Created by thebaker on 2/24/17.
 *  Entry point for the program. It's main use at the moment is to test out GA
 */
public class Main {
    private final int POPULATION_SIZE = 10;
    private final Random random = new Random();

    private List<Genome> population;

    public static void main(String [] args)
    {
        new Main();
    }

    public Main()
    {
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Genome genome = new Genome(random,i);
            genome.printGenome();
        }
    }
}
