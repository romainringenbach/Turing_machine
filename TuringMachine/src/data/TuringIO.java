package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TuringIO {
	
	private BufferedReader bf;
	private static final String PATH = "../FoncTrans.txt";
	
	private void loadBuffer(){
		FileReader fr;
		try {
			fr = new FileReader(new File(PATH));
			bf = new BufferedReader(fr);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public ArrayList<Character> loadMachineAlpha(){
		this.loadBuffer();
		
		ArrayList<Character> ret = new ArrayList<Character>();
		String line = "";
		
		try {
			//Lis jusqu'à la balise <machine_alpha>
			while(!(line = bf.readLine()).equals("<machine_alpha>")  && line != null);
			//Lis jusqu'à la balise </machine_alpha> (Fin du bloc)
			while(!(line = bf.readLine()).equals("</machine_alpha>") && line != null){
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
		this.loadBuffer();
		
		ArrayList<Character> ret = new ArrayList<Character>();
		String line = "";
		
		try {
			//Lis jusqu'à la balise <tape_alpha>
			while(!(line = bf.readLine()).equals("<tape_alpha>")  && line != null);
			//Lis jusqu'à la balise </tape_alpha> (Fin du bloc)
			while(!(line = bf.readLine()).equals("</tape_alpha>") && line != null){
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
		this.loadBuffer();
		
		ArrayList<Transition> ret = new ArrayList<Transition>();
		String line = "";
		
		try {
			//Lis jusqu'à la balise <transitions>
			while(!(line = bf.readLine()).equals("<transitions>")  && line != null);
			//Lis jusqu'à la balise </transitions> (Fin du bloc)
			while(!(line = bf.readLine()).equals("</transitions>") && line != null){
				String[] splited = line.split(" ");
				if(splited.length == 5){
					Transition t = new Transition(splited[0],splited[1].charAt(0),splited[2].charAt(0),splited[3],splited[4]);
					ret.add(t);
				}
				else{
					throw new IllegalArgumentException("In this bloc, transitions must have 5 parameters, separeted by a space.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public String loadInitState(){
		this.loadBuffer();
		
		String ret = "";
		
		try {
			//Lis jusqu'à la balise <init_state>
			while(!(ret = bf.readLine()).equals("<init_state>")  && ret != null);
			//Ligne suivante de la balise <init_state>
			ret = bf.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public String loadAcceptState(){
		this.loadBuffer();
		
		String ret = null;
		
		try {
			//Lis jusqu'à la balise <accept_state>
			while(!(ret = bf.readLine()).equals("<accept_state>")  && ret != null);
			//Ligne suivante de la balise <accept_state>
			ret = bf.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public String loadRejectState(){
		this.loadBuffer();
		
		String ret = null;
		try {
			//Lis jusqu'à la balise <reject_state>
			while(!(ret = bf.readLine()).equals("<reject_state>")  && ret != null);
			//Ligne suivante de la balise <reject_state>
			ret = bf.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public ArrayList<String> loadStopStates(){
		this.loadBuffer();
		
		ArrayList<String> ret = new ArrayList<String>();
		String line = "";
		
		try {
			//Lis jusqu'à la balise <stop_states>
			while(!(line = bf.readLine()).equals("<stop_states>")  && line != null);
			//Lis jusqu'à la balise </stop_states> (Fin du bloc)
			while(!(line = bf.readLine()).equals("</stop_states>") && line != null){
				ret.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
}