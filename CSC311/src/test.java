import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class test {

	public static void main(String[] args) {
		Huffman huff=new Huffman();
		boolean run=true;
		Scanner sk=new Scanner(System.in);
		
		System.out.println("1: compress\n2: decompress");
		
		int option=sk.nextInt();
		
		if(option==1) {
		while(run) {
		try {
			System.out.println("enter file location to compress");
			String file=sk.next();
			long start = System.currentTimeMillis();
		huff.encode(file);
		long elapsedTime = System.currentTimeMillis() - start;
		System.out.println("time in ms: "+elapsedTime);
		run=false;
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}
		}
		
		if(option==2) {
		run=true;
		while(run) {
			try {
			System.out.println("enter file location to decompress");
			String file1=sk.next();
			long start = System.currentTimeMillis();
			huff.decode(file1);
			long elapsedTime = System.currentTimeMillis() - start;
			System.out.println("time in ms: "+elapsedTime);
			HashMap<Character, String>map=huff.getHuffTree().getCompressionMap();
			for(Map.Entry<Character, String> entry : map.entrySet()) {
				Character key= entry.getKey();
				String code= entry.getValue();
				System.out.println("'"+key+"': "+"'"+code+"'");
			}
			new TreeGUI(huff.getHuffTree());
			run=false;
			}
			catch(Exception e){
				System.out.println(e);
			}
			
		}
		sk.close();

	}
	}

}
