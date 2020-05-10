package com.jmu.client.module.article.controller;

import com.github.pagehelper.PageInfo;
import com.jmu.client.entity.User;
import com.jmu.client.expection.BusinessException;
import com.jmu.client.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.jmu.client.entity.Manager;
import com.jmu.client.module.article.service.ArticleService;
import com.jmu.client.req.ArticleReq;
import com.jmu.client.req.Page;
import com.jmu.client.entity.UserInfo;
import com.jmu.client.req.UserReq;
import com.jmu.client.util.CodeGenerateUtil;
import com.jmu.client.util.Result;
import com.jmu.client.util.ResultUtil;
import com.jmu.client.vo.ArticleVO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/23 16:28
 * @since 1.0
 */
@RestController
@RequestMapping("/article")
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${imageUrl}")
    private String imageUrl;

    /**
     * 发布文章
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2019/12/24 11:32
     * @since 1.0
     */
    @PostMapping("/create")
    public Result<String> ArticleController(@RequestBody ArticleReq articleReq, @RequestHeader("token")String token) {
        System.out.println(token);
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo) redisUtil.get(token);
            System.out.println(userInfo);
        }
        System.out.println(userInfo);
        articleReq.setUserId(userInfo.getId());
        log.info("ArticleController ArticleController ArticleReq:{}", articleReq);
        try {
            articleService.sendArticle(articleReq);
        } catch (BusinessException e) {
            log.error(e.getMessage());
            return ResultUtil.error("发布消息失败");
        }
        return ResultUtil.success("发布消息成功");
    }


    /**
     * 物理删除文章
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/13 15:53
     * @since 1.0
     */
    @DeleteMapping("/deleteArticle/{id}")
    public Result<String> deleteArticle(@PathVariable("id") Long id,@RequestHeader("token")String token) {
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo) redisUtil.get(token);
        }
        try {
            articleService.deleteArticle(id, userInfo);
        } catch (BusinessException e) {
            log.error(e.getMessage());
            return ResultUtil.error("删除文章失败");
        }
        return ResultUtil.success("删除文章成功");
    }


    /**
     * 逻辑删除文章
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/13 15:53
     * @since 1.0
     */
    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable("id") Long id,@RequestHeader("token")String token) {
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo) redisUtil.get(token);
        }
        try {
            articleService.delete(id, userInfo);
        } catch (BusinessException e) {
            log.error(e.getMessage());
            return ResultUtil.error("删除文章失败");
        }
        return ResultUtil.success("删除文章成功");
    }

    /**
     * 查看一篇文章
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2019/12/24 17:10
     * @since 1.0
     */
    @GetMapping("/getArticle/{id}")
    public Result<ArticleVO> getArticle(@PathVariable("id") Long id,@RequestHeader("token")String token) {
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo) redisUtil.get(token);
        }
        ArticleVO articleVO = articleService.getArticle(id, userInfo);
        return ResultUtil.success(articleVO);
    }


    /**
     * 获取用户所有文章
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2019/12/26 10:36
     * @since 1.0
     */
    @GetMapping("/listArticleOfUser")
    public Result<PageInfo<ArticleVO>> getArticleByUserId(Page page,@RequestHeader("token")String token) {
        log.info("ArticleController getArticleByUserId page:{}", page);
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo) redisUtil.get(token);
        }
        PageInfo<ArticleVO> articleVOS = articleService.listArticleOfUser(userInfo.getId(), page);
        return ResultUtil.success(articleVOS);
    }

    /**
     * 分页获取文章信息
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/13 10:19
     * @since 1.0
     */
    @GetMapping("/listArticlePage")
    public Result<PageInfo<ArticleVO>> listArticlePage(Page page,@RequestHeader("token")String token) {
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo) redisUtil.get(token);
        }
        log.info("ArticleController listArticlePage page:{}", page);
        PageInfo<ArticleVO> articleVOS = articleService.listArticlePage(page,userInfo);
        return ResultUtil.success(articleVOS);
    }

    @PutMapping("/featured")
    public Result<String> feature(@RequestBody ArticleReq articleReq) {
        log.info("ArticleController feature articleReq:{}", articleReq);
        return articleService.featured(articleReq);
    }

   /**
     * 上传图片
     *
     * @param
     * @return
     * @author zhoujinmu (jinmu.zhou@ucarinc.com)
     * @date 2020/1/8 14:03
     * @since 1.0
     */
    @PostMapping("/uploadImage")
    public Result<String> uploadFile(MultipartFile file,@RequestHeader("token")String token) {
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo) redisUtil.get(token);
        }
        String imagePath = "";
        String netPath = "";
        String hostAdd = "";
        System.out.println("FileController uploadFile real Path:{}" + imageUrl);
        System.out.println("file" + file);
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostAdd = addr.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("获取主机地址失败：" + e.toString());
        }
        try {
            CommonsMultipartFile cf = (CommonsMultipartFile) file;
            System.out.println("cf:{}" + cf);
            DiskFileItem fi = (DiskFileItem) cf.getFileItem();
            System.out.println("fi:{}" + fi);
            File f1 = fi.getStoreLocation();
            System.out.println("FileController uploadFile real f1:{}" + f1);
            InputStream ips = new FileInputStream(f1);
            //每个照片生成唯一名称,用生成文章编号工具
            String imagePre = CodeGenerateUtil.generateArticleCode(userInfo.getId());
            imagePath = imageUrl + imagePre + ".jpg";
            netPath = "http://" + hostAdd + "/images/" + imagePre + ".jpg";
            OutputStream ops = new FileOutputStream(imagePath);
            byte[] b = new byte[1024];
            int len;
            try {
                while ((len = ips.read(b)) != -1) {
                    ops.write(b, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 完毕，关闭所有链接
                try {
                    ops.close();
                    ips.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ResultUtil.success(netPath);
    }

    /**
     * 获取其他人文章
     *
     * @author zhoujinmu
     * @date 2020/3/11
     * @since 1.0
     */
    @GetMapping("/listArticleOfOther")
    Result<PageInfo<ArticleVO>> getArticleOfOther(UserReq userReq) {
        log.info("ArticleController getArticleOfOther userReq:{}", userReq);
        Page page = new Page();
        page.setPageNum(userReq.getPageNum());
        page.setPageSize(userReq.getPageSize());
        PageInfo<ArticleVO> articleVOS = articleService.listArticleOfUser(userReq.getId(), page);
        return ResultUtil.success(articleVOS);
    }

    /**
     * 客户端：获得本人的文章数
     *
     * @author zhoujinmu
     * @date 2020/4/15
     * @since 1.0
     */
    @GetMapping("/getArticleNum")
    Result<Integer> getArticleNum(@RequestHeader("token")String token) {
        UserInfo userInfo = new UserInfo();
        if(token != null) {
            userInfo = (UserInfo) redisUtil.get(token);
        }
        return ResultUtil.success(articleService.getArticleNum(userInfo));
    }

    /**
     * 客户端：获得他人的文章数
     *
     * @author zhoujinmu
     * @date 2020/4/15
     * @since 1.0
     */
    @GetMapping("/getArticleNumOfOther")
    Result<Integer> getArticleNumOfOther(Long id) {
        System.out.println(id);
        System.out.println(articleService.getArticleNumOfOther(id));
        return ResultUtil.success(articleService.getArticleNumOfOther(id));
    }

    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    public Result<String> upload(String userId, String bgmId, double videoSeconds,
                                 int videoWidth, int videoHeight, String desc,
                                 MultipartFile files) {
        String netPath = "";
        String hostAdd = "";
        //保存文件的路径
        String fileSpace = "B:\\tools\\nginx\\nginx-1.16.1\\html";
        //保存到数据库的相对路径
        String uploadPathDB = "\\videos";
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostAdd = addr.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("获取主机地址失败：" + e.toString());
        }
        try {
            if (files != null) {
                String fileNmae = files.getOriginalFilename();

                if (StringUtils.isNotBlank(fileNmae)) {
                    //文件上传的最终保存路径
                    String finalVideoPath = fileSpace + uploadPathDB + "\\" + fileNmae;
                    //数据库最终保存的相对路径
                    uploadPathDB += ("\\" + fileNmae);
                    File outFile = new File(finalVideoPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        //创建父类文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                    netPath = "http://" + hostAdd + "/videos/" + fileNmae;
                }
            } else {
                return ResultUtil.error("上传出错");
            }
        } catch (Exception e) {
            return ResultUtil.error("上传出错");
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                return ResultUtil.error("上传出错");
            }
        }
        return ResultUtil.success(netPath);
    }
}
