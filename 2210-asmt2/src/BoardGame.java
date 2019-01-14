

public class BoardGame {
	
	//char array for game board
	char[][] gameBoard;
	
	//Constructor creates gameBoard given board size
	//Makes all initial values on the board empty, or 'g'
	public BoardGame(int board_size, int empty_positions, int max_levels) {
		
		gameBoard = new char[board_size][board_size];
		
		for(int i=0;i<board_size;i++) {
	
			for(int j=0;j<board_size;j++) {
				gameBoard[i][j]='g';
				
			}
		}
		
	}
	
	//Makes and returns a dictionary of a prime number size
	public HashDictionary makeDictionary() {
		return new HashDictionary(7877);
	}
	
	//Checks if the the current configuration of the gameboard
	//Is already stored in the dictionary
	public int isRepeatedConfig(HashDictionary dict) {
		String config = "";
		int result;
		
		//Converts the char array to a string that can be put in the dictionary
		for(int i=0; i<gameBoard.length; i++) {
		
			for(int j=0; j<gameBoard.length; j++) {
				config = config + String.valueOf(gameBoard[i][j]);
			}
		}
		
		//Uses getScore to give output
		/*
		 * If the string is in the dict, returns its associated score; otherwise
		 *it returns the value -1.
		 */
		result = dict.getScore(config);
		return result;
	}
	
	/*
	 * This method first represents
	 * the content of gameBoard as a string as described above; then it inserts this string and its
	 * score in dict
	 */
	public void putConfig(HashDictionary dict, int score) {
		
		//Converts char array to a string representation of the board
		String config = "";
		for(int i=0; i<gameBoard.length; i++) {
		
			for(int j=0; j<gameBoard.length; j++) {
				config = config + String.valueOf(gameBoard[i][j]);
			}
		}
		
		//Creates a new config pair
		Configuration pair = new Configuration(config, score);
		
		//Puts it into the dictionary, throws exception if it is already there
		try {
			dict.put(pair);
		} catch (DictionaryException e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * This method stores symbol in
	 * gameBoard[row][col]
	 */
	public void savePlay(int row, int col, char symbol) {
		gameBoard[row][col] = symbol;
	}
	
	/*
	 *  This method returns true if
	 *  gameBoard[row][col] is ’g’; otherwise it returns false
	 */
	public boolean positionIsEmpty(int row, int col) {
		
		if (gameBoard[row][col]=='g') {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	/*
	 * This method returns true if
	 * gameBoard[row][col] is ’o’; otherwise it returns false
	 */
	public boolean tileOfComputer(int row, int col) {
		
		if (gameBoard[row][col]=='o') {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	/*
	 * Returns true if gameBoard[row][col]
	 * is ’b’; otherwise it returns false
	 */
	public boolean tileOfHuman(int row, int col) {
		
		if (gameBoard[row][col]=='b') {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	/*
	 * Returns true if there are n adjacent tiles of type
	 * symbol in the same row, column, or diagonal of gameBoard, 
	 * where n is the size of the gameboard
	 */
	public boolean wins(char symbol) {
		
		boolean result = false;
		
		//Vertical check
		for (int i=0; i<gameBoard.length; i++) {
			boolean vertical = true;
			
			for (int j=0; j<gameBoard.length; j++) {
				if (gameBoard[i][j]!=symbol) {
					vertical=false;
				}
			}
			
			if(vertical==true) {
				result=true;
			}
		}
		
		//Horizontal check
		for (int i=0; i<gameBoard.length; i++) {
			boolean horizontal = true;
			
			for (int j=0; j<gameBoard.length; j++) {
				if (gameBoard[j][i]!=symbol) {
					horizontal=false;
				}
			}
			
			if(horizontal==true) {
				result=true;
			}
		}
		
		//Diagonal check 1
		boolean diagonal_1 = true;
		for (int i=0; i<gameBoard.length; i++) {

			if (gameBoard[i][i]!=symbol) {
				diagonal_1 = false;
			}
		}

		if(diagonal_1==true) {
			result = true;
		}
		
		//Diagonal check 2
		
		boolean diagonal_2 = true;
		
		for (int i=0; i<gameBoard.length; i++) {
			
			if (gameBoard[i][gameBoard.length-1-i]!=symbol) {
				diagonal_2 = false;
			}
		}
		
		if(diagonal_2==true) {
			result = true;
		}
		
		return result;
	}
	
	public boolean isDraw(char symbol, int empty_positions) {
		
		boolean result;
		//Checks if anyone has won, then it obviously isnt a draw
		if (this.wins('o')) {
			result = false;
			
		} else if(this.wins('b')) {
			result = false;
			
		} else if (empty_positions==0) {
			//If there are no empty positions, it is clearly over
			//And since nobody has one, it is a draw
			result = true;
			
		} else {
			
			//this variable is used to count the positions still left
			int positionsEmpty = 0;
			
			//Checks every possible move by seeing if
			//any of the empty spots can be moved into
			boolean moveIsStillPossible = false;
			
			for (int i=0; i<gameBoard.length; i++) {
				for (int j=0; j<gameBoard.length; j++) {
					
					if(gameBoard[i][j]=='g') {
						positionsEmpty++;
						
						if(i!=0) {
							if(j!=0) {
								if(gameBoard[i-1][j-1]==symbol) {
									moveIsStillPossible = true;
								}
							}
							if(gameBoard[i-1][j]==symbol) {
								moveIsStillPossible = true;
							}
							if(j!=gameBoard.length-1) {
								if(gameBoard[i-1][j+1]==symbol) {
									moveIsStillPossible = true;
								}
							}
						}
						
						if(j!=0) {
							if(gameBoard[i][j-1]==symbol) {
								moveIsStillPossible = true;
							}
						}
						if(j!=gameBoard.length-1) {
							if(gameBoard[i][j+1]==symbol) {
								moveIsStillPossible = true;
							}
						}
						
						if(i!=gameBoard.length-1) {
							if(j!=0) {
								if(gameBoard[i+1][j-1]==symbol) {
									moveIsStillPossible = true;
								}
							}
							if(gameBoard[i+1][j]==symbol) {
								moveIsStillPossible = true;
							}
							if(j!=gameBoard.length-1) {
								if(gameBoard[i+1][j+1]==symbol) {
									moveIsStillPossible = true;
								}
							}
						}
						
					}
				}
			}
			
			//If there are spaces left on the board to move
			if(positionsEmpty>empty_positions) {
				moveIsStillPossible=true;
			}
			
			result = !moveIsStillPossible;
		}
		return result;
	}
	
	public int evalBoard(char symbol, int empty_positions) {
		
		if(this.wins('o')) {
			return 3;
			
		} else if(this.wins('b')) {
			return 0;
			
		} else if(this.isDraw(symbol, empty_positions)){
			return 2;
			
		} else {
			return 1;
			
		}
	}
	
}
