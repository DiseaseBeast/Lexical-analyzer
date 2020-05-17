package nuc.ly;
/** 
* @author 作者:ly 
* @version 创建时间：2020年5月17日 上午10:39:47 
* 
*/
public class Binary {
	private int x;
	private String yString;
	
	public Binary(int x, String y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		yString = y;
	}
	
	public String toString() {
		return "<"+x+","+yString+">";
	}
}
