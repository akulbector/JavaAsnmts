public class Configuration {
	
	//string to be stored, represents gameboard as o's, b's, and g's
	private String config;
	//score related with its string
	private int score;
	
	//constructor with both string and score
	public Configuration(String config, int score) {
		
		this.config = config;
		this.score = score;
	}

	//returns string associated with the config pair
	public String getStringConfiguration() {
		return this.config;
	}
	
	//returns score associated with the config pair
	public int getScore() {
		return this.score;
	}
}
