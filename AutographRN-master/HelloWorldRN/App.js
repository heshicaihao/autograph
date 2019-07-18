import React, { Component } from 'react';
import {
  StyleSheet,
  Text,
  View } from 'react-native';
import Circle from './Circle'
import Autograph from './Autograph'

export default class HelloWorldApp extends Component {
  render() {
    return (
<View style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>

<Autograph
  style={{width: 475, height: 330}}
  myheight={330}
/>
      </View>
    );
  }
  
}
