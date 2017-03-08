import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Chris Wu on 3/6/2017.
 */
public class Recorder {
    public static void makeCSV(double bestFitness, double worstFitness, double totalFitness) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        BufferedWriter bw1 = null;
        FileWriter fw1 = null;
        BufferedWriter bw2 = null;
        FileWriter fw2 = null;

        try {

            fw = new FileWriter(Constants.folder+"/"+Constants.type+"_BEST_.csv", true);
            bw = new BufferedWriter(fw);
            bw.write(String.valueOf(bestFitness) + "\n");

            fw1 = new FileWriter(Constants.folder+"/"+Constants.type+"_WORST_.csv", true);
            bw1 = new BufferedWriter(fw1);
            bw1.write(String.valueOf(worstFitness) + "\n");

            fw2 = new FileWriter(Constants.folder+"/"+Constants.type+"_TOTAL_.csv", true);
            bw2 = new BufferedWriter(fw2);
            bw2.write(String.valueOf(totalFitness) + "\n");


        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

                if (bw1 != null)
                    bw1.close();

                if (fw1 != null)
                    fw1.close();

                if (bw2 != null)
                    bw2.close();

                if (fw2 != null)
                    fw2.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }
    }
}

