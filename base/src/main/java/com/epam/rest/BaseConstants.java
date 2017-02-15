package com.epam.rest;

/**
 * Created by U0160473 on 2/2/2015.
 */
public class BaseConstants
{
	public static final String APPLICATION_URL_KEY = "applicationURL";
	public static final String APPLICATION_CMS_URL_KEY = "applicationURLCMS";
	public static final String APPLICATION_EFILE_URL_KEY = "applicationURLEFile";
	public static final int DEFAULT_TIMEOUT_SECONDS = 10;

	public static final String DATATABLE_KEYWORD_EMPTY = "EMPTY";
	public static final String DATATABLE_KEYWORD_CURRENT_DATE = "CURRENT_DATE";
	public static final String DATATABLE_KEYWORD_CURRENT_DATETIME = "CURRENT_DATETIME";
	public static final String DATATABLE_KEYWORD_CURRENT_TIME = "CURRENT_TIME";
	public static final String DATATABLE_KEYWORD_GENERATE_ID = "GENERATE_ID";
	public static final String DATATABLE_KEYWORD_RANDOM = "RANDOM_";
	public static final String DATATABLE_KEYWORD_GLOBAL = "GLOBAL_";

	public static final int HTTP_STATUS_SUCCESSFUL_REST_POST = 201;
	public static final int HTTP_STATUS_SUCCESSFUL_REST_GET = 200;
	public static final int HTTP_STATUS_SUCCESSFUL_REST_PUT = 204;
	public static final int HTTP_STATUS_SUCCESSFUL_REST_DELETE = 204;
	public static final int HTTP_STATUS_NOT_FOUND = 404;

	public static final String SCREENSHOT_MODE_ALWAYS = "always";
	public static final String SCREENSHOT_MODE_NEVER = "never";
	public static final String SCREENSHOT_MODE_FAILURE = "failure";

	public static final int NO_MATCH_FOUND = -1;
}
