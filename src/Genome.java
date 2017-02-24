import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by c on 2/23/2017.
 */
public class Genome
{
    private final int id;
    private Random random;
    private List<Gene> genome;

    /**
     * Defines an invalid id, a new Random object, generates a default length genome.
     */
    Genome()
    {
        this.id = -1;
        genome = new ArrayList<>();
        this.random = new Random();
        generateGenome(-1);
    }

    /**
     * Takes a random object to help the genome generate, assign the genome id
     * @param random
     * @param id
     */
    Genome(Random random, int id)
    {
        this.id = id;
        genome = new ArrayList<>();
        this.random = random;
        generateGenome(-1);
    }

    /**
     * This constructor also defines the max length of the genome.
     * @param random a random object to generate an initial genome
     * @param id id of the genome
     * @param length max length a genome can be
     */
    Genome(Random random, int id, int length)
    {
        this.id = id;
        genome = new ArrayList<>();
        this.random = random;
        generateGenome(length);
    }

    /**
     * Generates a default length genome or a variable length genome
     * @param size max length of genome, -1 if to be a default length
     */
    void generateGenome(int size)
    {
        int length = (size == -1)?Constants.DEFAULT_LENGTH:random.nextInt(size)+1;
        for (int i = 0; i < length; i++) {
            genome.add(new Gene(length));
        }
    }

    void printGenome()
    {
        System.out.println("Genome ID: "+this.id);
        for (Gene g:genome) {
            System.out.println(g.toString());
        }
    }

    public class Gene{
        private int[] gene;

        Gene(int seed)
        {
            generateGene(seed);
        }

        //generate a gene with initial conditions
        Gene(int instruction, int mode1, int param1, int mode2, int param2)
        {
            gene = new int[]{instruction,mode1,param1,mode2,param2};
        }

        void generateGene(int seed)
        {
            int instruction = random.nextInt(Constants.Instruction.values().length-1);
            int mode1 = random.nextInt(Constants.modes.length);
            int param1 = random.nextInt(seed - (-seed) + 1) + seed;
            int mode2 = random.nextInt(Constants.modes.length);
            int param2 = random.nextInt(seed - (-seed) + 1) + seed;
            gene = new int[]{instruction,mode1,param1,mode2,param2};
        }

        public int[] getGene(){
            return gene;
        }

        public String toString()
        {
            return Constants.Instruction.values()[gene[0]] + " "+Constants.modes[gene[1]]+gene[2]+", "+Constants.modes[gene[3]]+gene[4];
        }
    }

}
