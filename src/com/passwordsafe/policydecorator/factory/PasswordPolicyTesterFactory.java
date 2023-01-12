package com.passwordsafe.policydecorator.factory;

import com.passwordsafe.policydecorator.IPasswordPolicyTester;

public interface PasswordPolicyTesterFactory {
    // Going with a FactoryMethod would have probably be fine as well but because the information in the PDF was not really enough
    // I decided to go for an Abstract Factory pattern because it is more flexible
    IPasswordPolicyTester createPasswordPolicyTester();
}
