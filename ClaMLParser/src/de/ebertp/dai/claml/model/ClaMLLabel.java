package de.ebertp.dai.claml.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ClaMLLabel implements Serializable {
	
	String value;
	ArrayList<String> paras = new ArrayList<String>();
	ArrayList<ClaMLFragment> fragments  = new ArrayList<ClaMLFragment>();
	
	
	//TODO extend this
	
	public ClaMLLabel(String value) {
		super();
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the paras
	 */
	public ArrayList<String> getParas() {
		return paras;
	}

	/**
	 * @param paras the paras to set
	 */
	public void setParas(ArrayList<String> paras) {
		this.paras = paras;
	}
	
	public void addPara(String param){
		this.paras.add(param);
	}

	/**
	 * @return the fragments
	 */
	public ArrayList<ClaMLFragment> getFragments() {
		return fragments;
	}

	/**
	 * @param fragments the fragments to set
	 */
	public void setFragments(ArrayList<ClaMLFragment> fragments) {
		this.fragments = fragments;
	}
	
	
	public void addFragment(ClaMLFragment fragment){
		this.fragments.add(fragment);
	}
	

}
