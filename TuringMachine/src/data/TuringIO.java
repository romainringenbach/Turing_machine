package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TuringIO {
	
	private BufferedReader bf;
	private static final String PATH = "../FoncTrans.txt";
	
	
	public TuringIO(){
		try{
			FileReader fr = new FileReader(new File(PATH));
			bf = new BufferedReader(fr);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public ArrayList<Character> loadMachineAlpha(){
		ArrayList<Character> ret = new ArrayList<Character>();
		String line = "";
		
		try {
			//Lis jusqu'à la balise <machine_alpha>
			while(!(line = bf.readLine()).equals("<machine_alpha>")  && line != null){
				
			}
			//Lis jusqu'à la balise </machine_alpha> (Fin du bloc)
			while(!(line = bf.readLine()).equals("</machine_alpha>") && line != null){
				System.out.println(line);
				if(line.length() == 1){
					ret.add(line.charAt(0));
				}
				else{
					throw new IllegalArgumentException("In this bloc, only single character are allow");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public ArrayList<Character> loadTapeAlpha(){
		ArrayList<Character> ret = new ArrayList<Character>();
		String line = "";
		
		try {
			//Lis jusqu'à la balise <tape_alpha>
			while(!(line = bf.readLine()).equals("<tape_alpha>")  && line != null){
			}
			//Lis jusqu'à la balise </tape_alpha> (Fin du bloc)
			while(!(line = bf.readLine()).equals("</tape_alpha>") && line != null){
				System.out.println(line);
				if(line.length() == 1){
					ret.add(line.charAt(0));
				}
				else{
					throw new IllegalArgumentException("In this bloc, only single character are allow");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public ArrayList<Transition> loadTrans(){
		ArrayList<Transition> ret = new ArrayList<Transition>();
		
		return ret;
	}
	
	public String loadInitState(){
		String ret = null;
		
		return ret;
	}
	
	public String loadAcceptState(){
		String ret = null;
		
		return ret;
	}
	
	public String loadRejectState(){
		String ret = null;
		
		return ret;
	}
	
	public ArrayList<Transition> loadStopStates(){
		ArrayList<Transition> ret = new ArrayList<Transition>();
		
		return ret;
	}
}