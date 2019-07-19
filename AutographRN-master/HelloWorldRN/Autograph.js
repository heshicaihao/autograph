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
    confirmbtntext: PropTypes.string,
	confirmbtntextsize: PropTypes.number,
	titletextsize: PropTypes.number,
	cleaniconsize: PropTypes.number,
	cleantextsize: PropTypes.number,
	token: PropTypes.string,
	type: PropTypes.string,
	workno: PropTypes.string,
    ...View.propTypes // 包含默认的View的属性
  },
});

class Autograph extends Component {

  static propTypes = {
    myheight: PropTypes.number,
    confirmbtntext: PropTypes.string,
	confirmbtntextsize: PropTypes.number,
	titletextsize: PropTypes.number,
	cleaniconsize: PropTypes.number,
	cleantextsize: PropTypes.number,
	token: PropTypes.string,
	type: PropTypes.string,
	workno: PropTypes.string,
	    ...View.propTypes // 包含默认的View的属性
  }

  render() {
    const { style, myheight,confirmbtntext,confirmbtntextsize, titletextsize,cleaniconsize,cleantextsize,token,type,workno} = this.props;

    return (
      <MAutograph
	    style={style}
        myheight={myheight}
        confirmbtntext={confirmbtntext}
		titletextsize={titletextsize}
        cleaniconsize={cleaniconsize}
        cleaniconsize={cleaniconsize}
		cleantextsize={cleantextsize}
        token={token}
        type={type}
		workno={workno}

      />
    );
  }

}

module.exports = Autograph;
