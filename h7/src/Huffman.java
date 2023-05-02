import java.util.*;
/**
 * Prefix codes and Huffman tree.
 * Tree depends on source data.
 */
public class Huffman {
    Node[] leaves;
    Node root;
    byte[] code;

    private class Node {
        byte symbol;
        Node left;
        Node right;
        int frequency;
        String path;

        private boolean isLeaf(){
            return right==null && left==null;}
    }
    
    /** Constructor to build the Huffman code for a given bytearray.
     * @param original source data
     */
    //https://enos.itcollege.ee/~japoia/algorithms/coding.html
    Huffman (byte[] original) {
        // initialization of leaves - there are 256 possible bytes
        leaves = new Node [256];
        for (int i=0; i<256; i++) {
            leaves [i] = new Node();
            leaves [i].left = null;
            leaves [i].right = null;
            leaves [i].symbol = (byte)(i-128); // Java specifics - signed bytes
            leaves [i].frequency = 0;
        }
        // calculate frequencies
        if (original.length == 0) {
            root = null;
            return;
        }
        for (int i=0; i<original.length; i++) {
            byte b = original[i];
            leaves[b + 128].frequency++;
        }
        // build the tree
        LinkedList<Node> roots = new LinkedList<Node>();
        for (int i=0; i < 256; i++) {
            if (leaves[i].frequency > 0)
                roots.add (leaves[i]); // initial fragments
        } // for i
        while (roots.size()>1) {
            Node least1 = removeSmallest (roots);
            Node least2 = removeSmallest (roots);
            Node newroot = new Node();
            newroot.left = least1;
            newroot.right = least2;
            newroot.frequency = least1.frequency + least2.frequency;
            roots.addLast (newroot);
        }
        root = (Node)roots.remove (0);

        visit(root,"");

    }

    //https://www.herongyang.com/Java/Bit-String-Stored-in-Byte-Array-Test-Program.html
    private static void setBit(byte[] data, int pos, int val) {
        int posByte = pos/8;
        int posBit = pos%8;
        byte oldByte = data[posByte];
        oldByte = (byte) (((0xFF7F>>posBit) & oldByte) & 0x00FF);
        byte newByte = (byte) ((val<<(8-(posBit+1))) | oldByte);
        data[posByte] = newByte;
    }

//https://www.herongyang.com/Java/Bit-String-Get-Bit-from-Byte-Array.html
    private static int getBit(byte[] data, int pos){
        int posByte =pos/8;
        int posBit =pos%8;
        byte valByte= data[posByte];
        int valInt= valByte >>(8-(posBit+1)) & 0x0001;
        return valInt;
    }

    /**
     * Set the code for every leaf node in the tree
     * @param n starting node
     * @param s path to node
     */
    private void visit(Node n,String s){
        if(n.isLeaf()){
            if(s!="") n.path=s;
        }
        else{
            if(n.left!=null){visit(n.left,s+"0");}
            if(n.right!=null){visit(n.right,s+"1");}
        }
    }


    private String searchByte(byte b, Node n){
        if(n.isLeaf()&& n.symbol ==b){
            return n.path;
        }
        if(n.left!=null && searchByte(b,n.left)!=null)return searchByte(b,n.left);
        if(n.right!=null && searchByte(b,n.right)!=null)return searchByte(b,n.right);
        return null;
    }

    private Node removeSmallest(LinkedList<Node> listNodes){
        int min =listNodes.get(0).frequency;
        int index=0;
        for(int i=1;i<listNodes.size();i++){
            Node item = listNodes.get(i);
            if(item.frequency <min){
                min=item.frequency;
                index=i;
            }
        }
        return listNodes.remove(index);
    }

    /** Length of encoded data in bits.
     * @return number of bits
     */
    public int bitLength() {
        int count=0;
        int c=0;
        int nBit=0;
        int nBitTemp=0;
        Node n =root;
        ArrayList<Byte> res = new ArrayList<Byte>();
        if(!root.isLeaf()){
            while(c<code.length*8 && count < root.frequency){

                if(getBit(code,c)==0){
                    n =n.left;
                    nBitTemp++;
                }else{
                    n=n.right;
                    nBitTemp++;
                }
                if(n.isLeaf()){
                    res.add(n.symbol);
                    n=root;
                    count++;
                    nBit+=nBitTemp;
                    nBitTemp=0;
                }
                c++;
            }
        }
        else{
            while (c<code.length*8 && c< root.frequency){
                res.add(root.symbol);
                c++;
                nBit++;
            }
        }
        return nBit;
    }
//https://gist.github.com/snarkbait/c939953337ad74d1ab04
    /** Encoding the byte array using this prefixcode.
     * @param origData original data
     * @return encoded data
     */
    public byte[] encode (byte [] origData) {
        String stat="";
        for (int i=0;i<origData.length;i++){
            byte b= origData[i];
            stat= stat+searchByte(b,root);
        }
        char[] ch_array=stat.toCharArray();
        int plus =0;
        if(ch_array.length%8 !=0){
            plus=1;
        }
        byte[] res  =new byte[ch_array.length/8 +plus];
        for(int i =0 ; i<ch_array.length;i++){
            int val=0;
            if(ch_array[i]=='0') val=0;
            if(ch_array[i]=='1') val=1;
            setBit(res,i,val);

        }
        code=res;
        return res;
    }

    /** Decoding the byte array using this prefixcode.
     * @param encodedData encoded data
     * @return decoded data (hopefully identical to original)
     */
    public byte[] decode (byte[] encodedData) {
        int count = 0;
        int c = 0;
        Node n = root;
        ArrayList<Byte> res = new ArrayList<Byte>();
        if (!root.isLeaf()) {
            while (c< encodedData.length*8 && count < root.frequency){

                if(getBit(encodedData,c)==0){
                    n=n.left;
                }else{
                    n=n.right;
                }
                if(n.isLeaf()){
                    res.add(n.symbol);
                    n=root;
                    count++;
                }
                c++;
            }
        }
        else//root is leaf
        {
            while (c<encodedData.length*8 && c<root.frequency){
                res.add(root.symbol);
                c++;
            }
        }
        byte[] res_byte = new byte[res.size()];
        for(int i=0;i<res.size();i++){
            res_byte[i]=res.get(i);
        }
        return res_byte;
    }

    /** Main method. */
    public static void main (String[] params) {
        String tekst = "AAAAAAAAAAAAABBBBBBCCCDDEEF";
        byte[] orig = tekst.getBytes();
        Huffman huf = new Huffman (orig);
        byte[] kood = huf.encode (orig);
        byte[] orig2 = huf.decode (kood);
        // must be equal: orig, orig2
        System.out.println (Arrays.equals (orig, orig2));
        int lngth = huf.bitLength();
        System.out.println ("Length of encoded data in bits: " + lngth);
    }

}

