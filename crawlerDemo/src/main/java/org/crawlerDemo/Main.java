package org.crawlerDemo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * 1、抓取总页数 2、循环页数抓取数据 3、分析页面获取数据封装Item对象
 * @Description: 爬虫：抓取京东手机商品数据
 * @author DOUBLE
 * @date 2016年12月8日10:45:22
 * @version
 */
public class Main {

    // 获取手机URL地址的数据
    //public static final String URL = "http://list.jd.com/list.html?cat=9987,653,655&page={page}";
	 // 获取平板电脑URL地址的数据
	//public static final String URL = "http://list.jd.com/list.html?cat=670,671,2694&page={page}";
	// 获取显示器URL地址的数据
	public static final String URL = "http://list.jd.com/list.html?cat=670,677,688&page={page}";
	
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    public static void main(String[] args) throws Exception {
        start();
    }

    /**
     *  start:循环获取页面
     *  @return_type:void
     *  @author DOUBLE
     *  @throws Exception
     */
    public static void start() throws Exception {
        Integer totalPage = getTotalPage();
        for (int i = 1; i <= totalPage; i++) {
            System.out.println("当前执行" + i + "/" + totalPage + "页");
            String url = StringUtils.replace(URL, "{page}", i + "");
            doStart(url);
        }
    }

    /**
     *  doStart:具体抓取商品数据逻辑
     *  @return_type:void
     *  @author DOUBLE
     *  @param url
     *  @throws Exception
     */
    private static void doStart(String url) throws Exception {
        System.out.println(url);
        // 获取源代码
        String html = doGET(url);
        // 解析页面
        Document document = Jsoup.parse(html);

        Elements select = document.select("#plist li.gl-item .gl-i-wrap");
        Map<String, Item> map = new HashMap<String, Item>();
        for (Element li : select) {
            String id = li.attr("data-sku");
            String name = li.select(".p-name").text();
            String image = li.select(".p-img img").attr("src");
            String image2 = li.select(".p-img img").attr("data-lazy-img");
            
            StringBuilder sb = new StringBuilder(image);
            sb.append(image2);
            
            Item item = new Item();
            item.setId(id);
            item.setTitle(name);
            item.setImage(sb.toString());
            
            map.put(item.getId(), item);
        }
        
        List<String> strIds = new ArrayList<String>();
        for (String id: map.keySet()) {
            strIds.add("J_"+id);
        }
        
        // 获取商品的价格
       /*System.out.println(strIds);
       String priceUrl = "http://p.3.cn/prices/mgets?type=1&area=1_72_2799_0&skuIds="+StringUtils.join(strIds,"%2C");
       System.out.println(priceUrl);
        
        String jsonData = doGET(priceUrl);
        System.out.println(jsonData);
        
        ArrayNode arrayNode = (ArrayNode) MAPPER.readTree(jsonData);
        for (JsonNode jsonNode : arrayNode) {
            Long id = Long.valueOf(StringUtils.substringAfter(jsonNode.get("id").asText(), "_"));
            Long price = jsonNode.get("p").asLong();
            //回填item对象中
            map.get(id).setPrice(price);
        }*/
        
        fileUpload(map);
        /*for (Item item : map.values()) {
            System.out.println(item);
        }*/
    }

    /**
     *  fileUpload:图片的下载
     *  @return_type:void
     *  @author DOUBLE
     *  @param map
     *  @throws Exception
     */
    public static void fileUpload(Map<String, Item> map) throws Exception{
        System.out.println("正在下载....");
        for (Map.Entry<String, Item> entry : map.entrySet()) {
            Item item = entry.getValue();
            String imgUrl = item.getImage();
            HttpGet httpGet = new HttpGet("http:"+imgUrl);
            CloseableHttpResponse response = HttpClients.createDefault().execute(httpGet);
            FileUtils.writeByteArrayToFile(new File("E:\\crawler\\jd-monitor\\"+System.currentTimeMillis()+".jpg"), IOUtils.toByteArray(response.getEntity().getContent()));
        }
        System.out.println("下载完成!");
        
    }
    
    
    
    /**
     *  getTotalPage:获取总页数
     *  @return_type:Integer
     *  @author DOUBLE
     *  @return
     *  @throws Exception
     */
    public static Integer getTotalPage() throws Exception {
        // 给定入口url
        String url = StringUtils.replace(URL, "{page}", "1");
        // 获取源代码
        String html = doGET(url);
        // 解析页面
        Document document = Jsoup.parse(html);
        String str = document.select("#J_topPage .fp-text").text();
        Integer totalPage = Integer.valueOf(StringUtils.substringAfter(str, "/"));
        return totalPage;
    }

    /**
     *  doGET: Httpclient发送get请求
     *  @return_type:String
     *  @author DOUBLE
     *  @param url
     *  @return
     *  @throws Exception
     */
    public static String doGET(String url) throws Exception {

        // 创建Httpclient对象,相当于打开浏览器
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 创建http GET请求,相当于在URL地址栏输入连接
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = null;
        try {
            // 执行请求,相当于输入回车,服务器给我们响应
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200.判断响应是否响应正常
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");

            }
        } finally {
            if (response != null) {
                response.close();
            }
            // 相当于关闭浏览器
             httpclient.close();
        }
        return null;
    }

}
