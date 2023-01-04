# README
## 项目结构
```
|------ cn.ecut.tomastu
  |------ bean          (Database Entity)
  |------ tableModel    (TableModel for JTable)
  |------ ui            (Application User Interface)
  |------ utils         (Utils for some feature)
  |------ Main.java     (Application entrance)
```

## 使用方法
1. 首先确认项目中是否已引入MySQL驱动。一般情况下，IDE在第一次加载此项目时，Maven会自动分析项目依赖并自动获取。如果未获取，请手动添加MySQL驱动。
2. 修改`cn.ecut.tomastu.utils`包下的`SqlUilts.java`文件的JDBC URI。
3. 连接自己提供服务的MySQL数据库，并在数据库控制台中执行`[ProjectBaseDirection]/src/main/resouces/DatabaseInit.sql`中的SQL语句，以初始化数据库数据。
4. 设置运行/调试配置。类型为“应用程序”，主类为`cn.ecut.tomastu.Main`, JDK版本为1.8。
5. 编译并运行程序即可。
