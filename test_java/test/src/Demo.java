package com.adtec.outconnplat.busi.hlwbusi;



import RsaUtil;

import java.util.Map;
import java.util.TreeMap;

public class Demo {


    public static void main(String[] args) {
        callHrb();
    }

    public static void callHrb() {
        try {
            String url ="https://xxxxxxxxxxx";

            Map<String,String> keyMap = RsaUtil.genKeys();
            String publicKey = keyMap.get("public");
            String privateKey = keyMap.get("private");

            String request = "{}"; //request body
            //rsa encrypt
            String data = RsaUtil.encryptByCrtPublicKey(request,publicKey);


            TreeMap<String,Object> reqMap = new TreeMap<>();
            reqMap.put("data", data);
            reqMap.put("requestTime", "2023-11-16 13:00:00");
            reqMap.put("serviceCode","xxxxx");
	    reqMap.put("channelNo","xxxxx");

            StringBuilder signStringBuilder = new StringBuilder();
            for (Map.Entry entry: reqMap.entrySet()) {
                //sm3加密的可能会有特殊符号，http发送后会变空字符串，做下urluncode
                signStringBuilder .append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
            String signStr = signStringBuilder .substring(1);
            System.out.println("signStr=" + signStr);

            //sign
            String sign = RsaUtil.signByPrivateKey(signStr,privateKey,"UTF-8");
            System.out.println("sign=" + sign);
            reqMap.put("signature",sign);

            System.out.println(reqMap);

            // Post
            //String respResult = HttpUtils.httpPost(url,reqMap);

            //verify sign
            boolean verify = RsaUtil.verifyByPublicKey(signStr,publicKey,sign,"UTF-8");
            System.out.println(verify);
            //rsa decrypt
            String decData = RsaUtil.decryptByPrivateKey(data,privateKey);
            System.out.println(decData);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
