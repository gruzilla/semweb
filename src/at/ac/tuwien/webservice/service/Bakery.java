package at.ac.tuwien.webservice.service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style=Style.DOCUMENT)
public class Bakery {
	public Object sparql(String sparql) {
		return new Object();
	}
	
	public void insert() {
		
	}
}
