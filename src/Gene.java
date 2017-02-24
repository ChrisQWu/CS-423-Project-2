/**
 * Created by c on 2/23/2017.
 *  Gene properties.
 */
public class Gene
{
    private int[] gene;

    Gene()
    {
        gene = new int[]{0,0,0,0,0,0};
    }

    public enum Instruction{
        DAT("DAT",2),           // data (kills the process)
        MOV("MOV",2),           // move (copies data from one address to another)
        ADD("ADD",2),           // add (adds one number to another)
        SUB("SUB",2),           // subtract (subtracts one number from another)
        MUL("MUL",2),           // multiply (multiplies one number with another)
        DIV("DIV",2),           // divide (divides one number with another)
        MOD("MOD",2),           // modulus (divides one number with another and gives the remainder)
        JMP("JMP",2),           // jump (continues execution from another address)
        JMZ("JMZ",2),           // jump if zero (continues execution from another address)
        JMN("JMN",2),           // jump if not zero (tests a number and jumps if it isn't 0)
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

        Instruction(String command, int argNum)
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

    public enum Mode{
        POUND('#'),          // # immediate
        DOLLAR('$'),         // $ direct
        AT('@'),             // @ indirect
        LESS('<'),           // < indirect with predecrement
        STAR('*'),           // * indirect using A-field
        OPEN_CURLY('{'),     // { predrecement indirect using A-field
        CLOSE_CURLY('}');     // } postincrement indirect using A-field

        char symbol;

        Mode(char symbol)
        {
            this.symbol = symbol;
        }

        public char getSymbol()
        {
            return symbol;
        }
    }
}
