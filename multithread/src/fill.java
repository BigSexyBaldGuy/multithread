/*
 *  Author:             Richard Barton
 *  Date:               14 March 2019
 *  Description:        Tell me what you think this program is doing.
 */


/***********************************************************
*  DON'T MODIFY ANYTHING FROM HERE UNTIL TOLD OTHERWISE.  *
***********************************************************/
// Todo: change code back to original
class fill {
    //private static int  array[] = new int[512 * 1024 * 1024];
    private static int  array[] = new int[10];

    public static void main(String[] args)
    {
        int             valuesAdded;
        long            elapsed;
        RandomFill      randomFill;

        randomFill = new RandomFill();

        elapsed = System.nanoTime();
        valuesAdded = randomFill.fillArray(array);
        elapsed = System.nanoTime() - elapsed;
        System.out.println(valuesAdded + " values added" +
                           " in " + elapsed + "ns");
    }
}

/************************************
*  MAKE YOUR MODIFICATIONS BELOW.  *
************************************/

class RandomFill {
    int  arrayCopy[];

    public int  fillArray(int array[])
    {
        int     i;
        int     arrayLength;
        int     count;
        int     maxValue;
        java.util.Random        myRandom;


        if ((arrayLength = array.length) <= 0) {
            return(0);
        }
        System.out.println(arrayLength + " items in array");

        maxValue = arrayLength;
        myRandom = new java.util.Random();

//        count = 0;
//        for (i = 0; (i < arrayLength); ++i, ++count) {
//            array[i] = myRandom.nextInt(maxValue);
//        }
        arrayCopy = array;

        PrintDemo PD = new PrintDemo();

        ThreadDemo T1 = new ThreadDemo( "Thread - 1 ", PD, arrayCopy,
                0,5 );
        ThreadDemo T2 = new ThreadDemo( "Thread - 2 ", PD, arrayCopy,
                5,10 );

        T1.start();
        T2.start();

        // wait for threads to end
        try {
            T1.join();
            T2.join();
        } catch ( Exception e) {
            System.out.println("Interrupted");
        }

        count=5;
        return(count);
    }
}


class PrintDemo {
    public void printCount(String name, int array[], int arrayLength,
                           int startPosition, int stopPosition) {
        try {
            for(int i = startPosition; i < stopPosition; i++) {
                System.out.println(name + " Counter   ---   "  + i );
                array[i] = i;
            }
        } catch (Exception e) {
            System.out.println("Thread  interrupted.");
        }
    }
}

class ThreadDemo extends Thread {
    private Thread t;
    private String threadName;
    private int arraySize;
    private int arrayCopy[];
    private int start;
    private int stop;
    private int numberOfValuesAdded;
    PrintDemo PD;

    ThreadDemo( String name,  PrintDemo pd,
                int array[], int startPosition, int stopPosition) {
        threadName = name;
        PD = pd;
        start = startPosition;
        stop = stopPosition;
        arrayCopy = array;
    }

    public int getCount(){
        return numberOfValuesAdded;
    }

    public void run() {
        PD.printCount(threadName, arrayCopy, arraySize, start,
                stop);
        System.out.println("Thread " +  threadName + " exiting.");
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}

