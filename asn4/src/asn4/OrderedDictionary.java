package asn4;

public class OrderedDictionary implements OrderedDictionaryADT {

	//root has no parent and has no record stored in it (atleast initially)
	Node root = new Node(null, null);
	
	//Constructor takes no parameters and does nothing
	public OrderedDictionary() {
		
	}
	
	//Gets a node based on the given key and returns the record stored in the node
	public Record get(Pair k) {
		// TODO Auto-generated method stub
		if (nodeGet(k)==null) {
			return null;
		} else {
			return nodeGet(k).getRecord();
		}
	}
	
	//Gets a node based on the key given
	private Node nodeGet(Pair k) {
		// Starts looking at root
		Node i=root;
		
		//Keeps looking until it reaches a leaf node
		while(i.getRecord()!=null) {
			//If found, return the node
			if(i.getRecord().getKey().compareTo(k)==0) {
				return i;
			//If the given key is greater than current node's key, go to its right child
			} else if(i.getRecord().getKey().compareTo(k)<0) {
				i = i.getRight();
			//If the given key is less than current node's key, go to its left child
			} else if(i.getRecord().getKey().compareTo(k)>0) {
				i = i.getLeft();
			}
		}

		return null;
	}

	//Puts a given record into dictionary
	public void put(Record r) throws DictionaryException {
		//Starts at root node
		//boolean to keep track of insertion
		boolean inserted = false;
		Node i=root;

		while(!inserted) {
			//If the node is empty, insert the record
			if(i.getRecord()==null) {
				i.setRecord(r);
				inserted=true;
			} else {
				//Go left if the given record's key is less than current node's key
				//Go right if the given record's key is greater than current node's key
				 int x = i.getRecord().getKey().compareTo(r.getKey());
				 
				 if(x>0) {
					 i=i.getLeft();
				 } else if(x<0) {
					 i=i.getRight();
					 //If key is already in dictionary, throw exception
				 } else if(x==0) {
					 throw new DictionaryException("Key already in Dictionary");
				 }
			 }
		}
		
		
	}

	
	//Removes a given key from the dictionary
	public void remove(Pair k) throws DictionaryException {
		// TODO Auto-generated method stub
		//Uses get to figure out node to remove
		Node nodeToRM = nodeGet(k);

		//If get did not find any node with the given key, throw exception
		if (nodeToRM==null) throw new DictionaryException("Key to be removed is not in dictionary");
	
		//If the node to remove has leaf/empty children, simply set the record to null
		if((nodeToRM.getLeft().getRecord()==null) && (nodeToRM.getRight().getRecord()==null)) {
			nodeToRM.setRecord(null);
		} else {
			//If the node to rm has only a right child
			if (nodeToRM.getLeft().getRecord()==null) {
				//If the node to rm is the root, set the root to the nodeToRM's right child
				if (nodeToRM.equals(root)) {
					root = nodeToRM.getRight();
					
				//Make the nodeToRM's parent branch to nodeToRM's children directly, skipping over nodeToRM
				} else if (nodeToRM.getParent().getLeft().equals(nodeToRM)){
					nodeToRM.getParent().setLeft(nodeToRM.getRight());
				} else {
					nodeToRM.getParent().setRight(nodeToRM.getRight());
				}
				
			//If the node to rm has only a left child
			} else if (nodeToRM.getRight().getRecord()==null) {
				//If the node to rm is the root, set the root to the nodeToRM's left child
				if (nodeToRM.equals(root)) {
					root = nodeToRM.getLeft();
					
				//Make the nodeToRM's parent branch to nodeToRM's children directly, skipping over nodeToRM
				} else if (nodeToRM.getParent().getLeft().equals(nodeToRM)){
					nodeToRM.getParent().setLeft(nodeToRM.getLeft());
				} else {
					nodeToRM.getParent().setRight(nodeToRM.getLeft());
				}
				
			//If the node to rm has 2 children
			} else {
				
				//Finds the node's successor and replace it with that
				Node replacement = this.nodeSuccessor(k);
				if (nodeToRM.equals(root)) {
					root = replacement;
				} else if (nodeToRM.getParent().getLeft().equals(nodeToRM)){
					nodeToRM.getParent().setLeft(replacement);
				} else {
					nodeToRM.getParent().setRight(replacement);
				}
			}
			
		}
		
	}

	//Returns a successor node's record given a successor method that returns a node
	@Override
	public Record successor(Pair k) {
		// TODO Auto-generated method stub
		if (nodeSuccessor(k)==null) {
			return null;
		} else {
			return (nodeSuccessor(k).getRecord());
		}
	}
	
	//Returns the node that precedes the given key
	public Node nodePredecessor(Pair k) {
		
		//boolean for pretend node
		boolean rmLater = false;
		//Tries to get the node from the dictionary
		Node n = this.nodeGet(k);
		
		//If the node is not already in the dictionary, it creates a pretend node and inserts it
		//It uses a boolean to remember to remove it later
		if(n==null) {
			rmLater = true;
			try {
				this.put(new Record(k,"hello"));
			} catch (DictionaryException e) {
				e.printStackTrace();
			}
		}
		
		//Gets the node again, this time it will definitely be there (pretend node)
		n = this.nodeGet(k);
		
		//If it has children to its left, return the largest key in its left subtree
		if(n.getLeft().getRecord()!=null) {
			return largestNode(n.getLeft());
		} else{
			Node result = null;
			boolean running = true;
			//Unless it reaches the root, keep going up the tree until it finds
			//a parent that is to the left of the key
			while(!n.equals(root)&&running) {
				if(n.getParent().getRight().equals(n)) {
					//Parent is to the left of the key
					//Return value
					result = n.getParent();
					running=false;
				} else {
					//Keep going
					n = n.getParent();
				}
			}
			//Removes pretend node if necessary
			if(rmLater) {
				try {
					this.remove(k);
				} catch (DictionaryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//Returns null if nothing was found
			return result;
		}
	}
	
	//Finds the smallest node by continously going as far left as possiible
	private Node largestNode(Node n) {
		// TODO Auto-generated method stub
		Node prev=n;
		while(n.getRecord()!=null) {
			prev = n;
			n = n.getRight();
		}
		return prev;
	}

	
	public Node nodeSuccessor(Pair k) {
		//boolean for pretend node
		boolean rmLater = false;
		//Tries to get the node from the dictionary
		Node n = this.nodeGet(k);
		
		//If the node is not already in the dictionary, it creates a pretend node and inserts it
		//It uses a boolean to remember to remove it later
		if(n==null) {
			rmLater = true;
			try {
				this.put(new Record(k,"hello"));
			} catch (DictionaryException e) {
				e.printStackTrace();
			}
		}
		//Gets the node again, this time it will definitely be there (pretend node)
		n = this.nodeGet(k);
		
		//If it has children to its right, return the smallest key in its right subtree
		if(n.getRight().getRecord()!=null) {
			return smallestNode(n.getRight());
		} else{
			
			Node result = null;
			boolean running = true;
			//Unless it reaches the root, keep going up the tree until it finds
			//a parent that is to the right of the key
			while(!n.equals(root)&&running) {
				
				if(n.getParent().getLeft().equals(n)) {
					//Parent is to the right of the key
					//Return value
					result = n.getParent();
					running=false;
				} else {
					//Keep going up the tree
					n = n.getParent();
				}
			}
			
			//Remove pretend node if necessary
			if(rmLater) {
				try {
					this.remove(k);
				} catch (DictionaryException e) {
					e.printStackTrace();
				}
			}
			
			//REturns null if nothing was found
			return result;
		}
		
	}
	
	
	
	

	//Returns a predecessor node's record given a predecessor method that returns a node
	public Record predecessor(Pair k) {
		// TODO Auto-generated method stub
		if (nodePredecessor(k)==null) {
			return null;
		} else {
			return (nodePredecessor(k).getRecord());
		}
	}
	
	@Override
	//Returns the smallest node's record
	public Record smallest() {
		// TODO Auto-generated method stub
		return smallestNode(root).getRecord();
	}
	
	//Finds the smallest node by continously going as far left as possiible
	private Node smallestNode(Node n) {
		// TODO Auto-generated method stub
		Node prev=n;
		while(n.getRecord()!=null) {
			prev = n;
			n = n.getLeft();
		}
		return prev;
	}

	@Override
	//Returns the largest node's record
	public Record largest() {
		// TODO Auto-generated method stub
		return largestNode(root).getRecord();
	}
	
}
