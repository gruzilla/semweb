package at.ac.tuwien.webservice.server;

import java.util.Scanner;

import javax.xml.ws.Endpoint;

import at.ac.tuwien.webservice.service.Bakery;

public class BakeryServer {
	public static void main (String args[]) {
		Bakery server = new Bakery();
		Endpoint endpoint =
			Endpoint.publish("http://localhost:8080/bakery", server);
		Scanner input = new Scanner(System.in);
		
		System.out.println("Press enter to stop the server.");
		input.hasNextLine();
		System.out.println("ty");
		
		endpoint.stop();
	}
}
