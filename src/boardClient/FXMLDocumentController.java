/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boardClient;

import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

/**
 *
 * @author Joe Gregg
 */
public class FXMLDocumentController implements Initializable {
    private BoardGateway gateway;
    @FXML
    private TextArea textArea;
    @FXML
    private TextField comment;
    @FXML
    private TextField storyname;
    @FXML
    private TextField storydesc;
    @FXML
    private TextField storypri;
    @FXML
    private TextArea storypane;

    
    @FXML
    private void sendStory(ActionEvent event) {
    	String text = storyname.getText() + "|" + storydesc.getText() + "|" + storypri.getText();

        gateway.sendStory(text);
    }
    
    @FXML
    private void sendComment(ActionEvent event) {
        String text = comment.getText();
        gateway.sendComment(text);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gateway = new BoardGateway(textArea, storypane);

        // Put up a dialog to get a handle from the user
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Start Chat");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter a handle:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> gateway.sendHandle(name));

        // Start the transcript check thread
        new Thread(new TranscriptCheck(gateway,textArea,storypane)).start();
    }
}

class TranscriptCheck implements Runnable, board.BoardConstants {
    private BoardGateway gateway; // Gateway to the server
    private TextArea textArea; // Where to display comments
    private TextArea storypane; // Where to display comments
    private int N; // How many comments we have read
    private int S; // How many stories we have read

    /** Construct a thread */
    public TranscriptCheck(BoardGateway gateway,TextArea textArea,TextArea storypane) {
      this.gateway = gateway;
      this.textArea = textArea;
      this.storypane = storypane;
      this.N = 0;
      this.S = 0;
    }

    /** Run a thread */
    public void run() {
      while(true) {
          if(gateway.getStoryCount() > S) {
              String newComment = gateway.getStory(S);
              String[] data = newComment.split("\\|");
              String name = data[0];
              String desc = data[1];
              String pri = data[2];
              Platform.runLater(()->storypane.appendText(name + "\n"));
              Platform.runLater(()->storypane.appendText(desc + "\n"));
              Platform.runLater(()->storypane.appendText("Priority: " + pri + "\n_______________________________________\n"));
              S++;
          } else {
              try {
                  Thread.sleep(250);
              } catch(InterruptedException ex) {}
          }
          if(gateway.getCommentCount() > N) {
              String newComment = gateway.getComment(N);
              Platform.runLater(()->textArea.appendText(newComment + "\n"));
              N++;
          } else {
              try {
                  Thread.sleep(250);
              } catch(InterruptedException ex) {}
          }

      }
    }
  }