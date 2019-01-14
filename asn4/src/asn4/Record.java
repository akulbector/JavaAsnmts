package asn4;

public class Record {
	private Pair key;
	private String data;
	
	//Create a record with a word,type pair as the key
	public Record(Pair key, String data) {
		this.data = data;
		this.key = key;
	}
	
	//Returns the word, type pair
	public Pair getKey() {
		return this.key;
	}
	
	//Returns the data of the record
	public String getData() {
		return this.data;
	}
}
