package com.passwordsafe.policydecorator;

public class UpperCasePasswordPolicyTester extends PasswordPolicyTesterDecorator {
    public UpperCasePasswordPolicyTester(IPasswordPolicyTester passwordPolicyTester) {
        super(passwordPolicyTester);
    }

    @Override
    protected boolean fulfilsSpecificPolicy(String password) {
        // The password must contain at least one upper case character
        return password.matches(".*[A-Z].*");
    }

    @Override
    protected String getSpecificPolicyMsg() {
        return "* The password must contain at least one upper case character.\n";
    }
}
