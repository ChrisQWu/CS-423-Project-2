import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by c on 2/23/2017.
 */
public class CommandLine {
    private static final String Command = "./pmars -r 5 ./Warriors_Folder/WARRIOR.RED ./WilkiesBench/BLUEFUNK.RED ./WilkiesBench/TORNADO.RED ./WilkiesBench/RAVE.RED";
    private static final String scores = ":  Handsome Jack by :  Team 12 scores ";

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

    private static final String Tournament = "./pmars -r 10 ./"+Constants.COMP_1+" ./"+Constants.COMP_2+" ./"+Constants.COMP_3+" ./"+Constants.COMP_4;
    private static final String competitorOne = ":  competitor 1 by :  Team 12 scores ";
    private static final String competitorTwo = ":  competitor 2 by :  Team 12 scores ";
    private static final String competitorThree = ":  competitor 3 by :  Team 12 scores ";
    private static final String competitorFour = ":  competitor 4 by :  Team 12 scores ";

    public static short tournament() {
        String line;
        float score1 = -1;
        float score2 = -1;
        float score3 = -1;
        float score4 = -1;
        try {
            // create a process and execute
            Process p = Runtime.getRuntime().exec(Tournament, null, new File("./"));
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = r.readLine()) != null) {
//                System.out.println(line);

                score1 = line.indexOf(competitorOne);
                if (score1 != -1) {
                    score1 = Float.parseFloat(line.substring(competitorOne.length()));
                    break;
                }
                score2 = line.indexOf(competitorTwo);
                if (score2 != -1) {
                    score2 = Float.parseFloat(line.substring(competitorTwo.length()));
                    break;
                }
                score3 = line.indexOf(competitorThree);
                if (score3 != -1) {
                    score3 = Float.parseFloat(line.substring(competitorThree.length()));
                    break;
                }
                score4 = line.indexOf(competitorFour);
                if (score4 != -1) {
                    score4 = Float.parseFloat(line.substring(competitorFour.length()));
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(score1>score2 && score1>score3 && score1>score4) return 1;
        if(score2>score1 && score2>score3 && score2>score4) return 2;
        if(score3>score1 && score3>score2 && score3>score4) return 3;
        else return 4;
    }
}
