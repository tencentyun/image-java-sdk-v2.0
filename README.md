# 腾讯云 [智能图像服务](https://cloud.tencent.com/product/cv) SDK for JAVA

## 使用前准备

1. 前往注册： [腾讯云账号注册](https://cloud.tencent.com/register) （详细指引见 [注册腾讯云](https://cloud.tencent.com/document/product/378/9603)）
2. 取得存储桶名称 **BucketName**： 请前往 [创建存储桶](https://cloud.tencent.com/document/product/460/10637) 
3. 取得 **APPID**、**SecretId**、**SecretKey**：请前往 [云API密钥](https://console.cloud.tencent.com/cam/capi) ，点击“新建密钥”

## 如何集成此 SDK 到你的项目中

### 使用 maven

```xml
<dependency>
  <groupId>com.qcloud</groupId>
  <artifactId>qcloud-image-sdk</artifactId>
  <version>2.3.2</version>
</dependency>
```

### 使用 Gradle

```groovy
compile 'com.qcloud:qcloud-image-sdk:2.3.2'
```

### 使用  jar 文件

1. jar 文件见 release/*-with-dependencies.jar
2. 把  jar 文件导入到你的项目中

## 使用简介

### 初始化

```java
ImageClient imageClient = new ImageClient(appId, secretId, secretKey, ImageClient.NEW_DOMAIN_recognition_image_myqcloud_com/*默认使用新域名, 如果你是老用户, 请选择旧域名*/);
```

### 设置代理

根据实际网络环境，可能要设置代理，例如: 

```java
Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress("127.0.0.1", 8080));
imageClient.setProxy(proxy);
```

### 使用

SDK 提供功能如下：

**图像识别**：鉴黄，标签  
**文字识别(OCR)**：身份证，名片，通用，驾驶证行驶证，营业执照，银行卡，车牌号  
**人脸识别**：人脸检测，五官定位，个体信息管理，人脸验证，人脸对比及人脸检索  
**人脸核身**：照片核身（通过照片和身份证信息），获取唇语验证码（用于活体核身），活体核身（通过视频和照片），活体核身（通过视频和身份证信息）

```java
// 调用车牌识别API示例
String imageUrl = "http://youtu.qq.com/app/img/experience/char_general/icon_ocr_license_3.jpg";
String result = imageClient.ocrPlate(new OcrPlateRequest("bucketName", imageUrl));
System.out.println(result);
```

更多例子详情可参见 [Demo.java](https://github.com/tencentyun/image-java-sdk-v2.0/blob/master/src/main/java/com/qcloud/image/demo/Demo.java) 的代码。

## 如何运行这个 Demo 工程

### 填写必要信息
修改文件 src/main/java/com/qcloud/image/demo/Demo.java 的 main() 方法，填入本文开头所述的 **APPID**、**SecretId**、**SecretKey**、**BucketName**

### 导入 Intellij IEDA
1. Import Project -> 选择工程目录 ->  Import project from external model -> Maven
3. 运行：Demo.java 右键，Run Demo.main()

### 导入 Eclipse
1. Eclipse 需要先安装 maven 插件: 点击 Eclipse 菜单栏 Help -> Eclipse Marketplace, 搜索框 Find 中输入 maven, 点击右侧的 Go, 搜索到插件 `Maven Integration for Eclipse`, 安装之.
2. Eclipse 菜单栏 File -> Import… -> Maven -> Existing Maven Project -> 选择工程目录 -> 点击 Finish.
3. 运行: Eclipse 菜单栏 Run -> Run.

## 如何修改源码
1. 把工程源码导入到 IDE 中, 如上面 [如何运行这个 Demo 工程] 所述
2. 修改源码
3. 在工程根目录下执行命令 `mvn assembly:assembly`，编译结果见 target/*-with-dependencies.jar
4. 在你的项目中取消对本 SDK 的依赖, 并导入编译所得的  jar 文件

