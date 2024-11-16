# Magic Dependencies



## 统一打包依赖 [flatten-maven-plugin](flatten-maven-plugin)

> clean、install 一下，你会发现每个模块根目录下多了一个 .flattened-pom.xml 文件

- updatePomFile 属性表示是否将生成的 .flattened-pom.xml 作为当前项目的 pom 文件。
默认只有打包的时候（package、install、deploy）会将 .flattened-pom.xml 做为当前项目的 pom 文件，但是打包类型 pom 的 pom.xml 中的占位符是不会被替换的。如果想要都被替换，那就将 updatePomFile 的属性设置为 true 吧。如果 flattenMode 被设置为 bom，updatePomFile 默认属性值为 true。