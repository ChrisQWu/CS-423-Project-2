import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by c on 2/23/2017.
 */
public class Genome
{
    private int id;
    private Random random;
    private List<Gene> genome;
    private float fitness;

    /**
     * Defines an invalid id, a new Random object, generates a default length genome.
     */
    Genome()
    {
        this.id = -1;
        genome = new ArrayList<>();
        this.random = new Random();
        this.fitness = 0;
        genome.add(new Gene(1,0,0,0,1));
//        generateGenome(-1);
    }

    /**
     * Takes a random object to help the genome generate, assign the genome id
     * @param random a Random Object to help generate genomes initially
     * @param id genome id
     */
    Genome(Random random, int id)
    {
        this.id = id;
        genome = new ArrayList<>();
        this.random = random;
        this.fitness = 0;
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
        this.fitness = 0;
        generateGenome(length);
    }

    void setId(int id)
    {
        this.id = id;
    }

    int getId()
    {
        return id;
    }

    String getGenome()
    {
        String string = "";
        for (Gene g:genome) {
            string += g.toString() + "\n";
        }
        return string;
    }

    void setFitness(float fitness)
    {
        this.fitness = fitness;
    }

    float getFitness()
    {
        return fitness;
    }

    /**
     * Generates a default length genome or a variable length genome
     * @param length max length of genome, -1 if to be a default length
     */
    void generateGenome(int length)
    {
        int size = (length == -1)?Constants.DEFAULT_LENGTH:random.nextInt(length)+1;
        for (int i = 0; i < size; i++) {
            genome.add(new Gene(size));
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

        Gene(int length)
        {
            generateGene(length);
        }

        //generate a gene with initial conditions
        Gene(int instruction, int mode1, int param1, int mode2, int param2)
        {
            gene = new int[]{instruction,mode1,param1,mode2,param2};
        }

        void generateGene(int length)
        {
            int instruction = random.nextInt(Constants.INSTRUCTION.values().length-1);
            int mode1 = random.nextInt(Constants.Amodes.length);
            int param1 = random.nextInt(length - (-length) + 1) + length;
            int mode2 = random.nextInt(Constants.Bmodes.length);
            int param2 = random.nextInt(length - (-length) + 1) + length;
            gene = new int[]{instruction,mode1,param1,mode2,param2};
        }

        public int[] getGene(){
            return gene;
        }

        public String toString()
        {
            String result = "";
            if(Constants.INSTRUCTION.values()[gene[0]].argNum > 0)
            {
                result += Constants.INSTRUCTION.values()[gene[0]] + " ";
                if (Constants.Amodes[gene[1]] == ' ') result += gene[2];
                else result += Constants.Amodes[gene[1]] + gene[2];
            }
            if(Constants.INSTRUCTION.values()[gene[0]].argNum > 1)
            {
                if(Constants.Amodes[gene[3]] == ' ') result += ", "+gene[4];
                else result += ", "+Constants.Bmodes[gene[3]]+gene[4];
            }
            return result;
        }
    }

}
