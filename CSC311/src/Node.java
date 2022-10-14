
public class Node {
	public int frequency;
    public char letter;
    public Node left, right, parent;
 
    public Node(int frequency ,char letter) {
        this.frequency = frequency;
        this.letter=letter;
        parent = left = right = null;
    }
    
    public Node(int frequency) {
        this.frequency = frequency;
        parent = left = right = null;
    }
    
    public Node(Node node) {
        this.frequency = node.frequency;
        this.letter=node.letter;
        this.parent=node;
        left = right = null;
    }
}
