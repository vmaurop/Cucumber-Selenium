package com.vmavropo.utils.common;

import com.vmavropo.utils.Test;
import com.vmavropo.utils.config.EnvDataConfig;

import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class TestData {


    Test test;

    EnvDataConfig envDataConfig;

    public TestData(Test test) {
        this.test = test;
        envDataConfig = test.envDataConfig();
    }


    public String uuidCorrelationID() {
        return UUID.randomUUID().toString();
    }

    public String uuidConversationID() {
        return UUID.randomUUID().toString();
    }

    public static String generateRandomString(int length) {
        return new SecureRandom()
                .ints('a', 'z' +1).limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static int generateRandomInt(int min, int max){
       return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
