package com.cloudsea.photo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;


/**
 * 所有实体对象的基类
 * 如果数据库表没有主键，可以用实体类中所有的属性做成组合主键
 */
@MappedSuperclass
public class BaseEntity implements Serializable{

	private static final long serialVersionUID = 743946443592736083L;

	private Long id;

	private Date createTime;//
	
	private Date updateTime;//

//	private String description;// 

	private String enable;//

	
	@Id
	
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@GenericGenerator(name = "id_increment", strategy = "increment")
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ")
	@SequenceGenerator(name = "SEQ", sequenceName = "SEQ_SYS_BASE")
	
	@Column(name = "ID", unique = true, nullable = false, length = 20)  
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
    @Column(name = "CREATE_TIME", nullable = true, length = 30)
    public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    @Column(name = "UPDATE_TIME", nullable = true, length = 30)
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

//    @Column(name = "Description", nullable = true, length = 100)
//	public String getDescription() {
//		return description;
//	}
//	public void setDescription(String description) {
//		this.description = description;
//	}	
	
    @Column(name = "ENABLE", nullable = true, length = 1)
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	
	

}
