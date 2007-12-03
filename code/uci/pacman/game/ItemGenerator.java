package code.uci.pacman.game;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ItemGenerator {
	static int space;
	static FileWriter f;
	static String folder = "levels/";

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void execute() throws IOException {
		// pills
		makePillsOne();
		f.close();
	}

	private static void makePillsOne() throws IOException {
		String item = "pills";
		f = new FileWriter(new File(folder + item + "/1.txt"));
		space = 20;
		
		//unique ones
		h(42, 100, 520);
		h(20, 560, 562);
		h(131, 441, 342);
		
		
		v(20, 20, 150);
		v(573, 20, 150);
		
		h(42,20,227);
		h(334, 20, 227);
		
		h(334, 388, 250);
		h(20, 388, 250);
		
		v(130, 125, 250);
		v(460, 125, 250);
		
		h(20,500,125);
		h(450, 500, 125);
		
		h(40,160,65);
		h(490,160,65);
		
		h(530, 444, 58);
		h(20, 444, 58);
		
		h(198, 500, 78);
		h(330,500,78);
		h(198, 160, 78);
		h(330,160,78);
		
		v(20, 406, 22);
		v(131, 406, 22);
		v(260, 406, 22);
		v(332, 406, 22);
		v(460, 406, 22);
		v(573, 406, 22);
		
		v(60, 461, 22);
		v(128, 461, 22);
		v(200, 461, 22);
		v(390, 461, 22);
		v(460, 461, 22);
		v(530, 461, 22);
		
		v(22, 520, 22);
		v(260, 520, 22);
		v(332, 520, 22);
		v(573, 520, 22);
		
		v(130, 42, 42);
		v(260, 42, 42);
		v(332, 42, 42);
		v(462, 42, 42);
		
		v(198, 120, 22);
		v(390, 120, 22);
		
		
	}

//	private static void makeWalls() throws IOException {
//		String item = "walls";
//		f = new FileWriter(new File(folder + item + "/1.txt"));
//		space = 2;
//		//box
//		makeWall(0, 0, 600, 10);
//		makeWall(0, 0, 10, 540);
//		makeWall(590, 0, 10, 540);
//		makeWall(0, 540, 600, 10);
//		
//		//row 1
//		makeWall(40, 40, 80, 55);
//		makeWall(154, 40, 120, 60);
//		makeWall(303, 10, 18, 88);
//		makeWall(352, 40, 120, 60);
//		makeWall(504,40, 60,55);
//		
//		makeWall(43, 126, 80, 20);
//		makeWall(10, 173, 110, 214);
//
//	}
//
	private static void v(int xPos, int yPos, int height) throws IOException {
			for (int y = yPos; y < yPos + height; y += space) {
				f.write(xPos + "," + y);
				f.write("\r\n");
			}
	}
	private static void h(int xPos, int yPos, int width) throws IOException{
		for (int x = xPos; x < xPos + width; x += space) {
			f.write(x + "," + yPos);
			f.write("\r\n");
		}
	}
	

}
