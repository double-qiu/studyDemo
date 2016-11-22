package cn.didadu.sample.arithmetic;

/**
 * 瓶子喝水问题
 * 示例有23瓶水，喝完了之后两个空瓶可以换一瓶水，问一共能喝多少瓶水
 */
public class DrinkProblem {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DrinkProblem.drink(23, 23);
	}

	
	public static void drink(int drink,int empty){
		if(empty >= 2){
			int tempDrink = empty/2;
			int tempEmpty = empty%2;
			
			drink += tempDrink;
			empty = tempEmpty+tempDrink;
			
			DrinkProblem.drink(drink, empty);
		}else{
			System.out.println(drink);
			System.out.println(empty);
		}
	}
	
	
}
