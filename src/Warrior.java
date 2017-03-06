import java.io.*;

/**
 * Created by thebaker on 2/25/17.
 * Take genomes and generate a warrior file
 */
public class Warrior {

    private static final String FILENAME = "Warriors_Folder/WARRIOR.RED";
    private static final String COMP_1 = "Warriors_Folder/Competitor_1.RED";
    private static final String COMP_2 = "Warriors_Folder/Competitor_2.RED";
    private static final String TOP_1 = "Top_Warriors/Warrior_1.RED";
    private static final String TOP_2 = "Top_Warriors/Warrior_2.RED";

    private static final String OPENING = "; redcode\n" +
            "; name :  Handsome Jack\n" +
            "; author :  Team 12\n" +
            "; assert    CORESIZE == 8000 && MAXLENGTH >= 100\n";
    private static final String competitor1 = "; redcode\n" +
            "; name :  competitor 1\n" +
            "; author :  Team 12\n" +
            "; assert    CORESIZE == 8000 && MAXLENGTH >= 100\n";
    private static final String competitor2 = "; redcode\n" +
            "; name :  competitor 2\n" +
            "; author :  Team 12\n" +
            "; assert    CORESIZE == 8000 && MAXLENGTH >= 100\n";
    private static final String CLOSING = "end";

    public static void main(String[] args) throws Exception {
        Genome genome = new Genome();
        makeWarrior(genome);
    }

    /**
     * Takes a genome and prints it to a .red file to be used for pmars
     *
     * @param genome prints out its genes
     */
    public static void makeWarrior(Genome genome) {

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            fw = new FileWriter(FILENAME, false);
            bw = new BufferedWriter(fw);
            String content = genome.getGenomeAsCommand();
            bw.write(OPENING);
            bw.write(content);
            bw.write(CLOSING);

//            System.out.println("Warrior written");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }
    }

    /**
     * Makes competitor files from two genomes
     * @param genome1
     * @param genome2
     * @throws IOException
     */
    public static void makeWarrior(Genome genome1, Genome genome2, boolean forTopWarriors) throws IOException {

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            if(!forTopWarriors) {
                fw = new FileWriter(COMP_1, false);
            }
            else
            {
                fw = new FileWriter(TOP_1, false);
            }
            bw = new BufferedWriter(fw);
            String content = genome1.getGenomeAsCommand();
            bw.write(competitor1);
            bw.write(content);
            bw.write(CLOSING);

            if(!forTopWarriors) {
                fw = new FileWriter(COMP_2, false);
            }
            else
            {
                fw = new FileWriter(TOP_2, false);
            }
            bw = new BufferedWriter(fw);
            content = genome2.getGenomeAsCommand();
            bw.write(competitor2);
            bw.write(content);
            bw.write(CLOSING);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
        }
    }
}