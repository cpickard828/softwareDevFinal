/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boardClient;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

/**
 *
 * @author Joe Gregg
 */
public class FXMLDocumentController implements Initializable {
	public BoardGateway gateway;
	@FXML
	public TextArea textArea;
	@FXML
	public TextField comment;
	@FXML
	public TextField storyname;
	@FXML
	public TextField storydesc;
	@FXML
	public TextField storypri;
	@FXML
	public TextField storyID;
	@FXML
	public VBox storypane;
	@FXML
	public VBox progresspane;
	@FXML
	public VBox finishpane;

	public List<String> storyTranscript = Collections.synchronizedList(new ArrayList<String>());

	public void graph(ActionEvent event) {
	     int total = gateway.getStoryPoints();
	     System.out.println(total);
		Stage stage = new Stage();
		stage.setTitle("Burndown Chart");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("# of items Finished");
        yAxis.setLabel("Story Points");
        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle("Burndown");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        //series.setName("My portfolio");
        //populating the series with data
        series.getData().add(new XYChart.Data(0, total));


        int x = 1;
        String vec = gateway.getFinishedPoints();
        String[] vecArray = vec.split(",");
        for(int i = 0; i < Array.getLength(vecArray); i++ ) {
        	total -= Integer.parseInt(vecArray[0]);
        	series.getData().add(new XYChart.Data(x, total));
        	x++;
        }

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
	}
	@FXML
	public void sendStory(ActionEvent event) {
		if(!storyname.getText().trim().equals("") && !storydesc.getText().trim().equals("") && !storypri.getText().trim().equals("")  && storypri.getText().matches("-?\\d+") && Integer.parseInt(storypri.getText()) >= 0 && Integer.parseInt(storypri.getText()) <= 10) {
			String text = storyname.getText() + "|" + storydesc.getText() + "|" + storypri.getText() + "|1";
			storyname.clear();
			storydesc.clear();
			storypri.clear();
			gateway.sendStory(text);
		}
	}

	@FXML
	public void delete(ActionEvent event) {
		String id = storyID.getText();
		storyID.clear();
		// String text = storyname.getText() + "|" + storydesc.getText() + "|" +
		// storypri.getText();
		//System.out.println(id);
		gateway.deleteID(id);
	}

	@FXML
	public void moveNotStarted(ActionEvent event) {
		String id = storyID.getText();
		storyID.clear();
		// String text = storyname.getText() + "|" + storydesc.getText() + "|" +
		// storypri.getText();
		String transferText;
		transferText = gateway.deleteID(id);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transferText = transferText.substring(0, transferText.length() - 1);
		transferText = transferText.split("\\|", 2)[1] + "1";
		gateway.sendStory(transferText);
	}

	@FXML
	public void moveProgress(ActionEvent event) {
		String id = storyID.getText();
		storyID.clear();
		// String text = storyname.getText() + "|" + storydesc.getText() + "|" +
		// storypri.getText();
		String transferText;
		transferText = gateway.deleteID(id);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transferText = transferText.substring(0, transferText.length() - 1);
		transferText = transferText.split("\\|", 2)[1] + "2";
		gateway.sendStory(transferText);
	}

	@FXML
	public void moveFinished(ActionEvent event) {
		String id = storyID.getText();
		storyID.clear();
		// String text = storyname.getText() + "|" + storydesc.getText() + "|" +
		// storypri.getText();
		String transferText;
		transferText = gateway.deleteID(id);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transferText = transferText.substring(0, transferText.length() - 1);
		transferText = transferText.split("\\|", 2)[1] + "3";
		gateway.sendStory(transferText);
	}

	@FXML
	public void modify(ActionEvent event) {
		if(!storyname.getText().trim().equals("") && !storydesc.getText().trim().equals("") && !storypri.getText().trim().equals("")  && storypri.getText().matches("-?\\d+") && Integer.parseInt(storypri.getText()) >= 0 && Integer.parseInt(storypri.getText()) <= 10) {
			String id = storyID.getText();
			storyID.clear();
			// String text = storyname.getText() + "|" + storydesc.getText() + "|" +
			// storypri.getText();
			String transferText;
			transferText = gateway.deleteID(id);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			char col = transferText.charAt(transferText.length() - 1);
			String text = storyname.getText() + "|" + storydesc.getText() + "|" + storypri.getText() + "|" + col;
			storyname.clear();
			storydesc.clear();
			storypri.clear();
			gateway.sendStory(text);
		}
	}

	@FXML
	public void view(ActionEvent event) {
		String id = storyID.getText();
		// String text = storyname.getText() + "|" + storydesc.getText() + "|" +
		// storypri.getText();

		gateway.deleteID(id);
	}

	@FXML
	public void sendComment(ActionEvent event) {
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
		new Thread(new TranscriptCheck(gateway, textArea, storypane, progresspane, finishpane, this)).start();
	}
}

class TranscriptCheck implements Runnable, board.BoardConstants {
	Map<Integer, TextArea> map = new HashMap<Integer, TextArea>();

	private BoardGateway gateway; // Gateway to the server
	private TextArea textArea; // Where to display comments
	private VBox storypane; // Where to display comments
	private VBox progresspane; // Where to display comments
	private VBox finishpane; // Where to display comments
	private int N; // How many comments we have read
	private int S; // How many stories we have read
	private int D;
	private FXMLDocumentController fxdc;

	/** Construct a thread */
	public TranscriptCheck(BoardGateway gateway, TextArea textArea, VBox storypane, VBox progresspane, VBox finishpane, FXMLDocumentController fxdc) {
		this.gateway = gateway;
		this.textArea = textArea;
		this.storypane = storypane;
		this.progresspane = progresspane;
		this.finishpane = finishpane;
		this.N = 0;
		this.S = 0;
		this.D = 0;
		this.fxdc = fxdc;
	}

	/** Run a thread */
	public void run() {
		while (true) {
			if (gateway.getDeletedCount() > D) {
				// delete story
				D++;
			} else {
				try {
					Thread.sleep(250);
				} catch (InterruptedException ex) {
				}
			}
			if (gateway.getStoryCount() > S) {
				String newComment = gateway.getStory(S);
				System.out.println(newComment);
				String[] data = newComment.split("\\|");
				String storyKey = data[0];
				String name = data[1];
				String desc = data[2];
				String pri = data[3];
				int col = Integer.parseInt(data[4]);

				fxdc.storyTranscript.add(newComment);
				TextArea newTextArea = new TextArea();
				newTextArea.setEditable(false);
				map.put(Integer.parseInt(storyKey), newTextArea);

				switch (col) {
				case 1: {
					Platform.runLater(() -> storypane.getChildren().add(newTextArea));
					break;
				}
				case 2: {
					Platform.runLater(() -> progresspane.getChildren().add(newTextArea));
					break;
				}
				case 3: {
					Platform.runLater(() -> finishpane.getChildren().add(newTextArea));
					break;
				}}
				Platform.runLater(() -> newTextArea.appendText("Story " + storyKey + "\n"));
				Platform.runLater(() -> newTextArea.appendText(name + "\n"));
				Platform.runLater(() -> newTextArea.appendText(desc + "\n"));
				Platform.runLater(() -> newTextArea.appendText("Priority: " + pri + "\n"));
				S++;
			} else if (gateway.getStoryCount() < S) {
				int lastDeleted = gateway.getLastDeleted();
				if (lastDeleted > 0) {
					S--;
					// delete story for real this time
					TextArea deleteStory = map.get(lastDeleted);
					Platform.runLater(() -> ((VBox) deleteStory.getParent()).getChildren().remove(deleteStory));
					map.remove(lastDeleted);
				}

			} else {
				try {
					Thread.sleep(250);
				} catch (InterruptedException ex) {
				}
			}
			if (gateway.getCommentCount() > N) {
				String newComment = gateway.getComment(N);
				Platform.runLater(() -> textArea.appendText(newComment + "\n"));
				N++;
			} else {
				try {
					Thread.sleep(250);
				} catch (InterruptedException ex) {
				}
			}

		}
	}
}
