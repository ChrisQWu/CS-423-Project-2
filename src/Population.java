
import java.util.*;

/**
 * Created by thebaker on 2/24/17.
 */
public class Population {
    private final int POPULATION_SIZE;
    private final Random random = new Random();
    private PriorityQueue<Genome> currentpopulation;
    private Comparator<Genome> fitnessComparator;
    private double crossoverRate;
    private double mutatationRate;

    Population() {
        this.POPULATION_SIZE = 1000;
        new Population(POPULATION_SIZE, 0.1, 0.001);
    }

    Population(int POPULATION_SIZE, double crossoverRate, double mutatationRate) {
        this.POPULATION_SIZE = POPULATION_SIZE;
        this.crossoverRate = crossoverRate;
        this.mutatationRate = mutatationRate;
        fitnessComparator = new FitnessComparator();
        currentpopulation = new PriorityQueue<>(POPULATION_SIZE, fitnessComparator);
        generatePopulation();
    }

    /**
     * initialize currentPopulation with random genomes.
     */
    private void generatePopulation() {
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Genome genome = new Genome(random, i);
            Warrior.makeWarrior(genome);
            float fitness = CommandLine.fitness();
            if (fitness > 1) {
                System.out.println("Fitness: " + fitness);
                genome.printGenome();
            }
            genome.setFitness(fitness);
            currentpopulation.add(genome);
        }
    }

    private void generateNewPopulation(Constants.SELECTION_MODE selection_mode,
                                       Constants.CROSSOVER_MODE crossover_mode) {
        switch (selection_mode) {
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
        switch (crossover_mode) {
            case NO_CROSSOVER:
                break;
            case ONE_POINT_CROSSOVER:
                break;
            case UNIFORM_CROSSOVER:
                break;
            default:
                break;
        }
        mutatePopulation();
    }

    /**
     * Take the top 75% of the population
     */
    private void selectRandom() {
        List<Genome> topPercent = new ArrayList<>();
        int top = POPULATION_SIZE - (int) (POPULATION_SIZE * (0.75));
        for (int i = 0; i < top; i++) {
            Genome g = currentpopulation.poll();
            topPercent.add(g);//save the good ones
        }
        currentpopulation.clear();//remove all of the lower fit genomes
        currentpopulation.addAll(topPercent);//put in the fit genomes back
        for (int i = top; i < POPULATION_SIZE; i++) {
            Genome g = topPercent.get(random.nextInt(top));
            currentpopulation.add(g);//randomly put fit genomes in
        }
    }

    private void selectRoulette() {
        float fitness = 0;
        List<Genome> winners = new ArrayList<>();
        for (Genome g:currentpopulation) {
            fitness+=g.getFitness();
        }

        for (int i = 0; i < POPULATION_SIZE; i++) {
            float rand = random.nextFloat()*fitness;
            for (Genome g:currentpopulation) {
                rand -= g.getFitness();
                if(rand <= 0){
                    winners.add(g);
                    break;
                }
            }
        }
        currentpopulation.clear();
        currentpopulation.addAll(winners);

    }

    private void selectTournament() {

    }



    private void mutatePopulation()
    {
        for (Genome g:currentpopulation) {

        }
    }

    /**
     * Evaluates each genome in the current population
     */
    private void evaluatePopulation() {
        for (Genome g : currentpopulation) {
            Warrior.makeWarrior(g);
            float fitness = CommandLine.fitness();
            System.out.println("id: " + g.getId() + " fitness: " + fitness);
            g.setFitness(fitness);
        }
    }

    protected class FitnessComparator implements Comparator<Genome> {
        @Override
        public int compare(Genome genome1, Genome genome2) {
            if (genome1.getFitness() > genome2.getFitness()) return 1;
            else if (genome1.getFitness() < genome2.getFitness()) return -1;
            return 0;
        }
    }

}
