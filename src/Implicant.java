import java.util.ArrayList;
import java.util.List;

public class Implicant {
    protected int value;
    protected int mask;
    protected List<Integer> coveredMinterms;
    boolean used;

    // First time
    public Implicant(int minterm){
        value = minterm;
        mask = (1 << Quine_McCluskey.num_var) - 1;  // make bit 1111
        coveredMinterms = new ArrayList<>();
        coveredMinterms.add(minterm);
        used = false;
    }
    
    // After round
    public Implicant(int v, int m, List<Integer> c_m){
        value = v;
        mask = m;
        coveredMinterms = c_m;
        used = false;
    }

}
