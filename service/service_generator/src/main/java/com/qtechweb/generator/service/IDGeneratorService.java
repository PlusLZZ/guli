package com.qtechweb.generator.service;

import com.qtechweb.generator.utils.FormNoTypeEnum;

public interface IDGeneratorService {

    /**
     * 根据单据编号类型 生成单据编号
     *
     * @param formNoTypeEnum 单据编号类型
     */
    String generateFormNo(FormNoTypeEnum formNoTypeEnum);

}
