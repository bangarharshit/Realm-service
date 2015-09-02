package com.example.harshit.servicelearning;

import io.realm.RealmObject;

/**
 * Created by harshit on 2/9/15.
 */
public class TestModel extends RealmObject {
    private String testId;

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }
}
