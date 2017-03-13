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
        BufferedWriter bw1 = null;
        FileWriter fw1 = null;
        BufferedWriter bw2 = null;
        FileWriter fw2 = null;
        BufferedWriter bw3 = null;
        FileWriter fw3 = null;


        try {

            fw = new FileWriter(Constants.COMP_1, false);
            bw = new BufferedWriter(fw);
            String content = genome1.getGenomeAsCommand();
            bw.write(Constants.COMPETITOR_1);
            bw.write(content);
            bw.write(Constants.CLOSING);

            fw1 = new FileWriter(Constants.COMP_2, false);
            bw1 = new BufferedWriter(fw1);
            content = genome2.getGenomeAsCommand();
            bw1.write(Constants.COMPETITOR_2);
            bw1.write(content);
            bw1.write(Constants.CLOSING);

            fw2 = new FileWriter(Constants.COMP_3, false);
            bw2 = new BufferedWriter(fw2);
            content = genome3.getGenomeAsCommand();
            bw2.write(Constants.COMPETITOR_3);
            bw2.write(content);
            bw2.write(Constants.CLOSING);

            fw3 = new FileWriter(Constants.COMP_4, false);
            bw3 = new BufferedWriter(fw3);
            content = genome4.getGenomeAsCommand();
            bw3.write(Constants.COMPETITOR_4);
            bw3.write(content);
            bw3.write(Constants.CLOSING);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                bw.close();
            }
            if (fw != null) {
                fw.close();
            }
            if (bw1 != null) {
                bw1.close();
            }
            if (fw1 != null) {
                fw1.close();
            }
            if (bw2 != null) {
                bw2.close();
            }
            if (fw2 != null) {
                fw2.close();
            }
            if (bw3 != null) {
                bw3.close();
            }
            if (fw3 != null) {
                fw3.close();
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

    public static void makeSecondBest(Genome secondBest) throws IOException {

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            fw = new FileWriter(Constants.SECOND_BEST_WARRIOR, false);
            bw = new BufferedWriter(fw);
            String content = secondBest.getGenomeAsCommand();
            bw.write(Constants.ABS_SECOND_BEST);
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
        BufferedWriter bw1 = null;
        FileWriter fw1 = null;

        try {
            if(Constants.DEBUG) System.out.println("folder: "+Constants.folder);
            fw = new FileWriter(Constants.BEST_WARRIOR+Constants.ext, false);
            bw = new BufferedWriter(fw);
            String content = genome1.getGenomeAsCommand();
            bw.write(Constants.BEST);
            bw.write(content);
            bw.write(Constants.CLOSING);

            if(Constants.DEBUG)
            {
                fw1 = new FileWriter(Constants.WORST_WARRIOR + Constants.ext, false);
                bw1 = new BufferedWriter(fw1);
                content = genome2.getGenomeAsCommand();
                bw1.write(Constants.WORST);
                bw1.write(content);
                bw1.write(Constants.CLOSING);
            }
            fw1 = new FileWriter(Constants.SECOND_BEST_WARRIOR + Constants.ext, false);
            bw1 = new BufferedWriter(fw1);
            content = genome2.getGenomeAsCommand();
            bw1.write(Constants.SECOND_BEST);
            bw1.write(content);
            bw1.write(Constants.CLOSING);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                bw.close();
            }
            if (fw != null) {
                fw.close();
            }
            if (bw1 != null) {
                bw1.close();
            }
            if (fw1 != null) {
                fw1.close();
            }
        }
    }
}