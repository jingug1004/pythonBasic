package snis.rbm.common.util;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;


public class MailTest {
	public void sendMail() {
		EmailAttachment attachment = new EmailAttachment();
		  attachment.setPath("/WAS/usrbm/upload_file/RSM/20130329_1364538316317.PDF");
		  attachment.setDisposition(EmailAttachment.ATTACHMENT);
		  attachment.setDescription("scan file");
		  attachment.setName("지급조서");

		  // Create the email message
		  try {
			  MultiPartEmail email = new MultiPartEmail();
			  //email.setHostName("mail.kspo.or.kr");
			  email.setHostName("140.101.1.33");
			  email.addTo("jsshin@kspo.or.kr", "신재선");
			  email.setFrom("jsshin@kspo.or.kr", "Me");
			  email.setSubject("The picture");
			  email.setMsg("Here is the picture you wanted");
	
			  // add the attachment
			  email.attach(attachment);
	
			  // send the email
			  email.send();
		  } catch(Exception e) {
			  e.printStackTrace();
		  }
	}	

    public static void main(String[] args) {

    	MailTest mailTest = new MailTest();
        System.out.println("데몬 실행 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        
		try { 
	
			mailTest.sendMail();
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}

    }
}
