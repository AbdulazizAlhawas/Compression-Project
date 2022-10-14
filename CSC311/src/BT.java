//import java.io.Serializable;
import java.util.HashMap;
//import java.util.Stack;
/* regular implementation of binary trees */


public class BT {
    Node root, current;
    
    HashMap<Character, String> compressionMap = new HashMap<Character, String>();
   
    public BT() {
        root = current = null;
    }
    
    public BT(Node node) {
        root = current = node;
    }
 
    public boolean empty(){
        return root == null;
    }

    public boolean full() {
        return false;
    }
 
    public boolean find(String rel){
        switch (rel) {
           case "Root":   // Easy case
            current = root;
            return true;
           case "Parent":
            if(current == root)
            return false;
            current = current.parent;
            return true;
           case "Left":
            if(current.left == null)
                return false;
            current = current.left;
            return true;
           case "Right":
            if(current.right == null)
                return false;
            current = current.right;
            return true;
           default:
            return false;
        }
    }

    public int retrieve() {
        return current.frequency;
    }
  
    public void update(int val) {
        current.frequency = val;
    }
   
    public void deleteSub() {
        if(current == root){
            current = root = null;
        }
        else {
            Node p = current;
            find("Parent");
            if(current.left == p)
                current.left = null;
            else
                current.right = null;
        }
 
       
    }
 
    public boolean isLeaf(Node currentNode) {
        if(currentNode == null)
            return false;
        if(currentNode.left == null && currentNode.right == null)
            return true;
        return false;
    }
    
    public int getheight(Node root) {
        if (null == root)
            return 0;
        int hLeftSub = getheight(root.left);
        int hRightSub = getheight(root.right);
        return Math.max(hLeftSub, hRightSub) + 1;

    }
 
    public Node getRoot() {
        return root;
    }
    
    public Node getCurrent() {
        return current;
    }
	
	// traverses the tree and adds 0 or 1 depending on which route it takes. once it reaches a leaf the string and the name of the node is put in a hashmap. this creates a dictionary.
	private void buildMaps1 (Node node, String bits) {
		if(node != null) {
			if(node.right==null && node.left==null) {
				compressionMap.put(node.letter, bits);
			}
			if(node.left != null)
				buildMaps1(node.left,bits+'0');
			if(node.right != null)
				buildMaps1(node.right,bits+'1');
		}
			
	}
	
	public void buildMaps () {
		String bits ="";
		buildMaps1 (root,bits);
	}

	public HashMap<Character, String> getCompressionMap() {
		return compressionMap;
	}
	
	
 
}