/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React from 'react';
import {
    AppRegistry,
} from 'react-native';

import FirstPage from './src/FirstRNPage';
import SecondPage from './src/SecondRNPage';

AppRegistry.registerComponent('FirstRNPage', () => FirstPage);

AppRegistry.registerComponent('SecondRNPage', () => SecondPage);
