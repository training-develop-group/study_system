package com.example.study_system.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bytedeco.javacv.FrameGrabber.Exception;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.example.study_system.model.ResourceInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IResourceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ResourceServiceImpl extends BaseService implements IResourceService {

	public static String oriFilePath = "F:\\study\\files\\";	// 输出文件路径

	/**
	 * 上传资源
	 * 
	 * @param resourceInfo
	 * @return
	 */
	@Override
	public int uploadResourceInfo(ResourceInfo resourceInfo, MultipartFile file, String ffmpeg_path) {
		String oriName = ""; // 原文件名称
		String desFilePath = ""; // 文件路径
		Integer resType = 0; // 文件类型
		String uuid = ""; // 重命名名字（uuid）
		String extName = "";
		File path = new File(oriFilePath);
		if (!path.exists()) { // 如果文件夹不存在
			path.mkdirs(); // 创建文件夹
		}

		if (file != null) {
			oriName = file.getOriginalFilename(); // 获取原文件名
			long resSize = Math.round(file.getSize()); // 获取文件大小
			String fileType = file.getContentType(); // 获取文件类型
			System.err.println(fileType);
			extName = oriName.substring(oriName.lastIndexOf(".")); // 获取源文件后缀名
			System.err.println(extName);

			// 截取文件后缀名判断文件类型
			if (extName.equals(".mp4") || extName.equals(".avi") || extName.equals(".mpg") || extName.equals(".mpeg")
					|| extName.equals(".wmv") || extName.equals(".rmvb") || fileType.equals("video/ogg")) {
				fileType = "1";
				resType = Integer.valueOf(fileType);
			} else if (extName.equals(".ogv") || extName.equals(".mp3") || extName.equals(".wav")
					|| fileType.equals("audio/ogg")) {
				fileType = "2";
				resType = Integer.valueOf(fileType);
			} else if (extName.equals(".txt") || extName.equals(".doc") || extName.equals(".docx")
					|| extName.equals(".xlsx") || extName.equals(".xls") || extName.equals(".ppt")
					|| extName.equals(".pptx")) {
				fileType = "3";
				resType = Integer.valueOf(fileType);
			}

			uuid = UUID.randomUUID().toString().replaceAll("-", ""); // 生成UUID
			String newName = uuid + extName; // UUID生成的新名字+后缀名
			MultipartConfigFactory factory = new MultipartConfigFactory();

			try {
				String filePath = oriFilePath; // 获取要保存的路径文件夹
				// 保存文件
				desFilePath = filePath + newName; // 保存文件路径
				File desFile = new File(desFilePath);
				if (desFile.createNewFile()) {
					desFile.setExecutable(true);
					desFile.setReadable(true);
					desFile.setWritable(true);
					System.out.println("is execute allow : " + desFile.canExecute());
				}
				file.transferTo(desFile);
				logger.info("保存文件路径:" + desFilePath);
				String resName = oriName.substring(0, oriName.lastIndexOf("."));
				resourceInfo.setResName(resName); // 添加原文件名
				resourceInfo.setResType(resType); // 添加文件类型
				resourceInfo.setPath(newName); // 添加保存文件路径
				if (resType != 1) {
					resourceInfo.setImgPath("");
				}
				resourceInfo.setResExt(extName); // 添加后缀名
				resourceInfo.setResSize(resSize); // 添加文件大小
				resourceInfo.setStatus(0); // 添加状态默认是 0：未发布
				Date date = new Date(); // 获取当前系统时间
				resourceInfo.setcTime(date); // 添加当前系统时间
				resourceInfo.setmTime(date); // 添加当前系统时间
				resourceInfo.setcUser("123"); // 添加创建用户
				resourceInfo.setmUser("456"); // 添加修改用户
				System.out.println("文件类型" + "resType:" + resType);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 判断文件类型是否为视频类型
		if (resourceInfo.getResType() == 1) {
			VideoScreenshots(resourceInfo, desFilePath, ffmpeg_path, uuid);
		} else if (resourceInfo.getResType() == 3) {
			if (!extName.equals(".txt")) {
				String desPath = oriFilePath + uuid + ".pdf";
				officeOpenPDF(desFilePath, desPath);
				logger.error("成功进入方法");
			}
		}

		return resourceInfoMapper.insert(resourceInfo);
	}

	// 使用ffmpeg截取视频里的一帧图片
	public void VideoScreenshots(ResourceInfo resourceInfo, String desFilePath, String ffmpeg_path, String uuid) {
		String video_path = desFilePath;
		File videoFile = new File(video_path);
		// 测试此抽象路径名表示的文件或目录是否存在
		if (videoFile.exists()) {
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
			commands.add("700x525");// 设置截取图片大小
			commands.add(video_path.substring(0, video_path.lastIndexOf(".")) + ".jpg"); // 截取视频的文件名再加上jpg
			System.out.println(commands);
			System.out.println(videoFile);
			System.out.println(video_path.substring(0, video_path.lastIndexOf(".")));
			try {
				ProcessBuilder builder = new ProcessBuilder();
				builder.command(commands);
				builder.start(); // 执行命令并返回一个Process对象，用于获取对执行程序的输入和输出
				System.out.println("截取成功");
				resourceInfo.setImgPath(uuid + ".jpg");
			} catch (Exception e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			System.err.println("路径	[" + video_path + "]对应的视频文件不存在!");
		}

	}

	// 使用openoffice把office文档转换成为pdf文件
	public void officeOpenPDF(String inputFile, String pdfFile) {

		Process p = null;
		// 判断源文件目录是否存在
		File inputFiles = new File(inputFile);
		if (!inputFiles.exists()) {
			logger.error("源文件不存在!");
			return;
		}

		// 判断输出文件上级目录是否存在
		File outputFile = new File(pdfFile);
		if (!outputFile.getParentFile().exists()) {
			outputFile.getParentFile().exists();
		}

		// 调用openoffice服务线程
		String command = "C:\\Program Files (x86)\\OpenOffice 4\\program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
		try {
			p = Runtime.getRuntime().exec(command);	//调用cmd命令
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.err.println("成功--------1");
		OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);

		try {
			connection.connect();
			DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
			converter.convert(inputFiles, outputFile);
			connection.disconnect();

		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			System.out.println("openoffice报错");
			e.printStackTrace();
		}

	}

//	public void Word2Pdf(String srcPath, String desPath) throws IOException {
//		OpenOfficeConnection connection = null;
//		Process p = null;
//		try {
//			// 源文件目录
//			File inputFile = new File(srcPath);
//			if (!inputFile.exists()) {
//				System.out.println("源文件不存在！");
//				return;
//			}
//			// 输出文件目录
//			File outputFile = new File(desPath);
//			if (!outputFile.getParentFile().exists()) {
//				outputFile.getParentFile().exists();
//			}
//			// 调用openoffice服务线程
//			String command = "C:\\Program Files (x86)\\OpenOffice 4\\program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
//			p = Runtime.getRuntime().exec(command);
//
//			// 连接openoffice服务
//			connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
//			connection.connect();
//
//			// 转换word到pdf
//			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
//			converter.convert(inputFile, outputFile);
//
//			System.out.println("转换完成！");
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (connection != null) {
//				// 关闭连接
//				connection.disconnect();
//			}
//			if (p != null) {
//				// 关闭进程
//				p.destroy();
//			}
//		}
//	}

	/**
	 * 删除资源
	 * 
	 * @param resId
	 * @return
	 */
	@Override
	@Transactional
	public int deleteResourceInfoByResId(Long resId) {
		String path = resourceInfoMapper.selectByPrimaryKey(resId).getPath();
		String filePath = oriFilePath + path;
		File file = new File(filePath);
		System.err.println(filePath);
		if (file.exists() && file.isFile()) {
			file.delete();
			return resourceInfoMapper.deleteByPrimaryKey(resId);
		}
		return 0;
	}

	/**
	 * 修改资源名
	 * 
	 * @param resId
	 * @return
	 */
	@Override
	public int modifyResourceNameByResId(Long resId, String resName) {
		if (resName.equals("")) {
			return 0;
		}
		return resourceInfoMapper.updateByPrimaryKey(resId, resName);
	}

	/**
	 * 获取资源详情
	 * 
	 * @param resId
	 * @return
	 */
	@Override
	public ResourceInfo getResourceDetailByResId(Long resId) {
		return resourceInfoMapper.selectByPrimaryKey(resId);
	}

	/**
	 * 获取资源列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageInfo<ResourceInfo> getResourceList(Integer pageNum, Integer pageSize, String resName, Integer resType) {
		PageHelper.startPage(pageNum, pageSize);
		List<ResourceInfo> resourceList = resourceInfoMapper.selectList(resName, resType);
		PageInfo<ResourceInfo> result = new PageInfo<>(resourceList);
		return result;
	}

	/**
	 * 获取资源数
	 */
	@Override
	public int getResourceListCount() {
		return resourceInfoMapper.selectListCount();
	}

}
