package com.jmu.client.module.articleComment.service.impl;

import com.github.pagehelper.PageHelper;
import com.jmu.client.module.article.mapper.ArticleInfoExtMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.jmu.client.entity.ArticleComment;
import com.jmu.client.mapper.ArticleCommentMapper;
import com.jmu.client.mapper.AttentionMapper;
import com.jmu.client.util.RedisUtil;
import com.jmu.client.vo.ArticleCommentVO;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleCommentServiceImplTest {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ArticleCommentMapper articleCommentMapper;

    @Autowired
    private ArticleInfoExtMapper articleInfoExtMapper;

    @Autowired
    private AttentionMapper attentionMapper;

    @Test
    public void listComment() {
        Long articleId = 1L;
        List<ArticleCommentVO> comments = new ArrayList<>();
        List<ArticleComment> articleComments = articleCommentMapper.listCommentByParentId(articleId, 0L);
        comments.addAll(listChildComment(articleId, articleComments, ""));
    }

    private List<ArticleCommentVO> listChildComment(Long articleId, List<ArticleComment> articleComments, String nick) {
        List<ArticleComment> comments = new ArrayList<>();
        List<ArticleCommentVO> articleCommentVOS = new ArrayList<>();

        for (ArticleComment articleComment : articleComments) {
            ArticleCommentVO articleCommentVO = new ArticleCommentVO();
            articleCommentVO.setSendNick(articleComment.getNick());
            articleCommentVO.setText(articleComment.getContent());
            articleCommentVO.setSendUserId(articleComment.getUserId());
            articleCommentVO.setReturnNick(nick);
            articleCommentVOS.add(articleCommentVO);
            comments.add(articleComment);
            Long id = articleComment.getId();
            String returnNick = articleComment.getNick();
            List<ArticleComment> articleCommentList = articleCommentMapper.listCommentByParentId(articleId, id);
            if (articleCommentList != null && articleCommentList.size() > 0) {
                listChildComment(articleId, articleCommentList, returnNick);
            }
        }
        return articleCommentVOS;
    }

    @Test
    public void att() {
        System.out.println("--------------------");
        System.out.println(attentionMapper.listAttentionByUserId(1L));
        ;
    }

    @Test
    public void page() {
    }

    @Test
    public void redis() {
        redisUtil.hset("测试", "项目", "值");
        redisUtil.hset("测试", "项目2", "值2");
        redisUtil.hset("测试", "项目3", "值0", 30);
    }
}