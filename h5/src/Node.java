


//https://www.geeksforgeeks.org/construct-binary-tree-string-bracket-representation/?ref=lbp

import java.util.*;

public class Node {
   private String name;
   private Node firstChild;
   private Node nextSibling;
   public static Stack<Node> parents;

   /* private static int totalDepth;*/


   //private Stack<Node> parentStack;
   Node (String s, Node a, Node b) {
      this.setName (s);
      this.setFirstChild (a);
      this.setNextSibling (b);
      this.parents = new Stack<>();
      /*this.totalDepth = 0;*/
   }
   public void setName(String s) {name = s;}
   public void setNextSibling (Node b) {nextSibling = b;}
   public void setFirstChild (Node a) {firstChild = a;}
   public Node getFirstChild() {return firstChild;}
   public Node getNextSibling() {return nextSibling;}
   public String getName() {return name;}


   public static Node parsePostfix (String s) {

      if (s == null || s.isEmpty()) {
         throw new RuntimeException("Invalid input: input string is null or empty");
      }

      Stack<Node> stack = new Stack<>();
      Stack<Node> parents = new Stack<>();

      int depth = 0;
      int i=0;
      int commaCounter = 0;
      int children= 0;
      int str=s.length();

      /////total
      int t = 0;
      int total=0;
      while (t<s.length()) {
         if (s.charAt(t) == '(') {
            total++;
         }
         t++;
      }

      /////Bracket test
      int z = 0;
      int levels=0;
      while (z<s.length()) {
         if (s.charAt(z) == '(') {
            levels++;
         }
         z++;
      }

      String p =s;


      ///comma2 test
/*      if (s.charAt(0) == ')') {
         if (levels == 0) {
            //
         }else {
            throw new RuntimeException("string does not start with a bracket");
         }
      }*/

      //START OF THE WHILE LOOP THAT HANDLES THE INPUT STRING
      while (i < p.length()) {
         char c = p.charAt(i);
         if (c == '(') {
            depth++;
            i++;

            /////////// CREATING CHILDREN
         }else if (Character.isLetterOrDigit(c)  || c == '-'  ) {
            int j = i;
            while (j < p.length() && p.charAt(j) != ',' && p.charAt(j) != ')') {
               j++;
            }
            /// getting the node name
            String name = p.substring(i, j);
            if (name.isEmpty() || name.contains("(") || name.contains(")") || name.contains(" ") ||name.contains(",")) {
               throw new RuntimeException("Invalid input: invalid node name " + name);
            }

            Node node = new Node(name, null, null);
            stack.push(node);
            if (levels == 0 && stack.size()==1){
               parents.push(stack.pop());
            }

            children++;
            i = j;

         } else if (c == ',') {
            commaCounter++;
            i++;
         } else if (c == '\t') {
            throw new RuntimeException("Invalid input: empty space");



            //////////CREATING THE PARENT/////////////////////////////////////////
         } else if (c == ')') {
            levels--;
            /////////// to add parent after finding that ) is the current token
            int j = i+1;
            int k= j+1;
            String name = p.substring(j, k);

            if (name.contains("(") || name.contains(")") || name.contains(",")) {
               throw new RuntimeException("Invalid input: invalid node name  " + name);
            }
            i=k;

            //////////////////////CHECK IF THIS IS ROOT AND IF SO BREAK
            if (stack.size()==0){
               Node root = new Node(name, null, null);
               root.setFirstChild(parents.pop());
               parents.push(root);
               break;
            }

            ///for testmore
            if (stack.size()==1 && parents.size()==2){
               Node root = new Node(name, null, null);
               Node prevParent=parents.pop();
               Node prevprevParent = parents.pop();
               root.setFirstChild(prevprevParent);
               parents.push(root);
               continue;
            }

            /////// creating  parents
            Node parent = new Node(name, null, null);
            //totalDepth=holder;
            Node oldParent= null;

            if (parents.size()==1 ){
               oldParent=parents.pop();
            }

            parents.push(parent);

            if (parents.size()==1 && stack.size()==1){
               Node child = stack.pop();
               if (oldParent==null) {
               parent.setFirstChild(child);

               }
               //first testwork
               if (oldParent!=null && oldParent.getFirstChild().nextSibling!=null) {
               parent.setFirstChild(oldParent);
               oldParent.setNextSibling(child);
               }
               //testmore
               if (oldParent!=null && oldParent.getFirstChild().nextSibling==null ) {
                  parent.setFirstChild(child);
                  oldParent.setNextSibling(parent);
               }


            }else {
               Node child = stack.pop();
               if (total == 1) {
                  int size = stack.size();
                  for (int w = 0; w < size ; w++) {
                     Node nextChild = stack.pop();
                     nextChild.setNextSibling(child);
                     child = nextChild;
                  }
                  parent.setFirstChild(child);
               } else {

                  for (int w = 0; w < stack.size() + 1; w++) {
                     Node nextChild = stack.pop();
                     if (w == 0 && stack.size() == 0) {
                        nextChild.setNextSibling(child);
                        child = nextChild;
                        break;
                     } else {
                        nextChild.setNextSibling(child);
                        child = nextChild;
                     }
                  }
                  parent.setFirstChild(child);
               }
            }





            if (stack.isEmpty()){
               depth--;
            }
            if (oldParent != null) {

               if (parent.getFirstChild()!= null && oldParent.getFirstChild()!=null && levels ==1 ) {
                  oldParent.setNextSibling(parent);
                  parents.pop();
                  parents.push(oldParent);
               }
            }

            /////////handling deeper  levels
            if (depth != 0 ) {
               p = p.substring(i, p.length());
               i=0;
            }

         }else {
            throw new RuntimeException("Invalid step " + s);

         }

      } /// END OF WHILE LOOP WITH A STACK OF ONE NODE (PARENT)

      if (levels < 1 && parents.size()!=1) {
         throw new RuntimeException("Invalid input: no bracket");
      }
      ///testcomma3
      if (parents.size() == 1 && stack.size()==1 ) {
         throw new RuntimeException("Invalid input: too many nodes on the stack");
      }

      if (parents.size() != 1) {
         throw new RuntimeException("Invalid input: too many nodes on the stack");
      }
      if (commaCounter != children-1){
         throw new RuntimeException("Invalid input: too many commas");
      }
      if (s.charAt(s.length() / 2) == ',' && commaCounter <=1) {
         throw new RuntimeException("Invalid input: two roots " + s);
      }

      Node root = parents.pop();
      //root.processNode();
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
   public static Stack<Node> parents;

   private static int totalDepth;


   //private Stack<Node> parentStack;
   Node (String s, Node a, Node b) {
         this.setName (s);
         this.setFirstChild (a);
         this.setNextSibling (b);
         this.parents = new Stack<>();
         this.totalDepth = 0;
   }
   public void setName(String s) {name = s;}
   public void setNextSibling (Node b) {nextSibling = b;}
   public void setFirstChild (Node a) {firstChild = a;}
   public Node getFirstChild() {return firstChild;}
   public Node getNextSibling() {return nextSibling;}
   public String getName() {return name;}


   public static Node parsePostfix (String s) {

      if (s == null || s.isEmpty()) {
         throw new RuntimeException("Invalid input: input string is null or empty");
      }

      Stack<Node> stack = new Stack<>();
      int depth = 0;
      int i=0;
      int commaCounter = 0;
      int children= 0;

      //START OF THE WHILE LOOP THAT HANDLES THE INPUT STRING
      while (i < s.length()) {

         char c = s.charAt(i);
         if (c == '(') {
            depth++;
            i++;
            /////////// CREATING CHILDREN
         }else if (Character.isLetterOrDigit(c)) {
            int j = i;
            while (j < s.length() && s.charAt(j) != ',' && s.charAt(j) != ')') {
               j++;
            }
            String name = s.substring(i, j);
            if (name.isEmpty() || name.contains("(") || name.contains(")") || name.contains(" ") ||name.contains(",")) {
               throw new RuntimeException("Invalid input: invalid node name " + name);
            }

            if (parents != null && parents.size() == 1) {
               Node parentOfoverlevel = parents.pop();
               stack.push(parentOfoverlevel);
            }
            int holder = totalDepth;
            Node node = new Node(name, null, null);
            totalDepth =holder;
            stack.push(node);
            children++;
            i = j;

         } else if (c == ',') {

            commaCounter++;
            i++;
         } else if (c == '\t') {
            // skip tab
            throw new RuntimeException("Invalid input: empty space");

         } else if (c == ')') {  //////////CREATING THE PARENT

            /////////// to add parent after finding that ) is the current token
            int j = i+1;
            int k= j+1;
            String name = s.substring(j, k);

            if (name.contains("(") || name.contains(")") || name.contains(",")) {
               throw new RuntimeException("Invalid input: invalid node name  " + name);
            }

            int holder = totalDepth;
            //////////////////////CHECK IF THIS IS ROOT AND IF SO BREAK
            if (stack.size()==0){
               stack.push(parents.pop());
               Node root = new Node(name, null, null);
               root.setFirstChild(stack.pop());
               stack.push(root);
               break;
            }

            /////// creating  parents
            Node parent = new Node(name, null, null);
            totalDepth=holder;


            ////////////////////////////CONNECTING CHILDREN
            Node child = stack.pop();

            for (int w =0 ; w < children-1 ;w++){
               Node nextChild = stack.pop();
               nextChild.setNextSibling(child);
               child = nextChild;
            }
            if (!stack.isEmpty()){
               Node anotherParent =stack.pop();
               parent.setNextSibling(anotherParent);
            }

            ///////////////////CONNECTING LAST CHILD TO THE PARENT
            parent.setFirstChild(child);
            stack.push(parent);

            if (depth == totalDepth) {
               totalDepth = depth+1 ;
            }
            if (depth > totalDepth) {
               totalDepth = depth;
            }
            break;

         }else {
            throw new RuntimeException("Invalid step " + s);

         }

      } /// END OF WHILE LOOP WITH A STACK OF ONE NODE (PARENT)

      //parents relation assigning step
      while (totalDepth > 1 ||  Objects.equals(stack.peek().getName(),"+")) {
         Node prevParent = stack.pop();
         if (Objects.equals(prevParent.getName(),"+")){
            stack.push(prevParent);
            break;
         }else {
            int x = i + 2; // testcomma3 failure reason
            String novo = s.substring(x, s.length());
            parents.push(prevParent);
            totalDepth--;
            Node nextParent = Node.parsePostfix(novo);
            if (Objects.equals(nextParent.getName(),"+")){
               stack.push(nextParent);
               break;
            }
            prevParent.setNextSibling(nextParent);
         }
      }


      Node root = stack.pop();
      //root.processNode();
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

*//////////////////////////////////////////////////////////////////////






/*            if (depth < 1 ){
               throw new RuntimeException("Invalid input: no bracket");
            }*/

/*      if (stack.size() != 1) {
         throw new RuntimeException("Invalid input: too many nodes on the stack");
      }*/
/*     if (commaCounter != children-1){
         throw new RuntimeException("Invalid input: too many commas");
      }*/
/*

while (j < s.length() && s.charAt(j) != ',' && s.charAt(j) != ')' && Character.isLetterOrDigit(c)) {

String input = "Hello, world!";

if (input.matches("Hello,.*")) {
    System.out.println("Input starts with 'Hello,'");
} else if (input.matches(".*world!")) {
    System.out.println("Input ends with 'world!'");
} else {
    System.out.println("Input does not match any pattern.");
}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      //START OF THE WHILE LOOP THAT HANDLES THE INPUT STRING
      while (i < s.length()) {
         char c = s.charAt(i);
         if (c == '(') {
            // skip opening parenthesis
            depth++;
            i++;
         } else if (c == ',') {
            // skip comma
            commaCounter++;

            i++;
         } else if (c == '\t') {
            // skip tab
               throw new RuntimeException("Invalid input: empty space");
         } else if (c == ')') {


            //CREATING THE PARENT
            depth--;
            if (stack.size() < 1) {
               throw new RuntimeException("Invalid input: too few nodes on the stack");
            }
            // to add parent after finding that ) is the current
            int j = i+1;
            int k= j+1;
            String name = s.substring(j, k);
            if (name.contains("(") || name.contains(")") || name.contains(",")) {
               throw new RuntimeException("Invalid input: invalid node name  " + name);
            }
            Node parent = new Node(name, null, null);


            //CONNECTING CHILDREN
            Node child = stack.pop();

            for (int w =0 ; w < stack.size()+1 ;w++){
               Node nextChild = stack.pop();
               nextChild.setNextSibling(child);
               child = nextChild;
            }


            //CONNECTING LAST NODE TO THE PARENT
            parent.setFirstChild(child);
            stack.push(parent);
            //parents.push(parent);

            break;
         } else {



            // CREATING CHILDREN
            int j = i;
            while (j < s.length() && s.charAt(j) != ',' && s.charAt(j) != ')') {
               j++;
            }
            String name = s.substring(i, j);
            if (name.isEmpty() || name.contains("(") || name.contains(")") || name.contains(" ") ||name.contains(",")) {
               throw new RuntimeException("Invalid input: invalid node name " + name);
            }

            if (parents != null && parents.size() == 1) {
               Node parentOfoverlevel = parents.pop();
               stack.push(parentOfoverlevel);
            }

            Node node = new Node(name, null, null);
            stack.push(node);
            children++;
            i = j;




         }

      } /// END OF WHILE LOOP WITH A STACK OF ONE NODE (ROOT)
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
*/

/*      if (Objects.equals(stack.peek().getName(), "+")) {
         Node prevParent = stack.pop();
            return prevParent;
      }*/