'use strict';

import React, { Component} from 'react';
import PropTypes from 'prop-types';
import {
  View,
  requireNativeComponent
} from 'react-native';

const MAutograph = requireNativeComponent('MAutograph', {
  propTypes: {
    myheight: PropTypes.number,
    ...View.propTypes // 包含默认的View的属性
  },
});

class Autograph extends Component {

  static propTypes = {
    myheight: PropTypes.number,
	    ...View.propTypes // 包含默认的View的属性
  }

  render() {
    const { style, myheight} = this.props;

    return (
      <MAutograph
	    style={style}
        myheight={myheight}
      />
    );
  }

}

module.exports = Autograph;
