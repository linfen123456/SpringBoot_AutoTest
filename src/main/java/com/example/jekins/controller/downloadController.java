package com.example.jekins.controller;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 下载测试 控制器
 */
@RestController
public class downloadController {


    public static void main(String[] args){
        InputStream in = downloadController.class.getClassLoader().getResourceAsStream("\\static\\pattern\\test.xlsx");
        System.out.println(in);
        ClassPathResource resource = new ClassPathResource("\\static\\pattern\\test.xlsx");
        System.out.println(resource);
    }




    @GetMapping("/download/pattern")
    public void downloadPattern(HttpServletRequest request, HttpServletResponse response){
        System.out.println("下载文件.....");

        ClassPathResource resource = new ClassPathResource("\\static\\pattern\\test.xlsx");
        System.out.println("获取路径："+ resource);
        try {
            InputStream in = resource.getInputStream();
            System.out.println("获取输入流："+in);
//            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("\\static\\pattern\\test.xlsx");
           XSSFWorkbook workbook = new XSSFWorkbook(in);
            downFile("test",request,response,workbook);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 下载文件
     * @param fileName 下载文件名称
     * @param response 响应
     * @throws IOException 异常
     */
    public static void downFile(String fileName,HttpServletRequest request,
                                HttpServletResponse response,XSSFWorkbook workbook) throws IOException {
        OutputStream os = null;
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            os = response.getOutputStream();
            response.reset();
//            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//            response.setContentType("application/octet-stream; charset=UTF-8");
            response.setContentType("application/binary;charset=utf-8");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            workbook.write(os);
            os.flush();
        } catch (Exception e) {
            System.out.println("fileName=" + fileName);
            e.printStackTrace();
        } finally {
            if (os != null)
                os.close();
        }
    }


}
