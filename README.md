#腾讯云 [智能图像服务](https://cloud.tencent.com/document/product/641/12437) SDK for JAVA

	Maven 信息：
	GroupId： com.qcloud
	ArtifactId：qcloud-image-sdk
	Version: 2.0.1

	产品文档地址
	https://cloud.tencent.com/document/product/641/12437
	

## 1. 在腾讯云申请业务的授权
授权包括：
		
 1. 前往注册： [腾讯云账号注册](https://cloud.tencent.com/register) （详细指引见 [注册腾讯云](https://cloud.tencent.com/document/product/378/9603)）
 2. 取得存储桶名称 `BucketName`： 请前往 [创建存储桶](https://cloud.tencent.com/document/product/460/10637) 
 3. 取得 `APPID`、`SecretId`、`SecretKey`：请前往 [云API密钥](https://console.cloud.tencent.com/cam/capi) ，点击“新建密钥”

## 2. 快速体验
1. 修改文件 src/main/java/com/qcloud/image/demo/Demo.java 的 main() 方法，填入上述申请到的`APPID`、`SecretId`、`SecretKey`、`BucketName`
2. 导入到 IDE：项目以 Maven 构建，以 Intellij IDEA 为例，导入方式为：`Import Project -> 选择工程目录 -> 
Import project from external model -> Maven`
3. 运行：Demo.java 右键，`Run Demo.main()`

## 3. 使用简介
### 初始化

`ImageClient imageClient = new ImageClient(APPID, SecretId, SecretKey);`

### 调用方法
在创建完对象后，根据实际需求，调用对应的操作方法就可以了。SDK 提供功能如下，详情可参见 `Demo.java` 的代码。

**图像识别**：鉴黄，标签  
**文字识别(OCR)**：身份证，名片，通用，驾驶证行驶证，营业执照，银行卡，车牌号  
**人脸识别**：人脸检测，五官定位，个体信息管理，人脸验证，人脸对比及人脸检索  
**人脸核身**：用户上传照片与高清身份证照片比对，活体检测—获取唇语验证码，活体检测-视频与身份证高清照片的比对，活体检测-视频与用户照片的比对

### 释放资源
`imageClient.shutdown();`


