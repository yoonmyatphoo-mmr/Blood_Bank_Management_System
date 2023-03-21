package com.ymp.j2ee_project.util;

/**
 * @author: Yoon Myat Phoo
 * @created: 22/08/2022
 * @project: J2EE_Project
 * @package: com.ymp.j2ee_project.util
 */

public class ValidationUtil {
    public static boolean isValidString(String value){
        if(value == null || value.trim().equals(""))
            return false;
        return true;
    }

}
