package bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import annotation.Column;
import annotation.Table;

@Table(tableName="t_article")
public class articleData implements  Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8777269230562657250L;

	/**
	 * 
	 */

	@Column(field = "article_content",type="longtext",defaultNull=false)
	private String articleContent;//文章正文
	
	@Column(field = "article_create_time",type="datetime",defaultNull=false)
	private  Date articleCreateTime;//文章创建时间
	
	@Column(field = "article_update_time",type="timestamp",defaultNull=false)
	private Date articleUpdateTime;//文章最后更新时间
	
	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	@Column(field = "is_delete",type="int(1)")
	private Integer isDelete;//是否删除0未删除 1已删除;
	
	@Column(field = "is_published",type="int(1)")
	private Integer isPublished;//是否发布1 发布 0待发布
	
	
	@Column(field = "id",type="varchar(100)" , primarykey=true,defaultNull=false)
	private String id;//主键
	
	@Column(field = "article_abstract",type="varchar(100)")
	private String articleAbstract;//文章简介
	
	@Column(field = "article_name",type="varchar(30)" ,defaultNull=false)
	private String articleName;//文章名称
	
	@Column(field="author_name", type="varchar(30)",defaultNull=false)
	private String authorName;

	@Column(field = "author_id",type="varchar(100)",defaultNull=false)
	private String authorId;//作者id
	
	@Column(field = "author_head_portrait_url",type="varchar(120)")
	private String authorHeadPortraitUrl;//作者头像url地址
	
	@Column(field = "article_picture_url",type="varchar(120)")
	private String articlePictureUrl;//文章封面图片位置
	
	@Column(field = "like_count",type="int(9)",defaultNull=false)
	private Integer likeCount;//喜欢数
	
	@Column(field = "read_count",type="int(9)",defaultNull=false)
	private Integer  readCount;//阅读数
	
	@Column(field = "collection_tag_id",type="int(9)",defaultNull=false)
	private Integer collectionTagId;//文章类型
	
	@Column(field = "comment_count",type="int(9)",defaultNull=false)
	private Integer commentCount;//评论数
	
	@Column(field = "no_like_count" ,type="int(8)", defaultNull=false)
	private Integer noLikeCount;//不喜欢数

	/**
	 * @return the articleContentUrl
	 */
	public String getArticleContent() {
		return articleContent;
	}

	/**
	 * @param articleContentUrl the articleContentUrl to set
	 */
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	/**
	 * @return the articleCreateTime
	 */
	public Date getArticleCreateTime() {
		return articleCreateTime;
	}

	/**
	 * @param articleCreateTime the articleCreateTime to set
	 */
	public void setArticleCreateTime(Date articleCreateTime) {
		this.articleCreateTime = articleCreateTime;
	}

	/**
	 * @return the articleUpdateTime
	 */
	public Date getArticleUpdateTime() {
		return articleUpdateTime;
	}

	/**
	 * @param articleUpdateTime the articleUpdateTime to set
	 */
	public void setArticleUpdateTime(Date articleUpdateTime) {
		this.articleUpdateTime = articleUpdateTime;
	}

	/**
	 * @return the isDelete
	 */
	public Integer getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @return the isPublished
	 */
	public Integer getIsPublished() {
		return isPublished;
	}

	/**
	 * @param isPublished the isPublished to set
	 */
	public void setIsPublished(Integer isPublished) {
		this.isPublished = isPublished;
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(generator = "uuid2" )   //指定生成器名称  
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" )  
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the articleAbstract
	 */
	public String getArticleAbstract() {
		return articleAbstract;
	}

	/**
	 * @param articleAbstract the articleAbstract to set
	 */
	public void setArticleAbstract(String articleAbstract) {
		this.articleAbstract = articleAbstract;
	}

	/**
	 * @return the articleName
	 */
	public String getArticleName() {
		return articleName;
	}

	/**
	 * @param articleName the articleName to set
	 */
	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	/**
	 * @return the authorId
	 */
	public String getAuthorId() {
		return authorId;
	}

	/**
	 * @param authorId the authorId to set
	 */
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	/**
	 * @return the authorHeadPortraitUrl
	 */
	public String getAuthorHeadPortraitUrl() {
		return authorHeadPortraitUrl;
	}

	/**
	 * @param authorHeadPortraitUrl the authorHeadPortraitUrl to set
	 */
	public void setAuthorHeadPortraitUrl(String authorHeadPortraitUrl) {
		this.authorHeadPortraitUrl = authorHeadPortraitUrl;
	}

	/**
	 * @return the articlePictureUrl
	 */
	public String getArticlePictureUrl() {
		return articlePictureUrl;
	}

	/**
	 * @param articlePictureUrl the articlePictureUrl to set
	 */
	public void setArticlePictureUrl(String articlePictureUrl) {
		this.articlePictureUrl = articlePictureUrl;
	}

	/**
	 * @return the likeCount
	 */
	public Integer getLikeCount() {
		return likeCount;
	}

	/**
	 * @param likeCount the likeCount to set
	 */
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	/**
	 * @return the readCount
	 */
	public Integer getReadCount() {
		return readCount;
	}

	/**
	 * @param readCount the readCount to set
	 */
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	/**
	 * @return the collectionTagId
	 */
	public Integer getCollectionTagId() {
		return collectionTagId;
	}

	/**
	 * @param collectionTagId the collectionTagId to set
	 */
	public void setCollectionTagId(Integer collectionTagId) {
		this.collectionTagId = collectionTagId;
	}

	/**
	 * @return the commentCount
	 */
	public Integer getCommentCount() {
		return commentCount;
	}

	/**
	 * @param commentCount the commentCount to set
	 */
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	/**
	 * @return the noLikeCount
	 */
	public Integer getNoLikeCount() {
		return noLikeCount;
	}

	/**
	 * @param noLikeCount the noLikeCount to set
	 */
	public void setNoLikeCount(Integer noLikeCount) {
		this.noLikeCount = noLikeCount;
	}

	@Override
	public String toString() {
		return "article_data [articleContent=" + articleContent + ", articleCreateTime=" + articleCreateTime
				+ ", articleUpdateTime=" + articleUpdateTime + ", isDelete=" + isDelete + ", isPublished=" + isPublished
				+ ", id=" + id + ", articleAbstract=" + articleAbstract + ", articleName=" + articleName
				+ ", authorName=" + authorName + ", authorId=" + authorId + ", authorHeadPortraitUrl="
				+ authorHeadPortraitUrl + ", articlePictureUrl=" + articlePictureUrl + ", likeCount=" + likeCount
				+ ", readCount=" + readCount + ", collectionTagId=" + collectionTagId + ", commentCount=" + commentCount
				+ ", noLikeCount=" + noLikeCount + ", getAuthorName()=" + getAuthorName() + ", getArticleContent()="
				+ getArticleContent() + ", getArticleCreateTime()=" + getArticleCreateTime()
				+ ", getArticleUpdateTime()=" + getArticleUpdateTime() + ", getIsDelete()=" + getIsDelete()
				+ ", getIsPublished()=" + getIsPublished() + ", getId()=" + getId() + ", getArticleAbstract()="
				+ getArticleAbstract() + ", getArticleName()=" + getArticleName() + ", getAuthorId()=" + getAuthorId()
				+ ", getAuthorHeadPortraitUrl()=" + getAuthorHeadPortraitUrl() + ", getArticlePictureUrl()="
				+ getArticlePictureUrl() + ", getLikeCount()=" + getLikeCount() + ", getReadCount()=" + getReadCount()
				+ ", getCollectionTagId()=" + getCollectionTagId() + ", getCommentCount()=" + getCommentCount()
				+ ", getNoLikeCount()=" + getNoLikeCount() + "]";
	}


}
