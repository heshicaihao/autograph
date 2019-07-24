import React, { Component } from 'react';
import {
  StyleSheet,
  Text,
  View ,
Button,
NativeModules 
} from 'react-native';
import Circle from './Circle'
import Autograph from './Autograph'

export default class HelloWorldApp extends Component {
  clickMe(){
    NativeModules.ToastExample.getDistance(22.5362579907,113.9553466268,22.5379116697, 113.9313512825)
  }
  render() {
    return (
<View style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>

<Button
 onPress={this.clickMe}
  title="Learn More"
  color="#841584"

/>
<Autograph
  style={{width: 475, height: 330}}
  myheight={330}
/>
      </View>
    );
  }
  
}
