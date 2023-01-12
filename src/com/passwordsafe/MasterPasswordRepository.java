package com.passwordsafe;

import com.passwordsafe.datasourcelayer.IDataSourceLayer;

import java.util.InputMismatchException;

public class MasterPasswordRepository {
    private final IDataSourceLayer dataLayer;

    public MasterPasswordRepository(IDataSourceLayer dataLayer) {
        this.dataLayer = dataLayer;
    }
    public void setMasterPasswordPlain(String masterPassword) throws Exception {
        this.StoreMasterPasswordToFile(masterPassword);
    }

    public void resetMasterPasswordPlain(String masterPassword, String masterPasswordRepeated) throws Exception {
        if(!masterPasswordRepeated.equals(masterPassword)){
            throw new InputMismatchException("Passwords do not match!");
        }

        this.setMasterPasswordPlain(masterPassword);
    }

    public boolean MasterPasswordIsEqualTo(String masterPassword) throws Exception {
        return masterPassword.equals(this.GetMasterPasswordFromFile());
    }
    private String GetMasterPasswordFromFile() throws Exception {
        return dataLayer.getMasterPassword();
    }
    private void StoreMasterPasswordToFile(String masterPassword) throws Exception {
        dataLayer.storeMasterPassword(masterPassword);
    }

    public void purgeAllPasswords() {
        dataLayer.purgeAllPasswords();
    }
}
