

public class Node {
	
	//Simple Linked List Node implementation
	private Configuration pair;
	private Node next=null;
	
	public Node(Configuration pair) {
		this.pair = pair;
	}
	
	public Node getNext() {
		return next;
	}
	
	public void setNext(Node n) {
		next = n;
	}
	
	public Configuration getPair() {
		return this.pair;
	}
	
	public void setPair(Configuration pair) {
		this.pair = pair;
	}
}
