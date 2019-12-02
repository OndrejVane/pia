package com.vane.pia.configuration;

public class Pages {

    private static final String ERROR = "?error";
    private static final String SUCCESS = "?success";
    private static final String REDIRECT = "redirect:";
    private static final String FORWARD = "forward:";

    // web paths
    public static final String HOME_PAGE = "/";
    public static final String LOGIN_PAGE = "/login";
    public static final String INFORMATION_PAGE = "/information";
    public static final String ADD_USER_PAGE = "/admin/users/add";
    public static final String USERS_PAGE = "/admin/users";
    public static final String USER_DETAIL_PAGE = "/user";
    public static final String USER_DETAIL_EDIT_PAGE = "/user/details";
    public static final String USER_PASSWORD_EDIT_PAGE = "/user/password";

    // html templates
    public static final String HOME_PAGE_TEMPLATE = "index";
    public static final String LOGIN_PAGE_TEMPLATE = "login";
    public static final String INFORMATION_PAGE_TEMPLATE = "information";
    public static final String ADD_USER_TEMPLATE = "add-user";
    public static final String USER_DETAIL_TEMPLATE = "user";

    // pages with error
    public static final String ERROR_ADD_USER_PAGE = ADD_USER_PAGE + ERROR;

    // pages with success
    public static final String USER_DETAIL_TEMPLATE_SUCCESS = USER_DETAIL_TEMPLATE + SUCCESS;

    // redirects
    public static final String REDIRECT_USERS_PAGE_WITH_SUCCESS = REDIRECT + USERS_PAGE + SUCCESS;
    public static final String REDIRECT_USER_PAGE_DETAILS_SUCCESS = REDIRECT + USER_DETAIL_PAGE + "?successDetail";
    public static final String REDIRECT_USER_PAGE_PASSWORD_SUCCESS = REDIRECT + USER_DETAIL_PAGE + "?successPassword";
    public static final String REDIRECT_USER_PAGE_PASSWORD_ERROR_CONFIRM = REDIRECT + USER_DETAIL_PAGE + "?confirmPassword";
    public static final String REDIRECT_USER_PAGE_PASSWORD_ERROR_INCORRECT = REDIRECT + USER_DETAIL_PAGE + "?passwordIncorrect";


    // forwards
    public static final String FORWARD_HOME_PAGE = FORWARD + HOME_PAGE;

    private Pages() {
    }
}
