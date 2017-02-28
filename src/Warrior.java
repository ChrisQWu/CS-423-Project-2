import java.io.*;

/**
 * Created by thebaker on 2/25/17.
 *  Take genomes and generate a warrior file
 */
public class Warrior {

    private static final String FILENAME = "./Warriors_Folder/warrior.red";
    private static final String OPENING = "; redcode\n" +
            "; name :  Handsome Jack\n" +
            "; author :  Team 12\n" +
            "; assert    CORESIZE == 8000 && MAXLENGTH >= 100\n";
    private static final String CLOSING = "end";

    public static void main(String[] args) throws Exception
    {
        Genome genome = new Genome();
        makeWarrior(genome);
    }

    /**
     * Takes a genome and prints it to a .red file to be used for pmars
     * @param genome prints out its genes
     */
    public static void makeWarrior(Genome genome) {

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            fw = new FileWriter(FILENAME,false);
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
}