package nuc.ly;
/** 
* @author ����:ly 
* @version ����ʱ�䣺2020��5��17�� ����10:39:47 
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
