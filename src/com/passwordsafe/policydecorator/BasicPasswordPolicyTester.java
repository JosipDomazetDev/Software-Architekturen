package com.passwordsafe.policydecorator;

public class BasicPasswordPolicyTester implements IPasswordPolicyTester {
    @Override
    public boolean fulfilsPolicy(String password) {
        // Add some base level policies here
        // Ich hab alle Regeln in eigenen decoratorn implementiert ("each rule is implemented by a separate decorator.")
        // Daher returnt diese Methode nur true.
        return true;
    }

    @Override
    public String getPolicyMsg() {
        // Add some base level policy msg here
        return "";
    }
}
