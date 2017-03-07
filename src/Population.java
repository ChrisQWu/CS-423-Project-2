import java.io.IOException;
import java.util.*;

/**
 * Created by thebaker on 2/24/17.
 */
public class Population {
    private final double POPULATION_SIZE;
    private final Random random = new Random();
    private ArrayList<Genome> currentpopulation;
    private List<Genome> elites = new ArrayList<>();
    private double crossoverRate;
    private int currentId;
    private double mutatationRate;
    private Constants.SELECTION_MODE selection_mode;
    private Constants.CROSSOVER_MODE crossover_mode;
    private Constants.MUTATION_MODE mutation_mode;
    private FitnessComparator fitnessComparator = new FitnessComparator();

    Population() {
        this(2000, 0.5, 0.02,
                Constants.SELECTION_MODE.ROULETTE,
                Constants.CROSSOVER_MODE.UNIFORM_CROSSOVER,
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
            if (Constants.DEBUG && fitness > 1) {
                System.out.println("Fitness: " + fitness);
            }
            genome.setFitness(fitness);
            currentpopulation.add(genome);
        }
        Collections.sort(currentpopulation, new FitnessComparator());
    }

    public void start() {
        generatePopulation();
        runGeneticAlgorithm(100);
        int i = 0;
        for (Genome g : currentpopulation) {
            i++;
            if (Constants.DEBUG && g.getFitness() > 0) {
                System.out.println(i + " fitness:" + g.getFitness());
            }
            if (i < 10) {
                g.printGenome();
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
            if (i > iterations / 10)
                Constants.bound = false;//unbound the genome generation after some number of iterations
            generateNewPopulation(selection_mode, crossover_mode, mutation_mode);
            evaluatePopulation(i);
        }
    }

    private void generateNewPopulation(Constants.SELECTION_MODE selection_mode,
                                       Constants.CROSSOVER_MODE crossover_mode,
                                       Constants.MUTATION_MODE mutation_mode) {
        List<Genome> selected = new ArrayList<>();
        List<Genome> toAdd = new ArrayList<>();
        elitism();
        System.out.println("elites size:" + elites.size());

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
                if (Constants.DEBUG) System.out.println("Invalid Selection Mode");
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
                if (Constants.DEBUG) System.out.println("Invalid Crossover Mode");
                break;
        }

        currentpopulation.clear();
        currentpopulation.addAll(toAdd);
        currentpopulation.addAll(elites);
        if (Constants.DEBUG) System.out.println("Current Population: " + currentpopulation.size());
        switch (mutation_mode) {
            case MUTATION:
                mutatePopulation();
                break;
            case NO_MUTATION:
                break;
            default:
                if (Constants.DEBUG) System.out.println("Invalid Mutation");
                break;
        }



    }


    private void elitism() {
        elites.clear();
        int topPercent = (int) (POPULATION_SIZE * Constants.ELITISM);
        elites.addAll(currentpopulation.subList(0, topPercent));
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
        while (toAdd.size() < POPULATION_SIZE - elites.size()) {
            child1 = selection.get(random.nextInt((int) POPULATION_SIZE / 2 - elites.size()-1));
            child2 = selection.get(random.nextInt((int) POPULATION_SIZE / 2 - elites.size()-1));
            child1.setId(currentId++);
            child2.setId(currentId++);
            child1.setFitness(0);
            child2.setFitness(0);
            g1 = child1.getGenome();
            g2 = child2.getGenome();

            //Goes through the shortest length to not cause conflicts
            length = g1.size() < g2.size() ? g1.size() : g2.size();

            if (g1.size() == 1 && g2.size() == 1) {
                g1.add(g2.get(0));
                g2.add(g1.get(0));
            } else {
                for (int j = (int) (length * crossoverRate); j < length; j++) {
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
        if (toAdd.size() != POPULATION_SIZE - elites.size()) {
            while (toAdd.size() != POPULATION_SIZE - elites.size()) {
                toAdd.remove(random.nextInt(toAdd.size()));
            }
        }

        return toAdd;
    }

    /**
     * Implements uniform crossover using the crossover rate of the population
     *
     * @param selection list of selected parents
     * @return list of children to add to population
     */
    private List<Genome> uniformCrossover(List<Genome> selection) {
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
        while (toAdd.size() < POPULATION_SIZE - elites.size()) {
            child1 = selection.get(random.nextInt((int) POPULATION_SIZE / 2 - elites.size()));
            child2 = selection.get(random.nextInt((int) POPULATION_SIZE / 2 - elites.size()));
            child1.setId(currentId++);
            child2.setId(currentId++);
            child1.setFitness(0);
            child2.setFitness(0);
            g1 = child1.getGenome();
            g2 = child2.getGenome();

            //Goes through the shortest length to not cause conflicts
            length = g1.size() < g2.size() ? g1.size() : g2.size();

            if (g1.size() == 1 && g2.size() == 1) {
                g1.add(g2.get(0));
                g2.add(g1.get(0));
            } else {
                for (int j = 0; j < length; j++) {
                    if (random.nextDouble() < crossoverRate) {
                        int[] holder1 = g1.get(j);
                        g1.set(j, g2.get(j));
                        g2.set(j, holder1);
                    }
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
        if (toAdd.size() != POPULATION_SIZE - elites.size()) {
            while (toAdd.size() != POPULATION_SIZE - elites.size()) {
                toAdd.remove(random.nextInt(toAdd.size()));
            }
        }

        return toAdd;
    }

    //Random chance of a genome getting chosen for crossover, probably wont use.
    private List<Genome> selectRandom() {
        List<Genome> winners = new ArrayList<>();
        while (winners.size() < POPULATION_SIZE / 2 - elites.size()) {
            for (Genome g : currentpopulation) {
                if (random.nextDouble() > 0.90) {
                    winners.add(g);
                    if (winners.size() == POPULATION_SIZE / 2 - elites.size()) break;
                }
            }
        }

//        //Removes the parents of the soon to be children
//        for (Genome g : winners) {
//            if (!elites.contains(g)) {
//                currentpopulation.remove(g);
//            } else {
//                int size = currentpopulation.size();
//                currentpopulation.remove(size - 1);
//            }
//        }

        return winners;
    }

    //This gives each genome the probability of fitness/totalfitness of being chosen for crossover.
    private List<Genome> selectRoulette() {
        double totalFitness = 0;
        List<Genome> winners = new ArrayList<>();

        for (Genome g : currentpopulation) {//gets total fitness of the population
            totalFitness += g.getFitness();
        }

        if (totalFitness > 0) {
            while (winners.size() < POPULATION_SIZE / 2 - elites.size()) {
                for (Genome g : currentpopulation) {
                    if (g.getFitness() > 0) {
                        double prob = g.getFitness() / totalFitness;
                        if (random.nextDouble() < prob) {
                            winners.add(g);
                        }
                    } else {
                        if (random.nextDouble() > 0.95) {
                            winners.add(g);
                        }
                    }
                }
            }

            //Removes the parents of the soon to be children
            /*for (Genome g : winners) {
                if (!elites.contains(g)) {
                    currentpopulation.remove(g);
                } else {
                    int size = currentpopulation.size();
                    currentpopulation.remove(size - 1);
                }
            }*/

        } else {//might as well do random picking if there are no good genomes
            winners = selectRandom();
        }

        return winners;
    }

    //A tournament is ran, and the winners will be use in crossovers.
    private List<Genome> selectTournament() {
        List<Genome> winners = new ArrayList<>();
        while (winners.size() < POPULATION_SIZE / 2 - elites.size()) {
            Genome g1 = currentpopulation.get(random.nextInt(currentpopulation.size())),
                    g2 = currentpopulation.get(random.nextInt(currentpopulation.size())),
                    g3 = currentpopulation.get(random.nextInt(currentpopulation.size())),
                    g4 = currentpopulation.get(random.nextInt(currentpopulation.size()));
//            while (true) {
//                if (!elites.contains(g1)) g1 = currentpopulation.get(random.nextInt(currentpopulation.size()));
//                if (!elites.contains(g2)) g2 = currentpopulation.get(random.nextInt(currentpopulation.size()));
//                if (!elites.contains(g3)) g3 = currentpopulation.get(random.nextInt(currentpopulation.size()));
//                if (!elites.contains(g4)) g4 = currentpopulation.get(random.nextInt(currentpopulation.size()));
//                if (!elites.contains(g1) && !elites.contains(g2) && !elites.contains(g3) && !elites.contains(g4)) break;
//            }
            try {
                Warrior.makeWarrior(g1, g2, g3, g4);
                short round_winner = CommandLine.tournament();
                switch (round_winner) {
                    case 1:
                        winners.add(g1);
                        break;
                    case 2:
                        winners.add(g2);
                        break;
                    case 3:
                        winners.add(g3);
                        break;
                    case 4:
                        winners.add(g4);
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Removes the parents of the soon to be children
        /*for (Genome g : winners) {
            int pop;
            if (!elites.contains(g)) {
                if (currentpopulation.contains(g)) {
                    currentpopulation.remove(g);
                    removed++;
                } else {
                    pop = currentpopulation.size();
                    currentpopulation.remove(pop - 1);
                    removed++;
                }
            } else {
                pop = currentpopulation.size();
                currentpopulation.remove(pop - 1);
                removed++;
            }
        }*/

        return winners;
    }


    private void mutatePopulation() {
        for (Genome g : currentpopulation) {
            if (!elites.contains(g)) {
                Double r = random.nextDouble();
                if (r > 1.0 - mutatationRate) {
                    g.mutateGenome();
                }
            }
        }
    }

    /**
     * Evaluates each genome in the current population
     */
    private void evaluatePopulation(int generation) {
        int pop = currentpopulation.size();
        Genome best = null, worst = null;
        for (int i = 0; i < pop; i++) {
            Genome g = currentpopulation.get(i);
            Warrior.makeWarrior(g);
            float fitness = CommandLine.fitness();
            g.setFitness(fitness);
            if (best == null && worst == null) {
                best = g;
                worst = g;
            }
            if (best.getFitness() < g.getFitness()) best = g;
            if (worst.getFitness() > g.getFitness()) worst = g;
        }
        try {
            Warrior.makeWarrior(best, worst, generation);//save the best of this generation
            Recorder.makeCSV(best.getFitness(),worst.getFitness());
            if (best.getFitness() > Constants.BEST_FITNESS) {
                Constants.BEST_FITNESS = best.getFitness();
                Warrior.makeBest(best);
            }
            if (worst.getFitness() < Constants.WORST_FITNESS) {
                Constants.WORST_FITNESS = worst.getFitness();
                Warrior.makeWorst(worst);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(currentpopulation, fitnessComparator);
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



    public static void main(String[] args) {
        Population population = new Population(1000, 0.5, 0.01,
                Constants.SELECTION_MODE.TOURNAMENT,
                Constants.CROSSOVER_MODE.NO_CROSSOVER,
                Constants.MUTATION_MODE.NO_MUTATION);
        population.start();

    }
}
