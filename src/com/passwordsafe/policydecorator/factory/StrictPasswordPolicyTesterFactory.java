package com.passwordsafe.policydecorator.factory;

import com.passwordsafe.policydecorator.*;

public class StrictPasswordPolicyTesterFactory implements PasswordPolicyTesterFactory {
    @Override
    public IPasswordPolicyTester createPasswordPolicyTester() {
        IPasswordPolicyTester passwordPolicyTester =
                new BasicPasswordPolicyTester();

        passwordPolicyTester =
                new UpperCasePasswordPolicyTester(passwordPolicyTester);
        passwordPolicyTester =
                new SpecialCharPasswordPolicyTester(passwordPolicyTester);
        passwordPolicyTester =
                new LengthPasswordPolicyTester(passwordPolicyTester);
        passwordPolicyTester =
                new NumberPasswordPolicyTester(passwordPolicyTester);

        return passwordPolicyTester;
    }
}