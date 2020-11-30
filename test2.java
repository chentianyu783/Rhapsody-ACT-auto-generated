package test;


import java.awt.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.telelogic.rhapsody.core.*;

public class test2 {
	static IRPApplication app = RhapsodyAppServer.getActiveRhapsodyApplication();

// main method
	public static void main(String[] args) {
		if (app != null)
			manipulateClassData();
	}

	// this method demonstrates some of the things you can do with classes, and their attributes and operations
	private static void manipulateClassData() {
	
		IRPProject prj = app.activeProject();
		
		Map<String,IRPModelElement> theMap = new HashMap();
		IRPCollection allColl = prj.getNestedElementsRecursive();
		System.out.println("共遍历项目中"+allColl.getCount()+"个对象");
		for (int i = 1; i <= allColl.getCount(); i++) {
			IRPModelElement element = (IRPModelElement)allColl.getItem(i);
			theMap.put(element.getMetaClass()+i,  element);
//			System.out.println(element.getMetaClass()+"\t"+element.getName());
			}
		
		Map<String,IRPFlowchart> actMap = new HashMap();
		Set<String> mapKeySet = theMap.keySet();
		int i = 1, j=1, n=1, k=1, m=1;
		for(String strMetaClass : mapKeySet) {
			if (strMetaClass.contains("ActivityDiagram") && strMetaClass.contains("ActivityDiagramGE")==false) {
				actMap.put("act"+i,   (IRPFlowchart) theMap.get(strMetaClass));
				System.out.println("已找到第"+i+"个活动"+"\t"+theMap.get(strMetaClass).getName());
				i++;
			}
		}

		IRPFlowchart rootact = null;

		for(i=1,j=1; i<=actMap.size(); i++) {
			IRPCollection refact =  actMap.get("act"+i).getNestedElementsByMetaClass("ReferenceActivity", 1);
			if(actMap.get("act"+i).getName().contains("root")) {
				rootact = actMap.get("act"+i);
				System.out.println("找到根目录！");
				actMap.get("act"+i).setTagValue(actMap.get("act"+i).getTag("id"), "F0");
				System.out.println("已经为"+actMap.get("act"+i).getName()+"添加id"+"\t"+"F0");
				
			}
		}
		
		IRPObjectModelDiagram ACTtoBDD = (IRPObjectModelDiagram) prj.addObjectModelDiagram("act7");
		
		ACTtoBDD.addNewNodeForElement(rootact, 0, 0,  150, 100);
		
		Map<String,Integer> xposition = new HashMap();
		Map<String,Integer> yposition = new HashMap();
		Map<String,Map<String, IRPFlowchart>> total2 = new HashMap();
		Map<String,Map<String, IRPFlowchart>> total3 = new HashMap();
		Map<String,Map<String, IRPFlowchart>> total4 = new HashMap();
		Map<String,Map<String, IRPFlowchart>> total5 = new HashMap();
		
		Map<String, IRPFlowchart> subacts1 =addsubid(rootact,actMap);
//		total.put("class"+1, subacts1);
		int layoutclass = 2;
//		gg:
		for(int n2 = 1; n2<=subacts1.size(); n2++) {
			IRPFlowchart element = subacts1.get("act"+n2);	
			
			String str = getidnum(element);
			int inn = Integer.parseInt(str);
			System.out.println(element.getName()+"的纵坐标为："+300*(inn-1));
			xposition.put(element.getTag("id").toString(), 250);
			yposition.put(element.getTag("id").toString(), (300*(inn-1)));
			ACTtoBDD.addNewNodeForElement(element, 250, 300*(inn-1), 150, 100);
			
			Map<String, IRPFlowchart> subacts2 = addsubid(element,actMap);
			total2.put("class"+n2, subacts2);
			if(subacts2.size()>0) {
				if(layoutclass<3) {
					layoutclass=3;
				}
				for(int n3=1; n3<=subacts2.size(); n3++) {
					IRPFlowchart element2 = subacts2.get("act"+n3);
					
					String str2 = getidnum(element2);
					int inn2 = Integer.parseInt(str2);
					double nn2 = Double.parseDouble(str2)/10-0.1;
					ACTtoBDD.addNewNodeForElement(element2, 500, 300*(inn2/10-1)+(int)((nn2-inn2/10)*1500), 150, 100);
					System.out.println(element2.getName()+"的纵坐标为："+(300*(inn2/10-1)+(int)((nn2-inn2/10)*1500)));
					xposition.put(element2.getTag("id").toString(), 500);
					yposition.put(element2.getTag("id").toString(), (300*(inn2/10-1)+(int)((nn2-inn2/10)*1500)));
					
					
					Map<String, IRPFlowchart> subacts3 = addsubid(element2,actMap);
					total3.put("class"+n3, subacts3);
					if(subacts3.size()>0) {
						if(layoutclass<4) {
							layoutclass=4;
						}
						for(int n4=1; n4<=subacts3.size(); n4++) {
							IRPFlowchart element3 = subacts3.get("act"+n4);
							
							String str3 = getidnum(element3);
							int inn3 = Integer.parseInt(str3);
							double nn3 = Double.parseDouble(str3)/10-0.1;
							ACTtoBDD.addNewNodeForElement(element3, 750, 
									300*(inn3/100-1)+150*(inn3/10-(inn3/100)*10-1)+(int)((nn3-inn3/10)*1200), 150, 100);
							System.out.println(element3.getName()+"的纵坐标为："+
									(300*(inn3/100-1)+150*(inn3/10-(inn3/100)*10-1)+(int)((nn3-inn3/10)*1200)));
							xposition.put(element3.getTag("id").toString(), 750);
							yposition.put(element3.getTag("id").toString(), 
									(300*(inn3/100-1)+150*(inn3/10-(inn3/100)*10-1)+(int)((nn3-inn3/10)*1200)));
							
							Map<String, IRPFlowchart> subacts4 = addsubid(element3,actMap);
							total4.put("class"+n4, subacts4);
							if(subacts4.size()>0) {
								if(layoutclass<5) {
									layoutclass=5;
								}
								for(int n5=1; n5<=subacts4.size(); n5++) {
									IRPFlowchart element4 = subacts4.get("act"+n5);
									
									String str4 = getidnum(element4);
									int inn4 = Integer.parseInt(str4);
									double nn4 = Double.parseDouble(str4)/10-0.1;
									ACTtoBDD.addNewNodeForElement(element4, 1000, 
											300*(inn4/1000-1)+150*(inn4/100-(inn4/1000)*10-1)+
											120*(inn4/10-(inn4/100)*10-1)+(int)((nn4-inn4/10)*1050), 150, 100);
									System.out.println(element4.getName()+"的纵坐标为："+
											(300*(inn4/1000-1)+150*(inn4/100-11)+120*(inn4/10-111)+(int)((nn4-inn4/10)*1050)));
									xposition.put(element3.getTag("id").toString(), 1000);
									yposition.put(element3.getTag("id").toString(), 
											(300*(inn4/1000-1)+150*(inn4/100-11)+120*(inn4/10-111)+(int)((nn4-inn4/10)*1050)));
									
									Map<String, IRPFlowchart> subacts5 = addsubid(element4,actMap);
									total5.put("class"+n5, subacts5);
								}
							}
						}
					}
				}
			}		
		}
		System.out.println("共需要画："+layoutclass+"层功能图");

		ACTtoBDD.completeRelations(ACTtoBDD.getGraphicalElements(), 0);	
		IRPCollection blocks = ACTtoBDD.getGraphicalElements();
		ACTtoBDD.rearrangePorts(blocks);
		ACTtoBDD.setShowDiagramFrame(1);
		
		
		ACTtoBDD.openDiagram();	
		System.out.println("已绘制对象模型图");	
		
	}
	
	
	
	
	
	
	public int subactnum(IRPFlowchart element) {
		//返回子操作数量
		IRPCollection refact =  element.getNestedElementsByMetaClass("ReferenceActivity", 1);
		return refact.getCount();
	}
	

	
	
	public static  Map<String, IRPFlowchart> addsubid(IRPFlowchart element, Map<String, IRPFlowchart> actMap) {
		Map<String, IRPFlowchart> subacts = new HashMap();
		int n = 1;
		//为每个子操作添加id
		System.out.println("开始添加id");
		IRPCollection refact =  element.getNestedElementsByMetaClass("ReferenceActivity", 1);
		String rootid = element.getTag("id").getValue();
		for(int i=1; i<=refact.getCount(); i++) {

			IRPState refelement = (IRPState) refact.getItem(i);//flowchart or modelelement
			System.out.println("正在匹配第"+i+"个引用功能："+refelement.getName());
			for(int j=1; j<=actMap.size(); j++) {
				IRPFlowchart subelement = (IRPFlowchart) actMap.get("act"+j);
				System.out.println("功能"+subelement.getName());
				if(refelement.getName().contentEquals(subelement.getName())) {
					subacts.put("act"+n, subelement);
					subelement.setTagValue(subelement.getTag("id"), rootid+"."+n);
					System.out.println("已经为："+subelement.getName()+"添加id："+subelement.getTag("id").getValue());
					n++;
					element.addRelationTo(subelement, "", "Association", 
							"1", "sub", "Association", "1", "");		
				}
			}
			
		}
		
		IRPCollection real = element.getNestedElementsByMetaClass("AssociationEnd", 1);
		for(int jj=1; jj<=real.getCount(); jj++) {
			IRPRelation r1 =  (IRPRelation) real.getItem(jj);
			if(r1.getRelationRoleName().contains("sub")==false) {
				r1.setRelationType("Composition");
			}
		}
		
		return subacts;
	}
	
	public static  String getidnum (IRPFlowchart element) {
		double nn = 0;
		
		String str =  element.getTag("id").getValue().toString();
		str=str.trim();
		String str2="";
		if(str != null && !"".equals(str)){
		for(int a=0; a<str.length(); a++){
			if(str.charAt(a)>=48 && str.charAt(a)<=57){
				str2+=str.charAt(a);
				}
			}
		}
//		int inn = Integer.parseInt(str2)/10;
		nn = Double.parseDouble(str2);	
		System.out.println(element.getName()+"的id数字为"+str2);
		return str2;
//		return nn;
	}
	

}
