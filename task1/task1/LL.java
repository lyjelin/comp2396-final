package task1;

/**
 * Classes LL is part of a data structure library
 * 
 * LL is part of linked list data structure
 * 
 * @author Lee Yoon Jeong
 *
 */
public class LL {
	
	private int v;
	protected LL n;
	
	public LL(int v) {
		this.v = v;
		n = null;
	}
	
	public LL(int v, LL n) {
		this.v = v;
		this.n = n;
	}
	
	public int getV() { return v; }

}
