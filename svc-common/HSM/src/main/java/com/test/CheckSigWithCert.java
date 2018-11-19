package com.test;


public class CheckSigWithCert {

	/**
	 * @param args
	 */
/*	public static void main(String[] args) {
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			Document doc = null;
			
			DocumentBuilder db = dbf.newDocumentBuilder();
			//org.apache.xml.security.Init.init();
				//doc = db.parse("c:\\chkSigResult.xml");
			doc = db.parse("c:\\signew\\CheckSigWithCert.xml");
					
			String xmlString = SecurityUtil.documentToString(doc);
			//logger.info("xmlString1 = " +xmlString);
			logger.info("------------------>");
			String cfgname=System.getProperty("user.dir")+"\\src\\hsm.ini";
			
			boolean hsminit=HsmUtil.initial(cfgname, "11111111");
			if (!hsminit) {
				logger.info("Hsm init failure");
				return;
			}
			
			FileInputStream fis=null;
			CertificateFactory cf=CertificateFactory.getInstance("X.509");
			X509Certificate x509 = null;
			fis=new FileInputStream("C://cert//Cert20070226102246.cer");
			x509 = (X509Certificate)cf.generateCertificate(fis);
					
			boolean check=false;
			check=SecurityUtil.validateSignWithCert(xmlString, x509);
			logger.info("hsm check value =" + check);
			
			check = CheckXmlSignature(doc, x509.getPublicKey());
			logger.info("soft check value =" + check);
			
		}
		catch ( Exception e){
				logger.error(e.getMessage());
		}
	}   
	
	public static boolean CheckXmlSignature(Document inputDoc, PublicKey publicKey) {
		//String baseURI = null;
		try {
			if (inputDoc == null) {
				return false;
			}
		
		Element sigElement = (Element) inputDoc
			.getElementsByTagNameNS(Constants.SignatureSpecNS,
					Constants._TAG_SIGNATURE).item(0);
	    
		XMLSignature sig = new XMLSignature(sigElement, null);
		boolean check = sig.checkSignatureValue(publicKey);
		
		return check;
		} catch (Exception ex) {
			logger.error(ex.getMessage());			
		}
		return false;
		
	}*/
}
