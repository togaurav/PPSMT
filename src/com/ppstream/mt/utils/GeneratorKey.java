package com.ppstream.mt.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class GeneratorKey {
	
	/**
	 * 产生公钥私钥对
	 * 
	 * @return
	 */
	@SuppressWarnings("finally")
	@Deprecated
	public Map<String, String> createKey() {
		Map<String, String> map = new HashMap<String, String>();
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			SecureRandom ran1 = new SecureRandom();
			// 创建‘密匙对’生成器
			kpg.initialize(1024, ran1);
			KeyPair kp = kpg.genKeyPair();
			// 获得公匙和私匙
			PublicKey public_key = kp.getPublic();
			PrivateKey private_key = kp.getPrivate();
			// 可以保存在数据库当中
			String pubKey = Codec.encodeBASE64(public_key.getEncoded());
			String priKey = Codec.encodeBASE64(private_key.getEncoded());
			map.put("pubKey", pubKey);
			map.put("priKey", priKey);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return map;
		}
	}

	/**
	 * 使用公钥加密字符串
	 * @param pubKey
	 * @param verifyCode
	 * @return  加密后
	 */
	@SuppressWarnings("finally")
	@Deprecated
	public static String createCipherText(String pubKey, String verifyCode) {
		String cipherTextStr = null;
		try {
			byte[] plainText = verifyCode.getBytes("UTF8");

			// 重新生成公钥
			KeyFactory factory;
			factory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpecPub = new X509EncodedKeySpec(
					Codec.decodeBASE64(pubKey));
			PublicKey newPublicKey;
			newPublicKey = factory.generatePublic(keySpecPub);
			// 使用公鈅加密
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, newPublicKey);
			byte[] cipherText = cipher.doFinal(plainText);
			cipherTextStr = Codec.encodeBASE64(cipherText);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return cipherTextStr;
		}
	}

}
