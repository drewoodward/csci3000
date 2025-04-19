package pageReplace;
/*
This class defines the abstract method "insert()" that was declared in the ReplacementAlgorithm.
It first creates an object of inner class AAAList (i.e., frameList), and then defines insert() which invokes
the method "insert()" in the inner class AAAList, which inserts the pageNumbers into the pageFrameList.
 */

public class AAA extends ReplacementAlgorithm
{
	private AAAList frameList;

	public AAA(int pageFrameCount) {
		super(pageFrameCount);
		frameList = new AAAList(pageFrameCount);
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
    // If not, then the pageNumber will be inserted into the pageFrameList in a FIFO manner. 
	class AAAList
	{
	    // List of pages in the allocated frames
		int[] pageFrameList;
	    
	    // Pointer to the location in the list of frames
	    // It is used to see if a pageNumber is in a frame or not
		int elementCount;

	    // Creates an array of frames with the size of pageFrameCount.
		AAAList(int pageFrameCount) {
			pageFrameList = new int[pageFrameCount];

            java.util.Arrays.fill(pageFrameList,-1);
			elementCount = 0;
		}

	    // If the pageNumber is not in the pageFrameList, then insert it into the pageFrameList
	    // in circular manner which represents FIFO algorithm.
		void insert(int pageNumber) {
			if (!search(pageNumber)) {

				if (System.getProperty("debug") != null)
					System.out.print("*");
				pageFaultCount++;
				pageFrameList[(elementCount++ % pageFrameCount)] = pageNumber;
			}
		}

		void dump() {
			for (int i = 0; i < pageFrameList.length; i++)
				System.out.print("["+i+"]"+pageFrameList[i]+", ");
		}


	    // search to find the pageNumber in the pageFrameList. If not found retrun false.
		boolean search(int pageNumber) {
			boolean returnVal = false;

			for (int i = 0; i < pageFrameList.length; i++) {
				if (pageNumber == pageFrameList[i]) {
					returnVal = true;
					break;
				}
			}
			return returnVal;
		}
	}
}
