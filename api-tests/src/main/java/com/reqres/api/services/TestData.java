package com.reqres.api.services;
import com.github.javafaker.Faker;
import com.reqres.api.model.User;

public class TestData {
    Faker faker = new Faker();

    public User SetUserForTests() {
        User testUser = new User();
        testUser.setName(faker.name().fullName());
        testUser.setEmail(faker.internet().emailAddress());
        testUser.setJob(faker.job().position());
        testUser.setPassword(faker.internet().password());
        return testUser;
    }

    public User SetUserWithMissedPassword() {
        User testUser = new User();
        testUser.setName(faker.name().fullName());
        testUser.setEmail(faker.internet().emailAddress());
        testUser.setJob(faker.job().position());
        return testUser;
    }

    public User SetUserWithMissingEmail() {
        User testUser = new User();
        testUser.setName(faker.name().fullName());
        testUser.setJob(faker.job().position());
        testUser.setPassword(faker.internet().password());
        return testUser;
    }

}
