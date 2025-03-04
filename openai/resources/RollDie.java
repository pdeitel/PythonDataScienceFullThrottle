// Fig. 6.6: RollDie.java
// Die-rolling program using arrays instead of switch.
import java.util.random.RandomGenerator;

public class RollDie {
   public static void main(String[] args) {
      var randomNumbers = RandomGenerator.getDefault(); 
      int[] frequency = new int[7]; // array of frequency counters

      // roll die 60,000,000 times; use die value as frequency index
      for (int roll = 1; roll <= 60_000_000; ++roll) {
         ++frequency[randomNumbers.nextInt(1, 7)];
      } 

      System.out.printf("%s%12s%n", "Face", "Frequency");
   
      // output each array element's value
      for (int face = 1; face < frequency.length; ++face) {
         System.out.printf("%4d%12d%n", face, frequency[face]);
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
