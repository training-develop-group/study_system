package com.example.study_system.service.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.stereotype.Service;

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
	public int uploadResourceInfo(ResourceInfo resourceInfo, String video_path, String ffmpeg_path) {
		   File file = new File(video_path);
		    //测试此抽象路径名表示的文件或目录是否存在
		    if (file.exists()) {
			    List<String> commands = new ArrayList<>();
			    commands.add(ffmpeg_path);
			    commands.add("-i");
			    commands.add(video_path);
			    commands.add("-y");
			    commands.add("-f");
			    commands.add("image2");
			    commands.add("-ss");
			    commands.add("0.5");// 这个参数是设置截取视频多少秒时的画面
			    // commands.add("-t");
			    // commands.add("0.001");
			    commands.add("-s");
			    commands.add("700x525");//设置截取图片大小
			    commands.add(video_path.substring(0, video_path.lastIndexOf(".")) + ".jpg");//截取视频的文件名再加上jpg
			    System.out.println(commands);
			    System.out.println(file);
			    System.out.println(video_path.substring(0, video_path.lastIndexOf(".")));
			    try {
			      ProcessBuilder builder = new ProcessBuilder();
			      builder.command(commands);
			      builder.start();//执行命令并返回一个Process对象，用于获取对执行程序的输入和输出
			      System.out.println("截取成功");
			    } catch (Exception e) {
			      e.printStackTrace();
			      
			    } catch (IOException e) {
					e.printStackTrace();
				}
		    } else { 
		    	
		    	System.err.println("路径[" + video_path + "]对应的视频文件不存在!");
		    }
		return resourceInfoMapper.insert(resourceInfo);
	}
	
	/**
	 * 删除资源
	 * @param resId
	 * @return
	 */
	@Override
	public int deleteResourceInfoByResId(Long resId) {
		return resourceInfoMapper.deleteByPrimaryKey(resId);
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
