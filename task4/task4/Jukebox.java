package task4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Jukebox class manipulate a list of songs 
 * @author Lee Yoon Jeong
 *
 */
public class Jukebox {
	
	ArrayList<Song> songList = new ArrayList<Song>();
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Jukebox().go();

	}
	
	/**
	 * 
	 */
	public void go() {
		getSongs();
		
		//original
		printSongs();
		
		// Subtask1. Sort the ArrayList<Song> wrt title of the Song
		// Collections.sort(songList);
		Collections.sort(songList, new Comparator<Song>() {
			
			@Override
			public int compare(Song s1, Song s2) {
				
				return s1.getTitle().compareTo(s2.getTitle());
			}
			
		});
		
		
		printSongs();
		
		// Subtask 2. Also sort the ArrayList<Song> wrt artist of the song
		
		Collections.sort(songList, new Comparator<Song>() {
			/**
			 * 
			 */
			@Override
			public int compare(Song s1, Song s2) {
				
				return s1.getArtist().compareTo(s2.getArtist());
			}
			
		});

		printSongs();
	}
	
	
	void printSongs() {
		System.out.println("List of songs");
		System.out.println("=============");
		Iterator<Song> iter = songList.iterator();
		while ( iter.hasNext() ) {
			iter.next().printSong();
		}
		
		System.out.println("\n\n");
	}
	
	void getSongs() {
		try {
			File f = new File("C:\\SongList.txt");
			BufferedReader reader=new BufferedReader(new FileReader(f));
			String line = null;
			
			while ((line = reader.readLine()) != null ) 
				addSong(line);
			
		} catch (Exception e) {

		}
	}
	
	void addSong(String lineToParse) {
		String[] toks = lineToParse.split("/");
		Song aSong = new Song(toks[0],toks[1],toks[2]);
		songList.add(aSong);
	}

}
