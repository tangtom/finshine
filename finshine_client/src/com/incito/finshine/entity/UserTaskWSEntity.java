package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.List;

import com.android.core.util.StrUtil;

/**
 * 
 * 
 * @author xiaoming
 * 
 */
public class UserTaskWSEntity implements Serializable {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserTaskWSEntity other = (UserTaskWSEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}

	private static final long serialVersionUID = -9059949400503378948L;

	private long id;

	/**
	 * 用户ID
	 */
	private long user_fk;

	/**
	 * 任务ID
	 */
	private Task task;

	/**
	 * 任务类型
	 */
	private TaskType type;

	/**
	 * 难度
	 */
	private Difficulty difficulty;

	/**
	 * 任务状态
	 */
	private TaskStatus status;

	/**
	 * 任务进度中的值
	 */
	private String valueOfInProcess;

	/**
	 * 任务目标值
	 */
	private String valueOfTarget;

	/**
	 * 是否为新任务
	 */
	private short isNew;

	/**
	 * 失效日期
	 */
	private String dateOfExpire;

	/**
	 * 创建时间
	 */
	private long dateOfCreate;

	/**
	 * 奖励
	 */
	private List<Props> bonuses;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUser_fk() {
		return user_fk;
	}

	public void setUser_fk(long user_fk) {
		this.user_fk = user_fk;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public TaskType getType() {
		return type;
	}

	public void setType(TaskType type) {
		this.type = type;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public String getValueOfInProcess() {
		if (StrUtil.isBlank(valueOfInProcess)) {
			return "";
		}
		return valueOfInProcess;
	}

	public void setValueOfInProcess(String valueOfInProcess) {
		this.valueOfInProcess = valueOfInProcess;
	}

	public String getValueOfTarget() {
		if (StrUtil.isBlank(valueOfTarget)) {
			return "";
		}
		return valueOfTarget;
	}

	public void setValueOfTarget(String valueOfTarget) {
		this.valueOfTarget = valueOfTarget;
	}

	public short getIsNew() {
		return isNew;
	}

	public void setIsNew(short isNew) {
		this.isNew = isNew;
	}

	public String getDateOfExpire() {
		if (StrUtil.isBlank(dateOfExpire)) {
			return "";
		}
		return dateOfExpire;
	}

	public void setDateOfExpire(String dateOfExpire) {
		this.dateOfExpire = dateOfExpire;
	}

	public long getDateOfCreate() {
		return dateOfCreate;
	}

	public void setDateOfCreate(long dateOfCreate) {
		this.dateOfCreate = dateOfCreate;
	}

	public List<Props> getBonuses() {
		return bonuses;
	}

	public void setBonuses(List<Props> bonuses) {
		this.bonuses = bonuses;
	}

    private List<String> taskAchievements;

    public List<String> getTaskAchievements() {
		return taskAchievements;
	}

	public void setTaskAchievements(List<String> taskAchievements) {
		this.taskAchievements = taskAchievements;
	}

}
