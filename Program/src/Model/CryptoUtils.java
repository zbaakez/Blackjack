package Model;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * CryptoUtils class that encrypts or decrypts a file with AES algorithm
 * @author Busted
 */
public class CryptoUtils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    /**
     * Method that encrypts a file
     * @param key as String, which is used to encrypt the file
     * @param inputFile as File, needs to be an existing file that will be encrypted
     * @param outputFile as File, will be the encrypted output File
     * @throws CryptoException
     */
    public static void encrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }

    /**
     * Method that decrypts a file
     * @param key as String, which is used to encrypt the file
     * @param inputFile as File, needs to be an existing encrypted file, that will be decrypted
     * @param outputFile as File, will be the decrypted output File
     * @throws CryptoException
     */
    public static void decrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }

    /**
     * Method that handles the encrypting and decrypting, is called by decrypt and encrypt Method
     * @param cipherMode
     * @param key
     * @param inputFile
     * @param outputFile
     * @throws CryptoException
     */
    private static void doCrypto(int cipherMode, String key, File inputFile,
                                 File outputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }
}