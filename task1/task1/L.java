package task1;

/**
 * Classes LL is part of a data structure library
 * 
 * L is part of linked list data structure
 * 
 * @author Lee Yoon Jeong
 *
 */
public class L {
	
	private LL h;
	
	public L() {
		h = null;
	}
	
	public L(LL ll) {
		h = ll;
	}
	
	public void R() {
		
		LL l1, l2, l3, l4;
		
		if ( h== null ) return;
		
		l1 = h;
		l2 = h.n;
		l3 = null;
		
		while ( l2 != null ) {
			l4 = l2.n;
			l1.n = l3;
			l3 = l1;
			l1 = l2;
			l2 = l4;
			l1.n = l3; //Task 1.c
		}
		
		h = l1;
	}
	
	public void printL() {
		LL l = h;
		
		while ( l != null ) {
			System.out.println(l.getV());;
			l = l.n;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Start L");
		
		LL ll = new LL(1,new LL(2, new LL(3, new LL(4))));
		L l = new L(ll);
		
		l.printL();
		l.R();
		System.out.println("After R");
		l.printL();		
		

	}

}
