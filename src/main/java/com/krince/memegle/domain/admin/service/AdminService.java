package com.krince.memegle.domain.admin.service;

import com.krince.memegle.domain.admin.dto.request.RequestAdminLoginDto;

public interface AdminService {

    void login(RequestAdminLoginDto requestAdminLoginDto);
}
