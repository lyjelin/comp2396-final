package task2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

/**
 * CountObject class is Simple Java program to count the number of newly created 
 * Java objects in the heap of the Java Virtual Machine (JVM) in a Java source file 
 * by scanning the source file
 * 
 * @author Lee Yoon Jeong
 *
 */
public class CountObjects {
	
	File file;
	
	/**
	 * 
	 * Main class
	 * 
	 * @param args
	 * @throws FileNotFoundException if file not found
	 * @throws IOException if there is problem in input file
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		/*if ( args.length < 1 ) {
			System.err.println("Usage: java CountObjects <s>");
				return;
		} */
		
		CountObjects cobj = new CountObjects();
		cobj.doCountObjects(args[0]);

	}

	/**
	 * Estimate the number of Java objects created on the heap
	 * 
	 * @param filename name of file to be checked
	 * @throws FileNotFoundExceptionif file not found
	 * @throws IOException if there is problem in input file
	 */
	public void doCountObjects(String filename) throws FileNotFoundException, IOException {
		
		int obj_count = 0;
		int autoBox_count = 0;
		int stringLiteral_count = 0;
		
		System.out.println("Esimtating number of objects creating in file " + filename);
		
		// estimating the number of newly created in the filename
		// store the count in obj_count
		file=new File(filename); 
		
		/*JFileChooser fileChooser = new JFileChooser();
		
		int returnVal = fileChooser.showOpenDialog(null);
		
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}*/
		
	    FileReader freader = new FileReader(file);  
	    BufferedReader breader = new BufferedReader(freader); 
	      
	    int[] countList = Object_Count(breader, obj_count, autoBox_count, stringLiteral_count);
	      
		  
		System.out.println("Estimated number of object in file " + filename + " is " + countList[0]);
		System.out.println("Estimated number of primitive data types in file " + filename + " is " + countList[1]);
		System.out.println("Estimated number of string literal objects in file " + filename + " is " + countList[2]);
	}
	
	/**
	 * Estimate the number of newly created objects (autoboxing on primitive data 
	 * types, string literal objects) in a Java source file
	 * 
	 * @param breader BufferedReader to read file line by line
	 * @param obj_count counts newly created objects
	 * @param autoBox_count counts counts primitive data types
	 * @param stringLiteral_count counts string literal objects
	 * @return 1D countList:
	 * 					countList[0] = obj_count;
	 * 					countList[1] = autoBox_count;
	 * 					countList[2] = stringLiteral_count;
	 * @throws FileNotFoundExceptionif file not found
	 * @throws IOException if there is problem in input file
	 */
	public int[] Object_Count (BufferedReader breader, int obj_count, int autoBox_count, int stringLiteral_count) throws FileNotFoundException, IOException {
		String[] wordsFromLine = null;  
		String line;
		
		String target = "new";
		String[] autotTargets = {"long", "boolean", "char", "float", "double", "int", "byte", "short"};
		String sLiterals = "String +([0-9a-zA-Z]+,* *)+ *= *\\\"( *[0-9a-zA-Z]*\\S*)*\\\";";
		
		Pattern pattern = Pattern.compile(sLiterals);
		while( (line=breader.readLine()) != null) {
			wordsFromLine = line.split(" ");
			Matcher m = pattern.matcher(line);
			if (m.find()) {
				stringLiteral_count++;
			}
			
			for (String word: wordsFromLine) {
				word = word.replaceAll("[\\n\\t ]", "");
				
				for (String pdt: autotTargets) {
					if (word.equals(pdt)) {
						autoBox_count++;
					}
					if (word.equals(target)) {
						obj_count++;
					}
				}
			}
		}
		
		int[] countList = new int[3];
		
		countList[0] = obj_count;
		countList[1] = autoBox_count;
		countList[2] = stringLiteral_count;
		
		return countList;
		
		  

		
	}


}