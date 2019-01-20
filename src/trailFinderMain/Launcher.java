package trailFinderMain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.LOG;
/**
 * This class has the "main" function which is the entry point for the application
 * The purpose of this application is to find the best matching trail for a user according to his preference. 
 * The program has the following classes:
 * Launcher: The entry point for the program and it runs the main flow of the application, it is a singleton
 * GetUserData: Gets the user preferences for the trail. For each trail attributes it asks the user to give a score of importance
 * 0-5. The interface in this application is simple CLI. It can be easily changed to a rest API. 
 * TrailsManager: A manager for the trails, it loads the data, parses it and chooses the best trails for the user  
 * Trail: Holds the data of one trail, and calculates its score according to the user preference.
 * TrailWantedFeaturs: A simple POJO that holds the user preference for a trail.
 * LOG: A simple logger implementation. Logs the application errors and debug lines in to trailFinder.log
 */
public class Launcher
{  
   TrailsManager trailsManager = new TrailsManager();
   List<Trail> trailList = new ArrayList<>();

   /**
    * Entry point for the application
    * @param args app arguments
    */ 
   public static void main(String[] args)
   {
      LOG.info("starting app");
      System.out.println("\nTHIS PROGRAM WILL RECOMMEND THE BEST TRAILS IN BOULDER FOR YOU BY ASKING ON YOUR PREFERENCES");
      System.out.println("");
      Launcher launcher = new Launcher();
      launcher.run();
   }

   /**
    * Runs the main flow of the application and prints out the results.
    */
   void run()
   {
      try
      {
         trailsManager.loadTrails();
         GetUserData getUserData = new GetUserData();
         TrailWantedFeaturs trailWantedFeaturs = getUserData.getUserData();
         
         List<Trail>[] bestTrails = trailsManager.getBestTrails(trailWantedFeaturs);
         System.out.println("");
         System.out
               .println("----------------------------------------------------------------------------------");
         System.out.println("");
       
         if (bestTrails[0].size() > 1)
            System.out.println("The best matching trails for you are:");
         else
            System.out.println("The best matching trail for you is:");
         for (Trail t : bestTrails[0])
            System.out.println(t);

         System.out.println("");
         if (bestTrails[1].size() > 1)
            System.out.println("The second best matching trails for you are:");
         else
            System.out.println("The second best matching trail for you is:");
         for (Trail t : bestTrails[1])
            System.out.println(t);
         System.out.println("");

      }
      catch (IOException e)
      {
         LOG.error("Unexpected Exception", e);

      }
   }

}
