package com.homedepot.hr.gd.hrreview.dao;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.homedepot.hr.gd.hrreview.bl.AdvancedSearch;
import com.homedepot.hr.gd.hrreview.dto.AdvancedQuickSearchCategoryDTO;
import com.homedepot.hr.gd.hrreview.dto.AdvancedSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.AdvancedSearchCategoryDTO;
import com.homedepot.hr.gd.hrreview.dto.ApplyAllAssociateDTO;
import com.homedepot.hr.gd.hrreview.dto.ApplyAssociateDTO;
import com.homedepot.hr.gd.hrreview.dto.FindDTO;
import com.homedepot.hr.gd.hrreview.dto.LoadQueryDTO;
import com.homedepot.hr.gd.hrreview.dto.PotentialPerformanceSearchDTO;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewApplLogMessage;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewException;
import com.homedepot.hr.gd.hrreview.interfaces.Constants;
import com.homedepot.hr.gd.hrreview.interfaces.DAOConstants;
import com.homedepot.hr.gd.hrreview.request.FindRequest;
import com.homedepot.ta.aa.dao.Inputs;
import com.homedepot.ta.aa.dao.Query;
import com.homedepot.ta.aa.dao.Results;
import com.homedepot.ta.aa.dao.ResultsReader;
import com.homedepot.ta.aa.dao.basic.BasicDAO;
import com.homedepot.ta.aa.dao.builder.DAO;
import com.homedepot.ta.aa.dao.exceptions.QueryException;
import com.homedepot.ta.aa.dao.stream.MapStream;

public class AdvancedSearchDAO implements DAOConstants, Constants {

	private static final Logger logger = Logger
			.getLogger(AdvancedSearchDAO.class);
	//TODO: change to match your business use id
	private static final int BUSINESS_USE_ID = 55;
	//Contract name for WorkforceSuccessionPlanning
	private static final String WORKFORCESUCCESSIONPLANNING_NAME = "WorkforceSuccessionPlanning";
	//Version number for WorkforceSuccessionPlanning
	private static final int WORKFORCESUCCESSIONPLANNING_VERSION = 1;
	

	//Loop through the list of query lines and create a query
	public static List<AdvancedSearchDTO> getAdvancedSearchDetails(
			List<AdvancedSearch> selected, String ldap) throws QueryException, HrReviewException, ParseException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getAdvancedSearchDetails");
		}
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		final List<AdvancedSearchDTO> advancedSearchList = new ArrayList<AdvancedSearchDTO>();
		MapStream inputs = new MapStream(
				"readDistinctSuccessionPlanEmployeeAndIndividualPlanDetails");

		boolean multiple = false;
		if (selected != null) {
			//Loop through query to extract data
			for (int i = 0; i < selected.size(); i++) {
				//Find the data field of the query from the Advanced Search Object 
				if (selected.get(i).getDataField() != null
						&& !selected.get(i).getDataField().trim().isEmpty()) {

					if (logger.isDebugEnabled()) {
						logger.debug("userId " + ldap + "*");
						logger.debug("Data Field "
								+ selected.get(i).getDataField() + " *");
						logger.debug("And/OR " + selected.get(i).getAndOr()
								+ " *");
						logger.debug("Operator "
								+ selected.get(i).getOperator() + " *");
						logger.debug("Data Value "
								+ selected.get(i).getDataValue() + " *");
						logger.debug("Like Contains "
								+ selected.get(i).isContainsLike() + " *");
						logger.debug("Like Begins with "
								+ selected.get(i).isBeginsWithLike() + " *");
						logger.debug("Like Ends with "
								+ selected.get(i).isEndsWithLike() + " *");
					}

					Date date = new Date(System.currentTimeMillis());
					inputs.put("userId", ldap);
					inputs.put("successionPlanSecurityGroupId", 300);
					inputs.put("languageCode", DAOConstants.lang_code);
					inputs.put("successionPlanEmployeeStatusCode", (short) 100);
					inputs.put("nlsEffectiveBeginDate", date);
					inputs.addQualifier("nlsEffectiveBeginDateLessThanEqualTo", true);

					//AssociateID
					if (selected.get(i).getDataField().equals("AssociateID")) {
						if (logger.isDebugEnabled()) {
							logger.debug("AssociateID");
						}
						
						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"zeroEmployeeIdOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"zeroEmployeeIdCloseParanthesisRequested",
									true);
						}

						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"zeroEmployeeIdOrRequested", true);
							}
						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								//Data Value
								if (selected.get(i).getDataValue() != null
										&& !selected.get(i).getDataValue()
												.isEmpty()) {
									String dataVal = selected.get(i)
											.getDataValue();
									inputs.put("zeroEmployeeId", dataVal.trim());
								}
							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								//DataValue
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<String> employeeIDList = new ArrayList<String>();
									for (int j = 0; j < selected.get(i)
											.getInputList().size(); j++) {
										employeeIDList.add(selected.get(i)
												.getInputList().get(j).trim());
									}
									inputs.put("zeroEmployeeIdList",
											employeeIDList);
									if (logger.isDebugEnabled()) {
										logger.debug("zeroEmployeeIdList*"
												+ employeeIDList + "*");
									}
								}
							}
						}
					}

					//Courses & Certifications OK
					else if (selected.get(i).getDataField()
							.equals("CoursesCertification")) {
						if (logger.isDebugEnabled()) {
							logger.debug("CoursesCertification");
						}
						
						inputs.addQualifier("employeeHumanResourcesSystemEmployeeIdJoin", true);

						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"successionPlanCourseDescriptionOrRequested",
										true);
							}
						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							
							inputs.put("nlsEffectiveBeginDate", date);
							inputs.addQualifier("nlsEffectiveBeginDateLessThanEqualTo",
									true);
							
							//=
							String dataVal = "";
							if (selected.get(i).getDataValue() != null
									&& !selected.get(i).getDataValue()
											.isEmpty()) {
								dataVal = selected.get(i).getDataValue();
							}
							inputs.put("successionPlanCourseDescription",
									dataVal.trim());

							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("LIKE")) {
								if (selected.get(i).isBeginsWithLike())
									inputs.addQualifier(
											"successionPlanCourseDescriptionBeginsWith",
											true);
								else if (selected.get(i).isContainsLike())
									inputs.addQualifier(
											"successionPlanCourseDescriptionContains",
											true);
								else if (selected.get(i).isEndsWithLike())
									inputs.addQualifier(
											"successionPlanCourseDescriptionEndsWith",
											true);
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"successionPlanCourseDescriptionOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"successionPlanCourseDescriptionCloseParanthesisRequested",
									true);
						}
					}

					//Degree 
					else if (selected.get(i).getDataField().equals("Degree")) {
						if (logger.isDebugEnabled()) {
							logger.debug("Degree");
						}
						
						inputs.addQualifier("educateHumanResourcesSystemEmployeeIdJoin", true);
						inputs.addQualifier("degreeTypeCodeJoin", true);
						
						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"degreeTypeDescriptionOrRequested",
										true);
							}
						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							String dataVal = "";
							if (selected.get(i).getDataValue() != null
									&& !selected.get(i).getDataValue()
											.isEmpty()) {
								dataVal = selected.get(i).getDataValue();
							}

							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								inputs.put("degreeTypeDescription", dataVal.toUpperCase().trim());
								inputs.put("overrideDegreeDescription", dataVal.toUpperCase().trim());
							} else if (selected.get(i).getOperator()
									.equals("<>")) {
								inputs.put("degreeTypeDescriptionNotEqualTo",
										dataVal.toUpperCase().trim());
								inputs.put(
										"overrideDegreeDescriptionNotEqualTo",
										dataVal.toUpperCase().trim());
							} else if (selected.get(i).getOperator()
									.equals("LIKE")) {
								inputs.put("degreeTypeDescription", dataVal.toUpperCase().trim());
								inputs.put("overrideDegreeDescription", dataVal.toUpperCase().trim());

								if (selected.get(i).isBeginsWithLike()) {
									inputs.addQualifier(
											"degreeTypeDescriptionBeginsWith",
											true);
									inputs.addQualifier(
											"overrideDegreeDescriptionBeginsWith",
											true);
								} else if (selected.get(i).isContainsLike()) {
									inputs.addQualifier(
											"degreeTypeDescriptionContains",
											true);
									inputs.addQualifier(
											"overrideDegreeDescriptionContains",
											true);
								} else if (selected.get(i).isEndsWithLike()) {
									inputs.addQualifier(
											"degreeTypeDescriptionEndsWith",
											true);
									inputs.addQualifier(
											"overrideDegreeDescriptionEndsWith",
											true);
								}

							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<String> degreeList = new ArrayList<String>();
									for (int j = 0; j < selected.get(i)
											.getInputList().size(); j++) {
										degreeList.add(selected.get(i)
												.getInputList().get(j).toUpperCase().trim());
									}
									inputs.put("degreeTypeDescriptioList",
											degreeList);
									inputs.put("overrideDegreeDescriptionList",
											degreeList);
								}
							} else if (selected.get(i).getOperator()
									.equals("NOT IN")) {
								List<String> degreeUnmatchedList = new ArrayList<String>();
								for (int j = 0; j < selected.get(i)
										.getInputList().size(); j++) {
									degreeUnmatchedList.add(selected.get(i)
											.getInputList().get(j).toUpperCase().trim());
								}
								inputs.put("unmatchedDegreeTypeDescriptioList",
										degreeUnmatchedList);
								inputs.put(
										"unmatchedOverrideDegreeDescriptionList",
										degreeUnmatchedList);
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"degreeTypeDescriptionOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"overrideDegreeDescriptionCloseParanthesisRequested",
									true);
						}
					}

					//Degree Yes/No 
					else if (selected.get(i).getDataField()
							.equals("HaveDegree")) {
						if (logger.isDebugEnabled()) {
							logger.debug("HaveDegree");
						}
						
						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"unmatchedDegreeTypeCodeOrRequested",
										true);
							}
						}

						if(selected.get(i).getDataValue().equalsIgnoreCase("NO"))
							inputs.addQualifier("humanResourcesSystemEmployeeIdNotIn", true);
						
						//DataValue
							List<String> degreeTypeCodeList = new ArrayList<String>();
							degreeTypeCodeList.add("-1");
							degreeTypeCodeList.add("21");							
							inputs.put("unmatchedDegreeTypeCodeList",
									degreeTypeCodeList);

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"degreeTypeCodeOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"degreeTypeCodeCloseParanthesisRequested",
									true);
						}
					}

					//Department OK
					else if (selected.get(i).getDataField()
							.equals("Department")) {
						if (logger.isDebugEnabled()) {
							logger.debug("Department");
						}		
						
						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"humanResourcesSystemDepartmentNumberOrRequested",
										true);
							}
						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							if (selected.get(i).getOperator().equals("=")) {
								multiple = false;
							} else if (selected.get(i).getOperator()
									.equals("LIKE")) {
								multiple = false;
								if (selected.get(i).isBeginsWithLike()) {
									inputs.addQualifier(
											"humanResourcesSystemDepartmentNumberBeginsWith",
											true);
								} else if (selected.get(i).isContainsLike()) {
									inputs.addQualifier(
											"humanResourcesSystemDepartmentNumberContains",
											true);
								} else if (selected.get(i).isEndsWithLike()) {
									inputs.addQualifier(
											"humanResourcesSystemDepartmentNumberEndsWith",
											true);
								}

							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								multiple = true;
								//DataValue
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<String> departmentList = new ArrayList<String>();
									for (int j = 0; j < selected.get(i)
											.getInputList().size(); j++) {
										departmentList.add(selected.get(i)
												.getInputList().get(j).trim());
									}
									inputs.put(
											"humanResourcesSystemDepartmentNumberList",
											departmentList);
								}
							}
						}
						if (!multiple) {
							//Data Value
							if (selected.get(i).getDataValue() != null
									&& !selected.get(i).getDataValue()
											.isEmpty()) {
								String dataVal = selected.get(i).getDataValue().trim();
								inputs.put(
										"humanResourcesSystemDepartmentNumber",
										dataVal.trim());
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"humanResourcesSystemDepartmentNumberOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"humanResourcesSystemDepartmentNumberCloseParanthesisRequested",
									true);
						}
					}

					//District OK
					else if (selected.get(i).getDataField().equals("District")) {
						if (logger.isDebugEnabled()) {
							logger.debug("District");
						}
						
						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"humanResourcesSystemRegionCodeOrRequested",
										true);
							}
						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							if (selected.get(i).getOperator().equals("=")) {
								multiple = false;
							} else if (selected.get(i).getOperator()
									.equals("LIKE")) {
								multiple = false;
								if (selected.get(i).isBeginsWithLike()) {
									inputs.addQualifier(
											"humanResourcesSystemRegionCodeBeginsWith",
											true);
								} else if (selected.get(i).isContainsLike()) {
									inputs.addQualifier(
											"humanResourcesSystemRegionCodeContains",
											true);
								} else if (selected.get(i).isEndsWithLike()) {
									inputs.addQualifier(
											"humanResourcesSystemRegionCodeEndsWith",
											true);
								}
							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								multiple = true;
								//DataValue
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<String> regionList = new ArrayList<String>();
									for (int j = 0; j < selected.get(i)
											.getInputList().size(); j++) {
										regionList.add(selected.get(i)
												.getInputList().get(j).toUpperCase().trim());
									}
									inputs.put(
											"humanResourcesSystemRegionCodeList",
											regionList);
								}
							}
						}
						if (!multiple) {
							//Data Value
							if (selected.get(i).getDataValue() != null
									&& !selected.get(i).getDataValue()
											.isEmpty()) {
								String dataVal = selected.get(i).getDataValue().trim();
								inputs.put("humanResourcesSystemRegionCode",
										dataVal.toUpperCase().trim());
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"humanResourcesSystemRegionCodeOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"humanResourcesSystemRegionCodeCloseParanthesisRequested",
									true);
						}
					}

					//Division OK
					else if (selected.get(i).getDataField().equals("Division")) {
						if (logger.isDebugEnabled()) {
							logger.debug("Division");
						}

						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"humanResourcesSystemDivisionCodeOrRequested",
										true);
							}
						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								//Data Value
								if (selected.get(i).getDataValue() != null
										&& !selected.get(i).getDataValue()
												.isEmpty()) {
									String dataVal = selected.get(i)
											.getDataValue();
									inputs.put(
											"humanResourcesSystemDivisionCode",
											dataVal.toUpperCase().trim());
								}
							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<String> divisionList = new ArrayList<String>();
									for (int j = 0; j < selected.get(i)
											.getInputList().size(); j++) {
										divisionList.add(selected.get(i)
												.getInputList().get(j).toUpperCase().trim());
									}
									inputs.put(
											"humanResourcesSystemDivisionCodeList",
											divisionList);
								}
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"humanResourcesSystemDivisionCodeOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"humanResourcesSystemDivisionCodeCloseParanthesisRequested",
									true);
						}
					}

					//First Name OK
					else if (selected.get(i).getDataField().equals("FirstName")) {
						if (logger.isDebugEnabled()) {
							logger.debug("FirstName");
						}

						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier("firstNameOrRequested",
										true);
							}
						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							String dataVal = "";
							if (selected.get(i).getDataValue() != null
									&& !selected.get(i).getDataValue()
											.isEmpty()) {
								dataVal = selected.get(i).getDataValue();
							}
							// =
							inputs.put("firstName", dataVal.toUpperCase().trim());

							if (selected.get(i).getOperator().equals("LIKE")) {
								if (selected.get(i).isBeginsWithLike()) {
									inputs.addQualifier("firstNameBeginsWith",
											true);
								} else if (selected.get(i).isContainsLike()) {
									inputs.addQualifier("firstNameContains",
											true);
								} else if (selected.get(i).isEndsWithLike()) {
									inputs.addQualifier("firstNameEndsWith",
											true);
								}
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"firstNameOpenParanthesisRequested", true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"firstNameCloseParanthesisRequested", true);
						}
					}

					//Gender OK
					else if (selected.get(i).getDataField().equals("Gender")) {
						if (logger.isDebugEnabled()) {
							logger.debug("Gender");
						}

						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier("sexTypeCodeOrRequested",
										true);
							}
						}

						if (selected.get(i).getDataValue() != null
								&& !selected.get(i).getDataValue().isEmpty()) {
							String dataVal = selected.get(i).getDataValue();
							inputs.put("sexTypeCode", dataVal.toUpperCase().trim());
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"sexTypeCodeOpenParanthesisRequested", true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"sexTypeCodeCloseParanthesisRequested",
									true);
						}
					}

					//Group OK
					else if (selected.get(i).getDataField().equals("Group")) {
						if (logger.isDebugEnabled()) {
							logger.debug("Group");
						}

						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"successionPlanEmployeeGroupTextOrRequested",
										true);
							}
						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								if (selected.get(i).getDataValue() != null
										&& !selected.get(i).getDataValue()
												.isEmpty()) {
									String dataVal = selected.get(i)
											.getDataValue().trim();
									inputs.put(
											"successionPlanEmployeeGroupText",
											dataVal.trim());
								}
							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<String> groupList = new ArrayList<String>();
									for (int j = 0; j < selected.get(i)
											.getInputList().size(); j++) {
										groupList.add(selected.get(i)
												.getInputList().get(j).trim());
									}
									inputs.put(
											"successionPlanEmployeeGroupTextList",
											groupList);
								}
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"successionPlanEmployeeGroupTextOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"successionPlanEmployeeGroupTextCloseParanthesisRequested",
									true);
						}
					}

					//IndividualCareerInterestReadiness
					else if (selected.get(i).getDataField()
							.equals("IndividualCareerInterestReadiness")) {
						if (logger.isDebugEnabled()) {
							logger.debug("IndividualCareerInterestReadiness");
						}

						inputs.addQualifier("humanResourcesSystemEmployeeIdJoin", true);
						inputs.addQualifier("successionPlanGlobalStatusCodeJoin", true);
						
						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"successionPlanGoalStatusDescriptionOrRequested",
										true);
							}
						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								if (selected.get(i).getDataValue() != null
										&& !selected.get(i).getDataValue()
												.isEmpty()) {
									String dataVal = selected.get(i)
											.getDataValue();
									inputs.put(
											"successionPlanGoalStatusDescription",
											dataVal.toUpperCase().trim());
								}
							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<String> readinessList = new ArrayList<String>();
									for (int j = 0; j < selected.get(i)
											.getInputList().size(); j++) {
										readinessList.add(selected.get(i)
												.getInputList().get(j).toUpperCase().trim());
									}
									inputs.put(
											"successionPlanGoalStatusDescriptionList",
											readinessList);
								}
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"successionPlanGoalStatusDescriptionOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"successionPlanGoalStatusDescriptionCloseParanthesisRequested",
									true);
						}
					}

					//IndividualCareerInterest OK
					else if (selected.get(i).getDataField()
							.equals("IndividualCareerInterest")) {
						if (logger.isDebugEnabled()) {
							logger.debug("IndividualCareerInterest");
						}

						inputs.addQualifier("humanResourcesSystemEmployeeIdJoin", true);
						inputs.addQualifier("jobTitleCodeJoin", true);
						
						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"jobTitleDescriptionOrRequested", true);
							}
						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							if (selected.get(i).getOperator().equals("=")) {
								multiple = false;
							} else if (selected.get(i).getOperator()
									.equals("LIKE")) {
								multiple = false;
								if (selected.get(i).isBeginsWithLike()) {
									inputs.addQualifier(
											"jobTitleDescriptionBeginsWith",
											true);
									inputs.addQualifier(
											"overridePositionDescriptionBeginsWith",
											true);
								} else if (selected.get(i).isContainsLike()) {
									inputs.addQualifier(
											"jobTitleDescriptionContains", true);
									inputs.addQualifier(
											"overridePositionDescriptionContains",
											true);
								} else if (selected.get(i).isEndsWithLike()) {
									inputs.addQualifier(
											"jobTitleDescriptionEndsWith", true);
									inputs.addQualifier(
											"overridePositionDescriptionEndsWith",
											true);
								}
							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								multiple = true;
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<String> careerInterestList = new ArrayList<String>();
									for (int j = 0; j < selected.get(i)
											.getInputList().size(); j++) {
										careerInterestList.add(selected.get(i)
												.getInputList().get(j).toUpperCase().trim());
									}
									inputs.put("jobTitleDescriptionList",
											careerInterestList);
									inputs.put(
											"overridePositionDescriptionList",
											careerInterestList);
									inputs.put("effectiveBeginDate", date);
									inputs.addQualifier(
											"effectiveBeginDateLessThanEqualTo",
											true);
								}
							}
						}
						if (!multiple) {
							//Data Value
							if (selected.get(i).getDataValue() != null
									&& !selected.get(i).getDataValue()
											.isEmpty()) {
								String dataVal = selected.get(i).getDataValue();
								inputs.put("jobTitleDescription", dataVal.toUpperCase().trim());
								inputs.put("overridePositionDescription",
										dataVal.toUpperCase().trim());
								inputs.put("effectiveBeginDate", date);
								inputs.addQualifier(
										"effectiveBeginDateLessThanEqualTo",
										true);
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"jobTitleDescriptionOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"jobTitleDescriptionCloseParanthesisRequested",
									true);
						}
					}

					//Job Title OK
					else if (selected.get(i).getDataField().equals("JobTitle")) {
						if (logger.isDebugEnabled()) {
							logger.debug("JobTitle");
						}

						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier("jobTitleCodeOrRequested",
										true);
							}
						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							if (selected.get(i).getOperator().equals("=")) {
								multiple = false;
							} else if (selected.get(i).getOperator()
									.equals("LIKE")) {
								multiple = false;
								if (selected.get(i).isBeginsWithLike()) {
									inputs.addQualifier(
											"jobTitleCodeBeginsWith", true);
								} else if (selected.get(i).isContainsLike()) {
									inputs.addQualifier("jobTitleCodeContains",
											true);
								} else if (selected.get(i).isEndsWithLike()) {
									inputs.addQualifier("jobTitleCodeEndsWith",
											true);
								}
							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								multiple = true;
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<String> jobTitleList = new ArrayList<String>();
									for (int j = 0; j < selected.get(i)
											.getInputList().size(); j++) {
										jobTitleList.add(selected.get(i)
												.getInputList().get(j).toUpperCase().trim());
									}
									inputs.put("jobTitleCodeList", jobTitleList);
								}
							}
						}
						if (!multiple) {
							//Data Value
							if (selected.get(i).getDataValue() != null
									&& !selected.get(i).getDataValue()
											.isEmpty()) {
								String dataVal = selected.get(i).getDataValue();
								inputs.put("jobTitleCode", dataVal.toUpperCase().trim());
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"jobTitleCodeOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"jobTitleCodeCloseParanthesisRequested",
									true);
						}
					}

					//Known Language OK
					else if (selected.get(i).getDataField()
							.equals("KnownLanguage")) {
						if (logger.isDebugEnabled()) {
							logger.debug("KnownLanguage");
						}

						inputs.addQualifier("languageHumanResourcesSystemEmployeeIdJoin", true);
						inputs.addQualifier("languageCodeJoin", true);
						
						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"internationalOrganizationForStandardsLanguageNameOrRequested",
										true);
							}

						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								multiple = false;
							} else if (selected.get(i).getOperator()
									.equals("LIKE")) {
								multiple = false;

								if (selected.get(i).isBeginsWithLike()) {
									inputs.addQualifier(
											"internationalOrganizationForStandardsLanguageNameBeginsWith",
											true);
									inputs.addQualifier(
											"overrideInternationalOrganizationForStandardsLanguageNameBeginsWith",
											true);
								} else if (selected.get(i).isContainsLike()) {
									inputs.addQualifier(
											"internationalOrganizationForStandardsLanguageNameContains",
											true);
									inputs.addQualifier(
											"overrideInternationalOrganizationForStandardsLanguageNameContains",
											true);
								} else if (selected.get(i).isEndsWithLike()) {
									inputs.addQualifier(
											"internationalOrganizationForStandardsLanguageNameEndsWith",
											true);
									inputs.addQualifier(
											"overrideInternationalOrganizationForStandardsLanguageNameEndsWith",
											true);
								}
							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								multiple = true;
								//DataValue
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<String> languageList = new ArrayList<String>();
									for (int j = 0; j < selected.get(i)
											.getInputList().size(); j++) {
										languageList.add(selected.get(i)
												.getInputList().get(j).trim());
									}
									inputs.put(
											"internationalOrganizationForStandardsLanguageNameList",
											languageList);
									inputs.put(
											"overrideInternationalOrganizationForStandardsLanguageNameList",
											languageList);
								}
							}
						}
						if (!multiple) {
							//Data Value
							if (selected.get(i).getDataValue() != null
									&& !selected.get(i).getDataValue()
											.isEmpty()) {
								String dataVal = selected.get(i).getDataValue();
								inputs.put(
										"internationalOrganizationForStandardsLanguageName",
										dataVal.trim());
								inputs.put(
										"overrideInternationalOrganizationForStandardsLanguageName",
										dataVal.trim());
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"internationalOrganizationForStandardsLanguageNameOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"internationalOrganizationForStandardsLanguageNameCloseParanthesisRequested",
									true);
						}
					}

					//LastName
					else if (selected.get(i).getDataField().equals("LastName")) {
						if (logger.isDebugEnabled()) {
							logger.debug("LastName");
						}

						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier("lastNameOrRequested", true);
							}
						}

						boolean like = false;
						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							if (logger.isDebugEnabled()) {
								logger.debug("Operator*"
										+ selected.get(i).getOperator());
							}
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								multiple = false;
								inputs.addQualifier("lastNameEqualTo", true);
							} else if (selected.get(i).getOperator()
									.equals(">")) {
								multiple = false;
								inputs.addQualifier("lastNameGreaterThan", true);
							} else if (selected.get(i).getOperator()
									.equals("<")) {
								multiple = false;
								inputs.addQualifier("lastNameLessThan", true);
							} else if (selected.get(i).getOperator()
									.equals(">=")) {
								multiple = false;
								inputs.addQualifier(
										"lastNameGreaterThanEqualTo", true);
							} else if (selected.get(i).getOperator()
									.equals("<=")) {
								multiple = false;
								inputs.addQualifier("lastNameLessThanEqualTo",
										true);
							} else if (selected.get(i).getOperator()
									.equals("LIKE")) {
								multiple = false;
								if (selected.get(i).isBeginsWithLike()) {
									inputs.addQualifier(
											"matchLastNameBeginsWith", true);
								} else if (selected.get(i).isContainsLike()) {
									inputs.addQualifier(
											"matchLastNameContains", true);
								} else if (selected.get(i).isEndsWithLike()) {
									inputs.addQualifier(
											"matchLastNameEndsWith", true);
								}
								like = true;
							} else if (selected.get(i).getOperator()
									.equals("BETWEEN")) {
								multiple = true;
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									inputs.put(
											"lastNameGreaterThanBetween",
											selected.get(i).getInputList()
													.get(0).trim().toUpperCase());
									inputs.put(
											"lastNameLessThanBetween",
											selected.get(i).getInputList()
													.get(1).trim().toUpperCase());
								}
							} else if (selected.get(i).getOperator()
									.equals("NOT BETWEEN")) {
								multiple = true;
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									inputs.put(
											"lastNameGreaterThanNotBetween",
											selected.get(i).getInputList()
													.get(0).trim().toUpperCase());
									inputs.put(
											"lastNameLessThanNotBetween",
											selected.get(i).getInputList()
													.get(1).trim().toUpperCase());
								}
							}
						}
						if (!multiple) {
							//Data Value
							if (selected.get(i).getDataValue() != null
									&& !selected.get(i).getDataValue()
											.isEmpty()) {
								if (logger.isDebugEnabled()) {
									logger.debug("dataVal*"
											+ selected.get(i).getDataValue());
								}
								String dataVal = selected.get(i).getDataValue();
								if (!like)
									inputs.put("lastName", dataVal.toUpperCase().trim());
								else if (like)
									inputs.put("matchLastName", dataVal.toUpperCase().trim());
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"lastNameOpenParanthesisRequested", true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"lastNameCloseParanthesisRequested", true);
						}
					}

					//Location OK
					else if (selected.get(i).getDataField().equals("Location")) {
						if (logger.isDebugEnabled()) {
							logger.debug("Location");
						}

						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"humanResourcesSystemStoreNumberOrRequested",
										true);
							}

						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								multiple = false;
							} else if (selected.get(i).getOperator()
									.equals("LIKE")) {
								multiple = false;
								if (selected.get(i).isBeginsWithLike()) {
									inputs.addQualifier(
											"humanResourcesSystemStoreNumberBeginsWith",
											true);
								} else if (selected.get(i).isContainsLike()) {
									inputs.addQualifier(
											"humanResourcesSystemStoreNumberContains",
											true);
								} else if (selected.get(i).isEndsWithLike()) {
									inputs.addQualifier(
											"humanResourcesSystemStoreNumberEndsWith",
											true);
								}
							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								multiple = true;
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<String> locationList = new ArrayList<String>();
									for (int j = 0; j < selected.get(i)
											.getInputList().size(); j++) {
										locationList.add(selected.get(i)
												.getInputList().get(j).trim().toUpperCase());
									}
									inputs.put(
											"humanResourcesSystemStoreNumberList",
											locationList);
								}
							}
						}
						if (!multiple) {
							//Data Value
							if (selected.get(i).getDataValue() != null
									&& !selected.get(i).getDataValue()
											.isEmpty()) {
								String dataVal = selected.get(i).getDataValue();
								inputs.put("humanResourcesSystemStoreNumber",
										dataVal.trim().toUpperCase());
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"humanResourcesSystemStoreNumberOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"humanResourcesSystemStoreNumberCloseParanthesisRequested",
									true);
						}
					}

					//Major
					else if (selected.get(i).getDataField().equals("Major")) {
						if (logger.isDebugEnabled()) {
							logger.debug("Major");
						}
						
						inputs.addQualifier("educateHumanResourcesSystemEmployeeIdJoin", true);
						inputs.addQualifier("collegeMajorCodeJoin", true);

						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"collegeMajorDescriptionOrRequested",
										true);
							}

						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								multiple = false;
							} else if (selected.get(i).getOperator()
									.equals("LIKE")) {
								multiple = false;
								if (selected.get(i).isBeginsWithLike()) {
									inputs.addQualifier(
											"collegeMajorDescriptionBeginsWith",
											true);
									inputs.addQualifier(
											"overrideCollegeMajorDescriptionEndsWith",
											true);
								} else if (selected.get(i).isContainsLike()) {
									inputs.addQualifier(
											"collegeMajorDescriptionContains",
											true);
									inputs.addQualifier(
											"overrideCollegeMajorDescriptionContains",
											true);
								} else if (selected.get(i).isEndsWithLike()) {
									inputs.addQualifier(
											"collegeMajorDescriptionEndsWith",
											true);
									inputs.addQualifier(
											"overrideCollegeMajorDescriptionEndsWith",
											true);
								}
							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								multiple = true;
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<String> majorsList = new ArrayList<String>();
									for (int j = 0; j < selected.get(i)
											.getInputList().size(); j++) {
										majorsList.add(selected.get(i)
												.getInputList().get(j).toUpperCase().trim());
									}
									inputs.put("collegeMajorDescriptionList",
											majorsList);
									inputs.put(
											"overrideCollegeMajorDescriptionList",
											majorsList);

								}
							}
						}
						if (!multiple) {
							//Data Value
							if (selected.get(i).getDataValue() != null
									&& !selected.get(i).getDataValue()
											.isEmpty()) {
								String dataVal = selected.get(i).getDataValue();
								inputs.put("collegeMajorDescription", dataVal.toUpperCase().trim());
								inputs.put("overrideCollegeMajorDescription",
										dataVal.toUpperCase().trim());
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"collegeMajorDescriptionOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"collegeMajorDescriptionCloseParanthesisRequested",
									true);
						}
					}

					//OriginalHireDate OK
					else if (selected.get(i).getDataField()
							.equals("OriginalHireDate")) {
						if (logger.isDebugEnabled()) {
							logger.debug("OriginalHireDate");
						}

						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"originHireDateOrRequested", true);
							}

						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								multiple = false;
								inputs.addQualifier("originHireDateEqualTo",
										true);
							} else if (selected.get(i).getOperator()
									.equals(">")) {
								multiple = false;
								inputs.addQualifier(
										"originHireDateGreaterThan", true);
							} else if (selected.get(i).getOperator()
									.equals("<")) {
								multiple = false;
								inputs.addQualifier("originHireDateLessThan",
										true);
							} else if (selected.get(i).getOperator()
									.equals(">=")) {
								multiple = false;
								inputs.addQualifier(
										"originHireDateGreaterThanEqualTo",
										true);
							} else if (selected.get(i).getOperator()
									.equals("<=")) {
								multiple = false;
								inputs.addQualifier(
										"originHireDateLessThanEqualTo", true);
							} else if (selected.get(i).getOperator()
									.equals("BETWEEN")) {
								multiple = true;
								java.sql.Date originHireDateGreaterThanBetween = new java.sql.Date(sdf1.parse(selected.get(i).getInputList().get(0)).getTime());
								java.sql.Date originHireDateLessThanBetween = new java.sql.Date(sdf1.parse(selected.get(i).getInputList().get(1)).getTime());
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									inputs.put(
											"originHireDateGreaterThanBetween",
											originHireDateGreaterThanBetween);
									inputs.put(
											"originHireDateLessThanBetween",
											originHireDateLessThanBetween );
								}
							} else if (selected.get(i).getOperator()
									.equals("NOT BETWEEN")) {
								multiple = true;
								java.sql.Date originHireDateGreaterThanNotBetween = new java.sql.Date(sdf1.parse(selected.get(i).getInputList().get(0)).getTime());
								java.sql.Date originHireDateLessThanNotBetween = new java.sql.Date(sdf1.parse(selected.get(i).getInputList().get(1)).getTime());
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									inputs.put(
											"originHireDateGreaterThanNotBetween",
											originHireDateGreaterThanNotBetween);
									inputs.put(
											"originHireDateLessThanNotBetween",
											originHireDateLessThanNotBetween);
								}
							}
						}
						if (!multiple) {
							//Data Value
							if (selected.get(i).getDataValue() != null
									&& !selected.get(i).getDataValue()
											.isEmpty()) {
								String dataVal = selected.get(i).getDataValue();
								
								java.util.Date orginDate = null;
								try {
									orginDate = sdf1.parse(dataVal);
								} catch (ParseException pe) {
									logger.error(HrReviewApplLogMessage.create(
											HrReviewApplLogMessage.FATAL_ERROR,
											ERROR_SEARCH_CANDIDATE_LIST_QUERY, pe));
									throw new HrReviewException(HrReviewApplLogMessage.FATAL_ERROR,
											ERROR_SEARCH_CANDIDATE_LIST_QUERY, pe);
								}
								
								java.sql.Date sqlStartDate = new java.sql.Date(orginDate.getTime()); 
								inputs.put("originHireDate", sqlStartDate);
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"originHireDateOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"originHireDateOpenParanthesisRequested",
									true);
						}
					}

					//PerformanceCode OK
					else if (selected.get(i).getDataField()
							.equals("PerformanceCode")) {
						if (logger.isDebugEnabled()) {
							logger.debug("PerformanceCode");
						}
						
						inputs.addQualifier("humanResourceSystemRatingCodeJoin", true);
						
						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"evaluateCategoryCodeOrRequested", true);
							}

						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								if (selected.get(i).getDataValue() != null && !selected.get(i).getDataValue().isEmpty())
								{
									String dataVal = selected.get(i).getDataValue();
									Short shortVal = 0;
									//Get Rating Codes from ID
									List<PotentialPerformanceSearchDTO> perfCodes = DataDAO.getPotentialPerformanceDetails();
									for(int j = 0; j < perfCodes.size(); j++)
									{
										if(perfCodes.get(j).getCategoryCode() == 1)
										{
											if(perfCodes.get(j).getId().equals(dataVal.trim()))
												shortVal = perfCodes.get(j).getEvaluateRatingCode();
										}
									}
									inputs.put("evaluateCategoryCode", (short)1);
									inputs.put("evaluateRatingCode", shortVal);
								}
							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList().size() > 0)
								{
									List<Short> categoryList = new ArrayList<Short>();
									//Get Rating Codes from ID
									List<PotentialPerformanceSearchDTO> perfCodes = DataDAO.getPotentialPerformanceDetails();
									for (int j = 0; j < selected.get(i).getInputList().size(); j++) 
									{
										for(int k = 0; k < perfCodes.size(); k++)
										{
											if(perfCodes.get(k).getCategoryCode() == 1)
											{
												if(perfCodes.get(k).getId().equals(selected.get(i).getInputList().get(j).trim()))
													categoryList.add(perfCodes.get(k).getEvaluateRatingCode());
											}
										}										
									}
									inputs.put("evaluateCategoryCode", (short)1);
									inputs.put("evaluateRatingCodeList", categoryList);
								}
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"evaluateCategoryCodeOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"evaluateCategoryCodeCloseParanthesisRequested",
									true);
						}
					}

					//PotentialCode OK
					else if (selected.get(i).getDataField()
							.equals("PotentialCode")) {
						if (logger.isDebugEnabled()) {
							logger.debug("PotentialCode");
						}
						
						inputs.addQualifier("potentialRatingCodeJoin", true);

						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier("categoryCodeOrRequested",
										true);
							}
						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								if (selected.get(i).getDataValue() != null
										&& !selected.get(i).getDataValue()
												.isEmpty()) {
									String dataVal = selected.get(i).getDataValue();
									Short shortVal = 0;
									//Get Rating Codes from ID
									List<PotentialPerformanceSearchDTO> perfCodes = DataDAO.getPotentialPerformanceDetails();
									for(int j = 0; j < perfCodes.size(); j++)
									{
										if(perfCodes.get(j).getCategoryCode() == 2)
										{
											if(perfCodes.get(j).getId().equals(dataVal.trim()))
												shortVal = perfCodes.get(j).getEvaluateRatingCode();
										}
									}
									inputs.put("categoryCode", (short)2);
									inputs.put("ratingCode", shortVal);
								}
							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<Short> categoryList = new ArrayList<Short>();
									//Get Rating Codes from ID
									List<PotentialPerformanceSearchDTO> perfCodes = DataDAO.getPotentialPerformanceDetails();
									for (int j = 0; j < selected.get(i).getInputList().size(); j++) 
									{
										for(int k = 0; k < perfCodes.size(); k++)
										{
											if(perfCodes.get(k).getCategoryCode() == 2)
											{
												if(perfCodes.get(k).getId().equals(selected.get(i).getInputList().get(j).trim()))
													categoryList.add(perfCodes.get(k).getEvaluateRatingCode());
											}
										}										
									}
									inputs.put("categoryCode", (short)2);
									inputs.put("ratingCodeList", categoryList);

								}
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"categoryCodeOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"categoryCodeCloseParanthesisRequested",
									true);
						}
					}

					//PotentialNextPositionReadiness OK
					else if (selected.get(i).getDataField()
							.equals("PotentialNextPositionReadiness")) {
						if (logger.isDebugEnabled()) {
							logger.debug("PotentialNextPositionReadiness");
						}
						
						inputs.addQualifier("planHumanResourcesSystemEmployeeIdJoin", true);
						inputs.addQualifier("globalStatusCodeJoin", true);

						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"successionPlanStatusDescriptionOrRequested",
										true);
							}
						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								if (selected.get(i).getDataValue() != null
										&& !selected.get(i).getDataValue()
												.isEmpty()) {
									String dataVal = selected.get(i)
											.getDataValue();
									inputs.put(
											"successionPlanStatusDescription",
											dataVal.toUpperCase().trim());
								}
							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<String> categoryList = new ArrayList<String>();
									for (int j = 0; j < selected.get(i)
											.getInputList().size(); j++) {
										categoryList.add(selected.get(i)
												.getInputList().get(j).toUpperCase().trim());
									}
									inputs.put(
											"successionPlanStatusDescriptionList",
											categoryList);
								}
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"successionPlanStatusDescriptionOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"successionPlanStatusDescriptionCloseParanthesisRequested",
									true);
						}
					}

					//PotentialNextPositions
					else if (selected.get(i).getDataField()
							.equals("PotentialNextPositions")) {
						if (logger.isDebugEnabled()) {
							logger.debug("PotentialNextPositions");
						}
						
						inputs.addQualifier("planHumanResourcesSystemEmployeeIdJoin", true);
						inputs.addQualifier("nlsJobTitleCodeJoin", true);

						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"jobDescriptionOrRequested", true);
							}
						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								multiple = false;
							} else if (selected.get(i).getOperator()
									.equals("LIKE")) {
								multiple = false;
								if (selected.get(i).isBeginsWithLike()) {
									inputs.addQualifier(
											"jobDescriptionBeginsWith", true);
									inputs.addQualifier(
											"positionDescriptionBeginsWith",
											true);
								} else if (selected.get(i).isContainsLike()) {
									inputs.addQualifier(
											"jobDescriptionContains", true);
									inputs.addQualifier(
											"positionDescriptionContains", true);
								} else if (selected.get(i).isEndsWithLike()) {
									inputs.addQualifier(
											"jobDescriptionEndsWith", true);
									inputs.addQualifier(
											"positionDescriptionEndsWith", true);
								}
							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								multiple = true;
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<String> nextPositionsList = new ArrayList<String>();
									for (int j = 0; j < selected.get(i)
											.getInputList().size(); j++) {
										nextPositionsList.add(selected.get(i)
												.getInputList().get(j).toUpperCase().trim());
									}
									inputs.put("jobDescriptionList",
											nextPositionsList);
									inputs.put("nlsEffectiveBeginDate", date);
									inputs.put("positionDescriptionList",
											nextPositionsList);
									inputs.addQualifier(
											"nlsEffectiveBeginDateLessThanEqualTo",
											true);
								}
							}
						}
						if (!multiple) {
							//Data Value
							if (selected.get(i).getDataValue() != null
									&& !selected.get(i).getDataValue()
											.isEmpty()) {
								String dataVal = selected.get(i).getDataValue();
								inputs.put("jobDescription", dataVal.toUpperCase().trim());
								inputs.put("nlsEffectiveBeginDate", date);
								inputs.put("positionDescription", dataVal.toUpperCase().trim());
								inputs.addQualifier(
										"nlsEffectiveBeginDateLessThanEqualTo",
										true);
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"jobDescriptionOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"jobDescriptionCloseParanthesisRequested",
									true);
						}
					}

					//PreviousEmployer OK
					else if (selected.get(i).getDataField()
							.equals("PreviousEmployer")) {
						if (logger.isDebugEnabled()) {
							logger.debug("PreviousEmployer");
						}
						
						inputs.addQualifier("historyHumanResourcesSystemEmployeeIdJoin", true);

						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier("employerNameOrRequested",
										true);
							}

						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								multiple = false;
							} else if (selected.get(i).getOperator()
									.equals("LIKE")) {
								multiple = false;
								if (selected.get(i).isBeginsWithLike()) {
									inputs.addQualifier(
											"employerNameBeginsWith", true);
								} else if (selected.get(i).isContainsLike()) {
									inputs.addQualifier("employerNameContains",
											true);
								} else if (selected.get(i).isEndsWithLike()) {
									inputs.addQualifier("employerNameEndsWith",
											true);
								}
							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								multiple = true;
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<String> employerList = new ArrayList<String>();
									for (int j = 0; j < selected.get(i)
											.getInputList().size(); j++) {
										employerList.add(selected.get(i)
												.getInputList().get(j).trim());
									}
									inputs.put("employerNameList", employerList);
								}
							}
						}
						if (!multiple) {
							//Data Value
							if (selected.get(i).getDataValue() != null
									&& !selected.get(i).getDataValue()
											.isEmpty()) {
								String dataVal = selected.get(i).getDataValue();
								inputs.put("employerName", dataVal.trim());
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"employerNameOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"employerNameCloseParanthesisRequested",
									true);
						}
					}

					//PreviousTitle
					else if (selected.get(i).getDataField()
							.equals("PreviousTitle")) {
						if (logger.isDebugEnabled()) {
							logger.debug("PreviousTitle");
						}
						
						inputs.addQualifier("personIdJoin", true);

						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier("jobTitleCodeOrRequested",
										true);
							}
						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								multiple = false;
							} else if (selected.get(i).getOperator()
									.equals("LIKE")) {
								multiple = false;
								if (selected.get(i).isBeginsWithLike()) {
									inputs.addQualifier("jobTitleIdBeginsWith",
											true);
								} else if (selected.get(i).isContainsLike()) {
									inputs.addQualifier("jobTitleIdContains",
											true);
								} else if (selected.get(i).isEndsWithLike()) {
									inputs.addQualifier("jobTitleIdEndsWith",
											true);
								}
							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								multiple = true;
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<String> employerList = new ArrayList<String>();
									for (int j = 0; j < selected.get(i)
											.getInputList().size(); j++) {
										employerList.add(selected.get(i).getInputList().get(j).toUpperCase().trim());
									}
									inputs.put("jobTitleIdList", employerList);
									inputs.put("unmatchedJobTitleCodeList", employerList);
									inputs.put("effectiveDate", date);
									inputs.addQualifier("effectiveDateLessThanEqualTo", date);
								}
							}
						}
						if (!multiple) {
							//Data Value
							if (selected.get(i).getDataValue() != null
									&& !selected.get(i).getDataValue()
											.isEmpty()) {
								String dataVal = selected.get(i).getDataValue();
								inputs.put("effectiveDate", date);
								inputs.addQualifier("effectiveDateLessThanEqualTo", date);
								inputs.put("unmatchedJobTitleCode", dataVal.toUpperCase().trim());
								inputs.put("jobTitleId", dataVal.toUpperCase());
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"effectiveDateOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"effectiveDateCloseParanthesisRequested",
									true);
						}
					}

					//RaceEthnicity OK
					else if (selected.get(i).getDataField()
							.equals("RaceEthnicity")) {
						if (logger.isDebugEnabled()) {
							logger.debug("RaceEthnicity");
						}

						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier("raceCodeOrRequested", true);
							}
						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								if (selected.get(i).getDataValue() != null
										&& !selected.get(i).getDataValue()
												.isEmpty()) {
									String dataVal = selected.get(i)
											.getDataValue();
									inputs.put("raceCode", dataVal.trim());
								}
							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<String> raceList = new ArrayList<String>();
									for (int j = 0; j < selected.get(i)
											.getInputList().size(); j++) {
										raceList.add(selected.get(i)
												.getInputList().get(j).trim());
									}
									inputs.put("raceCodeList", raceList);
								}
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"raceCodeOpenParanthesisRequested", true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"raceCodeCloseParanthesisRequested", true);
						}
					}

					//Region OK
					else if (selected.get(i).getDataField().equals("Region")) {
						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"humanResourcesSystemOperationsGroupCodeOrRequested",
										true);
							}
						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								multiple = false;
							} else if (selected.get(i).getOperator()
									.equals("LIKE")) {
								multiple = false;

								if (selected.get(i).isBeginsWithLike()) {
									inputs.addQualifier(
											"humanResourcesSystemOperationsGroupCodeBeginsWith",
											true);
								} else if (selected.get(i).isContainsLike()) {
									inputs.addQualifier(
											"humanResourcesSystemOperationsGroupCodeContains",
											true);
								} else if (selected.get(i).isEndsWithLike()) {
									inputs.addQualifier(
											"humanResourcesSystemOperationsGroupCodeEndsWith",
											true);
								}
							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								multiple = true;
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<String> employerList = new ArrayList<String>();
									for (int j = 0; j < selected.get(i)
											.getInputList().size(); j++) {
										employerList.add(selected.get(i)
												.getInputList().get(j).toUpperCase().trim());
									}
									inputs.put(
											"humanResourcesSystemOperationsGroupCodeList",
											employerList);
								}
							}
						}
						if (!multiple) {
							//Data Value
							if (selected.get(i).getDataValue() != null
									&& !selected.get(i).getDataValue()
											.isEmpty()) {
								String dataVal = selected.get(i).getDataValue();
								inputs.put(
										"humanResourcesSystemOperationsGroupCode",
										dataVal.toUpperCase().trim());
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"humanResourcesSystemOperationsGroupCodeOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"humanResourcesSystemOperationsGroupCodeCloseParanthesisRequested",
									true);
						}
					}

					//Relocate 
					else if (selected.get(i).getDataField().equals("Relocate")) {
						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"humanResourcesSystemEmployeeIdOrRequested",
										true);
							}
						}

						if(selected.get(i).getDataValue().equalsIgnoreCase("NO"))
						{
							inputs.addQualifier("employeeIdNotIn", true);
						}
						
						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								inputs.addQualifier(
										"isHumanResourcesSystemEmployeeIdRequired",
										true);
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"humanResourcesSystemEmployeeIdOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"humanResourcesSystemEmployeeIdCloseParanthesisRequested",
									true);
						}
					}

					//SchoolName OK
					else if (selected.get(i).getDataField()
							.equals("SchoolName")) {
						
						inputs.addQualifier("educateHumanResourcesSystemEmployeeIdJoin", true);
						inputs.addQualifier("facilityIdJoin", true);
						
						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier("facilityNameOrRequested",
										true);
							}
						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								multiple = false;
							} else if (selected.get(i).getOperator()
									.equals("LIKE")) {
								multiple = false;
								if (selected.get(i).isBeginsWithLike()) {
									inputs.addQualifier(
											"facilityNameBeginsWith", true);
									inputs.addQualifier(
											"overrideSchoolNameBeginsWith",
											true);
								} else if (selected.get(i).isContainsLike()) {
									inputs.addQualifier("facilityNameContains",
											true);
									inputs.addQualifier(
											"overrideSchoolNameContains", true);
								} else if (selected.get(i).isEndsWithLike()) {
									inputs.addQualifier("facilityNameEndsWith",
											true);
									inputs.addQualifier(
											"overrideSchoolNameEndsWith", true);
								}
							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								multiple = true;
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<String> schoolList = new ArrayList<String>();
									for (int j = 0; j < selected.get(i)
											.getInputList().size(); j++) {
										schoolList.add(selected.get(i)
												.getInputList().get(j).toUpperCase().trim());
									}
									inputs.put("facilityNameList", schoolList);
									inputs.put("overrideSchoolNameList",
											schoolList);
								}
							}
						}
						if (!multiple) {
							//Data Value
							if (selected.get(i).getDataValue() != null
									&& !selected.get(i).getDataValue()
											.isEmpty()) {
								String dataVal = selected.get(i).getDataValue();
								inputs.put("facilityName", dataVal.toUpperCase().trim());
								inputs.put("overrideSchoolName", dataVal.toUpperCase().trim());
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"facilityNameOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"facilityNameCloseParanthesisRequested",
									true);
						}
					}

					//StoreNumber OK
					else if (selected.get(i).getDataField()
							.equals("StoreNumber")) {
						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"humanResourcesSystemStoreNumberOrRequested",
										true);
							}
						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								if (selected.get(i).getDataValue() != null
										&& !selected.get(i).getDataValue()
												.isEmpty()) {
									String dataVal = selected.get(i)
											.getDataValue();
									inputs.put(
											"humanResourcesSystemStoreNumber",
											dataVal.toUpperCase().trim());
								}
							} else if (selected.get(i).getOperator()
									.equals("IN")) {
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									List<String> storeList = new ArrayList<String>();
									for (int j = 0; j < selected.get(i)
											.getInputList().size(); j++) {
										storeList.add(selected.get(i)
												.getInputList().get(j).toUpperCase().trim());
									}
									inputs.put(
											"humanResourcesSystemStoreNumberList",
											storeList);
								}
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"humanResourcesSystemStoreNumberOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"humanResourcesSystemStoreNumberCloseParanthesisRequested",
									true);
						}
					}

					//TimeInTitle
					else if (selected.get(i).getDataField()
							.equals("TimeInTitle")) {
						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"jobTitleEffectiveBeginDateOrRequested",
										true);
							}
						}

						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								multiple = false;
								inputs.addQualifier(
										"jobTitleEffectiveBeginDateEqualTo",
										true);
							} else if (selected.get(i).getOperator()
									.equals(">")) {
								multiple = false;
								inputs.addQualifier(
										"jobTitleEffectiveBeginDateGreaterThan",
										true);
							} else if (selected.get(i).getOperator()
									.equals("<")) {
								multiple = false;
								inputs.addQualifier(
										"jobTitleEffectiveBeginDateLessThan",
										true);
							} else if (selected.get(i).getOperator()
									.equals(">=")) {
								multiple = false;
								inputs.addQualifier(
										"jobTitleEffectiveBeginDateGreaterThanEqualTo",
										true);
							} else if (selected.get(i).getOperator()
									.equals("<=")) {
								multiple = false;
								inputs.addQualifier(
										"jobTitleEffectiveBeginDateLessThanEqualTo",
										true);
							} else if (selected.get(i).getOperator()
									.equals("BETWEEN")) {
								multiple = true;
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									//Change the years from inputList and change to Date
									int years1 = Integer.parseInt(selected.get(i).getInputList().get(0));
									int years2 = Integer.parseInt(selected.get(i).getInputList().get(1));
									
									//Put older year first
									if(years1<years2)
									{
										int temp = years2;
										years2 = years1;
										years1 = temp;
									}
									
									Calendar cal = Calendar.getInstance();
									cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)-years1);
									Date dataVal1 = new Date(cal.getTimeInMillis());		
									cal = Calendar.getInstance();
									cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)-years2);
									Date dataVal2 = new Date(cal.getTimeInMillis());
									
									inputs.put(
											"jobTitleEffectiveBeginDateGreaterThanEqualTo",
											dataVal1);
									inputs.put(
											"jobTitleEffectiveBeginDateLessThanEqualTo",
											dataVal2);
								}
							}
						}
						if (!multiple) {
							//Data Value
							if (selected.get(i).getDataValue() != null
									&& !selected.get(i).getDataValue()
											.isEmpty()) {
								//Convert year to Date
								int years = Integer.parseInt(selected.get(i).getDataValue());
								Calendar cal = Calendar.getInstance();
								cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)-years);
								Date dataVal = new Date(cal.getTimeInMillis());							
								inputs.put("jobTitleEffectiveBeginDate", dataVal);
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"jobTitleEffectiveBeginDateOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"jobTitleEffectiveBeginDateCloseParanthesisRequested",
									true);
						}
					}

					//TimeWithCompany
					else if (selected.get(i).getDataField()
							.equals("TimeWithCompany")) {
						//AND&OR
						if (selected.get(i).getAndOr() != null
								&& !selected.get(i).getAndOr().isEmpty()) {
							if (selected.get(i).getAndOr().equals("OR")) {
								inputs.addQualifier(
										"employmentAnniversaryDateOrRequested",
										true);
							}
						}
						
						//Operator
						if (selected.get(i).getOperator() != null
								&& !selected.get(i).getOperator().isEmpty()) {
							// =, >, <, >=, <=, Like, Between, Not Between
							if (selected.get(i).getOperator().equals("=")) {
								multiple = false;
								inputs.addQualifier(
										"employmentAnniversaryDateEqualTo",
										true);
							} else if (selected.get(i).getOperator()
									.equals(">")) {
								multiple = false;
								inputs.addQualifier(
										"employmentAnniversaryDateLessThan"
										,
										true);
							} else if (selected.get(i).getOperator()
									.equals("<")) {
								multiple = false;
								inputs.addQualifier(
										"employmentAnniversaryDateGreaterThan",
										true);
							} else if (selected.get(i).getOperator()
									.equals(">=")) {
								multiple = false;
								inputs.addQualifier(
										"employmentAnniversaryDateLessThanEqualTo",
										true);
							} else if (selected.get(i).getOperator()
									.equals("<=")) {
								multiple = false;
								inputs.addQualifier(
										"employmentAnniversaryDateGreaterThanEqualTo",
										true);
							} else if (selected.get(i).getOperator()
									.equals("BETWEEN")) {
								multiple = true;
								if (selected.get(i).getInputList() != null
										&& selected.get(i).getInputList()
												.size() > 0) {
									//Change the years from inputList and change to Date
									int years1 = Integer.parseInt(selected.get(i).getInputList().get(0));
									int years2 = Integer.parseInt(selected.get(i).getInputList().get(1));
									
									//Put older year first
									if(years1<years2)
									{
										int temp = years2;
										years2 = years1;
										years1 = temp;
									}
									Calendar cal = Calendar.getInstance();
									cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)-years1);
									Date dataVal1 = new Date(cal.getTimeInMillis());		
									cal = Calendar.getInstance();
									cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)-years2);
									Date dataVal2 = new Date(cal.getTimeInMillis());
									
									inputs.put(
											"employmentAnniversaryDateGreaterThanEqualTo",
											dataVal1);
									inputs.put(
											"employmentAnniversaryDateLessThanEqualTo",
											dataVal2);
								}
							}
						}
						if (!multiple) {
							//Data Value
							if (selected.get(i).getDataValue() != null
									&& !selected.get(i).getDataValue()
											.isEmpty()) {
								//Convert years to Date
								int years = Integer.parseInt(selected.get(i).getDataValue());
								Calendar cal = Calendar.getInstance();
								cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)-years);
								Date dataVal = new Date(cal.getTimeInMillis());
								inputs.put("employmentAnniversaryDate", dataVal);
							}
						}

						//Open close parenthesis
						if (selected.get(i).hasOpenParenVal()) {
							inputs.addQualifier(
									"employmentAnniversaryDateOpenParanthesisRequested",
									true);
						} if (selected.get(i).hasCloseParenVal()) {
							inputs.addQualifier(
									"employmentAnniversaryDateCloseParanthesisRequested",
									true);
						}
					}
				}
			}

			BasicDAO.getResult("WorkforceSuccessionPlanning", 55, 1, inputs,
					new ResultsReader() {
						public void readResults(Results results, Query query,
								Inputs inputs) throws QueryException {
							AdvancedSearchDTO advancedSearchDTO = null;
							while (results.next()) {

								advancedSearchDTO = new AdvancedSearchDTO();
								advancedSearchDTO.setAssociates(results.getString(
										"lastName").trim() + ", " + results.getString(
										"firstName"));
								advancedSearchDTO
									.setFirstName(results
											.getString("firstName").trim());
								advancedSearchDTO
									.setLastName(results.getString("lastName").trim());
								advancedSearchDTO
										.setConsentDecreeJobCategoryCode(results
												.getString(
														"consentDecreeJobCategoryCode")
												.trim());
								advancedSearchDTO.setDistrictCode(results
										.getString("districtCode").trim());
								advancedSearchDTO
										.setHumanResourcesSystemDepartmentNumber(results
												.getString(
														"humanResourcesSystemDepartmentNumber")
												.trim());
								advancedSearchDTO
										.setHumanResourcesSystemStoreNumber(results
												.getString(
														"humanResourcesSystemStoreNumber")
												.trim());
								advancedSearchDTO.setJobTitleCode(results
										.getString("jobTitleCode").trim());
								advancedSearchDTO.setJobTitleDescription(results
										.getString("jobTitleDescription"));
								advancedSearchDTO.setZeroEmployeeId(results
										.getString("zeroEmployeeId").trim());
								advancedSearchList.add(advancedSearchDTO);
							}
						}
					});
		}
		if (logger.isDebugEnabled()) {
			//logger.debug("finish getAdvancedSearchDetails");
			//logger.debug("returning " + advancedSearchList);
		}
		return advancedSearchList;
	}

	public static List<AdvancedSearchDTO> getAdvancedQuickSearchDetails(
			String ldap, String selectedOption) throws QueryException {

		if (logger.isDebugEnabled()) {
			logger.debug("start getAdvancedQuickSearchDetails");
		}

		final List<AdvancedSearchDTO> advancedSearchList = new ArrayList<AdvancedSearchDTO>();

		MapStream inputs = new MapStream(
				"readSuccessionPlanEmployeeAndHumanResourcesTransferNlsJobTitleDetails");
		if (selectedOption != null && !selectedOption.trim().isEmpty()) {
			//VP
			if (selectedOption.equalsIgnoreCase("1")) {
				inputs.put("userId", ldap);
				inputs.put("successionPlanSecurityGroupId", 300);
				inputs.put("languageCode", DAOConstants.lang_code);
				inputs.put("nlsJobTitleDescription", "%VP%");
				inputs.addQualifier("nlsJobTitleDescriptionContains", true);
				inputs.put("effectiveBeginDate", new Date(Calendar
						.getInstance().getTimeInMillis()));
				inputs.addQualifier("effectiveBeginDateLessThanEqualTo", true);
				inputs.put("effectiveEndDate", new Date(Calendar.getInstance()
						.getTimeInMillis()));
				inputs.addQualifier("effectiveEndDateGreaterThanEqualTo", true);
				inputs.put("jobEffectiveBeginDate", new Date(Calendar
						.getInstance().getTimeInMillis()));
				inputs.addQualifier("jobEffectiveBeginDateLessThanEqualTo",
						true);
			}
			//Officer
			else if (selectedOption.equalsIgnoreCase("2")) {
				inputs.put("userId", ldap);
				inputs.put("successionPlanSecurityGroupId", 300);
				inputs.put("languageCode", DAOConstants.lang_code);
				inputs.put("employmentCategory", "OF");
				inputs.put("jobEffectiveBeginDate", new Date(Calendar
						.getInstance().getTimeInMillis()));
				inputs.addQualifier("jobEffectiveBeginDateLessThanEqualTo",
						true);
			}
			//Director
			else if (selectedOption.equalsIgnoreCase("3")) {
				inputs.put("userId", ldap);
				inputs.put("successionPlanSecurityGroupId", 300);
				inputs.put("languageCode", DAOConstants.lang_code);
				inputs.put("nlsJobTitleDescription", "%DIR%");
				inputs.addQualifier("nlsJobTitleDescriptionContains", true);

				List<String> jobTitleCode = new ArrayList<String>();
				jobTitleCode.add("HADMDI");
				jobTitleCode.add("SADMDI");
				inputs.put("unmatchedJobTitleCodeList", jobTitleCode);

				inputs.put("effectiveBeginDate", new Date(Calendar
						.getInstance().getTimeInMillis()));
				inputs.addQualifier("effectiveBeginDateLessThanEqualTo", true);
				inputs.put("effectiveEndDate", new Date(Calendar.getInstance()
						.getTimeInMillis()));
				inputs.addQualifier("effectiveEndDateGreaterThanEqualTo", true);
				inputs.put("jobEffectiveBeginDate", new Date(Calendar
						.getInstance().getTimeInMillis()));
				inputs.addQualifier("jobEffectiveBeginDateLessThanEqualTo",
						true);
			}
			//District Manager
			else if (selectedOption.equalsIgnoreCase("4")) {
				inputs.put("userId", ldap);
				inputs.put("successionPlanSecurityGroupId", 300);
				inputs.put("languageCode", DAOConstants.lang_code);

				List<String> jobTitleCode = new ArrayList<String>();
				jobTitleCode.add("INTRDM");
				jobTitleCode.add("SDM");
				jobTitleCode.add("SDAPMG");
				jobTitleCode.add("SMRAPO");
				jobTitleCode.add("SSMAPM");
				inputs.put("jobTitleCodeList", jobTitleCode);

				inputs.put("effectiveBeginDate", new Date(Calendar
						.getInstance().getTimeInMillis()));
				inputs.addQualifier("effectiveBeginDateLessThanEqualTo", true);
				inputs.put("effectiveEndDate", new Date(Calendar.getInstance()
						.getTimeInMillis()));
				inputs.addQualifier("effectiveEndDateGreaterThanEqualTo", true);
				inputs.put("jobEffectiveBeginDate", new Date(Calendar
						.getInstance().getTimeInMillis()));
				inputs.addQualifier("jobEffectiveBeginDateLessThanEqualTo",
						true);
			}

			//Merchant
			else if (selectedOption.equalsIgnoreCase("5")) {
				inputs.put("userId", ldap);
				inputs.put("successionPlanSecurityGroupId", 300);
				inputs.put("languageCode", DAOConstants.lang_code);
				inputs.put("nlsJobTitleDescription", "MERCHANT");
				inputs.addQualifier("nlsJobTitleDescriptionContains", true);

				inputs.put("jobTitleCode", "SMCHAD");
				inputs.addQualifier("jobTitleCodeNotEqualTo", true);

				//inputs.put("employeeJobTitleCode", "SREGIN");

				List<String> jobTitleCode = new ArrayList<String>();
				jobTitleCode.add("SMCEMR");
				jobTitleCode.add("SDEXEC");
				jobTitleCode.add("SMCERM");
				jobTitleCode.add("SREGIN");
				inputs.put("employeeJobTitleCodeList", jobTitleCode);

				inputs.put("effectiveBeginDate", new Date(Calendar
						.getInstance().getTimeInMillis()));
				inputs.addQualifier("effectiveBeginDateLessThanEqualTo", true);
				inputs.put("effectiveEndDate", new Date(Calendar.getInstance()
						.getTimeInMillis()));
				inputs.addQualifier("effectiveEndDateGreaterThanEqualTo", true);
				inputs.put("jobEffectiveBeginDate", new Date(Calendar
						.getInstance().getTimeInMillis()));
				inputs.addQualifier("jobEffectiveBeginDateLessThanEqualTo",
						true);
			}

			//Store Manager
			else if (selectedOption.equalsIgnoreCase("6")) {
				inputs.put("userId", ldap);
				inputs.put("successionPlanSecurityGroupId", 300);
				inputs.put("languageCode", DAOConstants.lang_code);

				List<String> jobTitleCode = new ArrayList<String>();
				jobTitleCode.add("STRMGR");
				jobTitleCode.add("COMGR");
				jobTitleCode.add("INTRSM");
				inputs.put("jobTitleCodeList", jobTitleCode);

				inputs.put("effectiveBeginDate", new Date(Calendar
						.getInstance().getTimeInMillis()));
				inputs.addQualifier("effectiveBeginDateLessThanEqualTo", true);
				inputs.put("effectiveEndDate", new Date(Calendar.getInstance()
						.getTimeInMillis()));
				inputs.addQualifier("effectiveEndDateGreaterThanEqualTo", true);
				inputs.put("jobEffectiveBeginDate", new Date(Calendar
						.getInstance().getTimeInMillis()));
				inputs.addQualifier("jobEffectiveBeginDateLessThanEqualTo",
						true);
			}

			//HR Manager
			else if (selectedOption.equalsIgnoreCase("7")) {
				inputs.put("userId", ldap);
				inputs.put("successionPlanSecurityGroupId", 300);
				inputs.put("languageCode", DAOConstants.lang_code);

				List<String> jobTitleCode = new ArrayList<String>();
				jobTitleCode.add("SHRM");
				jobTitleCode.add("HRM");
				jobTitleCode.add("ASDSUP");
				jobTitleCode.add("KCASUP");
				inputs.put("jobTitleCodeList", jobTitleCode);

				inputs.put("effectiveBeginDate", new Date(Calendar
						.getInstance().getTimeInMillis()));
				inputs.addQualifier("effectiveBeginDateLessThanEqualTo", true);
				inputs.put("effectiveEndDate", new Date(Calendar.getInstance()
						.getTimeInMillis()));
				inputs.addQualifier("effectiveEndDateGreaterThanEqualTo", true);
				inputs.put("jobEffectiveBeginDate", new Date(Calendar
						.getInstance().getTimeInMillis()));
				inputs.addQualifier("jobEffectiveBeginDateLessThanEqualTo",
						true);
			}

			//Assistant Manager
			else if (selectedOption.equalsIgnoreCase("8")) {
				inputs.put("userId", ldap);
				inputs.put("successionPlanSecurityGroupId", 300);
				inputs.put("languageCode", DAOConstants.lang_code);

				List<String> jobTitleCode = new ArrayList<String>();
				jobTitleCode.add("ASM");
				jobTitleCode.add("ASMAD");
				jobTitleCode.add("NOPASM");
				jobTitleCode.add("SOPASM");
				jobTitleCode.add("TRNASM");
				jobTitleCode.add("MGRTRN");
				jobTitleCode.add("ARSM");
				jobTitleCode.add("ASM");
				jobTitleCode.add("ASMAD");
				jobTitleCode.add("ASMHDS");
				jobTitleCode.add("NOPASM");
				jobTitleCode.add("SDCASM");
				jobTitleCode.add("SINMGR");
				jobTitleCode.add("SOPASM");
				jobTitleCode.add("SOPMAN");
				jobTitleCode.add("SOPRMG");
				jobTitleCode.add("SPSMGR");
				jobTitleCode.add("STRLPM");
				jobTitleCode.add("TRNASM");
				jobTitleCode.add("SASM");
				jobTitleCode.add("SMGXDS");

				inputs.put("jobTitleCodeList", jobTitleCode);
				inputs.put("effectiveBeginDate", new Date(Calendar
						.getInstance().getTimeInMillis()));
				inputs.addQualifier("effectiveBeginDateLessThanEqualTo", true);
				inputs.put("effectiveEndDate", new Date(Calendar.getInstance()
						.getTimeInMillis()));
				inputs.addQualifier("effectiveEndDateGreaterThanEqualTo", true);
				inputs.put("jobEffectiveBeginDate", new Date(Calendar
						.getInstance().getTimeInMillis()));
				inputs.addQualifier("jobEffectiveBeginDateLessThanEqualTo",
						true);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}

		BasicDAO.getResult("WorkforceSuccessionPlanning", 55, 1, inputs,
				new ResultsReader() {
					public void readResults(Results results, Query query,
							Inputs inputs) throws QueryException {
						AdvancedSearchDTO advancedSearchDTO = null;
						while (results.next()) {

							advancedSearchDTO = new AdvancedSearchDTO();
							advancedSearchDTO.setAssociates(results.getString("associates").trim());
							if (advancedSearchDTO.getAssociates() != null && !advancedSearchDTO.getAssociates().trim().isEmpty()) {
								String[] names = advancedSearchDTO.getAssociates().split(",");
								if (names.length == 2) {
									advancedSearchDTO.setFirstName(names[1]);
									advancedSearchDTO.setLastName(names[0]);
								} else if (names.length == 1) {
									advancedSearchDTO.setLastName(names[0]);
								}
							}
							advancedSearchDTO
									.setConsentDecreeJobCategoryCode(results
											.getString(
													"consentDecreeJobCategoryCode")
											.trim());
							advancedSearchDTO.setDistrictCode(results
									.getString("districtCode").trim());
							advancedSearchDTO
									.setHumanResourcesSystemDepartmentNumber(results
											.getString(
													"humanResourcesSystemDepartmentNumber")
											.trim());
							advancedSearchDTO
									.setHumanResourcesSystemStoreNumber(results
											.getString(
													"humanResourcesSystemStoreNumber")
											.trim());
							advancedSearchDTO.setJobTitleCode(results
									.getString("jobTitleCode").trim());
							advancedSearchDTO.setJobTitleDescription(results
									.getString("jobTitleDescription").trim());
							advancedSearchDTO.setZeroEmployeeId(results
									.getString("zeroEmployeeId").trim());
							advancedSearchList.add(advancedSearchDTO);
						}
					}
				});

		if (logger.isDebugEnabled()) {
			logger.debug("finish getAdvancedQuickSearchDetails()");
			logger.debug("returning " + advancedSearchList.size() + " item(s)");
		}
		return advancedSearchList;
	}

	public static List<AdvancedQuickSearchCategoryDTO> getAdvancedQuickSearchCategoryDetails()
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getAdvancedSearchCategoryDetails");
		}
//		List<Object> inputUnmatchedSuccessionPlanQueryDescriptionList = new ArrayList<Object>();

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readNlsSuccessionPlanAndQueryDetails") //TODO: Verify contract name, version, and replace values below
				.input("publicFlag", true) // optional
				.input("languageCode", DAOConstants.lang_code) // optional
				//				.input("unmatchedSuccessionPlanQueryDescription", "value") // optional
				//				.input("unmatchedSuccessionPlanQueryDescriptionList",
				//						inputUnmatchedSuccessionPlanQueryDescriptionList) // optional

				.list(AdvancedQuickSearchCategoryDTO.class);
	}

	public static List<AdvancedSearchCategoryDTO> getAdvancedSearchCategoryDetails()
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getAdvancedSearchCategoryDetails");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readNlsSuccessionPlanAndColumnDetails") //TODO: Verify contract name, version, and replace values below
				.input("unmatchedSuccessionPlanColumnId", 1104) // optional
				.input("languageCode", DAOConstants.lang_code) // optional
				//				.inputAllowNull("unmatchedPromptText", "value") // can be null, optional

				.list(AdvancedSearchCategoryDTO.class);
	}

	public static List<ApplyAssociateDTO> applyAssociate(String ldap)
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start applyAssociate");
		}

//		Date date = new Date(System.currentTimeMillis());

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceEmployeePerformance.1.readNlsSuccessionPlanUserEmployeeWorkAndJobTitleDetails") //TODO: Verify contract name, version, and replace values below
				.input("userId", ldap) // optional
				.input("successionPlanSecurityGroupId", 300) // optional
				.input("workOkayFlag", true) // optional
				.input("successionPlanEmployeeStatusCode", (short) 100) // optional
				.input("effectiveBeginDate",
						new Date(System.currentTimeMillis())) // optional
				.input("languageCode", DAOConstants.lang_code) // optional
				.qualify("effectiveBeginDateLessThanEqualTo", true) // optional
				.list(ApplyAssociateDTO.class);
	}

	public static boolean applyAssociateUpdate(String ldap, boolean workFlag,
			boolean allAssociates) throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start applyAssociateUpdate");
		}
		//TODO: Replace values below

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		if (!allAssociates) {
			if (workFlag) {
				List<Object> inputEmployeeIdList = new ArrayList<Object>();
				inputEmployeeIdList.add("001000278");
				return DAO
						.update("WorkforceSuccessionPlanning.1.updateSuccessionPlanUserEmployeeWork") //TODO: Verify contract name, version, and replace values below
						.input("userId", ldap) // required
						.input("successionPlanSecurityGroupId", 300) // required
						//					.input("employeeId", "value") // optional
						.input("employeeIdList", inputEmployeeIdList) // optional
						.input("workOkayFlag", workFlag) // optional
						.success();
			} else {
				return DAO
						.update("WorkforceSuccessionPlanning.1.updateSuccessionPlanUserEmployeeWork") //TODO: Verify contract name, version, and replace values below
						.input("userId", ldap) // required
						.input("successionPlanSecurityGroupId", 300) // required
						//					.input("employeeId", "value") // optional
						//					.input("employeeIdList", inputEmployeeIdList) // optional
						.input("workOkayFlag", workFlag) // optional
						.success();
			}
		} else {
			if (workFlag) {
				List<String> inputEmployeeIdList = new ArrayList<String>();
				inputEmployeeIdList.add("500049457");
				inputEmployeeIdList.add("345344356");
				inputEmployeeIdList.add("001388620");
				inputEmployeeIdList.add("001354458");
				inputEmployeeIdList.add("001349542");
				inputEmployeeIdList.add("001342977");
				inputEmployeeIdList.add("001330527");
				inputEmployeeIdList.add("001313966");
				inputEmployeeIdList.add("001305061");
				inputEmployeeIdList.add("001302635");
				inputEmployeeIdList.add("001267790");
				inputEmployeeIdList.add("001263795");
				inputEmployeeIdList.add("001238312");
				inputEmployeeIdList.add("001228415");
				inputEmployeeIdList.add("001211591");
				inputEmployeeIdList.add("001196357");
				inputEmployeeIdList.add("001187075");
				inputEmployeeIdList.add("001185428");
				inputEmployeeIdList.add("001183979");
				inputEmployeeIdList.add("001183113");
				inputEmployeeIdList.add("001177387");
				inputEmployeeIdList.add("001176052");
				inputEmployeeIdList.add("001174795");
				inputEmployeeIdList.add("001154133");
				inputEmployeeIdList.add("001145213");
				inputEmployeeIdList.add("001144995");
				inputEmployeeIdList.add("001144286");
				inputEmployeeIdList.add("001143705");
				inputEmployeeIdList.add("001141406");
				inputEmployeeIdList.add("001137915");
				inputEmployeeIdList.add("001131118");
				inputEmployeeIdList.add("001129762");
				inputEmployeeIdList.add("001125170");
				inputEmployeeIdList.add("001122925");
				inputEmployeeIdList.add("001120200");
				inputEmployeeIdList.add("001119342");
				inputEmployeeIdList.add("001118497");
				inputEmployeeIdList.add("001117686");
				inputEmployeeIdList.add("001115847");
				inputEmployeeIdList.add("001115550");
				inputEmployeeIdList.add("001113958");
				inputEmployeeIdList.add("001113952");
				inputEmployeeIdList.add("001113792");
				inputEmployeeIdList.add("001113656");
				inputEmployeeIdList.add("001112399");
				inputEmployeeIdList.add("001112151");
				inputEmployeeIdList.add("001111810");
				inputEmployeeIdList.add("001110920");
				inputEmployeeIdList.add("001110477");
				inputEmployeeIdList.add("001109929");
				inputEmployeeIdList.add("001109692");
				inputEmployeeIdList.add("001109356");
				inputEmployeeIdList.add("001109121");
				inputEmployeeIdList.add("001108952");
				inputEmployeeIdList.add("001108705");
				inputEmployeeIdList.add("001107420");
				inputEmployeeIdList.add("001104216");
				inputEmployeeIdList.add("001104172");
				inputEmployeeIdList.add("001100655");
				inputEmployeeIdList.add("001097956");
				inputEmployeeIdList.add("001096024");
				inputEmployeeIdList.add("001093110");
				inputEmployeeIdList.add("001088132");
				inputEmployeeIdList.add("001087097");
				inputEmployeeIdList.add("001079802");
				inputEmployeeIdList.add("001079544");
				inputEmployeeIdList.add("001079413");
				inputEmployeeIdList.add("001079200");
				inputEmployeeIdList.add("001074957");
				inputEmployeeIdList.add("001074526");
				inputEmployeeIdList.add("001072955");
				inputEmployeeIdList.add("001071625");
				inputEmployeeIdList.add("001071567");
				inputEmployeeIdList.add("001070965");
				inputEmployeeIdList.add("001070795");
				inputEmployeeIdList.add("001069536");
				inputEmployeeIdList.add("001068940");
				inputEmployeeIdList.add("001067600");
				inputEmployeeIdList.add("001066598");
				inputEmployeeIdList.add("001062945");
				inputEmployeeIdList.add("001061446");
				inputEmployeeIdList.add("001039175");
				inputEmployeeIdList.add("001030165");
				inputEmployeeIdList.add("001028794");
				inputEmployeeIdList.add("001005987");
				inputEmployeeIdList.add("001003065");
				inputEmployeeIdList.add("001002991");
				inputEmployeeIdList.add("001002620");
				inputEmployeeIdList.add("001002522");
				inputEmployeeIdList.add("001002516");
				inputEmployeeIdList.add("001002177");
				inputEmployeeIdList.add("001000658");
				inputEmployeeIdList.add("001000278");
				inputEmployeeIdList.add("001000260");
				inputEmployeeIdList.add("001000182");
				inputEmployeeIdList.add("001000026");

				return DAO
						.update("WorkforceSuccessionPlanning.1.updateSuccessionPlanUserEmployeeWork") //TODO: Verify contract name, version, and replace values below
						.input("userId", ldap) // required
						.input("successionPlanSecurityGroupId", 300) // required
						//					.input("employeeId", "value") // optional
						.input("employeeIdList", inputEmployeeIdList) // optional
						.input("workOkayFlag", workFlag) // optional
						.success();
			} else {
				return DAO
						.update("WorkforceSuccessionPlanning.1.updateSuccessionPlanUserEmployeeWork") //TODO: Verify contract name, version, and replace values below
						.input("userId", ldap) // required
						.input("successionPlanSecurityGroupId", 300) // required
						//					.input("employeeId", "value") // optional
						//					.input("employeeIdList", inputEmployeeIdList) // optional
						.input("workOkayFlag", workFlag) // optional
						.success();
			}
		}
	}

	public static List<ApplyAllAssociateDTO> applyAllAssociate(String ldap)
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start applyAllAssociate");
		}
		List<Object> inputJobTitleCodeList = new ArrayList<Object>();
		inputJobTitleCodeList.add("INTRDM");
		inputJobTitleCodeList.add("sdm");

//		List<Object> inputUnmatchedJobTitleCodeList = new ArrayList<Object>();
//		Date date = new Date(System.currentTimeMillis());

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readSuccessionPlanEmployeeAndNlsJobTitleHumanResourcesWorkDetails") //TODO: Verify contract name, version, and replace values below
				//				.input("jobTitleCode", "value") // optional
				//				.input("unmatchedJobTitleCode", "value") // optional
				.input("effectiveBeginDate",
						new Date(System.currentTimeMillis())) // optional
				.inputAllowNull("effectiveEndDate",
						new Date(System.currentTimeMillis())) // can be null, optional
				.input("jobTittleEffectiveBeginDate",
						new Date(System.currentTimeMillis())) // optional
				.input("languageCode", DAOConstants.lang_code) // optional
				//				.input("jobTitleDescription", "value") // optional
				//				.input("titleDescription", "value") // optional
				.input("userId", ldap) // optional
				.input("successionPlanSecurityGroupId", 300) // optional
				.input("jobTitleCodeList", inputJobTitleCodeList) // optional
				//				.input("unmatchedJobTitleCodeList",
				//						inputUnmatchedJobTitleCodeList) // optional
				//				.qualify("effectiveBeginDateGreaterThanEqualTo", false) // optional
				//				.qualify("effectiveBeginDateGreaterThan", false) // optional
				.qualify("effectiveBeginDateLessThanEqualTo", true) // optional
				//				.qualify("effectiveBeginDateLessThan", false) // optional
				//				.qualify("effectiveBeginDateEqualTo", false) // optional
				//				.qualify("effectiveBeginDateNotEqualTo", false) // optional
				.qualify("effectiveEndDateGreaterThanEqualTo", true) // optional
				//				.qualify("effectiveEndDateGreaterThan", false) // optional
				//				.qualify("effectiveEndDateLessThanEqualTo", false) // optional
				//				.qualify("effectiveEndDateLessThan", false) // optional
				//				.qualify("effectiveEndDateEqualTo", false) // optional
				//				.qualify("effectiveEndDateNotEqualTo", false) // optional
				//				.qualify("jobTittleEffectiveBeginDateGreaterThanEqualTo", false) // optional
				//				.qualify("jobTittleEffectiveBeginDateGreaterThan", false) // optional
				.qualify("jobTittleEffectiveBeginDateLessThanEqualTo", true) // optional
				//				.qualify("jobTittleEffectiveBeginDateLessThan", false) // optional
				//				.qualify("jobTittleEffectiveBeginDateEqualTo", false) // optional
				//				.qualify("jobTittleEffectiveBeginDateNotEqualTo", false) // optional
				//				.qualify("jobTitleDescriptionContains", false) // optional
				//				.qualify("jobTitleDescriptionBeginsWith", false) // optional
				//				.qualify("jobTitleDescriptionEndsWith", false) // optional
				//				.qualify("titleDescriptionContains", false) // optional
				//				.qualify("titleDescriptionBeginsWith", false) // optional
				//				.qualify("titleDescriptionEndsWith", false) // optional
				.list(ApplyAllAssociateDTO.class);
	}

	public static List<LoadQueryDTO> loadQuery(String ldap)
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start loadQuery");
			logger.debug("executing the query");
			logger.debug("ldap " + ldap);
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readSuccessionPlanQueryAndParameterDetails") //TODO: Verify contract name, version, and replace values below
				.input("createUserId", ldap) // optional
				.input("publicFlag", false) // optional

				.list(LoadQueryDTO.class);
	}

	public static boolean saveSQLQuery(int sqlCount, String ldap,
			String description, String queryName) throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start saveSQLQuery");
			logger.debug("executing the query");
			logger.debug("ldap " + ldap);
		}

		return DAO
				.insert("WorkforceSuccessionPlanning.1.createSuccessionPlanQuery")//TODO: Verify contract name, version, and replace values below
				.input("successionPlanQueryId", sqlCount) // required
				.input("createUserId", ldap) // optional
				.input("successionPlanQueryName", queryName) // required
				.input("publicFlag", false) // required
				//.input("lastUpdateUserId", associateId) // optional
				.inputAllowNull("successionPlanQueryDescription", description) // can be null, required
				.success();
	}

	public static List<Integer> getQueryId() throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getQueryId");
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readMaximumSuccessionPlanQueryDetails") //TODO: Verify contract name, version, and replace values below
				.list(Integer.class);
	}

	public static boolean saveSQLQueryParameter(int sqlCount, short seqNum,
			int colId, String openParentValue, String operator,
			String paramValue, String connectOperatorText,
			String closeParentValue, String ldap) throws QueryException {

		if (logger.isDebugEnabled()) {
			logger.debug("start saveSQLQueryParameter");
			logger.debug("executing the query");
		}
		return DAO
				.insert("WorkforceSuccessionPlanning.1.createSuccessionPlanQueryParameter")//TODO: Verify contract name, version, and replace values below
				.input("successionPlanQueryId", sqlCount) // required
				.input("successionPlanColumnId", colId) // required
				.input("sequenceNumber", seqNum) // required
				.inputAllowNull("openParenthesesValue", openParentValue) // can be null, required
				.input("operatorText", operator) // required
				.input("parameterValue", paramValue) // required
				.inputAllowNull("connectOperatorText", connectOperatorText) // can be null, required
				.inputAllowNull("closeParenthesesValue", closeParentValue) // can be null, required
				.input("lastUpdateUserId", ldap) // optional
				.success();
	}

	public static boolean deleteQuery(int sqlID) throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start deleteQuery");
			logger.debug("executing the query");
			logger.debug("sqlId is" + sqlID);
		}
		return DAO
				.delete("WorkforceSuccessionPlanning.1.deleteSuccessionPlanQuery") //TODO: Verify contract name, version, and replace values below
				.input("successionPlanQueryId", sqlID) // required
				.success();
	}

	public static boolean deleteQueryParm(int sqlID) throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start deleteQueryParm");
			logger.debug("executing the query");
			logger.debug("sqlId is" + sqlID);
		}
		return DAO
				.delete("WorkforceSuccessionPlanning.1.deleteSuccessionPlanQueryParameter") //TODO: Verify contract name, version, and replace values below
				.input("successionPlanQueryId", sqlID) // required
				//				.input("successionPlanColumnId", sqlColID) // optional
				//				.input("sequenceNumber", (short) 0) // optional
				.success();
	}

	public static List<FindDTO> getFind(String ldap, FindRequest findRequest)
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getFind");
			logger.info(" ********** LDAP ID: " + ldap);
			logger.debug("executing the query");
		}
		Date date = new Date(System.currentTimeMillis());
		//Last Name
		if (findRequest.getNameLast() != null
				&& !findRequest.getNameLast().trim().isEmpty()) {

			if (logger.isDebugEnabled()) {
				logger.debug("getFind - search by last name: "
						+ findRequest.getNameLast().trim());
			}

			return DAO
					.select("WorkforceSuccessionPlanning.1.readDistinctSuccessionPlanningEmployeeAndIndividualPlanDetails") //TODO: Verify contract name, version, and replace values below
					.input("userId", ldap) // optional
					.input("successionPlanSecurityGroupId", 300) // optional
					.input("partialLastName",
							findRequest.getNameLast().toUpperCase()) // optional
					.input("successionPlanEmployeeStatusCode", (short) 100) // optional
					//					.inputAllowNull("zeroEmployeeId", associateId) // can be null, optional
					.input("effectiveBeginDate",
							new Date(System.currentTimeMillis())) // optional
					//.input("jobEffectiveBeginDate",
					//		new Date(System.currentTimeMillis())) // optional
					.input("languageCode", DAOConstants.lang_code) // optional
					.qualify("effectiveBeginDateLessThanEqualTo", date) // optional
					.qualify("isOrderByRequiredFlag", true) // optional
					.list(FindDTO.class);
		}
		//associateId
		else if (findRequest.getAssociateId() != null
				&& !findRequest.getAssociateId().trim().isEmpty()) {

			if (logger.isDebugEnabled()) {
				logger.debug("getFind - search by associate Id: "
						+ findRequest.getAssociateId().trim());
			}
			return DAO
					.select("WorkforceSuccessionPlanning.1.readDistinctSuccessionPlanningEmployeeAndIndividualPlanDetails") //TODO: Verify contract name, version, and replace values below
					.input("userId", ldap) // optional
					.input("successionPlanSecurityGroupId", 300) // optional
					.input("successionPlanEmployeeStatusCode", (short) 100) // optional
					.inputAllowNull("zeroEmployeeId",
							findRequest.getAssociateId().trim()) // can be null, optional
					.input("effectiveBeginDate",
							new Date(System.currentTimeMillis())) // optional
					.input("languageCode", DAOConstants.lang_code) // optional
					.qualify("effectiveBeginDateLessThanEqualTo", true) // optional
					.qualify("isOrderByRequiredFlag", true) // optional
					.list(FindDTO.class);
		}
		//NameContains
		else if (findRequest.getNameContain() != null
				&& !findRequest.getNameContain().trim().isEmpty()) {
			if (logger.isDebugEnabled()) {
				logger.debug("getFind - search by name contains: "
						+ findRequest.getNameContain().trim());
			}
			return DAO
					.select("WorkforceSuccessionPlanning.1.readDistinctSuccessionPlanningEmployeeAndIndividualPlanDetails") //TODO: Verify contract name, version, and replace values below
					.input("userId", ldap) // optional
					.input("successionPlanSecurityGroupId", 300) // optional
					.input("partialEmployeeLastName",
							findRequest.getNameContain().trim().toUpperCase()) // optional
					.input("partialEmployeeFirstName",
							"%"+findRequest.getNameContain().trim().toUpperCase()) // optional
					.input("successionPlanEmployeeStatusCode", (short) 100) // optional
					.input("effectiveBeginDate",date) // optional
					.input("languageCode", DAOConstants.lang_code) // optional
					.qualify("effectiveBeginDateLessThanEqualTo", true) // optional
					.qualify("isOrderByRequiredFlag", true) // optional
					.list(FindDTO.class);
		}
		//First Name Starts
		else if (findRequest.getFirstNameStartsWith() != null
				&& !findRequest.getFirstNameStartsWith().trim().isEmpty()) {

			if (logger.isDebugEnabled()) {
				logger.debug("getFind - search by first name starts with: "
						+ findRequest.getFirstNameStartsWith().trim());
			}
			return DAO
					.select("WorkforceSuccessionPlanning.1.readDistinctSuccessionPlanningEmployeeAndIndividualPlanDetails") //TODO: Verify contract name, version, and replace values below
					.input("userId", ldap) // optional
					.input("successionPlanSecurityGroupId", 300) // optional
					.input("partialEmployeeFirstName",
							findRequest.getFirstNameStartsWith().trim()
									.toUpperCase()) // optional
					.input("successionPlanEmployeeStatusCode", (short) 100) // optional
					.input("effectiveBeginDate", new Date(System.currentTimeMillis())) // optional
					.input("languageCode", DAOConstants.lang_code) // optional
					.qualify("effectiveBeginDateLessThanEqualTo", true) // optional
					.qualify("isOrderByRequiredFlag", true) // optional
					.list(FindDTO.class);
		}
		//Preferred Name
		else if (findRequest.getPreferredNameStartsWith() != null
				&& !findRequest.getPreferredNameStartsWith().trim().isEmpty()) {

			if (logger.isDebugEnabled()) {
				logger.debug("getFind - search by preferred name starts with: "
						+ findRequest.getPreferredNameStartsWith().trim());
			}
			return DAO
					.select("WorkforceSuccessionPlanning.1.readDistinctSuccessionPlanningEmployeeAndIndividualPlanDetails") //TODO: Verify contract name, version, and replace values below
					.input("userId", ldap) // optional
					.input("successionPlanSecurityGroupId", 300) // optional
					.input("successionPlanEmployeeStatusCode", (short) 100) // optional
					.input("effectiveBeginDate",
							new Date(System.currentTimeMillis())) // optional
					.input("languageCode", DAOConstants.lang_code) // optional
					.inputAllowNull(
							"partialPreferenceName",
							findRequest.getPreferredNameStartsWith().trim()
									.toUpperCase()) // can be null, optional
					.qualify("effectiveBeginDateLessThanEqualTo", true) // optional
					.qualify("isOrderByRequiredFlag", true) // optional
					.list(FindDTO.class);
		}
		//Title
		else if (findRequest.getJobTitle() != null
				&& !findRequest.getJobTitle().trim().isEmpty()) {

			if (logger.isDebugEnabled()) {
				logger.debug("getFind - search by job title starts with: "
						+ findRequest.getJobTitle().trim());
			}
			return DAO
					.select("WorkforceSuccessionPlanning.1.readDistinctSuccessionPlanningEmployeeAndIndividualPlanDetails") //TODO: Verify contract name, version, and replace values below
					.input("userId", ldap) // optional
					.input("successionPlanSecurityGroupId", 300) // optional
					.input("successionPlanEmployeeStatusCode", (short) 100) // optional
					.input("effectiveBeginDate",
							new Date(System.currentTimeMillis())) // optional
					.input("languageCode", DAOConstants.lang_code) // optional
					.input("partialJobTitleDescription",
							findRequest.getJobTitle().trim().toUpperCase()) // optional
					.qualify("effectiveBeginDateLessThanEqualTo", true) // optional
					.qualify("isOrderByRequiredFlag", true) // optional
					.list(FindDTO.class);
		}
		//Store Number
		else if (findRequest.getStoreNo() != null
				&& !findRequest.getStoreNo().trim().isEmpty()) {

			if (logger.isDebugEnabled()) {
				logger.debug("getFind - search by store number with: "
						+ findRequest.getStoreNo().trim().toUpperCase());
			}
			return DAO
					.select("WorkforceSuccessionPlanning.1.readDistinctSuccessionPlanningEmployeeAndIndividualPlanDetails") //TODO: Verify contract name, version, and replace values below
					.input("userId", ldap) // optional
					.input("successionPlanSecurityGroupId", 300) // optional
					.input("humanResourcesSystemStoreNumber",
							findRequest.getStoreNo().trim().toUpperCase()) // optional
					.input("successionPlanEmployeeStatusCode", (short) 100) // optional
					.input("effectiveBeginDate",
							new Date(System.currentTimeMillis())) // optional
					.input("languageCode", DAOConstants.lang_code) // optional
					.qualify("effectiveBeginDateLessThanEqualTo", true) // optional
					.qualify("isOrderByRequiredFlag", true) // optional
					.list(FindDTO.class);
		}
		//First Name, Last Name
		else if (findRequest.getLastfirstName() != null
				&& !findRequest.getLastfirstName().trim().isEmpty()) {
			if (logger.isDebugEnabled()) {
				logger.debug("getFind - search by last,first name with: "
						+ findRequest.getLastfirstName().trim());
			}
			String firstName = "";
			String lastName = "";
			int commaLocation = findRequest.getLastfirstName().trim()
					.indexOf(',');
			lastName = findRequest.getLastfirstName().substring(0,
					commaLocation);
			firstName = findRequest.getLastfirstName().substring(
					commaLocation + 1);

			return DAO
					.select("WorkforceSuccessionPlanning.1.readDistinctSuccessionPlanningEmployeeAndIndividualPlanDetails") //TODO: Verify contract name, version, and replace values below
					.input("userId", ldap) // optional
					.input("successionPlanSecurityGroupId", 300) // optional
					.input("successionPlanEmployeeStatusCode", (short) 100) // optional
					.input("effectiveBeginDate",
							new Date(System.currentTimeMillis())) // optional
					.input("languageCode", DAOConstants.lang_code) // optional
					.input("firstName", firstName.toUpperCase()) // optional
					.input("lastName", lastName.toUpperCase()) // optional
					.qualify("isOrderByRequiredFlag", true) // optional
					.qualify("effectiveBeginDateLessThanEqualTo", true) // optional
					.list(FindDTO.class);
		}
		return null;
	}
}
