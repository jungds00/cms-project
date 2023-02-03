package com.zerobase.cms.user.service.customer;

import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.domain.repository.CustomerRepository;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpCustomerService {
    private final CustomerRepository customerRepository;

    public Customer signUp(SignUpForm form) {
        return (Customer)this.customerRepository.save(Customer.from(form));
    }

    public boolean isEmailExist(String email) {
        return this.customerRepository.findByEmail(email.toLowerCase(Locale.ROOT)).isPresent();
    }

    @Transactional
    public void verifyEmail(String email, String code) {
        Customer customer = (Customer)this.customerRepository.findByEmail(email).orElseThrow(() -> {
            return new CustomException(ErrorCode.NOT_FOUND_USER);
        });
        if (customer.isVerify()) {
            throw new CustomException(ErrorCode.ALREADY_VERIFY);
        } else if (!customer.getVerifyExpiredAt().equals(code)) {
            throw new CustomException(ErrorCode.WRONG_VERIFICATION);
        } else if (customer.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.EXPIRE_CODE);
        } else {
            customer.setVerify(true);
        }
    }

    @Transactional
    public LocalDateTime changeCustomerValidateEmail(Long customerId, String verificationCode) {
        Optional<Customer> customerOptional = this.customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            Customer customer = (Customer)customerOptional.get();
            customer.setVerificationCode(verificationCode);
            customer.setVerifyExpiredAt(LocalDateTime.now().plusDays(1L));
            return customer.getVerifyExpiredAt();
        } else {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
    }


}