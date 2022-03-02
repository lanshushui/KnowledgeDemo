1.Android透明度
    100%	00
    99%	03
    98%	05
    97%	07
    96%	0A
    95%	0D
    94%	0F
    93%	12
    92%	14
    91%	17
    90%	1A
    89%	1C
    88%	1E
    87%	21
    86%	24
    85%	26
    84%	29
    83%	2B
    82%	2E
    81%	30
    80%	33
    79%	36
    78%	38
    77%	3B
    76%	3D
    75%	40
    74%	42
    73%	45
    72%	47
    71%	4A
    70%	4D
    69%	4F
    68%	52
    67%	54
    66%	57
    65%	59
    64%	5C
    63%	5E
    62%	61
    61%	63
    60%	66
    59%	69
    58%	6B
    57%	6E
    56%	70
    55%	73
    54%	75
    53%	78
    52%	7A
    51%	7D
    50%	80
    49%	82
    48%	85
    47%	87
    46%	8A
    45%	8C
    44%	8F
    43%	91
    42%	94
    41%	96
    40%	99
    39%	9C
    38%	9E
    37%	A1
    36%	A3
    35%	A6
    34%	A8
    33%	AB
    32%	AD
    31%	B0
    30%	B3
    29%	B5
    28%	B8
    27%	BA
    26%	BD
    25%	BF
    24%	C2
    23%	C4
    22%	C7
    21%	C9
    20%	CC
    19%	CF
    18%	D1
    17%	D4
    16%	D6
    15%	D9
    14%	DB
    13%	DE
    12%	E0
    11%	E3
    10%	E6
    9%	E8
    8%	EB
    7%	ED
    6%	F0
    5%	F2
    4%	F5
    3%	F7
    2%	FA
    1%	FC
    0%	FF
    
2.控制台中文乱码
在Android Studio的设置中，terminal设置，environment variable设置中添加：LESSCHARSET=utf-8，然后重启就好了。

3.uiautomator2 自动化测试工具 
https://cloud.tencent.com/developer/article/1689624
https://www.cnblogs.com/xmmc/p/10827495.html
手机安装ATX： pip install --pre uiautomator2  和  uiautomator2 init
电脑cmd命令：weditor
当出现 本地服务器未启动 问题时
使用 adb devices 获得设备udid(序列号) 网页手动填入id进行connect

4.git rebase 合并commit用法
  1.git rebase -i HEAD~3
  2.最新commit在最下面 按i进入编辑  从上到下 修改为 r f f f f...(合并到最旧的commit，并修改commit信息)
  3.按esc退出编辑 输入 :wq 保存
  
  
5.电脑快速截图手机屏幕
 adb exec-out screencap -p > C:\Users\YY\Desktop\1.png

6.Android stuido 报错处理方式
java.lang.RuntimeException: Invocation failed Unexpected end of file from server
at org.jetbrains.git4idea.GitAppUtil.sendXmlRequest(GitAppUtil.java:30)
at org.jetbrains.git4idea.http.GitAskPassApp.main(GitAskPassApp.java:58)
Caused by: java.net.SocketException: Unexpected end of file from server

--> File - Settings - Git - use credential helper

  

                                                         

   
   