import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

public class Sheep {

   enum Animal {sheep, goat};
   
   public static void main (String[] param) {
      // for debugging
   }
   
   public static void reorder (Animal[] animals) {
      // TODO!!! Your program here
      //https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html
      //String[] animals = new String[1];
      //animals = new Animal[10];
      //https://www.w3schools.com/java/java_for_loop.asp
      //for (int i = 0; i < 10; i++) {
      //   animals[i] = Animal.goat;
      //}
      //for (int i = 5; i < 10; i++) {
      //   animals[i] = Animal.sheep;
         //animals[i] = String.valueOf(Animal.goat);
      //}
      //https://enos.itcollege.ee/~japoia/docs/jdk-8-docs-all/api/index.html
       //int len = animals.length ;
       //int sheeps = 0 ;
       int goats = 0 ;
       for (int i = 0; i < animals.length ; i++) {

           if (animals[i] == Animal.goat) {
               goats++;
           }
       }

       for (int i = 0; i < goats ; i++) {
           animals[i] = (Animal.goat);
       }

       for (int i = goats ; i < animals.length ; i++) {
           animals[i] = (Animal.sheep);
       }

      /*
      int len = animals.length ;
      for (int i = 0; i < len/2 ; i++) {

         if (animals[i] != Animal.goat) {
            //i++;
         //} else {
            animals[i] = Animal.goat;
         }
      }

      for (int j = len/2; j < len -1; j++) {

         if (animals[j] != Animal.sheep) {
            //j++;
         //} else {
            animals[j] = Animal.sheep;
         }
      }

         /*
         while (i < j) {
             if (animals[i] == Animal.goat) {
                i++;
             } else {
                animals[i] = Animal.goat;
             }

          }
          */

             //int length = animals.length;
             //animals[length] = Sheep.Animal.goat;
             //Sheep.reorder (animals);
             //System.out.println(Arrays.toString(animals));

          }

}

