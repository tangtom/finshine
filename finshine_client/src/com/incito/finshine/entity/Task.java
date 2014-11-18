package com.incito.finshine.entity;

import java.io.Serializable;

import com.android.core.util.StrUtil;

/**
 * 
 * @author xiaoming
 * 
 */
public class Task implements Serializable {

	private static final long serialVersionUID = 7247662471172451033L;

	private long id = -1;

	/**
	 * 任务名称
	 */
	private String name;

	/**
	 * 任务描述
	 */
	private String memo;

	/**
	 * 任务类型
	 */
	private int type_fk = -1;

	/**
	 * 类型名称
	 * 
	 * @transient
	 */
	private String typeName;

	/**
	 * 任务难易度
	 */
	private int difficulty_fk = -1;

	/**
	 * 困难名称
	 * 
	 * @transient
	 */
	private String difficultyName;

	/**
	 * 生效时间
	 */
	private long dateOfEffect;

	/**
	 * 失效时间
	 */
	private long dateOfExpire;

	/**
	 * 是否启用
	 */
	private short isEnabled = 0;

	/**
	 * 平均耗时
	 */
	private float averageTime = -1;

	/**
	 * 时间单位
	 */
	private String timeUnit;

	/**
	 * 创建人员ID
	 */
	private long creator_fk;

	/**
	 * 更新人员ID
	 */
	private long updater_fk;

	/**
	 * 创建时间
	 */
	private long dateOfCreate;

	/**
	 * 更新时间
	 */
	private long dateOfUpdate;

	/**
	 * 是否数据有效
	 */
	private short isValid = 1;

	/**
	 * 完成条件
	 * 
	 * @Transient
	 */
	private String essentials;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		if (StrUtil.isBlank(name)) {
			return "";
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		if (StrUtil.isBlank(memo)) {
			return "";
		}
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getType_fk() {
		return type_fk;
	}

	public void setType_fk(int type_fk) {
		this.type_fk = type_fk;
	}

	public String getTypeName() {
		if (StrUtil.isBlank(typeName)) {
			return "";
		}
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getDifficulty_fk() {
		return difficulty_fk;
	}

	public void setDifficulty_fk(int difficulty_fk) {
		this.difficulty_fk = difficulty_fk;
	}

	public String getDifficultyName() {
		if (StrUtil.isBlank(difficultyName)) {
			return "";
		}
		return difficultyName;
	}

	public void setDifficultyName(String difficultyName) {
		this.difficultyName = difficultyName;
	}

	public long getDateOfEffect() {
		return dateOfEffect;
	}

	public void setDateOfEffect(long dateOfEffect) {
		this.dateOfEffect = dateOfEffect;
	}

	public long getDateOfExpire() {
		return dateOfExpire;
	}

	public void setDateOfExpire(long dateOfExpire) {
		this.dateOfExpire = dateOfExpire;
	}

	public short getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(short isEnabled) {
		this.isEnabled = isEnabled;
	}

	public float getAverageTime() {
		return averageTime;
	}

	public void setAverageTime(float averageTime) {
		this.averageTime = averageTime;
	}

	public String getTimeUnit() {
		if (StrUtil.isBlank(timeUnit)) {
			return "";
		}
		return timeUnit;
	}

	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}

	public long getCreator_fk() {
		return creator_fk;
	}

	public void setCreator_fk(long creator_fk) {
		this.creator_fk = creator_fk;
	}

	public long getUpdater_fk() {
		return updater_fk;
	}

	public void setUpdater_fk(long updater_fk) {
		this.updater_fk = updater_fk;
	}

	public long getDateOfCreate() {
		return dateOfCreate;
	}

	public void setDateOfCreate(long dateOfCreate) {
		this.dateOfCreate = dateOfCreate;
	}

	public long getDateOfUpdate() {
		return dateOfUpdate;
	}

	public void setDateOfUpdate(long dateOfUpdate) {
		this.dateOfUpdate = dateOfUpdate;
	}

	public short getIsValid() {
		return isValid;
	}

	public void setIsValid(short isValid) {
		this.isValid = isValid;
	}

	public String getEssentials() {
		if (StrUtil.isBlank(essentials)) {
			return "";
		}
		return essentials;
	}

	public void setEssentials(String essentials) {
		this.essentials = essentials;
	}

}
