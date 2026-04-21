# Quine-McCluskey-Method-in-Java

# Members
- 22100368 서준예

# Project Description
This project implements Quine-McCluskey Method in Java, which is one of algorithms which minimize logic boolean functions. The program works for boolean logic function, which supports 2 to 5 variables and don't care terms. The program takes minterm numbers and don't care terms as input, and Output will be minimized boolean expression in SOP form.

User Guide

Step 1: Input number of variables  
Step 2: Input minterms one by one, enter -1 to finish  
Step 3: Input don't Care terms. You can enter -1 directly if none.  
Step 4: Result  
  
<img width="331" height="327" alt="image" src="https://github.com/user-attachments/assets/d3005b03-f90f-4b7d-8a06-ceb9d17d3993" />





```mermaid
classDiagram
    class Main {
        +main(String[] args)
    }

    class Quine_McCluskey {
        #static int num_var
        -List~Integer~ minterms
        -List~Integer~ dontCares
        #String result
        +solve()
    }

    class MergeStep {
        -List~Integer~ minterms
        -List~Integer~ dontCares
        -int num_var
        +merge() List~Implicant~
        -initGroups() List~Implicant~[]
        -canMerge(Implicant a, Implicant b) boolean
        -mergeImplicant(Implicant a, Implicant b) Implicant
    }

    class PIChart {
        -List~Implicant~ PI
        -List~Integer~ minterms
        +findMinimum() List~Implicant~
        -findEPI() List~Implicant~
        -bruteForce(List rest_PI, List rest_Minterm) List~Implicant~
        -removeOnlyDontcare(List PI, List minterms) List~Implicant~
    }

    class Implicant {
        #int value
        #int mask
        #List~Integer~ coveredMinterms
        ~boolean used
        +Implicant(int minterm)
        +Implicant(int v, int m, List c_m)
    }

    class ImplicantTools {
        +static toSOP(List implicants, int num_var) String
        +static containsImplicant(List list, Implicant imp) boolean
    }

    Main ..> Quine_McCluskey : Creates and calls
    Quine_McCluskey *-- MergeStep : Composition
    Quine_McCluskey *-- PIChart : Composition
    MergeStep ..> Implicant : Uses
    PIChart ..> Implicant : Uses
    MergeStep ..> ImplicantTools : Calls
    PIChart ..> ImplicantTools : Calls
    Quine_McCluskey ..> ImplicantTools : Calls toSOP

```