package pageReplace;
/*
This is the driver class with method "main()". It first checks the format 
of the command line to make sure two parameters are provided, otherwise it 
warns and exits. It then passes the input parameter from command line
to the object of class PageGenerator to generate the referenceString and 
return it to this driver method to apply to the page replacement algorithms.
The AAA or BBB algorithms will then receive referenceString and apply the 
page numbers in the string one by one to method "insert()" in order
to generate page faulsts according to the specific page replacement algorithm.
Fianlly, the numbers of the page faults in each algorithm are prompted to the
terminal. 
 */

public class Test
{
	public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java Test <reference string size> <number of page frames>");
            System.exit(-1);
        }

		PageGenerator ref = new PageGenerator(new Integer(args[0]).intValue());

		int[] referenceString = ref.getReferenceString();

		/** Use either the AAA or BBB algorithms */
		ReplacementAlgorithm aaa = new AAA(new Integer(args[1]).intValue());
		ReplacementAlgorithm bbb = new BBB(new Integer(args[1]).intValue());

		for (int i = 0; i < referenceString.length; i++) {
			System.out.println("bbb inserting " + referenceString[i]);
			bbb.insert(referenceString[i]);
		}

		for (int i = 0; i < referenceString.length; i++) {
			System.out.println("aaa inserting " + referenceString[i]);
			aaa.insert(referenceString[i]);
		}

		System.out.println("BBB faults = " + bbb.getPageFaultCount());
		System.out.println("AAA faults = " + aaa.getPageFaultCount());
	}
}
