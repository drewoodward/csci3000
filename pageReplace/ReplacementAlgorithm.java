package pageReplace;

/* 
This is an abstract class that initializes the instance variable
pageFrameCount, and returns the number of page faults using
the getter method getPageFautCount. The class that extends this
abstract class must define the abstract method insert() which 
updates the instance variable pageFaultCoungt.
 */


public abstract class ReplacementAlgorithm
{

	protected int pageFaultCount;
	
	protected int pageFrameCount;

        // Checks that pageFrameCount is not a negative number.
	public ReplacementAlgorithm(int pageFrameCount) {
		if (pageFrameCount < 0)
			throw new IllegalArgumentException();
		
		this.pageFrameCount = pageFrameCount;
		pageFaultCount = 0;
	}
	
	public int getPageFaultCount() {
		return pageFaultCount;
	}
	
	public abstract void insert(int pageNumber); 
}
