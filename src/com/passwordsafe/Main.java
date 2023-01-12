package com.passwordsafe;

import com.passwordsafe.datasourcelayer.FileDSL;
import com.passwordsafe.datasourcelayer.IDataSourceLayer;
import com.passwordsafe.logging.ConsoleLoggerFactory;
import com.passwordsafe.logging.Logger;
import com.passwordsafe.logging.LoggerFactory;
import com.passwordsafe.policydecorator.IPasswordPolicyTester;
import com.passwordsafe.policydecorator.factory.PasswordPolicyTesterFactory;
import com.passwordsafe.policydecorator.factory.StrictPasswordPolicyTesterFactory;
import com.passwordsafe.policydecorator.factory.WeakPasswordPolicyTesterFactory;
import com.passwordsafe.subscriber.EventManager;
import com.passwordsafe.subscriber.ResetPasswordSubscriber;
import com.passwordsafe.subscriber.WrongPasswordSubscriber;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static PasswordSafeEngine passwordSafeEngine = null;
    static Logger logger;

    private static Logger createLogger() {
        // Use another factory if you want to add functionality i.e. file logging
        LoggerFactory loggerFactory = new ConsoleLoggerFactory();
        return loggerFactory.createLogger();
    }

    public static EventManager eventManager = new EventManager("wrongPassword", "resetPassword");

    static {
        logger = createLogger();
        eventManager.subscribe("wrongPassword", new WrongPasswordSubscriber());
        eventManager.subscribe("resetPassword", new ResetPasswordSubscriber());
    }

    public static void main(String[] args) throws Exception {
        logger.logInfo("Welcome to Passwordsafe");

        boolean abort = false;
        boolean locked = true;
        Scanner read = new Scanner(System.in);
        // Easily use another implementation of IDataSourceLayer instead, for e.g. databases
        IDataSourceLayer dataLayer = new FileDSL("./passwords.pw", "./master.pw");
        MasterPasswordRepository masterRepository = new MasterPasswordRepository(dataLayer);

        boolean isWeakMode = readIsWeakMode(read);

        while (!abort) {
            logger.logInfo("Enter master (1), show all (2), show single (3), add (4), delete(5), set new master (6), Abort (0)");
            int input = read.nextInt();
            switch (input) {
                case 0: {
                    abort = true;
                    break;
                }
                case 1: {
                    logger.logInfo("Enter master password");
                    String masterPw = read.next();
                    locked = !masterRepository.MasterPasswordIsEqualTo(masterPw);
                    if (!locked) {
                        passwordSafeEngine = new PasswordSafeEngine(dataLayer, new CipherFacility(masterPw));
                        logger.logInfo("unlocked");
                    } else {
                        eventManager.notify("wrongPassword", logger);
                    }
                    break;
                }
                case 2: {
                    if (locked) {
                        logger.logInfo("Please unlock first by entering the master password.");
                    } else {
                        Arrays.stream(passwordSafeEngine.GetStoredPasswords()).forEach(pw -> logger.logInfo(pw));
                    }
                    break;
                }
                case 3: {
                    if (locked) {
                        logger.logInfo("Please unlock first by entering the master password.");
                    } else {
                        logger.logInfo("Enter password name");
                        String passwordName = read.next();
                        logger.logInfo(passwordSafeEngine.GetPassword(passwordName));
                    }
                    break;
                }
                case 4: {
                    if (locked) {
                        logger.logInfo("Please unlock first by entering the master password.");
                    } else {
                        logger.logInfo("Enter new name of password");
                        String passwordName = read.next();
                        logger.logInfo("Enter password");
                        String password = read.next();
                        passwordSafeEngine.AddNewPassword(new PasswordInfo(password, passwordName));
                    }
                    break;
                }
                case 5: {
                    if (locked) {
                        logger.logInfo("Please unlock first by entering the master password.");
                    } else {
                        logger.logInfo("Enter password name");
                        String passwordName = read.next();
                        passwordSafeEngine.DeletePassword(passwordName);
                    }
                    break;
                }
                case 6: {
                    locked = true;
                    passwordSafeEngine = null;
                    IPasswordPolicyTester passwordPolicyTester = getPasswordPolicyTester(isWeakMode);

                    logger.logInfo("Enter new master password ! (Warning you will loose all already stored passwords)");
                    String masterPassword = read.next();

                    if (!passwordPolicyTester.fulfilsPolicy(masterPassword)) {
                        logger.logInfo("New password does not match requirements:");
                        logger.logInfo(passwordPolicyTester.getPolicyMsg());
                        logger.logInfo("Please try another one!");
                        break;
                    }

                    logger.logInfo("Please re-enter the password!");
                    String masterPasswordRepeated = read.next();

                    try {
                        // Separate method contains the check
                        masterRepository.resetMasterPasswordPlain(masterPassword, masterPasswordRepeated);
                        eventManager.notify("resetPassword", logger);

                    } catch (InputMismatchException e) {
                        logger.logError(e.getMessage());
                        logger.logError("Operation aborted.");
                        break;
                    }


                    masterRepository.purgeAllPasswords();
                    break;
                }
                default:
                    logger.logInfo("Invalid input");
            }
        }

        logger.logInfo("Good by !");
    }

    private static boolean readIsWeakMode(Scanner read) {
        String STRICT = "strict";
        String WEAK = "weak";
        logger.logInfo(String.format("Which password policy mode should be used? (%s/%s)", STRICT, WEAK));

        String inputWeakMode = read.next();
        boolean validInput = inputWeakMode.equalsIgnoreCase(STRICT) || inputWeakMode.equalsIgnoreCase(WEAK);

        while (!validInput) {
            logger.logInfo(String.format("Invalid input. Please enter %s or %s.", STRICT, WEAK));
            inputWeakMode = read.next();
            validInput = inputWeakMode.equalsIgnoreCase(STRICT) || inputWeakMode.equalsIgnoreCase(WEAK);
        }

        return inputWeakMode.equalsIgnoreCase(WEAK);
    }

    private static IPasswordPolicyTester getPasswordPolicyTester(boolean isWeakMode) {
        PasswordPolicyTesterFactory passwordPolicyTesterFactory;
        if (isWeakMode) {
            passwordPolicyTesterFactory = new WeakPasswordPolicyTesterFactory();
        } else {
            passwordPolicyTesterFactory = new StrictPasswordPolicyTesterFactory();
        }

        return passwordPolicyTesterFactory.createPasswordPolicyTester();
    }
}
