import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class Huffman {
	
	HashMap<Character, Integer> freqMap = new HashMap<Character, Integer>();
	String sentence="";
	Heap huffHeap;
	BT huffTree;
	String bits="";
	String decoded="";
	String bits2;
	
	//calculates the frequency of every letter in a text file. O(n)
	private void calcFreq(File file) throws IOException {
		
        try{ InputStream in = new FileInputStream(file);
             Reader reader = new InputStreamReader(in);

             int c;
             Integer kk;
             //moves through file one character at a time.
             while ((c = reader.read()) != -1) {
            	 char cc=(char)c;
            	 sentence+=cc;
            	 //if the character is already in the hashmap, we increase its frequency by 1.
                 if(freqMap.get((Character)cc) != null) {
                	 kk=freqMap.get((Character)cc)+1;
                	 freqMap.put((Character)cc,kk);
                 }
                 //if the character is not in the hashmap, we put it in the hashmap with frequency set to 1.
                 else
                	 freqMap.put((Character)cc,1);
             }
             //insert a special symbol to know that it is the end of file.
             sentence+='⌧';
             freqMap.put((Character)'⌧',1);
             reader.close();
             in.close();
        }
        catch(Exception e) {
        	throw e;
        }
    }
	//uses frequency hashmap to fill the heap. O(n logn)
	private void fillHeap() {
		huffHeap= new Heap(freqMap.size());
		//move through entire hashmap and creates a node then inserts the node in the heap.
		for(Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
			Character key= entry.getKey();
			Integer freq= entry.getValue();
			Node node= new Node(freq.intValue(),key.charValue());
			huffHeap.insert(node);
			
		}
		
	}
	
	//build the huffman tree. O(n logn)
	private void buildHuff() {
		// first we fill the heap.
		fillHeap();
		//while the heap has more than one element we remove 2 nodes attach them to a new parent node and insert the parent to the heap.
		while(huffHeap.getSize()>1) {
			Node x=huffHeap.remove();
			Node y=huffHeap.remove();
			Node z=new Node(x.frequency+y.frequency);
			z.left=x;
			z.right=y;
			huffHeap.insert(z);
		}
		//creates huffman tree.
		huffTree=new BT(huffHeap.remove());
	}

	//method to get the binary encoding of a file. O(n logn)
	public void encode(String loc) throws FileNotFoundException, IOException {
		//first we calculate the frequency.
		File file= new File(loc);
		try{
			calcFreq(file);
			
		}
		catch(Exception e) {
			throw e;
		}
		//then we build the huffman tree and get a hashmap from that tree.
		buildHuff();
		huffTree.buildMaps();
		HashMap<Character, String> compMap=huffTree.getCompressionMap();
		
		//we move through every character from the file and replace it with a binary representation found from the hashmap.
	             for(int i=0;i<sentence.length();i++) {
	            	 char cc=sentence.charAt(i);
	                 if(compMap.get((Character)cc) != null) {
	                	 bits+=compMap.get((Character)cc);
	                 }
	                 
	             }
	             
		byte[] bytearray= new byte[(bits.length()/8)+1];
		
		int count=0;
		int pos=0;
		byte x=0;
		String bit="";
		for(int i=0; i<bits.length();i++) {
			
			if(count==8) {
				x=(byte)Integer.parseInt(bit,2);
				bytearray[pos++]=x;
				count=0;
				bit="";
			}
				bit=bit+bits.charAt(i);
				count++;
		}
		if(count>0) {
			while(bit.length()!=8)
				bit+='0';
		x=(byte)Integer.parseInt(bit,2);
		bytearray[pos++]=x;
		count=0;
		bit="";
		}
		//we save the new encoding in a text file.
		File compfile=new File("huffmanCode.txt");
		compfile.createNewFile();
		File huffmantree=new File("huffmanTree.dat");
		huffmantree.createNewFile();
		try {
			FileOutputStream fos = new FileOutputStream(compfile);
			fos.write(bytearray);
			fos.close();
			fos = new FileOutputStream(huffmantree);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(freqMap);
			fos.close();
		}
		catch(Exception e) {
			throw e;
		}
		System.out.println(bits);
		System.out.println("original file size is: "+sentence.length()*16+" bits");
		System.out.println("new file size is: "+bits.length()+" bits");
		bits2=bits;
		bits="";
		
		
	}
	
	//method to decode a file. O(n)
	@SuppressWarnings("unchecked")
	public boolean decode(String loc) throws FileNotFoundException, IOException {
		File huffmantree=new File("huffmanTree.dat");
		try {
		FileInputStream fis = new FileInputStream(huffmantree);
		ObjectInputStream ois = new ObjectInputStream(fis);
		freqMap=(HashMap<Character,Integer>)ois.readObject();
		fis.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		buildHuff();
		huffTree.find("Root");
		
		try{ 
		File file= new File(loc);
		RandomAccessFile f = new RandomAccessFile(file, "r");
		byte[] bytearray = new byte[(int)f.length()];
		f.readFully(bytearray);
		String s1="";
        for(int i=0;i<bytearray.length;i++) {
        	s1 += String.format("%8s", Integer.toBinaryString(bytearray[i] & 0xFF)).replace(' ', '0');
        }
        f.close();
        System.out.println(s1);
        System.out.println(s1.equals(bits2));
        //we move through a file of binary 1 and 0. if we read a 0 we move left in the huffman tree, if we read 1 we move right.
        //if we find a leaf we save the character from the node and move to the root of the tree.
        for(int i=0;i<s1.length();i++) {
       	 char cc=s1.charAt(i);
			if(cc=='0') {
				huffTree.find("Left");
				Node curr=huffTree.getCurrent();
				if(huffTree.isLeaf(curr)) {
					if(curr.letter == '⌧')
						break;
					decoded+=curr.letter;
					huffTree.find("Root");
				}
			}
			else
			if(cc=='1') {
				huffTree.find("Right");
				Node curr=huffTree.getCurrent();
				if(huffTree.isLeaf(curr)) {
					if(curr.letter == '⌧')
						break;
					decoded+=curr.letter;
					huffTree.find("Root");
				}
			}
			//if we read a value other than 0 or 1 we return false.
			else {
				return false;
			}
		}
		System.out.println(decoded);
		decoded="";
		
	}
		catch(Exception e) {
			throw e;
		}
		return true;
	}

	public BT getHuffTree() {
		return huffTree;
	}
	
	

}
