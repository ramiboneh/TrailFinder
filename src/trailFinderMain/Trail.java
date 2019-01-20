package trailFinderMain;

/**
 * Holds the data of one trail, and calculates its score according to the user preference.
 * It does not hold all the attributes of the trail just the most important attributes
 * and also to keep this demo app a bit smaller.     
 */
public class Trail
{
   // The trail attributes
   final int FID;
   final String accessName;
   final boolean restrooms;
   final boolean picnic;
   final boolean fishing;
   final String address;
   final boolean fee;
   final boolean bikeTrail;
   final boolean grills;
   final boolean camping;
   final int trailDifficulty;
   final String strTrailDifficulty;

   // The score the trail gets the higher the better
   private int trailScore;

   /**
    * A constructor that takes the data in a comma delimited string parses the input, takes the data 
    * from the right corresponding index and normalizes the data to the Object member
    * the needed format and taked the dta
    * 
    * @param input the trail attributes in a comma delimited string
    */
   public Trail(String input)
   {
      String[] words = input.split(",");
      FID = Integer.parseInt(words[0]); // The id of the trail
      restrooms = getBooleanFromYesNo(words[1]);
      picnic = getBooleanFromYesNo(words[2]);
      fishing = getBooleanFromYesNo(words[3]);
      address = words[8];
      fee = getBooleanFromYesNo(words[9]);
      bikeTrail = getBooleanFromYesNo(words[11]);
      grills = getBooleanFromYesNo(words[13]);
      camping = getBooleanFromYesNo(words[19]);
      strTrailDifficulty = words[21];
      trailDifficulty = trailDifficultyToInt(strTrailDifficulty);
      accessName = words[30];
   }

   /**
    * Returns the trail score
    * 
    * @return the trail score
    */
   public int getTrailScore()
   {
      return trailScore;
   }

   /**
    * Changes yes (Ignore case) to true, otherwise false
    * 
    * @param input a string that should be yes or no
    * @return true for yes and false otherwise
    */
   private boolean getBooleanFromYesNo(String input)
   {
      if (input.equalsIgnoreCase("Yes"))
         return true;
      return false;

   }

   /**
    * Converts the trail difficulty from a descriptive string to a number1-8
    * 
    * @param input
    * @return the trail difficulty 1-8 (8 most difficult) 0 if the input string does not matches
    */
   private int trailDifficultyToInt(String input)
   {
      if (input.equals("Easy"))
         return 1;
      if (input.equals("Easy to Moderate"))
         return 2;
      if (input.equals("Moderate"))
         return 3;
      if (input.equals("Easy-Difficult"))
         return 4;
      if (input.equals("Moderate-Difficult"))
         return 5;
      if (input.equals("Difficult"))
         return 6;
      if (input.equals("Moderate-Most Difficult"))
         return 7;
      if (input.equals("Most Difficult"))
         return 8;
      return 0;
   }

   /**
    * Calculates the trail score according to the user input and sets it in trailScore class member
    * 
    * @param trailWantedFeaturs the user input on the attributes he wants in the trail
    */
   void calculateTrailScore(TrailWantedFeaturs trailWantedFeaturs)
   {
      trailScore = 0;
      trailScore += restrooms ? trailWantedFeaturs.restroomsVal : 0;
      trailScore += picnic ? trailWantedFeaturs.picnicVal : 0;
      trailScore += fishing ? trailWantedFeaturs.fishingVal : 0;
      trailScore += fee ? 0 : trailWantedFeaturs.noFeeVal; // The user will prefer a no fee trail
      trailScore += bikeTrail ? trailWantedFeaturs.bikeTrailVal : 0;
      trailScore += grills ? trailWantedFeaturs.grillsVal : 0;
      trailScore += camping ? trailWantedFeaturs.campingVal : 0;

      // If the difficulty of the trail is between the user preferences. adds the importance of the
      // difficulty to trail score.
      if (trailWantedFeaturs.minTrailDifficulty <= trailDifficulty
            && trailWantedFeaturs.maxTrailDifficulty >= trailDifficulty)
      {
         trailScore += trailWantedFeaturs.trailDifficultyVal;
      }
   }

   /**
    * Prints the trails data out. Being use to show the data for the trails the app chose for him. 
    */
   @Override
   public String toString()
   {
      return "Trail [FID=" + FID + ", accessName=" + accessName + ", restrooms=" + restrooms + ", picnic="
            + picnic + ", fishing=" + fishing + ", address=" + address + ", fee=" + fee + ", bikeTrail="
            + bikeTrail + ", grills=" + grills + ", camping=" + camping + ", trailDifficulty="
            + strTrailDifficulty + ", score=" + trailScore + "]";
   }
}
