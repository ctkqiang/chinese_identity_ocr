
# 中华人民共和国居民身份证 OCR 识别与图像处理系统

基于 OpenCV 和 Tesseract 的中国居民身份证光学字符识别（OCR）系统。本系统采用先进的计算机视觉技术和深度学习算法，
能够快速准确地识别身份证上的文字信息。通过多级图像预处理和优化的 OCR 引擎，即使在复杂光照条件下也能保持较高的识别
准确率。系统采用 MVC 设计模式，具有良好的可扩展性和维护性，可轻松集成到各类应用场景中。

## 功能特点

- 身份证图像预处理
- 光学字符识别（OCR）
- 中文文本提取
- 高精度识别

## 系统要求

- Java 17 或更高版本
- Maven 3.8+
- OpenCV 4.x
- Tesseract 4.x
- MacOS/Linux/Windows

## 安装依赖

### MacOS

```bash
# 安装 OpenCV
brew install opencv

# 安装 Tesseract 及中文语言包
brew install tesseract
brew install tesseract-lang
````

## 构建项目

```bash
mvn clean install
```

## 运行程序

```bash
mvn exec:java \
  -Dexec.mainClass="xin.ctkqiang.中华人民共和国居民身份证_OCR_识别与图像处理系统" \
  -Djava.library.path="/opt/homebrew/opt/opencv/share/java/opencv4" \
  -Dexec.args="<图像路径>"
```

## 使用说明

1. 准备清晰的身份证图像
2. 运行程序并输入图像路径
3. 系统将自动处理图像并识别文字内容

## 项目结构

```
src/
├── main/
│   └── java/
│       └── xin/
│           └── ctkqiang/
│               ├── 控制器/    # 控制器层
│               ├── 模型/      # 模型层
│               └── 视图/      # 视图层
```

## 技术架构

- 设计模式：MVC + 单例模式
- 图像处理：OpenCV
- 文字识别：Tesseract OCR
- 构建工具：Maven

