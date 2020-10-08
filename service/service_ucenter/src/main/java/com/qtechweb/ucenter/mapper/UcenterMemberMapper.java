package com.qtechweb.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qtechweb.ucenter.entity.UcenterMember;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author lzz
 * @since 2020-09-15
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    /* 查询某天的注册人数 */
    Integer countRegister(String day);

}
