package com.zerobase.cms.user.application;

import com.zerobase.cms.user.client.MailgunClient;
import com.zerobase.cms.user.client.mailgun.SendMailForm;
import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.exception.ErrorCode;
import com.zerobase.cms.user.service.SignUpCustomerService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SignUpApplication {
    private static final Logger log = LoggerFactory.getLogger(SignUpApplication.class);
    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;

    public void customerVerify(String email, String code) {
        this.signUpCustomerService.verifyEmail(email, code);
    }

    public String customerSignUp(SignUpForm form) {
        if (this.signUpCustomerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALERADY_REGIESTER_USER);
        } else {
            Customer c = this.signUpCustomerService.signUp(form);
            String code = this.getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder().from("tester@dannymytester.com").to(form.getEmail()).subject("Verification Email").text(this.getVerificationEmailBody(c.getEmail(), c.getName(), code)).build();
            log.info("Send email result :" + (String)this.mailgunClient.sendEmail(sendMailForm).getBody());
            this.signUpCustomerService.changeCustomerValidateEmail(c.getId(), code);
            return "회원 가입에 성공하였습니다";
        }
    }

    private String getRandomCode() {
        return RandomStringUtils.random(10, true, true);
    }

    private String getVerificationEmailBody(String email, String name, String code) {
        StringBuilder builder = new StringBuilder();
        return builder.append("Hello ").append(name).append("! Please Click Link for verification.\n\n").append("http://localhost:8080/customer/signup/verify?email=").append(email).append("&code=").append(code).toString();
    }

    public SignUpApplication(final MailgunClient mailgunClient, final SignUpCustomerService signUpCustomerService) {
        this.mailgunClient = mailgunClient;
        this.signUpCustomerService = signUpCustomerService;
    }
}