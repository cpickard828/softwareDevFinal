package boardServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author Joe Gregg
 */
public class Transcript {
	private List<String> transcript = Collections.synchronizedList(new ArrayList<String>());
	private List<String> storyTranscript = Collections.synchronizedList(new ArrayList<String>());
	private int deleted = 0;
	private int lastDeleted = 0;

	public Transcript() {
		Scanner s;
		try {
			s = new Scanner(new File(System.getProperty("user.dir") + "\\stories.dat"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		while (s.hasNext()) {
			addStory(s.next());
		}
		s.close();
	}

	public void save() {
		FileWriter writer;
		try {
			writer = new FileWriter(System.getProperty("user.dir") + "\\stories.dat");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		for (String str : storyTranscript) {
			try {
				writer.write(str + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addStory(String comment) {
		storyTranscript.add(comment);
		save();
	}

	public void addComment(String comment) {
		transcript.add(comment);
	}

	public int getSize() {
		return transcript.size();
	}

	public int getStorySize() {
		return storyTranscript.size();
	}

	public String getComment(int n) {
		return transcript.get(n);
	}

	public String getStory(int n) {
		return storyTranscript.get(n);
	}

	public int getDeletedSize() {
		return deleted;
	}

	public int getDeleted() {
		return lastDeleted;
	}

	public String deleteStory(int n) {
		for (int i = 0; i < storyTranscript.size(); i++) {
			if (Integer.parseInt(storyTranscript.get(i).split("\\|")[0]) == n) {
				String story = storyTranscript.get(i);
				storyTranscript.remove(i);
				lastDeleted = n;
				deleted++;
				save();
				return story;
			}
		}
		return null;
	}

	public int getAllPoints() {
		int sum = 0;
		for (int i = 0; i < storyTranscript.size(); i++) {
			sum += Integer.parseInt(storyTranscript.get(i).split("\\|")[3]);
		}
		return sum;
	}

	public String getFinishedPoints() {
		String vec = ",";
		for (int i = 0; i < storyTranscript.size(); i++) {
			if(Integer.parseInt(storyTranscript.get(i).split("\\|")[4]) == 3) {
				vec += (storyTranscript.get(i).split("\\|")[3]) + ",";
			}
		}
		return vec;
	}
}