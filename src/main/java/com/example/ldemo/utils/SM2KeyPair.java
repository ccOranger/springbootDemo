package com.example.ldemo.utils;

import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * @package: com.example.ldemo.utils
 * @className: ${TYPE_NAME}
 * @description: 类作用描述
 * @author: 李臣臣
 * @createDate: 2019/10/16 15:13
 * @updateUser: 李臣臣
 * @updateDate: 2019/10/16 15:13
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
public class SM2KeyPair {


    /** 公钥 */
    private  ECPoint publicKey;

    /** 私钥 */
    private BigInteger privateKey;

    SM2KeyPair(ECPoint publicKey, BigInteger privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public ECPoint getPublicKey() {
        return publicKey;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }
}
