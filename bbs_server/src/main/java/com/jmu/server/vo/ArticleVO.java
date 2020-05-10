package com.jmu.server.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jmu.server.entity.ArticleContent;
import com.jmu.server.entity.ArticleExt;
import com.jmu.server.entity.ArticleInfo;
import com.jmu.server.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2019/12/24 17:10
 * @since 1.0
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ArticleVO {
    /**
     * 文章id
     */
    private Long id;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 个性签名
     */
    private String signature;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 用户昵称
     */
    private String nick;
    /**
     * 用户头像
     */
    private String icon;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 文章主题
     */
    private String title;
    /**
     * 资源类型:音乐、视频、图片
     */
    private String resourceType;

    private Long viewNum;
    private Long favorityNum;
    private Long likeNum;
    private Long shareNum;
    /**
     * 图片行数
     */
    private Integer lineCount;
    /**
     * 拓展资源
     */
    private List<ArticleExtVO> articleExtVOS;

    private Boolean like;

    private Boolean favorite;

    @JsonIgnore
    private Long favoriteId;

    @JsonIgnore
    private Long likeId;

    @JsonIgnore
    private List<ArticleExt> articleExts;

    public static ArticleVO of(ArticleInfo info, ArticleContent content, List<ArticleExt> extS, UserInfo user) {

        return ArticleVO.builder()
                .id(info.getId())
                .content(content.getContent())
                .createTime(info.getCreateTime())
                .nick(user.getNickname())
                .icon(user.getIcon())
                .title(info.getTitle())
                .viewNum(info.getViewNum())
                .likeNum(info.getLikeNum())
                .shareNum(info.getShareNum())
                //每行放三个图片资源
                .lineCount(extS.size() / 3)
                .articleExtVOS(extS.stream().map(ArticleExtVO::of).collect(Collectors.toList()))
                .build();
    }
}
