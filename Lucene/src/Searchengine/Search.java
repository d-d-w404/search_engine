package Searchengine;

import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Search {
	private String index_location;//这个是索引库的地址。
	
	private String[][] s=new String[10][3];
	
	public Search(String index_location) {
		this.index_location=index_location;
	}//这个按照长度的搜索，只需要直到是那个索引库就可以了
	
	
    //创建IndexReader和IndexSearcher
    public IndexSearcher getIndexSearcher() throws Exception{
        // 第一步：创建一个Directory对象，也就是索引库存放的位置。
        Directory directory = FSDirectory.open(Paths.get(index_location));
        // 第二步：创建一个indexReader对象，需要指定Directory对象。
        IndexReader indexReader = DirectoryReader.open(directory);
        // 第三步：创建一个indexsearcher对象，需要指定IndexReader对象
        return new IndexSearcher(indexReader);
    }
    
    //执行查询的结果
    public String[][] printResult_addreturn(IndexSearcher indexSearcher,Query query)throws Exception{
    	//这个函数本来是在console中用来print出我们的查询结果，我还需要它将结果输出在gui中，所以addreturn
        // 第五步：执行查询。
        TopDocs topDocs = indexSearcher.search(query, 10);//这里的10代表的是查询结果中最大的数目
        int i=0;
        String[][] s=new String[10][3];
        // 第六步：返回查询结果。遍历查询结果并输出。
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int doc = scoreDoc.doc;
            Document document = indexSearcher.doc(doc);
            // 文件名称
            String fileName = document.get("fileName");
            System.out.println(fileName);
            // 文件内容
            String fileContent = document.get("fileContent");
            System.out.println(fileContent);
            // 文件路径
            String filePath = document.get("filePath");
            System.out.println(filePath);
            System.out.println("------------");
            
            s[i][0]="文件名:"+fileName;
            s[i][1]="文件内容:"+fileContent;
            s[i][2]="文件路径:"+filePath;
            i++;
        }
        return s;
    }
    //查询所有
   // @Test
    public String[][] testMatchAllDocsQuery() throws Exception {
        IndexSearcher indexSearcher = getIndexSearcher();
        Query query = new MatchAllDocsQuery();
        System.out.println(query);
        s=printResult_addreturn(indexSearcher, query);
        //关闭资源
        
        indexSearcher.getIndexReader().close();
        return s;
    }//当我查询”笔记“的时候，最多只有10个结果，就是因为在printResult中规定了查询结果为10
    
    
    //根据文件大小范围查询
    //@Test
    public String[][] testRangeQuery(long min,long max) throws Exception {
    	//这里需要做一个判断，保证min<=max
    	if(min<=max) {
            IndexSearcher indexSearcher = getIndexSearcher(); 
            Query query = LongPoint.newRangeQuery("fileSize", min, max);
            //通过测试发现，这就是文件的字节长度。等于右边的数，依旧会被包含进来，如果小于，就不包含了
            System.out.println(query);
            s=printResult_addreturn(indexSearcher, query);
            //关闭资源
            indexSearcher.getIndexReader().close();
            
    	}else {
    		System.out.println("请保证MIN<=MAX");
    	}
		return s;
    	
    }
    
    
    //多域查询
    //@Test
    public String[][] testMultiFieldQueryParser(String part,String manyterm) throws Exception {
        IndexSearcher indexSearcher = getIndexSearcher();
        ArrayList<String> fields_pre=new ArrayList();
        //String[] fields = new String[2];
        if(part.equals("fileName")) {
        	fields_pre.add("fileName");
        }else if(part.equals("fileContent")) {
        	fields_pre.add("fileContent");
        }else if(part.equals("all")) {
        	fields_pre.add("fileName");
        	fields_pre.add("fileContent");
        }
 
        String[] fields =  fields_pre.toArray(new String[fields_pre.size()]);
        //这一行是为了让fields成为String[]类型，MultiFieldQueryParser()中只接受String[]类型
        
        
        //参数1： 默认查询的域
        //参数2：采用的分析器
        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(fields,new StandardAnalyzer());
        // *:*   域：值
        Query query = queryParser.parse(manyterm);
        System.out.println(query);
        s=printResult_addreturn(indexSearcher, query);
        //关闭资源
        indexSearcher.getIndexReader().close();
        return s;
    }
    
    
//    public static void main(String args[]) throws Exception {
//    	Search_length s=new Search_length("D:\\temp\\index");
//    	//s.testMatchAllDocsQuery();
//    	//s.testRangeQuery(100,200);
//    	s.testMultiFieldQueryParser("fileName","java hadoop");
//    	System.out.println("--------------------------------------------------------------------");
//    	s.testMultiFieldQueryParser("fileContent","java hadoop");
//    	System.out.println("--------------------------------------------------------------------");
//    	s.testMultiFieldQueryParser("all","java hadoop");
//    	System.out.println("--------------------------------------------------------------------");
//    	
//    }

}
