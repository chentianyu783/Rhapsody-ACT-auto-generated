package test;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.telelogic.rhapsody.core.*;

public class delete {
	static IRPApplication app = RhapsodyAppServer.getActiveRhapsodyApplication();

// main method
	public static void main(String[] args) {
		if (app != null)
			manipulateClassData();
	}

	// this method demonstrates some of the things you can do with classes, and their attributes and operations
	private static void manipulateClassData() {
		
		IRPProject prj = app.activeProject();
		System.out.println("正在删除对象模型图");
		IRPObjectModelDiagram act = (IRPObjectModelDiagram) prj.findNestedElement("act7", "ObjectModelDiagram");
		if(act!=null) {
			act.deleteFromProject();
		}
//		act.deleteFromProject();
		
		Map<String,IRPModelElement> theMap = new HashMap();
		IRPCollection allColl = prj.getNestedElementsRecursive();
		for (int i = 1; i <= allColl.getCount(); i++) {
			IRPModelElement element = (IRPModelElement)allColl.getItem(i);
			theMap.put(element.getMetaClass()+i,  element);
			}
		
		Map<String,IRPFlowchart> actMap = new HashMap();
		Set<String> mapKeySet = theMap.keySet();
		int i = 1, j=1, n=1, k=1, m=1;
		for(String strMetaClass : mapKeySet) {
			if (strMetaClass.contains("ActivityDiagram") && strMetaClass.contains("ActivityDiagramGE")==false) {
				actMap.put("act"+i,   (IRPFlowchart) theMap.get(strMetaClass));
				i++;
			}
		}
		
		for(i=1; i<=actMap.size(); i++) {
			System.out.println("正在删除"+actMap.get("act"+i).getName()+"的多余属性");
			IRPCollection tag = actMap.get("act"+i).getNestedElementsByMetaClass("Tag", 1);
			for(j=1; j<=tag.getCount();j++) {
				IRPModelElement real = (IRPModelElement) tag.getItem(j);
				real.deleteFromProject();
			}
			
			IRPCollection asso = actMap.get("act"+i).getNestedElementsByMetaClass("AssociationEnd", 1);
			if(asso.getCount()!=0) {
				for(j=1; j<=asso.getCount();j++) {
					IRPRelation real = (IRPRelation) asso.getItem(j);
					real.deleteFromProject();
				}
			}

		}	
//		
//		prj.findNestedElement("act5", metaClass)
		System.out.println("清理完毕");
	}

}
