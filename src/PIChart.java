import java.util.ArrayList;
import java.util.List;

/**
 * Step 3: Build PI chart, find Essential Prime Implicants
 *         then find minimum cover using brute-force
 */

public class PIChart {

    private final List<Implicant> PI;
    private final List<Integer> minterms;

    public PIChart(List<Implicant> PI, List<Integer> minterms){
        this.PI = removeOnlyDontcare(PI, minterms);
        this.minterms = minterms;
    }

    // Remove PIs which only covers don't care terms, not any minterm
    private List<Implicant> removeOnlyDontcare(List<Implicant> PI, List<Integer> minterms){
        List<Implicant> res = new ArrayList<>(PI);
        List<Implicant> toRemove = new ArrayList<>();
        for(Implicant p : PI){
            boolean coversMinterms = false;
            for(int m : minterms){
                if(p.coveredMinterms.contains(m)){
                    coversMinterms = true;
                    break;
                }
            }
            if(!coversMinterms)
                toRemove.add(p);
        }

        res.removeAll(toRemove);

        return res;
    }

    // Returns the minimum expression(combination of PI)
    public List<Implicant> findMinimum(){
        List<Implicant> EPI = findEPI();

        List<Integer> rest_Minterm = getRemainingMinterms(EPI);
        List<Implicant> rest_PI = getRemainingPI(EPI);

        List<Implicant> best_comb = bruteForce(rest_PI, rest_Minterm);
        best_comb.addAll(EPI);

        return best_comb;
    }

    // find EPI
    private List<Implicant> findEPI(){
        List<Implicant> EPI = new ArrayList<>();

        for(int m : minterms){
            int count = 0;
            Implicant candidate = null;

            for(Implicant p : PI){
                if(p.coveredMinterms.contains(m)){
                    count++;
                    candidate = p;
                }
            }

            if(count == 1 && !ImplicantTools.containsImplicant(EPI, candidate))
                EPI.add(candidate);
        }

        return EPI;
    }

    // Return minterms not covered by EPI
    private List<Integer> getRemainingMinterms(List<Implicant> EPI){
        List<Integer> rest = new ArrayList<>(minterms);
        for(Implicant epi : EPI)
            rest.removeAll(epi.coveredMinterms);
        return rest;
    }

    // Return PI which is not EPI
    private List<Implicant> getRemainingPI(List<Implicant> EPI){
        List<Implicant> rest = new ArrayList<>(PI);
        rest.removeAll(EPI);
        return rest;
    }

    // Return PI combination that makes up the minimum expression
    private List<Implicant> bruteForce(List<Implicant> rest_PI, List<Integer> rest_Minterm){
        List<Implicant> min = new ArrayList<>();
        int n = rest_PI.size();
        int minSize = Integer.MAX_VALUE;

        // brute-force: Try all cases and choose best one
        for(int i = 0; i < (1 << n); i++){                  // choose PI(loop for all case)
            List<Implicant> comb = new ArrayList<>();
            List<Integer> covered = new ArrayList<>();

            for(int bit = 0; bit < n; bit++){               // check combination of PI covered all rest minterms
                if((i & (1 << bit)) != 0){
                    Implicant p = rest_PI.get(bit);
                    comb.add(p);
                    for(int m : p.coveredMinterms)
                        if(!covered.contains(m))
                            covered.add(m);
                }
            }

            if(covered.containsAll(rest_Minterm) && comb.size() < minSize){
                minSize = comb.size();
                min = new ArrayList<>(comb);
            }
        }

        return min;
    }
}
