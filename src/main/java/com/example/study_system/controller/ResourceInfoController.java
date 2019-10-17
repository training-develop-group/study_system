package com.example.study_system.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.model.ResourceInfo;

@RestController
@RequestMapping("/resource")
public class ResourceInfoController extends BaseController{
	
	@RequestMapping(value = "/{resId}", method = RequestMethod.GET)
	public ResultDTO getResourceDetails(@PathVariable("resId") Integer resId) {
		int result = serviceFacade.getResourceService().selectResource(resId);
		return success(result);
	}
	
	@RequestMapping(value = "/res-name", method = RequestMethod.POST)
	public ResultDTO modifyResourceName(@RequestParam("resName") String resName) {
		ResourceInfo resource = serviceFacade.getResourceService().modifyResourceByResName(resName);
		return success(resource);
	}
	
	@RequestMapping(value = "/{resId}", method = RequestMethod.DELETE)
	public ResultDTO deleteResource(@PathVariable("resId") Integer resId) {
		int result = serviceFacade.getResourceService().deleteResourceByresId(resId);
		return success(result);
	}
	
}
