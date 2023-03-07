
import java.util.*;

public class Node {
   private String name;
   private Node firstChild;
   private Node nextSibling;

   Node (String n, Node d, Node r) {
      name = n;
      firstChild = d;
      nextSibling = r;
   }
   public static Node parsePostfix(String s) {
      if (s == null || s.isEmpty()) {
         throw new RuntimeException("Invalid input: input string is null or empty");
      }

      Stack<Node> stack = new Stack<>();
      Node root = null;
      int i = 0;

      while (i < s.length()) {
         char c = s.charAt(i);

         if (c == '(') {
            // skip opening parenthesis
            i++;
         } else if (c == ',') {
            // skip comma
            i++;
         } else if (c == ')') {
            // closing parenthesis - pop two nodes from stack and link them
            if (stack.size() < 2) {
               throw new RuntimeException("Invalid input: too few nodes on the stack");
            }
            Node child = stack.pop();
            Node parent = stack.pop();
            child.nextSibling = parent.firstChild;
            parent.firstChild = child;
            stack.push(parent);
            i++;
         } else {
            // leaf node - parse name and create node, push onto stack
            int j = i;
            while (j < s.length() && s.charAt(j) != ',' && s.charAt(j) != ')') {
               j++;
            }
            String name = s.substring(i, j);
            Node node = new Node(name, null, null);
            if (root == null) {
               root = node;
            }
            stack.push(node);
            i = j;
         }
      }

      if (stack.size() != 1) {
         throw new RuntimeException("Invalid input: too many nodes on the stack");
      }

      return root;
   }
   public String leftParentheticRepresentation() {
      StringBuilder sb = new StringBuilder();
      sb.append(name);
      if (firstChild != null) {
         sb.append('(');
         sb.append(firstChild.leftParentheticRepresentation());
         sb.append(')');
      }
      if (nextSibling != null) {
         sb.append(',');
         sb.append(nextSibling.leftParentheticRepresentation());
      }
      return sb.toString();
   }

   public static void main (String[] param) {
      String s = "(B1,C)A";
      Node t = Node.parsePostfix (s);
      String v = t.leftParentheticRepresentation();
      System.out.println (s + " ==> " + v); // (B1,C)A ==> A(B1,C)

      String s2 = "A";
      Node t2 = Node.parsePostfix (s2);
      String v2 = t2.leftParentheticRepresentation();
      System.out.println (s2 + " ==> " + v2); // A ==> A

      String s3 = "A)";
      try {
         Node t3 = Node.parsePostfix (s3);
      } catch (RuntimeException e) {
         System.out.println(e.getMessage()); // Invalid input string: A)
      }

      String s4 = "(A,B,C)";
      Node t4 = Node.parsePostfix (s4);
      String v4 = t4.leftParentheticRepresentation();
      System.out.println (s4 + " ==> " + v4); // (A,B,C) ==> A(B,C)

      String s5 = "(A(B(C,D(E,F),G),H(I)))";
      Node t5 = Node.parsePostfix (s5);
      String v5 = t5.leftParentheticRepresentation();
      System.out.println (s5 + " ==> " + v5); // (A(B(C,D(E,F),G),H(I))) ==> A(B(C,D(E,F),G),H(I))
   }

}