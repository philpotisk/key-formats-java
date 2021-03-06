package com.danubetech.keyformats;

import java.security.interfaces.RSAPublicKey;

import org.bitcoinj.core.ECKey;
import org.bouncycastle.math.ec.ECPoint;

import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.util.Base64URL;

public class PublicKeyToJWK {

	public static JWK RSAPublicKeyToJWK(RSAPublicKey publicKey, String kid, String use) {

		JWK jsonWebKey = new com.nimbusds.jose.jwk.RSAKey.Builder(publicKey)
				.keyID(kid)
				.keyUse(use == null ? null : new KeyUse(use))
				.build();

		return jsonWebKey;
	}

	public static JWK P_256KPublicKeyToJWK(ECKey publicKey, String kid, String use) {

		ECPoint publicKeyPoint = publicKey.getPubKeyPoint();
		Base64URL xParameter = Base64URL.encode(publicKeyPoint.getAffineXCoord().getEncoded());
		Base64URL yParameter = Base64URL.encode(publicKeyPoint.getAffineYCoord().getEncoded());

		JWK jsonWebKey = new com.nimbusds.jose.jwk.ECKey.Builder(Curve.P_256K, xParameter, yParameter)
				.keyID(kid)
				.keyUse(use == null ? null : new KeyUse(use))
				.build();

		return jsonWebKey;
	}

	public static JWK P_256KPublicKeyBytesToJWK(byte[] publicKeyBytes, String kid, String use) {

		ECKey publicKey = ECKey.fromPublicOnly(publicKeyBytes);

		return P_256KPublicKeyToJWK(publicKey, kid, use);
	}

	public static JWK Ed25519PublicKeyBytesToJWK(byte[] publicKeyBytes, String kid, String use) {

		Base64URL xParameter = Base64URL.encode(publicKeyBytes);

		JWK jsonWebKey = new com.nimbusds.jose.jwk.OctetKeyPair.Builder(Curve.Ed25519, xParameter)
				.keyID(kid)
				.keyUse(use == null ? null : new KeyUse(use))
				.build();

		return jsonWebKey;
	}

	public static JWK X25519PublicKeyBytesToJWK(byte[] publicKeyBytes, String kid, String use) {

		Base64URL xParameter = Base64URL.encode(publicKeyBytes);

		JWK jsonWebKey = new com.nimbusds.jose.jwk.OctetKeyPair.Builder(Curve.X25519, xParameter)
				.keyID(kid)
				.keyUse(use == null ? null : new KeyUse(use))
				.build();

		return jsonWebKey;
	}
}
