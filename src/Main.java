import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        // Get number of variables
        int num_var = 0;
        boolean isVarValid = false;
        while(!isVarValid){
            System.out.println("Input number of variables(2 - 5): ");
            if (!sc.hasNextInt()){
                System.out.println("Wrong Input. Enter an Integer.");
                sc.next();
                continue;
            }
            num_var = sc.nextInt();
            if(num_var < 2 || num_var > 5){
                System.out.println("Invalid range.");
                continue;
            }
            isVarValid = true;
        }

        int num_rows = 1 << num_var;        // num_rows = 2^num_var

        // Get minterms
        List<Integer> minterms = new ArrayList<>();
        boolean isMintermInputActive = true;
        while(isMintermInputActive){
            System.out.println("Input Minterms(-1 for quit): ");
            if (!sc.hasNextInt()){
                System.out.println("Wrong Input. Enter an Integer.");
                sc.next();
                continue;
            }
            
            int input = sc.nextInt();
            if(input == -1)
                isMintermInputActive = false;
            else if(input > num_rows - 1 || input < 0)
                System.out.println("Invalid range.");
            else if(!minterms.contains(input))
                minterms.add(input);
        }

        // Get don't care terms
        List<Integer> dontCares = new ArrayList<>();
        boolean isDontCareInputActive = true;
        System.out.println("Input Don't Care terms(-1 for quit): ");
        while(isDontCareInputActive){
            if (!sc.hasNextInt()){
                System.out.println("Wrong Input. Enter an Integer.");
                sc.next();
                continue;
            }

            int input = sc.nextInt();
            if(input == -1)
                isDontCareInputActive = false;
            else if(input > num_rows - 1 || input < 0)
                System.out.println("Invalid range.");
            else if(minterms.contains(input))
                System.out.println("Already exists in minterms.");
            else if(!dontCares.contains(input))
                dontCares.add(input);
        }

        Quine_McCluskey qm = new Quine_McCluskey(minterms, dontCares, num_var);
        qm.solve();
        System.out.println("Result: " + qm.result);

        sc.close();
    }
}
