package com.sap.cloud.security.javasec.samples.usage;

import com.sap.cloud.security.config.Environments;
import com.sap.cloud.security.test.SecurityIntegrationTestRule;
import com.sap.cloud.security.token.Token;
import com.sap.cloud.security.xsuaa.http.HttpHeaders;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.junit.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static com.sap.cloud.security.config.Service.XSUAA;
import static com.sap.cloud.security.config.cf.CFConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

public class HelloJavaServletIntegrationTest {

	private static Properties oldProperties;

	@ClassRule
	public static SecurityIntegrationTestRule rule = SecurityIntegrationTestRule.getInstance(XSUAA)
			.useApplicationServer("src/test/webapp");

	@BeforeClass
	public static void prepareTest() throws Exception {
		oldProperties = System.getProperties();
		System.setProperty(VCAP_SERVICES, IOUtils.resourceToString("/vcap.json", StandardCharsets.UTF_8));
		rule.setClientId(Environments.getCurrentEnvironment().getXsuaaServiceConfiguration().getClientId());
	}

	@AfterClass
	public static void restoreProperties() {
		System.setProperties(oldProperties);
	}

	@Test
	public void requestWithoutToken_statusUnauthorized() throws IOException {
		HttpGet request = createGetRequest("Bearer ");
		try (CloseableHttpResponse response = HttpClients.createDefault().execute(request)) {
			assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
		}
	}

	@Test
	public void requestWithoutHeader_statusUnauthenticated() throws Exception {
		Token token = rule.createToken();

		HttpGet request = createGetRequest("Bearer " + token.getAccessToken());
		request.setHeader(HttpHeaders.AUTHORIZATION, null);
		try (CloseableHttpResponse response = HttpClients.createDefault().execute(request)) {
			assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
		}
	}

	@Test
	public void request_withValidToken() throws IOException {
		HttpGet request = createGetRequest("Bearer " + rule.createToken().getAccessToken());

		try (CloseableHttpResponse response = HttpClients.createDefault().execute(request)) {
			String responseBody = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
			assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);
		}
	}

	private HttpGet createGetRequest(String bearer_token) {
		HttpGet httpGet = new HttpGet(rule.getAppServerUri() + "/hello-java-security");
		httpGet.setHeader(HttpHeaders.AUTHORIZATION, bearer_token);
		return httpGet;
	}

}