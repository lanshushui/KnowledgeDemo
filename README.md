每个loader 的buildLoadData方法 会将 model 转为 data


传入 string 后，GLide工作流程
StringLoader 会将string 包装为 Uri ，并将Uri传如下一个 ModelLoader
UrlUriLoader 会将Uri 包装为 GlideUrl ，并将GlideUrl传如下一个 ModelLoader
默认情况：
HttpGlideUrlLoader 会将GlideUrl 包装为 InputStream ，内部使用HttpUrlFetcher获得InputStream  并将InputStream传如下一个 ModelLoader
依赖okhttp3-integration库后
OkHttpUrlLoader 会将GlideUrl 包装为 InputStream ，内部使用OkHttpStreamFetcher获得InputStream  并将InputStream传如下一个 ModelLoader

