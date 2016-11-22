package cn.didadu.sample.compare;

import java.util.Comparator;

public class AlphDesc implements Comparator<PersonBean> {

	@Override
	public int compare(PersonBean personA, PersonBean personB) {
		// TODO Auto-generated method stub
		 return personA.getName().compareTo(personB.getName()); 
	}

}
