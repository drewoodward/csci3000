package pageReplace;
/*
This class defines the abstract method "insert()" that was declared in the ReplacementAlgorithm.
It first creates an object of inner class BBBList (i.e., frameList), and then defines insert() which invokes
the method "insert()" in the inner class BBBList, which inserts the pageNumbers into the pageFrameList.
 */

public class BBB extends ReplacementAlgorithm
{
	private BBBList frameList;


	public BBB(int pageFrameCount) {
		super(pageFrameCount);
		frameList = new BBBList(pageFrameCount);
	}

	public void insert(int pageNumber) {
		frameList.insert(pageNumber);
		if (System.getProperty("debug") != null) {
			System.out.print("Inserting " + pageNumber);
			frameList.dump();
			System.out.println();
		}
	}


    // This is an inner class
    // The methods of this class search to see if a pageNumber is in a pageFrameList or not.
    // If not, then the pageNumber will be inserted into the pageFrameList in a LRU manner. 

	class BBBList
	{
	        // List of pages in the allocated frames
		int[] pageFrameList;

	       // Pointer to the location in the list of frames
	       // It is used to see if a pageNumber is in a frame or not
		int elementCount;

		int lastInserted = -1;

 	    // Creates an array of frames with the size of pageFrameCount.
		BBBList(int pageFrameCount) {
			pageFrameList = new int[pageFrameCount];
            java.util.Arrays.fill(pageFrameList,-1);
			elementCount = 0;
		}


	    // First searchers to see if the pageNumber is in the pageFrameList.
	    // If not found, increase the pageFaoultCount and insert the page in the list to fill all pageFrameList
	    // locations. This is done in the beginning that the list has been empty. 
		void insert(int pageNumber) {
			int searchVal;

			if ((searchVal = search(pageNumber)) == -1) { 
				if (System.getProperty("debug") != null)
					System.out.print("*");

				pageFaultCount++;
				pageFrameList[(elementCount++ % pageFrameCount)] = pageNumber;
				lastInserted = pageNumber;
			}
			else if (pageNumber != lastInserted) 
			        
				updatePageTable(searchVal);
		}


	    // This method performs the LRU, by inserting the pages in the proper locations
	    // of the pageFrameList
		void updatePageTable(int searchVal) {

			int savedPage = pageFrameList[elementCount % pageFrameList.length];

			int insertedPage = pageFrameList[searchVal];
	
			if (savedPage == insertedPage) {
				elementCount++;
				return;
			}


			if (System.getProperty("debug") != null) 
				System.out.println("sp = " + savedPage + " ec = " + elementCount + " sv = " + searchVal);

			int rightIndex = searchVal;
			int leftIndex = (rightIndex==0) ? pageFrameList.length-1 : searchVal-1;
			
			while (rightIndex != (elementCount % pageFrameList.length) ) {
				pageFrameList[rightIndex] = pageFrameList[leftIndex];
				rightIndex = leftIndex;

				leftIndex = (leftIndex==0) ? pageFrameList.length-1 : leftIndex-1;
			}
			pageFrameList[rightIndex] = insertedPage;
			elementCount++;
		}

		void dump() {
			for (int i = 0; i < pageFrameList.length; i++)
				System.out.print("["+i+"]"+pageFrameList[i]+", ");
			System.out.print(" element count = " + elementCount);
		}

	    	// search to find the pageNumber in the pageFrameList. If found return the index of the page
	        // in the pageFrameList. If not found retrun -1.
		int search(int pageNumber) {
			int returnVal = -1;

			for (int i = 0; i < pageFrameList.length; i++) {
				if (pageNumber == pageFrameList[i]) {
					returnVal = i;
					break;
				}
			}
			return returnVal;
		}
	}
}
