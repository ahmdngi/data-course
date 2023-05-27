import java.util.*;

public class Node {

   private String name;
   private Node   firstChild;
   private Node   nextSibling;

   Node (String n, Node d, Node r) {
      setName (n);
      setFirstChild (d);
      setNextSibling (r);
   }

   public void   setName (String n)        { name = n; }
   public String getName()                 { return name; }
   public void   setFirstChild (Node d)    { firstChild = d; }
   public Node   getFirstChild()           { return firstChild; }
   public void   setNextSibling (Node r)   { nextSibling = r; }
   public Node   getNextSibling()          { return nextSibling; }

   @Override
   public String toString() {
      return leftParentheticRepresentation();
   }

   public String leftParentheticRepresentation() {
      StringBuffer b = new StringBuffer();
      b.append (getName());
      if (getFirstChild() != null) {
         b.append ("(");
         b.append (getFirstChild().leftParentheticRepresentation());
         Node right = getFirstChild().getNextSibling();
         while (right != null) {
            b.append (",");
            b.append (right.leftParentheticRepresentation());
            right = right.getNextSibling();
         }
         b.append (")");
      }
      return b.toString();
   }

   public static Node parseTree (String s) {
      if (s == null) return null;
      if (s.length() == 0) return null;
      Node root = null;
      Node curr = null;
      Node last = null;
      int state = 0; // begin
      Stack<Node> stk = new Stack<Node>();
      StringTokenizer tok = new StringTokenizer (s, "(),", true);
      while (tok.hasMoreTokens()) {
         String w = tok.nextToken().trim();
         if (w.equals ("(")) {
            state = 1; // from up
         } else if (w.equals (",")) {
            state = 2; // from left
         } else if (w.equals (")")) {
            state = 3; // from down
            stk.pop();
         } else {
            curr = new Node (w, null, null);
            switch (state) {
               case 0: {
                  root = curr;
                  break;
               }
               case 1: {
                  last = stk.peek();
                  last.setFirstChild (curr);
                  break;
               }
               case 2: {
                  last = stk.pop();
                  last.setNextSibling (curr);
                  break;
               }
               default: {
               }
            } // switch
            stk.push (curr);
         }
      } // next w
      return root;
   }

   public int numberOfLeaves() {
      String s = String.valueOf(this);
      int no =0;
      String[] arr = s.split("");
      List<String> str = new ArrayList<String>(Arrays.asList(arr));
      for (int i = 0; i < str.size(); i++) {
         if (str.size() == 1) {
            no =1;
            System.out.println ("Number of leaves: " + 1);
            break;
         }

         if (str.get(i).equals("(")) {
            if (str.get(i + 2).equals(",")) {
               no++;
            }
         } else if (str.get(i).equals(")")) {

            if (str.get(i - 2).equals(",")) {
               no++;
            }
            if (str.get(i - 2).equals("(")) {
               no++;
            }
         } else if (str.get(i).equals(",")) {
            if (str.get(i + 2).equals(",")) {
               no++;
            }

         } else {
         }
      }
      return no;

   }

   public static void main (String[] param) {
      //Node v = Node.parseTree ("A(B,C(D,F(K,L,M,N(O)),P))");
      Node v = Node.parseTree ("A");
      System.out.println (v);
      int n = v.numberOfLeaves();
      if (v.getFirstChild() == null) {
         throw new RuntimeException("this is a single root node (number of leaves 1)");
      }else{
         System.out.println ("Number of leaves: " + n); // 7
      }
   }
}
