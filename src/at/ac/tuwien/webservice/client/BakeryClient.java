package at.ac.tuwien.webservice.client;

import org.apache.log4j.Logger;

import at.ac.tuwien.webservice.client.service.Bakery;
import at.ac.tuwien.webservice.client.service.BakeryService;

public class BakeryClient {

	private static Logger logger = Logger.getLogger(BakeryClient.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		BakeryService service = new BakeryService();
		Bakery bakery = service.getBakeryPort();
		
		String query = "SELECT ?x { ?x <http://localhost/bakery.owl#amountAvailable> \"333\" }";
		
		logger.info("query: " + query);
		logger.info("	res: " + bakery.sparql(query));
		
		logger.info("insert: " + bakery.insert("WTF", "WTF1", "WTF2"));
		
		query = "SELECT ?x { ?x <http://localhost/bakery.owl#WTF1> ?y }";
		
		logger.info("query: " + query);
		logger.info("	res: " + bakery.sparql(query));
		
	}

}
