package com.qianzibi.service;

import com.qianzibi.entity.dto.ImportErrorItem;
import com.qianzibi.entity.dto.SessionUserAdminDto;
import com.qianzibi.entity.po.QuestionInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianzibi.entity.query.QuestionInfoQuery;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author 86158
* @description 针对表【question_info(问题)】的数据库操作Service
* @createDate 2025-02-04 21:32:15
*/
public interface QuestionInfoService extends IService<QuestionInfo> {

    void saveOrUpdateQIF(Boolean superAdmin, QuestionInfo questionInfo);

    QuestionInfo showDetailNext(QuestionInfoQuery query, Integer nextType, Integer currentId, boolean b);

    void removeBatchQIF(String valueOf, Integer integer);

    void updateBatchByQIFId(QuestionInfo questionInfo, QuestionInfoQuery queryParams);

    List<ImportErrorItem> importQuestion(MultipartFile file, SessionUserAdminDto sessionUserAdminDto);
}
