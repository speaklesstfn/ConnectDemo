/**
 * Created by tfn on 17-1-4.
 */
import React, {Component} from 'react';
import {
    StyleSheet,
    Text,
    View,
    NativeModules,
    BackAndroid,
} from 'react-native';

export default class SecondRNPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            valueStr: '',
        };
    }

    /**
     * 组件渲染完毕后会回调该生命周期函数，我们在这里获取从Android原生界面传递过来的数据
     */
    componentDidMount() {
        console.log('componentDidMount方法回调');
        NativeModules.MyIntentModule.getDataFromIntent(
            (str) => {
                console.log('成功回调数据：' + str);
                this.setState({
                    valueStr: str,
                });
            },
            (error) => {
                alert('错误：' + error);
            }
        );

        BackAndroid.addEventListener('hardwareBackPress', () => {
            NativeModules.MyIntentModule.finishRNActivity("test从第二个RN界面返回给Native界面数据");
        });

    }

    componentDidUnMount() {
        BackAndroid.removeEventListener('hardwareBackPress');
    }

    render() {
        return (
            <View style={styles.container}>
                <Text style={styles.title}>
                    第二个RN界面
                </Text>

                <Text style={styles.valueText}>
                    从原生Android界面传递过来的数据：{this.state.valueStr}
                </Text>
            </View>
        );
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
    valueText: {
        fontSize: 18,
        textAlign: 'center',
        marginTop: 35,
    },
});