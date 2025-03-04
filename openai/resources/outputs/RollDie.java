import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

public class RollDie {

   private static final int NUM_ROLLS = 600_000_000;
   private static final int NUM_SIDES = 6;

   public static void main(String[] args) {

      // Use LongAdder for efficient counting in a concurrent environment
      LongAdder[] frequencies = new LongAdder[NUM_SIDES];
      for (int i = 0; i < NUM_SIDES; i++) {
         frequencies[i] = new LongAdder();
      }

      // Parallelizing the workload
      IntStream.range(0, NUM_ROLLS).parallel().forEach(i -> {
         int face = ThreadLocalRandom.current().nextInt(1, NUM_SIDES + 1);
         frequencies[face - 1].increment();
      });

      // Display the result
      System.out.printf("%-5s %-10s%n", "Face", "Frequency");
      for (int i = 0; i < NUM_SIDES; i++) {
         System.out.printf("%-5d %-10d%n", i + 1, frequencies[i].longValue());
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
