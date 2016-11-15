
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author drapek
 */
public class CarAdditionsSorting implements Comparator<String>{

    @Override
    public int compare(String o1, String o2) {
        final int BEFORE = -1;
      final int AFTER = 1;

      /* To reverse the sorting order, multiple by -1 */
      if (o2 == null) {
         return BEFORE * -1;
      }

      Comparable thisAddition = o1;
      Comparable thatAddition = o2;

      if(thisAddition == null) {
         return AFTER * -1;
      } else if(thatAddition == null) {
         return BEFORE * -1;
      } else {
         return thisAddition.compareTo(thatAddition) * -1;
      }
    }
    
}
