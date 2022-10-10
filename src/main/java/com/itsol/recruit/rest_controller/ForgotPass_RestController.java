package com.itsol.recruit.rest_controller;
import com.itsol.recruit.entity.OTPS;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.OTPsRepository;
import com.itsol.recruit.service.EmailService;
import com.itsol.recruit.service.OtpsService;
import com.itsol.recruit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/rest/forgotPassword")
public class ForgotPass_RestController {
   @Autowired
    UserService userService;
   @Autowired
    EmailService emailService;
   @Autowired
    OtpsService otpsService;

    @GetMapping("{email}")
    public boolean sendMail(@PathVariable("email") String email) {
        for (User x : userService.findAll()
        ) {
            if (x.getEmail().equals(email) && x.isActive()==true);
            Random random = new Random();
            int otp = random.nextInt(900000)+100000;

            OTPS ot = new OTPS();
            ot.setCode(otp);
            ot.setUser(x);;
            otpsService.save(ot);
            emailService.sendEmail(email,"OTP code","Your OTP code is: "+otp);
            return  true;
        }
        return false;
    }
}
