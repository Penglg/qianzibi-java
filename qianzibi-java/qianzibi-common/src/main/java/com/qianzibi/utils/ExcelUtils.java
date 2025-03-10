package com.qianzibi.utils;

import com.qianzibi.common.ResultCode;
import com.qianzibi.exception.BusinessException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * excel工具类
 */
public class ExcelUtils {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * 读取 Excel 文件并返回包含数据的二维列表
     *
     * @param file          要读取的 Excel 文件
     * @param title         Excel 文件的标题行
     * @param startRowIndex 开始读取数据的行索引
     * @return 包含数据的二维列表
     * @throws IOException 如果读取文件时发生 I/O 错误
     */
    public static List<List<String>> readExcel(MultipartFile file, String[] title, Integer startRowIndex) {
        InputStream is = null;
        Integer rowIndex = 0;
        try {
            is = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(is);
            List<List<String>> listData = new ArrayList<>();
            // 模板中有Sheet1、Sheet2、Sheet3，这里默认读第一个
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new BusinessException(ResultCode.ERROR_OTHER, "excel文件解析失败");
            }
            for (int rowNumIndex = 0; rowNumIndex <= sheet.getLastRowNum(); rowNumIndex++) {
                rowIndex = rowNumIndex;
                if (rowNumIndex < startRowIndex) {
                    continue;
                }
                List<String> rowData = new ArrayList<>();
                // 读一行
                Row row = sheet.getRow(rowNumIndex);
                if (row == null) {
                    continue;
                }
                int maxColCount = title.length;
                Boolean allEmpty = true;
                if (row.getLastCellNum() < maxColCount) {
                    throw new BusinessException(ResultCode.ERROR_OTHER, "请按照模板文件上传数据");
                }
                for (int colIndex = 0; colIndex < maxColCount; colIndex++) {
                    // 读一格
                    Cell cell = row.getCell(colIndex);
                    String cellValue = getCellValue(cell);
                    rowData.add(cellValue);
                    if (!StringUtils.isEmpty(cellValue)) {
                        allEmpty = false;
                    }
                }
                if (rowNumIndex == startRowIndex) {
                    String dataTitle = rowData.stream().collect(Collectors.joining("_"));
                    String titleStr = Arrays.asList(title).stream().collect(Collectors.joining("_"));
                    if (!dataTitle.equalsIgnoreCase(titleStr)) {
                        throw new BusinessException(ResultCode.ERROR_OTHER, "请按照模板文件上传数据");
                    }
                }
                if (allEmpty) {
                    continue;
                }
                if (rowNumIndex > startRowIndex) {
                    listData.add(rowData);
                }
            }
            return listData;
        } catch (BusinessException e) {
            logger.error("文件解析错误,第：{}行", rowIndex + 1, e);
            throw e;
        } catch (Exception e) {
            logger.error("文件解析错误,第：{}行", rowIndex + 1, e);
            throw new BusinessException(ResultCode.ERROR_OTHER, "文件第" + (rowIndex + 1) + "行解析错误");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("流关闭失败", e);
                }
            }
        }
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        //  数字
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(cell.getNumericCellValue());
        } else {
            String value = cell.getStringCellValue();
            return StringUtils.isEmpty(value) ? "" : value.trim();
        }
    }
}
