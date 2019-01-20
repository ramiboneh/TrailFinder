package trailFinderMain;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class TrailsManagerTest
{
   /**
    * Unit test reads the local file with data and checks for a two sets of user 
    * input we get the expected output.
    * @throws IOException if fails to load data from local file
    */
   @Test
   public void getBestTrailsTest()
      throws IOException
   {
      TrailsManager trailsManager = new TrailsManager();
      trailsManager.loadTrails(false);//Unit test read static data from local file
      TrailWantedFeaturs trailWantedFeaturs = new TrailWantedFeaturs();

      trailWantedFeaturs.bikeTrailVal = 2;
      trailWantedFeaturs.noFeeVal = 3;
      trailWantedFeaturs.trailDifficultyVal = 4;
      trailWantedFeaturs.minTrailDifficulty = 7;
      trailWantedFeaturs.maxTrailDifficulty = 8;
      trailWantedFeaturs.fishingVal = 1;

      List<Trail>[] bestTrails = trailsManager.getBestTrails(trailWantedFeaturs);
      
      assertEquals(1, bestTrails[0].size());
      assertEquals(6,bestTrails[1].size());
      assertEquals(11, bestTrails[0].get(0).FID);
      assertEquals(9, bestTrails[0].get(0).getTrailScore());
      assertEquals(6, bestTrails[1].get(0).getTrailScore());
      assertEquals(6, bestTrails[1].get(3).getTrailScore());

      trailWantedFeaturs = new TrailWantedFeaturs();
      trailWantedFeaturs.bikeTrailVal = 2;
      trailWantedFeaturs.noFeeVal = 1;
      trailWantedFeaturs.trailDifficultyVal = 5;
      trailWantedFeaturs.minTrailDifficulty = 2;
      trailWantedFeaturs.maxTrailDifficulty = 4;
      trailWantedFeaturs.fishingVal = 2;
      trailWantedFeaturs.restroomsVal = 4;

      bestTrails = trailsManager.getBestTrails(trailWantedFeaturs);
      
      assertEquals(1, bestTrails[0].size());
      assertEquals(1, bestTrails[1].size());
      assertEquals(15,bestTrails[0].get(0).FID);
      assertEquals(30, bestTrails[1].get(0).FID);
      assertEquals(12,bestTrails[0].get(0).getTrailScore());
      assertEquals(11, bestTrails[1].get(0).getTrailScore());

   }

}
