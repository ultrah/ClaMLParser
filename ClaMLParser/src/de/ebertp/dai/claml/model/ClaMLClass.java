package de.ebertp.dai.claml.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

public class ClaMLClass implements Serializable {

	// base for classes like chapter, block or category
	protected String kind;
	protected String code;

	protected ClaMLClass parent;
	protected TreeMap<String, ClaMLClass> children = new TreeMap<String, ClaMLClass>();

	protected ArrayList<ClaMLMeta> metaData = new ArrayList<ClaMLMeta>();
	protected ArrayList<ClaMLRubric> rubrics = new ArrayList<ClaMLRubric>();
	protected ArrayList<ClaMLModifiedBy> modifiedBys = new ArrayList<ClaMLModifiedBy>();

	public ClaMLClass() {
		this.kind = "";
		this.code = "";
	}

	public ClaMLClass(String code, String kind) {
		super();
		this.kind = kind;
		this.code = code;
	}

	/**
	 * @return the parent
	 */
	public ClaMLClass getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(ClaMLClass parent) {
		this.parent = parent;
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
	 * @return the metaData
	 */
	public ArrayList<ClaMLMeta> getMetaData() {
		if (metaData == null) {
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

	public void addMetaData(String name, String value) {
		if (metaData == null) {
			metaData = new ArrayList<ClaMLMeta>();
		}
		metaData.add(new ClaMLMeta(value, name));
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
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * @param kind
	 *            the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * @return the children
	 */
	public TreeMap<String, ClaMLClass> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(TreeMap<String, ClaMLClass> children) {
		this.children = children;
	}

	public void addChild(ClaMLClass child) {
		children.put(child.getCode(), child);
	}

	/**
	 * @return the modifiedBy
	 */
	public ArrayList<ClaMLModifiedBy> getModifiedBy() {
		return modifiedBys;
	}

	/**
	 * @param modifiedBy
	 *            the modifiedBy to set
	 */
	public void setModifiedBy(ArrayList<ClaMLModifiedBy> modifiedBy) {
		this.modifiedBys = modifiedBy;
	}

	public void addModifiedBy(ClaMLModifiedBy modifiedBy) {
		modifiedBys.add(modifiedBy);
	}

	@Override
	public String toString() {
		return "ClaMLClass - Code: " + getCode() + " Kind: " + getKind()
				+ " Parent: "
				+ (getParent() == null ? "--" : getParent().getCode())
				+ " Number of MetaData: " + getMetaData().size()
				+ " Number of Children: " + getChildren().size()
				+ " Number of Rubrics: " + getRubrics().size()
				+ " Has Modifier: " + (getModifiedBy() == null ? "no" : "yes");
	}

	/**
	 * @param modifiedBys
	 *            the modifiedBys to set
	 */
	public void setModifiedBys(ArrayList<ClaMLModifiedBy> modifiedBys) {
		this.modifiedBys = modifiedBys;
	}

}
