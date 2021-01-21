package com.alekseyk99.spring.dto;


import java.sql.Timestamp;

public class NoteDto {
	
	private Integer id;
	private String subject;
	private String text;
	private Timestamp createTime;
	private Timestamp modifyTime;
	
	public NoteDto() {
	}

	public Integer getId() {
		return id;
	}

	public NoteDto setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getSubject() {
		return subject;
	}

	public NoteDto setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public String getText() {
		return text;
	}

	public NoteDto setText(String text) {
		this.text = text;
		return this;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public NoteDto setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
		return this;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public NoteDto setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
		return this;
	}

	@Override
	public String toString() {
		return "NoteDto [id=" + id + ", subject=" + subject + ", text=" + text + ", createTime=" + createTime
				+ ", modifyTime=" + modifyTime + "]";
	}
}
