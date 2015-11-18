package com.homedepot.hr.gd.hrreview.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.homedepot.hr.gd.hrreview.dto.NextPositionDTO;
import com.homedepot.hr.gd.hrreview.dto.RatingDTO;
import com.homedepot.hr.gd.hrreview.dto.TesseractRatingSearchDTO;
import com.homedepot.hr.gd.hrreview.interfaces.Constants;
import com.homedepot.hr.gd.hrreview.interfaces.DAOConstants;
import com.homedepot.ta.aa.dao.builder.DAO;
import com.homedepot.ta.aa.dao.exceptions.QueryException;

public class PerformanceDevelopmentDAO implements DAOConstants, Constants {

	private static final Logger logger = Logger
			.getLogger(PerformanceDevelopmentDAO.class);

	public static List<RatingDTO> getRatingDetails(String associateId)
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getCurrentRatingDetails");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readNlsEvaluateCategoryCodeAndRatingCodeDetails") //TODO: Verify contract name, version, and replace values below
				.input("languageCode", DAOConstants.lang_code) // optional
				.inputAllowNull("zeroEmplid", associateId) // can be null, optional

				.list(RatingDTO.class);
	}

	public static List<NextPositionDTO> getNextPositionDetails(
			String associateId) throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start readSuccessionPlanEmployeeCompanyPlanningAndTesseractCrossReferenceDetails");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readSuccessionPlanEmployeeCompanyPlanningAndTesseractCrossReferenceDetails") //TODO: Verify contract name, version, and replace values below
				.inputAllowNull("zeroEmplid", associateId) // can be null, optional

				.list(NextPositionDTO.class);
	}

	public static List<TesseractRatingSearchDTO> getTesseractRatingDetails(String associateId)
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start readNlsEvaluateCategoryAndHumanResourcesRatingDetails");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readNlsEvaluateCategoryAndHumanResourcesRatingDetails") //TODO: Verify contract name, version, and replace values below
				.input("evaluateCategoryCode", (short) 1) // optional
				.input("evaluatedCategoryCode", (short) 2) // optional
				.input("evaluationCategoryCode", (short) 4) // optional
				.input("languageCode", DAOConstants.lang_code) // optional
				.input("evaluationLanguageCode", DAOConstants.lang_code) // optional
				.input("ratingLanguageCode", DAOConstants.lang_code) // optional
				.inputAllowNull("zeroEmployeeId", associateId) // can be null, optional
				.input("recordStatus", "A") // optional
				.input("evaluateRatingCode", (short) 27) // optional
				.input("evaluatedRatingCode", (short) 27) // optional
				.input("evaluationRatingCode", (short) 27) // optional
				.inputAllowNull("resourceZeroEmployeeId", associateId) // can be null, optional
				.input("resourceRecordStatus", "A") // optional
				.input("resourceEvaluateRatingCode", (short) 27) // optional
				.input("resourceEvaluatedRatingCode", (short) 27) // optional
//				.qualify("evaluatedRatingCodeGreaterThanEqualTo", false) // optional
//				.qualify("evaluatedRatingCodeGreaterThan", false) // optional
				.qualify("evaluatedRatingCodeLessThanEqualTo", true) // optional
//				.qualify("evaluatedRatingCodeLessThan", false) // optional
				.qualify("evaluateRatingCodeLessThanEqualTo",true)
//				.qualify("evaluatedRatingCodeEqualTo", false) // optional
//				.qualify("evaluatedRatingCodeNotEqualTo", false) // optional
//				.qualify("evaluatedRatingCodeGreaterThanEqualTo", false) // optional
//				.qualify("evaluatedRatingCodeGreaterThan", false) // optional
				.qualify("evaluatedRatingCodeLessThanEqualTo", true) // optional
//				.qualify("evaluatedRatingCodeLessThan", false) // optional
//				.qualify("evaluatedRatingCodeEqualTo", false) // optional
//				.qualify("evaluatedRatingCodeNotEqualTo", false) // optional
//				.qualify("evaluationRatingCodeGreaterThanEqualTo", false) // optional
//				.qualify("evaluationRatingCodeGreaterThan", false) // optional
				.qualify("evaluationRatingCodeLessThanEqualTo", true) // optional
//				.qualify("evaluationRatingCodeLessThan", false) // optional
//				.qualify("evaluationRatingCodeEqualTo", false) // optional
//				.qualify("evaluationRatingCodeNotEqualTo", false) // optional
//				.qualify("resourceEvaluateRatingCodeGreaterThanEqualTo", false) // optional
				.qualify("resourceEvaluateRatingCodeGreaterThan", true) // optional
//				.qualify("resourceEvaluateRatingCodeLessThanEqualTo", false) // optional
//				.qualify("resourceEvaluateRatingCodeLessThan", false) // optional
//				.qualify("resourceEvaluateRatingCodeEqualTo", false) // optional
//				.qualify("resourceEvaluateRatingCodeNotEqualTo", false) // optional
//				.qualify("resourceEvaluatedRatingCodeGreaterThanEqualTo", false) // optional
				.qualify("resourceEvaluatedRatingCodeGreaterThan", true) // optional
//				.qualify("resourceEvaluatedRatingCodeLessThanEqualTo", false) // optional
//				.qualify("resourceEvaluatedRatingCodeLessThan", false) // optional
//				.qualify("resourceEvaluatedRatingCodeEqualTo", false) // optional
//				.qualify("resourceEvaluatedRatingCodeNotEqualTo", false) // optional
				.list(TesseractRatingSearchDTO.class);
	}
}
