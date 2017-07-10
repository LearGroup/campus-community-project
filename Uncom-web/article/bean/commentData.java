package bean;

import java.io.Serializable;

import annotation.Column;
import annotation.Table;


@Table(tableName="t_article_comment")
public class commentData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5267335947440430926L;

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getLike() {
		return likes;
	}

	public void setLike(Integer like) {
		this.likes = like;
	}

	public Integer getNoLike() {
		return noLike;
	}

	public void setNoLike(Integer noLike) {
		this.noLike = noLike;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(field="content",type="varchar(200)",defaultNull=true)
	private String content ;
	
	
	@Column(field="id",type="varchar(100)",defaultNull=false,primarykey=true)
	private String Id;//����id
	
	@Column(field="author_id",type="varchar(100)",defaultNull=false)
	private String authorId;//����id
	
	@Column(field="article_id",type="varchar(100)",defaultNull=false)
	private String  articleId;//����Id
	
	@Column(field="create_time",type="datetime",defaultNull=false)
	private String createTime;//���۴���ʱ��
	
	@Column(field="update_time",type="datetime",defaultNull=false)
	private String updateTime;//������ʱ��
	
	@Column(field="is_delete",type="int(1)",defaultNull=false)
	private String isDelete;//ɾ��״̬ 0δɾ�� 1 ɾ��
    
	@Column(field="likes",type="int(10)",defaultNull=false)
	private Integer likes;//��ͬ��
	
	@Column(field="no_like",type="int(10)",defaultNull=true)
	private Integer noLike;//����ͬ��

	@Override
	public String toString() {
		return "commentData [content=" + content + ", Id=" + Id + ", authorId=" + authorId + ", articleId=" + articleId
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", isDelete=" + isDelete + ", like="
				+ likes + ", noLike=" + noLike + ", getContent()=" + getContent() + ", getId()=" + getId()
				+ ", getAuthorId()=" + getAuthorId() + ", getArticleId()=" + getArticleId() + ", getCreateTime()="
				+ getCreateTime() + ", getUpdateTime()=" + getUpdateTime() + ", getIsDelete()=" + getIsDelete()
				+ ", getLike()=" + getLike() + ", getNoLike()=" + getNoLike() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
	
}
