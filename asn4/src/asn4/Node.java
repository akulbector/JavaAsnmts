package asn4;

public class Node {
	private Node parent;
	private Record record;
	private Node right=null;
	private Node left=null;
	
	//Creates a binary search tree node
	//If no record is in the node, it is a leaf node
	//Leaf nodes do not have any right or left children
	public Node(Record record, Node parent) {
		this.record = record;
		this.parent = parent;
		if(record!=null) {
			this.right = new Node(null, this);
			this.left = new Node(null, this);
		}
	}
	
	//Change the left child
	public void setLeft(Node left) {
		this.left = left;
		left.setParent(this);
	}
	
	//Change the right child
	public void setRight(Node right) {
		this.right = right;
		right.setParent(this);
	}
	
	//Change the parent
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	//Add a record
	//Creates children nodes also
	public void setRecord(Record r) {
		this.record = r;
		this.right = new Node(null, this);
		this.left = new Node(null, this);
	}
	
	//Return right child
	public Node getRight() {
		return this.right;
	}
	
	//Return left child
	public Node getLeft() {
		return this.left;
	}
	
	//Return Parent
	public Node getParent() {
		return this.parent;
	}
	
	//Return record
	public Record getRecord() {
		return this.record;
	}
	
	
}
