package de.ebertp.dai.claml.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

public class ClaMLModifierClass implements Serializable {

	private String code;
	protected ArrayList<ClaMLRubric> rubrics = new ArrayList<ClaMLRubric>();
	protected ArrayList<ClaMLMeta> metaData = new ArrayList<ClaMLMeta>();

	private ClaMLModifierClass parent;
	protected TreeMap<String, ClaMLModifierClass> children = new TreeMap<String, ClaMLModifierClass>();

	public ClaMLModifierClass(String code) {
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the parent
	 */
	public ClaMLModifierClass getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(ClaMLModifierClass parent) {
		this.parent = parent;
	}

	/**
	 * @return the rubrics
	 */
	public ArrayList<ClaMLRubric> getRubrics() {
		return rubrics;
	}

	/**
	 * @param rubrics
	 *            the rubrics to set
	 */
	public void setRubrics(ArrayList<ClaMLRubric> rubrics) {
		this.rubrics = rubrics;
	}

	public void addRubric(ClaMLRubric rubric) {
		if (rubrics == null) {
			rubrics = new ArrayList<ClaMLRubric>();
		}
		rubrics.add(rubric);
	}

	/**
	 * @return the metaData
	 */
	public ArrayList<ClaMLMeta> getMetaData() {
		if(metaData==null){
			metaData = new ArrayList<ClaMLMeta>();
		}
		return metaData;
	}

	/**
	 * @param metaData
	 *            the metaData to set
	 */
	public void setMetaData(ArrayList<ClaMLMeta> metaData) {
		this.metaData = metaData;
	}

	public void addMetaData(String name, String value){
		if(metaData==null){
			metaData = new ArrayList<ClaMLMeta>();
		}
		metaData.add(new ClaMLMeta(value, name));
	}

	/**
	 * @return the children
	 */
	public TreeMap<String, ClaMLModifierClass> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(TreeMap<String, ClaMLModifierClass> children) {
		this.children = children;
	}

	public void addChild(ClaMLModifierClass child) {
		children.put(child.getCode(), child);
	}

	@Override
	public String toString() {
		return "ClaMLModifier - Code: " + getCode() + " Parent: "
				+ (getParent() == null ? "--" : getParent().getCode())
				+ " Number of MetaData: " + getMetaData().size()
				+ " Number of Children: " + getChildren().size()
				+ " Number of Rubrics: " + getRubrics().size();
	}

}
