package com.alekseyk99.spring.model;


import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "notes")
public class Note {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name="subject")
	private String subject;

	@Column(name="text")
	private String text;

	@Column(name="createTime", updatable = false)
	private Timestamp createTime;
	
	@Column(name="modifyTime")
	private Timestamp modifyTime;
	
	@PrePersist
	void onCreate() {
		Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
		setCreateTime(now);
		setModifyTime(now);
	}

	@PreUpdate
	void onUpdate() {
		setModifyTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
	}

	public Note() {
	}

	public Integer getId() {
		return id;
	}

	public Note setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getSubject() {
		return subject;
	}

	public Note setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public String getText() {
		return text;
	}

	public Note setText(String text) {
		this.text = text;
		return this;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public Note setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
		return this;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public Note setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
		return this;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", subject=" + subject + ", text=" + text + ", createTime=" + createTime
				+ ", modifyTime=" + modifyTime + "]";
	}
}
