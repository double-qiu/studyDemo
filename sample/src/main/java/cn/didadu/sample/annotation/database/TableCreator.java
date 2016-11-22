package cn.didadu.sample.annotation.database;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TableCreator {

	public static void main(String[] args) throws Exception{
		
		String className = "cn.didadu.sample.annotation.database.Member";
		
		//通过反射获取类
		Class<?> cl = Class.forName(className);
		
		//获取类上的注解类
		DBTable dbTable = cl.getAnnotation(DBTable.class);
		if(dbTable == null){
			System.out.println("No DBTable annotations in class " + className);
		}
		
		//获取注解的name属性
		String tableName = dbTable.name();
		if(tableName.length() < 1){
			tableName = cl.getName().toUpperCase();
		}
		
		List<String> columnDefs = new ArrayList<String>();
		
		//循环类中申明的字段
		for(Field field : cl.getDeclaredFields()){
			String columnName = null;
			
			//如果字段上没有注解，跳过
			Annotation[] anns = field.getDeclaredAnnotations();
			if(anns.length < 1){
				continue;
			}
			
			//处理Integer类型
			if(anns[0] instanceof SQLInteger){
				SQLInteger sInt = (SQLInteger) anns[0];
				if(sInt.name().length() < 1){
					columnName = field.getName().toUpperCase();
				}else{
					columnName = sInt.name();
				}
				columnDefs.add(columnName + " INT" +getConstraints(sInt.constraitns()));
			}
			
			//处理String类型
			if(anns[0] instanceof SQLString){
				SQLString sString = (SQLString) anns[0];
				
				if(sString.name().length() < 1){
					columnName = field.getName().toUpperCase();
				}else{
					columnName = sString.name();
				}
				columnDefs.add(columnName + " VARCHAR(" + sString.value() + ")" + getConstraints(sString.constraitns()));
			}
		}
		
		StringBuilder createCommand = new StringBuilder("CREATE TABLE " + tableName + "(");
		for(String columnDef : columnDefs){
			createCommand.append("\n    " + columnDef + ",");
		}
		String tableCreate = createCommand.substring(0,createCommand.length() - 1) + ");";
		
		System.out.println(tableCreate);
		
	}
	
	private static String getConstraints(Constraints con){
		String constraints = "";
		
		if(!con.allowNull()){
			constraints += " NOT NULL";
		}
		
		if(con.primaryKey()){
			constraints += " PRIMARY KEY";
		}
		
		if(con.unique()){
			constraints += " UNIQUE";
		}
		
		return constraints;
	}
	
}
