package com.vmavropo.utils;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class StaticContext {

    public static final String TENDER_REGISTER_INFO = "Office A, Station 01, Created: 13/04/2022 10:08:44, Open";
    public static final String PAYOR_NAME = "Kaladris";
    public static final String PAYOR_CHECKBOX_LABEL = "Kaladris S.A.";
    public static final String CURRENCY_OPTION = "Euro";
    public static final String TENDER_TYPE = "Cash";
    public static final int TENDER_AMOUNT = 100;
    public static final String DESTINATION_LABEL = "Pay a Pay Plan";
    public static final String DESTINATION_FILTER = "Kaladris";
    public static final String DESTINATION_PARTY_ROLE = "Kaladris S.A., Tax Identification Number: 889998898, Business";
    public static final String EDIT_CONTACT = "Business phone number: +30858959265";
    public static final String REVENUE_TYPE = "AS_REV_TY";
    public static final List<String> CASE_TYPE_DESCRIPTION = List.of("code!222", "code!222");
    public static final List<String> WORKER_PROFILES = List.of("Test", "User");
    public static final List<String> RELATED_ENTITY_TYPES = List.of("Process Instance", "Waivers-Write Off Definitions");
    public static final List<String> CASE_TYPE_ACTIVITIES = List.of("Case Task Type","code!333","code!333","1","test", "Human Task Type","code!444","code!444","2","P10");

    public static final String HUMAN_TASK_TYPE_ASSIGN_CASE_WORKER = "No";
    public static final String HUMAN_TASK_TYPE_FIRST_NAME = "Superuser";

    public static final String USER_PARAM = "User";
    public static final String NUMBER_PARAM = "50";
}
