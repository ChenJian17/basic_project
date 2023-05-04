package com.hxh.basic.project.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crc.openapi.sdk.client.SysClient;
import com.crc.openapi.sdk.entity.ApiCommonParameter;
import com.crc.openapi.sdk.entity.Result;
import com.crc.openapi.sdk.util.DateUtil;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.crypto.Cipher;

import static com.crc.openapi.sdk.common.CommonEnum.ChainOrderEnum.SIGN_ONLY;
import static com.crc.openapi.sdk.common.CommonEnum.GatewayTypeEnum.SOA;
import static com.crc.openapi.sdk.common.CommonEnum.HttpMethodEnum.POST;
import static com.crc.openapi.sdk.common.CommonEnum.ServerTypeEnum.RESTFUL;
import static com.crc.openapi.sdk.common.CommonEnum.SignMethodEnum.NO_SIGN;

public class RSA extends Coder{
	
	public static final String KEY_ALGORTHM = "RSA";
	public static String PUBLIC_KEY = "RSAPublicKey";
	public static String PRIVATE_KEY = "RSAPrivateKey";
	
	public static String PUBLIC_KEY_ENC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCexy/ucx2Z6FmK14NsTmIvVpIXjXwlgTJLlNlN"+
                                          "ACdnbMOGVkz4wC7wd9QeY/Nf+RwElNfF4Xmk82zwgq0FPjoZPMOgrlnmSuBTd8o8mfGOG0A8UBdM"+
                                          "oeV9ZXyI8UxE//Zus1zsQwaGc1RTo4v2s4f8QcZm+JOpcUtoyvjBXqD7TQIDAQAB";

    public static String PRIVATE_KEY_ENC = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAJ7HL+5zHZnoWYrXg2xOYi9WkheN"+
                                           "fCWBMkuU2U0AJ2dsw4ZWTPjALvB31B5j81/5HASU18XheaTzbPCCrQU+Ohk8w6CuWeZK4FN3yjyZ"+
                                           "8Y4bQDxQF0yh5X1lfIjxTET/9m6zXOxDBoZzVFOji/azh/xBxmb4k6lxS2jK+MFeoPtNAgMBAAEC"+
                                           "gYEAkEwl22by9/SZq/zAi6WQxcS/cHg84zjPJLFZ6wjAxtWpvGSh/09pWVENjbD4dbhHJkK+9i88"+
                                           "BhTUZJ/d7m3nDaciLR998nS/vUsFUOamV20ikrdRms4td+zNhpE+wILprNKflAaOX63hHqeiGtFl"+
                                           "ufq6CcZKND7y02hueyBBXQECQQDlDh0VxgAyPngzFPdqfymuZNp3rGAH5qX01pRcsd+gnPYj3ljl"+
                                           "7miPqYMCP5HiMFYj107AGtwik+73h070s7MJAkEAsXS2prs6OdbV9g6n2ssDEKBgmXBzfyxx49D1"+
                                           "CGP9lwKdRAijotFTGYu+0K2o65xNXbOoOcLtG+7t91VfgdcDJQJBAIWCzIE8gDDJKNbdibmRlYIJ"+
                                           "Vy4aU39HecRUrvW42FiOJQqTN84OQPQ8IhjVZPsqoYsSnNxsy5N8FvZA8EzB0cECQFP8QgnNKLSB"+
                                           "Ggh3TTf+xveSf8V+9BqnRUul9KJA+J8EzLuLPy7q1OKNQgTve7fu5KrJQX/84k1LGnLpg+cmhMUC"+
                                           "QQCMeWAMjlzYuya/x2hCpJdFuP6wSQ6JJFh2QiWLnrTx17KmysfB88CNyXksWI+s2iVHB7hAR5nX"+
                                           "P+YrxlFRZmV2";	
	
    public static final String split = " ";//分隔符
    /**  
     * RSA最大加密明文大小 
     */  
    private static final int MAX_ENCRYPT_BLOCK = 117;  
      
    /** 
     * RSA最大解密密文大小 
     */  
    private static final int MAX_DECRYPT_BLOCK = 128; 
   
	/**
	 * 根据指定的公匙和私匙key，生成密匙对
	 * @author zhaofubin5
	 * @param Map<String, Object>
	 * */

	public static Map<String,Object> initKey()throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORTHM);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //私钥
        RSAPrivateKey privateKey =  (RSAPrivateKey) keyPair.getPrivate();
        Map<String,Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        
        return keyMap;
    }
	
	/**
     * 取得公钥，并转化为String类型
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)throws Exception{
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return encryptBASE64(key.getEncoded());     
    }

    /**
     * 取得私钥，并转化为String类型
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception{
        Key key = (Key) keyMap.get(PRIVATE_KEY);  
        return encryptBASE64(key.getEncoded());     
    }

	
    /**
     * 用私钥加密
     * @param data  加密数据
     * @param key   密钥
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data,String key)throws Exception{
        //解密密钥
        byte[] keyBytes = decryptBASE64(key);
        //取私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
         
        //对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
         
        return cipher.doFinal(data);
    }

    /**
     * 用私钥解密<span style="color: rgb(0, 0, 0);"></span> * @param data    加密数据
     * @param key   密钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data,String key)throws Exception{
        //对私钥解密
        byte[] keyBytes = decryptBASE64(key);
        
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        int inputLen = data.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段解密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_DECRYPT_BLOCK;  
        }  
        byte[] decryptedData = out.toByteArray();  
        out.close();  
        return decryptedData;  
    }


    /**
     * 用公钥加密
     * @param data  加密数据
     * @param key   密钥
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data,String key)throws Exception{
        //对公钥解密
        byte[] keyBytes = decryptBASE64(key);
        //取公钥
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
         
        //对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        
        int inputLen = data.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段加密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_ENCRYPT_BLOCK;  
        }  
        byte[] encryptedData = out.toByteArray();  
        out.close();  
        return encryptedData; 
    }

    /**
     * 用公钥解密
     * @param data  加密数据
     * @param key   密钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data,String key)throws Exception{
        //对私钥解密
        byte[] keyBytes = decryptBASE64(key);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
         
        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
         
        return cipher.doFinal(data);
    }

    public static ApiCommonParameter initPostParameters(String json) {

        ApiCommonParameter parameter = new ApiCommonParameter();
        //----------------设置ECSB公共请求参数部分------------------------------//
        parameter.setApiID("crinfo.ldap.LDAP.MSCTVerifyPhone");
        parameter.setApiVersion("1");
        //----------以下部分参数调用app网关需要------------------//

        //----------以上部分参数调用app网关需要------------------//
        parameter.setSignSecret("81509604d3cb4b1d9526d539fee8078e");//签名密钥，取自ECSB调用方的【应用管理】-【基本信息】下的【报文签名密钥】
        parameter.setAppSubId("000600060105");//调用方应用编码，取自ECSB调用方的【应用管理】-【基本信息】下的【应用编码】
        parameter.setSysID("00060006");//调用方系统编码，取自ECSB调用方的【应用管理】-【基本信息】下的【系统编码】
        parameter.setPartnerID("00060000");//调用方组织编码，取自ECSB调用方的【应用管理】-【基本信息】下的【组织编码】
        parameter.setAppToken("852c74a99b3c43ffbc07eae05cdf2864");//调用方组织编码，取自ECSB调用方的【应用管理】-【基本信息】下的【应用授权令牌】

        parameter.setUserToken("");
        parameter.setSignMethod(NO_SIGN);//签名方式，根据所调用的服务所配置的值来确定：NO_SIGN表示不签名，NO_SIGN, MD5, RSA
        // 如果api是RSA加密，并设置私钥
        parameter.setTimeStamp(DateUtil.nowDateFormatDefault());//时间戳参数，该参数校验规则为不超过当前时间前后十分钟，时间格式为：2020-07-07 10:22:48:727
        //----------------设置ECSB公共请求参数部分结束------------------------------//
        // 设置业务请求参数
//		parameter.setRequestDate("{\"App_key\": \"34213004addd4004bc33060326da0c50\"}");
        parameter.setRequestDate(json);

        return parameter;

    }

    public static void testPost(String json) throws Exception {
        Properties props = new Properties();

        String url = "http://ecsb.crcloud.com/ecsb/gw";//ecsb服务调用地址前面部分（真实地址请根据实际情况配置）
        props.setProperty("http.ip", url);
        props.setProperty("gateway", SOA.name());//网关类型设置：soa表示集成网关，sys表示系统网关，cls表示集成网关，app表示移动网关
        props.setProperty("model", RESTFUL.name());//服务类型，根据所调用服务发布的服务类型所确定
        props.setProperty("method", POST.name());//请求方式：post，get等
        props.setProperty("order", SIGN_ONLY.name());//加密方式：SIGN_ONLY表示只签名不加密，具体的sdk里面有说明

        ApiCommonParameter parameter = initPostParameters(json);
        System.out.println(parameter);
        SysClient client = new SysClient(props);
        Result result = client.post(parameter);
        System.out.println("result: "+result);



    }
   
	public static void main(String args []){
		try {
			Map<String,Object> keyMap = RSA.initKey();
			String data = "Admin1122";
			byte [] encByte = RSA.encryptByPublicKey(data.getBytes(), PUBLIC_KEY_ENC);
			String enc = encryptBASE64(encByte);
			System.out.println("加密后：" + enc.replaceAll("(\\\r\\\n|\\\r|\\\n|\\\n\\\r)", ""));

//			byte [] dncByte = RSA.decryptByPrivateKey(decryptBASE64(enc), PRIVATE_KEY_ENC);
//			System.out.println("解密后:" + new String(dncByte));

//            Map<String, String> map = new HashMap();
//            map.put("uid", "CRCLOUDMOBILE");
//            map.put("password", enc.replaceAll("(\\\r\\\n|\\\r|\\\n|\\\n\\\r)", ""));
//            map.put("verifyuid", "chenjian383");
//            map.put("verifyphone", "13416141363");
//            Map<String,  Map<String, String>> param = new HashMap<>();
//            param.put("REQUEST_DATA", map);
//            Map<String, Map<String,  Map<String, String>>> params = new HashMap<>();
//            params.put("REQUEST", param);
//            String body = JSONObject.toJSONString(params);
//            String template = "{{first.DATA}}\n" +
//                    "来电号码：{{keyword1.DATA}}\n" +
//                    "来电时间：{{keyword2.DATA}}\n" +
//                    "{{remark.DATA}}";
//            String s = "{{first.DATA}} ↵↵来电号码：{{keyword1.DATA}}↵来电时间：{{keyword2.DATA}}↵ {{remark.DATA}}";
////            String s = JSON.toJSONString(body);
//            testPost(body);
            String s = "{\n" +
                    "                    \"1a0018970a9bea3e776\": {//查询的 registrationID\n" +
                    "                \"uid\": \"12734447051\",//对应的UID\n" +
                    "                        \"online\": true,//最近 10 分钟在线状态,true：10 分钟内在线,false：10 分钟内不在线\n" +
                    "                        \"last_online_time\": \"2020-03-18 18:23:06\"//最近在线时间\n" +
                    "            }\n" +
                    "}";
            JSONObject jsonObject = JSONObject.parseObject(s);
            System.out.println(jsonObject);

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
