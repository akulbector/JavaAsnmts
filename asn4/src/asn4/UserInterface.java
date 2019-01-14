package asn4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserInterface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OrderedDictionary dict = new OrderedDictionary();
		//If valid cmd line input was given
		if(args.length!=0) {
			System.out.println("Taking input from file");
			String inputFile = args[0];
			
			try {
				
				BufferedReader in = new BufferedReader(new FileReader(inputFile));
			    String wordin = in.readLine();
			    String definition;
	
			    //puts the data in line by line, inferring the data type
				while (wordin != null) {
					wordin=wordin.toLowerCase();
				    try {
						definition = in.readLine();
						if(definition.endsWith(".jpg")||definition.endsWith(".gif")) {
							dict.put(new Record(new Pair(wordin,"image"),definition));
						} else if(definition.endsWith(".wav")||definition.endsWith(".mid")) {
							dict.put(new Record(new Pair(wordin,"audio"),definition));
						} else {
							dict.put(new Record(new Pair(wordin,"text"),definition));
						}
						wordin = in.readLine();
				    }
				    catch(DictionaryException e) {
						System.out.println(e.getMessage());
					}
				}
				
				//Starts taking commands
				StringReader keyboard = new StringReader();
				String line = keyboard.read("Enter next command: ");
				
				//Keeps going until finish
				while(!line.equals("finish")) {
					
					//Splits commands by space character
					String commands[] = line.split(" ");
					
					
					if (commands[0].equals("get")) {
						//get
						if (commands.length==2) { 
							//Creates data for all 3 types
							Pair kt = new Pair(commands[1],"text");
							Record rt = dict.get(kt);
							Pair ki = new Pair(commands[1],"image");
							Record ri = dict.get(ki);
							Pair ka = new Pair(commands[1],"audio");
							Record ra = dict.get(ka);
							
							//If none are there, print predecessor and sucessor
							if((rt==null)&&(ri==null)&&(ra==null)) {
								System.out.println("The word is not in the dictionary");
								System.out.print("Predecessor: ");
								if(dict.predecessor(ka)!=null) {
									String x = dict.predecessor(ka).getKey().getWord();
									System.out.println(x);
								} else {
									System.out.println("N/A");
								}
								System.out.print("Successor: ");
								if(dict.successor(kt)!=null) {
									String x = dict.successor(ka).getKey().getWord();
									System.out.println(x);
								} else {
									System.out.println("N/A");
								}
							} else {
								
								//If audio is found, play it
								if(ra!=null) {
									SoundPlayer player = new SoundPlayer();
									try {
										player.play(ra.getData());
									} catch (MultimediaException e) {
										//TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								
								//If text is found, print it
								if(rt!=null) {
									printRecord(rt);
								}
								
								//If picture is found, show it
								if(ri!=null) {
									PictureViewer viewer = new PictureViewer();
									try {
										viewer.show(ri.getData());
									} catch (MultimediaException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								
								
							}
						} else {//Bad input check
							System.out.println("Invalid Input");
							System.out.println("Hint: enter exactly 1 argument after 'get'");
						}
						
						
					}
					
					else if (commands[0].equals("delete")) {
						if (commands.length==3) {
							
							//Tries to remove key, otherwise shows message
							Pair k = new Pair(commands[1],commands[2]);
							try {
								dict.remove(k);
							} catch (DictionaryException e) {
								// TODO Auto-generated catch block
								System.out.println("No record in the ordered dictionary has key"+"("+commands[1]+", "+commands[2]+")");
							}
						} else {//Bad input check
							System.out.println("Invalid Input");
							System.out.println("Hint: enter exactly 2 arguments after 'delete': 'word' 'type'");
						}
					}
					
					else if(commands[0].equals("put")) {
						if (commands.length>3) {
							
							
							Pair k = new Pair(commands[1].toLowerCase(),commands[2]);
							String data = "";
							
							//Accounts for spaces in last argument and concatenates the rest of the string
							for(int i=3;i<commands.length;i++) {
								data = data + " " + commands[i];
							}
							
							//Tries to put the record in, otherwise, show a message
							Record r = new Record(k,data);
							try {
								dict.put(r);
							} catch (DictionaryException e) {
								// TODO Auto-generated catch block
								System.out.print("A record with the given key (" +commands[1]+", "+commands[2] );
								 System.out.println(") is already in the ordered dictionary.");
							}
						} else {//Bad input check
							System.out.println("Invalid Input");
							System.out.println("Hint: enter exactly 3 arguments after 'put': 'word' 'type' 'data'");
						}
					}
					else if(commands[0].equals("list")) {
						if (commands.length==2) {
							boolean printedMsg=false;
							//Prints message if no keys with prefix are there
							boolean noneWithPrefix = true;
							Pair k = new Pair(commands[1],"audio");
							
							//First tries to find a key with the prefix as the word
							if(dict.get(k)!=null) {
								System.out.print(dict.get(k).getKey().getWord());
								noneWithPrefix = false;
							}
							
							//Then continues to get the successor while it still starts with the prefix
							if(dict.successor(k)!=null) {
								Record i = dict.successor(k);
								while(i.getKey().getWord().startsWith(commands[1])) {
									System.out.print(", "+i.getKey().getWord());
									noneWithPrefix = false;
									if(dict.successor(i.getKey())==null) {
										break;
									} else {
										i = dict.successor(i.getKey());
									}
								}
								System.out.println("");
							} else {
								printedMsg=true;
								System.out.println("No words in the ordered dictionary start with prefix "+commands[1]);
							}
							//Prints message if no records with prefix are stored (only once)
							if(noneWithPrefix&&(!printedMsg)) {
								System.out.println("No words in the ordered dictionary start with prefix "+commands[1]);
							}
						} else {//Bad input check
							System.out.println("Invalid Input");
							System.out.println("Hint: enter exactly 1 argument after 'list': 'prefix' ");
						}
					}
					//Returns smallest
					else if(commands[0].equals("smallest")) {
						if(commands.length>1) {//Bad input check
							System.out.println("Invalid Input");
							System.out.println("Hint: enter exactly 0 arguments after 'smallest'");
						} else {
							System.out.println("Record with smallest key:");
							printRecord(dict.smallest());
						}
					}
					//REturns largest
					else if(commands[0].equals("largest")) {
						if(commands.length>1) {//Bad input check
							System.out.println("Invalid Input");
							System.out.println("Hint: enter exactly 0 arguments after 'largest'");
						} else {
							System.out.println("Record with largest key:");
							printRecord(dict.largest());
						}
					} else {//Bad input check
						System.out.println("Not a valid command.");
						System.out.println("Use 'get', 'delete', 'put', 'list', 'smallest', or 'largest'");
					}
					//Read next line
					line = keyboard.read("Enter next command: ");
				}
			}
			catch(IOException e) {//File open error
				System.out.println("Unable to open file: "+args[0]);
			}
		} else {//No valid cmd line arg
			System.out.println("No input file given in command line args");
		}
		
		
	}
	
	//Print record function
	private static void printRecord(Record r) {
		String word = r.getKey().getWord();
		String type = r.getKey().getType();
		String data = r.getData();
		System.out.println("("+word+", "+type+", "+data+")");
	}

}
