package com.example.Task.Utils;

import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class Utils {

    public static Map<String, String> getAuthoritiesForUser() {

        HashMap<String, String> authoritiesMap = new HashMap<>();

        List<String> userAuthorities =
                Arrays.asList(
                        Constants.USER_INFO_AUTHORITY,
                        Constants.ADD_ITEM_AUTHORITY,
                        Constants.DELETE_ITEM_AUTHORITY
                );

        String userAuthoritiesAsString = String.join(Constants.DELIMITER, userAuthorities);

        authoritiesMap.put(Constants.USER, userAuthoritiesAsString);
        return authoritiesMap;

    }
}
