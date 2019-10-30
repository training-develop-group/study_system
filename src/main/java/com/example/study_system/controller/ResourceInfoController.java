package com.example.study_system.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(value = "/resource", method = RequestMethod.POST)
//	@ResponseBody
	
	public ResultDTO uploadResourceInfo(ResourceInfo resourceInfo, MultipartFile file,String ffmpeg_path) throws IllegalStateException, IOException {
//		String oriName = "";	//原名称
//		String desFilePath = "";	//系统生成的名称
//		Integer resType = 1;
//		ResourceInfo result = new ResourceInfo();
//		if(file != null){
//			oriName = file.getOriginalFilename();	//获取原文件名
//			long resSize = Math.round(file.getSize()/1024);	//获取文件大小
//			String fileType = file.getContentType();	//获取文件类型
//			String subFileType = fileType.substring(0, fileType.indexOf("/"));	//截取文件类型
//			//判断文件类型
//			if(subFileType.equals("video")) {
//				subFileType = "2";
//				resType = Integer.valueOf(subFileType);
//			}
//			
//			String extName = oriName.substring(oriName.lastIndexOf("."));	//获取源文件后缀名		
//			String uuid = UUID.randomUUID().toString().replaceAll("-","");	//生成UUID
//			String newName = uuid + extName;	//UUID生成的新名字+后缀名
//			MultipartConfigFactory factory = new MultipartConfigFactory();
//	        //文件最大  
////	        factory.setMaxFileSize("100MB"); //KB,MB
//	        /// 设置总上传数据总大小  
////	        factory.setMaxRequestSize("102400KB");  
////	        return factory.createMultipartConfig();  
//
//			try {
//				String filePath = "C:\\study\\files\\";	//获取要保存的路径文件夹
//				//保存文件
//				desFilePath = filePath + newName;	//保存文件路径
//			    File desFile = new File(desFilePath);
//			    if(desFile.createNewFile()) {
//			    	desFile.setExecutable(true);
//				    desFile.setReadable(true);
//				    desFile.setWritable(true);
//				    System.out.println("is execute allow : " + desFile.canExecute());
//			    }
//			    file.transferTo(desFile);
//			    logger.info("保存文件路径:" + desFilePath);
//			    result.setResName(uuid);	//添加文件名
//			    result.setResType(resType);	//添加文件类型
//			    result.setPath(desFilePath);	//添加保存文件路径
//			    result.setImgPath("123");
//			    result.setResExt(extName);	//添加后缀名
//			    result.setResSize(resSize);	//添加文件大小
//			    result.setStatus(1);
//			    Date date = new Date(4000);
//			    result.setcTime(date);
//			    result.setmTime(date);
//			    result.setcUser("123");
//			    result.setmUser("456");
//			    int res = serviceFacade.getResourceService().uploadResourceInfo(result,desFilePath,"C:\\\\Users\\\\CloudEasyServer\\\\Desktop\\\\ffmpeg-4.2.1-win64-static\\\\bin\\\\ffmpeg.exe");
//			    System.out.println("文件类型"+"resType:" + resType);
//			} catch (IllegalStateException e){
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		int res = serviceFacade.getResourceService().uploadResourceInfo(resourceInfo, file, "C:\\Users\\CloudEasyServer\\Desktop\\ffmpeg-4.2.1-win64-static\\bin\\ffmpeg.exe");
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
		if(resId != 0) {
			return success(result);
		} else {
			return success(resId);
		}
		
	}

	/**
	 * 修改资源名
	 * @param resId
	 * @return
	 */
	@RequestMapping(value = "/res-name", method = RequestMethod.POST)
	public int modifyResourceNameByResId(@RequestParam("resId") Long resId,
										@RequestParam("resName") String resName) {
		System.out.println(resId);
		int resource = serviceFacade.getResourceService().modifyResourceNameByResId(resId,resName);
		return resource;
	}

	/**
	 * 获取资源详情
	 * @param resId
	 * @return
	 */
	@RequestMapping(value = "/{resId}", method = RequestMethod.GET)
	public ResultDTO getResourceDetailByResId(@PathVariable("resId") Long resId) {
		ResourceInfo result = serviceFacade.getResourceService().getResourceDetailByResId(resId);
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
		List<ResourceInfo> res = serviceFacade.getResourceService().getResourceList();
		if (res == null) {
			return noData();
		}
		return success(res);
	}
	
	/**
	 * 获取资源总数
	 * @return
	 */
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResultDTO getResourceCount() {
		int count = serviceFacade.getResourceService().getResourceListCount();
		System.out.println(count);
		return success(count);
	}
	
	/**
	 * 记录视频播放时间
	 * @param seconds
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	public ResultDTO recordVideoPlaybackTime(@RequestParam("seconds") Long seconds) {
		long result = serviceFacade.getJUserVideoLogService().recordVideoPlaybackTime(seconds);
		return success(result);
	}
	
	
	/**
	 * 获取视频播放时间
	 * @param ref
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ResultDTO getVideoPlaybackTime(@RequestParam("ref") Long ref) {
		Long result = serviceFacade.getJUserVideoLogService().getVideoPlaybackTime(ref);
		return success(result);
	}
	

}
