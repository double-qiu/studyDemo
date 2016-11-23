/**  
 * Project Name:article  
 * File Name:ArticleTest.java  
 * Package Name:cn.didadu  
 * Date:2016年11月23日上午10:38:32  
 * Copyright (c) 2016, LoveBeanTec All Rights Reserved.  
 */
package cn.didadu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ArticleTest  
 * redis测试
 * @author DOUBLE
 * @version
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class ArticleTest {
    @Autowired
    private ArticleService articleService;

    @Test
    public void test() throws Exception {
        System.out.println(articleService.postArticle("zhangjing","first article","www.baidu.com"));
        System.out.println(articleService.postArticle("maxiaoxia","second article","www.google.com"));
        System.out.println(articleService.postArticle("dada","third article","www.didazu.cn"));
        articleService.voteArticle("maxiaoxia", "article:2");
        List<Map<String,String>> data = articleService.getArticles(1, "score:");
        for (Map<String, String> map : data) {
        	System.out.println(map.get("id")+"--->"+map.get("votes"));
		}
        articleService.addGroups("myGroup", "article:1");
        articleService.addGroups("myGroup", "article:2");
        articleService.getGroupArticles("group:myGroup");
    }

}
