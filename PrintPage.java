import javax.swing.*;
import java.awt.*;

import java.sql.Date;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


//CLASS FOR DIAPLAYING FILES 
public class PrintPage extends JFrame {

	private File file1 = null;

	//CONSTRUCTS PAGE WINDOW AND ADDS CONTENT
	public PrintPage(String op, String filePath) {

		//set up window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 600);
		setBackground(Color.WHITE);
		setTitle("Print Page: " + op);

		file1 = new File(filePath);

		//determine operation and execute
		if(!file1.exists()) {
			//error, file doesn't exist 
		}
		else if(op.equals("Listing")) 
			addContent(printListing(false));
		else if(op.equals("Numbered Listing")) 
			addContent(printListing(true));
		else {
			//error, not valid operation
		}

		//display window
		setVisible(true);
       	}


	//ADDS CONTENT TO PAGE 
	public void addContent(String fileString) {

		//create page heading panel
		String info = "File Name: " + file1.getName() + "<br>";
		info += "Last Modified: " + (new Date(file1.lastModified())).toString();
		JLabel heading = new JLabel("<html>" + info + "<hr size=2>" + "</html>");

		//create content for the file
 		String modString = fileString.replace("\n","<br>");
		modString = "<html>" + modString + "</html>";
		JLabel content = new JLabel(modString);

		//create panel to add content to
 		JPanel file_content = new JPanel();
		file_content.setBackground(Color.WHITE);
		file_content.add(content);

		//add panel to window
		add(heading, BorderLayout.NORTH);	
		add(file_content, BorderLayout.WEST);	
    	}


	//PRINTS ONLY CONTENT OF FILE
        public String printListing(boolean numbered) {

		//read file into array list and catch exceptions
		ArrayList<String> l = null;
		try {
		 	l = readFile();
		} 
		catch(IOException ioe) {}

		//turn list into on long string
		String fileContent = "";
		if(!l.isEmpty()) {
			Iterator<String> it = l.iterator();
			while(it.hasNext()) {
				//add html ordered list tags if needed
				if(numbered)
					fileContent += "<li>" + it.next() + "</li>";
				else
					fileContent += it.next() + "<br>";
			}	
		}

		//add html ordered list tags if needed
		if(numbered)
			return "<ol type='1'>" + fileContent + "</ol>";
		else	
			return fileContent;
 	}


	//READS FILE INTO AN ARRAY BY LINE 
	public ArrayList<String> readFile() throws IOException {

		//create input stream for reading file
		FileInputStream fin = new FileInputStream(file1);
		BufferedReader read = new BufferedReader(new InputStreamReader(fin));
		
		//read file into array list by line
		ArrayList<String> list = new ArrayList<String>();
		String line = null;
		while((line = read.readLine()) != null) {
			list.add(line);
		}
		return list;
	}
 
	
	//DRIVER FOR CLASS TESTING
	public static void main (String args[]) {

		//supported operations so far "Listing", "Numbered Listing"
		if(args.length == 2)
			new PrintPage(args[0], args[1]);
	}
}
