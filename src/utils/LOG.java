package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

/**
 * A simple logger implementation. Logs the application error and debug lines in to trailFinder.log
 */
public class LOG
{
   static final String FILE_NAME = System.getProperty("user.dir") + "/trailFinder.log";
   static
   {
      try
      {
         File file = new File(FILE_NAME);
         Files.deleteIfExists(file.toPath());
      }
      catch (IOException e)
      {
         System.err.println("Error: " + e.getMessage());
         throw new UnsupportedOperationException("Unexpected Exception", e);
      }
   }   
  

   static public void error(String msg, Exception e)
   {
      String txt = "ERROR:" + msg + " , " + e.toString() + "\n";
      writeToFile(txt);

   }
   
   static public void debug(String msg, Exception e)
   {
      String txt = "DEBUG:" + msg + " , " + e.toString() + "\n";
      writeToFile(txt);
   }
   
   static public void debug(String msg)
   {
      String txt = "DEBUG:" + msg +"\n";
      writeToFile(txt);
   }
   
   static public void info(String msg)
   {
      String txt = "INFO:" + msg +"\n";
      writeToFile(txt);
   }
   
   static public void info(String msg, Exception e)
   {
      String txt = "INFO:" + msg + " , " + e.toString() + "\n";
      writeToFile(txt);
   }

   static private void writeToFile(String txt)
   {
      BufferedWriter out = null;

      try
      {
         FileWriter fstream = new FileWriter(FILE_NAME, true);
         out = new BufferedWriter(fstream);
         out.write(txt);
      }

      catch (IOException e)
      {
         System.err.println("Error: " + e.toString());
      }

      finally
      {
         if (out != null)
         {
            try
            {
               out.close();
            }
            catch (IOException e)
            {
               System.err.println("Error: " + e.getMessage());
               throw new UnsupportedOperationException("Unexpected Exception", e);

            }
         }
      }
   }

}
