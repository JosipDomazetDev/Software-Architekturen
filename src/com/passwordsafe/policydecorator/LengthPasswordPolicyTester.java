package com.passwordsafe.policydecorator;

public class LengthPasswordPolicyTester extends PasswordPolicyTesterDecorator {
    public LengthPasswordPolicyTester(IPasswordPolicyTester passwordPolicyTester) {
        super(passwordPolicyTester);
    }

    @Override
    protected boolean fulfilsSpecificPolicy(String password) {
        // The minimum length of the password is 8 characters
        return password.length() >= 8;
    }

    @Override
    protected String getSpecificPolicyMsg() {
        return "* The minimum length of the password is 8 characters.\n";
    }

}
