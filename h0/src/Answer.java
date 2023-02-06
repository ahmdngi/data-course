import java.util.*;

/** Lab 1.
 * @since 1.8
 */
public class Answer {

    public static void main (String[] param) {

        // TODO!!! Solutions to small problems
        //   that do not need an independent method!

        // conversion double -> String

        // conversion String -> int

        // "hh:mm:ss"

        // cos 45 deg

        // table of square roots

        String firstString = "ABcd12";
        String result = reverseCase (firstString);
        System.out.println ("\"" + firstString + "\" -> \"" + result + "\"");

        // reverse string

        String s = "How  many      words   here";
        int nw = countWords (s);
        System.out.println (s + "\t" + nw);

        // pause. COMMENT IT OUT BEFORE JUNIT-TESTING!

        final int LSIZE = 100;
        ArrayList<Integer> randList = new ArrayList<Integer> (LSIZE);
        Random generaator = new Random();
        for (int i=0; i<LSIZE; i++) {
            randList.add (generaator.nextInt(1000));
        }

        // minimal element

        // HashMap tasks:
        //    create
        //    print all keys
        //    remove a key
        //    print all pairs

        System.out.println ("Before reverse:  " + randList);
        reverseList (randList);
        System.out.println ("After reverse: " + randList);

        System.out.println ("Maximum: " + maximum (randList));
    }

    /** Finding the maximal element.
     * @param a Collection of Comparable elements
     * @return maximal element.
     * @throws NoSuchElementException if <code> a </code> is empty.
     */
    static public <T extends Object & Comparable<? super T>>
    T maximum (Collection<? extends T> a)
            throws NoSuchElementException {
        return null; // TODO!!! Your code here
    }

    /** Counting the number of words. Any number of any kind of
     * whitespace symbols between words is allowed.
     * @param text text
     * @return number of words in the text
     */
    public static int countWords (String text) {
        return 0; // TODO!!! Your code here
    }

    /** Case-reverse. Upper -> lower AND lower -> upper.
     * @param s string
     * @return processed string
     */
    public static String reverseCase (String s) {
        return null; // TODO!!! Your code here
    }

    /** List reverse. Do not create a new list.
     * @param list list to reverse
     */
    public static <T> void reverseList (List<T> list)
            throws UnsupportedOperationException {
        // TODO!!! Your code here
    }
}

