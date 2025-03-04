import java.util.random.RandomGenerator;

public class CRD {
   public static void main(String[] args) {
      var rn = RandomGenerator.getDefault(); 
      int[] f = new int[7]; 

      for (int roll = 1; roll <= 60_000_000; ++roll) {
         ++f[rn.nextInt(1, 7)];
      } 

      System.out.printf("%s%12s%n", "Face", "Frequency");
   
      for (int i = 1; i < f.length; ++i) {
         System.out.printf("%4d%12d%n", i, f[i]);
      } 
   } 
} 


/**************************************************************************
 * (C) Copyright 1992-2025 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
