package cn.didadu.sample.childCloneFather;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class BasePojo {
    
    protected void fatherToChild (Object father,Object child)throws Exception{  
        if(!(child.getClass().getSuperclass()==father.getClass())){  
            throw new Exception("child不是father的子类");  
        }  
        Class<?> fatherClass= father.getClass();  
        Field ff[]= fatherClass.getDeclaredFields();  
        for(int i=0;i<ff.length;i++){  
            ff[i].setAccessible(true);
            Field f=ff[i];//取出每一个属性，如deleteDate  
            Method m = fatherClass.getMethod("get"+upperHeadChar(f.getName()));
            Object obj=m.invoke(father);//取出属性值               
            f.set(child,obj);  
        }  
    }  
     
     private String upperHeadChar(String in){  
        String head=in.substring(0,1);  
        String out=head.toUpperCase()+in.substring(1,in.length());  
        return out;  
    }  

}
