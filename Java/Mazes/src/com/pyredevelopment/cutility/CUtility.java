package com.pyredevelopment.cutility;

import java.io.*;

/**
 * CUtility is a class of custom utility objects, it's something of a catch all
 * for me to put any static methods I may use frequently into that don't really belong anywhere else
 */
public class CUtility
{

    /**
     * This allows you to write objects to file and (hopefully) catches any possible errors
     * to make life a little easier then re-writing this anytime you want to write to file
     * @param file The established File that will be writted to
     * @param obj The serializable Object you will be writing
     */
    public static void WriteObjectToFile(File file, Serializable obj)
    {
        // Try: Let's avoid any uncaught errors, please
        try
        {
            // Create a file Output stream based on passed file
            FileOutputStream fileOut = new FileOutputStream(file);

            // And then an ObjectOutputStream based on that
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            // Write the object
            objectOut.writeObject(obj);

            // And close the stream behind us
            objectOut.close();

        } catch (FileNotFoundException e) { // Catch error if file was invalid
            e.printStackTrace();
        } catch (IOException e) { // Or if a IO exception occurs
            e.printStackTrace();
        }
    }

    /**
     * This is designed to simplify loading a file into an object.
     * Note: It's advised to create a wrapper around this to filter and test as you unserialize the object
     * @param file That you want loaded
     */
    public static Object LoadObjectFromFile(File file)
    {
        try {
            // Create the input stream
            FileInputStream fileIn = new FileInputStream(file);

            // Object input strea
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            // Read the object from file
            Object obj = objectIn.readObject();

            // Close the stream behind us
            objectIn.close();

            // return the object
            return obj;
        } catch (IOException | ClassNotFoundException e) { // Catch if file isn't valid
            e.printStackTrace();
        }// Catch IO exception
        // Catch class error


        // Otherwise if something fails return null
        return null;
    }

}
