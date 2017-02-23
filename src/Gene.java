/**
 * Created by c on 2/23/2017.
 *  Contains
 */
public class Gene
{
    public enum Instruction{
        DAT(2),            // data (kills the process)
        MOV(2),            // move (copies data from one address to another)
        ADD(2),            // add (adds one number to another)
        SUB(2),            // subtract (subtracts one number from another)
        MUL(2),            // multiply (multiplies one number with another)
        DIV(2),            // divide (divides one number with another)
        MOD(2),            // modulus (divides one number with another and gives the remainder)
        JMP(2),            // jump (continues execution from another address)
        JMZ(2),            // jump if zero (continues execution from another address)
        JMN(2),            // jump if not zero (tests a number and jumps if it isn't 0)
        DJN(2),            // decrement and jump if not zero (decrements a number by one and jumps unless the result is 0)
        SPL(2),            // split (starts an execution thread at another address)
        CMP(2),            // compare (same as SEQ)
        SEQ(2),            // skip if equal (compares two instructions, and skips the next instruction if they are equal)
        SNE(2),            // skip if not equal (compares two instructions, and skips the next instruction if they aren't equal)
        SLT(2),            // skip if lower than (compares two values, and skips the next instruction if the first is lower than the second)
        LDP(2),            // load from p-space (loads a number from private storage space)
        STP(2),            // save to p-space (saves a number to private storage space)
        NOP(0);             // no operation (does nothing)

        int argNum;

        Instruction(int argNum)
        {
            this.argNum = argNum;
        }

        public int getArgNum()
        {
            return argNum;
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
