/*
package com.zerobase.cms.user.service;

import com.zerobase.cms.user.client.MailgunClient;
import com.zerobase.cms.user.client.mailgun.SendMailForm;
import feign.Response;
import org.springframework.stereotype.Service;

@Service
public class EmailSendService {
    private final MailgunClient mailgunClient;

    public Response sendEmail() {
        SendMailForm form = SendMailForm.builder().from("zerobase-test@my.com").to("royalsky00@gmail.com").subject("Test email from zero base").text("my text").build();
        return this.mailgunClient.sendEmail(form);
    }

    public EmailSendService(final MailgunClient mailgunClient) {
        this.mailgunClient = mailgunClient;
    }
}
*/
