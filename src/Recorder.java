import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Chris Wu on 3/6/2017.
 */
public class Recorder {
    public static void makeCSV(float bestFitness, float worstFitness) {
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            fw = new FileWriter(Constants.type+".csv", true);
            bw = new BufferedWriter(fw);
            String content = Constants.folder+"/BEST_Warrior_"+Constants.type+","+bestFitness+","
                    +Constants.folder+"/WORST_Warrior_"+Constants.type+","+worstFitness+","+"\n";
            bw.write(content);

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

