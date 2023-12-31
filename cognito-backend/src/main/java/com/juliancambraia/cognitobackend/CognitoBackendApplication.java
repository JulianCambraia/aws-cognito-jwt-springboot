package com.juliancambraia.cognitobackend;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.jose.util.ResourceRetriever;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.MalformedURLException;
import java.net.URL;

@SpringBootApplication
public class CognitoBackendApplication {

	@Value("${com.tutorial.jwt.aws.connectionTimeout}")
	private int connectionTimeout;

	@Value("${com.tutorial.jwt.aws.readTimeout}")
	private int readTimeout;

	@Value("${com.tutorial.jwt.aws.jwkUrl}")
	private String jwkUrl;

	public static void main(String[] args) {
		SpringApplication.run(CognitoBackendApplication.class, args);
	}

	@Bean
	public ConfigurableJWTProcessor<SecurityContext> configurableJWTProcessor() throws MalformedURLException {

		ResourceRetriever resourceRetriever = new DefaultResourceRetriever(connectionTimeout, readTimeout);
		URL jwkURL = new URL(jwkUrl);
		JWKSource<SecurityContext> jwkSource = new RemoteJWKSet<>(jwkURL, resourceRetriever);
		ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
		JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(JWSAlgorithm.RS256, jwkSource);
		jwtProcessor.setJWSKeySelector(keySelector);

		return jwtProcessor;
	}
}
