package de.ebertp.dai.claml.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class ClaMLRubric implements Serializable {
	
//	
//	//should be "coding-hint", but not permitted in enum
//	
//	public static enum RubricKind { 
//		coding_hint, definition, exclusion, inclusion, introduction, modifierlink, note, preferred, preferredLong, text 
//	}
//	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3998206913365859514L;
	private String kind;
	private String id;
	public Collection<ClaMLLabel> labels;
	

	public ClaMLRubric(String id, String kind) {
		super();
		this.id = id;
		this.kind = kind;
	}

	
	/**
	 * @return the labels
	 */
	public Collection<ClaMLLabel> getLabels() {
		if(labels==null){
			labels = new ArrayList<ClaMLLabel>();
		}
		return labels;
	}



	/**
	 * @param labels the labels to set
	 */
	public void setLabels(Collection<ClaMLLabel> labels) {
		this.labels = labels;
	}



	public void addLabel(ClaMLLabel label){
		if(labels==null){
			labels = new ArrayList<ClaMLLabel>();
		}
		labels.add(label);
	}
	
	

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * @param kind the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}
	
	
	
}
