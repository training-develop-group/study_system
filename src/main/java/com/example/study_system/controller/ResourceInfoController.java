package com.example.study_system.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.model.ResourceInfo;
import com.example.study_system.model.UserInfo;
import com.example.study_system.util.UserUtil;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/resource")
public class ResourceInfoController extends BaseController {

	/**
	 * 上传资源
	 * @param resourceInfo
	 * @param file
	 * @param ffmpeg_path
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "/resource", method = RequestMethod.POST)
	public ResultDTO uploadResourceInfo(ResourceInfo resourceInfo, MultipartFile file,String ffmpeg_path) throws IllegalStateException, IOException {
		int res = serviceFacade.getResourceService().uploadResourceInfo(resourceInfo, file, "C:\\Users\\CloudEasyServer\\Desktop\\ffmpeg-4.2.1-win64-static\\bin\\ffmpeg.exe");
		if(resourceInfo == null) {
			return noData();
		}
		return success(resourceInfo);  
	}


	/**
	 * 删除资源
	 * @param resId
	 * @return
	 */
	@RequestMapping(value = "/{resId}", method = RequestMethod.DELETE)
	public ResultDTO deleteResourceInfoByResId(@PathVariable("resId") Long resId) {
		int result = serviceFacade.getResourceService().deleteResourceInfoByResId(resId);
		if(resId == null) {
			return validationError();
		}
		return success(result);
	}

	/**
	 * 修改资源名
	 * @param resId
	 * @param resName
	 * @return
	 */
	@RequestMapping(value = "/res-name", method = RequestMethod.POST)
	public ResultDTO<Integer> modifyResourceNameByResId(@RequestParam("resId") Long resId,
														@RequestParam("resName") String resName) {
		int resource = serviceFacade.getResourceService().modifyResourceNameByResId(resId,resName);
		if(resId == null || resName.equals("")) {
			return validationError();
		}
		return success(resource);
	}

	/**
	 * 获取资源详情
	 * @param resId
	 * @return
	 */
	@RequestMapping(value = "/{resId}", method = RequestMethod.GET)
	public ResultDTO getResourceDetailByResId(@PathVariable("resId") Long resId, HttpServletRequest request) {
		UserInfo userInfo = UserUtil.getUser(request);
		System.err.println(userInfo);
		ResourceInfo result = serviceFacade.getResourceService().getResourceDetailByResId(resId);
		if(resId == null) {
			return validationError();
		}
		if (result == null) {
			return noData();
		}
		return success(result);
	}

	/**
	 * 获取资源列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/resources", method = RequestMethod.GET)
	public ResultDTO getResourceList(@RequestParam(value = "pageNum", required = false) Integer pageNum, 
									@RequestParam(value = "pageSize", required = false) Integer pageSize,
									@RequestParam(value = "resName", required = false) String resName,
									@RequestParam(value = "resType", required = false) Integer resType,
									HttpServletRequest request) {
		System.out.println(resType);
		UserInfo userInfo = UserUtil.getUser(request);
		PageInfo<ResourceInfo> resourceList = serviceFacade.getResourceService().getResourceList(pageNum, pageSize, resName, resType);
		if (resourceList == null) {
			return noData();
		}
		return success(resourceList);
	}
	
	/**
	 * 获取资源总数
	 * @return
	 */
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResultDTO getResourceCount() {
		int count = serviceFacade.getResourceService().getResourceListCount();
		return success(count);
	}
	
	/**
	 * 记录视频播放时间
	 * @param resId
	 * @param seconds
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	public ResultDTO recordVideoPlaybackTime(@RequestParam("resId") Long resId,
											@RequestParam("seconds") Long seconds) {
		if(resId == null || seconds == null) {
			return validationError();
		}
		long result = serviceFacade.getJUserVideoLogService().recordVideoPlaybackTime(resId, seconds);
		return success(result);
	}
	
	
	/**
	 * 获取视频播放时间
	 * @param resId
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ResultDTO getVideoPlaybackTime(@RequestParam("resId") Long resId, HttpServletRequest request) {
		UserInfo userInfo = UserUtil.getUser(request);
		long result = serviceFacade.getJUserVideoLogService().getVideoPlaybackTime(resId);
		if(resId == null) {
			return validationError();
		}
		return success(result);
	}
	

}
