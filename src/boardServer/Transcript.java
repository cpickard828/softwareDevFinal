package boardServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	}

	public void addStory(String comment) {
		storyTranscript.add(comment);
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
				return story;
			}
		}
		return null;
	}
}