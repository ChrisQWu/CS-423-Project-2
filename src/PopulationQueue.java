import javax.management.Query;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by thebaker on 3/4/17.
 */
public class PopulationQueue<E> extends PriorityQueue<E> {
    private PriorityQueue<Genome> populationQueue;

    PopulationQueue()
    {
        this(1000);
    }

    PopulationQueue(int size)
    {
        populationQueue = new PriorityQueue<>(size, new FitnessComparator());
    }

    public void remove(int index)
    {
        List<Genome> populationList = new ArrayList<>();
        for (int i = 0; i < index-1; i++) {
            populationList.add(this.populationQueue.poll());
        }
        populationQueue.poll();
        populationQueue.addAll(populationList);

    }

    public class FitnessComparator implements Comparator<Genome> {
        @Override
        public int compare(Genome genome1, Genome genome2) {
            if (genome1.getFitness() < genome2.getFitness()) return 1;
            else if (genome1.getFitness() > genome2.getFitness()) return -1;
            return 0;
        }
    }
}
