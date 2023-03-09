/**
 * Title: QValueStringArrayStore.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package ai.learning.q;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;

import utils.TwoTuple;

// TODO: Auto-generated Javadoc
/**
 * The Class QValueStringArrayStore.
 */
public class QValueStringArrayStore extends QValueStore{

	
	/*   EXAMPLE
	 * 			ACTION
	 *		1 2 3 4 5 6 7 8 9 
	 * s 1| 1 4 1 2 3 5 0 0 0 
	 * t 2| 0 0 0 0 4 0 0 1 0
	 * a 3| 4 4 1 0 0 4 6 7 8
	 * t 4| 1 1 3 4 0 0 3 3 3
	 * e 5| 1 2 4 5 2 8 9 0 7
	 * 
	 */
	
	/** The filename. */ 
	String filename;
	
	/** The problem name. */
	String problemName;
	
	/** The size. */
	int size;
	/**
	 * The holder.
	 */
	StoreList[][] holder;
	
	/**
	 * Instantiates a new q store holder().
	 *
	 * @param actionSize how many actions could be used 
	 * @param stateSize - guess as to how many states will be possibly available
	 * @param fileName - the filename to try to load from and to save to.
	 * @param problemName - saved to file to identify
	 */
	public QValueStringArrayStore(int stateSize,int actionSize, String fileName, 
			String problemName) {
		
		this.filename = fileName;
		this.problemName = problemName;
		
		//try to read filename
		if (!readFile()) {
			holder = new StoreList[stateSize][actionSize];
		}
	}

	/**
	 * The Class StoreList.
	 */
	class StoreList extends TwoTuple<Float,String>{

		/**
		 * Store list.
		 *
		 * @param a the a
		 * @param b the b
		 */
		public StoreList(Float a, String b) {
			super(a, b);
		}

	}
	

	@Override
	public boolean storeQValue(State state, String action, float value) {
		int number = state.getStateNUM();
		
		//if the state number is larger than the current holders length bubble up size 
		if (number >= holder.length) {
			bubbleUp(number + 1, holder[0].length);;
		}
		
		int index = findAction(holder[number],action);
		
		//if action already exists update value
		if (index >= 0) {	
			if (compareFloats(0.0f,holder[number][index].a))size++;
			holder[number][index].a = value;
			return true;
		}
		//if no action exists add new action to list 
		int emptyIndex = findNextEmpty(holder[number]);
		
		//array full - size up array 
		if (emptyIndex == -1) {
			//test to see if I need to size up 
			System.out.println("State:" + number + " is too full for more actions");
			return false;
		}
		
		//else add new action and value 
		holder[number][emptyIndex] = new StoreList(value,action);
		size++;
		
		return true;
	}
	
	/**
	 * Bubble up.
	 *
	 * @param stateSize the state size
	 * @param actionSize the action size
	 */
	public void bubbleUp(int stateSize,int actionSize) {
		//create larger array
		StoreList[][] temp = new StoreList[stateSize][actionSize];
		for (int i = 0;i < holder.length;i++) {
			for (int j = 0;j < holder[0].length;j++) {
				temp[i][j] = holder[i][j];
			}
		}
		holder = temp;
	}
	
	
	/**
	 * Find action.
	 *
	 * @param list the list
	 * @param action the action
	 * @return int
	 */
	private static int findAction(StoreList[] list, String action) {
		//go through action list 
		for (int i = 0; i < list.length;i++) {
			//if both are the same TYPE of action
			if (list[i] == null)continue;
			if (list[i].b.equalsIgnoreCase(action)) {
				//change value
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Find next empty.
	 *
	 * @param list the list
	 * @return int
	 */
	public static int findNextEmpty(StoreList[] list) {
		for (int i = 0; i < list.length;i++) {
			if (list[i] == null) return i;
		}
		return -1;
	}
	
	/**
	 * Size.
	 *
	 * @return int
	 */
	@Override
	public int size() {
		return size;
	}
	

	@Override
	public float getQValue(State state, String action) {

		//if no action yet return 0;
		
		if (action == null)return 0;
		int number = state.getStateNUM();
		if (number >= holder.length) {
			System.out.println("Holder to small");
			return 0;
		}
		//get the action list 
		int index = findAction(holder[number],action);
		//if action already exists update value
		if (index >= 0) {	
			return holder[number][index].a ;
		}

		//if no action exists yet return 0
		return 0;
	}
	

	@Override
	public String getBestAction(State state) {
		int number = state.getStateNUM();
		if (number >= holder.length) {
			bubbleUp(number + 1,holder[0].length);
			System.out.println("Holder(" + number + ") to small");
		}
		String action = null;
		float value = Float.NEGATIVE_INFINITY;
		for (int i = 0; i < holder[number].length;i++) {
			//if the current selection is null move to next 
			if (holder[number][i] == null)continue;
			
			//if the current actions value is greater than the saved value change action
			if (holder[number][i].a > value) {
				action = holder[number][i].b;
				value = holder[number][i].a;
			}
			
		}
		return action;
	}
	

	@Override
	public boolean readFile() {
		try {
			//check if file is a action list text file
			Scanner file = new Scanner(new File("src/resources/learning/" + filename));
			problemName = file.nextLine().substring(13);
			//System.out.println(text.substring(13));
			int rows = Integer.parseInt(file.nextLine().substring(5));
			//System.out.println(Integer.parseInt(file.nextLine().substring(5)));
			int columns = Integer.parseInt(file.nextLine().substring(8));
			//System.out.println(Integer.parseInt(file.nextLine().substring(8)));
			size = Integer.parseInt(file.nextLine().substring(5));
			//System.out.println(Integer.parseInt(file.nextLine().substring(5)));
			holder = new StoreList[rows][columns];
			
			for (int i = 0;i < holder.length;i++) {
				for (int j = 0;j < holder[0].length;j++) {
						//myWriter.write(holder[i][j] + " ");
						String text = file.next();
						//System.out.println("TEXT:" + text);
						if (text.equals("null") || text.equals("END")) continue;
						//create the action// maybe do not create a complete new action - this equals a lot of objects for only a couple of actions
						String[] action = text.split("[<>,]");
						holder[i][j] = new StoreList(Float.parseFloat(action[1]),action[2]);
								
					}
				}
			 file.close();
			return true;
		}
		catch(Exception e){//print out to see where error occured
			System.out.println("Error in setActions method:"  );
			e.printStackTrace();
		}
		return false;
	}
	


	@Override
	public boolean saveFile() {
		 try {	 
			 BufferedWriter myWriter = new BufferedWriter(
					 new FileWriter("src/resources/learning/" + filename));
			
			 myWriter.write("ProblemClass " + problemName);
			 myWriter.newLine();
			 myWriter.write("Rows " + holder.length);
			 myWriter.newLine();
			 myWriter.write("Columns " + holder[0].length);
			 myWriter.newLine();
			 myWriter.write("Size " + size);
			 myWriter.newLine();
			 for (int i = 0;i < holder.length;i++) {
					for (int j = 0;j < holder[0].length;j++) {
						myWriter.write(holder[i][j] + " ");
					}
					myWriter.newLine();
				}
			 myWriter.write("END");
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		      return true;
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		 return false;
	}
	
	/**
	 * Finalize. Make sure clip is closed properly
	 */
	@Override
	public void finalize() {
		saveFile();
	}
	
	
	/**
	 * The main method. This is used for testing 
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		

		/*QValueStore store = new QValueStore(10,4,"FindLearning.txt");
		
		Ant ant = new Ant(new Vector2(200,400),40f,40f,0.4f,ID.Player,
				new ThreeTuple<String,Integer,Integer>("images/computer1.png",46,30));
		Food food = new Food(new Vector2(300,300),10,10);
		
		State newState = new SearchingForObjectState(ant.getPosition(),0,
				food.getKinematic().getPosition().subtractTwoVectors(ant.getPosition()).getLength());
		
		store.storeQValue(newState, new MoveLeft(ant), 0);
		store.storeQValue(newState, new MoveUp(ant), 1);
		store.storeQValue(newState, new MoveDown(ant), 2);
		store.storeQValue(newState, new MoveRight(ant), 3);

		
		
		for (int i = 0;i < store.holder.length;i++) {
			for (int j = 0;j < store.holder[0].length;j++) {
				System.out.print("[" + store.holder[i][j] + "]");
			}
			System.out.println();
		}
		
		store.storeQValue(newState, new MoveRight(ant), 18);
		store.storeQValue(newState, new MoveUp(ant), 3);
		store.storeQValue(newState, new GrowObject(ant,5,5), 3);
		
		for (int i = 0;i < store.holder.length;i++) {
			for (int j = 0;j < store.holder[0].length;j++) {
				System.out.print("[" + store.holder[i][j] + "]");
			}
			System.out.println();
		}
		
		System.out.println("Size" + store.size());
		System.out.println(store.getQValue(newState, new MoveRight(ant)));
		System.out.println(store.getBestAction(newState));
		
		//store.saveFile();
			*/
	}


	/**
	 * Compare floats.
	 *
	 * @param f1 the f 1
	 * @param f2 the f 2
	 * @return true, if successful
	 */
	public static boolean compareFloats(float f1, float f2) {
		float epsilon = 0.000001f;
		return Math.abs(f1 - f2) < epsilon;
	}
	


}

