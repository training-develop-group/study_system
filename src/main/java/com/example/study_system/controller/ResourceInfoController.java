package com.example.study_system.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.emun.ResultEmun;
import com.example.study_system.model.ResourceInfo;
import com.example.study_system.model.UserInfo;
import com.example.study_system.util.UserUtil;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/resource")
public class ResourceInfoController extends BaseController {
	String userId = "";
	String userName = "";

	/**
	 * 	上传资源
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
		final String ffmpeg_path = "C:\\Users\\Admin\\Desktop\\ffmpeg-4.2.1-win64-static\\bin\\ffmpeg.exe";
		if (resourceInfo == null || file == null) {
			return validationError();
		}
		UserInfo userInfo = UserUtil.getUser(request);
		if (userInfo == null) {
			return noData();
		}
		resourceInfo.setcUser(userInfo.getUserName());
		String remark = userName + "上传资源";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "ResourceInfo:" + resourceInfo + "MultipartFile:" + file + "ffmpeg_path:" + ffmpeg_path;
		try {
			int res = serviceFacade.getResourceService().uploadResourceInfo(resourceInfo, file, ffmpeg_path);
			if (res == -7) {
				return new ResultDTO<>(ResultEmun.UPLOAD_FILE_ISEMPTY,file.getOriginalFilename());
			}

			if (res == -8) {
				return new ResultDTO<>(ResultEmun.THIS_FILE_FORMAT_IS_NOT_SUPPORTED,file.getOriginalFilename());
			}
			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
			return success(resourceInfo);
        } catch (Exception e){
        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
        	int res = serviceFacade.getResourceService().uploadResourceInfo(resourceInfo, file, ffmpeg_path);
    		if (res == -7) {
    			return new ResultDTO<>(ResultEmun.UPLOAD_FILE_ISEMPTY,file.getOriginalFilename());
    		}

    		if (res == -8) {
    			return new ResultDTO<>(ResultEmun.THIS_FILE_FORMAT_IS_NOT_SUPPORTED,file.getOriginalFilename());
    		}
			return success(resourceInfo);
        }
	}

	/**
	 * 	删除资源
	 *
	 * @param resId
	 * @return
	 */
	@RequestMapping(value = "/{resId}", method = RequestMethod.DELETE)
	public ResultDTO deleteResourceInfoByResId(HttpServletRequest request,@PathVariable("resId") Long resId) {
		if (resId == null) {
			return validationError();
		}
		String remark = userName + "删除资源";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "resId:" + resId;
		try {
			int result = serviceFacade.getResourceService().deleteResourceInfoByResId(resId);
			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
			return success(result);
        } catch (Exception e){
        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
        	int result = serviceFacade.getResourceService().deleteResourceInfoByResId(resId);
			return success(result);
        }
	}

	/**
	 * 	修改资源名
	 *
	 * @param resId
	 * @param resName
	 * @return
	 */
	@RequestMapping(value = "/res-name", method = RequestMethod.POST)
	public ResultDTO<Integer> modifyResourceNameByResId(HttpServletRequest request,@RequestParam("resId") Long resId,
			@RequestParam("resName") String resName) {
		if (resId == null || StringUtils.isEmpty(resName)) {
			return validationError();
		}
		String remark = userName + "修改资源名";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "resId:" + resId + "resName:" + resName;
		try {
			int resource = serviceFacade.getResourceService().modifyResourceNameByResId(resId, resName);
			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
			return success(resource);
        } catch (Exception e){
        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
        	int resource = serviceFacade.getResourceService().modifyResourceNameByResId(resId, resName);
			return success(resource);
        }
	}

	/**
	 * 	获取资源详情
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
		String remark = userName + "获取资源详情";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "resId:" + resId;
		try {
			ResourceInfo result = serviceFacade.getResourceService().getResourceDetailByResId(resId);
			if (result == null) {
				return noData();
			}
			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
			return success(result);
        } catch (Exception e){
        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
        	ResourceInfo result = serviceFacade.getResourceService().getResourceDetailByResId(resId);
    		if (result == null) {
    			return noData();
    		}
			return success(result);
        }
	}

	/**
	 *	 获取资源列表
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
		userId = UserUtil.getUser(request).getUserId();
		userName = UserUtil.getUser(request).getUserName();
		UserInfo userInfo = UserUtil.getUser(request);
		String remark = userName + "获取资源列表";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "pageNum:" + pageNum + "pageSize:" + pageSize + "resName:" + resName + "resType:" + resType;
		try {
			PageInfo<ResourceInfo> resourceList = serviceFacade.getResourceService().getResourceList(pageNum, pageSize,
					resName, resType);
			if (resourceList == null) {
				return noData();
			}
			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
			return success(resourceList);
        } catch (Exception e){
        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
        	PageInfo<ResourceInfo> resourceList = serviceFacade.getResourceService().getResourceList(pageNum, pageSize,
    				resName, resType);
    		if (resourceList == null) {
    			return noData();
    		}
			return success(resourceList);
        }
	}

	/**
	 * 	获取资源总数
	 *
	 * @return
	 */
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResultDTO getResourceCount(HttpServletRequest request) {
		String remark = userName + "获取资源总数";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "";
		try {
			int count = serviceFacade.getResourceService().getResourceListCount();
			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
			return success(count);
        } catch (Exception e){
        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
        	int count = serviceFacade.getResourceService().getResourceListCount();
			return success(count);
        }
	}

	/**
	 * 	记录视频播放时间
	 *
	 * @param resId
	 * @param seconds
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	public ResultDTO recordVideoPlaybackTime(HttpServletRequest request,@RequestParam("resId") Long resId, @RequestParam("seconds") Long seconds) {
		if (resId == null || seconds == null) {
			return validationError();
		}
		String remark = userName + "记录视频播放时间";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "resId:" + resId + "seconds:" + seconds;
		try {
			long result = serviceFacade.getJUserVideoLogService().recordVideoPlaybackTime(resId, seconds);
			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
			return success(result);
        } catch (Exception e){
        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
        	long result = serviceFacade.getJUserVideoLogService().recordVideoPlaybackTime(resId, seconds);
			return success(result);
        }
	}

	/**
	 * 	获取视频播放时间
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
		String remark = userName + "获取视频播放时间";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "resId:" + resId;
		try {
			long result = serviceFacade.getJUserVideoLogService().getVideoPlaybackTime(resId);
			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
			return success(result);
        } catch (Exception e){
        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
        	long result = serviceFacade.getJUserVideoLogService().getVideoPlaybackTime(resId);
			return success(result);
        }
	}

	/**
	 * 下载
	 * 
	 * @param resName
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/download")
	public ResponseEntity<Object> downloadFile(HttpServletRequest request,@RequestParam(value = "resName") String resName,
			@RequestParam(value = "path") String path) throws FileNotFoundException, UnsupportedEncodingException {

		// 截取path的后缀名再用resName和它拼接上成为resName.xxx格式
		String resFileName = resName + path.substring(path.lastIndexOf("."));
		String filePath = "F:/study/files/";
		// 用固定路径 + 文件名 = 文件地址
		String fileName = filePath + path;
		// 创建file对象
		File file = new File(fileName);
		InputStreamResource resource = new InputStreamResource(new FileInputStream((file)));

		HttpHeaders headers = new HttpHeaders();
//	    headers.add("Content-Disposition",String.format("attachment;filename=\"%s\"",file.getName()));
		headers.add("Content-Disposition", String.format(
				"attachment;filename=" + new String(resFileName.getBytes("gb2312"), "ISO8859-1"), file.getName()));
		headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		
		String remark = userName + "下载了" + path;
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "resName:" + resName + "path:" + path;
		try {
			ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/text")).body(resource);
			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
			return responseEntity;
        } catch (Exception e){
        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
        	ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length())
    				.contentType(MediaType.parseMediaType("application/text")).body(resource);
			return responseEntity;
        }
	}
}
