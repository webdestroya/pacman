// Help.java

package ucigame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

public class Help
{
    public static void main(String[] args) {

		System.out.println("Ucigame version " + Ucigame.version());
		System.out.println("Running version of Java is " +
							System.getProperty("java.runtime.version"));
		System.out.println("Current directory is " +
							System.getProperty("user.dir"));

		String sep = System.getProperty("path.separator");    // ; or :
		String slash = System.getProperty("file.separator");  //   / or \ 

		String[] ext = System.getProperty("java.ext.dirs").split(sep);
		String[] classpath = System.getProperty("java.class.path").split(sep);
		Vector<String> existingUcigames = new Vector<String>();

		for (String e : ext)
		{
			if (e.endsWith(".jar"))
				e = "";   // we'll add Ucigame.jar later
			else if (!e.endsWith(slash))
				e += slash;
			try {
				System.out.println("**Checking " + e + "Ucigame.jar");
				FileInputStream f = new FileInputStream(e + "Ucigame.jar");
				existingUcigames.add(e + "Ucigame.jar");  // only get here if file exists
				f.close();
			}
			catch (FileNotFoundException fnf) {}
			catch (IOException fnf) {}
		}

		//System.out.println("Current classpath includes:");
		for (String c : classpath)
		{
			if (c.endsWith(".jar"))
				c = "";   // we'll add Ucigame.jar later
			else if (!c.endsWith(slash))
				c += slash;
			try {
				System.out.println("*Checking " + c + "Ucigame.jar");
				FileInputStream f = new FileInputStream(c + "Ucigame.jar");
				existingUcigames.add(c + "Ucigame.jar");
				f.close();
			}
			catch (FileNotFoundException fnf) {}
			catch (IOException fnf) {}
		}

		int c = existingUcigames.size();
		if (c == 0)
			System.out.println("No existing Ucigame.jar files found on this computer.");
		else {
			System.out.println("" + c + " existing Ucigame.jar " +
							(c == 1 ? "file " : "files ") + "found on this computer:");
			for (String f : existingUcigames)
				System.out.println("  " + f);
		}

		System.out.println("");
		java.util.Properties p = System.getProperties();
		p.list(System.out);

		/*
        String [] properties = {
            "java.ext.dirs",
            "java.home",
            "path.separator",
            "file.separator",
            "java.library.path",
            "os.arch",
            "sun.boot.class.path"
        };
        for (int i = 0; i < properties.length; i++) {
            String key = properties[i];
            System.out.println(key + ": " + System.getProperty(key));
        }
        */
    }
}
