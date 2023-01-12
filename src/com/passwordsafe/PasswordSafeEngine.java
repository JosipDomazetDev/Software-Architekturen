package com.passwordsafe;;
import com.passwordsafe.datasourcelayer.IDataSourceLayer;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class PasswordSafeEngine {
    private final CipherFacility cipherFaciility;
    private final IDataSourceLayer dataLayer;
    public PasswordSafeEngine(
            IDataSourceLayer dataLayer, CipherFacility cipherFacility) {
        this.cipherFaciility = cipherFacility;
        this.dataLayer = dataLayer;
    }

    public String[] GetStoredPasswords() throws Exception {
        return dataLayer.getStoredPasswords();
    }
    public void AddNewPassword(PasswordInfo info) throws IOException, Exception {
        this.dataLayer.addNewPassword(
                info.getName(),
                this.cipherFaciility.Encrypt(info.getPlain()));
    }
    public void DeletePassword(String passwordName) throws Exception, IOException {
        this.dataLayer.deletePassword(passwordName, this);
    }
    public String GetPassword(String passwordName) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        char[] buffer = this.dataLayer.getPasswordCipher(passwordName, this);
        return this.cipherFaciility.Decrypt(new String(buffer));
    }
}
