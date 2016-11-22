package cn.didadu.sample.pattern.simplefactory;

/**
 * 简单工厂模式
 * 简单工厂用来生产同一等级结构中的任意产品。（对于增加新的产品，无能为力）
 */
public class FruitGardener {
	public static Fruit factory(String which) {
		if(which.equalsIgnoreCase("apple")){
			return new Apple();
		}else if(which.equalsIgnoreCase("strawberry")){
			return new Strawberry();
		}else if(which.equalsIgnoreCase("grape")){
			return new Grape();
		}
		return null;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		//直接通过factory方法的参数来创建对象
		Fruit fruit = FruitGardener.factory("apple");
		fruit.grow();

		//通过反射获取对象
		Fruit fruitByFlect = FruitGardener.reflectFactory("cn.didadu.sample.pattern.simplefactory.Apple");
		fruitByFlect.grow();
	}

	/**
	 * 通过类全路径名反射构建对象
	 * @param which
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Fruit reflectFactory(String which) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> clazz = Class.forName(which);
		
		Object instance = clazz.newInstance();
		
		return (Fruit) instance;
	}
}
