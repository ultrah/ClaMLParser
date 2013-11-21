package de.ebertp.dai.claml.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ClaMLModifiedBy implements Serializable {
	
	protected ClaMLModifierClass modifier;
	protected ArrayList<String> validFor = new ArrayList<String>();
	
	
	public ClaMLModifiedBy(ClaMLModifierClass modifier) {
		this.modifier = modifier;
	}
	/**
	 * @return the modifier
	 */
	public ClaMLModifierClass getModifier() {
		return modifier;
	}
	/**
	 * @param modifier the modifier to set
	 */
	public void setModifier(ClaMLModifierClass modifier) {
		this.modifier = modifier;
	}
	

	/**
	 * @return the validFor
	 */
	public ArrayList<String> getValidFor() {
		return validFor;
	}
	/**
	 * @param validFor the validFor to set
	 */
	public void setValidFor(ArrayList<String> validFor) {
		this.validFor = validFor;
	}

	
	public void addModifier(String code){
		if(validFor==null){
			validFor = new ArrayList<String>();
		}
		validFor.add(code);
	}
	
	

}
