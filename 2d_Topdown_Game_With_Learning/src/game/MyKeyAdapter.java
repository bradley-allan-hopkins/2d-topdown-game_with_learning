/**
 * Title: MyKeyAdapter.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Jan. 31, 2022
 */
package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import state.GameStateManager;

/**
 * The Class MyKeyAdapter. This class takes in key commands.
 */
public class MyKeyAdapter extends KeyAdapter{

	private static List<Integer> list = new ArrayList<Integer>();
	
	/** The handler. */
	private GameStateManager gsm;
	

	
	/**
	 * My key adapter.
	 * @param gsm - the GameStateManager currently in use 
	 */
	public MyKeyAdapter(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		//pass information to GameStateManager
		gsm.keyPressed(e);
		//go through list and check if key already pressed 
		for (Integer i : list) {
			//if key already pressed ignore 
			if (e.getKeyCode() == i)return;
		}
		//if no duplicate found add key to list 
		list.add(e.getKeyCode());
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		//pass information to GameStateManager
		gsm.keyReleased(e);
		//go through list and remove released key from list 
		for (int i = 0;i < list.size();i++) {
			if (e.getKeyCode() == list.get(i))
				//remove key from list 
				list.remove(i);
		}
	}
	
	


	/**
	 * Public method used to get
	 * @return the list
	 */
	public static List<Integer> getList() {
		return list;
	}

	/**
	 * Public method used to set
	 * @param list the list to set
	 */
	public static void setList(List<Integer> list) {
		MyKeyAdapter.list = list;
	}
	
	/** 
	 * public method that checks if key is pushed. If key is pushed remove from list and 
	 * return true. 
	 * @param code - the key code to check 
	 * @return boolean - true if key code found 
	 */
	public static boolean isKey(int code) {
		//loop through all the keys currently pressed 
		for (int i = 0; i < list.size();i++) {
			if (code == list.get(i)) {
				list.remove(i);
				return true;
			}
		}
		//no key exists 
		return false;
	}
}