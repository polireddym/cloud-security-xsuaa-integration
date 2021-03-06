package com.sap.cloud.security.token;

/**
 * Constants denoting JWT claims as specified here:
 * https://tools.ietf.org/html/rfc7519#section-4
 */
public final class TokenClaims {
	private TokenClaims() {
		throw new IllegalStateException("Utility class");
	}

	public static final String ISSUER = "iss";
	public static final String EXPIRATION = "exp";
	public static final String AUDIENCE = "aud";
	public static final String NOT_BEFORE = "nbf";
	public static final String SUBJECT = "sub"; // to be used instead of client id
	public static final String USER_NAME = "user_name";
	public static final String GIVEN_NAME = "given_name";
	public static final String FAMILY_NAME = "family_name";
	public static final String EMAIL = "email";
	public static final String SAP_GLOBAL_USER_ID = "user_uuid";
	public static final String SAP_GLOBAL_ZONE_ID = "zone_uuid"; // tenant GUID

	public final class XSUAA {

		private XSUAA() {
		}

		public static final String ORIGIN = "origin";
		public static final String GRANT_TYPE = "grant_type"; // OAuth grant type used for token creation
		public static final String ZONE_ID = "zid"; // tenant GUID -> SAP_GLOBAL_ZONE_ID
		public static final String CLIENT_ID = "cid"; // OAuth client identifier
		public static final String SCOPES = "scope"; // list of scopes including app id, e.g. "my-app!t123.Display"
		public static final String ISSUED_AT = "iat";
		public static final String EXTERNAL_ATTRIBUTE = "ext_attr";
		public static final String EXTERNAL_ATTRIBUTE_ZDN = "zdn";
		public static final String EXTERNAL_ATTRIBUTE_ENHANCER = "enhancer";

		public static final String TRUSTED_CLIENT_ID_SUFFIX = "trustedclientidsuffix";
	}

	// SAP User token
	/*
	 * public final class SAP_ID { private SAP_ID() { } // service_instance_id }
	 */

}
