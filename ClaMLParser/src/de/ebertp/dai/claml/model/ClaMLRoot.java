package de.ebertp.dai.claml.model;

import java.util.TreeMap;

public class ClaMLRoot extends ClaMLClass {
	
	
	public TreeMap<String, ClaMLModifierClass> modifiers = new TreeMap<String, ClaMLModifierClass>();
	
	//not needed, just to satisfy dependencies
	public ClaMLRoot(String type, String code) {
		super(type, code);
	}
	
	
	ClaMLTitle title;
	private String version;
	private ClaMLIdentifier identifier; 
	
	/**
	 * @return the identifier
	 */
	public ClaMLIdentifier getIdentifier() {
		return identifier;
	}
	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(ClaMLIdentifier identifier) {
		this.identifier = identifier;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the title
	 */
	public ClaMLTitle getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(ClaMLTitle title) {
		this.title = title;
	}
	/**
	 * @return the modifiers
	 */
	public TreeMap<String, ClaMLModifierClass> getModifiers() {
		return modifiers;
	}
	/**
	 * @param modifiers the modifiers to set
	 */
	public void setModifiers(TreeMap<String, ClaMLModifierClass> modifiers) {
		this.modifiers = modifiers;
	}
	
	public void addModifier(ClaMLModifierClass modifier){
		if(modifiers==null){
			modifiers = new TreeMap<String, ClaMLModifierClass>();
		}
		this.modifiers.put(modifier.getCode(), modifier);
	}
	
	

	
}
