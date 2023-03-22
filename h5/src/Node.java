

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
            if (stack.size() < 1) {
               throw new RuntimeException("Invalid input: too few nodes on the stack");
            }

            // to add root after finding that ) is the current
            int j = i+1;
            int k= j+1;

            String name = s.substring(j, k);
            if (name.isEmpty() || name.contains("(") || name.contains(")") || name.contains(",")) {
               throw new RuntimeException("Invalid input: invalid node name  " + name);
            }
            Node root = new Node(name, null, null);
            //stack.push(root);

            while (!stack.isEmpty()){
               Node child = stack.pop();
               Node nextChild= stack.pop();
               nextChild.nextSibling= child;
            }
            // end of root handling
            /////////////
           /* Node child = stack.pop();
            Node child2 = stack.pop();
            Node child3 = stack.pop();
            child2.nextSibling = child;
            child3.nextSibling= child2;
            root.firstChild=child3;*/
            ///////////////
/*          Node child = stack.pop();
            Node parent = stack.pop();
            child.nextSibling = parent.firstChild;
            parent.firstChild = child;
            stack.push(parent);*/

            //stack.push(child3);
            //stack.push(child2);
            //stack.push(child);
            stack.push(root);

            break;
         } else {
            // leaf node - parse name and create node, push onto stack
            int j = i;
            while (j < s.length() && s.charAt(j) != ',' && s.charAt(j) != ')') {
               j++;
            }
            String name = s.substring(i, j);
            if (name.isEmpty() || name.contains("(") || name.contains(")") || name.contains(",")) {
               throw new RuntimeException("Invalid input: invalid node name " + name);
            }
            Node node = new Node(name, null, null);
            stack.push(node);
            i = j;
         }
      }

     if (stack.size() != 1) {
        throw new RuntimeException("Invalid input: too many nodes on the stack");
     }

      Node root = stack.pop();
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
   }
}
/*
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

   public static Node parsePostfix (String s) {
      Stack<Node> stack = new Stack<>();
      for (int i = 0; i < s.length(); i++) {
         char c = s.charAt(i);
         if (Character.isLetterOrDigit(c)) {
            stack.push(new Node(Character.toString(c), null, null));
         } else {
            Node right = stack.pop();
            Node left = stack.pop();
            stack.push(new Node(Character.toString(c), left, right));
         }
      }
      Node root = stack.pop();
      return root;
   }

   public String leftParentheticRepresentation() {
      StringBuilder sb = new StringBuilder();
      sb.append(name);
      if (firstChild != null) {
         sb.append('(');
         sb.append(firstChild.leftParentheticRepresentation());
         sb.append(')');
         Node sibling = nextSibling;
         while (sibling != null) {
            sb.append(',');
            sb.append(sibling.leftParentheticRepresentation());
            sibling = sibling.nextSibling;
         }
      }
      return sb.toString();
   }

   public static void main (String[] param) {
      String s = "(B1,C)A";
      Node t = Node.parsePostfix (s);
      String v = t.leftParentheticRepresentation();
      System.out.println (s + " ==> " + v); // (B1,C)A ==> A(B1,C)

      s = "(((2,1)-,4)*,(69,3)/)+";
      t = Node.parsePostfix (s);
      v = t.leftParentheticRepresentation();
      System.out.println (s + " ==> " + v); // (((2,1)-,4)*,(69,3)/)+ ==> +(*(-(2,1),4),/(69,3))
   }
}
*/

/*
import java.util.*;

public class Node {

   private String name;
   private Node firstChild;
   private Node nextSibling;

   Node(String n, Node d, Node r) {
      this.name = n;
      this.firstChild = d;
      this.nextSibling = r;
   }

   public static Node parsePostfix(String s) {
      Stack<Node> stack = new Stack<>();
      for (int i = 0; i < s.length(); i++) {
         char c = s.charAt(i);
         if (c == ',') {
            continue;
         }
         if (c == ')') {
            Node child = stack.pop();
            Node parent = stack.pop();
            parent.firstChild = child;
            stack.push(parent);
         } else {
            Node node = new Node(Character.toString(c), null, null);
            stack.push(node);
         }
      }
      if (stack.size() != 1) {
         throw new RuntimeException("Invalid input string");
      }
      return stack.pop();
   }

   public String leftParentheticRepresentation() {
      StringBuilder sb = new StringBuilder();
      leftParentheticRepresentationHelper(sb);
      return sb.toString();
   }

   private void leftParentheticRepresentationHelper(StringBuilder sb) {
      sb.append(this.name);
      if (this.firstChild != null) {
         sb.append("(");
         this.firstChild.leftParentheticRepresentationHelper(sb);
         sb.append(")");
      }
      if (this.nextSibling != null) {
         sb.append(",");
         this.nextSibling.leftParentheticRepresentationHelper(sb);
      }
   }

   public static void main (String[] param) {
      String s = "(B1,C)A";
      Node t = Node.parsePostfix (s);
      String v = t.leftParentheticRepresentation();
      System.out.println (s + " ==> " + v); // (B1,C)A ==> A(B1,C)

      s = "(((2,1)-,4)*,(69,3)/)+";
      t = Node.parsePostfix (s);
      v = t.leftParentheticRepresentation();
      System.out.println (s + " ==> " + v); // (((2,1)-,4)*,(69,3)/)+ ==> +(*(-(2,1),4),/(69,3))
   }
}
*/

