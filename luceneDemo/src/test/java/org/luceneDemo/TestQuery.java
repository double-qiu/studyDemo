package org.luceneDemo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

public class TestQuery {
    
    
    /**
     * 布尔搜索
     * MUST 必须包含
     * MUST_NOT 不能包含
     * SHOULD 或
     * @throws Exception
     */
    @Test
    public void testBooleanQuery () throws Exception {
        BooleanQuery query = new BooleanQuery();
        
        //必须包含
        Query query1 = NumericRangeQuery.newLongRange("id", 10L, 30L, true, true);
        query.add(query1, Occur.MUST);
        //不能包含
        Query query2 = new TermQuery(new Term("title", "颜"));
        query.add(query2, Occur.MUST_NOT);
        
        search(query);
    }
    
    
    /**
     * 相似度搜索
     * @throws Exception
     */
    @Test
    public void testFuzzyQuery() throws Exception {
        Query query = new FuzzyQuery(new Term("title","viva"));
        //Query query = new FuzzyQuery(new Term("title","vree"),2);
        search(query);
    }
    
    /**
     * 模糊搜索
     * @throws Exception
     */
    @Test
    public void testWildcardQuery () throws Exception {
        //  ?代表1个任意字符     *代表0或多个任意字符
        Query query = new WildcardQuery(new Term("title", "vi*"));
        search(query);
        
    }
    
    /**
     * 匹配全部
     * @throws Exception
     */
    @Test
    public void testMatchAllDocsQuery() throws Exception{
        Query query = new MatchAllDocsQuery();
        search(query);
    }
 
    /**
     * 范围搜索
     * @throws Exception
     */
    @Test
    public void testNumericRangeQuery () throws Exception {
        //设置查询字段、最小值、最大值、最小值是否包含边界，最大值是否包含边界
        Query query = NumericRangeQuery.newLongRange("id", 10L, 30L, true, true);
        search(query);
    }

    /**
     * 词条查询
     * @throws Exception
     */
    @Test
    public void testTermQuery() throws Exception {
        Query query = new TermQuery(new Term("title", "美"));
        search(query);
    }
    
    /**
     * 通用的查询逻辑
     * @param query
     * @throws Exception
     */
    public void search(Query query) throws Exception{
        //定义索引位置
          Directory directory = FSDirectory.open(new File("index"));
          //构造索引搜索器
          IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(directory));

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
     * 一次性写一百条数据到索引库中
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
         
        List<Document> documents = new ArrayList<Document>();
        for (int i = 0; i < 100; i++) {
          //构建文档
            Document document = new Document();
            
            //设置文档字段
            //添加一个Long字段：字段名，值，是否存储
            document.add(new LongField("id", i+1, Store.YES));
            //TextField:做索引并且分词 //// 
            //document.add(new TextField("title", "【华为官方新品发布】Huawei/华为 麦芒5 全网通4GB+64GB", Store.YES));
            document.add(new TextField("title", "vivo X7全网通"+(i+1)+"G自拍美颜拍照智能手机指纹超薄大屏双卡vivox7", Store.YES));
            document.add(new LongField("price", 100*(i+1), Store.YES));
            //TextField:做索引并且分词
            document.add(new StringField("image", "http://image.taotao.com/images/2016/07/25/2016072503024233808430.jpg", Store.YES));
            document.add(new IntField("status", 1, Store.YES));
            documents.add(document);
        }
        
        //索引位置
        Directory directory = FSDirectory.open(new File("index"));
        
        //定义分词器(标准分词器)
        Analyzer analyzer = new StandardAnalyzer();
        //定义索引配置
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);
        //先删除原有数据在写入  默认是APPEND追加
        indexWriterConfig.setOpenMode(OpenMode.CREATE);
        //定义索引对象
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        
        //将文档写入索引库中
        indexWriter.addDocuments(documents);
        
        //关闭索引
        indexWriter.close();
    }

}
