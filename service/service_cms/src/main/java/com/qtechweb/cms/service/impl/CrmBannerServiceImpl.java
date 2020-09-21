package com.qtechweb.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtechweb.cms.entity.CrmBanner;
import com.qtechweb.cms.mapper.CrmBannerMapper;
import com.qtechweb.cms.service.CrmBannerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author lzz
 * @since 2020-09-11
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {


    @Cacheable(cacheNames = {"Banner"}, sync = true, key = "'hotBanner'")
    @Override
    public List<CrmBanner> getHotBanner() {
        QueryWrapper<CrmBanner> queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("sort");
        List<CrmBanner> bannerList = list(queryWrapper);
        return bannerList;
    }
}
