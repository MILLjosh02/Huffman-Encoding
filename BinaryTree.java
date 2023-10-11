//Joshua Millonado
//COMP282
//03/25/23
//Assignment Week 9
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import tools.Controls;
import tools.Colors;


public class BinaryTree {


    private Node root;


    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left;
        private final Node right;


        public Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }


        public boolean isLeaf() {
            return left == null && right == null;
        }
        @Override
        public int compareTo(Node other) {
            return this.freq - other.freq;
        }
    }


    public void binarytree(String input) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : input.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for (char c : frequencyMap.keySet()) {
            priorityQueue.offer(new Node(c, frequencyMap.get(c), null, null));
        }
        while (priorityQueue.size() > 1) {
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            priorityQueue.offer(parent);
        }
        this.root = priorityQueue.poll();
    }
   


    private void printNodes(Node node, int x, int y, int w, int h) {
        if (node != null) {
            Controls.Box(x - 2, y - 1, 6, 3, 91, Colors.WHITE, Colors.BLACK_BACKGROUND);
            Controls.SetForegroundBackgroundColor(Colors.RED, Colors.WHITE_BACKGROUND);
            Controls.printxy(x, y, node.ch + ":" + node.freq);
            Controls.SetForegroundBackgroundColor(Colors.WHITE, Colors.BLACK_BACKGROUND);
            printNodes(node.left, x - (w / 2), y + (h / 2), w, h);
            printNodes(node.right, x + (w / 2), y + (h / 2), w, h);
        }
    }


    public void DisplayTreeHuffmanEncoding(String input) {
       // Controls.cls();
       binarytree(input);
        displayTree();
        displayTable();
      //  printNodes(root, 40, 5, 20, 15);
    }
    public void displayTree(){
        Controls.cls();
        printNodes(root, 40, 5, 20, 15);
    }


    private void generateCodes(Node node, String code, Map<Character, String> codeMap) {
        if (node == null) {
            return;
        }
        if (node.isLeaf()) {
            codeMap.put(node.ch, code);
        } else {
            generateCodes(node.left, code + "0", codeMap);
            generateCodes(node.right, code + "1", codeMap);
        }
    }


    public Map<Character, String> generateCodes() {
        Map<Character, String> codeMap = new HashMap<>();
        generateCodes(root, "", codeMap);
        return codeMap;
    }
   
    public void displayTable() {
        Map<Character, String> codeMap = generateCodes();
        int x = 100;
        int y = 5;
        Controls.printxy(x, y, "Huffman Encoding Table:");
        y++;
        Controls.printxy(x, y, "+--------+-----------+------+------+" );
        Controls.printxy(x, y+1, "| Char   | Frequency | Code | Bits |");
        Controls.printxy(x, y+2, "+--------+-----------+------+------+" );
        y += 3;
        for (char c : codeMap.keySet()) {
            String code = codeMap.get(c);
            int freq = root.freq;
            Node node = root;
            for (int i = 0; i < code.length(); i++) {
                char bit = code.charAt(i);
                if (bit == '0') {
                    node = node.left;
                } else {
                    node = node.right;
                }
                freq = node.freq;
            }
            int bits = code.length();
            Controls.printxy(x, y, String.format("| %-6s | %-9d | %-4s | %-4d |", c, freq, code, bits));
            y++;
        }
        Controls.printxy(x, y, "+--------+-----------+------+------+" );
    }
}

/* 
class Node
{
    char value;
    int frequency;
    Node leftChild, rightChild;

    Node(char value, int frequency)
    {
        this.value = value;
        this.frequency = frequency;
    }

    Node (char value, int frequency, Node leftChild, Node rightChild)
    {
        this.value = value;
        this.frequency = frequency;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    boolean isLeaf()
    {
        return (leftChild == null) && (rightChild == null);
    }
} 

public class BinaryTree {
    public Node root;

    
    private class Node implements Comparable<Node>
    {
        char data;
        int freq;
        Node left, right;

        Node(char data, int freq)
        {
            this.data = data;
            this.freq = freq;
            left = right = null;
        }
        
        public booknea isLeaf()
        {
            return (left == null) && (right == null);
        }

        @override 
        public int compareTo(Node other)
        {
            return this.freq - other.freq;
        }
    }
    
    
    public BinaryTree(String input)
    {
        HashMap<Character, Integer> freqMap = new HashMap<>();
        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            if(freqMap.containsKey(c))
            {
                freqMap.put(c, feqMap.get(c) + 1);
            } else {
                freqMap.put(c,1);
            }
        }
        root = buildTree(freqMap);
    }


    private Node buildTree (HashMap<Character, Integer> freqMap)
    {
        PriorityQueue<Node> pq = new PriorityQueue<>(freqMap.Size(),(a-b) -> a.freq - b.freq);
        for ( char c : freqMap.keySet())
        {
            pq.add(new Node(c, freqMap.get(c)));
        }
        while (pq.size() > 1)
        {
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node('/0', left.freq + right.freq);
            parent.left = left;
            parent.right = right;
            pq.add(parent);
        }
        return pq.poll();
    }

    public void DisplayTreeHuffmanEncoding(String input)
    {
        try {
            BinaryTree bt = new BinaryTree(input);
            System.out.println("Huffman encoding binary tree: ");
            displayTree(bt.root,"");
            System.out.println("/nHuffman Encoding Table:");
            displayTable(bt.root,"");
        } catch (Exception e) {
            System.out.println("An error occured: " + e.getMessage());
        }
    }

    private void displayTree(Node node,String indent)
    {
        if ( node == null)
        {
            return;
        }
        System.out.println(indent + node.data + ":" + node.freq);
        displayTree(node.left, indent + " ");
        displayTree(node.right, indent + " ");
    }

    private void displayTable(Node node, String code)
    {
        if (node == null)
        {
            return;
        }
        if (node.data != '\0')
        {
            System.out.println(node.data + " -> " + code);
        }
        displayTable(node.left, code + "0");
        displayTable(node.right, code + "1");
    }
*/
/* 
    private Node addRecursive(Node current, int value) {
        if (current == null) {
            return new Node(value,0);
        }
    
        if (value < current.intvalue) {
            current.left = addRecursive(current.left, value);
        } else if (value > current.intvalue) {
            current.right = addRecursive(current.right, value);
        } else {
            // value already exists
            return current;
        }
    
        return current;
    }

    public void add(int value) {
        root = addRecursive(root, value);
    }

    private boolean containsNodeRecursive(Node current, int value) {
        if (current == null) {
            return false;
        } 
        if (value == current.value) {
            return true;
        } 
        return value < current.value
          ? containsNodeRecursive(current.left, value)
          : containsNodeRecursive(current.right, value);
    }

    public boolean containsNode(int value) {
        return containsNodeRecursive(root, value);
    }

    private int findSmallestValue(Node root) {
        return root.left == null ? root.value : findSmallestValue(root.left);
    }

    private Node deleteRecursive(Node current, int value) {
        if (current == null) {
            return null;
        }
    
        if (value == current.value) 
        {
            if (current.left == null && current.right == null) {
                return null;
            }
            if (current.right == null) {
                return current.left;
            }
            
            if (current.left == null) {
                return current.right;
            }  

            int smallestValue = findSmallestValue(current.right);
            current.intvalue = smallestValue;
            current.right = deleteRecursive(current.right, smallestValue);
            return current;

        } 
        if (value < current.value) {
            current.left = deleteRecursive(current.left, value);
            return current;
        }
        current.right = deleteRecursive(current.right, value);
        return current;
    }

    public void delete(int value) {
        root = deleteRecursive(root, value);
    }

    public void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.left);
            System.out.print(" " + node.value);
            traverseInOrder(node.right);
        }
    }

    public void traversePreOrder(Node node) {
        if (node != null) {
            System.out.print(" " + node.value);
            traversePreOrder(node.left);
            traversePreOrder(node.right);
        }
    }
    
    public void traversePostOrder(Node node) {
        if (node != null) {
            traversePostOrder(node.left);
            traversePostOrder(node.right);
            System.out.print(" " + node.value);
        }
    }

    public void printnodes(Node node,int x, int y, int w, int h) {
        if (node != null) 
        {
            Controls.Box(x-2,y-1,6,3,91,Colors.RED,Colors.BLACK_BACKGROUND);
            Controls.SetForegroundBackgroundColor(Colors.YELLOW,Colors.RED_BACKGROUND);
            Controls.printxy(x,y,node.c + ":" + node.freq);            
            Controls.SetForegroundBackgroundColor(Colors.WHITE,Colors.BLACK_BACKGROUND);
            printnodes(node.left,x - (w/2),y + (h/2), w, h);
            printnodes(node.right,x + (w/2),y + (h/2), w, h);
        }
    }
 
    public void printnodesweightAndChar (Node node,int x, int y, int w, int h)
    {
        if (node != null)
        {
            Controls.Box(x-2,y-1,6,3,91,Colors.RED,Colors.BLACK_BACKGROUND);
            Controls.SetForegroundBackgroundColor(Colors.YELLOW,Colors.BLACK_BACKGROUND);
            Controls.printxy(x,y,node.weight);
            Controls.printxy(x,y+1,node.value);
            Controls.SetForegroundBackgroundColor(Colors.WHITE,Colors.BLACK_BACKGROUND);
            printnodesweightAndChar(node.left,x-(w/2), y+(h/2), w, h);
            printnodesweightAndChar(node.right,x+(w/2), y+(h/2), w, h);
        }
    }

    public void DisplayInfo()
    {
        //CreateNodes();
        Controls.cls();
       // add(100);
        //add(50);
        //add(160);
        //add(45);
        //add(180);
        //add(78);
        printnodes(root,40,5,20,15);
        //traverseInOrder(root);
        //delete(100);
        //traverseInOrder(root);
        //add(1);
        //traverseInOrder(root);
    }

    public void PrintQueueTree(PriorityQueue<Node> Q2)
    {
        int x,y;
        x = 10;
        y = 1;
        Controls.cls();
        while (!Q2.isEmpty())
        {
            Node p = Q2.poll();
            printnodesweightAndChar(p, x, y, 20, 15);
            x=x+25;
        }
    }
    */
    /* 
    public void CreateNodes()
    {
        PriorityQueue<Node> Q = new PriorityQueue<Node>();
        
        Q.add(new Node('A',43));
        Q.add(new Node('Z',2));
        Q.add(new Node('X',69));
        Q.add(new Node('T',420));
        Q.add(new Node('R',365));
        Q.add(new Node('R',23));
        Q.add(new Node('R',13));

        while(Q.size() != 1)
        {
            Node p = Q.poll();
            Node p2 = Q.poll();
            Node newNode = new Node(0,p.weight+p2.weight);
            newNode.left = p;
            newNode.right = p2;
            Q.add(newNode);
            PrintQueueTree(Q);
        }
        /* 
        while (!Q.isEmpty())
        {
            Node x = Q.poll();
            System.out.println(x.value + " : " + String.valueOf(x.weight));
        }
        */
    //}

        /*
        public BinaryTree(String inputString)
        {
            Map<Character, Integer> frequencyMap = new HashMap<>();
            for (char c : inputString.toCharArray())
            {
                frequemcyMap.put(c,frequencyMap.getOrDefault(c,0) + 1);
            }

            PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(n -> n.frequency));

            for (char c : frequencyMap.keySet())
            {
                minHeap.offer(new Node(c, frequencyMap.get(c)));
            }

            while (minHeap.size() > 1)
            {
                Node left = minHeap.poll();
                Node right = minHeap.poll();
                minHeap.offer(new Node('\0',left.frequency + right.frequency, left, right));
            }

            root = minHeap.poll();
        }

        private void generateEncodingTable(Node node, String code, Map<Character, String> encodingTable)
        {
            if(node.isLeaf())
            {
                encodingTable.put(node.value, code);
            } else {
                generateEncodingTable(node.leftChild, code + "0",encodingTable);
                generateEncodingTable(node.rightChild, code + "1", encodingTable);
            }
        }

        public void DisplayTreeHuffmanEncoding(String inputString)
        {
            Map<Character, String> encodingTable = new HashMap<>();
            generateEncodingTable(root, "", encodingTable);

            System.out.println("\nHuffman Encoding Table: ");
            for (char c : encodingTable.keySet())
            {
                System.out.println(c + " -> " + encodingTable.get(c));
            }

            System.out.println("/nHuffman Encoding Binary Tree: ");
            displayTree(root, "");
        }

        private void displayTree(Node node, String prefix)
        {
            if (node != null)
            {
                System.out.println(prefix + node.value + "(" + node.frequency + ")");
                displayTree(node.leftChild, prefix + "0");
                displayTree(node.rightChild, prefix + "1");
            }
        }
         
}
*/
   
