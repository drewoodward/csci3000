package pageReplace;
/*
This class sets the size and range for generating a random reference string
and then genrates the random reference string and stores it in the instance
variable referenceString. The method getReferenceSting() returns a 
pre-set reference string in variabe "str".  However, if we want to 
use the random refernce string, this method should be modified to
return referenceSsting. 
 */

public class PageGenerator
{
	private static final int DEFAULT_SIZE = 100;
	private static final int RANGE = 9;

	int[] referenceString;

	public PageGenerator() {
		this(DEFAULT_SIZE);
	}

	public PageGenerator(int count) {
		if (count < 0)
			throw new IllegalArgumentException();

		java.util.Random generator = new java.util.Random();
		referenceString = new int[count];

		for (int i = 0; i < count; i++)
			referenceString[i] = generator.nextInt(RANGE + 1);
	}

	public int[] getReferenceString() {
        
        	int[] str = {7,0,1,2,0,3,0,4,2,3,0,3,2,1,2,0,1,7,0,1};

	               	return str;
		//return referenceString;
	}
}
