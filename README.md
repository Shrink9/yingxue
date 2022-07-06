# 应学前台系统

**根据B站陈老师视频学习完成，前端代码未作修改，后端代码是自己所写，加入了自己的理解和设计，和视频中所讲略有差异，仅供参考。**<br/>
视频链接：<br/>[https://www.bilibili.com/video/BV1764y1676c?spm_id_from=333.1007.top_right_bar_window_custom_collection.content.click&vd_source=aaa694664e737a6e51add82015de84a9](https://www.bilibili.com/video/BV1764y1676c?spm_id_from=333.1007.top_right_bar_window_custom_collection.content.click&vd_source=aaa694664e737a6e51add82015de84a9)
> 注意：后端配置中心中的配置文件由于涉及到账号密码等隐私遂不便公开，大家记得自己配置。

> 提示：<br/>
1.如果遇到跨域问题一时半会儿解决不了，可以考虑换个思路，我使用的是用nginx做代理，大家可以试试。
简单来说就是把前端和后端都通过nginx代理了，这样浏览器不管是请求前端还是后端都先访问nginx（因此在浏览器看来前端和后端的地址是一样的，就不存在跨域的问题了。），然后nginx根据请求路径把请求转发给前端或后端。<br/>
2.如果提示'anywhere'不是内部或外部命令，也不是可运行的程序，首先确认你是否安装了node.js，再确认是否安装了anywhere。anywhere安装语句：npm install anywhere -g
