

public class DictionaryException extends Exception {

	public DictionaryException() {
		// TODO Auto-generated constructor stub
		super("Invalid Dictionary Call: \nElement Already Put in Dictionary\n"
				+ "or\nElement to be removed is not in Dictionary");
	}

}
