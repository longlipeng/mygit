
package com.test;

import java.security.PublicKey;

import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.utils.Constants;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CheckSigWithoutCert {
	private static Logger logger = Logger.getLogger(CheckSigWithoutCert.class);
	//private static final String BASEDIR = "D:/Dai Ami/dev/temp/xml_sec";
	public static final String NAMESPACE = "http://www.w3.org/2000/09/xmldsig#";
	public static final String BLANK = "";
	//private static final String SEP = System.getProperty("file.separator");
	public final static String XMLNS = "xmlns";

	/**
	 * @param args
	 */
/*	public static void main(String[] args) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			Document doc = null;
			boolean check = false;
			DocumentBuilder db = dbf.newDocumentBuilder();
			org.apache.xml.security.Init.init();
			// doc = db.parse("c:\\chkSigResult.xml");
			doc = db.parse("c:\\signew\\CheckSigWithoutCert.xml");

			String xmlString = SecurityUtil.documentToString(doc);
			// logger.info("xmlString1 = " +xmlString);
			logger.info("------------------>");

			String cfgname = System.getProperty("user.dir") + "\\src\\hsm.ini";

			boolean hsminit = HsmUtil.initial(cfgname, "11111111");
			if (!hsminit) {
				logger.info("Hsm init failure");
				return;
			}

			try {
				check = SecurityUtil.validateSignWithoutCert(xmlString);
			} catch (Exception ex) {
				check = false;
			}

			logger.info("hsm check value =" + check);

			Element X509Certificate = (Element) doc.getElementsByTagNameNS(
					Constants.SignatureSpecNS, Constants._TAG_X509CERTIFICATE)
					.item(0);
			String X509CertString = ((Text) X509Certificate.getFirstChild())
					.getNodeValue();
			CertificateFactory cf;
			cf = CertificateFactory.getInstance("X.509");

			InputStream tIn = new ByteArrayInputStream(X509CertString
					.getBytes());
			X509Certificate x509 = (X509Certificate) cf
					.generateCertificate(tIn);
			check = SecurityUtil.validateSign4API(xmlString, X509CertString);
			logger.info("Sec soft check value =" + check);
			check = CheckXmlSignature(doc, x509.getPublicKey());
			logger.info("apache check value =" + check);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}*/

	public static boolean CheckXmlSignature(Document inputDoc,
			PublicKey publicKey) {
		//String baseURI = null;
		try {
			if (inputDoc == null) {
				return false;
			}

			Element sigElement = (Element) inputDoc.getElementsByTagNameNS(
					Constants.SignatureSpecNS, Constants._TAG_SIGNATURE)
					.item(0);

			XMLSignature sig = new XMLSignature(sigElement, null);
			boolean check = sig.checkSignatureValue(publicKey);

			return check;
		} catch (Exception ex) {
			logger.error(ex.getMessage());	
		}
		return false;

	}

}