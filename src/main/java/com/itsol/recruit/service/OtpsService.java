package com.itsol.recruit.service;

import com.itsol.recruit.entity.OTPS;

import java.util.List;

public interface OtpsService {
    public List<OTPS> getAllOtp();

    public OTPS save(OTPS otps);
}
