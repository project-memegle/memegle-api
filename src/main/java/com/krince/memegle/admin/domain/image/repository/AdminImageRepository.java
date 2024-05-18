package com.krince.memegle.admin.domain.image.repository;

import com.krince.memegle.client.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminImageRepository extends JpaRepository<Image, Long> {
}
