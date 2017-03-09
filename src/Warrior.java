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

    public static void makeBest(Genome best) throws IOException {

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            fw = new FileWriter(Constants.ABS_BEST_WARRIOR, false);
            bw = new BufferedWriter(fw);
            String content = best.getGenomeAsCommand();
            bw.write(Constants.ABS_BEST);
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

    public static void makeWorst(Genome worst) throws IOException {

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            fw = new FileWriter(Constants.ABS_WORST_WARRIOR, false);
            bw = new BufferedWriter(fw);
            String content = worst.getGenomeAsCommand();
            bw.write(Constants.ABS_WORST);
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

    public static void makeWarrior(Genome genome1, Genome genome2, int iterations) throws IOException {

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            if(Constants.DEBUG) System.out.println("folder: "+Constants.folder);
            fw = new FileWriter(Constants.BEST_WARRIOR+Constants.ext, false);
            bw = new BufferedWriter(fw);
            String content = genome1.getGenomeAsCommand();
            bw.write(Constants.BEST);
            bw.write(content);
            bw.write(Constants.CLOSING);

            fw = new FileWriter(Constants.WORST_WARRIOR+Constants.ext, false);
            bw = new BufferedWriter(fw);
            content = genome2.getGenomeAsCommand();
            bw.write(Constants.WORST);
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