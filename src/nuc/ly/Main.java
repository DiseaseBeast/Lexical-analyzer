package nuc.ly;

import java.io.IOException;

/** 
* @author 作者:ly 
* @version 创建时间：2020年5月17日 上午10:39:23 
* 
*/
public class Main {
	public static void main(String []args) throws IOException {
		Judge judge = new Judge();
		judge.Start("G:\\eclipse\\LexicalAnalyzer\\program.txt");
	}
}
