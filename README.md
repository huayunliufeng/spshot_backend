## spshot_backend               [![https://img.shields.io/badge/version-v1.0.6-green](https://img.shields.io/badge/version-v1.0.6-green)](https://github.com/huayunliufeng/spshot_backend.git)               [![](<https://img.shields.io/badge/author-huayunliufeng-orange>)](https://github.com/huayunliufeng/spshot_backend.git)

### 一、项目说明

- 请投意合是一个基于微信小程序的在线投票系统，可实现基本的投票功能。包括发布和参与投票、用户反馈、查询投票结果、接收微信消息等功能。
- 每个投票可设置多个问题，每个问题包含多个选项；投票可设置是否匿名、是否公开、参与密码、最大参与人数和有效时间等。
- 投票发布后需要经过管理员或运维审核通过方可在大厅展示，未公开的投票只能通过搜索标题或参与码进行参与。
- 后端使用了自定义的权限管理。包含动态菜单、角色管理、权限控制等。不同的管理员拥有的权限不同。
- 后端功能包括运营图表、推广管理、投票管理、用户管理等。
- 本项目是该系统的服务器端代码，所有项目地址：
  - github 地址：
    - 服务器端：https://github.com/huayunliufeng/spshot_backend.git
    - 后端：https://github.com/huayunliufeng/spshot_web.git
    - 小程序端：https://github.com/huayunliufeng/spshot.git
  - gitee 地址：
    - 服务器端：https://gitee.com/hylf/spshot_backend.git
    - 后端：https://gitee.com/hylf/spshot_web.git
    - 小程序端：https://gitee.com/hylf/spshot.git

### 二、项目架构

- 后端：springboot，springcloud，quartz，mybatis，redis，mongo 等。

- 前端：Vue.js，ElementUI，Echarts，Ajax 等。

- 如需查看项目完整开发文档可关注公众号华风部落回复：spshot开发文档。

- 完整开发文档包含：
  1. 请投意合API(v1.0.6).pdf
  2. 请投意合服务器端(v1.0.6).pdf
  3. 请投意合后端(v1.0.6).pdf
  4. 请投意合架构图和技术栈(v1.0.6).pdf
  5. 请投意合数据库关系模型图(v1.0.6).pdf
  6. 请投意合项目发布(v1.0.6).pdf
  7. 请投意合小程序端(v1.0.6).pdf
  8. 请投意合业务流程图（小程序端+后端）(v1.0.6).pdf
  9. 请投意合数据库文档(v1.0.6).xlsx
  10. spshot.sql，mysql 数据库文件
  11. spshot_mongo，mongodb 数据库文件。

