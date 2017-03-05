import java.util.*;

/**
 * Created by thebaker on 2/24/17.
 */
public class Population {
    private final int POPULATION_SIZE;
    private final Random random = new Random();
    private ArrayList<Genome> currentpopulation;
    private List<Genome> elites = new ArrayList<>();
    private double crossoverRate;
    private int currentId;
    private double mutatationRate;
    private List<Genome> toRemove = new ArrayList<>();
    private Constants.SELECTION_MODE selection_mode;
    private Constants.CROSSOVER_MODE crossover_mode;
    private Constants.MUTATION_MODE mutation_mode;

    Population() {
        this(1000, 0.5, 0.01,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION);
    }

    Population(int POPULATION_SIZE, double crossoverRate, double mutatationRate) {
        this(POPULATION_SIZE, crossoverRate, mutatationRate,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.ONE_POINT_CROSSOVER,
                Constants.MUTATION_MODE.MUTATION);
    }

    Population(int POPULATION_SIZE, double crossoverRate, double mutatationRate,
               Constants.SELECTION_MODE selection_mode,
               Constants.CROSSOVER_MODE crossover_mode,
               Constants.MUTATION_MODE mutation_mode) {
        this.POPULATION_SIZE = POPULATION_SIZE;
        this.crossoverRate = crossoverRate;
        this.mutatationRate = mutatationRate;
        this.selection_mode = selection_mode;
        this.crossover_mode = crossover_mode;
        this.mutation_mode = mutation_mode;
        currentpopulation = new ArrayList<>();
        currentId = POPULATION_SIZE;
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
        Collections.sort(currentpopulation, new FitnessComparator());
    }

    public void start() {
        generatePopulation();
        runGeneticAlgorithm(10);
        int i = 0;
        for (Genome g : currentpopulation) {
            i++;
            if (g.getFitness() > 0) {
                System.out.println(i + " fitness:" + g.getFitness());
            }
        }
    }

    public void start(int iterations) {
        generatePopulation();
        runGeneticAlgorithm(iterations);
    }

    /**
     * This will implement the genetic algorithm by running iterations over time using selection,
     * crossovers, and mutations
     */
    private void runGeneticAlgorithm(int iterations) {
        for (int i = 0; i < iterations; i++) {
            System.out.println("iteration: " + i);
            generateNewPopulation(selection_mode, crossover_mode, mutation_mode);
        }
    }

    private void generateNewPopulation(Constants.SELECTION_MODE selection_mode,
                                       Constants.CROSSOVER_MODE crossover_mode,
                                       Constants.MUTATION_MODE mutation_mode) {
        List<Genome> selected = new ArrayList<>();
        List<Genome> toAdd = new ArrayList<>();
        elitism();

        switch (selection_mode) {
            case RANDOM:
                selected = selectRandom();
                break;
            case ROULETTE:
                selected = selectRoulette();
                break;
            case TOURNAMENT:
                selected = selectTournament();
                break;
            default:
                System.out.println("Invalid Selection Mode");
                break;
        }

        switch (crossover_mode) {
            case NO_CROSSOVER:
                break;
            case ONE_POINT_CROSSOVER:
                toAdd = onePointCrossover(selected);
                break;
            case UNIFORM_CROSSOVER:
                toAdd = uniformCrossover(selected);
                break;
            default:
                System.out.println("Invalid Crossover Mode");
                break;
        }

        switch (mutation_mode) {
            case MUTATION:
                mutatePopulation();
                break;
            case NO_MUTATION:
                break;
            default:
                System.out.println("Invalid Mutation");
                break;
        }

        currentpopulation.addAll(elites);
        elites.clear();

        currentpopulation.addAll(toAdd);

        System.out.println("Current Population: " + currentpopulation.size());
        evaluatePopulation();
    }


    private void elitism() {
        int topPercent = (int) (POPULATION_SIZE * Constants.ELITISM);
        elites.addAll(currentpopulation.subList(0, topPercent));
        currentpopulation.removeAll(currentpopulation.subList(0, topPercent));
    }

    /**
     * implements one point crossover, will add the parents to a list to be removed then use crossover to create
     * the children.
     *
     * @param selection list of genomes for crossover
     * @return returns a list of children to be added
     */
    private List<Genome> onePointCrossover(List<Genome> selection) {
        List<Genome> toAdd = new ArrayList<>();
        int length, selectionSize = selection.size();
        List<int[]> g1, g2;
        Genome child1, child2;

        //If the selection list is odd, remove the last genome and reduce size by 1
        if (selectionSize % 2 != 0) {
            toAdd.add(selection.remove(selectionSize - 1));
            selectionSize--;
        }

        //Combines the genomes based on one-point crossover
        for (int i = 0; i < selectionSize; i += 2) {
            child1 = selection.remove(0);
            child2 = selection.remove(0);
            child1.setFitness(0);
            child2.setFitness(0);
            g1 = child1.getGenome();
            g2 = child2.getGenome();

            //Goes through the shortest length to not cause conflicts
            length = g1.size()<g2.size()?g1.size():g2.size();

            if (g1.size() == 1 && g2.size() == 1) {
                g1.add(g2.get(0));
                g2.add(g1.get(0));
            } else {
                for (int j = length / 2; j < length; j++) {
                    int[] holder1 = g1.get(j);
                    g1.set(j, g2.get(j));
                    g2.set(j, holder1);
                }
            }
            if (g1 != null && !g1.isEmpty()) {
                child1.setGenome(g1);
            }

            if (g2 != null && !g2.isEmpty()) {
                child2.setGenome(g2);
            }
            toAdd.add(child1);
            toAdd.add(child2);
        }
        int count = 0;

        //Still having problems with this, and I have no idea why
        /*for (int j = 0; j < 10; j += 2) {
            child1 = elites.get(j);
            child2 = elites.get(j + 1);
            child1.setFitness(0);
            child2.setFitness(0);
            currentId++;
            child1.setId(currentId);
            currentId++;
            child2.setId(currentId);
            g1 = child1.getGenome();
            g2 = child2.getGenome();

            //Goes through the shortest length to not cause conflicts
            length = g1.size()<g2.size()?g1.size():g2.size();

            if (g1.size() == 1 && g2.size() == 1) {
                g1.add(g2.get(0));
                g2.add(g1.get(0));
            } else {
                for (int k = length / 2; k < length; k++) {
                    int[] holder1;
                    holder1 = g1.get(k);
                    g1.set(k, g2.get(k));
                    g2.set(k, holder1);
                }
            }
            if (g1 != null && !g1.isEmpty()) {
                child1.setGenome(g1);
                count++;
            }

            if (g2 != null && !g2.isEmpty()) {
                child2.setGenome(g2);
                count++;
            }

            toAdd.add(child1);
            toAdd.add(child2);

        }

        int pop = currentpopulation.size();
        currentpopulation.removeAll(currentpopulation.subList(pop-count,pop));*/

        System.out.println("adding:" + toAdd.size() + ", elite children:" + count);

        return toAdd;
    }

    private List<Genome> uniformCrossover(List<Genome> selected) {
        List<Genome> toAdd = new ArrayList<>();

        return toAdd;
    }

    //Random chance of a genome getting chosen for crossover, probably wont use.
    private List<Genome> selectRandom() {
        List<Genome> winners = new ArrayList<>();
        int numRemoved = 0;
        List<Integer> indexToRemove = new ArrayList<>();
        int index = 0;

        for (Genome g : currentpopulation) {
            if (random.nextDouble() > 0.99) {
                winners.add(g);
                indexToRemove.add(index);
            }
            index++;
        }

        //Removes the parents of the soon to be children
        for(int i : indexToRemove)
        {
            currentpopulation.remove(i - numRemoved);
            numRemoved++;
        }

        return winners;
    }

    //This gives each genome the probability of fitness/totalfitness of being chosen for crossover.
    private List<Genome> selectRoulette() {
        double totalFitness = 0;
        int index = 0, numRemoved = 0;
        List<Integer> indexToRemove = new ArrayList<>();
        List<Genome> winners = new ArrayList<>();

        for (Genome g : currentpopulation) {//gets total fitness of the population
            totalFitness += g.getFitness();
        }
        System.out.println("total Fitness:" + totalFitness);

        if(totalFitness > 0) {
            for (Genome g : currentpopulation) {
                if (g.getFitness() > 0) {
                    double prob = g.getFitness() / totalFitness;
                    if (random.nextDouble() < prob) {
                        winners.add(g);
                        indexToRemove.add(index);
                    }
                } else {
                    if (random.nextDouble() > 0.99) {
                        winners.add(g);
                        indexToRemove.add(index);
                    }
                }
                index++;
            }

            //Removes the parents of the soon to be children
            for(int i : indexToRemove)
            {
                currentpopulation.remove(i - numRemoved);
                numRemoved++;
            }
            System.out.println("removed:" + numRemoved);

        }
        else
        {//might as well do random picking if there are no good genomes
            winners = selectRandom();
        }

        return winners;
    }

    //A tournament is ran, and the winners will be use in crossovers.
    private List<Genome> selectTournament() {
        List<Genome> winners = new ArrayList<>();


        return winners;
    }


    private void mutatePopulation() {
        int populationSize = currentpopulation.size();

        for (Genome g : currentpopulation) {
            Double r = random.nextDouble();
            if (r > 1.0 - mutatationRate) {
                g.mutateGenome();
            }
        }
    }

    /**
     * Evaluates each genome in the current population
     */
    private void evaluatePopulation() {
        int pop = currentpopulation.size();

        for (int i = 0; i < pop; i++) {
            Genome g = currentpopulation.get(i);
            Warrior.makeWarrior(g);
            float fitness = CommandLine.fitness();
            g.setFitness(fitness);
        }
        Collections.sort(currentpopulation, new FitnessComparator());
    }

    /**
     * @return Empties the population to a list and returns that list
     */
    public List<Genome> getCurrentPopulationAndEmpty() {
        List<Genome> population = new ArrayList<>();
        population.addAll(currentpopulation);
        currentpopulation.clear();
        return population;
    }

    public void setCurrentPopulation(Collection<Genome> currentPopulation) {
        this.currentpopulation.addAll(currentPopulation);
    }

    class FitnessComparator implements Comparator<Genome> {

        @Override
        public int compare(Genome o1, Genome o2) {
            if (o1.getFitness() < o2.getFitness()) return 1;
            else if (o1.getFitness() > o2.getFitness()) return -1;
            else return 0;
        }
    }
}
