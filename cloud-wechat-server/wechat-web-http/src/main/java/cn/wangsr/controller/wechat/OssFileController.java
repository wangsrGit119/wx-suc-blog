package cn.wangsr.controller.wechat;

import cn.wangsr.service.OssFileService;
import model.result.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author WJL
 */
@RestController
@RequestMapping("/web")
public class OssFileController {

    @Autowired
    OssFileService ossFileService;

    /**
     * 文件上传
     * @param file, userId
     * @return model.result.ResultMessage
     */
    @RequestMapping("/upload")
    public ResultMessage upload(@RequestParam("file") MultipartFile file, @RequestParam("userId") Integer userId) throws IOException {
        String result = ossFileService.uploadFile(file.getOriginalFilename(),file.getBytes(),userId);
        return  ResultMessage.success(result);
    }

    @GetMapping("/getCoverImages")
    public ResultMessage getCoverImage(HttpServletRequest request){
        String images = ossFileService.getCoverImage();
        return ResultMessage.success(images);
    }

}
