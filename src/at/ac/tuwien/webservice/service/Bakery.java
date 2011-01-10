package at.ac.tuwien.webservice.service;

import java.util.Iterator;
import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;

@WebService
@SOAPBinding(style=Style.RPC)
public class Bakery {
	
	private Model model;
	
	private InfModel infmodel;
	
	private static final String ResourceURI = "http://localhost/bakery.owl#";
	private static final String PropertyURI = "http://localhost/bakery.owl#";
	private static final String InstanceURI = "http://localhost/bakery_instances.owl#";
	
	
	private static Logger logger = Logger.getLogger(Bakery.class);
	
	public Bakery() {
		model = ModelFactory.createMemModelMaker().createModel("bakery");
		model.read("file:data/bakery.xml", "RDF/XML");
		model.read("file:data/bakery_instances.xml", "RDF/XML");
		
		this.inferModel();
		
//		this.printAllStatements(infmodel);
	}
	
	
	public String sparql(String sparql) {
		logger.info("querying "+sparql); 
		Query query = QueryFactory.create(sparql);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		ResultSetFormatter.outputAsXML(true);
		String xmlResult = ResultSetFormatter.asXMLString(results);
		
		qe.close();
		return xmlResult;
	}
	
	
	public boolean insert(String subject, String property, String object) {
		// TEST
		logger.info("insert: [sub:" + subject + " - " + property + " obj: " + object);
		
		Resource r = model.createResource(ResourceURI + subject);
		Property p = model.createProperty(PropertyURI + property);
		
		Statement stmt = model.createStatement(r, p, object);
		
		model.add(stmt);
		
		// when model is still valid
		if(inferModel())
			return true;
		
		logger.error("insert failed");
		// if model is not valid any more
		model.remove(stmt);
		return false;
	}
	
	
	/**
	 * creates the inferred model
	 * 
	 * @return boolean (successfull?)
	 */
	private boolean inferModel()	{
		List<Rule> rules = Rule.rulesFromURL("file:data/generic.rules");
		
		GenericRuleReasoner reasoner = new GenericRuleReasoner(rules);
		reasoner.setOWLTranslation(true);			   // not needed in RDFS case
		reasoner.setTransitiveClosureCaching(true);
		
		infmodel = ModelFactory.createInfModel(reasoner, model);
		
		
		ValidityReport validity = infmodel.validate();
		if (validity.isValid()) {
			logger.info("Validation OK");
			return true;
		} else {
			logger.error("Conflicts");
			for (Iterator<?> i = validity.getReports(); i.hasNext(); ) {
				logger.error(" - " + i.next());
			}
		}
		
		return false;
	}
	
	
	/** DEBUG
	 * 
	 * prints all statements
	 */
	private void printAllStatements(Model m)	{
		// print existing statements
		// list the statements in the Model
//		/*
		StmtIterator iter = m.listStatements();

		// print out the predicate, subject and object of each statement
		while (iter.hasNext()) {
			Statement stmt	  = iter.nextStatement();  // get next statement
			Resource  subject   = stmt.getSubject();	 // get the subject
			Property  predicate = stmt.getPredicate();   // get the predicate
			RDFNode   object	= stmt.getObject();	  // get the object

			System.out.print(subject.toString());
			System.out.print(" " + predicate.toString() + " ");
			if (object instanceof Resource) {
			   System.out.print("res: " + object.toString());
			} else {
				// object is a literal
				System.out.print(" \"" + object.toString() + "\"");
			}

			System.out.println(" .");
		}
//		*/
	}
}
