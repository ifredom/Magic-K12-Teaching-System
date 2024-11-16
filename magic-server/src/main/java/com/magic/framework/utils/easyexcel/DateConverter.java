package com.magic.framework.utils.easyexcel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.util.StringUtils;

import java.util.Date;

/**
 * Magic Server
 * +----------------------------------------------------------------------
 * 作用: EasyExcel 日期类型 Date 转换的问题
 * 作用:
 * +----------------------------------------------------------------------
 *
 * @author ifredom
 * +----------------------------------------------------------------------
 * @version v0.0.1
 * +----------------------------------------------------------------------
 * @date 2024/10/10 15:44
 * +----------------------------------------------------------------------
 * @email 1950735817@qq.com
 * +----------------------------------------------------------------------
 */
public class DateConverter implements Converter<Date> {

    @Override
    public Date convertToJavaData(ReadConverterContext<?> context) throws Exception {
        Class<?> aClass = context.getContentProperty().getField().getType();
        CellDataTypeEnum type = context.getReadCellData().getType();
        String stringValue = context.getReadCellData().getStringValue();
        if(aClass.equals(Date.class) && type.equals(CellDataTypeEnum.STRING)  && StringUtils.isBlank(stringValue)){
            return null;
        }

        return Converter.super.convertToJavaData(context);
    }
}
