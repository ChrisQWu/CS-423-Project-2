
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Created by thebaker on 2/24/17.
 */
public class Population {
    private final int POPULATION_SIZE;
    private final Random random = new Random();
    private PriorityQueue<Genome> currentpopulation;
    private PriorityQueue<Genome> newPopulation;
    private Comparator<Genome> fitnessComparator;

    Population()
    {
        this.POPULATION_SIZE = 1000;
        new Population(POPULATION_SIZE);
    }

    Population(int POPULATION_SIZE){
        this.POPULATION_SIZE = POPULATION_SIZE;
        fitnessComparator = new FitnessComparator();
        currentpopulation = new PriorityQueue<>(POPULATION_SIZE, fitnessComparator);
        newPopulation = new PriorityQueue<>(POPULATION_SIZE, fitnessComparator);
        generatePopulation();
    }

    /**
     * initialize currentPopulation with random genomes.
     */
    private void generatePopulation()
    {
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Genome genome = new Genome(random, i);
            Warrior.makeWarrior(genome);
            float fitness = CommandLine.fitness();
            if(fitness>1) {
                System.out.println("Fitness: " + fitness);
                genome.printGenome();
            }
            genome.setFitness(fitness);
            currentpopulation.add(genome);
        }
    }

    private void generateNewPopulation(Constants.SELECTION_MODE selection_mode,
                                       Constants.CROSSOVER_MODE crossover_mode){
        switch (selection_mode){
            case RANDOM:
                selectRandom();
                break;
            case ROULETTE:
                selectRoulette();
                break;
            case TOURNAMENT:
                selectTournament();
                break;
            default:
                System.out.println("Invalid Selection Mode");
                break;
        }
        switch (crossover_mode)
        {
            case NO_CROSSOVER:
                break;
            case ONE_POINT_CROSSOVER:
                break;
            case UNIFORM_CROSSOVER:
                break;
            default:
                break;
        }
    }

    /**
     * Take the top 75% of the population
     */
    private void selectRandom()
    {
        int top = POPULATION_SIZE-(int)(POPULATION_SIZE*(0.75));
        for(int i=0; i<top; i++)
        {
            newPopulation.add(currentpopulation.poll());
        }
        for (int i=top; i<POPULATION_SIZE; i++)
        {
            //do replacements or something
        }

    }

    private void selectRoulette()
    {

    }

    private void selectTournament()
    {

    }

    /**
     * Evaluates each genome in the current population
     */
    private void evaluatePopulation()
    {
        for (Genome g:newPopulation) {
            Warrior.makeWarrior(g);
            float fitness = CommandLine.fitness();
            System.out.println("id: "+g.getId()+" fitness: "+fitness);
            g.setFitness(fitness);
        }
    }

    protected class FitnessComparator implements Comparator<Genome>
    {
        @Override
        public int compare(Genome genome1, Genome genome2) {
            if(genome1.getFitness() > genome2.getFitness()) return 1;
            else if(genome1.getFitness() < genome2.getFitness()) return -1;
            return 0;
        }
    }

}
