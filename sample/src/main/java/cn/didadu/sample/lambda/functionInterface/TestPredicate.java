package cn.didadu.sample.lambda.functionInterface;


/**
 * Created by admin on 15/10/8.
 */
public class TestPredicate {

    public static void main(String[] args) {

        Predicate<Integer> atLeast5 = x -> x > 5;

        System.out.println(atLeast5.test(6));

    }

}
