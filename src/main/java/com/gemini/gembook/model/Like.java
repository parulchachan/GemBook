package com.gemini.gembook.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table( name="likes")
public class Like{
	
	@EmbeddedId
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private LikeIdentity likeIdentity;
	
	@Column(name="like_time")
	private long likeTime;

	@Column(name="like_flag")
	private String likeFlag;
	
	public Like() {}
	
	public Like(LikeIdentity likeIdentity,String likeFlag) {
		this.likeIdentity = likeIdentity;
		Date date=new Date();
		this.likeTime = date.getTime();
		this.likeFlag = likeFlag;
	}

	public LikeIdentity getLikeIdentity() {
		return likeIdentity;
	}

	public void setLikeIdentity(LikeIdentity likeIdentity) {
		this.likeIdentity = likeIdentity;
	}

	public long getLikeTime() {
		return likeTime;
	}

	public void setLikeTime(long likeTime) {
		this.likeTime = likeTime;
	}

	public String getLikeFlag() {
		return likeFlag;
	}

	public void setLikeFlag(String likeFlag) {
		this.likeFlag = likeFlag;
	}
}
