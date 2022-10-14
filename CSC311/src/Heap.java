public class Heap  {
	   
    private Node[] heap;
    private int size;
 
    public Heap(int maxSize){
        
        this.size = 0;
        heap = new Node[maxSize + 1];
        heap[0]=new Node(Integer.MIN_VALUE);
       
       
    }
 
    private void swap(int first, int second)
    {
        Node tmp;
        tmp = heap[first];
        heap[first] = heap[second];
        heap[second] = tmp;
    }

    private void minHeapify(int i) {
    	
    	if(i==0)
    		return;
        int left = 2*i;
        int right =(2*i)+1;
        int smallest = i;

        // find the smallest key between current node and its children.
        if (left < size && heap[left].frequency < heap[i].frequency) {
            smallest = left;
        } 

        if (right < size && heap[right].frequency < heap[smallest].frequency) {
            smallest = right;
        }

        // if the smallest key is not the current key then swap and call minHeapify again.
        if (smallest != i) {

            swap(i, smallest);
            minHeapify(smallest);
        }
    }
 
    public void insert(Node node){
        heap[++size] = node;
        int current = size;
        //while new node is smaller than parent node, swap.
        while (heap[current].frequency < heap[current/2].frequency){
            swap(current,current/2);
            current = current/2;
        }  
    }
     public int getSize() {
            return size;
        }
   
     public Node remove()
        {
            Node popped = heap[1];
            heap[1] = heap[size--];
            minHeapify(1);
            return popped;
        }

}