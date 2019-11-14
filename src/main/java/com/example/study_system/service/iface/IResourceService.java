package com.example.study_system.service.iface;

import com.example.study_system.model.ResourceInfo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

public interface IResourceService {

    //上传资源 (对应Mapper是insert方法)
    int uploadResourceInfo(ResourceInfo resourceInfo, MultipartFile file, String ffmpeg_path);

    //删除资源 (对应Mapper是deleteByPrimaryKey方法)
    int deleteResourceInfoByResId(Long resId);

    //修改资源名 (对应Mapper是updateByPrimaryKey方法)
    int modifyResourceNameByResId(Long resId, String resName);

    //获取资源详情 (对应Mapper是selectByPrimaryKey方法)
    ResourceInfo getResourceDetailByResId(Long resId);

    //获取资源列表 (对应Mapper是selectList方法)
    PageInfo<ResourceInfo> getResourceList(Integer pageNum, Integer pageSize, String resName, Integer resType);

    //获取资源总数
    int getResourceListCount();
}
