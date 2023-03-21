//import java.util.Arrays;
//https://www.geeksforgeeks.org/stack-class-in-java/
//https://stevepesce879.medium.com/what-is-a-stack-and-how-to-create-one-in-java-e2c0f2852eb7
//https://www.youtube.com/watch?v=fptlqsesjxY&t=48s
//https://www.youtube.com/watch?v=MJ23yC-1tVw
//https://github.com/TheAlgorithms/Java/blob/master/src/main/java/com/thealgorithms/datastructures/stacks/StackOfLinkedList.java
//https://github.com/TheAlgorithms/Java/blob/master/src/main/java/com/thealgorithms/datastructures/stacks/NodeStack.java
public class LongStack {
   public static void main (String[] arg) {
   }
   private static class Node {
      long data;
      Node next;
      Node(long data){
         this.data=data;
         this.next=null;
      }
   }
   Node top;
   int size;
   LongStack(){
      top = null;
      size=0;
   }

   @Override
   public Object clone() throws CloneNotSupportedException {
      LongStack clonedStack = new LongStack();
      Node clonedTop = new Node(this.top.data);
      clonedStack.top = clonedTop;
      Node current = this.top.next;
      Node clonedCurrent = clonedTop;
      while (current != null) {
         Node clonedNode = new Node(current.data);
         clonedCurrent.next = clonedNode;
         current = current.next;
         clonedCurrent = clonedNode;
      }
      return clonedStack;

   }
   public int size() {

      return size;
   }
   public boolean stEmpty() {

      return top == null;
   }

   public void push (long a) {
      Node newNode = new Node(a);
      newNode.next = top;
      top = newNode;
      size ++;
   }

   public long pop() {
      if (stEmpty()) {
         throw new RuntimeException("Stack Underflow");
      }
      long data = top.data;
      top = top.next;
      size --;
      return data;
   }
   public void op (String s) {
      if (this.size() < 2) {
         throw new RuntimeException("Stack Underflow" + s);
      }
      long b = pop();
      long a = pop();
      long result;
      switch (s) {
         case "+":
            result = a + b;
            break;
         case "-":
            result = a - b;
            break;
         case "*":
            result = a * b;
            break;
         case "/":
            if (b == 0) {
               throw new RuntimeException("Division by zero" + s);
            }
            result = a / b;
            break;
         default:
            throw new RuntimeException("Invalid operator: " + s);
      }
      push(result);

   }
   public long tos() {
      if (stEmpty()) {
         throw new RuntimeException("Stack Underflow");
      }
      return top.data;
   }

   @Override
   //https://stackoverflow.com/questions/28844026/writing-an-equals-method-for-linked-list-object
   public boolean equals (Object o) {
      if (o == this) {
         return true;
      }
      if (!(o instanceof LongStack)) {
         return false;
      }
      LongStack other = (LongStack) o;
      if (stEmpty() != other.stEmpty()) {
         return false;
      }
      Node thisTop = top;
      Node otherTop = other.top;
      while (thisTop != null && otherTop != null) {
         if (thisTop.data != otherTop.data) {
            return false;
         }
         thisTop = thisTop.next;
         otherTop = otherTop.next;
      }
      return true;
   }

   @Override
   public String toString() {
      StringBuilder a = new StringBuilder();
      Node current = top;
      while (current != null) {
         a.insert(0, current.data + " ");
         current = current.next;
      }
      return a.toString().trim();
   }

   public static long interpret (String pol) {
      LongStack stack = new LongStack();
      String[] tokens = pol.split(" ");
      for (String token : tokens) {
         try {
            if (isNull(token)) {
               throw new RuntimeException("Empty Token " + pol);
            } else if (isWhite(token)) {
               throw new RuntimeException("White Space Token " + pol);
            } else if (token.contains("\t")) {
               token = token.replace("\t", "");
               stack.push(Long.parseLong(token));
               throw new RuntimeException("Token Contains Tab " + pol);
            } else if (isIllegal(token)) {
               stack.pop();
               throw new RuntimeException("an illegal symbol " + pol);
            }
         } catch (RuntimeException e) {
            continue;
         }
         if (isNumber(token)) {
            stack.push(Long.parseLong(token));
         }
         else if (isOperator(token)) {
            if (stack.size() < 1) {
               throw new RuntimeException("Not enough numbers to perform operation " + pol);
            }
            switch (token) {
               case "+": {
                  long op2 = stack.pop();
                  long op1 = stack.pop();
                  stack.push(op1 + op2);
                  break;
               }
               case "-": {
                  long op2 = stack.pop();
                  long op1 = stack.pop();
                  stack.push(op1 - op2);
                  break;
               }
               case "*": {
                  long op2 = stack.pop();
                  long op1 = stack.pop();
                  stack.push(op1 * op2);
                  break;
               }
               case "/": {
                  long op2 = stack.pop();
                  long op1 = stack.pop();
                  if (op2 == 0) {
                     throw new RuntimeException("Division by zero" + pol);
                  }
                  stack.push(op1 / op2);
                  break;
               }
               case "SWAP": {
                  if (stack.size() < 2) {
                     throw new RuntimeException("Not enough elements to perform SWAP operation " + pol);
                  }
                  long op2 = stack.pop();
                  long op1 = stack.pop();
                  stack.push(op2);
                  stack.push(op1);
                  break;
               }
               case "ROT": {
                  if (stack.size() < 3) {
                     throw new RuntimeException("Not enough elements to perform ROT operation " + pol);
                  }
                  long top = stack.pop();
                  long mid = stack.pop();
                  long bot = stack.pop();
                  stack.push(mid);
                  stack.push(top);
                  stack.push(bot);
                  break;
               }
               case "DUP": {
                  if (stack.size() < 1) {
                     throw new RuntimeException("Not enough elements to perform DUP operation " + pol);
                  }
                  long op = stack.pop();
                  stack.push(op);
                  stack.push(op);
                  break;
               }
               default:
                  throw new RuntimeException("Invalid operator: " + pol);
            }
         }
         else {
            throw new RuntimeException("Invalid token: " + pol);
         }
      }
      if (stack.stEmpty() ) {
         throw new RuntimeException("Not enough numbers"  + pol);

      }
      long result = stack.pop();
      if (!stack.stEmpty()){
         throw new RuntimeException("Too many Numbers " + pol);

      }
      return result;
   }
   private static boolean isNumber(String s) {
      return s.matches("-?\\d+");
   }
   private static boolean isWhite(String s) {
      return s.matches("\\s+");
   }

   private static boolean isNull(String s) {
      return s.matches("^$");
   }
   private static boolean isOperator(String s) {
      return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("SWAP") || s.equals("ROT") || s.equals("DUP") ;

   }
   private static boolean isIllegal(String s) {
      return s.matches("[a-zA-Z]+") && !s.equals("SWAP") && !s.equals("ROT") && !s.equals("DUP");
   }

}

//System.out.println("Token Contains Tab " + Arrays.toString(tokens));
//token = token.replace("[a-zA-Z]+", "");
//System.out.println("an illegal symbol " + pol);
//System.out.println("Too many Numbers " + pol);
/*
         catch (RuntimeException e) {
            // handle the error and continue processing
            System.err.println("Error: " + e.getMessage());
            continue;
         }
         if (isNull(token)) {
            stack.pop(Long.parseLong(token));
            throw new RuntimeException("Null Token " + token);
            continue;
         }
         else if (isWhite(token)) {
            throw new RuntimeException("White Space" + token);
         }
         else if (token.contains("\t")) {
               token = token.replace("\t", "");
               stack.push(Long.parseLong(token));
            throw new RuntimeException("White Space" + token);
         }
         */
/*
   private static boolean isTab(String s) {
      return s.matches("\\t+");
   }
*/
      /*
      if (stack.size() != 1) {
         throw new RuntimeException("Too many operands");
      }
      */
      /*
      Node current=this.top;
      Node newTop;
      newTop = new Node(current.data);
      clonedstack.top = newTop;
      */