package it.polito.dp2.NFFG.sol1;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.xml.datatype.XMLGregorianCalendar;

import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.NodeReader;
import it.polito.dp2.NFFG.sol1.jaxb.NFFGType;
import it.polito.dp2.NFFG.sol1.jaxb.NodeType;

public class NffgReaderCode implements NffgReader {

	private String nffgName;
	private XMLGregorianCalendar XMLGregorianlastUpdateTime; 
	private Calendar lastUpdateTime;
	private Set<NodeReader> nodeReaders;
	
	public NffgReaderCode(NFFGType nffg) {
		this.nffgName = nffg.getName();
		System.out.println("NffgReaderCode - Inside Costructor");

		this.XMLGregorianlastUpdateTime = nffg.getLastUpdateTime();
		this.lastUpdateTime = this.XMLGregorianlastUpdateTime.toGregorianCalendar();

		nodeReaders = new HashSet<NodeReader>();
		for(NodeType node : nffg.getNodes().getNode()) {
			System.out.println("NffgReaderCode - filling Set<NodeReader>");
			NodeReader nodeReader = new NodeReaderCode(node, nffg);
			System.out.println("NffgReaderCode - adding to Set<NodeReader>");
			this.nodeReaders.add(nodeReader);
		}
		System.out.println("NffgReaderCode - End Costructor");

	}
	
	@Override
	public String getName() {
		return this.nffgName;
	}

	@Override
	public Calendar getUpdateTime() {
		return this.lastUpdateTime;
	}

	@Override
	public Set<NodeReader> getNodes() {
		return this.nodeReaders;
	}

	@Override
	public NodeReader getNode(String arg0) {
		for(NodeReader nodeReader: nodeReaders){
			if(nodeReader.equals(arg0)){
				return nodeReader;
			}
		}
		//TODO
		return null;
	}

}
