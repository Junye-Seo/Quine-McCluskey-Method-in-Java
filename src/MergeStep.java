import java.util.ArrayList;
import java.util.List;

/**
 * Step 1: Group minterms by number of 1-bits
 * Step 2: Find list of PI - Repeatedly merge adjacent groups
 */

public class MergeStep {

    private final List<Integer> minterms;
    private final List<Integer> dontCares;
    private final int num_var;

    public MergeStep(List<Integer> minterms, List<Integer> dontCares, int num_var){
        this.minterms = minterms;
        this.dontCares = dontCares;
        this.num_var = num_var;
    }

    // Return the list of Prime Implicants
    public List<Implicant> merge(){
        List<Implicant>[] groups = initGroups();
        List<Implicant> PI = new ArrayList<>();

        for(int i = 0; i < num_var; i++){
            List<Implicant> tmp = new ArrayList<>();
            boolean merge_check = false;

            for(int j = 0; j < num_var - i; j++){
                for(Implicant k : groups[j]){
                    for(Implicant l : groups[j + 1]){
                        if(canMerge(k, l)){
                            k.used = true;
                            l.used = true;

                            Implicant merged = mergeImplicant(k, l);
                            if(!ImplicantTools.containsImplicant(tmp, merged))
                                tmp.add(merged);

                            merge_check = true;
                        }
                    }

                    if(!k.used && !ImplicantTools.containsImplicant(PI, k))
                        PI.add(k);
                }

                groups[j].clear();
                groups[j].addAll(tmp);
                tmp.clear();
            }

            for(Implicant last : groups[num_var - i])
                if(!last.used && !ImplicantTools.containsImplicant(PI, last))
                    PI.add(last);

            // if merge nothing, break
            if(!merge_check)
                break;
        }

        return PI;
    }

    // Returns Implicants groups according to number of 1
    private List<Implicant>[] initGroups(){
        List<Implicant>[] groups = new ArrayList[num_var + 1];
        for(int i = 0; i <= num_var; i++)
            groups[i] = new ArrayList<>();

        for(int m : minterms)
            groups[Integer.bitCount(m)].add(new Implicant(m));
        for(int d : dontCares)
            groups[Integer.bitCount(d)].add(new Implicant(d));

        return groups;
    }

    // Return true if two Implicants can merge
    private boolean canMerge(Implicant a, Implicant b){
        int xor = a.value ^ b.value;
        return (a.mask == b.mask) && (xor != 0) && ((xor & (xor - 1)) == 0);
    }

    // Return new Implicants which merge two Implicants
    private Implicant mergeImplicant(Implicant a, Implicant b){
        int xor = a.value ^ b.value;
        int newMask = a.mask & ~xor;
        int newValue = a.value & newMask;

        List<Integer> newCovered = new ArrayList<>(a.coveredMinterms);
        newCovered.addAll(b.coveredMinterms);

        return new Implicant(newValue, newMask, newCovered);
    }

}
