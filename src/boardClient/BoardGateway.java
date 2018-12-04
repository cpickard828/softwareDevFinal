package boardClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class BoardGateway implements board.BoardConstants {

    private PrintWriter outputToServer;
    private BufferedReader inputFromServer;
    private TextArea textArea;
    private VBox storypane;

    // Establish the connection to the server.
    public BoardGateway(TextArea textArea, VBox storypane) {
        this.textArea = textArea;
        this.storypane = storypane;
        try {
            // Create a socket to connect to the server
        	InetAddress addr = InetAddress.getByName("localhost");
            Socket socket = new Socket(addr, 8000);

            // Create an output stream to send data to the server
            outputToServer = new PrintWriter(socket.getOutputStream());

            // Create an input stream to read data from the server
            inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Exception in gateway constructor: " + ex.toString() + "\n"));
            //Platform.runLater(() -> storypane.appendText("Exception in gateway constructor: " + ex.toString() + "\n"));
        }
    }

    // Start the chat by sending in the user's handle.
    public void sendHandle(String handle) {
        outputToServer.println(SEND_HANDLE);
        outputToServer.println(handle);
        outputToServer.flush();
    }

    // Send a new comment to the server.
    public void sendComment(String comment) {
        outputToServer.println(SEND_COMMENT);
        outputToServer.println(comment);
        outputToServer.flush();
    }

    // Send a new comment to the server.
    public void sendStory(String comment) {
        outputToServer.println(SEND_STORY);
        outputToServer.println(comment);
        outputToServer.flush();
    }

    public void deleteID(String id) {
        outputToServer.println(DELETE);
        outputToServer.println(id);
        outputToServer.flush();
    }


    // Ask the server to send us a count of how many comments are
    // currently in the transcript.
    public int getCommentCount() {
        outputToServer.println(GET_COMMENT_COUNT);
        outputToServer.flush();
        int count = 0;
        try {
            count = Integer.parseInt(inputFromServer.readLine());
        } catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Error in getCommentCount: " + ex.toString() + "\n"));
            //Platform.runLater(() -> storypane.appendText("Error in getCommentCount: " + ex.toString() + "\n"));
        }
        return count;
    }

    // Ask the server to send us a count of how many comments are
    // currently in the transcript.
    public int getStoryCount() {
        outputToServer.println(GET_STORY_COUNT);
        outputToServer.flush();
        int count = 0;
        try {
            count = Integer.parseInt(inputFromServer.readLine());
        } catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Error in getStoryCount: " + ex.toString() + "\n"));
            //Platform.runLater(() -> storypane.appendText("Error in getStoryCount: " + ex.toString() + "\n"));
        }
        return count;
    }
    
    // Ask the server to send us a count of how many comments are
    // currently in the transcript.
    public int getDeletedCount() {
        outputToServer.println(GET_DELETED);
        outputToServer.flush();
        int count = 0;
        try {
            count = Integer.parseInt(inputFromServer.readLine());
        } catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Error in getStoryCount: " + ex.toString() + "\n"));
            //Platform.runLater(() -> storypane.appendText("Error in getStoryCount: " + ex.toString() + "\n"));
        }
        return count;
    }

    // Fetch comment n of the transcript from the server.
    public String getComment(int n) {
        outputToServer.println(GET_COMMENT);
        outputToServer.println(n);
        outputToServer.flush();
        String comment = "";
        try {
            comment = inputFromServer.readLine();
        } catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Error in getComment: " + ex.toString() + "\n"));
            //Platform.runLater(() -> storypane.appendText("Error in getComment: " + ex.toString() + "\n"));
        }
        return comment;
    }

    // Fetch comment n of the transcript from the server.
    public String getStory(int n) {
        outputToServer.println(GET_STORY);
        outputToServer.println(n);
        outputToServer.flush();
        String comment = "";
        try {
            comment = inputFromServer.readLine();
        } catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Error in getStory: " + ex.toString() + "\n"));
            //Platform.runLater(() -> storypane.appendText("Error in getStory: " + ex.toString() + "\n"));
        }
        return comment;
    }
    
    
}