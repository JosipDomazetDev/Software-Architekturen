package com.passwordsafe.policydecorator;

public class SpecialCharPasswordPolicyTester extends PasswordPolicyTesterDecorator {
    public SpecialCharPasswordPolicyTester(IPasswordPolicyTester passwordPolicyTester) {
        super(passwordPolicyTester);
    }

    @Override
    protected boolean fulfilsSpecificPolicy(String password) {
        // The password must contain at least one special character out of following set {‘*’, ’#’, ’!’, ’?’, ’%’, ’$’, ’@’}.
        String specialChars = "[*#!?%$@]";

        return password.matches(".*[" + specialChars + "].*");
    }

    @Override
    protected String getSpecificPolicyMsg() {
        return "* The password must contain at least one special character out of following set {‘*’, ’#’, ’!’, ’?’, ’%’, ’$’, ’@’}.\n";
    }
}
