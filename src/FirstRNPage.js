/**
 * Created by tfn on 17-1-4.
 */
import React, {Component} from 'react';
import {
    StyleSheet,
    Text,
    View,
    TouchableOpacity,
    NativeModules,
    ToastAndroid,
    DeviceEventEmitter,
} from 'react-native';

export default class FirstRNPage extends Component {

    constructor(props) {
        super(props);
        this.state = {
            resultStr: '',
        };

    }

    componentWillMount() {
        DeviceEventEmitter.addListener('EventFromNative', (msg) => {
            ToastAndroid.show('通过RCTDeviceEventEmitter获取的当前时间：' + msg.TIME, ToastAndroid.SHORT);
        });
    }

    render() {
        return (
            <View style={styles.container}>
                <Text style={styles.title}>
                    原始RN界面
                </Text>

                <TouchableOpacity style={styles.button} onPress={this.onButtonClick.bind(this)}>
                    <Text style={styles.buttonText}>
                        点击跳转至原生界面
                    </Text>
                </TouchableOpacity>

                <TouchableOpacity style={styles.button} onPress={this.onButtonClickForResult.bind(this)}>
                    <Text style={styles.buttonText}>
                        点击跳转至原生界面,返回原生界面数据
                    </Text>
                </TouchableOpacity>

                <Text style={styles.resultText}>
                    {this.state.resultStr}
                </Text>

                <TouchableOpacity style={styles.button} onPress={this.onCallbackClick}>
                    <Text style={styles.buttonText}>
                        点击通过Callback获取时间
                    </Text>
                </TouchableOpacity>

                <TouchableOpacity style={styles.button} onPress={this.onPromiseClick}>
                    <Text style={styles.buttonText}>
                        点击通过Promise获取时间
                    </Text>
                </TouchableOpacity>

                <TouchableOpacity style={styles.button} onPress={this.onDeviceEventEmitterClick}>
                    <Text style={styles.buttonText}>
                        点击通过RCTDeviceEventEmitter获取时间
                    </Text>
                </TouchableOpacity>

            </View>
        );
    }

    /**
     * 点击通过Callback方式获取Native模块的时间
     */
    onCallbackClick = () => {
        NativeModules.MyMessageModule.getCallbackMsg((timeMsg) => {
            ToastAndroid.show('通过Callback获取的当前时间：' + timeMsg, ToastAndroid.SHORT);
        });
    };

    /**
     * 点击通过Callback方式获取Native模块的时间
     */
    onPromiseClick = () => {
        NativeModules.MyMessageModule.getPromiseMsg()
            .then((timeMsg) => {
                ToastAndroid.show('通过Promise获取的当前时间：' + timeMsg, ToastAndroid.SHORT);
            }).catch(
            (error) => {
                ToastAndroid.show('获取当前时间失败：' + error, ToastAndroid.SHORT);
            }
        );
    };

    /**
     * 点击通过RCTDeviceEventEmitter方式获取Native模块的时间
     */
    onDeviceEventEmitterClick = () => {
        NativeModules.MyMessageModule.getListenerMsg();
    };

    /**
     * 点击从RN界面跳转至Android原生界面，携带数据过去
     */
    onButtonClick() {
        NativeModules.MyIntentModule.startNativeActivity('com.connectdemo.NativeActivity', '我是从RN界面带过来的数据');
        this.setState({
            resultStr: '',
        });
    }

    /**
     * 点击从RN界面跳转至Android原生界面，携带数据过去，并返回Android原生界面的数据
     */
    onButtonClickForResult() {
        NativeModules.MyIntentModule.startNativeActivityForResult('com.connectdemo.NativeActivity', '我是从RN界面带过来的数据', 100)
            .then((result) => {
                this.setState({
                    resultStr: result,
                });
            }).catch((errorStr) => {
            alert('错误：' + errorStr);
        });

        this.setState({
            resultStr: '',
        });
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#F5FCFF',
    },
    title: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10,
    },
    button: {
        marginTop: 35,
        justifyContent: 'center',
        alignItems: 'center',
        height: 30,
        marginHorizontal: 20,
        backgroundColor: '#48BBEC',
        borderRadius: 5,
        borderWidth: 1,
        borderColor: '#48BBEC',
    },
    buttonText: {
        fontSize: 18,
        textAlign: 'center',
    },
    resultText: {
        fontSize: 18,
        textAlign: 'center',
        marginTop: 35,
    },
});