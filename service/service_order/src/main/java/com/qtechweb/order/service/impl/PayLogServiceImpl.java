package com.qtechweb.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtechweb.order.entity.PayLog;
import com.qtechweb.order.mapper.PayLogMapper;
import com.qtechweb.order.service.PayLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author lzz
 * @since 2020-09-22
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

}
