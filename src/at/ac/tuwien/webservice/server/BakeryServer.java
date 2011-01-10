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
		
		System.out.println("Type 'exit' to stop the server or 'printModel' to print it.");
		while(input.hasNextLine()) {
			String line = input.next().trim();
			if (line.equals("") || line.equals("exit")) break;
			if (line.equals("printModel")) {
				server.printAllStatements();
			}
		}
		System.out.println("ty");
		
		endpoint.stop();
	}
}
