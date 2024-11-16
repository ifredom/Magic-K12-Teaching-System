package com.magic.framework.handler;

import cn.hutool.system.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;




/**
 * 作用: 应用启动后，使用系统默认浏览器自动打开浏览器指定页面
 * @author ifredom
 * @date 2024/9/19 下午8:48
 * @version v0.0.1
 * @email 1950735817@qq.com
 */

@Component
@Slf4j
public class OpenBrowserHandler implements CommandLineRunner {
    @Value("${server.port}")
    private int serverPort;

    @Override
    public void run(String... args) throws Exception {
        log.info("AutoOpenBrowser is running...{}", serverPort);
//        try {
//            if(SystemUtil.getOsInfo().isWindows()){
//                Runtime.getRuntime().exec("cmd /c start http://localhost:"+serverPort+"/admin/login.html");
//            }else{
//                String name = SystemUtil.getOsInfo().getName();
//                System.out.println("==================================================注意====================================================");
//                System.out.println("当前操作系统为：" + name + ",不支持自动打开浏览器，请自行在浏览器访问：" + "http://localhost:" + serverPort + "/admin");
//                System.out.println("==================================================注意====================================================");
//            }
//        } catch (Exception e){
//            log.error("AutoOpenBrowser error",e);
//        }
    }
}
