✰✰✰✰✰
插件化demo代码
    //插入资源
    private fun insertResources(dexPath1: String,dexPath2: String) {
        val newAssetManager: AssetManager = AssetManager::class.java.newInstance()
        val addAssetPath: Method = AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
        addAssetPath.invoke(newAssetManager, packageResourcePath)
        addAssetPath.invoke(newAssetManager, dexPath1)
        addAssetPath.invoke(newAssetManager, dexPath2)
        //创造  Resources
        val newResources: Resources =
            Resources(newAssetManager, resources.displayMetrics, resources.configuration)
        //替换application.baseContext下的mResources属性
        val mResourcesField = getField(application.baseContext.javaClass, "mResources")
        mResourcesField?.set(application.baseContext, newResources)
        //替换application.baseContext下的 mPackageInfo属性 的 mResources属性
        val packageInfoField = getField(application.baseContext.javaClass, "mPackageInfo")
        val mPackageInfoObj = packageInfoField?.get(application.baseContext)
        val mResourcesField2 = getField(mPackageInfoObj?.javaClass, "mResources")
        mResourcesField2?.set(mPackageInfoObj, newResources)
        //替换avtivity的baseContext的mTheme属性为null
        val themeField = getField(baseContext.javaClass, "mTheme")
        themeField?.set(baseContext, null)
    }
    //插入dex
    private fun insertDex(dexPath1: String,dexPath2: String) {
        val pathListField =
            getField(BaseDexClassLoader::class.java, "pathList"); // DexPathList类的Field
        val pathListObj = pathListField?.get(this.classLoader)
        //获得classLoader下的pathList的Element[]类实例
        val dexElementsField = getField(pathListObj?.javaClass, "dexElements"); //Element[]类的Field
        var oldElementsObj = dexElementsField?.get(pathListObj)
        //利用apk创建dex文件
        val files1 = listOf<File>(File(dexPath1))
        val files2 = listOf<File>(File(dexPath2))
        val makeDexElements = pathListObj?.javaClass?.getDeclaredMethod(
            "makePathElements",
            List::class.java,
            File::class.java,
            List::class.java
        )
        makeDexElements?.isAccessible = true
        var newElementsObj1 = makeDexElements?.invoke(pathListObj, files1, null, null)
        var newElementsObj2 = makeDexElements?.invoke(pathListObj, files2, null, null)
        //创造新的Element[]类实例并进行pathList下的dexElements属性替换
        val combined = Array.newInstance(Class.forName("dalvik.system.DexPathList\$Element"), 3)
        System.arraycopy(oldElementsObj!!, 0, combined, 0, 1)
        System.arraycopy(newElementsObj1!!, 0, combined, 1, 1)
        System.arraycopy(newElementsObj2!!, 0, combined, 2, 1)
        dexElementsField?.set(pathListObj, combined)
    }


✰✰✰✰✰
打包一个 apk 需要哪些 task
gradlew android-gradle-plugin-source:assembleDebug --console=plain

Task	                                                对应实现类	                  作用
preBuild	 	                                        空 task，                     只做锚点使用
preDebugBuild	 	                                    空 task，                     只做锚点使用，与 preBuild 区别是这个 task 是 variant 的锚点
compileDebugAidl	                                    AidlCompile	                  处理 aidl
compileDebugRenderscript	                            RenderscriptCompile	          处理 renderscript
checkDebugManifest	                                    CheckManifest	              检测 manifest 是否存在
generateDebugBuildConfig	                            GenerateBuildConfig	           生成 BuildConfig.java
prepareLintJar	                                        PrepareLintJar	               拷贝 lint jar 包到指定位置
generateDebugResValues	                                GenerateResValues	           生成 resvalues，generated.xml
generateDebugResources	 	                            空 task，                       锚点
mergeDebugResources	                                    MergeResources	               合并资源文件
createDebugCompatibleScreenManifests	                CompatibleScreensManifest	    manifest 文件中生成 compatible-screens，指定屏幕适配
processDebugManifest	                                MergeManifests	                合并 manifest 文件
splitsDiscoveryTaskDebug	                            SplitsDiscovery	                生成 split-list.json，用于 apk 分包
processDebugResources	                                ProcessAndroidResources	aapt    打包资源
generateDebugSources	 	                            空 task，                       锚点
javaPreCompileDebug	                                    JavaPreCompileTask	            生成 annotationProcessors.json 文件
compileDebugJavaWithJavac	                            AndroidJavaCompile	            编译 java 文件
compileDebugNdk	NdkCompile	                            编译                             ndk
compileDebugSources	 	                                空 task，                        锚点使用
mergeDebugShaders	                                    MergeSourceSetFolders	         合并 shader 文件
compileDebugShaders	                                    ShaderCompile	                 编译 shaders
generateDebugAssets	 	                                空 task，                         锚点
mergeDebugAssets	                                    MergeSourceSetFolders	          合并 assets 文件
transformClassesWithDexBuilderForDebug	                DexArchiveBuilderTransform	      class 打包 dex
transformDexArchiveWithExternalLibsDexMergerForDebug	ExternalLibsMergerTransform	      打包三方库的 dex，在 dex 增量的时候就不需要再 merge 了，节省时间
transformDexArchiveWithDexMergerForDebug	            DexMergerTransform	              打包最终的 dex
mergeDebugJniLibFolders	MergeSouceSetFolders	        合并 jni                          lib 文件
transformNativeLibsWithMergeJniLibsForDebug	            MergeJavaResourcesTransform	      合并 jnilibs
transformNativeLibsWithStripDebugSymbolForDebug	        StripDebugSymbolTransform	      去掉 native lib 里的 debug 符号
processDebugJavaRes                                   	ProcessJavaResConfigAction	      处理 java res
transformResourcesWithMergeJavaResForDebug	            MergeJavaResourcesTransform	      合并 java res
validateSigningDebug	                                ValidateSigningTask	              验证签名
packageDebug	                                        PackageApplication                打包 apk
assembleDebug	                                        空 task，                          锚点

transformClassesAndResourcesWithR8For$variantName        TransformTask          R8 proguard混淆代码                       
transformClassesAndResourcesWithProguardFor$variantName  TransformTask          proguard混淆代码   


processDebugResources aapt任务输出的是_ap文件 本质上是zip包
包括三部分
'AndroidManifest.xml'
'resources.arsc'
'res/**/*'

groovy写法 解压到某个目录
FileTree apFiles = project.zipTree(apFile)
File unzipApDir = new File(apFile.parentFile, 'ap_unzip')
unzipApDir.delete()
project.copy {
    from apFiles
    into unzipApDir
    include 'AndroidManifest.xml'
    include 'resources.arsc'
    include 'res/**/*'
}


✰✰✰✰✰
PorterDuff.Mode图形混合处理

1.PorterDuff.Mode.CLEAR
所绘制不会提交到画布上，也就是不显示内容。

2.PorterDuff.Mode.SRC
显示绘制图片的上层图片。

3.PorterDuff.Mode.DST
显示绘制图片下层图片。

4.PorterDuff.Mode.SRC_OVER
正常绘制显示，上下层绘制叠盖。

5.PorterDuff.Mode.DST_OVER
上下层都显示，下层居上显示。

6.PorterDuff.Mode.SRC_IN
取两层绘制交集。显示上层。

7.PorterDuff.Mode.DST_IN
取两层绘制交集。显示下层。

8.PorterDuff.Mode.SRC_OUT
取上层绘制非交集部分。

9.PorterDuff.Mode.DST_OUT
取下层绘制非交集部分。

10.PorterDuff.Mode.SRC_ATOP
取下层非交集部分与上层交集部分。

11.PorterDuff.Mode.DST_ATOP

取上层非交集部分与下层交集部分。
12.PorterDuff.Mode.XOR
异或：去除两图层交集部分。

13.PorterDuff.Mode.DARKEN
取两图层全部区域，交集部分颜色加深。

14.PorterDuff.Mode.LIGHTEN
取两图层全部，点亮交集部分颜色。

15.PorterDuff.Mode.MULTIPLY
取两图层交集部分叠加后颜色。

16.PorterDuff.Mode.SCREEN
取两图层全部区域，交集部分变为透明色
                                                         

   
   