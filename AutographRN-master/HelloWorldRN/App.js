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
		style={{width: 700, height: 550}}
		myheight={500}
		/>
//	<Circle
//		style={{width: 200, height: 200}}
//		color="#ee3142"
//		radius={100}
//		/>
      </View>
    );
  }
  
}
