package cn.didadu.sample.lambda.functionInterface;

/**
 * Created by admin on 15/10/8.
 */

/**
 * 建议每一个函数接口都添加@FunctionalInterface注解
 * 该注解有助于区分其他接口,并且javac会检测是否符合函数接口标准
 * @param <T>
 */
@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);
}
