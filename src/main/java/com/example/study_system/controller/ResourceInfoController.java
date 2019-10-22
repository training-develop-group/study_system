package com.example.study_system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.model.ResourceInfo;


@RestController
@RequestMapping("/resource")
public class ResourceInfoController extends BaseController {

	// 上传资源
	@RequestMapping(value = "/resource", method = RequestMethod.POST)
	public ResultDTO AddResourceInfo(@RequestBody ResourceInfo resourceInfo) {
		int result = serviceFacade.getResourceService().insert(resourceInfo);
		return success(result);
	}

	// 删除资源
	@RequestMapping(value = "/{resId}", method = RequestMethod.DELETE)
	public ResultDTO deleteResourceInfo(@PathVariable("resId") Long resId) {
		int result = serviceFacade.getResourceService().deleteByPrimaryKey(resId);
		return success(result);
	}

	// 修改资源名
	@RequestMapping(value = "/res-name", method = RequestMethod.POST)
	public int modifyResourceName(@RequestParam(value = "resId", required = true) Long resId) {
		int resource = serviceFacade.getResourceService().updateByPrimaryKey(resId);
		return resource;
	}

	// 获取资源详情
	@RequestMapping(value = "/{resId}", method = RequestMethod.GET)
	public ResultDTO getResourceInfoDetails(@PathVariable("resId") String resId) {
		ResourceInfo result = serviceFacade.getResourceService().selectByPrimaryKey(resId);
		if (result == null) {
			return noData();
		}
		return success(result);
	}

	// 获取资源列表
	@RequestMapping(value = "/resources", method = RequestMethod.GET)
	public ResultDTO getResourceList() {
		List<ResourceInfo> resource = serviceFacade.getResourceService().selectList();
//		if (resource == null) {
//			return noData();
//		}
		return success(resource);
	}
	
	//获取资源总数

}
