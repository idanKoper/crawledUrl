package com.sysaid.crawled.repository;

import com.sysaid.crawled.model.urlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface urlRepository extends JpaRepository<urlEntity,Integer> {
    urlEntity findByUrl(String url);

}
