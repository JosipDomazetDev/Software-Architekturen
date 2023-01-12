package com.passwordsafe.datasourcelayer;

import com.passwordsafe.PasswordInfo;
import com.passwordsafe.PasswordSafeEngine;

import java.io.IOException;

public interface IDataSourceLayer {
    String[] getStoredPasswords() throws Exception;

    char[] getPasswordCipher(String passwordName, PasswordSafeEngine passwordSafeEngine) throws IOException;

    void addNewPassword(String passwordName, String cypher) throws Exception;

    void deletePassword(String passwordName, PasswordSafeEngine passwordSafeEngine) throws Exception;

    void updatePassword(PasswordInfo info) throws Exception;

    // master pw methods
    String getMasterPassword() throws Exception;

    void storeMasterPassword(String masterPassword) throws Exception;

    void purgeAllPasswords();
}
