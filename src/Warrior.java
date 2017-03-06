import java.io.*;

/**
 * Created by thebaker on 2/25/17.
 * Take genomes and generate a warrior file
 */
public class Warrior {

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

            fw = new FileWriter(Constants.FILENAME, false);
            bw = new BufferedWriter(fw);
            String content = genome.getGenomeAsCommand();
            bw.write(Constants.OPENING);
            bw.write(content);
            bw.write(Constants.CLOSING);

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
     *
     * @param genome1
     * @param genome2
     * @throws IOException
     */
    public static void makeWarrior(Genome genome1, Genome genome2, Genome genome3, Genome genome4) throws IOException {

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            fw = new FileWriter(Constants.COMP_1, false);
            bw = new BufferedWriter(fw);
            String content = genome1.getGenomeAsCommand();
            bw.write(Constants.COMPETITOR_1);
            bw.write(content);
            bw.write(Constants.CLOSING);

            fw = new FileWriter(Constants.COMP_2, false);
            bw = new BufferedWriter(fw);
            content = genome2.getGenomeAsCommand();
            bw.write(Constants.COMPETITOR_2);
            bw.write(content);
            bw.write(Constants.CLOSING);

            fw = new FileWriter(Constants.COMP_3, false);
            bw = new BufferedWriter(fw);
            content = genome3.getGenomeAsCommand();
            bw.write(Constants.COMPETITOR_3);
            bw.write(content);
            bw.write(Constants.CLOSING);

            fw = new FileWriter(Constants.COMP_4, false);
            bw = new BufferedWriter(fw);
            content = genome4.getGenomeAsCommand();
            bw.write(Constants.COMPETITOR_4);
            bw.write(content);
            bw.write(Constants.CLOSING);

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