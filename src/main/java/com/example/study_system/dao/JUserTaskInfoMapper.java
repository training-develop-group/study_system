<<<<<<< HEAD
package com.example.study_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.study_system.model.JUserTaskInfo;
@Mapper
public interface JUserTaskInfoMapper {
	List<JUserTaskInfo> selectByTaskIdusers(@Param("taskId")Long taskId);
}
=======
package com.example.study_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.study_system.model.JUserTaskInfo;
@Mapper
public interface JUserTaskInfoMapper {
	List<JUserTaskInfo> selectByTaskIdusers(@Param("taskId")Long taskId);
}
>>>>>>> caa8072d35903f32aea5d24014a89c312b1470ab
