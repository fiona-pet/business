package com.fionapet.business.service;

import com.fionapet.business.entity.Images;
import com.fionapet.business.repository.ImagesDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 图片信息 Created by tom on 2016-07-31 16:42:53.
 */

@Service
public class ImagesServiceImpl extends CURDServiceBase<Images> implements ImagesService {

    @Autowired
    private ImagesDao imagesDao;

    @Override
    public DaoBase<Images> getDao() {
        return imagesDao;
    }
}
