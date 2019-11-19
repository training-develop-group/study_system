package com.example.study_system.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.example.study_system.emun.ResultEmun;
import com.example.study_system.model.ResourceInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IResourceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.awt.Dimension;   
 
 
import java.awt.Color;   
import java.awt.Graphics2D;   
import java.awt.geom.Rectangle2D;   
import java.awt.image.BufferedImage;   
 
import org.apache.poi.hslf.record.Slide;
import org.apache.poi.hslf.model.TextRun;   
import org.apache.poi.hslf.usermodel.RichTextRun;   
import org.apache.poi.hslf.usermodel.SlideShow;

@Service
public class ResourceServiceImpl extends BaseService implements IResourceService {

	public static String ORI_FILE_PATH = "F:\\study\\files\\"; // 输出文件路径

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
		File path = new File(ORI_FILE_PATH);
		if (!path.exists()) { // 如果文件夹不存在
			path.mkdirs(); // 创建文件夹
		}

		if (file != null) {
			if (file.getSize() == 0) {
				return ResultEmun.UPLOAD_FILE_ISEMPTY.getCode();
			}
			oriName = file.getOriginalFilename(); // 获取原文件名
			long resSize = Math.round(file.getSize()); // 获取文件大小
			String fileType = file.getContentType(); // 获取文件类型
			System.err.println(fileType);
			extName = oriName.substring(oriName.lastIndexOf(".")).toLowerCase(); // 获取源文件后缀名
			System.err.println(extName);

			// 截取文件后缀名判断文件类型
			if (extName.equals(".mp4") || extName.equals(".avi") || extName.equals(".mpg") || extName.equals(".mpeg")
					|| extName.equals(".wmv") || extName.equals(".rmvb") || extName.equals(".rm")
					|| extName.equals(".flv") || extName.equals(".mov")) {
				fileType = "1";
				resType = Integer.valueOf(fileType);
			} else if (extName.equals(".ogv") || extName.equals(".mp3") || extName.equals(".wav")
					|| extName.equals(".wma") || extName.equals(".cd") || extName.equals(".wav")
					|| extName.equals(".aiff") || extName.equals(".aac") || extName.equals(".midi")) {
				fileType = "2";
				resType = Integer.valueOf(fileType);
			} else if (extName.equals(".txt") || extName.equals(".doc") || extName.equals(".docx")
					|| extName.equals(".xlsx") || extName.equals(".xls") || extName.equals(".ppt")
					|| extName.equals(".pptx") || extName.equals(".pdf")) {
				fileType = "3";
				resType = Integer.valueOf(fileType);
			} else {
				return ResultEmun.THIS_FILE_FORMAT_IS_NOT_SUPPORTED.getCode();
			}

			uuid = UUID.randomUUID().toString().replaceAll("-", ""); // 生成UUID
			String newName = uuid + extName; // UUID生成的新名字+后缀名

			try {
				String filePath = ORI_FILE_PATH; // 获取要保存的路径文件夹
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
				resourceInfo.setcUser(resourceInfo.getcUser()); // 添加创建用户
				resourceInfo.setmUser(resourceInfo.getcUser()); // 添加修改用户

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
		} else if (resourceInfo.getResType() == 3 && !extName.equals(".pdf")) {
			if (!extName.equals(".txt")) {
				String desPath = ORI_FILE_PATH + uuid + ".pdf";
				officeOpenPDF(desFilePath, desPath);
				logger.info("成功进入方法");
				try {
					excel();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				testExcel();
//				try {
//					docxToHtml();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		}
		return resourceInfoMapper.insert(resourceInfo);
	}

	// 使用ffmpeg截取视频里的一帧图片
	private void VideoScreenshots(ResourceInfo resourceInfo, String desFilePath, String ffmpeg_path, String uuid) {
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
	private void officeOpenPDF(String inputFile, String pdfFile) {

		Process p = null;
		// 判断源文件目录是否存在
		File inputFiles = new File(inputFile);
		if (!inputFiles.exists()) {
			logger.info("源文件不存在!");
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
			p = Runtime.getRuntime().exec(command); // 调用cmd命令
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);

		try {
			connection.connect();
			DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
			converter.convert(inputFiles, outputFile);
			connection.disconnect();

		} catch (ConnectException e) {
			System.out.println("openoffice报错");
			e.printStackTrace();
		}

	}

	public String docxToHtml() throws Exception, IOException {
		File path = new File(ResourceUtils.getURL("classpath:").getPath());
		String imagePath = path.getAbsolutePath() + "\\image";
		String sourceFileName = "F:\\01.xls";
		String targetFileName = path.getAbsolutePath() + "\\每日笔记.html";
//	    System.err.println("path:"+path);
//	    System.err.println("imagePath:"+imagePath);
//	    System.err.println("targetFileName:"+targetFileName);

		OutputStreamWriter outputStreamWriter = null;
		try {
			XWPFDocument document = new XWPFDocument(new FileInputStream(sourceFileName));
			XHTMLOptions options = XHTMLOptions.create();
			// 存放图片的文件夹
			options.setExtractor(new FileImageExtractor(new File(imagePath)));
			// html中图片的路径
			options.URIResolver(new BasicURIResolver("image"));
			outputStreamWriter = new OutputStreamWriter(new FileOutputStream(targetFileName), "utf-8");
			XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
			xhtmlConverter.convert(document, outputStreamWriter, options);
		} finally {
			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}
		}
		return targetFileName;
	}

//	public void testExcel() throws FileNotFoundException {
//		File path = new File(ResourceUtils.getURL("classpath:").getPath());
//		String imagePath = path.getAbsolutePath() + "\\image";
//		String sourceFileName = "F:\\01.xls";
//		String targetFileName = path.getAbsolutePath() + "\\01.html";
////		String path = "F:\\11.18";
////		String imagePath = "F:\\11.18\\image";
//		String file = "\\02.xlsx";
//		HSSFWorkbook excelBook = null;
//		ExcelToHtmlConverter excelToHtmlConverter = null;
//		try {
//			InputStream input = new FileInputStream(sourceFileName);
//			excelBook = new HSSFWorkbook(input);
//			excelToHtmlConverter = new ExcelToHtmlConverter(
//					DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		}
//
//		// 加载html页面时图片路径
//		XHTMLOptions options = XHTMLOptions.create();
//		// 图片保存文件夹路径
//		options.setExtractor(new FileImageExtractor(new File(imagePath)));
//		options.URIResolver(new BasicURIResolver("image"));
//		excelToHtmlConverter.setOutputRowNumbers(false);
//		excelToHtmlConverter.setOutputHiddenRows(false);
//		excelToHtmlConverter.setOutputColumnHeaders(false);
//		excelToHtmlConverter.setOutputHiddenColumns(true);
//		excelToHtmlConverter.processWorkbook(excelBook);
//		List pics = excelBook.getAllPictures();
//		if (pics != null) {
//			for (int i = 0; i < pics.size(); i++) {
//				HSSFPictureData pic = (HSSFPictureData) pics.get(i);
//				try {
////                        pic.writeImageContent (new FileOutputStream (path + pic.suggestFullFileName() ) );
//					new FileOutputStream(imagePath + "11").write(pic.getData());
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//
//			}
//		}
//		Document htmlDocument = excelToHtmlConverter.getDocument();
//		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//		DOMSource domSource = new DOMSource(htmlDocument);
//		StreamResult streamResult = new StreamResult(outStream);
//		TransformerFactory tf = TransformerFactory.newInstance();
//		try {
//			Transformer serializer = tf.newTransformer();
//			serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
//			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
//			serializer.setOutputProperty(OutputKeys.METHOD, "html");
//			serializer.transform(domSource, streamResult);
//		} catch (TransformerException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				outStream.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		String content = new String(outStream.toByteArray());
//
//		try {
//			FileUtils.writeStringToFile(new File(path, "exportExcel.html"), content, "utf-8");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public void excel() throws IOException, ParserConfigurationException, TransformerException {
		String path = "F:\\";
		String file = "02.xls";
		String path2 = "F:/123/123.jpg";

		InputStream input = new FileInputStream(path + file);
		HSSFWorkbook excelBook = new HSSFWorkbook(input);
		ExcelToHtmlConverter excelToHtmlConverter = new ExcelToHtmlConverter(
				DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
		excelToHtmlConverter.processWorkbook(excelBook);
		List pics = excelBook.getAllPictures();
		if (pics != null) {
			for (int i = 0; i < pics.size(); i++) {
				HSSFPictureData pic = (HSSFPictureData) pics.get(i);
				try {
//					pic.writeImageContent(new FileOutputStream(path + pic.suggestFullFileName()));
					new FileOutputStream("F:/123/" + i + ".jpg").write(pic.getData());
					logger.error("1");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		Document htmlDocument = excelToHtmlConverter.getDocument();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(outStream);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);
		outStream.close();

		String content = new String(outStream.toByteArray());

		FileUtils.writeStringToFile(new File(path, "exportExcel.html"), content, "utf-8");
	}
	
	
	 public static boolean doPPTtoImage(File file) {   
	        boolean isppt = checkFile(file);   
	        if (!isppt) {   
	            System.out.println("The image you specify don't exit!");   
	            return false;   
	        }   
	        try {   
	 
	            FileInputStream is = new FileInputStream(file);   
	            SlideShow ppt = new SlideShow(is);   
	            is.close();   
	            Dimension pgsize = ppt.getPageSize();   
	            org.apache.poi.hslf.model.Slide[] slide = ppt.getSlides();   
	            for (int i = 0; i < slide.length; i++) {   
	                System.out.print("第" + i + "页。");   
	 
	                TextRun[] truns = slide[i].getTextRuns();      
	                for ( int k=0;k<truns.length;k++){      
	                   RichTextRun[] rtruns = truns[k].getRichTextRuns();      
	                  for(int l=0;l<rtruns.length;l++){      
	                       int index = rtruns[l].getFontIndex();      
	                        String name = rtruns[l].getFontName();                
	                        rtruns[l].setFontIndex(1);      
	                        rtruns[l].setFontName("宋体");  
//	                        System.out.println(rtruns[l].getText());
	                   }      
	                }      
	                BufferedImage img = new BufferedImage(pgsize.width,pgsize.height, BufferedImage.TYPE_INT_RGB);   
	 
	                Graphics2D graphics = img.createGraphics();   
	                graphics.setPaint(Color.BLUE);   
	                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));   
	                slide[i].draw(graphics);   
	 
	                // 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径   
	                FileOutputStream out = new FileOutputStream("D:/poi-test/pptToImg/pict_"+ (i + 1) + ".jpeg");   
	                javax.imageio.ImageIO.write(img, "jpeg", out);   
	                out.close();   
	 
	            }   
	            System.out.println("success!!");   
	            return true;   
	        } catch (FileNotFoundException e) {   
	            System.out.println(e);   
	            // System.out.println("Can't find the image!");   
	        } catch (IOException e) {   
	        }   
	        return false;   
	    }   
	 
	    // function 检查文件是否为PPT   
	    public static boolean checkFile(File file) {   
	 
	        boolean isppt = false;   
	        String filename = file.getName();   
	        String suffixname = null;   
	        if (filename != null && filename.indexOf(".") != -1) {   
	            suffixname = filename.substring(filename.indexOf("."));   
	            if (suffixname.equals(".ppt")) {   
	                isppt = true;   
	            }   
	            return isppt;   
	        } else {   
	            return isppt;   
	        }   
	    }   

	

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
		String filePath = ORI_FILE_PATH + path;
		File file = new File(filePath);
		System.err.println(filePath);
		if (file.exists() && file.isFile()) {
			file.delete();

		}
		return resourceInfoMapper.deleteByPrimaryKey(resId);
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
