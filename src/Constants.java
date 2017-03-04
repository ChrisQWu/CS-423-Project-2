/**
 * Created by c on 2/23/2017.
 *  Gene properties.
 */
public class Constants
{
    public final static int DEFAULT_LENGTH = 1;
    private final static char EMPTY_CHAR = ' ';
    public final static double ELITISM = 0.01;//top X percent of the population automatically kept
    public static char[] Amodes = new char[]{//added a lot of extra EMPTY_CHAR to bias towards empty characters.
            EMPTY_CHAR,
//            '#',        // # immediate
            EMPTY_CHAR,
//            '$',        // $ direct
            EMPTY_CHAR,
            EMPTY_CHAR,
            EMPTY_CHAR,
//            '*',        // * indirect using A-field
            EMPTY_CHAR,
//            '{',        // { predrecement indirect using A-field
            EMPTY_CHAR,
//            '}',        // } postincrement indirect using A-field
            EMPTY_CHAR,};

    public static char[] Bmodes = new char[]{//added a lot of extra EMPTY_CHAR to bias towards empty characters.
            EMPTY_CHAR,
//            '#',        // # immediate
            EMPTY_CHAR,
//            '$',        // $ direct
            EMPTY_CHAR,
//            '@',        // @ indirect
            EMPTY_CHAR,
//            '<',        // < indirect with predecrement
            EMPTY_CHAR,
//            '>',        // > indirect with postdecrement
            EMPTY_CHAR,
            EMPTY_CHAR,
            EMPTY_CHAR,};

    public enum INSTRUCTION{
        DAT("DAT",2),           // data (kills the process)
        MOV("MOV",2),           // move (copies data from one address to another)
        ADD("ADD",2),           // add (adds one number to another)
        SUB("SUB",2),           // subtract (subtracts one number from another)
        MUL("MUL",2),           // multiply (multiplies one number with another)
        DIV("DIV",2),           // divide (divides one number with another)
        MOD("MOD",2),           // modulus (divides one number with another and gives the remainder)
        JMP("JMP",1),           // jump (continues execution from another address)
        JMZ("JMZ",1),           // jump if zero (continues execution from another address)
        JMN("JMN",1),           // jump if not zero (tests a number and jumps if it isn't 0)
        DJN("DJN",2),           // decrement and jump if not zero (decrements a number by one and jumps unless the result is 0)
        SPL("SPL",2),           // split (starts an execution thread at another address)
        CMP("CMP",2),           // compare (same as SEQ)
        SEQ("SEQ",2),           // skip if equal (compares two instructions, and skips the next instruction if they are equal)
        SNE("SNE",2),           // skip if not equal (compares two instructions, and skips the next instruction if they aren't equal)
        SLT("SLT",2),           // skip if lower than (compares two values, and skips the next instruction if the first is lower than the second)
        LDP("LDP",2),           // load from p-space (loads a number from private storage space)
        STP("STP",2),           // save to p-space (saves a number to private storage space)
        NOP("NOP",0);           // no operation (does nothing)

        int argNum;
        String command;

        INSTRUCTION(String command, int argNum)
        {
            this.argNum = argNum;
            this.command = command;
        }

        public int getArgNum()
        {
            return argNum;
        }

        public String getCommand()
        {
            return command;
        }
    }

    public enum MUTATION_MODE{
        MUTATION,
        NO_MUTATION
    }

    public enum SELECTION_MODE{
        ROULETTE,       //
        TOURNAMENT,     //
        RANDOM          //randomly replaces lower % of population with with upper %
    }
    
    public enum CROSSOVER_MODE{
        NO_CROSSOVER,
        UNIFORM_CROSSOVER,
        ONE_POINT_CROSSOVER
    }

}
