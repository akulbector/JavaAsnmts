

public class HashDictionary implements DictionaryADT {
	
	//This is the array of Node (ie linked lists) that will store the objects
	private Node hashTable[];
	
	//Initializes the array of the specified size with all Nodes 
	public HashDictionary(int size) {
		
		hashTable = new Node[size];
		Node initNode = null;
		
		//Makes sure all Nodes are initialized to null
		for(int i=0; i<size;i++) {
			hashTable[i]=initNode;
		}
		
	}
	
	//Hash function takes a string and returns a long integer
	private long hash(String str) {
		
		//Get bytes from the string
		byte[] byteArray = str.getBytes();
		long result = 0;
		
		//Loop runs for every 4 bytes, or every 32 bits
		//Creates a simple polynomial rolling hash function
		//Each 32 bit section is multiplied by 2^(which section it is)
		//All values are added up
		for (int i=0; i<byteArray.length-3; i+=4) {
			result = (long) (result + ((byteArray[i]*byteArray[i+1]*byteArray[i+2]*byteArray[i+3])*(Math.pow(2, i/4))));
		}
		
		//Does another hash on the same byte array
		//This time, cut into 1 byte pieces, or every 8 bits
		//But only runs for the first 3 bytes of the byteArray
		//Multiplies the byte value by which section it is (ie. 1st, 2nd, or 3rd)
		long result2 = 0;
		
		for (int i=0; i<byteArray.length; i++) {
			if(i<4) {
				result2 += byteArray[i]*i;
			}
		}
		
		//Both results from both hash functions are added
		return result+result2;
	}
	
	@Override
	public int put(Configuration data) throws DictionaryException {
		
		//value that is returned
		int result;
		
		//gets the hashed long from the string
		long hashCode = this.hash(data.getStringConfiguration());
		//uses modulur division to find the location to put it in the hashtable
		int location = (int) (hashCode% hashTable.length);
		
		//Gets the node at the location
		Node chain = hashTable[location];
		
		//If the node is empty, create a new node and place in that location
		if(chain==null) {
			hashTable[location] = new Node(data);
			result = 0;
		} else {
			
			//If there is a node already there
			Node prev = null;
			result = 1;
			
			//Keep going until program reaches the end of the chain
			while(chain!=null) {
				
				//Checks if the object has already been stored and throws exception
				if(chain.getPair().getStringConfiguration().equals(data.getStringConfiguration())) {
					throw new DictionaryException();
				}
				
				//Increment/Goes to the next node
				prev = chain;
				chain = chain.getNext();
			}
			
			//Change Node "chain" from null to a new Node containing the data
			chain = new Node(data);
			//Link the node so that it can be accessed later
			prev.setNext(chain);
			
		}
		
		return result;
	}

	@Override
	public void remove(String config) throws DictionaryException {
		
		//Hashes the string and gets its location
		long hashCode = this.hash(config);
		int location = (int) (hashCode%hashTable.length);
		
		//Creates two nodes
		//chain is assigned the value of the node at the calculated location
		/*prev is null since it will only be a non null Node if/when the
		 * chain node is incremented
		 */
		Node prev = null;
		Node chain = hashTable[location];
		
		//If there is no Node at the location
		//You cannot remove something that does not exist
		if(chain==null) {
			throw new DictionaryException();
		}
		
		//Keeps incrementing chain until the matching string is found
		while(!chain.getPair().getStringConfiguration().equals(config)) {
			
			//Increment
			prev = chain;
			chain = chain.getNext();
			
			//If there is no Node at the last linked node
			//You cannot remove something that does not exist
			if(chain==null) {
				throw new DictionaryException();
			}
		}
		
		//Removes the object from the linked list
		//Or sets the node to null if there is only one node
		if(prev!=null){
			prev.setNext(chain.getNext());
		} else {
			chain = null;
		}
	}

	@Override
	public int getScore(String config) {
		
		//Hashes the string and gets its location
		long hashCode = this.hash(config);
		int location = (int) (hashCode%hashTable.length);
		
		//Creates two nodes
		//chain is assigned the value of the node at the calculated location
		/*prev is null since it will only be a non null Node if/when the
		 * chain node is incremented
		 */
		Node prev = null;
		Node chain = hashTable[location];
		
		//If there is no Node at the location
		//There is no score to return
		if(chain==null) {
			return -1;
		}
		
		//Keeps incrementing chain until the matching string is found
		while(!chain.getPair().getStringConfiguration().equals(config)) {
			
			//Increment
			prev = chain;
			chain = chain.getNext();
			
			//If there is no Node at the last linked node
			//There is no score to return
			if(chain==null) {
				return -1;
			}
		}
		
		//Now that the matching string is found, the corresponding score is returned
		return chain.getPair().getScore();
	}

}
