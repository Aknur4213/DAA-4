
package DAA;

import DAA.util.InputGenerator;
import DAA.util.InputGenerator.InputPair;

public class Main {

    public static void main(String[] args) {

        InputPair equal = InputGenerator.editEqual(5);
        InputPair different = InputGenerator.editDifferent(5);
        InputPair random = InputGenerator.searchRandom(20);
        InputPair worst = InputGenerator.searchWorstCase(20);

        System.out.println("EQUAL:");
        System.out.println(equal);

        System.out.println("\nDIFFERENT:");
        System.out.println(different);

        System.out.println("\nRANDOM:");
        System.out.println(random);

        System.out.println("\nWORST CASE:");
        System.out.println(worst);
    }
}
