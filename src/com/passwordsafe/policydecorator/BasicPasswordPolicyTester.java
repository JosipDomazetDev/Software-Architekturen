package com.passwordsafe.policydecorator;

public class BasicPasswordPolicyTester implements IPasswordPolicyTester {
    @Override
    public boolean fulfilsPolicy(String password) {
        // Nach Email-Rücksprache habe ich entschieden R1 (UpperCase-Regel) als BasicPasswordPolicyTester zu implementieren,
        // damit der BasicTester auch eine Logik enthält. (R1 ist sowieso immer verpflichtend zu erfüllen)

        // The password must contain at least one upper case character
        return password.matches(".*[A-Z].*");
    }

    @Override
    public String getPolicyMsg() {
        return "* The password must contain at least one upper case character.\n";
    }
}
