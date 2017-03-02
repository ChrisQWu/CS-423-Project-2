import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by c on 2/23/2017.
 */
public class CommandLine
{
    private static final String CommandStart ="./pmars ./Warriors_Folder/WARRIOR";
    private static final String File = "./Wariors_Folder/WARRIOR";
    private static final String scores = "scores ";

    /**
     * Code from http://stackoverflow.com/questions/26697916/running-a-bash-command-in-different-directory-from-a-java-program
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        String Command ="./pmars ./Warriors_Folder/WARRIOR0.RED ./WilkiesBench/BLUEFUNK.RED ./WilkiesBench/CANNON.RED";   //Bash Command
        // create a process and execute
        Process p = Runtime.getRuntime().exec(Command, null, new File("."));
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = r.readLine()) != null)
        {
            System.out.println(line);
            int index = line.indexOf("scores ");
            System.out.println("index: "+index);
            if(index != -1)
            {
                System.out.println(line.substring(index+7));
            }
        }
    }

    /**
     *  Calls pmars and get the warrior's fitness score
     * @return fitness score of warrior.red
     */
    public static float fitness(int id)
    {
        String line;
        float score=-1;
        try {
            // create a process and execute
            Process p = Runtime.getRuntime().exec(CommandStart + id + ".RED ./WilkiesBench/BLUEFUNK.RED ./WilkiesBench/TORNADO.RED ./WilkiesBench/RAVE.RED" , null, new File("."));
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = r.readLine()) != null) {
//                System.out.println(line);
                score = line.indexOf(scores);
//                System.out.println("index: " + score);
                if (score != -1) {
                    //System.out.println(line.substring((int)score + 7));
                    score = Float.parseFloat(line.substring((int)score + 7));
                    break;
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return score;
    }
}
