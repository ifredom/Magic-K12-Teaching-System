package com.magic.server.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.magic.module.excel.entity.ExcelDataModel;
import com.magic.module.excel.mapper.ExcelMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Magic Server
 * +----------------------------------------------------------------------
 * 作用:
 * +----------------------------------------------------------------------
 *
 * @author ifredom
 * +----------------------------------------------------------------------
 * @version v0.0.1
 * +----------------------------------------------------------------------
 * @date 2024/09/30 17:29
 * +----------------------------------------------------------------------
 * @email 1950735817@qq.com
 * +----------------------------------------------------------------------
 */
@SpringBootTest
@Slf4j
public class TestExcel {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ExcelMapper excelMapper;

    @Test
    public void testReadExcel() {
        EasyExcel.read("D:\\test.xlsx", ExcelDataModel.class,new PageReadListener<ExcelDataModel>(dataList->{
//            if (dataList == null || dataList.isEmpty()){
//                log.info("dataList is empty");
//                return;
//            }
            for (ExcelDataModel model : dataList){
                // 打印model字符串化
                try {
                    log.info(mapper.writeValueAsString(model));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        })).sheet().doRead();
    }

    @Test
    public void testWriteExcel() {
        // 查询所有数据

        List<ExcelDataModel> dataList = excelMapper.selectAllData();
        System.out.println(dataList);
        EasyExcel.write("D:\\test.xlsx", ExcelDataModel.class).sheet("模板").doWrite(getDataList());
    }


    private List<ExcelDataModel> getDataList() {
         List<ExcelDataModel> dataList = new ArrayList<>();

         for (int i = 0; i < 10; i++) {
             ExcelDataModel model = new ExcelDataModel();
             model.setHotelName("7星酒店" + i);
             model.setCreateTime(new Date());
             model.setPrice(100.0);
             model.setGender(2);
             dataList.add(model);
         }
         return dataList;
    }
}
