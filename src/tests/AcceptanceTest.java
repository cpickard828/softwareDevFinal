package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import boardClient.BoardClient;
import boardClient.FXMLDocumentController;
import boardServer.BoardServer;

class AcceptanceTest {

	@Test
	void test() {
		BoardServer boardServer = new BoardServer();
		BoardClient boardClient = new BoardClient();
		
		boardServer.FXMLDocumentController serverDocumentController = new boardServer.FXMLDocumentController();
		boardClient.FXMLDocumentController clientDocumentController = new boardClient.FXMLDocumentController();
		
		clientDocumentController.storyID.setText("1374");
		assert(true);
	}

}
