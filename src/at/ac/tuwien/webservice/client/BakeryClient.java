package at.ac.tuwien.webservice.client;

import org.apache.log4j.Logger;

import at.ac.tuwien.webservice.client.service.Bakery;
import at.ac.tuwien.webservice.client.service.BakeryService;

public class BakeryClient {

	private static final String ResourceURI = "http://localhost/bakery.owl#";
	private static Logger logger = Logger.getLogger(BakeryClient.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		BakeryService service = new BakeryService();
		Bakery bakery = service.getBakeryPort();
		
		String query = "SELECT ?x { ?x <"+ResourceURI+"amountAvailable> \"333\" }";
		
		logger.info("query: " + query);
		logger.info("	res: " + bakery.sparql(query));
		
		logger.info("insert: " + bakery.insert("Nut", "subClassOf", ResourceURI+"NamedIngredients"));
		logger.info("insert: " + bakery.insert("nut1", "type", ResourceURI+"Nut"));
		logger.info("insert: " + bakery.insert("nut1", "amountAvailable", "30"));
		
		query = "SELECT ?x { ?x ?y <"+ResourceURI+"Nut> }";
		
		logger.info("query: " + query);
		logger.info("	res: " + bakery.sparql(query));
		
	}

}
