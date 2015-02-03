




public abstract class TuringIO {

	private String path = "../../configuration.txt"

	public void load(){

		File f = null;
		FileImageInputStream in = null;

		try {
			f = new File(path);
		
		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		try {
			in = new FileImageInputStream(f);
		
		} catch (Exception e) {

			System.out.println(e.getMessage());

		}	

		try {

			imageCourante = ImageIO.read(in);

	
		} catch (Exception e) {

			System.out.println(e.getMessage());

		}



	}



	private void sauver(String path){

		File f = null;
		FileImageOutputStream out = null;

		try {
			f = new File(path);
		
		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		try {
			out = new FileImageOutputStream(f);
		
		} catch (Exception e) {

			System.out.println(e.getMessage());

		}		


		try {

			if( !ImageIO.write(imageCourante,formatOrigine,out) ){
				System.out.println("attention, problème, bad format : "+formatOrigine);
			}

		} catch (Exception e) {

			System.out.println(e.getMessage());
			System.out.println("attention, problème, bad format : "+formatOrigine);

		}


	}

}