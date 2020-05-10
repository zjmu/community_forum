package com.jmu.client.service.impl;

import com.jmu.client.mapper.UserMapper;
import com.jmu.client.module.article.mapper.ArticleContentExtMapper;
import com.jmu.client.module.article.mapper.ArticleExtExtMapper;
import com.jmu.client.module.article.mapper.ArticleInfoExtMapper;
import com.jmu.client.module.article.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.jmu.client.entity.ArticleExt;
import com.jmu.client.entity.ArticleInfo;
import com.jmu.client.enums.SystemArticleStatuEnum;
import com.jmu.client.mapper.ArticleInfoMapper;
import com.jmu.client.mq.ReceiveMsg;
import com.jmu.client.mq.SendMsg;
import com.jmu.client.util.CodeGenerateUtil;
import com.jmu.client.util.JodaUtils;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceImplTest {

    @Autowired
    ArticleInfoMapper articleInfoMapper;
    @Autowired
    private ArticleContentExtMapper articleContentExtMapper;
    @Autowired
    private ArticleExtExtMapper articleExtExtMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleInfoExtMapper articleInfoExtMapper;
    @Autowired
    private ReceiveMsg receiveMsg;
    @Autowired
    private SendMsg sendMsg;

    @Test
    public void sendAndReceive_2() {
        System.out.println(SystemArticleStatuEnum.getCodeByMessage("未发布"));
    }

    @Test
    public void sendArticle() {
        String articleCode = CodeGenerateUtil.generateArticleCode(11L);
        ArticleInfo articleInfo = ArticleInfo.builder()
                .articleCode(articleCode)
                .state((byte) 0)
                .favoryNum(0L)
                .likeNum(0L)
                .shareNum(0L)
                .userId(11L)
                .viewNum(0L)
                .title("FSDFSDFSDF")
                .createTime(JodaUtils.getCurrentDate())
                .modifyTime(JodaUtils.getCurrentDate())
                .build();
        System.out.println(articleInfoMapper);
        System.out.println(userMapper);
        articleInfoMapper.insert(articleInfo);
        System.out.println(articleInfo.getId());
    }

    @Test
    public void ext() {
        List<ArticleExt> articleExts = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            ArticleExt articleExt = ArticleExt.builder()
                    .articleId(1L)
                    .state((byte) 0)
                    .resourceUrl(String.valueOf(i))
                    .type((byte) 1)
                    .createTime(JodaUtils.getCurrentDate())
                    .modifyTime(JodaUtils.getCurrentDate())
                    .build();
            articleExts.add(articleExt);
        }
        System.out.println(articleExts);
    }

    @Test
    public void exception() {
        System.out.println("异常后继续运行");

    }

    private void test() throws Exception {
        throw new Exception("异常了");

    }

    @Test
    public void getArticle() {
        System.out.println(articleInfoExtMapper.listArticleByUserId(1L));
    }
}