import java.util.Scanner;
import java.io.*;

public class Main {

    public static final int GRID_ROWS = 25;
    public static final int GRID_COLUMNS = 75;


    public static char [] [] readNewWorld(){
        char [] [] newWorld = new char [ GRID_ROWS + 2 ] [ GRID_COLUMNS + 2 ]; //Adding borders by adding +2

        Scanner consoleReader = new Scanner(System.in);
        System.out.print ("Which file do you want to open?");
        String filename = consoleReader.next();
        File file = new File(filename);
        Scanner fileReader = null;

        try {
            fileReader = new Scanner (file);
        }
        catch (Exception e) {
            System.out.print("file " + file + " does not exist");
            System.exit(0);
        }

        for (int i = 1; i <= GRID_ROWS ; i++) {
            String line = fileReader.nextLine();

            for (int j = 1; j <= GRID_COLUMNS ; j++)

                newWorld[i][j]=line.charAt(j-1);

        }

        return newWorld;

    }


    public static int numOfNeighbors(char [] [] world , int x , int y){

        int numNeighbors = 0;

        if( world [x] [y+1] == 'X' ){ numNeighbors += 1; };
        if( world [x+1] [y+1] == 'X' ){ numNeighbors += 1; };
        if( world [x+1] [y] == 'X' ){ numNeighbors += 1; };
        if( world [x+1] [y-1] == 'X' ){ numNeighbors += 1; };
        if( world [x] [y-1] == 'X' ){ numNeighbors += 1; };
        if( world [x-1] [y-1] == 'X' ){ numNeighbors += 1; };
        if( world [x-1] [y] == 'X' ){ numNeighbors += 1; };
        if( world [x-1] [y+1] == 'X' ){ numNeighbors += 1; };

        return numNeighbors;

    }

    // is world empty

    public static boolean isWorldEmpty(char [][] world){

        boolean isEmpty=true;

        for(int i = 1; i <= GRID_ROWS ; i++){
            for(int j = 1; j <= GRID_COLUMNS ; j++){

                if(world[i][j] != '.'){
                    isEmpty = false;
                    break;
                }

            }

      }
        return  isEmpty;
    }

    // print world

    public static void printWorld(char [] [] world, int generation){

        System.out.println("The current generation is generation " +generation);

        for(int i = 1; i <= GRID_ROWS ; i++){

            for(int j = 1; j <= GRID_COLUMNS ; j++){

                System.out.print(world[i][j]);

                }

            System.out.print("\n");

            }
        System.out.print("\n\n");
    }

    // create next generation

    public static char [] [] nextGenWorld( char [] [] currentGen ){

        char nextGenGrid [][] = new char [ GRID_ROWS + 2 ] [ GRID_COLUMNS + 2 ];

        for (int i = 1; i <= GRID_ROWS; i++){

            for(int j = 1; j <= GRID_COLUMNS; j++){

                if ( currentGen [i] [j] == 'X'){

                    if ( (numOfNeighbors(currentGen , i , j ) == 2 ) || (numOfNeighbors( currentGen , i , j ) == 3 ) ) {

                        nextGenGrid [i] [j] = 'X';

                    }else{

                        nextGenGrid [i] [j] = '.';

                    }


                }

                if ( currentGen [i] [j] == '.'){

                    if ( (numOfNeighbors(currentGen , i , j ) == 3 ) ) {

                        nextGenGrid [i] [j] = 'X';

                    }else{

                        nextGenGrid [i] [j] = '.';

                    }


                }

            }

        }


        return nextGenGrid;

    }

    // are the two gens the same?

    public static boolean nextGenIsSame(char [] [] worldGrid, char [] [] tempGrid){

        boolean areGensSame = true;


        for( int i = 1; i <= GRID_ROWS ; i++){

            for( int j = 1; j <= GRID_COLUMNS ; j++){

                if ( worldGrid[i][j] != tempGrid[i][j]){

                    areGensSame = false;

                    break;

                }
            }
        }

        return areGensSame;
    };


    // main

    public static void main( String[] args ) {


    char currentWorldGrid [][] = new char [ GRID_ROWS+2 ] [ GRID_COLUMNS+2 ]; //Adding borders by adding +2
    char tempGrid [][] = new char [ GRID_ROWS + 2 ] [ GRID_COLUMNS+2 ]; //Adding borders by adding +2
    int generationNum = 1;

    // Read world grid

        currentWorldGrid = readNewWorld();
        tempGrid = currentWorldGrid;
        printWorld( currentWorldGrid , generationNum );

        do {

            currentWorldGrid = tempGrid;

            Scanner userinput = new Scanner(System.in);
            System.out.print ("Do you like to see the next generation (y/n) ? ");
            char moveForward = userinput.next().charAt(0);

            if( moveForward == 'n') {

                System.out.println ("End of the simulation of life by user. ");
                break;

            }

            if( moveForward == 'y') {

                tempGrid = nextGenWorld(currentWorldGrid);

                generationNum += 1;

                printWorld( tempGrid , generationNum );

            }

        }while( ! ( isWorldEmpty( tempGrid ) || nextGenIsSame( currentWorldGrid , tempGrid ) ) );


        if( isWorldEmpty( tempGrid ) ) { System.out.println ("End of the simulation of life - the grid has no organism."); }

        if( nextGenIsSame( currentWorldGrid , tempGrid )  ) { System.out.println ("End of the simulation of life - the previous generation is the same as the current generation."); }


    }


}
