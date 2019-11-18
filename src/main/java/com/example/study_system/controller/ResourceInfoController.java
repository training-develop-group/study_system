package com.example.study_system.controller;

import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.emun.ResultEmun;
import com.example.study_system.model.JUserVideoLog;
import com.example.study_system.model.ResourceInfo;
import com.example.study_system.model.UserInfo;
import com.example.study_system.util.UserUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/resource")
public class ResourceInfoController extends BaseController {

	/**
	 * 上传资源
	 *
	 * @param resourceInfo
	 * @param file
	 * @param ffmpeg_path
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "/resource", method = RequestMethod.POST)
	public ResultDTO uploadResourceInfo(HttpServletRequest request, ResourceInfo resourceInfo, MultipartFile file) throws IllegalStateException, IOException {

		System.err.println(resourceInfo.getResExt());
		final String ffmpeg_path = "C:\\Users\\Admin\\Desktop\\ffmpeg-4.2.1-win64-static\\bin\\ffmpeg.exe";
		if (resourceInfo == null || file == null) {
			return validationError();
		}
		UserInfo userInfo = UserUtil.getUser(request);
		if (userInfo == null) {
			return noData();
		}
		resourceInfo.setcUser(userInfo.getUserName());
		int res = serviceFacade.getResourceService().uploadResourceInfo(resourceInfo, file, ffmpeg_path);
		if (res == -7) {
			return new ResultDTO<>(ResultEmun.UPLOAD_FILE_ISEMPTY,file.getOriginalFilename());
		}
		
		if (res == -8) {
			return new ResultDTO<>(ResultEmun.THIS_FILE_FORMAT_IS_NOT_SUPPORTED,file.getOriginalFilename());
		}
		
		return success(resourceInfo);
	}

	/**
	 * 删除资源
	 *
	 * @param resId
	 * @return
	 */
	@RequestMapping(value = "/{resId}", method = RequestMethod.DELETE)
	public ResultDTO deleteResourceInfoByResId(@PathVariable("resId") Long resId) {
		if (resId == null) {
			return validationError();
		}
		int result = serviceFacade.getResourceService().deleteResourceInfoByResId(resId);
		return success(result);
	}

	/**
	 * 修改资源名
	 *
	 * @param resId
	 * @param resName
	 * @return
	 */
	@RequestMapping(value = "/res-name", method = RequestMethod.POST)
	public ResultDTO<Integer> modifyResourceNameByResId(@RequestParam("resId") Long resId,
			@RequestParam("resName") String resName) {
		int resource = serviceFacade.getResourceService().modifyResourceNameByResId(resId, resName);
		if (resId == null || StringUtils.isEmpty(resName)) {
			return validationError();
		}
		return success(resource);
	}

	/**
	 * 获取资源详情
	 *
	 * @param resId
	 * @return
	 */
	@RequestMapping(value = "/{resId}", method = RequestMethod.GET)
	public ResultDTO getResourceDetailByResId(@PathVariable("resId") Long resId, HttpServletRequest request) {
		if (resId == null) {
			return validationError();
		}
		UserInfo userInfo = UserUtil.getUser(request);
		System.err.println(userInfo);
		ResourceInfo result = serviceFacade.getResourceService().getResourceDetailByResId(resId);
		if (result == null) {
			return noData();
		}
		return success(result);
	}

	/**
	 * 获取资源列表
	 *
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/resources", method = RequestMethod.GET)
	public ResultDTO getResourceList(@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "resName", required = false) String resName,
			@RequestParam(value = "resType", required = false) Integer resType, HttpServletRequest request) {
		UserInfo userInfo = UserUtil.getUser(request);
		PageInfo<ResourceInfo> resourceList = serviceFacade.getResourceService().getResourceList(pageNum, pageSize,
				resName, resType);
		if (resourceList == null) {
			return noData();
		}
		return success(resourceList);
	}

	/**
	 * 获取资源总数
	 *
	 * @return
	 */
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResultDTO getResourceCount() {
		int count = serviceFacade.getResourceService().getResourceListCount();
		return success(count);
	}

	/**
	 * 记录视频播放时间
	 *
	 * @param resId
	 * @param seconds
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	public ResultDTO recordVideoPlaybackTime(@RequestParam("resId") Long resId, @RequestParam("seconds") Long seconds) {
		if (resId == null || seconds == null) {
			return validationError();
		}
		long result = serviceFacade.getJUserVideoLogService().recordVideoPlaybackTime(resId, seconds);
		return success(result);
	}

	/**
	 * 获取视频播放时间
	 *
	 * @param resId
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ResultDTO getVideoPlaybackTime(@RequestParam("resId") Long resId, HttpServletRequest request) {
		if (resId == null) {
			return validationError();
		}
		UserInfo userInfo = UserUtil.getUser(request);
		if (userInfo == null) {
			return noData();
		}
		long result = serviceFacade.getJUserVideoLogService().getVideoPlaybackTime(resId);

		return success(result);
	}

	@RequestMapping(value = "/download")
	public ResponseEntity<Object> downloadFile(@RequestParam(value = "resName") String resName,
			@RequestParam(value = "path") String path) throws FileNotFoundException, UnsupportedEncodingException {

		String resFileName = resName + path.substring(path.lastIndexOf(".")); // 截取path的后缀名再用resName和它拼接上成为resName.xxx格式
		System.err.println(resName);
		System.err.println(resFileName);
		System.err.println(path);
		String filePath = "F:/study/files/";
		String fileName = filePath + path; // 用固定路径 + 文件名 = 文件地址
		File file = new File(fileName); // 创建file对象
		InputStreamResource resource = new InputStreamResource(new FileInputStream((file)));

		HttpHeaders headers = new HttpHeaders();
//	    headers.add("Content-Disposition",String.format("attachment;filename=\"%s\"",file.getName()));
		System.err.println(file.getName());
		headers.add("Content-Disposition", String.format(
				"attachment;filename=" + new String(resFileName.getBytes("gb2312"), "ISO8859-1"), file.getName()));
		headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/text")).body(resource);
		return responseEntity;
	}
}
