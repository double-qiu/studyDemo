package cn.didadu.sample.thread.forkjoin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjing on 2015/9/10.
 */
public class ProductListGenerator {

    public List<Product> generate(int size) {
        List<Product> list = new ArrayList<Product>();
        for(int i = 0 ; i < size; i++) {
            Product product = new Product();
            product.setName("Product" + i);
            product.setPrice(10);
            list.add(product);
        }
        return list;
    }
}
