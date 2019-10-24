package com.example.study_system.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Request;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.model.JUserVideoLog;
import com.example.study_system.model.ResourceInfo;

@RestController
@RequestMapping("/resource")
public class ResourceInfoController extends BaseController {

	/**
	 * 上传资源
	 * @param resourceInfo
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
//	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/resource", method = RequestMethod.POST)
//	@ResponseBody
	public ResourceInfo uploadResourceInfo(MultipartFile file,HttpServletRequest request) throws IllegalStateException, IOException {
		String oriName = "";	//原名称
		String desFilePath = "";	//系统生成的名称
		Integer resType =null;
		ResourceInfo result = new ResourceInfo();
		Map<String, String> dataMap = new HashMap<>();
		ResourceInfo imgResult = new ResourceInfo();
		if(file != null){
			oriName = file.getOriginalFilename();	//获取原文件名
			long resSize = Math.round(file.getSize()/1024);	//获取文件大小
			String fileType = file.getContentType();	//获取文件类型
			String subFileType = fileType.substring(0, fileType.indexOf("/"));	//截取文件类型
			//判断文件类型
			if(subFileType.equals("video")) {
				subFileType = "2";
				resType = Integer.valueOf(subFileType);
			}
			
			String extName = oriName.substring(oriName.lastIndexOf("."));	//获取源文件后缀名		
			String uuid = UUID.randomUUID().toString().replaceAll("-","");	//生成UUID
			String newName = uuid + extName;	//UUID生成的新名字+后缀名
			try {
				String filePath = "C:\\study\\files\\";	//获取要保存的路径文件夹
				//保存文件
				desFilePath = filePath + newName;	//保存文件路径
			    File desFile = new File(desFilePath);	
			    file.transferTo(desFile);
			    logger.info(desFilePath);
			    long resId = 123;
			    result.setResName(uuid);	//添加文件名
			    result.setResType(resType);	//添加文件类型
			    result.setPath(desFilePath);	//添加保存文件路径
			    result.setImgPath("123");
			    result.setResExt(extName);	//添加后缀名
			    result.setResSize(resSize);	//添加文件大小
			    result.setStatus(1);
//			    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//			    System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
			    Date date = new Date(4000);
			    result.setcTime(date);
			    result.setmTime(date);
			    result.setcUser("123");
			    result.setmUser("456");
			    int res = serviceFacade.getResourceService().uploadResourceInfo(result);
			    System.out.println(resType);
			} catch (IllegalStateException e){
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		    return result;  
	}


	/**
	 * 删除资源
	 * @param resId
	 * @return
	 */
	@RequestMapping(value = "/{resId}", method = RequestMethod.DELETE)
	public ResultDTO deleteResourceInfoByResId(@PathVariable("resId") Long resId) {
		int result = serviceFacade.getResourceService().deleteResourceInfoByResId(resId);
		return success(result);
	}

	/**
	 * 修改资源名
	 * @param resId
	 * @return
	 */
	@RequestMapping(value = "/res-name", method = RequestMethod.POST)
	public int modifyResourceNameByResId(@RequestParam(value = "resId", required = true) Long resId) {
		int resource = serviceFacade.getResourceService().modifyResourceNameByResId(resId);
		return resource;
	}

	/**
	 * 获取资源详情
	 * @param resId
	 * @return
	 */
	@RequestMapping(value = "/{resId}", method = RequestMethod.GET)
	public ResultDTO getResourceDetailsByResId(@PathVariable("resId") String resId) {
		ResourceInfo result = serviceFacade.getResourceService().getResourceDetailsByResId(resId);
		if (result == null) {
			return noData();
		}
		return success(result);
	}

	/**
	 * 获取资源列表
	 * @return
	 */
	@RequestMapping(value = "/resources", method = RequestMethod.GET)
	public ResultDTO getResourceList() {
		List<ResourceInfo> resource = serviceFacade.getResourceService().getResourceList();
		if (resource == null) {
			return noData();
		}
		return success(resource);
	}
	
	/**
	 * 获取资源总数
	 * @return
	 */
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResultDTO getResourceCount() {
			int resource = serviceFacade.getResourceService().getResourceListCount();
		System.out.println(resource);
		return success(resource);
	}
	
	
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	public ResultDTO getVideoPlaybackTime(@RequestBody JUserVideoLog seconds) {
		int result = serviceFacade.getJUserVideoLogService().getVideoPlaybackTime(seconds);
		return success(result);
	}
	
	
	
//	@RequestMapping(value = "/view", method = RequestMethod.GET)
//	public ResultDTO getVideoPlaybackRecord() {
//		
//	}
	

}
