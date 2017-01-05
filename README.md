# ConnectDemo
一个RN程序，包括Android原生界面与RN界面数据互传互调，以及三种RN界面调用原生模块的数据的测试

## 原生界面跳转至RN界面
包括直接跳转，携带数据跳转，跳转后返回获取从RN界面带回来的数据

## RN界面跳转至原生界面
包括直接跳转，携带数据跳转，跳转后返回获取从原生界面带回来的数据

## RN界面与原生模块的通信
包括三种方式：Callback回调，Promise以及通过RCTDeviceEventEmitter方式。
其中CallBack和Promise是由JS端主动调用，而RCTDeviceEventEmitter方式则是Native端主动向JS端发生消息，JS端被动接收。
