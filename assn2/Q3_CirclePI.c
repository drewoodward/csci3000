/*     Q4.24  Parallel
An interesting way of calculating π is to use a technique known as Monte Carlo, which involves randomization. This technique works as follows: Suppose you have a circle inscribed within a square, as shown in Figure 4.25. (Assume that the radius of this circle is 1.) 
- First, generate a series of random points as simple (x, y) coordinates. These points must fall within the Cartesian coordinates that bound the square. Of the total number of random points that are generated, some will occur within the circle. 
- Next, estimate π by performing the following calculation: π = 4 × ( number of points in circle ) / ( total number of points ) π = 4 × ( number of points in circle ) / ( total number of points ) 

Write a multithreaded version of this algorithm that creates a separate thread to generate a number of random points. The thread will count the number of points that occur within the circle and store that result in a global variable. When this thread has exited, the parent thread will calculate and output the estimated value of π. It is worth experimenting with the number of random points generated. As a general rule, the greater the number of points, the closer the approximation to π. Figure 4.25 Monte Carlo technique for calculating π. 
In the source-code download for this text, you will find a sample program that provides a technique for generating random numbers, as well as determining if the random (x, y) point occurs within the circle. Readers interested in the details of the Monte Carlo method for estimating π should consult the bibliography at the end of this chapter. In Chapter 6, we modify this exercise using relevant material from that chapter.
 */
/**
 * Parallel version of calculcating Pi 
 *
 * Solution to exercise 4.24
 *
 * Compilation:
 *	gcc -lpthread parallel.c
 *
 * Operating System Concepts - Tenth Edition
 * Copyright John Wiley & Sons - 2018.
 */

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>

void *worker(void *param);

#define NUMBER_OF_DARTS		50000000
#define NUMBER_OF_THREADS	2

/* the number of hits in the circle */
int circle_count = 0;

/*
 * Generates a double precision random number
 */
double random_double() 
{
	return random() / ((double)RAND_MAX + 1);
}

int main (int argc, const char * argv[]) {
	int darts_per_thread = NUMBER_OF_DARTS/ NUMBER_OF_THREADS;
	int i;
	
	double estimated_pi;
	
	pthread_t workers[NUMBER_OF_THREADS];

	/* seed the random number generator */
	srandom((unsigned)time(NULL));

	for (i = 0; i < NUMBER_OF_THREADS; i++)
		pthread_create(&workers[i], 0, worker, &darts_per_thread);
	
	for (i = 0; i < NUMBER_OF_THREADS; i++)
		pthread_join(workers[i],NULL);
	
	/* estimate Pi */
	estimated_pi = 4.0 * circle_count / NUMBER_OF_DARTS;

	printf("Pi = %f\n",estimated_pi);
	
	return 0;
}

void *worker(void *param)
{
	int number_of_darts;
	number_of_darts = *((int *)param);
	int i;
	int hit_count = 0;
	double x,y;
	
	for (i = 0; i < number_of_darts; i++) {
		
		/* generate random numbers between -1.0 and +1.0 (exclusive) */
		x = random_double() * 2.0 - 1.0;
		y = random_double() * 2.0 - 1.0;
		
		if ( sqrt(x*x + y*y) < 1.0 )
			++hit_count;		
	}
	
	circle_count += hit_count;
	
	pthread_exit(0);
}

