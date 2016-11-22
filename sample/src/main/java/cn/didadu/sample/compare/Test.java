package cn.didadu.sample.compare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<PersonBean> list = new ArrayList<PersonBean>();
		
		list.add(new PersonBean(20, "Zaker"));
		list.add(new PersonBean(30, "Mary"));
		list.add(new PersonBean(20, "Ada"));
		list.add(new PersonBean(40, "Walton"));
		list.add(new PersonBean(61, "Peter"));
		list.add(new PersonBean(20, "Bush"));
		
		//通过Comparable排序
		Collections.sort(list);
		for(PersonBean person : list){
			System.out.println(person.getAge() + "---" + person.getName());
		}
		
		System.out.println("---------------------------------------");
		
		//通过Comparator排序
		AlphDesc asc = new AlphDesc();
		Collections.sort(list,asc);
		for(PersonBean person : list){
			System.out.println(person.getAge() + "---" + person.getName());
		}

	}
	
	
	

}
