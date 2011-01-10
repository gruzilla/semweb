package at.ac.tuwien.webservice.service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

@WebService
@SOAPBinding(style=Style.DOCUMENT)
public class Bakery {
	
	private Model model;

	public Bakery() {
		model = ModelFactory.createMemModelMaker().createModel("bakery");
		model.read("data/bakery.xml", "RDF/XML");
		model.read("data/bakery_instances.xml", "RDF/XML");
	}
	
	public String sparql(String sparql) {
		Query query = QueryFactory.create(sparql);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		ResultSetFormatter.outputAsXML(true);
		String xmlResult = ResultSetFormatter.asXMLString(results);
		
		qe.close();
		return xmlResult;
	}
	
	public void insert() {
		
	}
}
