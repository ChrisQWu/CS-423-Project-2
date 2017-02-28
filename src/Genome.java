import java.util.Random;

/**
 * Created by c on 2/23/2017.
 */
public class Genome {
    private int id;
    private Random random;
    private String genome = "";
    private float fitness;

    /**
     * Defines an invalid id, a new Random object, generates a default length genome.
     */
    Genome() {
        this.id = -1;
        genome = "";
        this.random = new Random();
        this.fitness = 0;
        genome = "010000000001";//mov 0 1
//        generateGenome(-1);
    }

    /**
     * Takes a random object to help the genome generate, assign the genome id
     *
     * @param random a Random Object to help generate genomes initially
     * @param id     genome id
     */
    Genome(Random random, int id) {
        this.id = id;
        this.random = random;
        this.fitness = 0;
        generateGenome(-1);
    }

    /**
     * This constructor also defines the max length of the genome.
     *
     * @param random a random object to generate an initial genome
     * @param id     id of the genome
     * @param length max length a genome can be
     */
    Genome(Random random, int id, int length) {
        this.id = id;
        this.random = random;
        this.fitness = 0;
        generateGenome(length);
    }

    void setId(int id) {
        this.id = id;
    }

    int getId() {
        return id;
    }

    String getGenomeAsString() {
        return genome;
    }

    String getGenomeAsCommand() {
        String cmd = "";
        for (int i = 0; i < genome.length(); i += 12) {
            int instruction = Integer.parseInt(genome.substring(i, i+2));
            int mode1 = Integer.parseInt(genome.substring(i+2, i+4));
            int param1 = Integer.parseInt(genome.substring(i+4, i+7));
            int mode2 = Integer.parseInt(genome.substring(i+7, i+9));
            int param2 = Integer.parseInt(genome.substring(i+9, i+12));
            cmd+=Constants.INSTRUCTION.values()[instruction]+" "+Constants.Amodes[mode1]+param1+", "+Constants.Bmodes[mode2]+param2+"\n";
        }
        return cmd;
    }

    void setFitness(float fitness) {
        this.fitness = fitness;
    }

    float getFitness() {
        return fitness;
    }

    /**
     * Generates a default length genome or a variable length genome
     *
     * @param length max length of genome, -1 if to be a default length
     */
    void generateGenome(int length) {
        int size = (length == -1) ? Constants.DEFAULT_LENGTH : random.nextInt(length) + 1;
        for (int i = 0; i < size; i++) {
            genome += generateGene(size);
        }
    }

    void printGenome() {
        System.out.println("Genome ID: " + this.id);
        System.out.println(getGenomeAsCommand());
    }

    private String generateGene(int length) {
        int instruction = random.nextInt(Constants.INSTRUCTION.values().length - 1);
        int mode1 = random.nextInt(Constants.Amodes.length);
        int param1 = random.nextInt(2*length) - length;
        int mode2 = random.nextInt(Constants.Bmodes.length);
        int param2 = random.nextInt(2*length) - length;
        return String.format("%02d%02d%03d%02d%03d", instruction, mode1, param1, mode2, param2);
    }

}
