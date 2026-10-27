# PhigrosRemake

[![Build desktop](https://github.com/luanmenglei/PhigrosRemake/actions/workflows/build.yml/badge.svg)](https://github.com/luanmenglei/PhigrosRemake/actions/workflows/build.yml)

一个用 **Java + libGDX** 编写的 Phigros 谱面播放与渲染实验，窗口标题为 *Phigros Runner*。代码由 luanmenglei 编写。

目前启动后会自动播放内置的 `demo/a` 谱面，展示判定线、音符、背景与打击效果。它仍是早期实验，尚未实现完整的玩家输入、选曲和计分系统。

## 快速开始

需要 **JDK 21** 和支持 OpenGL 的桌面环境。Gradle Wrapper 已包含在仓库中，不用另外安装 Gradle；首次构建会下载 Gradle 和 Maven 依赖。

```bash
git clone https://github.com/luanmenglei/PhigrosRemake.git
cd PhigrosRemake
```

Windows PowerShell：

```powershell
.\gradlew.bat desktop:run
```

macOS / Linux：

```bash
./gradlew desktop:run
```

启动入口为 `desktop/src/main/java/me/clesip/phirunner/DesktopLauncher.java`。使用 IntelliJ IDEA 时，打开仓库根目录作为 Gradle 项目，选择 JDK 21，再执行 `desktop:run`。

若系统默认 Java 版本不是 21，请先把 `JAVA_HOME` 指向 JDK 21。Wrapper 固定为 Gradle 8.14.3；游戏依赖保留 libGDX 1.11.0。

## 构建可运行 JAR

```bash
./gradlew clean build desktop:dist
java -jar desktop/build/libs/PhigrosRemake.jar
```

Windows 将 `./gradlew` 换成 `.\gradlew.bat`。macOS 启动 JAR 需要首线程参数：

```bash
java -XstartOnFirstThread -jar desktop/build/libs/PhigrosRemake.jar
```

生成的 JAR 包含依赖和内置资源，可以移到其他目录运行，仍然需要 Java 运行环境。普通的 `desktop-1.0.jar` 不包含全部依赖，请使用 `PhigrosRemake.jar`。

## 目录

```text
core/src/main/java/       谱面数据、判定线、音符与渲染逻辑
core/src/test/            WAV 读取测试及自制测试音频
desktop/src/main/java/   LWJGL3 桌面入口
desktop/src/test/java/   无窗口、静音的渲染检查
assets/                  原有贴图、音效、演示谱面和音乐
gradle/wrapper/          完整的 Gradle Wrapper
legacy/                  原包中的 GWT 描述文件，当前不参与构建
docs/                    归档迁移说明和文件校验记录
```

## 谱面与资源

默认谱面在 `PhiRunner.create()` 中通过 `new PhigrosChart("demo", "a")` 加载，需要同一前缀的 `.json`、`.wav`、`.png`。目前没有选曲界面或通用谱面转换器。

`assets/demo/` 内的其他音乐、PEC、CSV 等文件沿用原始上传包，保留用于后续研究，不表示都能直接由当前加载器播放。例如 `SpasmodicSP.wav` 在原包中就是空文件；请不要直接切换到它。

素材和原有元数据一并保留，音乐、谱面及图片的权利归各自作者。本次整理没有新增统一开源许可证。

## 验证

```bash
./gradlew build desktop:dist
./gradlew desktop:smokeTest desktop:smokeTestDist
```

单元测试覆盖 WAV 时长读取，包括只读文件、classpath 资源和无效音频。渲染检查使用隐藏窗口且关闭音频，加载默认谱面、渲染三帧，并验证画面不为空；它们分别检查松散资源和独立 JAR 中的资源。截图位于 `desktop/build/reports/`。

渲染检查需要 OpenGL。CI 在 Linux 上通过 Xvfb 和软件 OpenGL 运行，并上传可运行 JAR 与验证报告。这些检查不代表完整歌曲和所有谱面都已验证。

## 从压缩包迁移

现在直接维护源码和资源，不必解压、拼接或手动上传分卷。原来的 ZIP 和四个资源分卷保存在 Git 历史及 `legacy/manual-uploads` 标签中，没有重写历史。

详细记录见 [迁移说明](docs/MIGRATION.md) 和 [文件 SHA-256 清单](docs/migration-manifest.json)。之后正常修改文件、`git add`、`git commit`、`git push` 即可；构建产物由 `.gitignore` 排除。
