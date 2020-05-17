package nuc.ly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/** 
* @author ����:ly 
* @version ����ʱ�䣺2020��5��17�� ����10:39:57 
* 
*/
public class Judge {
	private ArrayList<String> Reserve;			//�����ּ���
	private ArrayList<String> Operator;			//���������
	private ArrayList<String> Boundary;			//�������
	private HashMap<String, Integer> Symbol;	//��ʶ����
	private HashMap<String, Integer> Digits;	//������
	private ArrayList<Binary> binaries;			//��Ԫ�鼯
	private char character;						//��ǰ�жϵ��ַ�
	File filename;
	InputStreamReader reader;
	
	/**
	 * ���ļ����ж�ȡ��ֵ������
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
	 * ��ȡ�ļ�
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
	 * �ʷ�����
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
							System.out.println(string+"�д���");
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
								System.out.println(string+"�д���");
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
	 * �ж��Ƿ�������
	 * @param charact
	 * @return
	 */
	private boolean isDigit(char charact) {
		if (charact >= '0'&&charact <= '9')
			return true;
		return false;
	}

	/**
	 * �ж��Ƿ�����ĸ
	 * @param charact
	 * @return
	 */
	private boolean isLetter(char charact) {
		if ((charact >= 'A'&&charact <= 'Z') || (charact >= 'a'&&charact <= 'z'))
			return true;
		return false;
	}
	
	/**
	 * �ж��Ƿ��ǽ��
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
	 * �ж��Ƿ��������
	 * @param string
	 * @return
	 */
	private boolean isOperator(String string) {
		if (Operator.contains(string))
			return true;
		return false;
	}
	
	/**
	 * �ж��Ƿ��Ǳ�����
	 * @param string
	 * @return
	 */
	private boolean isReserve(String string) {
		if (Reserve.contains(string))
			return true;
		return false;
	}
	
	/**
	 * �ж��Ƿ����Ѵ��ڵı�ʶ��
	 * @param string
	 * @return
	 */
	private boolean isSymbol(String string) {
		if (Symbol.containsKey(string))
			return true;
		return false;
	}
	
	/**
	 * �ж��Ƿ����Ѵ��ڵĳ���
	 * @param string
	 * @return
	 */
	private boolean isDigits(String string) {
		if (Digits.containsKey(string))
			return true;
		return false;
	}
	
	/**
	 * ��ʼ����Ϊ�������ϸ�ֵ 
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
	 * չʾ��������
	 */
	private void show() {
		System.out.println("------������------");
		for (String reserve : Reserve) {
			System.out.println(reserve  );
		}
		System.out.println();
		System.out.println("------�����------");
		for (String operator : Operator) {
			System.out.println(operator);
		}
		System.out.println();
		System.out.println("------���------");
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
	 * ��ʼ�ʷ�����
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
