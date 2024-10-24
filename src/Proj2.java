/**********************************************************************
 * @file Proj2.java
 * @brief This program implements the Proj2 class. It reads the input data
 * file and writes to an output file using unsorted and randomized arraylists.
 * @author Blythe Greene
 * @date: October 24, 2024
 ***********************************************************************/
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Proj2 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        //Gte input file name from first argument and number of lines.
        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();


        //one for input file, dataset
        //number of lines to read in
        //FINISH ME
        // 1) read Arraylist dataset
        // 2) 2 copies: sorted and randomized
        //Divide every line into a sting array.
        //Create an arrayList to store the data of every element.
        ArrayList<RemoteWork> remoteList = new ArrayList<RemoteWork>();
        int num = 0;//Keeps track of line count

        //Go through numLines
        while (num < numLines) {
            String line = inputFileNameScanner.nextLine();
            //System.out.println(line);
            String[] parts = line.split(","); // split the string into multiple parts

            RemoteWork r;//Data stored in r

            r = new RemoteWork(Integer.parseInt(parts[0]), parts[1],
                    Integer.parseInt(parts[2]), Integer.parseInt(parts[3]),
                    Integer.parseInt(parts[4]));
            remoteList.add(r);
            num++;
        }
        inputFileNameStream.close();

        //insert each element from the sorted and unsorted arraylists into BST
        //There will be 4 trees.
        FileWriter output = null;
        try {
            //Create/open the file and write to it.
            output = new FileWriter("output.txt", true);

            //Store data from the original matrix
            ArrayList<RemoteWork> original = new ArrayList<>();
            for(int i = 0; i < remoteList.size(); i++) {
                original.add(remoteList.get(i));
            }

            //Sort the arraylist and add the elements to the sorted.
            Collections.sort(remoteList);
            ArrayList<RemoteWork> sortedList = new ArrayList<>();
            for(int i = 0; i < remoteList.size(); i++) {
                sortedList.add(remoteList.get(i));
            }

            //Shuffle the arrayList
            Collections.shuffle(remoteList);
            //Create objects to store the four trees
            BST<RemoteWork> sortedBST = new BST<>();
            AvlTree<RemoteWork> sortedAVL = new AvlTree<>();
            BST<RemoteWork> shuffledBST = new BST<>();
            AvlTree<RemoteWork> shuffledAVL = new AvlTree<>();

            //Check for number of lines error
            if(numLines > remoteList.size() || numLines < 0) {
                System.err.println("Usage: java TestAvl <number of lines>");
                System.exit(1);
            }

            //For each of the trees, insert elements from the sorted or random arraylists
            //Sorted insert into BST and AVL
            //Sorted insert into BST and AVL
            long time1 = System.nanoTime();
            for(int i = 0; i < sortedList.size(); i++) {
                sortedAVL.insert(sortedList.get(i));
            }

            long time2 = System.nanoTime() - time1;
            long time3 = System.nanoTime();
            for(int i = 0; i < sortedList.size(); i++) {
                sortedBST.insert(sortedList.get(i));
            }
            long time4 = System.nanoTime() - time3;


            //Unsorted insert into BST and AVL
            long time5 = System.nanoTime();
            for(int i = 0; i < remoteList.size(); i++) {
                shuffledBST.insert(remoteList.get(i));
            }
            long time6 = System.nanoTime() - time5;
            long time7 = System.nanoTime();
            for(int i = 0; i < remoteList.size(); i++) {
                shuffledAVL.insert(remoteList.get(i));
            }
            long time8 = System.nanoTime() - time7;


            //Sorted search of BST and AVL
            long beginSearch = System.nanoTime();
            for(int i = 0; i<original.size(); i++) {
                sortedBST.search(original.get(i));
            }
            long stopSearch = System.nanoTime() - beginSearch;
            long beginSearch2 = System.nanoTime();
            for(int i = 0; i<original.size(); i++) {
                sortedAVL.contains(original.get(i));
            }
            long stopSearch2 = System.nanoTime() - beginSearch2;

            //Unsorted search of BST and AVL
            long beginSearch3 = System.nanoTime();
            for(int i = 0; i<original.size(); i++) {
                shuffledBST.search(original.get(i));
            }
            long stopSearch3 = System.nanoTime()-beginSearch3;
            long beginSearch4 = System.nanoTime();
            for(int i = 0; i<original.size(); i++) {
                shuffledAVL.contains(original.get(i));
            }
            long stopSearch4 = System.nanoTime()-beginSearch4;

            //Write the number of lines and the time it takes to complete every operation for each tree.
            output.write(numLines + "," + time4 + "," + time2 + "," + time6 + "," + time8 + "," + stopSearch + "," + stopSearch2 + ","
                    + stopSearch3 + "," + stopSearch4 + "\n");
            System.out.println(numLines + "," + time4 + "," + time2 + "," + time6 + "," + time8 + "," + stopSearch + "," + stopSearch2 + ","
                    + stopSearch3 + "," + stopSearch4);

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        finally {
            output.close();
        }
    }
}
