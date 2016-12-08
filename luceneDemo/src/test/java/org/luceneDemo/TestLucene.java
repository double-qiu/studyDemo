package org.luceneDemo;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Before;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class TestLucene {

    @Before
    public void setUp() throws Exception {
        
    }

    /**
     * 创建数据文档
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        
        //构建文档
        Document document = new Document();
        
        //设置文档字段
        //添加一个Long字段：字段名，值，是否存储
        document.add(new LongField("id", 12L, Store.YES));
        //TextField:做索引并且分词 
        //document.add(new TextField("title", "【华为官方新品发布】Huawei/华为 麦芒5 全网通4GB+64GB", Store.YES));
        document.add(new TextField("title", "vivo X7全网通4G自拍美颜拍照智能手机指纹超薄大屏双卡vivox7", Store.YES));
        document.add(new LongField("price", 2999L, Store.YES));
        //TextField:做索引并且分词
        document.add(new StringField("image", "http://image.taotao.com/images/2016/07/25/2016072503024233808430.jpg", Store.YES));
        document.add(new IntField("status", 1, Store.YES));
        
        //索引位置
        Directory directory = FSDirectory.open(new File("index"));
        
        //定义分词器(标准分词器)
        Analyzer analyzer = new StandardAnalyzer();
        //定义索引配置
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);
        //先删除原有数据在写入  默认是APPEND追加
        indexWriterConfig.setOpenMode(OpenMode.APPEND);
        //定义索引对象
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        
        //将文档写入索引库中
        indexWriter.addDocument(document);
        
        //关闭索引
        indexWriter.close();
    }
    
    
    /**
     * 索引搜索
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        //定义索引位置
        Directory directory = FSDirectory.open(new File("index"));
        //构造索引搜索器
        IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(directory));
        //构造查询条件，词条搜索
        Query query = new TermQuery(new Term("title", "颜"));        
        //执行搜索,返回命中的数据
        TopDocs search = indexSearcher.search(query, 10);
        
        System.out.println("命中数据条数 ："+search.totalHits);
        
        ScoreDoc[] scoreDocs = search.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            System.out.println("得分 ："+scoreDoc.score);
            //得到文档id
            Integer docId = scoreDoc.doc;
            Document doc = indexSearcher.doc(docId);
            
            System.out.println("id:"+doc.get("id"));
            System.out.println("标题:"+doc.get("title"));
            System.out.println("价格:"+doc.get("price"));
            System.out.println("图片:"+doc.get("image"));
            System.out.println("状态:"+doc.get("status"));
        }
    }
    
    
    /**
     * 分词搜索
     * @throws Exception
     */
    @Test
    public void testSearch() throws Exception {
        //定义索引位置
        Directory directory = FSDirectory.open(new File("index"));
        //构造索引搜索器
        IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(directory));
        
        //定义分词器(标准分词器)
        Analyzer analyzer = new StandardAnalyzer();
        //定义查询分析器
        QueryParser queryParser = new QueryParser("title", analyzer);
        //构造查询对象，分词查询
        Query query = queryParser.parse("智能手机");
        
        //执行搜索,返回命中的数据
        TopDocs search = indexSearcher.search(query, 10);
        
        System.out.println("命中数据条数 ："+search.totalHits);
        
        ScoreDoc[] scoreDocs = search.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            System.out.println("得分 ："+scoreDoc.score);
            //得到文档id
            Integer docId = scoreDoc.doc;
            Document doc = indexSearcher.doc(docId);
            
            System.out.println("id:"+doc.get("id"));
            System.out.println("标题:"+doc.get("title"));
            System.out.println("价格:"+doc.get("price"));
            System.out.println("图片:"+doc.get("image"));
            System.out.println("状态:"+doc.get("status"));
        }
    }
 
    
    /**
     * IK分词器
     * @throws Exception
     */
    @Test
    public void testIK() throws Exception {
        
        //构建文档
        Document document = new Document();
        
        //设置文档字段
        //添加一个Long字段：字段名，值，是否存储
        document.add(new LongField("id", 12L, Store.YES));
        //TextField:做索引并且分词 //// 
        //document.add(new TextField("title", "【华为官方新品发布】Huawei/华为 麦芒5 全网通4GB+64GB", Store.YES));
        document.add(new TextField("title", "中通快递淘淘商城  vivo X7全网通4G自拍美颜拍照智能手机指纹超薄大屏双卡vivox7", Store.YES));
        document.add(new LongField("price", 2999L, Store.YES));
        //TextField:做索引并且分词
        document.add(new StringField("image", "http://image.taotao.com/images/2016/07/25/2016072503024233808430.jpg", Store.YES));
        document.add(new IntField("status", 1, Store.YES));
        
        //索引位置
        Directory directory = FSDirectory.open(new File("index"));
        
        //定义分词器(标准分词器)
        Analyzer analyzer = new IKAnalyzer();
        //定义索引配置
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);
        //先删除原有数据在写入  默认是APPEND追加
        indexWriterConfig.setOpenMode(OpenMode.CREATE);
        //定义索引对象
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        
        //将文档写入索引库中
        indexWriter.addDocument(document);
        
        //关闭索引
        indexWriter.close();
    }
    
    /**
     * IK分词搜索
     * @throws Exception
     */
    @Test
    public void testSearchIK() throws Exception {
        //定义索引位置
        Directory directory = FSDirectory.open(new File("index"));
        //构造索引搜索器
        IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(directory));
        
        //定义分词器(标准分词器)
        Analyzer analyzer = new IKAnalyzer();
        //定义查询分析器
        QueryParser queryParser = new QueryParser("title", analyzer);
        //构造查询对象，分词查询
        Query query = queryParser.parse("淘淘");
        
        //执行搜索,返回命中的数据
        TopDocs search = indexSearcher.search(query, 10);
        
        System.out.println("命中数据条数 ："+search.totalHits);
        
        ScoreDoc[] scoreDocs = search.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            System.out.println("得分 ："+scoreDoc.score);
            //得到文档id
            Integer docId = scoreDoc.doc;
            Document doc = indexSearcher.doc(docId);
            
            System.out.println("id:"+doc.get("id"));
            System.out.println("标题:"+doc.get("title"));
            System.out.println("价格:"+doc.get("price"));
            System.out.println("图片:"+doc.get("image"));
            System.out.println("状态:"+doc.get("status"));
        }
    }
}
