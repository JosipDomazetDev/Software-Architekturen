package com.passwordsafe.policydecorator.factory;

import com.passwordsafe.policydecorator.*;

public class WeakPasswordPolicyTesterFactory implements PasswordPolicyTesterFactory {
    @Override
    public IPasswordPolicyTester createPasswordPolicyTester() {
        IPasswordPolicyTester passwordPolicyTester =
                new BasicPasswordPolicyTester();

        passwordPolicyTester =
                new UpperCasePasswordPolicyTester(passwordPolicyTester);
        passwordPolicyTester =
                new LengthPasswordPolicyTester(passwordPolicyTester);

        return passwordPolicyTester;
    }
}
