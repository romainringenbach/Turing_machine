package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TuringIO {
	
	private BufferedReader br;
	private BufferedWriter bw;
	private static String LOAD_PATH;
	private static String SAVE_PATH;
	
	private static TuringIO instance;
	
	public static TuringIO getInstance(){
		if(instance == null){
			instance = new TuringIO();
		}
		return instance;
	}
	
	private TuringIO(){
		//LOAD_PATH = "../FoncTrans.txt";
		SAVE_PATH = "../Configurations.txt";
	}
	
	private void loadBufferReader(){
		FileReader fr;
		try {
			fr = new FileReader(new File(LOAD_PATH));
			br = new BufferedReader(fr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadBufferWriter(){
		FileWriter fw;
		try {
			fw = new FileWriter(new File(SAVE_PATH));
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Character> loadMachineAlpha(){
		this.loadBufferReader();
		
		ArrayList<Character> ret = new ArrayList<Character>();
		String line = "";
		
		try {
			//Lis jusqu'à la balise <machine_alpha>
			while(!(line = br.readLine()).equals("<machine_alpha>")  && line != null);
			//Lis jusqu'à la balise </machine_alpha> (Fin du bloc)
			while(!(line = br.readLine()).equals("</machine_alpha>") && line != null){
				if(line.length() == 1){
					ret.add(line.charAt(0));
				}
				else{
					throw new IllegalArgumentException("In this bloc, only single character are allow");
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public ArrayList<Character> loadTapeAlpha(){
		this.loadBufferReader();
		
		ArrayList<Character> ret = new ArrayList<Character>();
		String line = "";
		
		try {
			//Lis jusqu'à la balise <tape_alpha>
			while(!(line = br.readLine()).equals("<tape_alpha>")  && line != null);
			//Lis jusqu'à la balise </tape_alpha> (Fin du bloc)
			while(!(line = br.readLine()).equals("</tape_alpha>") && line != null){
				if(line.length() == 1){
					ret.add(line.charAt(0));
				}
				else{
					throw new IllegalArgumentException("In this bloc, only single character are allow");
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public ArrayList<Transition> loadTrans(){
		this.loadBufferReader();
		
		ArrayList<Transition> ret = new ArrayList<Transition>();
		String line = "";
		
		try {
			//Lis jusqu'à la balise <transitions>
			while(!(line = br.readLine()).equals("<transitions>")  && line != null);
			//Lis jusqu'à la balise </transitions> (Fin du bloc)
			while(!(line = br.readLine()).equals("</transitions>") && line != null){
				String[] splited = line.split(" ");
				if(splited.length == 5){
					Transition t = new Transition(splited[0],splited[1].charAt(0),splited[2].charAt(0),splited[3],splited[4]);
					ret.add(t);
				}
				else{
					throw new IllegalArgumentException("In this bloc, transitions must have 5 parameters, separeted by a space.");
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public String loadInitState(){
		this.loadBufferReader();
		
		String ret = "";
		
		try {
			//Lis jusqu'à la balise <init_state>
			while(!(ret = br.readLine()).equals("<init_state>")  && ret != null);
			//Ligne suivante de la balise <init_state>
			ret = br.readLine();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public String loadAcceptState(){
		this.loadBufferReader();
		
		String ret = null;
		
		try {
			//Lis jusqu'à la balise <accept_state>
			while(!(ret = br.readLine()).equals("<accept_state>")  && ret != null);
			//Ligne suivante de la balise <accept_state>
			ret = br.readLine();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public String loadRejectState(){
		this.loadBufferReader();
		
		String ret = null;
		try {
			//Lis jusqu'à la balise <reject_state>
			while(!(ret = br.readLine()).equals("<reject_state>")  && ret != null);
			//Ligne suivante de la balise <reject_state>
			ret = br.readLine();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public ArrayList<String> loadStopStates(){
		this.loadBufferReader();
		
		ArrayList<String> ret = new ArrayList<String>();
		String line = "";
		
		try {
			//Lis jusqu'à la balise <stop_states>
			while(!(line = br.readLine()).equals("<stop_states>")  && line != null);
			//Lis jusqu'à la balise </stop_states> (Fin du bloc)
			while(!(line = br.readLine()).equals("</stop_states>") && line != null){
				ret.add(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	
	public void saveConfigurations(ArrayList<String> conf){
		this.loadBufferWriter();
		
		try {
			bw.write("Configurations du programme FoncTrans.txt : \r\n\r\n");
			for(int i=0;i<conf.size();i++){
				bw.write(conf.get(i)+"\r\n");
			}
			bw.close();
		} catch (IOException e) {
				e.printStackTrace();
		}
	}

	public String getLOAD_PATH() {
		return LOAD_PATH;
	}

	public static void setLOAD_PATH(String path) {
		LOAD_PATH = path;
	}

	public String getSAVE_PATH() {
		return SAVE_PATH;
	}

	public static void setSAVE_PATH(String path) {
		SAVE_PATH = path+"/Configurations.txt";
	}
}