import java.io.ByteArrayOutputStream;
import java.util.*;

public class Huffman {
   private TreeNode root;
   private Map<Byte, String> huffmanCodes;

   Huffman(byte[] original) {
      Map<Byte, Integer> frequencyMap = buildFrequencyMap(original);
      root = buildHuffmanTree(frequencyMap);
      huffmanCodes = new HashMap<>();
      generateHuffmanCodes(root, "");
   }

   private Map<Byte, Integer> buildFrequencyMap(byte[] original) {
      Map<Byte, Integer> frequencyMap = new HashMap<>();
      for (byte b : original) {
         frequencyMap.put(b, frequencyMap.getOrDefault(b, 0) + 1);
      }
      return frequencyMap;
   }

   private TreeNode buildHuffmanTree(Map<Byte, Integer> frequencyMap) {
      PriorityQueue<TreeNode> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.frequency));

      for (Map.Entry<Byte, Integer> entry : frequencyMap.entrySet()) {
         queue.offer(new TreeNode(entry.getKey(), entry.getValue()));
      }

      while (queue.size() > 1) {
         TreeNode left = queue.poll();
         TreeNode right = queue.poll();
         TreeNode parent = new TreeNode(null, left.frequency + right.frequency);
         parent.left = left;
         parent.right = right;
         queue.offer(parent);
      }

      return queue.poll();
   }

   private void generateHuffmanCodes(TreeNode node, String code) {
      if (node.isLeaf()) {
         huffmanCodes.put(node.value, code);
      } else {
         generateHuffmanCodes(node.left, code + "0");
         generateHuffmanCodes(node.right, code + "1");
      }
   }

   public int bitLength() {
      int length = 0;
      for (String code : huffmanCodes.values()) {
         length += code.length();
      }
      return length;
   }

   public byte[] encode(byte[] original) {
      StringBuilder sb = new StringBuilder();
      for (byte b : original) {
         sb.append(huffmanCodes.get(b));
      }
      return sb.toString().getBytes();
   }

   public byte[] decode(byte[] encodedData) {
      StringBuilder sb = new StringBuilder(new String(encodedData));
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      TreeNode current = root;

      for (int i = 0; i < sb.length(); i++) {
         current = sb.charAt(i) == '0' ? current.left : current.right;
         if (current.isLeaf()) {
            baos.write(current.value);
            current = root;
         }
      }

      return baos.toByteArray();
   }

   private static class TreeNode {
      Byte value;
      int frequency;
      TreeNode left;
      TreeNode right;

      TreeNode(Byte value, int frequency) {
         this.value = value;
         this.frequency = frequency;
      }

      boolean isLeaf() {
         return left == null && right == null;
      }
   }
   public static void main(String[] args) {
      String tekst = "ABCDEFAAABBC";
      byte[] orig = tekst.getBytes();
      Huffman huf = new Huffman(orig);
      byte[] kood = huf.encode(orig);
      byte[] orig2 = huf.decode(kood);
      System.out.println(Arrays.equals(orig, orig2));

      for (byte b : orig) {
         //String encoded = huf.encodingMap.get(b);
         //System.out.println("Symbol " + (char) b + " code: " + b + " frequency " + huf.root.frequency + " and hcodeLength " + encoded.length() + " bits hcode " + encoded);
      }
   }
}




//https://www.delftstack.com/howto/java/java-huffman-code/
//import java.util.*;
//
///**
// * Prefix codes and Huffman tree.
// * Tree depends on source data.
// */
//public class Huffman {
//
//   // TODO!!! Your instance variables here!
//
//   /** Constructor to build the Huffman code for a given bytearray.
//    * @param original source data
//    */
//   Huffman (byte[] original) {
//      // TODO!!! Your constructor here!
//   }
//
//   /** Length of encoded data in bits.
//    * @return number of bits
//    */
//   public int bitLength() {
//      return 0; // TODO!!!
//   }
//
//
//   /** Encoding the byte array using this prefixcode.
//    * @param origData original data
//    * @return encoded data
//    */
//   public byte[] encode (byte [] origData) {
//      return null; // TODO!!!
//   }
//
//   /** Decoding the byte array using this prefixcode.
//    * @param encodedData encoded data
//    * @return decoded data (hopefully identical to original)
//    */
//   public byte[] decode (byte[] encodedData) {
//      return null; // TODO!!!
//   }
//
//   /** Main method. */
//   public static void main (String[] params) {
//      String tekst = "AAAAAAAAAAAAABBBBBBCCCDDEEF";
//      byte[] orig = tekst.getBytes();
//      Huffman huf = new Huffman (orig);
//      byte[] kood = huf.encode (orig);
//      byte[] orig2 = huf.decode (kood);
//      // must be equal: orig, orig2
//      System.out.println (Arrays.equals (orig, orig2));
//      int lngth = huf.bitLength();
//      System.out.println ("Length of encoded data in bits: " + lngth);
//      // TODO!!! Your tests here!
//   }
//}
//
