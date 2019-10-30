package com.example.study_system.service.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.study_system.model.ResourceInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IResourceService;
@Service
public class ResourceServiceImpl extends BaseService implements IResourceService {

	/**
	 * 上传资源
	 * @param resourceInfo
	 * @return
	 */
	@Override
	public int uploadResourceInfo(ResourceInfo resourceInfo, MultipartFile file, String ffmpeg_path) {
		String oriName = "";	//原名称
		String desFilePath = "";	//系统生成的名称
		Integer resType = 1;
		
//		ResourceInfo result = new ResourceInfo();
		if(file != null){
			oriName = file.getOriginalFilename();	//获取原文件名
			long resSize = Math.round(file.getSize()/1024);	//获取文件大小
			String fileType = file.getContentType();	//获取文件类型
			String subFileType = fileType.substring(0, fileType.indexOf("/"));	//截取文件类型
			System.err.println(fileType);
			System.err.println(subFileType);
			//判断文件类型
			if(subFileType.equals("video")) {
				subFileType = "2";
				resType = Integer.valueOf(subFileType);
			}
			String extName = oriName.substring(oriName.lastIndexOf("."));	//获取源文件后缀名		
			String uuid = UUID.randomUUID().toString().replaceAll("-","");	//生成UUID
			String newName = uuid + extName;	//UUID生成的新名字+后缀名
			MultipartConfigFactory factory = new MultipartConfigFactory(); 
			try {
				String filePath = "C:\\study\\files\\";	//获取要保存的路径文件夹
				//保存文件
				desFilePath = filePath + newName;	//保存文件路径
			    File desFile = new File(desFilePath);
			    if(desFile.createNewFile()) {
			    	desFile.setExecutable(true);
				    desFile.setReadable(true);
				    desFile.setWritable(true);
				    System.out.println("is execute allow : " + desFile.canExecute());
			    }
			    file.transferTo(desFile);
			    logger.info("保存文件路径:" + desFilePath);
			    resourceInfo.setResName(uuid);	//添加文件名
			    resourceInfo.setResType(resType);	//添加文件类型
			    resourceInfo.setPath(desFilePath);	//添加保存文件路径
			    
			    resourceInfo.setImgPath("123");
			    resourceInfo.setResExt(extName);	//添加后缀名
			    resourceInfo.setResSize(resSize);	//添加文件大小
			    resourceInfo.setStatus(1);
			    Date date = new Date(4000);
			    resourceInfo.setcTime(date);
			    resourceInfo.setmTime(date);
			    resourceInfo.setcUser("123");
			    resourceInfo.setmUser("456");
//			    int res = serviceFacade.getResourceService().uploadResourceInfo(result,desFilePath,"C:\\\\Users\\\\CloudEasyServer\\\\Desktop\\\\ffmpeg-4.2.1-win64-static\\\\bin\\\\ffmpeg.exe");
			    System.out.println("文件类型"+"resType:" + resType);
			} catch (IllegalStateException e){
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
//		   String video_path = resourceInfo.getPath();
//		   File videoFile = new File(video_path);
//		    //测试此抽象路径名表示的文件或目录是否存在
//		    if (videoFile.exists()) {
//			    List<String> commands = new ArrayList<>();
//			    commands.add(ffmpeg_path);
//			    commands.add("-i");
//			    commands.add(video_path);
//			    commands.add("-y");
//			    commands.add("-f");
//			    commands.add("image2");
//			    commands.add("-ss");
//			    commands.add("0.5");// 这个参数是设置截取视频多少秒时的画面
//			    // commands.add("-t");
//			    // commands.add("0.001");
//			    commands.add("-s");
//			    commands.add("700x525");//设置截取图片大小
//			    commands.add(video_path.substring(0, video_path.lastIndexOf(".")) + ".jpg");//截取视频的文件名再加上jpg
//			    System.out.println(commands);
//			    System.out.println(videoFile);
//			    System.out.println(video_path.substring(0, video_path.lastIndexOf(".")));
//			    try {
//			      ProcessBuilder builder = new ProcessBuilder();
//			      builder.command(commands);
//			      builder.start();//执行命令并返回一个Process对象，用于获取对执行程序的输入和输出
//			      System.out.println("截取成功");
//			    } catch (Exception e) {
//			      e.printStackTrace();
//			      
//			    } catch (IOException e) {
//					e.printStackTrace();
//				}
//		    } else { 
//		    	System.err.println("路径[" + video_path + "]对应的视频文件不存在!");
//		    }
		return resourceInfoMapper.insert(resourceInfo);
	}
	
	/**
	 * 删除资源
	 * @param resId
	 * @return
	 */
	@Override
	public int deleteResourceInfoByResId(Long resId) {
		String path = resourceInfoMapper.selectByPrimaryKey(resId).getPath();
		File file = new File(path);
		if(file.exists() && file.isFile()) {
			file.delete();
			return resourceInfoMapper.deleteByPrimaryKey(resId);
		} else {
			System.err.println("文件不存在");
			logger.info("文件不存在");
			return 0;
		}
		
	}
	
	/**
	 * 修改资源名
	 * @param resId
	 * @return
	 */
	@Override
	public int modifyResourceNameByResId(Long resId,String resName) {
		return resourceInfoMapper.updateByPrimaryKey(resId, resName);
	}
	
	/**
	 * 获取资源详情
	 * @param resId
	 * @return
	 */
	@Override
    public ResourceInfo getResourceDetailByResId(Long resId) {
       return resourceInfoMapper.selectByPrimaryKey(resId);
    }
	
	/**
	 * 获取资源列表
	 * @return
	 * @throws Exception 
	 */
	@Override
	public List<ResourceInfo> getResourceList() {
		return resourceInfoMapper.selectList();
	}


	/**
	 * 获取资源数
	 */
	@Override
	public int getResourceListCount() {
		return resourceInfoMapper.selectListCount();
	}
	
	
	
	
	
	
	

   
    
    
}
