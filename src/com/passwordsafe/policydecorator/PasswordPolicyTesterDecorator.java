package com.passwordsafe.policydecorator;

public abstract class PasswordPolicyTesterDecorator implements IPasswordPolicyTester {
    private final IPasswordPolicyTester passwordPolicyTester;

    public PasswordPolicyTesterDecorator(IPasswordPolicyTester passwordPolicyTester) {
        this.passwordPolicyTester = passwordPolicyTester;
    }

    @Override
    public boolean fulfilsPolicy(String password) {
        return fulfilsSpecificPolicy(password) &&
                passwordPolicyTester.fulfilsPolicy(password);
    }


    @Override
    public String getPolicyMsg() {
        return passwordPolicyTester.getPolicyMsg() + getSpecificPolicyMsg();
    }

    protected abstract boolean fulfilsSpecificPolicy(String password);

    protected abstract String getSpecificPolicyMsg();
}
