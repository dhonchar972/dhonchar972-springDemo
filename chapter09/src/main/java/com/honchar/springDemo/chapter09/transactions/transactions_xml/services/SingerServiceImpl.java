package com.honchar.springDemo.chapter09.transactions.transactions_xml.services;

import com.google.common.collect.Lists;
import com.honchar.springDemo.chapter09.transactions.base_dao.entities.Singer;
import com.honchar.springDemo.chapter09.transactions.base_dao.repos.SingerRepository;
import com.honchar.springDemo.chapter09.transactions.base_dao.services.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("singerService")
@Repository
public class SingerServiceImpl implements SingerService {
    @Autowired
    private SingerRepository singerRepository;

    @Override
    public List<Singer> findAll() {
        return Lists.newArrayList(singerRepository.findAll());
    }

    @Override
    public Singer findById(Long id) {
        return singerRepository.findById(id).orElse(new Singer());
    }

    @Override
    public Singer save(Singer singer) {
        return singerRepository.save(singer);
    }

    @Override
    public long countAll() {
        return singerRepository.countAllSingers();
    }

    @Autowired
    public void setSingerRepository(SingerRepository singerRepository) {
        this.singerRepository = singerRepository;
    }
}
