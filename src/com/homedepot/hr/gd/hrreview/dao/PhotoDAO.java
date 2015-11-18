package com.homedepot.hr.gd.hrreview.dao;

import java.sql.Blob;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.homedepot.hr.gd.hrreview.interfaces.DAOConstants;
import com.homedepot.ta.aa.dao.Resultant;
import com.homedepot.ta.aa.dao.Results;
import com.homedepot.ta.aa.dao.builder.DAO;
import com.homedepot.ta.aa.dao.exceptions.QueryException;

public class PhotoDAO implements DAOConstants {

	private static final Logger logger = Logger.getLogger(PhotoDAO.class);
	

	/*
	 * Insert Photo in SPEMPL_PHOTO_IMG using Tesseract ID
	 */
	public static void insertPhoto(Blob imageBlob, String associateID, String userID)
			throws QueryException, SQLException {
		if (logger.isDebugEnabled()) {
			logger.debug("start insertPhoto(Blob)");
			logger.debug("associateID: " + associateID);
			logger.debug("userID"+ userID);
		}
		logger.info(imageBlob.toString());

		String hrSystemEmploymentID = getTesseractID(associateID);

			//try updating photo in SPEMPL_PHOTO_IMG before inserting
			  int count = DAO.useJNDI(workForceSuccessionPlanning)
			  .setSQL("UPDATE SPEMPL_PHOTO_IMG " + "SET EMPL_IMG_VAL = ?, " +
			  "LAST_UPD_SYSUSR_ID = ?, " +
			  "LAST_UPD_TS = CURRENT TIMESTAMP " +
			  "WHERE HR_SYS_EMPL_ID = ?", imageBlob, userID,
			  hrSystemEmploymentID).count();
			
			 //if no updates try insert
			if (count == 0) {
				count = DAO
						.useJNDI(workForceSuccessionPlanning)
						.setSQL("INSERT INTO SPEMPL_PHOTO_IMG "
								+ "(HR_SYS_EMPL_ID, EMPL_IMG_VAL, LAST_UPD_SYSUSR_ID, LAST_UPD_TS) "
								+ "VALUES(?, ?, ?, CURRENT TIMESTAMP)",
								hrSystemEmploymentID, imageBlob, userID)
						.count();
			}
	

		if (logger.isDebugEnabled()) {
			if (count == 1)
				logger.info("insertPhoto SUCCESS");
			else
				logger.info("insertPhoto FAIL");
			logger.debug("finish insertPhoto()");
		}
	}

	/*
	 * Get Photo Blob from SPEMPL_PHOTO_IMG using associate ID via HR_EMPL_TSRT_XREF cross reference
	 */
	public static Blob getPhoto(String associateID) throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getPhoto");
		}

		String hrSystemEmploymentID = getTesseractID(associateID);

		if (logger.isDebugEnabled()) {
			logger.debug("input: " + hrSystemEmploymentID);
			logger.debug("executing the query");
		}

		
			return DAO
					.select("WorkforceSuccessionPlanning.1.readSuccessionPlanEmployeePhotoImageByInputList")
					.input("humanResourcesSystemEmployeeId",
							hrSystemEmploymentID)
					.results(new Resultant<Blob>() {
						@Override
						public Blob read(Results results) throws Exception {
							Blob returnValue = null;
							while (results.next()) {
								returnValue = new javax.sql.rowset.serial.SerialBlob(
										results.getBytes("employeeImageValue"));

							}
							return returnValue;
						}
					});
	}

	/*
	 * get Tesseract ID from the associate ID
	 */
	public static String getTesseractID(String associateID) throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("Executing query for hr system employment id in getTesseractID");
		}

		String hrSystemEmploymentID = null;
	
			hrSystemEmploymentID = DAO
					.useJNDI(workForceSuccessionPlanning)
					.setSQL("SELECT TSRT_EMPL_ID " 
							+ "FROM HR_EMPL_TSRT_XREF "
							+ "WHERE Z_EMPLID = ? "
							+ "WITH UR", associateID)
					.get(String.class);
		

		if (logger.isDebugEnabled()) {
			logger.debug("getTesseractID completed.  Returning "
					+ hrSystemEmploymentID);
		}

		return hrSystemEmploymentID;

	}
}
