package com.passwordsafe.policydecorator;

public interface IPasswordPolicyTester {
    boolean fulfilsPolicy(String password);

    String getPolicyMsg();
}
