package bean;

import java.io.Serializable;
import java.util.Date;

import annotation.Column;
import annotation.Table;


@Table(tableName="t_article_comment")
public class commentData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5267335947440430926L;



	@Column(field="content",type="varchar(200)",defaultNull=true)
	private String content ;
	
	
	@Column(field="id",type="varchar(100)",defaultNull=false,primarykey=true)
	private String Id;//����id
	
	@Column(field="author_id",type="varchar(100)",defaultNull=false)
	private String authorId;//����id
	
	@Column(field="article_id",type="varchar(100)",defaultNull=false)
	private String  articleId;//����Id
	
	@Column(field="create_time",type="datetime",defaultNull=false)
	private Date createTime;//���۴���ʱ��
	
	@Column(field="update_time",type="datetime",defaultNull=false)
	private Date updateTime;//������ʱ��
	
	@Column(field="is_delete",type="int(1)",defaultNull=false)
	private Integer isDelete;//ɾ��״̬ 0δɾ�� 1 ɾ��
    
	@Column(field="likes",type="int(10)",defaultNull=false)
	private Integer likes;//��ͬ��
	
	@Column(field="no_like",type="int(10)",defaultNull=true)
	private Integer noLike;//����ͬ��
	
	private Integer commentLevel;
	
	
	private String commentParentId;

	
	
	private Integer commentChildCount;



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public String getId() {
		return Id;
	}



	public void setId(String id) {
		Id = id;
	}



	public String getAuthorId() {
		return authorId;
	}



	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}



	public String getArticleId() {
		return articleId;
	}



	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}



	public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public Date getUpdateTime() {
		return updateTime;
	}



	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}



	public Integer getIsDelete() {
		return isDelete;
	}



	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}



	public Integer getLikes() {
		return likes;
	}



	public void setLikes(Integer likes) {
		this.likes = likes;
	}



	public Integer getNoLike() {
		return noLike;
	}



	public void setNoLike(Integer noLike) {
		this.noLike = noLike;
	}



	public Integer getCommentLevel() {
		return commentLevel;
	}



	public void setCommentLevel(Integer commentLevel) {
		this.commentLevel = commentLevel;
	}



	public String getCommentParentId() {
		return commentParentId;
	}



	public void setCommentParentId(String commentParentId) {
		this.commentParentId = commentParentId;
	}



	public Integer getCommentChildCount() {
		return commentChildCount;
	}



	public void setCommentChildCount(Integer commentChildCount) {
		this.commentChildCount = commentChildCount;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	public String toString() {
		return "commentData [content=" + content + ", Id=" + Id + ", authorId=" + authorId + ", articleId=" + articleId
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", isDelete=" + isDelete + ", likes="
				+ likes + ", noLike=" + noLike + ", commentLevel=" + commentLevel + ", commentParentId="
				+ commentParentId + ", commentChildCount=" + commentChildCount + ", getContent()=" + getContent()
				+ ", getId()=" + getId() + ", getAuthorId()=" + getAuthorId() + ", getArticleId()=" + getArticleId()
				+ ", getCreateTime()=" + getCreateTime() + ", getUpdateTime()=" + getUpdateTime() + ", getIsDelete()="
				+ getIsDelete() + ", getLikes()=" + getLikes() + ", getNoLike()=" + getNoLike() + ", getCommentLevel()="
				+ getCommentLevel() + ", getCommentParentId()=" + getCommentParentId() + ", getCommentChildCount()="
				+ getCommentChildCount() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	
	
	
	
	

	
	
	
}
