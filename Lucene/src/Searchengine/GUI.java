package Searchengine;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI {
	private FileIO fileio;
	private Index index;
	
	private int width=1000;
	private int height=600;
	private int southw=width;
	private int southh=height/6;
	private int northw=width;
	private int northh=height/6;
	private int westw=(width/10)*2;
	private int westh=height-southh-northh;
	private int eastw=width/10;
	private int easth=height-southh-northh;
	private int centerw=width-eastw-westw;
	private int centerh=height-southh-northh;
	
	private int index_westw=width/2;
	private int index_westh=(height/6)*5;
	private int index_eastw=width/2;
	private int index_easth=index_westh;
	private int index_southw=width;
	private int index_southh=height-index_westh;
		
	/*
	 *                                      ----north
	 *                                      -
	                       ---searchpane-------- west
	                       -  (JPanel)      -
  frame-----main_page()-----                ----center
(JFrame)   (JTabbenPane)   -               
	                       ---indexpane-----
	                       -   (JPanel)
	                       -
	                       ----searchsizepane-----
	                            (JPanel)
		
	*/
	private JFrame frame;//整个界面	
	//顶端有选项框的结构
	JTabbedPane main_page;
	JPanel searchpane;
	JPanel indexpane;
	
	//-----------------------search-------------------------------
	//search_south...................................................................	
	
	
	
	//search_north...................................................................	
	JPanel search_north;
	
	JLabel search;
	JComboBox search_part;//这个下拉框是为了选择我的搜索按名字还是内容
	JTextField search_input;
	JButton search_go;
	
	
	JLabel search_min;
	JLabel search_max;
	JTextField search_min_input;
	JTextField search_max_input;
	JButton search_size_go; 
	
	//east...................................................................	
	
	//west...................................................................
	JPanel search_west;
	
	JLabel search_west_notice;
	JComboBox yourindex;
	
	//center...................................................................
	JPanel search_center=new JPanel();
	
	JTextArea search_result;
	JScrollPane search_result_js;
	//这个JScrollPane单纯就是为了让search_result这个文本区域能够下滑
	
	
	//-----------------------------index------------------------
		JFileChooser jfc;//文件选择器,我这里只用了一个文件选择器
		
		//index_center-------------------------------------------
		JPanel index_west;
		//第一行
		JLabel index_label;
		//第二行
		JLabel source_file;
		JTextField source_file_input;
		JButton source_file_find;
		//第三行
		JLabel index_file;
		JTextField index_file_input;
		JButton index_file_find;
		//第四行
		JButton buildindex;
		//第五行
		JLabel index_label2;
		JTextArea delete_notice;
		JComboBox deletepart;
		JComboBox deleteindex;
		JTextField deleteterm;
		JButton termdelete;//需要deletepart,deleteindex,deleteterm
		JButton alldelete;//只需要deleteindex
	
		
	public GUI() {
		fileio=new FileIO();
		
		initgui();
	}
	
	private void initgui() {
		frame=new JFrame("SEARCH ENGINIE");
		frame.setSize(width,height);
		frame.setLocation(10,10);
		//frame.setLayout(new  BorderLayout());//确定了边框布局
				
		
		//main_page
		main_page=new JTabbedPane();
				
		searchpane=new JPanel();
		searchpane.setLayout(null);
		searchpane.setLayout(new  BorderLayout());//设定了边框布局
		
		indexpane=new JPanel();
		indexpane.setLayout(null);
		indexpane.setLayout(new  BorderLayout());//设定了边框布局

		
		main_page.add("SEARCH",searchpane);
		main_page.add("INDEX_BUILD",indexpane);
		
		
		//-------------------------------search-----------------------
		
		//south---------------------------------------------------------
				
		//north---------------------------------------------------------
		search_north=new JPanel();
		search_north.setLayout(null);
		search_north.setPreferredSize(new Dimension(northw, northh));//每个Jpanel中使用流式布局
		
		search=new JLabel("Search");
		search.setBounds(0,25,100,50);
		
		search_part=new JComboBox();
		search_part.addItem("fileName");
		search_part.addItem("fileContent");
		search_part.addItem("all");
		search_part.setBounds(100,25,100,50);
		
		search_input=new JTextField();
		search_input.setBounds(200,25,200,50);
		
		search_go=new JButton("GO");
		search_go.setBounds(450,25,100,50);
		search_go.addActionListener(new MyListener());
		
		//按长度搜索
		search_min=new JLabel("MIN");
		search_min.setBounds(700,0,50,50);
		
		search_max=new JLabel("MAX");
		search_max.setBounds(700,50,50,50);
		
		search_min_input=new JTextField();
		search_min_input.setBounds(750,12,50,25);
		
		search_max_input=new JTextField();
		search_max_input.setBounds(750,67,50,25);
		
		search_size_go=new JButton("Search by size");
		search_size_go.setBounds(850,25,150,50);
		search_size_go.addActionListener(new MyListener());
		
		search_north.add(search);
		search_north.add(search_part);
		search_north.add(search_input);
		search_north.add(search_go);
		search_north.add(search_min);
		search_north.add(search_max);
		search_north.add(search_min_input);
		search_north.add(search_max_input);
		search_north.add(search_size_go);
		
		//east---------------------------------------------------------
		
		//west---------------------------------------------------------
		search_west=new JPanel();
		search_west.setLayout(null);
		search_west.setPreferredSize(new Dimension(westw, westh));//每个Jpanel中使用流式布局
		
		search_west_notice=new JLabel("PLEASE  CHOOSE  INDEX:");
		search_west_notice.setBounds(0,0,200,25);
		
		yourindex=new JComboBox();
		yourindex.setBounds(100,25,100,25);
		for(String index_location:fileio.read()) {
			yourindex.addItem(index_location);
		}
		
		search_west.add(search_west_notice);
		search_west.add(yourindex);
		
		
		//center---------------------------------------------------------
		search_center.setLayout(new FlowLayout());
		search_center.setLayout(null);
		search_center.setPreferredSize(new Dimension(centerw, centerh));
		
		
		search_result=new JTextArea();
		search_result.setEditable(false);//保证显示框中的内容无法改动
		search_result.setLineWrap(true);//自动换行,意思是到了右边界自动换行，但是我想要每个append都自开一行，需要设置result.append("\n");
		//result.setBounds(0,0,500,400);
		
		
		search_result_js = new JScrollPane(search_result);
		search_result_js.setBounds(0,0,700,400);
		
		search_center.add(search_result_js,BorderLayout.CENTER);

		
		
		//--------------------------index-------------------------------------
		
		//center
		jfc=new JFileChooser();//文件选择器
		jfc.setCurrentDirectory(new File("d:\\"));//文件选择器的初始目录定为d盘
		
		index_west=new JPanel();
		index_west.setLayout(null);
		index_west.setPreferredSize(new Dimension(index_westw,index_eastw));//每个Jpanel中使用流式布局
		
		//第一行
		index_label=new JLabel("BUILD  INDEX");
		index_label.setBounds(150,0,100,100);
		
		//第二行
		source_file=new JLabel("Source_File");
		source_file.setBounds(0,112,100,25);
		
		source_file_input=new JTextField();
		source_file_input.setBounds(100,112,200,25);
		
		source_file_find=new JButton("Find Directory");
		source_file_find.setBounds(300,112,100,25);
		source_file_find.addActionListener(new MyListener());
		
		//第三行
		index_file=new JLabel("Index_File");
		index_file.setBounds(0,162,200,25);
		
		index_file_input=new JTextField();
		index_file_input.setBounds(100,162,200,25);
		
		index_file_find=new JButton("Find Directory");
		index_file_find.setBounds(300,162,100,25);
		index_file_find.addActionListener(new MyListener());
		//index所在的文件可以不存在，那我就直接从index_file_input中输入，也可以存在，我就可以从index_file_find按钮去找

		//第四行
//		yourindex=new JComboBox();
//		yourindex.setBounds(100,300,100,50);
//		for(String index_location:fileio.read()) {
//			yourindex.addItem(index_location);
//		}
		
		buildindex=new JButton("BuildIndex");
		//每build一次，都要把库的地址放入indexname.txt中，如果已经有了该库，则不再写入，不需要调用FileIO中的write
		buildindex.setBounds(300,200,100,50);
		buildindex.addActionListener(new MyListener());
		
		
		//第五行
		index_label2=new JLabel("DELETE  INDEX");
		index_label2.setBounds(150,250,100,50);
		
		
		delete_notice=new JTextArea("please choose the path of index,and delete it all."
				+ "you can also add some information right,and delete by term.");
		delete_notice.setEditable(false);
		delete_notice.setLineWrap(true);
		delete_notice.setBounds(50,300,200,50);
		
		
		
		deleteindex=new JComboBox();
		deleteindex.setBounds(50,362,100,25);
		for(String index_location:fileio.read()) {
			deleteindex.addItem(index_location);
		}
		
		deletepart=new JComboBox();
		deletepart.addItem("fileName");
		deletepart.addItem("fileContent");
		deletepart.setBounds(300,312,100,25);
						

		
		deleteterm=new JTextField();
		deleteterm.setBounds(300,362,100,25);
		
		termdelete=new JButton("DELETE BY Term");
		termdelete.setBounds(250,400,150,50);
		termdelete.addActionListener(new MyListener());
		
		alldelete=new JButton("DELETE ALL");
		alldelete.setBounds(50,400,150,50);
		alldelete.addActionListener(new MyListener());
		
		
		index_west.add(delete_notice);
		index_west.add(index_label);
		index_west.add(jfc);
		index_west.add(source_file);
		index_west.add(source_file_input);
		index_west.add(source_file_find);
		
		index_west.add(index_file);
		index_west.add(index_file_input);
		index_west.add(index_file_find);
		
//		west.add(yourindex);
		index_west.add(buildindex);
		
		
		index_west.add(index_label2);
		index_west.add(deletepart);
		index_west.add(deleteindex);
		index_west.add(deleteterm);
		index_west.add(termdelete);
		index_west.add(alldelete);
		
		
	
		
		
		//---------------------------------------------------------------
		frame.add(main_page);
		
		
        //frame.add(south,BorderLayout.SOUTH);
        searchpane.add(search_center,BorderLayout.CENTER);
        searchpane.add(search_north,BorderLayout.NORTH);
        searchpane.add(search_west,BorderLayout.WEST);
        
        
        indexpane.add(index_west,BorderLayout.CENTER);
        
        

        
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter(){
        	public void windowClosing(WindowEvent arg0){
        		System.exit(0);
        		}
        	});
	}
	
	private class MyListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	        if(e.getSource().equals(source_file_find)){//判断触发方法的按钮是哪个
	            jfc.setFileSelectionMode(1);//设定只能选择到文件夹
	            int state=jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
	            if(state==1){
	                return;//撤销则返回
	            }
	            else{
	                File f=jfc.getSelectedFile();//f为选择到的目录
	                source_file_input.setText(f.getAbsolutePath());
	            }
	        }
	        
	        if(e.getSource().equals(index_file_find)){
	            jfc.setFileSelectionMode(1);//设定只能选择到目录
	            int state=jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
	            if(state==1){
	                return;//撤销则返回
	            }
	            else{
	                File f=jfc.getSelectedFile();//f为选择到的文件
	                index_file_input.setText(f.getAbsolutePath());
	            }
	        }
	        if(e.getSource().equals(buildindex)){
	        	if(index_file_input.getText()!=null && source_file_input.getText()!=null) {
	        		Index index=new Index(index_file_input.getText(),source_file_input.getText());	        	    
	        		try {
						index.index();
						//可能是生成新的索引库，或者是在原来的索引库上添加一些索引
						//一旦发现建立的索引的地址没有在我的indexname.txt的文件上
						//需要通过FileIo的write()函数添加新的地址
						boolean if_common=false;
						for(String index_location:fileio.read()) {
							if(index_location.equals(index_file_input.getText())) {
								//这里一开始使用==做判断的时候有问题，即使两个String长得一样，也判定不等
								if_common=true;
								break;
							}
						}
						if(!if_common) {
							fileio.write(index_file_input.getText());					
						}
						
						yourindex.removeAllItems();
						//这句话主要是先把gui界面中的yourindex变成空，否则后面的addItem不会删掉以前的Item
						for(String index_location:fileio.read()) {
							yourindex.addItem(index_location);
						}//这个循环是为了让gui立马获得新添加的index_location
						
						deleteindex.removeAllItems();
						for(String index_location:fileio.read()) {
							deleteindex.addItem(index_location);
						}
												
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	        		
	        	}else {
	        		System.out.println("index和source不能为空");
	        	}
	        	//当index和source的输入都不为null时才能生成索引库
	        }
	                
	        
	        if(e.getSource().equals(termdelete)){
	        	index=new Index((String) deleteindex.getSelectedItem(),source_file_input.getText());
	        	//感觉这里的第二个参数实在无关紧要，毕竟delete不会用这个了
	        	
	        	try {
					index.indexDelete((String) deletepart.getSelectedItem(),deleteterm.getText());
					System.out.println("成功删除"+deletepart.getSelectedItem()+"库中的"+deleteterm.getText()+"的索引");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	
	        }
	        
	        if(e.getSource().equals(alldelete)){

	        	index=new Index((String) deleteindex.getSelectedItem(),source_file_input.getText());
	        	//感觉这里的第二个参数实在无关紧要，毕竟delete不会用这个了
	        	
	        	try {
					index.indexAllDelete((String) deleteindex.getSelectedItem());
					System.out.println("成功删除"+deletepart.getSelectedItem()+"库的所有索引");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	        		        
	        }
	        
	        if(e.getSource().equals(search_size_go)){
        		  Search search_length=new Search((String) yourindex.getSelectedItem());    
        		  
        		  try {
        			String[][] s=new String[10][3];
					s=search_length.testRangeQuery(Long.parseLong(search_min_input.getText()),Long.parseLong(search_max_input.getText()) );
        			search_result.setText("");
        			for(int i=0;i<10;i++) {
        				if(s[i][0]==null) {
        					
        				}else {
	        				for(int j=0;j<3;j++) {
	        					search_result.append(s[i][j]+"\n");
	        				}
	        				search_result.append("...........................................\n");
        				}        			
        			}
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		  
	        }
	        
	        if(e.getSource().equals(search_go)){
	        	if(search_input.getText()!=null) {
	        		//Search search=new Search((String) yourindex.getSelectedItem());  //这个地方的参数是我需要search的索引库的地址
	        		Search search_length=new Search((String) yourindex.getSelectedItem());
	        		try {
	        			String[][] s=new String[10][3];
	        			s=search_length.testMultiFieldQueryParser((String) search_part.getSelectedItem(), search_input.getText());
	        			search_result.setText("");
	        			for(int i=0;i<10;i++) {
	        				if(s[i][0]==null) {
	        					
	        				}else {
		        				for(int j=0;j<3;j++) {
		        					search_result.append(s[i][j]+"\n");
		        				}
		        				search_result.append("...........................................\n");
	        				}
	        			}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}        		
	        	}else {
	        		System.out.println("please input some words");
	        	}
	        }
	        
		}
		
	}
	
	public static void main(String args[]) {
		GUI gui=new GUI();

	}

}
