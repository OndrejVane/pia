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

    // html templates
    public static final String HOME_PAGE_TEMPLATE = "index";
    public static final String LOGIN_PAGE_TEMPLATE = "login";
    public static final String INFORMATION_PAGE_TEMPLATE = "information";
    public static final String ADD_USER_TEMPLATE = "add-user";

    // pages with error
    public static final String ERROR_ADD_USER_PAGE = ADD_USER_PAGE + ERROR;

    // pages with success

    // redirects
    public static final String REDIRECT_USERS_PAGE_WITH_SUCCESS = REDIRECT + USERS_PAGE + SUCCESS;

    // forwards
    public static final String FORWARD_HOME_PAGE = FORWARD + HOME_PAGE;

    private Pages() {
    }
}
