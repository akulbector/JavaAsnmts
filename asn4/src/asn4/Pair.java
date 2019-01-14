package asn4;

public class Pair {
	private String word;
	private String type;
	
	public Pair(String word, String type) {
		//Convert to lower case
		this.word = word.toLowerCase();
		this.type = type;
	}
	
	//Return word string
	public String getWord() {
		return word;
	}
	
	//Return type string
	public String getType() {
		return type;
	}
	
	//Compare one key to another first using the word, but if words are the same, using the type
	public int compareTo(Pair pair) {
		if(this.word.compareTo(pair.getWord())!=0) {
			return this.word.compareTo(pair.getWord());
		} else {
			return this.type.compareTo(pair.getType());
		}
	}
	
	
	
}
