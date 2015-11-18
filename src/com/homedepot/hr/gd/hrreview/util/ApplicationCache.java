package com.homedepot.hr.gd.hrreview.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ApplicationCache {

	private static final Logger LOG = Logger.getLogger(ApplicationCache.class);
	
	// Singleton instance variable
	private static ApplicationCache instance;
	
	private static Map<String, Map<String, Boolean>> accessMap ;
	
	// Default Constructor. 
	private ApplicationCache()
	{
		LOG.debug("Entering ApplicationCache() constructor.");
		try {
			loadCache() ;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			
			LOG.error("Error loading Cache");
			LOG.error(e.getMessage());
		}
		LOG.debug("Exiting ApplicationCache() constructor.");
	}
	
	// Returns the singleton object.  
	public static synchronized ApplicationCache getInstance()
	{
		// if the cache hasn't been initialized yet, initialize it
		if(instance == null)
		{
			instance = new ApplicationCache();
			LOG.debug("Application Cache initialized");
		} // end if(mInstance == null)
			
		return instance;
	} // end function getInstance()
	
	private static synchronized void loadCache() throws ParserConfigurationException, SAXException, IOException
	{
		accessMap = new HashMap<String, Map<String, Boolean>>() ;
		URL url = ApplicationCache.class.getResource("..//resources//Permissions.xml");
		File xmlFile =new File( url.getFile()) ;
		LOG.debug(xmlFile.getAbsolutePath());
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		
		Document doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();
		
		LOG.debug(doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("group") ;
		
		for(int temp = 0 ;temp < nList.getLength(); temp++)
		{
			Map <String, Boolean> actionMap= new HashMap<String, Boolean>() ;
			Node n = nList.item(temp);
			Element e = (Element) n ;
			LOG.debug(e.getAttribute("id"));
			
			if (n.getNodeType() == Node.ELEMENT_NODE)
			{
				Element actionElement = (Element) n ;
				
				NodeList actionNodeList = actionElement.getElementsByTagName("action");
				
				LOG.debug(actionNodeList.getLength());
				for (int t = 0; t <actionNodeList.getLength(); t++)
				{
					Node an = actionNodeList.item(t) ;
					Element ae = (Element) an ;
					
					LOG.debug(ae.getAttribute("id")+" "+ae.getTextContent());
					
					//if "true" put true for access, everything else is false
					if(ae.getTextContent().trim().equalsIgnoreCase("true"))
						actionMap.put(ae.getAttribute("id"), true);
					else
						actionMap.put(ae.getAttribute("id"), false);
					
					
					
					
				}//end for actionNodeList
			}//end if
			
			accessMap.put(e.getAttribute("id"), actionMap) ;
			
		}//end forloop
	}
	
	public Map<String, Map<String, Boolean>> getAccessMap()
	{	
		
		return  accessMap ;
	}
	
}
