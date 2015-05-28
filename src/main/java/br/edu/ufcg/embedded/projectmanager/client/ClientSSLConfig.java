package br.edu.ufcg.embedded.projectmanager.client;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;



@SuppressWarnings("deprecation")
public class ClientSSLConfig {

public ClientSSLConfig(){}
	
	public static DefaultHttpClient createHttpClient() throws NoSuchAlgorithmException, KeyManagementException {
		  TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				    public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
				    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) { }
				    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) { }
		  		}
		  };
		  
		  SSLContext context = SSLContext.getInstance("TLS");
		  context.init(null, trustAllCerts, null);
		  SSLSocketFactory sf = new SSLSocketFactory(context, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		  SchemeRegistry schemeRegistry = new SchemeRegistry();
		  schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		  schemeRegistry.register(new Scheme("https", 443, sf));
		  SingleClientConnManager cm = new SingleClientConnManager(schemeRegistry);
		  
		  return new DefaultHttpClient(cm);
		}
} 
	

