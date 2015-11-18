package com.homedepot.hr.gd.hrreview.util;

public class StoreUtil {
	
	public final static String   HOME_DEPOT           = "HD";
	public final static String   HD_STRATEGIC_MARKET  = "HDSM";
	public final static String   EXPO                 = "EXPO";
	public final static String   VILLAGERS_HARDWARE   = "VILH";
	public final static String   DOMESTIC_DC          = "DC";
	public final static String   INTERNATIONAL_DC     = "IDC";
	public final static String   DISTRIBUTION_CENTER  = "DC";

	public static boolean isHomeDepot(String storeTypeCd){ 
		return (storeTypeCd.equals(HOME_DEPOT) || storeTypeCd.equals(HD_STRATEGIC_MARKET)); 
	}

	public static boolean isStrategicMarket(String storeTypeCd){ 
		return storeTypeCd.equals(HD_STRATEGIC_MARKET); 
	}
	
	public static boolean isEXPO(String storeTypeCd){ 
		return storeTypeCd.equals(EXPO); 
	}
	
	public static boolean isVillagersHardware(String storeTypeCd){ 
		return storeTypeCd.equals(VILLAGERS_HARDWARE); 
	}
	
	public static boolean isDistributionCenter(String storeTypeCd){ 
		return storeTypeCd.equals(DISTRIBUTION_CENTER); 
	}
	
	public static String getExternalLocationID(String storeTypeCd, String storeNumber) {
      
      String pfx;
      
      if (isHomeDepot(storeTypeCd)){
      	pfx = "HDO";
      }
      else if (isEXPO(storeTypeCd)){
      	pfx = "HDE";
      }
      else if (isDistributionCenter(storeTypeCd)){
      	pfx = "HDD";
      }
      else{
      	pfx = "HDO";
      }
      
      return pfx + storeNumber;
   }
}
