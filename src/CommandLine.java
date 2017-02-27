import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by c on 2/23/2017.
 */
public class CommandLine
{
    static ProcessBuilder builder = new ProcessBuilder(
        "cmd.exe", "/c", "./pmars warrior.red");

    /**
     * Code from http://stackoverflow.com/questions/15464111/run-cmd-commands-through-java
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        ProcessBuilder builder = new ProcessBuilder(
            "cmd.exe", "/c", "cd \"C:\\Program Files\\Microsoft SQL Server\" && dir");
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true)
        {
            line = r.readLine();
            if (line == null)
            {
                break;
            }
            System.out.println(line);
        }
    }

    public static int fitness()
    {
        builder.redirectErrorStream(true);
        try
        {
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = r.readLine()) != null)
            {
                System.out.println(line);
            }
            p.destroy();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
}
