package com.jmu.client;

import com.jmu.client.module.articleReview.mapper.ArticleReviewMapperExt;
import com.jmu.client.module.label.mapper.LabelMapperExt;
import com.jmu.client.module.user.mapper.UserMapperExt;
import com.jmu.client.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.jmu.client.entity.TemplateData;
import com.jmu.client.util.WxMessageUtil;
import com.jmu.client.vo.WxMessVO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
//import ucar.train.security.util.TokenUtil;

//import ucar.train.security.service.MyUserDetailsService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SecurityApplicationTests {

//    @Autowired
//    private TokenUtil tokenUtil;

    //    @Autowired
//    private MyUserDetailsService userDetailsService;
    @Autowired
    private UserMapperExt userMapperExt;

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private LabelMapperExt labelMapper;

    @Autowired
    private ArticleReviewMapperExt articleReviewMapperExt;

    @Autowired
    private WxMessageUtil wxMessageUtil;


//    @Autowired
//    private RoleDao roleDao;

//
//    @Test
//    public void generateToken() {
//        UserDetails userDetails = userDetailsService.loadUserByUsername("zjm");
//        String token = tokenUtil.generateToken(userDetails);
//        System.out.println(token);
//    }
//
//    @Test
//    public void say() {
//        System.out.println(passwordEncoder);
//        System.out.println("hello");
//    }
//
//    @Test
//    public void validate() {
//        UserDetails userDetails = userDetailsService.loadUserByUsername("zjm");
//        Boolean success = tokenUtil.validateToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ6am0iLCJleHAiOjE1NzY0NjY0OTksImlhdCI6MTU3NjQ2NjQ0OX0.ibtO2ha_GgYBIU-MR2yvCH5-RbJ_ENlIcN-OSQ7y5rdZOBl-Kk-UtKxhH7f4Jqv9md1alfcHKklvb1gikXX7Ig",userDetails);
//        System.out.println(success);
//    }
//
    @Test
    public void refreshToken() {
        System.out.println(redisUtil.hashKey("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1ODgxNTU3MDAsInVzZXJuYW1lIjoib29LYS00dVhtbzFqcmpJRXRMS3Zhb0dyTWZFUSJ9.hflUkvUIg0jjlNFdJaQyeV1rNWGAXlXiArBCPZJ65rE"));
        System.out.println(redisUtil.get("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1ODgxNTU3MDAsInVzZXJuYW1lIjoib29LYS00dVhtbzFqcmpJRXRMS3Zhb0dyTWZFUSJ9.hflUkvUIg0jjlNFdJaQyeV1rNWGAXlXiArBCPZJ65rE"));
    }


    @Test
    public void base64() {
//        System.out.println(Base64.getEncoder().encodeToString(getBytes("1234")));
        log.debug("nihadiosdf");
        log.info("nihadiosdf");
        log.error("nihadiosdf");

    }

    @Test
    public void time() {
        Calendar calendar = Calendar.getInstance();
    }

    @Test
    public void data() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
    }

    @Test
    public void isNull() {
        Map<String, Object> map = new HashMap<>();
        map.put("label", "标签1");
        Long a = labelMapper.selectCount(map);

    }

    @Test
    public void list() {
        System.out.println(articleReviewMapperExt.listArticleReview());
    }

    @Test
    public void sendWxMess() {
        WxMessVO wxMessVO = new WxMessVO();
        wxMessVO.setTouser("ooKa-4uXmo1jrjIEtLKvaoGrMfEQ");
        wxMessVO.setTemplate_id("EXRvqLjpGw5kyT8WNOnY_nfQ38wlLXnmG63rv_6ESDQ");
        wxMessVO.setPage("pages/hoem/home");
        Map<String, TemplateData> m = new HashMap<>(3);
        m.put("phrase2", new TemplateData("通过"));
        if ("通过".equals("通过")) {
            m.put("thing3", new TemplateData("您"));
        } else {
            m.put("thing3", new TemplateData("抱歉，您未通过住户审核，请重新提交审核材料！"));
        }
        wxMessVO.setData(m);
        String messResult = wxMessageUtil.push(wxMessVO);
        System.out.println(messResult);
    }


}
