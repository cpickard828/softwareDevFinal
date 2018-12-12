package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

import boardClient.BoardClient;
import boardClient.FXMLDocumentController;
import boardServer.BoardServer;
import board.BoardConstants;


class AcceptanceTest {

	@Test
	void test() {
		BoardServer boardServer = new BoardServer();
		BoardClient boardClient = new BoardClient();
		
		boardServer.FXMLDocumentController serverDocumentController = new boardServer.FXMLDocumentController();
		boardClient.FXMLDocumentController clientDocumentController = new boardClient.FXMLDocumentController();
		
		serverDocumentController.initialize(null, null);
		
		assert(serverDocumentController.clientNo==0);
		
		serverDocumentController.test=1;
		InetAddress addr;
		Socket socket;
		PrintWriter outputToServer;
		try {
			addr = InetAddress.getByName("localhost");
			try {
				socket = new Socket(addr, 8000);
				outputToServer = new PrintWriter(socket.getOutputStream());
				outputToServer.println(BoardConstants.SEND_HANDLE);
				outputToServer.println("handle");
				outputToServer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(serverDocumentController.clientNo);
		assert(serverDocumentController.clientNo==1);
		
	}

}
