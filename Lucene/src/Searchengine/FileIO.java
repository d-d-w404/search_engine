package Searchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class FileIO {
	
    private File file=new File("indexname\\indexname"); 
    //这个类专门用来写入我最新生成的index库的地址和读出他们的地址

	
	public FileIO() {
		
	}
	
	public void write(String index_location) {
		FileWriter fw = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file,true);
            fw.write(index_location+"\r\n");
//            for(int i = 1;i <=3000;i++){
//            fw.write("abcdefgabcdefg"+i+",");              //向文件中写内容
//            fw.write("sssssssssssssss"+i+",\r\n");        //加上换行
//            fw.flush();
//            }
            System.out.println("写数据成功！");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(fw != null){
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	public ArrayList<String> read() {
        StringBuilder result = new StringBuilder();
        ArrayList<String> index_location_array =new ArrayList<String>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));        //构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){
                //使用readLine方法，一次读一行
            	index_location_array.add(s);
                result.append(System.lineSeparator()+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return index_location_array;
        //return result.toString();
	}
	


	
    public static void main(String[] args) {
    	FileIO f=new FileIO();
    	f.write("happy");
    	System.out.println(f.read());

    }
}
