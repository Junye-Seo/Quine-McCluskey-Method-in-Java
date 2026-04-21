import java.util.List;

public class ImplicantTools {
    // Converts list of Implicants to minimal SOP expression string
    protected static String toSOP(List<Implicant> implicants, int num_var){
        char[] varNames = {'A', 'B', 'C', 'D', 'E'};
        String result = "";

        for(int i = 0; i < implicants.size(); i++){
            Implicant imp = implicants.get(i);
            String term = "";

            for(int bit = num_var - 1; bit >= 0; bit--){
                int pos = 1 << bit;

                if((imp.mask & pos) == 0)
                    continue;

                char varName = varNames[num_var - 1 - bit];

                if((imp.value & pos) != 0)
                    term += varName;
                else
                    term += varName + "'";
            }

            result += term;
            if(i < implicants.size() - 1)
                result += " + ";
        }

        return result;
    }

    // Return true if list already contains an equivalent Implicant
    protected static boolean containsImplicant(List<Implicant> list, Implicant imp){
        for(Implicant e : list)
            if(e.value == imp.value && e.mask == imp.mask)
                return true;
        return false;
    }
}
