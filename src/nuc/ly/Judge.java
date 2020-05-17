package nuc.ly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/** 
* @author 作者:ly 
* @version 创建时间：2020年5月17日 上午10:39:57 
* 
*/
public class Judge {
	private ArrayList<String> Reserve;			//保留字集合
	private ArrayList<String> Operator;			//运算符集合
	private ArrayList<String> Boundary;			//界符集合
	private HashMap<String, Integer> Symbol;	//标识符集
	private HashMap<String, Integer> Digits;	//常数集
	private ArrayList<Binary> binaries;			//二元组集
	private char character;						//当前判断的字符
	File filename;
	InputStreamReader reader;
	
	/**
	 * 从文件逐行读取赋值给集合
	 * @param filename
	 * @param list
	 */
	private void Filer(File filename, ArrayList<String> list){
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);
			String line = "";
	    	line = br.readLine();
			while(line != null) {
	        list.add(line);
	        line = br.readLine();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取文件
	 * @param pathname
	 */
	private void FileInit(String pathname) {
		filename = new File(pathname);
		try {
			reader = new InputStreamReader(new FileInputStream(pathname));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 词法分析
	 */
	private void getChar(){
		try {
			Symbol = new HashMap<>();
			Digits = new HashMap<>();
			binaries = new ArrayList<>();
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);
	    	int num = br.read();
	    	character = (char)num;
	    	String string = "";
			while(num != -1) {
				if(character != ' ')
				{
					if(isLetter(character)) {
						while(isLetter(character) || isDigit(character)) {
							string += character;
							num = br.read();
							character = (char)num;	
						}
						if(isReserve(string)) {
							binaries.add(new Binary(Reserve.indexOf(string)+1, string));
						}
						else
						{
							binaries.add(new Binary(Reserve.size() + Operator.size() + Boundary.size()+1, string));
						}
						string="";
						continue;
					}
					if(isDigit(character)) {
						while(isDigit(character)) {
							string += character;
							num = br.read();
							character = (char)num;
							
						}
						if(isDigit(character))
							binaries.add(new Binary(Reserve.size() + Operator.size() + Boundary.size()+2, string));
						else {
							string += character;
							System.out.println(string+"有错误");
						}
						string="";
						continue;
					}
					if(isOperator(""+character) || character == '!') {
						string += character;
						num = br.read();
						character = (char)num;
						if(isOperator(""+character)) {
							string += character;
							if(isOperator(string)) {
								binaries.add(new Binary(Operator.indexOf(string)+1, string));
							}
							else {
								string+=character;
								System.out.println(string+"有错误");
							}
						}
						else {
							binaries.add(new Binary(Operator.indexOf(string)+1, string));
						}
						string = "";
						continue;
					}
					if(isBoundary(character)) {
						binaries.add(new Binary(Reserve.size() + Operator.size() + Boundary.indexOf(""+character)+1, ""+character));
					}
					
				}
				num = br.read();
		    	character = (char)num;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断是否是数字
	 * @param charact
	 * @return
	 */
	private boolean isDigit(char charact) {
		if (charact >= '0'&&charact <= '9')
			return true;
		return false;
	}

	/**
	 * 判断是否是字母
	 * @param charact
	 * @return
	 */
	private boolean isLetter(char charact) {
		if ((charact >= 'A'&&charact <= 'Z') || (charact >= 'a'&&charact <= 'z'))
			return true;
		return false;
	}
	
	/**
	 * 判断是否是界符
	 * @param charact
	 * @return
	 */
	private boolean isBoundary(char charact) {
		String string = "";
		string += charact;
		if (Boundary.contains(string))
			return true;
		return false;
	}
	
	/**
	 * 判断是否是运算符
	 * @param string
	 * @return
	 */
	private boolean isOperator(String string) {
		if (Operator.contains(string))
			return true;
		return false;
	}
	
	/**
	 * 判断是否是保留字
	 * @param string
	 * @return
	 */
	private boolean isReserve(String string) {
		if (Reserve.contains(string))
			return true;
		return false;
	}
	
	/**
	 * 判断是否是已存在的标识符
	 * @param string
	 * @return
	 */
	private boolean isSymbol(String string) {
		if (Symbol.containsKey(string))
			return true;
		return false;
	}
	
	/**
	 * 判断是否是已存在的常数
	 * @param string
	 * @return
	 */
	private boolean isDigits(String string) {
		if (Digits.containsKey(string))
			return true;
		return false;
	}
	
	/**
	 * 初始化，为三个集合赋值 
	 */
	private void init() {
		Reserve = new ArrayList<String>();
		File filename = new File("G:\\eclipse\\LexicalAnalyzer\\Reserve.txt");
		Filer(filename, Reserve);
		Operator = new ArrayList<String>();
		filename = new File("G:\\eclipse\\LexicalAnalyzer\\Operator.txt");
		Filer(filename, Operator);
		Boundary = new ArrayList<String>();
		filename = new File("G:\\eclipse\\LexicalAnalyzer\\Boundary.txt");
		Filer(filename, Boundary);
	}
	
	/**
	 * 展示各个集合
	 */
	private void show() {
		System.out.println("------保留字------");
		for (String reserve : Reserve) {
			System.out.println(reserve  );
		}
		System.out.println();
		System.out.println("------运算符------");
		for (String operator : Operator) {
			System.out.println(operator);
		}
		System.out.println();
		System.out.println("------界符------");
		for (String boundary : Boundary) {
			System.out.println(boundary);
		}
		System.out.println();
	}
	
	private void prin() {
		for (Binary binary : binaries) {
			System.out.println(binary);
		}
	}
	
	/**
	 * 开始词法分析
	 * @param pathname
	 * @throws IOException
	 */
	public void Start(String pathname) throws IOException {
		init();
		show();
		FileInit(pathname);
		getChar();
		prin();
	}

}
