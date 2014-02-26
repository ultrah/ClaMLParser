package de.ebertp.dai.claml;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import de.ebertp.dai.claml.model.ClaMLClass;
import de.ebertp.dai.claml.model.ClaMLFragment;
import de.ebertp.dai.claml.model.ClaMLIdentifier;
import de.ebertp.dai.claml.model.ClaMLLabel;
import de.ebertp.dai.claml.model.ClaMLModifiedBy;
import de.ebertp.dai.claml.model.ClaMLModifierClass;
import de.ebertp.dai.claml.model.ClaMLRoot;
import de.ebertp.dai.claml.model.ClaMLRubric;
import de.ebertp.dai.claml.model.ClaMLTitle;

/**
 * @author Philipp Ebert Parses the ClaML DTD to an object model
 * 
 */
public class ClaMLParser {

	private ClaMLRoot claMLRoot;

	public ClaMLRoot parseClaMLFromXML(File input) throws DocumentException {
		Element root = ParserUtils.readXmlFromFile(input).getRootElement();
		return parseClaML(root);
	}

	public ClaMLRoot parseClaMLFromXML(InputStream is) throws DocumentException {
		Element root = ParserUtils.readXmlFromFile(is).getRootElement();
		return parseClaML(root);
	}

	protected ClaMLRoot parseClaML(Element root) {
		// ClaMLTree is added here so we also can parse ClaMLTree
		if (root.getQualifiedName() == "ClaML" || root.getQualifiedName() == "ClaMLTree") {
			return parseClaMLRoot(root);
		}
		return null;
	}

	private ClaMLRoot parseClaMLRoot(Element root) {
		System.out.println(this.getClass().getName() + " : Parsing from XML");

		claMLRoot = new ClaMLRoot("", "");
		claMLRoot.setVersion(root.attributeValue("version"));

		for (Iterator<Element> i = root.elementIterator(); i.hasNext();) {
			Element e = i.next();
			if (e.getQualifiedName() == "Meta") {
				claMLRoot.addMetaData(e.attributeValue("name"), e.attributeValue("value"));
			} else if (e.getQualifiedName() == "Identifier") {
				claMLRoot.setIdentifier(new ClaMLIdentifier(e.attributeValue("authority"), e.attributeValue("uid")));
			} else if (e.getQualifiedName() == "Title") {
				claMLRoot.setTitle(new ClaMLTitle(e.attributeValue("date"), e.attributeValue("name"), e.attributeValue("version")));
			} else if (e.getQualifiedName() == "Modifier") {
				claMLRoot.addModifier(parseClaMLModifier(e));
			} else if (e.getQualifiedName() == "ModifierClass") {
				insertModifier(claMLRoot, parseClaMLModifier(e));
			} else if (e.getQualifiedName() == "Class") {
				ClaMLClass claMLClass = parseClaMLClass(e);

				// if we can not find a place to put this child, add it at the
				// root node
				if (!insertChild(claMLRoot, claMLClass)) {
					addChild(claMLRoot, claMLClass);
					// claMLRoot.addChild(claMLClass);
				}
			}

		}
		System.out.println(this.getClass().getName() + " : Parsing finished");
		return claMLRoot;
	}

	private ClaMLRubric parseRubric(Element root) {
		ClaMLRubric rubric = new ClaMLRubric(root.attributeValue("id"), root.attributeValue("kind"));

		for (Iterator<Element> i = root.elementIterator(); i.hasNext();) {
			Element e = i.next();
			if (e.getQualifiedName() == "Label") {
				rubric.addLabel(parseLabel(e));
			}
		}
		return rubric;
	}

	private ClaMLLabel parseLabel(Element root) {
		ClaMLLabel label = new ClaMLLabel(root.getText());

		for (Iterator<Element> i = root.elementIterator(); i.hasNext();) {
			Element e = i.next();

			if (e.getQualifiedName() == "Para") {
				label.addPara(e.getText());
			} else if (e.getQualifiedName() == "Fragment") {
				label.addFragment(new ClaMLFragment(e.attributeValue("type"), e.getText()));
			}
		}
		return label;
	}

	private void insertModifier(ClaMLRoot root, ClaMLModifierClass child) {

		if (root.getModifiers().containsKey(child.getParent().getCode())) {
			ClaMLModifierClass parent = root.getModifiers().get(child.getParent().getCode());

			child.setParent(parent);
			// addChild(parent, child);
			parent.addChild(child);
		}

	}

	private boolean insertChild(ClaMLClass root, ClaMLClass child) {
		if (child.getParent() == null) {
			// this child has no parent. its very sad.
			return false;
		}

		// if the parent has a child, that is a superclass of the child we want
		// to insert --> add this child to the parents child.
		else if (root.getChildren().containsKey(child.getParent().getCode())) {
			ClaMLClass parent = root.getChildren().get(child.getParent().getCode());

			addChild(parent, child);
			// parent.addChild(child);
			return true;
		}

		// if we couldn't find parent on this layer, recursively search inner
		// layers.
		else {
			for (ClaMLClass innerChild : root.getChildren().values()) {
				if (insertChild(innerChild, child)) {
					return true;
				}
			}
		}

		// well, at least we tried
		return false;
	}

	private ClaMLClass parseClaMLClass(Element root) {
		ClaMLClass claMLClass = new ClaMLClass(root.attributeValue("code"), root.attributeValue("kind"));

		for (Iterator<Element> i = root.elementIterator(); i.hasNext();) {
			Element e = i.next();
			if (e.getQualifiedName() == "SuperClass") {
				claMLClass.setParent(findClass(claMLRoot, e.attributeValue("code")));
				// claMLClass.setParent(new ClaMLClass(e.attributeValue("code"),
				// findSuper));
			}
			// Class is added here so we also can parse ClaMLTree
			else if (e.getQualifiedName() == "SubClass " || e.getQualifiedName() == "Class") {
				claMLClass.addChild(parseClaMLClass(e));
			} else if (e.getQualifiedName() == "Rubric") {
				claMLClass.addRubric(parseRubric(e));
			} else if (e.getQualifiedName() == "Meta") {
				claMLClass.addMetaData(e.attributeValue("name"), e.attributeValue("value"));
			} else if (e.getQualifiedName() == "ModifiedBy") {
				claMLClass.addModifiedBy(parseModifiedBy(e));
			}
		}
		return claMLClass;
	}

	private ClaMLModifiedBy parseModifiedBy(Element root) {
		ClaMLModifiedBy modifiedBy = new ClaMLModifiedBy(claMLRoot.getModifiers().get(root.attributeValue("code")));

		for (Iterator<Element> i = root.elementIterator(); i.hasNext();) {
			Element e = i.next();
			if (e.getQualifiedName() == "ValidModifierClass") {
				modifiedBy.addModifier(e.attributeValue("code"));
			}
		}

		return modifiedBy;
	}

	private ClaMLModifierClass parseClaMLModifier(Element root) {
		ClaMLModifierClass claMLModifier = new ClaMLModifierClass(root.attributeValue("code"));

		for (Iterator<Element> i = root.elementIterator(); i.hasNext();) {
			Element e = i.next();
			if (e.getQualifiedName() == "SuperClass") {
				claMLModifier.setParent(findModifier(claMLRoot, e.attributeValue("code")));
			} else if (e.getQualifiedName() == "SubClass") {
				// if this document is wellformed, we do not need to parse this
				// here
			} else if (e.getQualifiedName() == "Rubric") {
				claMLModifier.addRubric(parseRubric(e));
			} else if (e.getQualifiedName() == "Meta") {
				claMLModifier.addMetaData(e.attributeValue("name"), e.attributeValue("value"));
			}
		}
		return claMLModifier;
	}

	private ClaMLClass findClass(ClaMLClass root, String code) {
		if (root.getChildren().containsKey(code)) {
			return root.getChildren().get(code);
		} else {
			ClaMLClass foundIt = null;

			for (ClaMLClass innerChild : root.getChildren().values()) {
				foundIt = findClass(innerChild, code);
				if (foundIt != null) {
					return foundIt;
				}
			}
		}
		return null;
	}

	private ClaMLModifierClass findModifier(ClaMLRoot root, String code) {
		// only one layer according to specification
		if (root.getModifiers().containsKey(code)) {
			return root.getModifiers().get(code);
		}
		return null;
	}

	protected void addSubClass() {

	}

	protected void addChild(ClaMLClass parent, ClaMLClass child) {
		child.setParent(parent);
		parent.addChild(child);
	}

}
