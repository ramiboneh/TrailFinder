package trailFinderMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import utils.LOG;

/**
 * A manager for the trails, it loads the data, parses it and chooses the best trails for the user  
 * The data is loaded directly from the Boulder Colorado web site to get the most updated data.
 * In case it fails to load from the web site it takes the data from a local file that was taken offline from this site. 
 */
public class TrailsManager
{
   private static String URL_TO_DATA = "https://www-static.bouldercolorado.gov/docs/opendata/OSMPTrailheads.csv";
   private static String FILE_PATH = "/resources/OSMPTrailheads.csv";
   private Trail[] trails;//The list of the all the trails loaded

   
   /**
    * Gets the data from the web
    * @return A list of the trails in a list of strings
    * @throws IOException
    */
   private List<String> loadRawDataFromWeb()
      throws IOException
   {
      List<String> ret = new ArrayList<String>();
      System.setProperty("http.agent", "Chrome");
      URL url = new URL(URL_TO_DATA);
      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
      String inputLine;      
      while ((inputLine = in.readLine()) != null)
      {
         ret.add(inputLine);
      }
      in.close();
      return ret;
   }

   /**
    * Gets the data from the file
    * @return the file data as one long string
    * @throws IOException
    */
   private String loadRawDataFromFile()
      throws IOException
   {
      byte[] encoded = Files
            .readAllBytes(Paths.get(System.getProperty("user.dir") + FILE_PATH));
      return new String(encoded, "UTF-8");
   }

   
   /**
    * Converts the list of Strings of trails to a list of Trail Objects 
    * @param lines
    * @return the list of trail objects
    */
   private Trail[] parseData(String[] lines)
   {
      Trail ret[] = new Trail[lines.length - 1];// Line 0 is headers
      // Starting at line 1, line 0 is the headers
      for (int i = 1; i < lines.length; i++)
      {
         ret[i - 1] = new Trail(lines[i]);
      }
      return ret;
   }

   /**
    * Loads the trails data in to the Object member trails   
    * @throws IOException if fails to load from web and local file
    */
   public void loadTrails()
      throws IOException
   {
      loadTrails(true);
   }

   /**
    * Loads the trails data in to the Object member trails   
    * @param fromWeb true to try to load from the web false to load from the local file (False should be used for unit testing)
    * @throws IOException
    */
   void loadTrails(boolean fromWeb)
      throws IOException
   {
      if (fromWeb)
      {
         try
         {
            List<String> rawDataList = loadRawDataFromWeb();
            String[] arr = new String[rawDataList.size()]; 
            trails = parseData(rawDataList.toArray(arr));
            return;
         }
         catch (IOException e)
         {
            LOG.error("Cannot read data from web:" + URL_TO_DATA, e);
            System.out.println("Cannot read data from web:" + URL_TO_DATA);
            System.out.println("Will use static local data from January 2019");
         }         
      }
      //If it failed from the web try from local file 
      String rawData = loadRawDataFromFile(); 
      String[] lines = rawData.split("\n");
      trails = parseData(lines);

   }

   /**
    * Returns the member list of trails
    * @return the list of trails
    */
   Trail[] getTrailsData()
   {
      return trails;
   }

   /**
    * On each trail object in the trail list calls the trail class method to calculate the trail score according to the user
    * preferences. Then it iterates once through the list to find the best matching trails 
    * @param trailWantedFeaturs The user preferences for the trail
    * @return Two lists. The first list has all the trails that got the same highest score 
    * the second list all the trails that got the same second highest score. 
    */
   public List<Trail>[] getBestTrails(TrailWantedFeaturs trailWantedFeaturs)
   {        
      @SuppressWarnings("unchecked")
      ArrayList<Trail>[] ret = new ArrayList[2];
      ret[0] = new ArrayList<Trail>();
      ret[1] = new ArrayList<Trail>();
      for (Trail trail : trails)
      {
         trail.calculateTrailScore(trailWantedFeaturs);
      }
      
      /**
       * The following code goes through the list and returns two lists of the best matching trails.
       * It could have been done through a simple java array sort, but this will be O(NlogN) complexity
       * So the following code is just O(N) complexity 
       */
      
      //Put in the first list the first item of the main trail list, and continue while the score of the next trails equals to the first one.
      ret[0].add(trails[0]);
      int i = 0;
      while (i + 1 < trails.length && (trails[i].getTrailScore() == trails[i + 1].getTrailScore()))
      {
         ret[0].add(trails[++i]);
      }
      i++;
      
      //If all the trails in the main trail list have the same score we are done.
      if (i == trails.length)
         return ret;
      
      //Continue and put in the second best trail list the trail after it and continue until a trail has a different score
      ret[1].add(trails[i]);
      while (i + 1 < trails.length && (trails[i].getTrailScore() == trails[i + 1].getTrailScore()))
      {
         ret[1].add(trails[++i]);
      }
      i++;
      
      //Substitute the best and second best if the second has a higher score
      if (ret[0].get(0).getTrailScore() < ret[1].get(0).getTrailScore())
      {
         ArrayList<Trail> temp = ret[0];
         ret[0] = ret[1];
         ret[1] = temp;
      }      
      //Continue iterating the main trail list and Substitute the trails
      //to get the highest same scoring in the first list and the second scoring 
      //in the second list.  
      for (; i < trails.length; i++)
      {
         if (trails[i].getTrailScore() > ret[0].get(0).getTrailScore())
         {
            ret[1] = ret[0];
            //Have to new ret[0], if it was just cleared it will create a bug where ret[0] and ret[1] will point to the same list  
            ret[0] = new ArrayList<Trail>();
            ret[0].add(trails[i]);
         }
         else if (trails[i].getTrailScore() == ret[0].get(0).getTrailScore())
         {
            ret[0].add(trails[i]);
         }
         else if (trails[i].getTrailScore() > ret[1].get(0).getTrailScore())
         {
            ret[1].clear();
            ret[1].add(trails[i]);
         }
         else if (trails[i].getTrailScore() == ret[1].get(0).getTrailScore())
         {
            ret[1].add(trails[i]);
         }

      }
      return ret;

   }   
}
