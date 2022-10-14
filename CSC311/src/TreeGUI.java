import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
 
/* we use this class from https://github.com/EslaMx7/AI-Tasks-JADE-Tests/blob/master/src/trees/tasks/treeGUI.java 
 to show Huffman tree. we edited some things and added lines to make it work with our code.  */

public class TreeGUI extends JFrame {
 
    private JPanel contentPane;
    public BT tree;
    public DrawTree drawer;

    public TreeGUI(BT tree) {
        setBounds(100, 100, 500, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawer = new DrawTree(tree);
        drawer.revalidate();
        contentPane.add(drawer);
        setContentPane(contentPane);
        this.tree = tree;
        setVisible(true);
    }
 
}
 
class DrawTree extends JPanel{
   
    public BT tree;
   
    public DrawTree(BT tree){
        this.tree = tree;
        
        
    }
   
    @Override
    protected void paintComponent(Graphics g) {
   
        g.setFont(new Font("Tahoma", Font.BOLD, 20));
 
         
			DrawTree(g, 0, getWidth()-50, 0, getHeight() / tree.getheight(tree.root), tree.root);
		
    }
    public void DrawTree(Graphics g, int StartWidth, int EndWidth, int StartHeight, int Level, Node node)  {
    	String data;
    	if(node.left==null && node.right==null)
        data = String.valueOf(node.frequency+" ("+node.letter+")");
    	else
    		data = String.valueOf(node.frequency);
        g.setFont(new Font("Tahoma", Font.BOLD, 18));
        
        FontMetrics fm = g.getFontMetrics();
        int dataWidth = fm.stringWidth(data);
        g.drawString(data, (StartWidth + EndWidth) / 2 - dataWidth / 2, StartHeight + Level / 2);
        
        if(node.left!=null) {
            g.drawLine((StartWidth + EndWidth) / 2 - dataWidth / 2,  StartHeight + Level / 2, (StartWidth + (StartWidth + EndWidth) / 2) / 2 - dataWidth / 2, StartHeight + Level + Level / 2);
       }
        
        if(node.right!=null) {
            g.drawLine((StartWidth + EndWidth) / 2 - dataWidth / 2,StartHeight + Level / 2,((StartWidth + EndWidth) / 2 + EndWidth) / 2 - dataWidth / 2,StartHeight + Level + Level / 2);
        }
 
        if (node.left != null)            
            DrawTree(g, StartWidth, (StartWidth + EndWidth) / 2, StartHeight + Level, Level, node.left);
       
        if (node.right != null)
            DrawTree(g, (StartWidth + EndWidth) / 2, EndWidth, StartHeight + Level, Level, node.right);
    }
   
   
}