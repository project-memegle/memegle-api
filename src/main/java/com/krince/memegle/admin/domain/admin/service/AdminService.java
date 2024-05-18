package com.krince.memegle.admin.domain.admin.service;

import com.krince.memegle.admin.domain.admin.dto.request.RequestAdminLoginDto;

public interface AdminService {

    void login(RequestAdminLoginDto requestAdminLoginDto);
}
