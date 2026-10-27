# 从手动上传包还原项目

整理前提交：[`39c1eacc3c127bbe9097260c93b17611604c765b`](https://github.com/luanmenglei/PhigrosRemake/tree/39c1eacc3c127bbe9097260c93b17611604c765b)。保留标签为 `legacy/manual-uploads`。

## 原始内容

- `core.zip`：核心 Java 源码、模块构建文件、GWT 描述文件。
- `desktop.zip`：桌面启动器和模块构建文件。
- `assets.zip.001` 到 `.004`：同一份 ZIP 的四个连续分卷，按编号拼接后通过 ZIP CRC 校验。

共还原 **69 个文件**，其中 **51 个资源文件按字节保留**。包括点号开头的演示贴图和原本为空的 `SpasmodicSP.wav`，没有按文件名或用途猜测后删除素材。

## 路径和格式

- `core/src/me/...` → `core/src/main/java/me/...`。
- `desktop/src/me/...` → `desktop/src/main/java/me/...`。
- `assets/...` 保持原路径。
- `core/src/PhiRunner.gwt.xml` → `legacy/PhiRunner.gwt.xml`。
- Java、Gradle、XML 文本统一为 UTF-8 / LF，修正原文件中的重复 CR 换行，清理 Java 行尾空白；资源内容不改动。

`migration-manifest.json` 记录每个文件的原始路径、迁移路径、归档中的 SHA-256 和刚解压并规范换行后的 SHA-256。后者记录的是迁移基线，不要求文件在后续开发中一直不变。

## 必要的构建修复

- 补全 Gradle 8.14.3 Wrapper，并配置分发包 SHA-256。Wrapper JAR 下载时也核对了官方校验和。
- 根项目只保留实际存在的 `core`、`desktop`，去除缺失的 `html` 模块以及无用的 GWT/Gretty 插件。
- 改为标准 `src/main/java` 目录，通过 Maven Central 获取依赖。
- 保留 libGDX 1.11.0 和 Java 8 字节码目标；构建和验证使用 JDK 21。
- `desktop:dist` 生成包含依赖和资源的可运行 JAR。

运行时只修复了影响可移植性的 WAV 时长读取：去除 `E:/code/phirunner/assets/` 绝对路径，改为 libGDX 资源流与 JDK 音频 API，并正确关闭流。旧的 jaudiotagger 依赖随之移除。

`PhigrosChart.java`、`CommonUtil.java` 和两个模块的 `build.gradle` 因此与解压基线存在功能或构建配置差异；其余解压出的源码和配置除路径、空白规范外保持原样。没有重写判定或渲染算法。

## 取回原始上传包

可以在 GitHub 浏览上述原始提交，或者在另一个目录检出保留标签：

```bash
git worktree add ../PhigrosRemake-manual-uploads legacy/manual-uploads
```

历史未被重写，因此常规完整克隆仍会下载历史中的原始压缩包。只需要当前源码时，可以使用 `git clone --depth 1`；需要旧标签时再获取完整历史。
