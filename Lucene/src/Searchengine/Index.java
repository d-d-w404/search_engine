package Searchengine;

import java.io.File;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Index {
	private String index_path;
	private String source_path;
	
	public Index(String index_path,String source_path) {
		this.index_path=index_path;
		this.source_path=source_path;
	}
	
    public void index() throws Exception {
        /*
         * 第一步：创建一个indexwriter对象
         * 1指定索引库的存放位置Directory对象
         * 2指定一个分析器，对文档内容进行分析。
         */
        Directory directory = FSDirectory.open(Paths.get(index_path));
        // Directory directory = new RAMDirectory();//保存索引到内存中 （内存索引库）
        // 官方推荐分词器，对中文不友好
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);
 
        // 第二步：通过IO读取磁盘上的文件信息
        File f = new File(source_path);
        File[] listFiles = f.listFiles();
        if(listFiles != null){
            for (File file : listFiles) {
                if(file.isFile()){
                    // 第三步：创建document对象, 并把文件信息添加到document对象中
                    Document document = new Document();
                    // 文件名称
                    String file_name = file.getName();
                    Field fileNameField = new TextField("fileName", file_name, Field.Store.YES);
                    // 文件路径
                    String file_path = file.getPath();
                    Field filePathField = new StoredField("filePath", file_path);
 
                    // 文件大小
                    long file_size = FileUtils.sizeOf(file);
                    //索引
                    Field fileSizeField1 = new LongPoint("fileSize", file_size);
                    //存储
                    Field fileSizeField2 = new StoredField("fileSize", file_size);
 
                    // 文件内容
                    String file_content = FileUtils.readFileToString(file, "UTF-8");
                    Field fileContentField = new TextField("fileContent", file_content, Field.Store.NO);
 
                    document.add(fileNameField);
                    document.add(fileSizeField1);
                    document.add(fileSizeField2);
                    document.add(filePathField);
                    document.add(fileContentField);
                    // 第四步：使用indexwriter对象将document对象写入索引库，此过程进行索引创建。并将索引和document对象写入索引库。
                    indexWriter.addDocument(document);
                }
            }
            // 第五步：关闭IndexWriter对象。
            indexWriter.close();
        }
    }
    
    
    public IndexWriter getIndexWriter() throws Exception{
        // 第一步：创建一个java工程，并导入jar包。
        // 第二步：创建一个indexwriter对象。
    	Directory directory = FSDirectory.open(Paths.get(index_path));
        // Directory directory = new RAMDirectory();//保存索引到内存中 （内存索引库）
        Analyzer analyzer = new StandardAnalyzer();// 官方推荐
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        return new IndexWriter(directory, config);
    }
    
    
    //删除fileName为albert的索引
    public void indexDelete(String deletepart,String deleteterm) throws Exception {
    	//这个删除操作会删除相关的term的索引，生成一个liv,cfe,cfs,以及segments_x,wirte.lock文件都在
        IndexWriter indexWriter = getIndexWriter();
        Query query = new TermQuery(new Term(deletepart, deleteterm));
        indexWriter.deleteDocuments(query);
        indexWriter.close();
    }
    //两种delete都会让segment_x增加1
    
    //删除全部索引
    //删除cfe,cfs,si以及liv，保留segment_x和write.lock文件
    public void indexAllDelete(String deleteindex) throws Exception {
        // 第一步：创建一个indexwriter对象。
        Directory directory = FSDirectory.open(Paths.get(deleteindex));
        // 官方推荐
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);
        indexWriter.deleteAll();
        indexWriter.close();
    }
    
    
    

}


//这个类通过源文件向index中导入索引。建立的索引库。