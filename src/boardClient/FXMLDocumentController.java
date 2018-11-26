/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boardClient;

import java.net.URL;
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
    private void sendComment(ActionEvent event) {
        String text = comment.getText();
        gateway.sendComment(text);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gateway = new BoardGateway(textArea);

        // Put up a dialog to get a handle from the user
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Start Chat");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter a handle:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> gateway.sendHandle(name));

        // Start the transcript check thread
        new Thread(new TranscriptCheck(gateway,textArea)).start();
    }
}

class TranscriptCheck implements Runnable, board.BoardConstants {
    private BoardGateway gateway; // Gateway to the server
    private TextArea textArea; // Where to display comments
    private int N; // How many comments we have read

    /** Construct a thread */
    public TranscriptCheck(BoardGateway gateway,TextArea textArea) {
      this.gateway = gateway;
      this.textArea = textArea;
      this.N = 0;
    }

    /** Run a thread */
    public void run() {
      while(true) {
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