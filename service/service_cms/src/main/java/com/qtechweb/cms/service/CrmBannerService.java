package com.qtechweb.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qtechweb.cms.entity.CrmBanner;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author lzz
 * @since 2020-09-11
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> getHotBanner();

}
