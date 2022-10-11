package com.itsol.recruit.service.impl;

import com.itsol.recruit.entity.OTPS;
import com.itsol.recruit.repository.OTPsRepository;
import com.itsol.recruit.service.OtpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OtpsServiceImpl implements OtpsService{
//    public final OTPsRepository otpsRepository;
//
//    public OtpsServiceImpl(OTPsRepository otpsRepository) {
//        this.otpsRepository = otpsRepository;
//    }
    @Autowired
    OTPsRepository otpsRepository;
    @Override
    public List<OTPS> getAllOtp(){
        return otpsRepository.findAll();
    }

    @Override
    public OTPS save(OTPS otps){
        return otpsRepository.save(otps);
    }

    @Override
    public List<OTPS> findAllByUser() {
        return null;
    }
}
