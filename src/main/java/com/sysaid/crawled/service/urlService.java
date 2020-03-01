package com.sysaid.crawled.service;

import com.sysaid.crawled.model.urlEntity;
import com.sysaid.crawled.repository.urlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class urlService {

    @Autowired
    private urlRepository urlRepo;

    public List<urlEntity> listAll()
    {
        return urlRepo.findAll();
    }

    public void save (urlEntity url){
        urlRepo.save(url);
    }

    public urlEntity get(Integer id){
        return urlRepo.findById(id).get();
    }

    public urlEntity getByUrl(String url){
        return urlRepo.findByUrl(url);
    }

    public void delete(Integer id){
        urlRepo.deleteById(id);
    }

}
