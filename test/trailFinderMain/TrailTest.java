package trailFinderMain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TrailTest
{
   /**
    * Checks that the Trail constructor parses a comma delimited string correctly
    */
   @Test
   public void createObjectTest()
   {
      String data = "18,No,No,No, ,TH,513,T1,66 S. Cherryvale Rd,No,Yes,Yes,1.0,No,1.0,50,Roadbase/Crusher,No,No,No,No,Easy,Yes,Yes,Cherryvale Offices,Pull Through,2005-12-31 00:00:00,2099-12-31 00:00:00,No,No,Cherryvale,Yes";
      Trail trailData = new Trail(data);

      assertEquals(18, trailData.FID);
      assertEquals(false, trailData.restrooms);
      assertEquals(false, trailData.picnic);
      assertEquals(false, trailData.fishing);
      assertEquals("66 S. Cherryvale Rd", trailData.address);
      assertEquals(false, trailData.fee);
      assertEquals(true, trailData.bikeTrail);
      assertEquals(false, trailData.grills);
      assertEquals(false, trailData.camping);
      assertEquals(1, trailData.trailDifficulty);
      assertEquals("Cherryvale", trailData.accessName);
   }
}
