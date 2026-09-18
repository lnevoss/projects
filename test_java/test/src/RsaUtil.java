package com.adtec.outconnplat.busi.swjbusi;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;



/**
 * @Author: chenlei
 * @Date: 2020/6/22 10:52
 * @Description: TODO RSA加解密算法工具
 * @Version: V1.0.0
 */
public class RsaUtil {

    private static final String encoding = "UTF-8";
    private static final String AES_CBC_PKC_ALG = "AES/CBC/PKCS5Padding";
    private static final byte[] AES_IV = initIV(AES_CBC_PKC_ALG);

    /**
     * 算法
     */
    private static final String KEY_ALGORITHM = "RSA";

    /**
     * 特定的key算法
     */
    private static final String SPECIFIC_KEY_ALGORITHM = "RSA/ECB/PKCS1Padding";

    /**
     * 签名算法
     */
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    /**
     * 默认的key大小
     */
    private static final int DEFAULTKEYSIZE = 2048;

    /**
     * 加密块大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;


    /**
     * 初始化向�?
     * @param aesCbcPkcAlg
     * @return
     */
    private static byte[] initIV(String aesCbcPkcAlg) {
        Cipher cp;
        try {
            cp = Cipher.getInstance(aesCbcPkcAlg);
            int blockSize = cp.getBlockSize();
            byte[] iv = new byte[blockSize];
            for(int i = 0; i < blockSize; ++i){
                iv[i] = 0;
            }
            return iv;

        } catch (Exception e) {
            int blockSize = 16;
            byte[] iv = new byte[blockSize];
            for(int i = 0; i < blockSize; ++i){
                iv[i] = 0;
            }
            return iv;
        }
    }


    /**
     * 随机�?
     * @return
     */
    public static String getRandom(){
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            sb.append("1234567890qwertyuiopasdfghjklzxcvbnm".charAt(random
                    .nextInt("1234567890qwertyuiopasdfghjklzxcvbnm".length())));
        }
        return sb.toString();
    }

    /**
     * aes解密
     *
     * @param content
     *            待解密内�?
     * @param password
     *            解密密钥
     * @return
     */
    public static String decrypt4Base64(String content, String password) throws Exception {
        byte[] byteContent = Base64.decodeBase64(content);
        byte[] enCodeFormat = password.getBytes(encoding);
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance(AES_CBC_PKC_ALG);// 创建密码�?
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(AES_IV));// 初始�?
        byte[] result = cipher.doFinal(byteContent);
        return new String(result,encoding); // 加密
    }

    /**
     * aes加密
     *
     * @param content
     *            �?��加密的内�?
     * @param password
     *            加密密码
     * @return
     */
    public static String encrypt4Base64(String content, String password) throws Exception {
        byte[] bytePwd = password.getBytes(encoding);
        SecretKeySpec key = new SecretKeySpec(bytePwd, "AES");
        Cipher cipher = Cipher.getInstance(AES_CBC_PKC_ALG);// 创建密码�?
        byte[] byteContent = content.getBytes(encoding);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(AES_IV));// 初始�?
        byte[] result = cipher.doFinal(byteContent);
        return new String(Base64.encodeBase64(result),encoding); // 加密
    }


    /**
     * base64加密
     * @param binaryData
     * @return
     */
    public static String encodeBase64(byte[] binaryData){
        return new String(Base64.encodeBase64(binaryData));
    }

    /**
     * base64解密
     * @param str
     * @return
     */
    public static byte[] decodeBase64(String str){
        return Base64.decodeBase64(str);
    }


    /**
     * 生成公私钥对
     * @param randomStr 用于产生随机数的字符
     * @return
     * @throws Exception
     */
    public static Map<String,Object> genKeyPair(boolean flag, String... randomStr) throws Exception{

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);

        if(flag){
            SecureRandom ranDom = new SecureRandom(randomStr[0].getBytes());
            keyPairGenerator.initialize(DEFAULTKEYSIZE, ranDom);
        }else{
            keyPairGenerator.initialize(DEFAULTKEYSIZE);
        }

        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        Map<String, Object> keyPairMap = new HashMap<String,Object>(4);
        keyPairMap.put("privatekey", privateKey);
        keyPairMap.put("publickey", publicKey);

        return keyPairMap;
    }

    /**
     * 使用私钥对数据进行签名
     * @param data
     * @param privateKeyStr
     * @param charsetName
     * @return
     * @throws Exception
     */
    public static String signByPrivateKey(String data, String privateKeyStr,String charsetName) throws Exception{

        byte[] privateKeyByte = decodeBase64(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyByte);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data.getBytes(charsetName));

        return encodeBase64(signature.sign());
    }

    /**
     * 读取文件内容
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String getFileTxt(String filePath) throws Exception{
        StringBuffer sb = new StringBuffer();
        InputStreamReader read = null;
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                read = new InputStreamReader(new FileInputStream(file), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    sb.append(lineTxt);
                }
            }
        } finally {
            if(read!=null){
                read.close();
            }
        }
        return sb.toString();
    }

    /**
     * 使用公钥进行校验
     * @param data
     * @param publicKeyStr
     * @param sign
     * @param charsetName
     * @return
     * @throws Exception
     */
    public static boolean verifyByPublicKey(String data, String publicKeyStr, String sign,String charsetName) throws Exception{
        byte[] publicKeyByte = decodeBase64(publicKeyStr);
        X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(publicKeyByte);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(encodedKeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data.getBytes(charsetName));
        return signature.verify(decodeBase64(sign));
    }

    /**
     * 根据银行ID读取公钥文件并解密
     * @param filePath
     * @param data
     * @param sign
     * @param charsetName
     * @return
     * @throws Exception
     */
    public static boolean verifyByCert(String filePath,String data,String sign,String charsetName) throws Exception{

        if(filePath == null || "".equals(filePath)){
            return false;
        }

        String publicKeyStr = getFileTxt(filePath);

        return verifyByPublicKey(data, publicKeyStr, sign, charsetName);

    }

    /**
     * 公钥加密
     * @param data
     * @param publicKeyStr
     * @return
     * @throws Exception
     */
    public static String encryptByCrtPublicKey(String data,String publicKeyStr) throws Exception {
        if(null == data) {
            return null;
        }
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] publicKeyByte = Base64.decodeBase64(publicKeyStr);

        X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(publicKeyByte);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(encodedKeySpec);

        Cipher cipher = Cipher.getInstance(SPECIFIC_KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        byte[] encryptedData = null;
        try {
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(dataBytes, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(dataBytes, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            encryptedData = out.toByteArray();
        } finally {
            out.close();
        }
        return new String(Base64.encodeBase64(encryptedData));

    }

    /**
     * 私钥解密
     * @param data
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data, String privateKeyStr)
            throws Exception {
        if(null == data) {
            return null;
        }
        byte[] dataB = decodeBase64(data);
        byte[] privateKeyByte = decodeBase64(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyByte);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

        Cipher cipher = Cipher.getInstance(SPECIFIC_KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        int key_len = privateKey.getModulus().bitLength() / 8;
        byte[] decryptedData = null;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            int dataLength = dataB.length;
            for (int i = 0; i < dataLength; i += key_len) {
                int decryptLength = dataLength - i < key_len ? dataLength - i
                        : key_len;
                byte[] doFinal = cipher.doFinal(dataB, i, decryptLength);
                bout.write(doFinal);
            }
            decryptedData = bout.toByteArray();
        } finally {
            bout.close();
        }
        return new String(decryptedData,StandardCharsets.UTF_8);
    }

    /**
     * 生成秘钥对
     * @return
     * @throws Exception
     */
    public static Map<String,String> genKeys() throws Exception{
        Map<String,Object> keys = RsaUtil.genKeyPair(false, "");

        String publicKey = new String(Base64.encodeBase64(((PublicKey)keys.get("publickey")).getEncoded()));
        String privateKey = new String(Base64.encodeBase64(((PrivateKey)keys.get("privatekey")).getEncoded()));

        Map<String,String> keymap = new HashMap<String,String>(4);
        keymap.put("public",publicKey);
        keymap.put("private", privateKey);

        return keymap;
    }
    
}
