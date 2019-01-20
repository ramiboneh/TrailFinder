package trailFinderMain;

import java.util.InputMismatchException;
import java.util.Scanner;

import utils.LOG;


/**
 Gets the user preferences for the trail. For a few of the trail attributes it ask the user to give a score of importance
 0-5, and the level of difficulty the user want for the trail. The interface in this application is simple CLI. It can be easily changed to a rest API.
 It stores the preferences in the class TrailWantedFeaturs back to the trail (trough the TrailsManager) to calculate the trail score.  
*/
public class GetUserData
{
   Scanner sc = new Scanner(System.in);

   /**
    * Uses the console to let the user enter its preferences 
    * @return
    */
   TrailWantedFeaturs getUserData()
   {
      TrailWantedFeaturs ret = new TrailWantedFeaturs();     
      
      ret.restroomsVal = getItemData("are restrooms");
      ret.picnicVal = getItemData("are picnic tables");
      ret.fishingVal = getItemData("is fishing");
      ret.noFeeVal = getItemData("is free entry");
      ret.bikeTrailVal = getItemData("is a bike trail");
      ret.grillsVal = getItemData("are grills");
      ret.campingVal = getItemData("is camping");
      ret.trailDifficultyVal = getItemData("is the difficulty of the trail");
      //Will ask the user to what kind difficulty he wants only if the user answered that he is intersted in it  
      if (ret.trailDifficultyVal > 0)
      {
         //Validates the Max > Min
         do
         {
            ret.minTrailDifficulty = getTrailDifficultyMinOrMax("minimum");
            ret.maxTrailDifficulty = getTrailDifficultyMinOrMax("maximum");
            if (ret.maxTrailDifficulty < ret.minTrailDifficulty)
            {
               System.out.println("Max difficulty cannot be smaller then your min input");
            }
         }
         while (ret.maxTrailDifficulty < ret.minTrailDifficulty);
      }
      return ret;
   }

   /**
    * Gets from the user how impotent a trail attribute is from 0-5.
    * @param subject The attribute to ask about
    * @return The score the user gave
    */
   int getItemData(String subject)
   {
      int ret;

      do
      {
         System.out.println("For a scale 0-5 how importent " + subject + " for you?");
         try
         {
            ret = sc.nextInt();
         }
         //Catches  when the user does not types an integer
         catch (InputMismatchException e)
         {
            ret = -1;
            LOG.info("The user printed bad input", e);
            sc = new Scanner(System.in);
         }
         //If the input is not between 0 to 5 asks the user to enter data again. 
         if (ret < 0 || ret > 5)
            System.out.println("The range is 0 to 5");
      }
      while (ret < 0 || ret > 5);

      return ret;
   }

   /**
    * Gets from the user the preference for the difficulty of the trail 1 to 8
    * This function is used to get the min difficulty the user wants and the max difficulty the user wants   
    * @param subject
    * @return
    */
   int getTrailDifficultyMinOrMax(String subject)
   {
      int ret;
      do
      {
         System.out.println(
               "For a scale 1-8 (8 is the most difficult) what is the " + subject + " difficulty for you?");
         try
         {
            ret = sc.nextInt();
         }
         //Catches  when the user does not types an integer
         catch (InputMismatchException e)
         {
            ret = -1;
            LOG.info("The user printed bad input", e);
            sc = new Scanner(System.in);
         }
         //If the input is not between 1 to 8 asks the user to enter data again. 
         if (ret < 1 || ret > 8)
            System.out.println("The range is 1 to 8");
      }
      while (ret < 1 || ret > 8);

      return ret;
   }
}
