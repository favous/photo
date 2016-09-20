//package com.cloudsea.photo.utils.bizutils;
//
//import java.util.List;
//import java.util.Set;
//
//import com.cloudsea.sys.data.enums.authority.ViewResourceType;
//import com.cloudsea.sys.entity.Resource;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
///**
// * @author zhangxiaorong
// * 2014-1-26
// */
//public class ResourceConvert {
//
//	
//	/*
//	 * 用资源数组来创建JSONArray对象
//	 * 要求：Resource实体类配置时，父子资源关系必须配有一对多映射
//	 */
//	public static JSONArray createJSONArrayByResourceSet(Set<Resource> resourceSet) {
//		
//		JSONArray jsonAarry = new JSONArray();
//		for(Resource res:resourceSet){
//			
//			if (res != null){
//				JSONObject obj = new JSONObject();
//                 if(res.getResourceType()==ViewResourceType.MENU.getKey()){//菜单资源，多级
//     				JSONObject attribute = new JSONObject();
//     				attribute.put("url", res.getURL());
//     				String resourceName = res.getResourceName();
//     				obj.put("id", res.getId());
//     				obj.put("text", resourceName);
//     				obj.put("attributes",attribute);
//                 }else{//按钮资源，固定两级
//                	 
//                 }			
//				
//								
//				if (res.getChildren() != null && res.getChildren().size() != 0)
//					obj.put("children", createJSONArrayByResourceSet(res.getChildren()));
//				
//				jsonAarry.add(obj);
//			}
//		}
//		
//		return jsonAarry;
//		
//	}
//	
//	
//	/*
//	 * 用资源数组来创建JSONArray对象
//	 * 要求：Resource实体类配置时，父子资源关系必须配有一对多映射
//	 */
//	public static JSONArray createJSONArrayWithFilter(Set<Resource> resourceSet, List<Resource> list){
//
//		JSONArray jsonAarry = new JSONArray();
//		
//		for(Resource res: resourceSet){
//			for(Resource resource: list){			
//				if (res != null && res.getId() == resource.getId()&&res.getResourceType()==ViewResourceType.MENU.getKey()){
//					// if(res.getResourceType()==ResourceType.MENU.getKey()){//菜单资源，多级
//					JSONObject obj = new JSONObject();
//					JSONObject attribute = new JSONObject();
//					attribute.put("url", res.getURL());
//					String resourceName = res.getResourceName();
//					obj.put("id", res.getId());
//					obj.put("text", resourceName);
//					obj.put("attributes",attribute);
//									
//					if (res.getChildren() != null && res.getChildren().size() != 0)
//						obj.put("children", createJSONArrayWithFilter(res.getChildren(), list));
//					
//					jsonAarry.add(obj);
//				 }
//				
//			}
//		}
//		
//		return jsonAarry;		
//	}
//
//}
