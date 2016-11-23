package cn.didadu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * Created by zhangjing on 16-11-4.
 */

@RunWith(SpringRunner.class)
@SpringBootTest()
public class ShopTest {

    @Autowired
    private ShopService shopService;

    @Test
    public void test() throws Exception {
        String token = UUID.randomUUID().toString();
        //String token = "59b3d577-d614-44a1-b42a-4766eeaf8e72";
       shopService.updateToken(token, "zhangjing", "item1");
       shopService.updateToken(token, "zhangjing", "item2");
       shopService.updateToken(token, "zhangjing", "item3");
       shopService.updateToken(token, "zhangjing", "item4");
       shopService.updateToken(token, "zhangjing", "item5");
       shopService.updateToken(token, "zhangjing", null);
       System.out.println(shopService.checkToken(token));

       shopService.updateToken(token, "zhangjing", "item8");
       shopService.addToCart(token, "item1" , -1);

        shopService.cacheRequest("www.didadu.cn",request -> "content for" + request);
        shopService.cacheData();
    }
}
