import java.util.Comparator;

class FitnessComparator implements Comparator<Genome> {

    @Override
    public int compare(Genome o1, Genome o2) {
        if (o1.getFitness() < o2.getFitness()) return 1;
        else if (o1.getFitness() > o2.getFitness()) return -1;
        else return 0;
    }
}