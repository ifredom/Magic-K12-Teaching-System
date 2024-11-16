package com.magic.framework.utils.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Magic Server
 * +----------------------------------------------------------------------
 * 作用: 通用Excel监听器
 * 1. AnalysisEventListener 集成 ReadListener
 * +----------------------------------------------------------------------
 *
 * @author ifredom
 * +----------------------------------------------------------------------
 * @version v0.0.1
 * +----------------------------------------------------------------------
 * @date 2024/10/10 14:12
 * +----------------------------------------------------------------------
 * @email 1950735817@qq.com
 * +----------------------------------------------------------------------
 */
@Slf4j
public class ExcelListener<T> extends AnalysisEventListener<T> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 1000;

    /**
     * 缓存的数据
     */
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 委托对象
     * 通常可以是service，也可以是mapper
     */
    private ExcelBusinessServiceDetail delegateService;

    public ExcelListener() {}

    public ExcelListener(ExcelBusinessServiceDetail delegateService) {
        this.delegateService = delegateService;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. It is same as {@link AnalysisContext#readRowHolder()}
     * @param analysisContext AnalysisContext
     */
    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));

        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if(cachedDataList.size() >= BATCH_COUNT){
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 所有数据解析完成后调用
     *
     * @param analysisContext AnalysisContext 是
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 返回list
     */
    public  List<T> getData() {
        return this.cachedDataList;
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        if(delegateService == null){
            log.error("没有委托对象，无法存储数据！");
            return;
        }
        delegateService.save(cachedDataList);
        log.info("存储数据库成功！");
    }
}
