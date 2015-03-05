package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Read the configuration file
 * Write the result file
 * 
 * TuringIO is a singleton.
 */
public class TuringIO {
	
	private BufferedReader br;
	private BufferedWriter bw;
	/**
	 * The path to access the configuration file
	 */
	private static String LOAD_PATH;
	/**
	 * The path to save the result file
	 */
	private static String SAVE_PATH;
	
	/**
	 * The instance of the class
	 */
	private static TuringIO instance;
	
	/**
	 * @return Return the instance of the class
	 */
	public static TuringIO getInstance(){
		if(instance == null){
			instance = new TuringIO();
		}
		return instance;
	}
	
	/**
	 * Init the save path, by default, to the current directory
	 */
	private TuringIO(){
		SAVE_PATH = "./TapeConfiguration.txt";
	}

	/**
	 * Read the file containing all the data of the program
	 * @param path The path to the data file
	 * @return Return the string containing the file
	 */
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
	
	/**
	 * Load the buffer to write the result file
	 */
	private void loadBufferWriter(){
		FileWriter fw;
		try {
			fw = new FileWriter(new File(SAVE_PATH));
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write the result file, containing all the configurations of the program (result)
	 * @param conf The list of configurations to write in the file
	 */
	public void saveConfigurations(ArrayList<String> conf){
		this.loadBufferWriter();
		
		try {
			//Write french date
			TimeZone tz2 = TimeZone.getTimeZone("GMT+1" );
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.setTimeZone(tz2); 
			
			bw.write("Le "+sdf.format(new Date())+"\r\n"+"Configurations du programme FoncTrans.txt :\r\n\r\n");
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
		SAVE_PATH = path+"/TuringResult.txt";
	}
}