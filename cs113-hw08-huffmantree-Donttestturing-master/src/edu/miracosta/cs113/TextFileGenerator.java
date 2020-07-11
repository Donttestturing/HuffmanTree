package edu.miracosta.cs113;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class TextFileGenerator {
    private static final String USER_AGENT = "Mozilla/5.0"; //needed for HTTP request
    private static final String VALID_SYMBOLS = " !.?\t\n";

    /**
     * A getter of raw html from a url, outputting to a file
     * @param url The url to fetch
     * @param outputFilename The file we want to output into
     * @throws IOException
     */
    public static void makeCleanFile(String url, String outputFilename) throws IOException {
        URL http = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) http.openConnection();

        // optional default is GET
        conn.setRequestMethod("GET");
        //add request header
        conn.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = conn.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        //Only execute if we had a 200 response code
        if (responseCode == 200) {
            try {
                PrintWriter cleanFile = new PrintWriter(new FileOutputStream(outputFilename));
                Scanner dirtyFile = new Scanner(conn.getInputStream());

                while(dirtyFile.hasNextLine()) {
                    cleanFile.println( TextFileGenerator.cleanString( dirtyFile.nextLine() ) );
                }

                dirtyFile.close();
                cleanFile.close();
                System.out.println("Downloaded webpage (and cleaned) to text file :D.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid response code.");
        }
        conn.disconnect();
    }

    /**
     * Take in an unParsed String and clean it, only leaving characters
     * that are in the whiteList
     * @param unParsed The dirty/unparsed line
     * @return The cleaned line
     */
    public static String cleanString(String unParsed) {
        char current;
        StringBuilder returnString = new StringBuilder();

        // Loop through each char and if it is within the whitelist,
        // then add it to the StringBuilder
        for(int i = 0; i < unParsed.length(); i++) {
            current = unParsed.charAt(i);

            if(TextFileGenerator.withinWhiteList(current)) {
                returnString.append(current);
            }
        }
        return returnString.toString();
    }

    /**
     * Check to see if the ascii value is within our whiteList
     * @param ascii The ascii of the character we are checking
     * @return True if it is within the whitelist, false if not
     */
    public static boolean withinWhiteList(char ascii) {
        //If it is a valid symbols, digit, capital letter or lower case letter
        return VALID_SYMBOLS.indexOf(ascii) != -1 || ((ascii >= '0' && ascii <= '9') || (ascii >= 'A' && ascii <= 'Z')
                || (ascii >= 'a' && ascii <= 'z'));
    }

    /**
     * Counts number of unicode chars in a text file
     *
     * @param filename file to open and count characters for
     * @return number of unicode characters (includes whitespace, etc.)
     */
    public static int getNumChars(String filename) {
        int count = 0;

        try {
            Scanner inputFile = new Scanner(new FileReader(filename));
            while (inputFile.hasNextLine()) {
                count += inputFile.nextLine().length() + 1; //add 1 to account for newline
            }
            inputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        count--;//accounting for end of file (EOF) not having newline char

        return count;
    }
}