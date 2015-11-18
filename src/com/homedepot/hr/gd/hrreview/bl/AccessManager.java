package com.homedepot.hr.gd.hrreview.bl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;





import org.apache.log4j.Logger;

import com.homedepot.hr.gd.hrreview.dto.AccessInfoTO;
import com.homedepot.hr.gd.hrreview.interfaces.Constants;
import com.homedepot.hr.gd.hrreview.util.ApplicationCache;
import com.homedepot.ta.aa.catalina.realm.Profile;


public class AccessManager implements Constants {

	private static final Logger logger = Logger.getLogger(AccessManager.class);
	
	public static AccessInfoTO getAccess(HttpServletRequest request)
	{
		AccessInfoTO access = new AccessInfoTO() ;
		Map<String, Map<String, Boolean>> accessMap =  ApplicationCache.getInstance().getAccessMap();
		Map<String, Boolean> tempResult = new HashMap<String, Boolean>() ;
		
		Profile profile = Profile.getCurrent();
		access.setFirstName(profile.getProperty(Profile.FIRST_NAME));
		access.setCommonName(profile.getProperty(Profile.COMMON_NAME));
		access.setLastName(profile.getProperty(Profile.LAST_NAME));
		access.setLocation(profile.getProperty(Profile.LOCATION_NUMBER));
		
		//for each ldap group
		for(String key: accessMap.keySet())
		{
			//if user is in ldap group
			if(	request.isUserInRole(key))
			{
				logger.debug(String.format("User in role %s", key));
				for(String action: accessMap.get(key).keySet())
				{
					if(tempResult.get(action) == null || tempResult.get(action).equals(false))
					{
						tempResult.put(action, accessMap.get(key).get(action));
					}
					logger.debug(String.format("Action %s, value: %s", action, accessMap.get(key).get(action))) ;
				}
			}

		}
		
		access.setAccessMap(tempResult);
		
		
		return access;
	}

}
