package com.danubetech.keytypes;

import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyType;
import com.nimbusds.jose.jwk.OctetKeyPair;

public class JWKKeyTypes {

	public static String keyTypeForJWK(JWK jsonWebKey) {

		if (KeyType.RSA.equals(jsonWebKey.getKeyType()))
			return jsonWebKey.getKeyType().getValue();
		else if (KeyType.EC.equals(jsonWebKey.getKeyType()))
			return ((ECKey) jsonWebKey).getCurve().getName();
		else if (KeyType.OKP.equals(jsonWebKey.getKeyType()))
			return ((OctetKeyPair) jsonWebKey).getCurve().getName();
		else
			throw new IllegalArgumentException("Unsupported key type " + jsonWebKey.getKeyType());
	}
}
