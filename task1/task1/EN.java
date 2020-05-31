package task1;

/**
 * Class EN is part of a number processing library
 * 
 * EN reads out the input number (long n) into English
 * 
 * @author Lee Yoon Jeong
 *
 */
public class EN {
	
	private static final String[] ones = {
			" one", " two", " three", " four", " five",
			" six", "seven", " eight", " nine", " ten",
			" eleven", " twelve", " thirteen", " fourteen",
			" fifteen", " sixteen", " seventeen", 
			" eighteen", " nineteen"
	};
	private static final String[] tens = {
			" twenty", " thirty", " forty", "fifty",
			" sixty", " seventy", " eighty", " ninety"
	};
	private static final String[] groups = {
			"",
			" thousand",
			" million",
			" billion",
			" trillion",
			" quadrillion",
			" quintillion"
	};
	
	private String s = new String();
	public String getString() { return s; }
	
	public EN (long n) {
		
		for ( int i=groups.length-1; i>=0; i-- ) {
			long c = (long) Math.pow((double)10, (double)(i*3));
			
			if ( n>=c ) {
				int t = (int) (n/c);
				
				if ( t >= 100 ) {
					s += ones[t/100 - 1] + " hundred";
					t = t % 100;					
				}
				if ( t >= 20 ) {
					s += tens[(t/10 - 2)];
					t = t % 10;
				}
				if ( t >= 1 ) {
					s += ones[t - 1];
				}
				
				s += groups[i];
				n = n % c;
			}
		}
		
		if ( s.length() == 0 ) {
			s = "zero";
		} else {
			s = s.substring(1);
		}
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if (args.length > 0) {
						
			Long l = Long.parseLong(args[0]);
			EN e = new EN(l);
			
			System.out.println(e.getString());
		}
	}

}

