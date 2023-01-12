package com.passwordsafe.policydecorator;

public class NumberPasswordPolicyTester extends PasswordPolicyTesterDecorator {
    public NumberPasswordPolicyTester(IPasswordPolicyTester passwordPolicyTester) {
        super(passwordPolicyTester);
    }

    @Override
    protected boolean fulfilsSpecificPolicy(String password) {
        // The password must contain at least one number.
        return password.matches(".*[0-9].*");
    }

    @Override
    protected String getSpecificPolicyMsg() {
        return "* The password must contain at least one number.\n";
    }
}
