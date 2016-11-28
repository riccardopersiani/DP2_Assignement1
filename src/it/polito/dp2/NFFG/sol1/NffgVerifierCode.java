package it.polito.dp2.NFFG.sol1;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.PolicyReader;
import it.polito.dp2.NFFG.ReachabilityPolicyReader;
import it.polito.dp2.NFFG.TraversalPolicyReader;
import it.polito.dp2.NFFG.sol1.jaxb.NFFGType;
import it.polito.dp2.NFFG.sol1.jaxb.ReachabilityPolicyType;
import it.polito.dp2.NFFG.sol1.jaxb.RootNetworkType;
import it.polito.dp2.NFFG.sol1.jaxb.TraversalPolicyType;

public class NffgVerifierCode implements NffgVerifier {

	private Set<NffgReader> nffgReaders;
	private Set<PolicyReader> policyReaders;
	
	private Map<NffgReader,Set<PolicyReader>> policyMap;

	public static final String XSD_NAME = "xsd/NffgInfo.xsd";
	public static final String PACKAGE = "it.polito.dp2.NFFG.sol1.jaxb";

	public NffgVerifierCode() throws SAXException, JAXBException{	
		System.out.println("START NffgVerifierCode!!");

		nffgReaders = new HashSet<NffgReader>();
		policyReaders = new HashSet<PolicyReader>();
		
		policyMap = new HashMap<NffgReader,Set<PolicyReader>>();

		String fileName = System.getProperty("it.polito.dp2.NFFG.sol1.NffgInfo.file");
		RootNetworkType root = null;
		root = doUnmarshall(new File(fileName));
		System.out.println("root: "+root);

		for(NFFGType nffg : root.getNFFG()) {
			NffgReader nffgReader = new NffgReaderCode(nffg);
			nffgReaders.add(nffgReader);
			
			for(ReachabilityPolicyType reachabilityPolicy: nffg.getPolicies().getReachabilityPolicy()){
				System.out.println("Inside reachability for{}");
				ReachabilityPolicyReader reachabilityReader = new ReachabilityPolicyReaderCode(nffg, nffgReader, reachabilityPolicy);
				policyReaders.add(reachabilityReader);
			}
			
			for(TraversalPolicyType traversalPolicy: nffg.getPolicies().getTraversalPolicy()){
				System.out.println("Inside traversal for{}");
				TraversalPolicyReader traversalReader = new TraversalPolicyReaderCode(nffg, nffgReader, traversalPolicy);
				policyReaders.add(traversalReader);
			}
			policyMap.put(nffgReader, policyReaders);
		}	
	}

	@Override
	public Set<NffgReader> getNffgs() {
		return nffgReaders;
	}

	@Override
	public NffgReader getNffg(String arg0) {
		for(NffgReader nffgReader: nffgReaders){
			if(nffgReader.equals(arg0)){
				return nffgReader;
			}
		}
		return null;
	}

	@Override
	public Set<PolicyReader> getPolicies() {
		return policyReaders;
	}

	@Override
	//Filtering policies by nffg name
	public Set<PolicyReader> getPolicies(String arg0) {
		for(NffgReader nffgReader: nffgReaders){
			if(nffgReader.equals(arg0)){
				return policyReaders;
			}
		}
		return null;
	}

	@Override
	public Set<PolicyReader> getPolicies(Calendar arg0) {
		// TODO Auto-generated method stub
		for(PolicyReader policyReader: policyReaders){
			if(policyReader.equals(arg0)){
				return policyReaders;
			}
		}
		return null;
	}
	
	private RootNetworkType doUnmarshall(File inputFile) throws JAXBException, SAXException, IllegalArgumentException {
		System.out.println("inputFile: "+inputFile);
		JAXBContext jc = JAXBContext.newInstance(PACKAGE);
		Schema schema = null;
		// Create the package where the class used to read XML elements and create objects like NodeType

		try {
			// Instantiate the schema from the file .xsd
			schema = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI).newSchema(new File(XSD_NAME));
			
		}catch(IllegalArgumentException e) {
			System.err.println("Error! No implementation of the schema language is available");
			throw e;
		}
		catch(NullPointerException e) {
			System.err.println("Error! The instance of the schema or the file of the schema is not well created!\n");
			throw new SAXException("The schema file is null!");
		}
		// Create the Unmarshaller to extract the schema data
		Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
		// Set the schema 
		jaxbUnmarshaller.setSchema(schema);
		// Set the input file to be unmarshalled 
		System.out.println("Unmarshaling Done!!");
		//RootNetworkType root = (RootNetworkType)jaxbUnmarshaller.unmarshal(inputFile);
		//TODO fix this warning
		JAXBElement<RootNetworkType> root = (JAXBElement<RootNetworkType>) jaxbUnmarshaller.unmarshal(inputFile);
		RootNetworkType r = root.getValue();
		return r;
	}

}
