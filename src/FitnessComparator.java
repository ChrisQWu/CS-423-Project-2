import java.util.Comparator;

/**
 * Created by thebaker on 3/4/17.
 */
public class FitnessComparator implements Comparator<Genome> {
    @Override
    public int compare(Genome genome1, Genome genome2) {
        if (genome1.getFitness() < genome2.getFitness()) return 1;
        else if (genome1.getFitness() > genome2.getFitness()) return -1;
        return 0;
    }
}