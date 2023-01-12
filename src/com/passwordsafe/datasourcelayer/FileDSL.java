package com.passwordsafe.datasourcelayer;

import com.passwordsafe.PasswordInfo;
import com.passwordsafe.PasswordSafeEngine;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

// File Data Source Layer
// How to extend e.g.: for a Database connection implement this interface in a new class and use that instead.
public class FileDSL implements IDataSourceLayer {
    private final String path;
    private final String masterPath;

    public FileDSL(String path, String masterPath) {
        this.path = path;
        this.masterPath = masterPath;
    }

    @Override
    public String[] getStoredPasswords() throws Exception {
        File directory = new File(path);
        if (!directory.isDirectory() && !directory.mkdir()) {
            throw new Exception("Unable to create directory");
        }
        List<File> files = Arrays.asList(Objects.requireNonNull(directory.listFiles()));
        return files.stream()
                .filter(s -> s.getName().endsWith(".pw"))
                .map(f -> f.getName().split("\\.")[0]).toList().toArray(new String[0]);
    }

    @Override
    public void addNewPassword(String passwordName, String cypher) throws Exception {
        File directory = new File(path);
        if (!directory.isDirectory() && !directory.mkdir()) {
            throw new Exception("Unable to create directory");
        }
        File storage = (this.getFileFromName(passwordName));
        if (storage.createNewFile()) {
            this.writeToFile(storage.getPath(), cypher);
        } else {
            throw new Exception("Password with the same name already existing. Please choose another name of update the existing one.");
        }
    }

    @Override
    public void deletePassword(String passwordName, PasswordSafeEngine passwordSafeEngine) throws Exception {
        File storage = this.getFileFromName(passwordName);
        if (!storage.delete()) {
            throw new Exception("Unable to delete password setting under " + storage.getName());
        }
    }

    @Override
    public char[] getPasswordCipher(String passwordName, PasswordSafeEngine passwordSafeEngine) throws IOException {
        File passwordFile = this.getFileFromName(passwordName);
        char[] buffer = null;
        if (passwordFile.exists()) {
            FileReader reader = null;
            try {
                buffer = new char[(int) passwordFile.length()];
                reader = new FileReader(passwordFile);
                reader.read(buffer);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ex) {
                    }
                }
                ;
            }
        }
        return buffer;
    }


    @Override
    public void updatePassword(PasswordInfo info) throws Exception {
        File storage = this.getFileFromName(info.getName());
        if (storage.exists()) {
            this.writeToFile(storage.getPath(), info.getPlain());
        } else {
            throw new Exception("Password with the same name not existing.");
        }
    }

    private void writeToFile(String filename, String crypted) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        FileWriter writer = null;
        try {
            writer = new FileWriter(filename);
            writer.write(crypted);
        } finally {
            if (writer != null) try {
                writer.close();
            } catch (IOException ignore) {
            }
        }
    }

    private File getFileFromName(String name) {
        return new File(path + "/" + name + ".pw");
    }


    // Master-Password Logic
    @Override
    public String getMasterPassword() throws Exception {
        File passwordFile = new File(this.masterPath);
        char[] buffer = null;
        if (passwordFile.exists()) {
            FileReader reader = null;
            try {
                buffer = new char[(int) passwordFile.length()];
                reader = new FileReader(passwordFile);
                reader.read(buffer);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ex) {
                    }
                }
                ;
            }
        }
        return buffer == null ? null : new String(buffer);
    }

    @Override
    public void storeMasterPassword(String masterPassword) throws Exception {
        FileWriter writer = null;
        try {
            writer = new FileWriter(this.masterPath);
            writer.write(masterPassword);
        } finally {
            if (writer != null) try {
                writer.close();
            } catch (IOException ignore) {
            }
        }
    }

    @Override
    public void purgeAllPasswords() {
        // urgent hotfix delete old passwords after changing the master
        File oldPasswords = new File(path);
        if (oldPasswords.isDirectory()) {
            String[] children = oldPasswords.list();
            for (int i = 0; i < children.length; i++) {
                (new File(oldPasswords, children[i])).delete();
            }
        }
        // The directory is now empty or this is a file so delete it
        oldPasswords.delete();
    }
}
