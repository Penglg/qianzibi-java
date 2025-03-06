package com.qianzibi.service;

import com.qianzibi.entity.dto.ImportErrorItem;
import com.qianzibi.entity.dto.SessionUserAdminDto;
import com.qianzibi.entity.po.ExamQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianzibi.entity.po.ExamQuestionItem;
import com.qianzibi.entity.query.ExamQuestionQuery;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author 86158
* @description 针对表【exam_question(考试题目)】的数据库操作Service
* @createDate 2025-02-04 21:32:08
*/
public interface ExamQuestionService extends IService<ExamQuestion> {

    void saveExamQuestion(List<ExamQuestionItem> examQuestionItemList, ExamQuestion examQuestion, boolean isSuperAdmin);

    void updateBatch(ExamQuestionQuery query, ExamQuestion examQuestion);

    void removeExamQuestion(List<String> questionIds, Integer integer);

    List<ImportErrorItem> importExamQuestion(MultipartFile file, SessionUserAdminDto sessionUserAdminDto);

    ExamQuestion showDetailNext(ExamQuestionQuery query, Integer nextType, Integer currentId, boolean b);
}
