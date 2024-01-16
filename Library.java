//Name: Anas Sajid
//Student Number: 501167312
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	//Instance fields
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	
  	private ArrayList<Podcast> 	podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	String errorMsg = "";
	//Error message getter
	public String getErrorMessage()
	{
		return errorMsg;
	}
	//Constructor for library class
	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); 
		playlists   = new ArrayList<Playlist>();
	  	podcasts		= new ArrayList<Podcast>(); 
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	//Check to see what datatype is being downloaded, check if it is already downloaded and if not, download it to the appropriate arraylist.
	//Set appropriate error message in case of already downloaded content or invalid content
	public boolean download(AudioContent content)
	{
		if ((content.getType()).equals("SONG")){
			if (songs.size()==0){
				Song content1 = (Song) content;
				songs.add(content1);
				return true;
			}
			for (int i=0;i<songs.size();i++){
				if (songs.get(i).equals(content)){
					errorMsg="Song already downloaded";
					return false;
				}
			}
			Song content1 = (Song) content;
			songs.add(content1);
			return true;
		}
		else if (content.getType().equals("AUDIOBOOK")){
			if (audiobooks.size()==0){
				AudioBook content1 = (AudioBook) content;
				audiobooks.add(content1);
				return true;
			}
			for (int i=0;i<audiobooks.size();i++){
				if (audiobooks.get(i).equals(content)){
					errorMsg="Audiobook already downloaded";
					return false;
				}
			}
			AudioBook content1 = (AudioBook) content;
			audiobooks.add(content1);
			return true;
		}
		else if (content.getType().equals("PODCAST")){
			if (podcasts.size()==0){
				Podcast content1 = (Podcast) content;
				podcasts.add(content1);
				return true;
			}
			else {
					errorMsg="Podcast already downloaded";
					return false;
			}
		}
		System.out.println(content.getType());
		errorMsg="Content already downloaded";
		return false;
	}
	
	// Print Information (printInfo()) about all songs in the array list
	//Iterate through all songs and display information
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	//Iterate through all audiobooks and display information
	public void listAllAudioBooks()
	{
		for (int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();	
		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
  //Iterate through all podcasts and display information
	public void listAllPodcasts()
	{
		for (int i = 0; i < podcasts.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			podcasts.get(i).printInfo();
			System.out.println();	
		}
	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	//Iterate through all playlists and display playlist titles
	public void listAllPlaylists()
	{
		for (int i = 0; i < playlists.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			System.out.print(playlists.get(i).getTitle());
			System.out.println();	
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arraylist is complete, print the artists names
		ArrayList<String> aName = new ArrayList<String>();
		boolean add;
		for (int i=0;i<songs.size();i++){
			add=true;
			for (int j=0;j<aName.size();j++){
				if (aName.get(j).equals(songs.get(i).getArtist())){
					add=false;
				}
			}
			if (add==true){
				aName.add(songs.get(i).getArtist());
			}
		}

		for (int i = 0; i < aName.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			System.out.println(aName.get(i));
		}
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	//If the song index is invalid, return an appropriate error message
	public boolean deleteSong(int index)
	{
		if (index<1||index>songs.size()){
			errorMsg="Song not found";
			return false;
		}
		Song removing = songs.get(index-1);
		songs.remove(index-1);
		//Iterate through each playlist and check if the removed song is in there, if it is then remove it
		for (int i=0;i<playlists.size();i++){
			if (playlists.get(i).getContent().contains(removing)){
				playlists.get(i).deleteContent(playlists.get(i).getContent().indexOf(removing)+1);
				return true;
			}
		}
		return false;
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		Collections.sort(songs, new SongYearComparator());
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song song, Song song1){
			return song.getYear()-song1.getYear();
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort() 
	 Collections.sort(songs, new SongLengthComparator());
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song song, Song song1){
			return song.getLength()-song1.getLength();
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		Collections.sort(songs);
	}

	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public boolean playSong(int index)
	{
		if (index < 1 || index > songs.size())
		{
			errorMsg = "Song not found";
			return false;
		}
		songs.get(index-1).play();
		return true;
	}
	
	// Play podcast from list (specify season and episode)
	// Bonus
	//Validate all 3 inputs and display appropriate error messages if they are invalid
	//Play the specific episode of the podcast
	public boolean playPodcast(int index, int season, int episode)
	{
		if (index < 1 || index > podcasts.size())
		{
			errorMsg = "Podcast not found";
			return false;
		}
		if (season<1||season>podcasts.get(index-1).getSeasons().size()){
			errorMsg="Season not found";
			return false;
		}
		if (episode<1||episode>podcasts.get(index-1).getSeasons().get(season-1).getFiles().size()){
			errorMsg="Episode not found";
			return false;
		}
		podcasts.get(index-1).play(season,episode);
		return true;
	}
	
	// Print the episode titles of a specified season
	// Bonus 
	//Validate inputs, return appropriate error messages if invalid
	//Loop through the episodeTitles of a season and display each title
	public boolean printPodcastEpisodes(int index, int season)
	{	
		if (index<1||index>podcasts.size()){
			errorMsg="Podcast not found";
			return false;
		}
		if (season>podcasts.get(index-1).getSeasons().size()||season<1){
			errorMsg="Season not found";
			return false;
		}
		for (int i = 0; i < podcasts.get(index-1).getSeasons().get(season-1).getTitles().size(); i++)
		{
			int indexp = i + 1;
			System.out.print("Episode " + indexp + ". ");
			System.out.println(podcasts.get(index-1).getSeasons().get(season-1).getTitles().get(i));
			System.out.println();
		}
		return true;
	}
	
	// Play a chapter of an audio book from list of audiobooks
	//Validate inputs, return appropriate error messages if invalid
	//Find the book in audiobooks arraylist and play the chapter
	public boolean playAudioBook(int index, int chapter)
	{
		if (index>audiobooks.size()||index<1){
			errorMsg="Audiobook not found";
			return false;
		}
		if (chapter>audiobooks.get(index-1).getChapters().size()||chapter<1){
			errorMsg="Chapter not found";
			return false;
		}
		audiobooks.get(index-1).selectChapter(chapter);
		audiobooks.get(index-1).play();
		return true;
		
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	//Validate inputs, return appropriate error messages if invalid
	public boolean printAudioBookTOC(int index)
	{
		if (index<1||index>audiobooks.size()){
			errorMsg="Audiobook not found";
			return false;
		}
		audiobooks.get(index-1).printTOC();
		return true;
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public boolean makePlaylist(String title)
	{
		for (int i=0;i<playlists.size();i++){
			if(playlists.get(i).getTitle().equals(title)){
				errorMsg="Playlist "+title+" already exists";
				return false;
			}
		}
		playlists.add(new Playlist(title));
		return true;
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	//Validate input, return appropriate error message if invalid
	//Loop through the playlist, check the data type of each piece of content, call the appropriate method to display the information
	public boolean printPlaylist(String title)
	{
		for (int i=0;i<playlists.size();i++){
			if (playlists.get(i).getTitle().equals(title)){
				for (int j=0;j<playlists.get(i).getContent().size();j++){
					int index = j+1;
					if(playlists.get(i).getContent().get(j).getType().equals("SONG")){
						Song tempSong = (Song) playlists.get(i).getContent().get(j);
						System.out.print("" + index + ". ");
						tempSong.printInfo();
						System.out.println();	
					}
					else if (playlists.get(i).getContent().get(j).getType().equals("AUDIOBOOK")){
						AudioBook tempBook = (AudioBook) playlists.get(i).getContent().get(j);
						System.out.print("" + index + ". ");
						tempBook.printInfo();
						System.out.println();
					}
					else if (playlists.get(i).getContent().get(j).getType().equals("PODCAST")){
						Podcast tempPod = (Podcast) playlists.get(i).getContent().get(j);
						System.out.print("" + index + ". ");
						tempPod.printInfo();
						System.out.println();
					}
				}
				return true;
			}
		}
		errorMsg="Playlist not found";
		return false;
	}
	
	// Play all content in a playlist
	//Validate input, return appropriate error message if invalid
	//Loop through the playlist, check the data type of each piece of content, call the appropriate method to play each content
	public boolean playPlaylist(String playlistTitle)
	{
		for (int i=0;i<playlists.size();i++){
			if (playlists.get(i).getTitle().equals(playlistTitle)){
				for (int j=0;j<playlists.get(i).getContent().size();j++){
					if (playlists.get(i).getContent().get(j).getType().equals("SONG")){
						Song tempSong = (Song) playlists.get(i).getContent().get(j);
						tempSong.play();
						System.out.println();
					}
					else if (playlists.get(i).getContent().get(j).getType().equals("AUDIOBOOK")){
						AudioBook tempBook = (AudioBook) playlists.get(i).getContent().get(j);
						tempBook.play();
						System.out.println();
					}
					else if (playlists.get(i).getContent().get(j).getType().equals("PODCAST")){
						Podcast tempPod = (Podcast) playlists.get(i).getContent().get(j);
						tempPod.play();
						System.out.println();
					}
				}
				return true;
			}
		}
		errorMsg="Playlist not found";
		return false;
	}
	
	// Play a specific song/audiobook in a playlist
	//Validate inputs, return appropriate error message if invalid
	//Loop through the playlist, check the data type of each piece of content, call the appropriate method to play the content
	//Also allow to play podcast but just play the whole podcast for simplicity
	public boolean playPlaylist(String playlistTitle, int indexInPL)
	{
		for (int i=0;i<playlists.size();i++){
			if (playlists.get(i).getTitle().equals(playlistTitle)){
				System.out.println(playlistTitle);
				if (indexInPL>playlists.get(i).getContent().size()||indexInPL<1){
					errorMsg="Content not found";
					return false;
				}
				if (playlists.get(i).getContent().get(indexInPL-1).getType().equals("SONG")){
					Song tempSong = (Song) playlists.get(i).getContent().get(indexInPL-1);
					tempSong.play();
					return true;
				}
				else if (playlists.get(i).getContent().get(indexInPL-1).getType().equals("AUDIOBOOK")){
					AudioBook tempBook = (AudioBook) playlists.get(i).getContent().get(indexInPL-1);
					tempBook.play();
					return true;
				}
				else if (playlists.get(i).getContent().get(indexInPL-1).getType().equals("PODCAST")){
					Podcast tempPod = (Podcast) playlists.get(i).getContent().get(indexInPL-1);
					tempPod.play();
					return true;
				}
				errorMsg="Invalid type";
				return false;
			}
		}
		errorMsg="Playlist not found";
		return false;
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	//Validate input, return appropriate error message if invalid
	//Add the content to the appropriate list by checking the data type
	public boolean addContentToPlaylist(String type, int index, String playlistTitle)
	{	
		int pLInd=0;
		boolean found = false;
		for (int i=0;i<playlists.size();i++){
			if(playlists.get(i).getTitle().equals(playlistTitle)){
				found = true;
				pLInd=i;
			}
		}

		if (!found){
			errorMsg="Playlist not found";
			return false;
		}

		if (type.equalsIgnoreCase("SONG")){
			if (index>songs.size()||index<1){
				errorMsg="Song not found";
				return false;
			}
			playlists.get(pLInd).addContent(songs.get(index-1));
			return true;
		}

		else if (type.equalsIgnoreCase("AUDIOBOOK")){
			if (index>audiobooks.size()||index<1){
				errorMsg="AudioBook not found";
				return false;
			}
			playlists.get(pLInd).addContent(audiobooks.get(index-1));
			return true;
		}
		else if (type.equalsIgnoreCase("PODCAST")){
			if (index>podcasts.size()||index<1){
				errorMsg="Podcast not found";
				return false;
			}
			playlists.get(pLInd).addContent(podcasts.get(index-1));
			return true;
		}

		else{
			errorMsg="Invalid type";
			return false;
		}
	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	//Validate input, return appropriate error message if invalid
	//Loop through the playlists, get the correct playlist, delete the content at the index
	public boolean delContentFromPlaylist(int index, String title)
	{
		int pLInd=0;
		boolean found = false;
		for (int i=0;i<playlists.size();i++){
			if(playlists.get(i).getTitle().equals(title)){
				found = true;
				pLInd=i;
			}
		}
		if (!found){
			errorMsg="Playlist not found";
			return false;
		}
		if (index>playlists.get(pLInd).getContent().size()||index<1){
			errorMsg="Content not found";
			return false;
		}
		else{
			playlists.get(pLInd).deleteContent(index);
			return true;
		}
	}
	
}

