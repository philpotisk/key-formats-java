package com.danubetech.keyformats;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

import org.bitcoinj.core.ECKey;
import org.bouncycastle.math.ec.ECPoint;

import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.util.Base64URL;

public class PrivateKeyToJWK {

	public static com.nimbusds.jose.jwk.RSAKey RSAPrivateKeyToJWK(RSAPrivateKey privateKey, RSAPublicKey publicKey, String kid, String use) {

		com.nimbusds.jose.jwk.RSAKey jsonWebKey = new com.nimbusds.jose.jwk.RSAKey.Builder(publicKey)
				.privateKey(privateKey)
				.keyID(kid)
				.keyUse(use == null ? null : new KeyUse(use))
				.build();

		return jsonWebKey;
	}

	public static com.nimbusds.jose.jwk.ECKey P_256KPrivateKeyToJWK(ECKey privateKey, String kid, String use) {

		ECPoint publicKeyPoint = privateKey.getPubKeyPoint();
		byte[] privateKeyBytes = privateKey.getPrivKeyBytes();
		Base64URL xParameter = Base64URL.encode(publicKeyPoint.getAffineXCoord().getEncoded());
		Base64URL yParameter = Base64URL.encode(publicKeyPoint.getAffineYCoord().getEncoded());
		Base64URL dParameter = Base64URL.encode(privateKeyBytes);

		com.nimbusds.jose.jwk.ECKey jsonWebKey = new com.nimbusds.jose.jwk.ECKey.Builder(Curve.P_256K, xParameter, yParameter)
				.d(dParameter)
				.keyID(kid)
				.keyUse(use == null ? null : new KeyUse(use))
				.build();

		return jsonWebKey;
	}

	public static com.nimbusds.jose.jwk.ECKey P_256KPrivateKeyBytesToJWK(byte[] privateKeyBytes, String kid, String use) {

		ECKey privateKey = ECKey.fromPrivate(privateKeyBytes);

		return P_256KPrivateKeyToJWK(privateKey, kid, use);
	}

	public static com.nimbusds.jose.jwk.OctetKeyPair Ed25519PrivateKeyBytesToJWK(byte[] privateKeyBytes, byte[] publicKeyBytes, String kid, String use) {

		byte[] onlyPrivateKeyBytes = Arrays.copyOf(privateKeyBytes, 32);
		Base64URL xParameter = Base64URL.encode(publicKeyBytes);
		Base64URL dParameter = Base64URL.encode(onlyPrivateKeyBytes);

		com.nimbusds.jose.jwk.OctetKeyPair jsonWebKey = new com.nimbusds.jose.jwk.OctetKeyPair.Builder(Curve.Ed25519, xParameter)
				.d(dParameter)
				.keyID(kid)
				.keyUse(use == null ? null : new KeyUse(use))
				.build();

		return jsonWebKey;
	}

	public static com.nimbusds.jose.jwk.OctetKeyPair X25519PrivateKeyBytesToJWK(byte[] privateKeyBytes, byte[] publicKeyBytes, String kid, String use) {

		byte[] onlyPrivateKeyBytes = Arrays.copyOf(privateKeyBytes, 32);
		Base64URL xParameter = Base64URL.encode(publicKeyBytes);
		Base64URL dParameter = Base64URL.encode(onlyPrivateKeyBytes);

		com.nimbusds.jose.jwk.OctetKeyPair jsonWebKey = new com.nimbusds.jose.jwk.OctetKeyPair.Builder(Curve.X25519, xParameter)
				.d(dParameter)
				.keyID(kid)
				.keyUse(use == null ? null : new KeyUse(use))
				.build();

		return jsonWebKey;
	}
}
