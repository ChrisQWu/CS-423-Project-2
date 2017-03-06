import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by c on 2/23/2017.
 */
public class CommandLine {
    private static final String Command = "./pmars -r 10 ./Warriors_Folder/WARRIOR.RED ./WilkiesBench/BLUEFUNK.RED ./WilkiesBench/TORNADO.RED ./WilkiesBench/RAVE.RED";
    private static final String Tournament = "./pmars -r 10 ./Warriors_Folder/Competitor_1.RED ./Warriors_Folder/Competitor_2.RED";
    //    private static final String Command ="./pmars ./Warriors_Folder/WARRIOR.RED";
//    private static final String File = "./Wariors_Folder/WARRIOR";
    private static final String scores = ":  Handsome Jack by :  Team 12 scores ";
    private static final String competitorOne = ":  competitor 1 by :  Team 12 scores ";
    private static final String competitorTwo = ":  competitor 2 by :  Team 12 scores ";

    /**
     * Code from http://stackoverflow.com/questions/26697916/running-a-bash-command-in-different-directory-from-a-java-program
     *
     * @param args aguments
     * @throws Exception IOException
     */
    public static void main(String[] args) throws Exception {
        String line;
        float score = -1;
        try {
            // create a process and execute
            Process p = Runtime.getRuntime().exec(Command, null, new File("."));
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = r.readLine()) != null) {
                System.out.println(line);

                score = line.indexOf(scores);
//                System.out.println("index: " + score);
                if (score != -1) {
                    //System.out.println(line.substring((int)score + 7));
                    score = Float.parseFloat(line.substring(scores.length()));
                    System.out.println(score);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calls pmars and get the warrior's fitness score
     *
     * @return fitness score of warrior.red
     */
    public static float fitness() {
        String line;
        float score = -1;
        try {
            // create a process and execute
            Process p = Runtime.getRuntime().exec(Command, null, new File("./"));
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = r.readLine()) != null) {
//                System.out.println(line);

                score = line.indexOf(scores);
//                System.out.println("index: " + score);
                if (score != -1) {
                    //System.out.println(line.substring((int)score + 7));
                    score = Float.parseFloat(line.substring(scores.length()));
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return score;
    }

    public static boolean tournament() {
        String line;
        float score1 = -1;
        float score2 = -1;
        try {
            // create a process and execute
            Process p = Runtime.getRuntime().exec(Tournament, null, new File("./"));
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = r.readLine()) != null) {
//                System.out.println(line);

                score1 = line.indexOf(competitorOne);
//                System.out.println("index: " + score);
                if (score1 != -1) {
                    //System.out.println(line.substring((int)score + 7));
                    score1 = Float.parseFloat(line.substring(scores.length()));
                    break;
                }
                score2 = line.indexOf(competitorTwo);
//                System.out.println("index: " + score);
                if (score2 != -1) {
                    //System.out.println(line.substring((int)score + 7));
                    score2 = Float.parseFloat(line.substring(scores.length()));
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return score1>score2?true:false;
    }
}
