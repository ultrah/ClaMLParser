package de.ebertp.dai.claml;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import de.ebertp.dai.claml.model.ClaMLClass;
import de.ebertp.dai.claml.model.ClaMLFragment;
import de.ebertp.dai.claml.model.ClaMLLabel;
import de.ebertp.dai.claml.model.ClaMLMeta;
import de.ebertp.dai.claml.model.ClaMLModifiedBy;
import de.ebertp.dai.claml.model.ClaMLModifierClass;
import de.ebertp.dai.claml.model.ClaMLRoot;
import de.ebertp.dai.claml.model.ClaMLRubric;

/**
 * @author Philipp Ebert
 * 
 *  Parses the object notation back to a tree based xml structure
 *
 */
public class CLaMLXmlCreator {
	
	private boolean reducedOuput;

	public CLaMLXmlCreator(boolean reducedOutput) {
		this.reducedOuput = reducedOutput;
	}
	
	public Document toDocument(ClaMLRoot claMLRoot){
		Document document = DocumentHelper.createDocument();
		Element docRoot = document.addElement("ClaMLTree");
		
		for(ClaMLMeta meta: claMLRoot.getMetaData()){
			addMeta(meta, claMLRoot, docRoot);
		}
		
		docRoot.addElement("Identifier")
			.addAttribute("authority", claMLRoot.getIdentifier().getAuthority())
			.addAttribute("uid", claMLRoot.getIdentifier().getUid());
		
		docRoot.addElement("Title")
			.addAttribute("date", claMLRoot.getTitle().getDate())
			.addAttribute("name", claMLRoot.getTitle().getName())
			.addAttribute("version", claMLRoot.getTitle().getVersion());
		
		for(ClaMLClass child: claMLRoot.getChildren().values()){
			addClassChild(child, docRoot);
		}
		
		
		return document;
	}

	private void modifyContent(ClaMLClass child) {
		ClaMLModifiedBy modifiedBy = child.getModifiedBy().get(0);
		
		if(modifiedBy.getValidFor().size()==0){
			for(ClaMLModifierClass modifierClass : modifiedBy.getModifier().getChildren().values()){
				child.addChild(convertModifierClassToClass(child, modifierClass));
			}
		}
		else {
			for(String code : modifiedBy.getValidFor()){
				child.addChild(convertModifierClassToClass(child, modifiedBy.getModifier().getChildren().get(code)));
			}
		}
	}
	


	private ClaMLClass convertModifierClassToClass(ClaMLClass parent, ClaMLModifierClass modifierClass){
		ClaMLClass claMLClass = new ClaMLClass(parent.getCode()+modifierClass.getCode(), "category");
		claMLClass.setParent(parent);
		
		claMLClass.setMetaData(modifierClass.getMetaData());
		claMLClass.setRubrics(modifierClass.getRubrics());
		
		if(parent.getModifiedBy().size()>=2){
			//we have another modifier, add subclasses to existing modifier if they are not excluded
			//
			//yeah, claml sucks.
			addChildFromSecondModifier(modifierClass.getCode(), claMLClass, parent.getModifiedBy().get(1));
			
		}
		return claMLClass;
	}
	
	private void addChildFromSecondModifier(String parentCode, ClaMLClass firstModifiedClass, ClaMLModifiedBy secondModifiedBy){
//		System.out.println(firstModifiedClass.getCode()+" - Analyzing "+parentCode+"/"+secondModifiedBy.getModifier().getCode()+" -----------------------------------");
		
		for(ClaMLModifierClass modifierClassChild : secondModifiedBy.getModifier().getChildren().values()){
//			System.out.println(firstModifiedClass.getCode()+" - Analyzing SubClass "+modifierClassChild.getCode());
			
			boolean includeSubClass = true;	
			
			for(ClaMLMeta meta : modifierClassChild.getMetaData()){
				if(meta.getName().equals("excludeOnPrecedingModifier")){
//					System.out.println(firstModifiedClass.getCode()+" - SubClass "
//							+modifierClassChild.getCode()+": Matching exlusion"+meta.getValue()+" against "+parentCode);
					
					if (meta.getValue().matches(".*"+"\\"+parentCode+".*")) {
//						System.out.println(firstModifiedClass.getCode()+" - SubClass "
//								+modifierClassChild.getCode()+": Matched "+meta.getValue()+" against "+parentCode+", excluding "+modifierClassChild.getCode());
						includeSubClass = false;
						break;
					}
				}
			}
			
			if(includeSubClass){
//				System.out.println(firstModifiedClass.getCode()+" - SubClass "+modifierClassChild.getCode()+": Adding this child!");
				firstModifiedClass.addChild(
						convertModifierClassToClass(firstModifiedClass, modifierClassChild));
			}
			else {
//				System.out.println(firstModifiedClass.getCode()+" - SubClass "
//						+modifierClassChild.getCode()+": Excluding Child!");
			}
		}
	}

	private void addClassChild(ClaMLClass claMLClass, Element rootElement) {
		if(claMLClass.getModifiedBy().size()!=0){
			modifyContent(claMLClass);
		}
		
		Element element = rootElement.addElement("Class")
			.addAttribute("code", claMLClass.getCode())
			.addAttribute("kind", claMLClass.getKind())
			.addAttribute("parent", (claMLClass.getParent() == null)  ? "" : claMLClass.getParent().getCode());
		
		if(!reducedOuput){
			for(ClaMLMeta meta: claMLClass.getMetaData()){
				addMeta(meta, claMLClass, element);
			}	
		}
		
		for(ClaMLRubric rubric : claMLClass.getRubrics()){
			addRubric(rubric, element);
		}
		
		for(ClaMLClass child : claMLClass.getChildren().values()){
			addClassChild(child, element);
		}
		
	}
	
	private void addRubric(ClaMLRubric rubric, Element classElement) {
		Element element = classElement.addElement("Rubric")
					.addAttribute("id", rubric.getId())
					.addAttribute("kind", rubric.getKind());
		
		for(ClaMLLabel label : rubric.getLabels()){
			addLabel(label, element);
		}
					
		
	}

	private void addLabel(ClaMLLabel label, Element rootElement) {
		Element element = rootElement.addElement("Label")
								.addText(label.getValue());
		
		if(!reducedOuput){
			for(ClaMLFragment fragment : label.getFragments()){
				element.addElement("Fragment")
					.addAttribute("type", fragment.getType())
					.addText(fragment.getContent());
			}
			
			for(String paraText : label.getParas()){
				element.addElement("Para")
						.addText(paraText);
			}	
		}
	}

	private void addMeta(ClaMLMeta meta, ClaMLClass claMLClass, Element root) {
		root.addElement("Meta")
			.addAttribute("name", meta.getName())
			.addAttribute("value", meta.getValue());
	}

}
