import java.util.List;

public class Quine_McCluskey {

    protected static int num_var;
    private final List<Integer> minterms;
    private final List<Integer> dontCares;
    protected String result;

    public Quine_McCluskey(List<Integer> minterms, List<Integer> dontCares, int num_var){
        this.minterms = minterms;
        this.dontCares = dontCares;
        Quine_McCluskey.num_var = num_var;
    }

    // solve the algorithms
    public void solve(){
        // Stage 1 & 2: grouping + merge → Prime Implicants
        MergeStep mergeStep = new MergeStep(minterms, dontCares, num_var);
        List<Implicant> PI = mergeStep.merge();

        // Stage 3: PI chart → minimum cover
        PIChart piChart = new PIChart(PI, minterms);
        List<Implicant> minimal = piChart.findMinimum();

        this.result = ImplicantTools.toSOP(minimal, num_var);
    }
}
