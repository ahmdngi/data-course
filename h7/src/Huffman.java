import java.util.*;

public class Huffman {
   private final Node root;
   private final Map<Byte, String> codeMap;
   private int bitLength;

   private static class Node implements Comparable<Node> {
      Byte value;
      int frequency;
      Node left, right;

      Node(Byte value, int frequency) {
         this.value = value;
         this.frequency = frequency;
      }

      Node(Node left, Node right) {
         this.left = left;
         this.right = right;
         this.frequency = left.frequency + right.frequency;
      }

      boolean isLeaf() {
         return left == null && right == null;
      }

      @Override
      public int compareTo(Node other) {
         return Integer.compare(this.frequency, other.frequency);
      }
   }

   public Huffman(byte[] original) {
      Map<Byte, Integer> frequencyMap = new HashMap<>();
      for (byte b : original) {
         frequencyMap.put(b, frequencyMap.getOrDefault(b, 0) + 1);
      }

      PriorityQueue<Node> pq = new PriorityQueue<>();
      for (Map.Entry<Byte, Integer> entry : frequencyMap.entrySet()) {
         pq.offer(new Node(entry.getKey(), entry.getValue()));
      }

      while (pq.size() > 1) {
         Node left = pq.poll();
         Node right = pq.poll();
         pq.offer(new Node(left, right));
      }

      root = pq.poll();
      codeMap = new HashMap<>();
      generateCodes(root, new StringBuilder());
   }

   private void generateCodes(Node node, StringBuilder code) {
      if (node.isLeaf()) {
         codeMap.put(node.value, code.toString());
         bitLength += node.frequency * code.length();
      } else {
         generateCodes(node.left, code.append('0'));
         code.setLength(code.length() - 1);
         generateCodes(node.right, code.append('1'));
         code.setLength(code.length() - 1);
      }
   }

   public int bitLength() {
      return bitLength;
   }

   public byte[] encode(byte[] origData) {
      StringBuilder encodedBits = new StringBuilder();
      for (byte b : origData) {
         encodedBits.append(codeMap.get(b));
      }

      int encodedSize = (encodedBits.length() + 7) / 8;
      byte[] encodedData = new byte[encodedSize];

      for (int i = 0; i < encodedBits.length(); i++) {
         if (encodedBits.charAt(i) == '1') {
            encodedData[i / 8] |= 1 << (7 - (i % 8));
         }
      }

      return encodedData;
   }

   public byte[] decode(byte[] encodedData) {
      List<Byte> decodedData = new ArrayList<>();
      Node currentNode = root;

      for (int i = 0; i < encodedData.length * 8; i++) {
         int bit = (encodedData[i / 8] >> (7 - (i % 8))) & 1;
         currentNode = bit == 0 ? currentNode.left : currentNode.right;

         if (currentNode.isLeaf()) {
            decodedData.add(currentNode.value);
            currentNode = root;
         }
      }

      byte[] result = new byte[decodedData.size()];
      for (int i = 0; i < result.length; i++) {
         result[i] = decodedData.get(i);
      }
      return result;
   }

   public static void main(String[] params) {
      String tekst = "AAAAAAAAAAAAABBBBBBCCCDDEEF";
      byte[] orig = tekst.getBytes();
      Huffman huf = new Huffman(orig);
      byte[] kood = huf.encode(orig);
      byte[] orig2 = huf.decode(kood);

      System.out.println(Arrays.equals(orig, orig2));
      int lngth = huf.bitLength();
      System.out.println("Length of encoded data in bits: " + lngth);

      // Additional test for bytearray of length 1
      String singleCharText = "A";
      byte[] singleCharOrig = singleCharText.getBytes();
      Huffman singleCharHuf = new Huffman(singleCharOrig);
      byte[] singleCharKood = singleCharHuf.encode(singleCharOrig);
      byte[] singleCharOrig2 = singleCharHuf.decode(singleCharKood);

      System.out.println(Arrays.equals(singleCharOrig, singleCharOrig2));
      int singleCharLngth = singleCharHuf.bitLength();
      System.out.println("Length of encoded data in bits for bytearray of length 1: " + singleCharLngth);

   }
}



