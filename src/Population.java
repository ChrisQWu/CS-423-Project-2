
import java.util.*;

/**
 * Created by thebaker on 2/24/17.
 */
public class Population {
    private final int POPULATION_SIZE;
    private final Random random = new Random();
    private PriorityQueue<Genome> currentpopulation;
    private List<Genome> elites = new ArrayList<>();
    private Comparator<Genome> fitnessComparator;
    private double crossoverRate;
    private double mutatationRate;

    Population() {
        this.POPULATION_SIZE = 1000;
        new Population(POPULATION_SIZE, 0.2, 0.01);
    }

    Population(int POPULATION_SIZE, double crossoverRate, double mutatationRate) {
        this.POPULATION_SIZE = POPULATION_SIZE;
        this.crossoverRate = crossoverRate;
        this.mutatationRate = mutatationRate;
        fitnessComparator = new FitnessComparator();
        currentpopulation = new PriorityQueue<>(POPULATION_SIZE, fitnessComparator);
        generatePopulation();
        runGeneticAlgorithm();
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

    /**
     * This will implement the genetic algorithm by running iterations over time using selection,
     * crossovers, and mutations

     */
    private void runGeneticAlgorithm()
    {
        while(currentpopulation.size() > 2)
        {
            generateNewPopulation(Constants.SELECTION_MODE.TOURNAMENT,
                                  Constants.CROSSOVER_MODE.NO_CROSSOVER,
                                  Constants.MUTATION_MODE.MUTATION);

            System.out.println(currentpopulation.size());
        }

        Genome g1 = currentpopulation.poll();
        Genome g2 = currentpopulation.poll();
        System.out.println("Fitness" + g1.getFitness());
        g1.printGenome();
        System.out.println("Fitness" + g2.getFitness());
        g2.printGenome();
    }

    private void generateNewPopulation(Constants.SELECTION_MODE selection_mode,
                                       Constants.CROSSOVER_MODE crossover_mode,
                                       Constants.MUTATION_MODE mutation_mode) {
        elitism();
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
        currentpopulation.addAll(elites);
        elites.clear();
        switch (crossover_mode) {

            case NO_CROSSOVER:
                break;
            case ONE_POINT_CROSSOVER:
                onePointCrossover();
                break;
            case UNIFORM_CROSSOVER:
                uniformCrossover();
                break;
            default:
                System.out.println("Invalid Crossover Mode");
                break;
        }
        switch (mutation_mode){
            case MUTATION:
                mutatePopulation();
                break;
            case NO_MUTATION:
                break;
            default:
                System.out.println("Invalid Mutation Mode");
                break;
        }

        evaluatePopulation();
    }


    private void elitism()
    {
        int topPercent = (int)(currentpopulation.size()*Constants.ELITISM);
        for (int i = 0; i < topPercent; i++) {
            elites.add(currentpopulation.poll());
        }
    }

    private void onePointCrossover() {
        List<Genome> producedTop = new ArrayList<>();
        int length, populationSize;
        List<int[]> g1, g2;
        Genome child1, child2;
        populationSize = currentpopulation.size();
        //Combines the genomes based on one-point crossover
        for (int i = 0; i < populationSize; i += 2) {
            if(i == populationSize - 1)
            {
                break;
            }
            child1 = currentpopulation.poll();
            child2 = currentpopulation.poll();
            g1 = child1.getGenome();
            g2 = child2.getGenome();
            length = g1.size();
            if (g2.size() < length) length = g2.size();
            for (int j = 0; j < length; j++) {
                //If the random is within 1 - crossover rate it undergoes crossover
                if (random.nextDouble() > (1.0 - crossoverRate)) {
                    //System.out.println("Crossover:" + child1.getId() + "," + child2.getId());
                    //switches the commands after the crossover point
                    if(g1.size() == 1 && g2.size() == 1)
                    {
                        g1.add(g2.get(0));
                        g2.add(g1.get(0));
                        break;
                    }
                    for (int k = length - j; k < length; k++) {
                        int[] holder1, holder2;
                        holder1 = g1.get(k);
                        holder2 = g2.get(k);
                        g1.set(k, holder2);
                        g2.set(k, holder1);
                    }
                    break;
                }
            }
            child1.setGenome(g1);
            child2.setGenome(g2);
            producedTop.add(child1);
            producedTop.add(child2);
        }
        //Adds the children if they underwent crossover or not
        currentpopulation.addAll(producedTop);
    }

    private void uniformCrossover() {

    }

    /**
     * Take the top 75% of the population
     */
    private void selectRandom() {
        List<Genome> topPercent = new ArrayList<>();
        int populationSize = currentpopulation.size();
        int top = populationSize - (int) (populationSize * (0.75));
        for (int i = 0; i < top; i++) {
            Genome g = currentpopulation.poll();
            topPercent.add(g);//save the good ones
        }
        currentpopulation.clear();//remove all of the lower fit genomes
        currentpopulation.addAll(topPercent);//put in the fit genomes back
        for (int i = top; i < populationSize * 0.95; i++) {
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

    //Uses the elites to refill the population using crossover,
    //this will implement crossover, but will later use the crossover methods.
    private void selectTournament() {
        int populationSize = currentpopulation.size();
        List<int[]> g1, g2;
        int length;

        currentpopulation.clear();
        for(Genome p1 : elites)
        {
            for(Genome p2 : elites)
            {
                if(currentpopulation.size() > ((populationSize * 0.98) - elites.size()))
                {
                    break;
                }
                Genome c1 = p1;
                Genome c2 = p2;
                g1 = c1.getGenome();
                g2 = c2.getGenome();
                length = g1.size();
                if (g2.size() < length) length = g2.size();
                if(g1.size() == 1 && g2.size() == 1)
                {
                    g1.add(g2.get(0));
                    g2.add(g1.get(0));
                    break;
                }
                else
                {
                    for (int k = length/2; k < length; k++) {
                        int[] holder1, holder2;
                        holder1 = g1.get(k);
                        holder2 = g2.get(k);
                        g1.set(k, holder2);
                        g2.set(k, holder1);
                    }
                }
                c1.setGenome(g1);
                c2.setGenome(g2);
                currentpopulation.add(c1);
                currentpopulation.add(c2);
            }
        }

    }


    private void mutatePopulation()
    {
        List<Genome> holder = new ArrayList<>();
        int populationSize = currentpopulation.size();
        for(int j = 0; j < populationSize; j++)
        {
            Double r = random.nextDouble();
            Genome g = currentpopulation.poll();
            if(r > 1.0 - mutatationRate)
            {
                //System.out.println("Mutating" + g.getId());
                g.mutateGenome();
            }
            holder.add(g);
        }
        currentpopulation.clear();
        currentpopulation.addAll(holder);
    }

    /**
     * Evaluates each genome in the current population
     */
    private void evaluatePopulation() {
        for (Genome g : currentpopulation) {
            Warrior.makeWarrior(g);
            float fitness = CommandLine.fitness();
            //System.out.println("id: " + g.getId() + " fitness: " + fitness);
            g.setFitness(fitness);
        }
    }

    protected class FitnessComparator implements Comparator<Genome> {
        @Override
        public int compare(Genome genome1, Genome genome2) {
            if (genome1.getFitness() < genome2.getFitness()) return 1;
            else if (genome1.getFitness() > genome2.getFitness()) return -1;
            return 0;
        }
    }

}
