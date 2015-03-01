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
		LOAD_PATH = "../Configuration.txt";
		SAVE_PATH = "../TapeConfiguration.txt";
	}

	public String getConfiguration(String path){

		String ret = null;

		try {

			if (path == null){
				path = TuringIO.LOAD_PATH;
			}

			FileReader fr = new FileReader(new File(path));
			br = new BufferedReader(fr);

			String line = br.readLine();
			ret = line ;
			line = br.readLine();
	 
			while (line != null)
			{
			       
			    ret = ret + "\n" + line;
			    line = br.readLine();
			       
			}
			 
			br.close();

		}
		catch (Exception e){

			System.out.println(e.getMessage());
			e.printStackTrace();
		}	

		return ret;


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

	public static String getLOAD_PATH() {
		return LOAD_PATH;
	}

	public static void setLOAD_PATH(String path) {
		LOAD_PATH = path;
	}

	public static String getSAVE_PATH() {
		return SAVE_PATH;
	}

	public static void setSAVE_PATH(String path) {
		SAVE_PATH = path+"/TapeConfiguration.txt";
	}
}
